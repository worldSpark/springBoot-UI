<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="投票开始" prop="voteTime">
        <el-date-picker
                v-model="queryParams.takeTime"
                type="daterange"
                align="left"
                unlink-panels
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="截止日期"
                @change="dateChange">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:vote:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="voteList">
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="用户选择的候选项的上限" align="center" prop="optionNum" />
      <el-table-column label="用户提交自定义候选项" align="center" prop="isCustomOption">
        <template slot-scope="scope">
            {{ scope.row.isCustomOption==1?'是':scope.row.isCustomOption==2?'否':'未知'}}
        </template>
      </el-table-column>
      <el-table-column label="是否记名投票" align="center" prop="isRegistered">
        <template slot-scope="scope">
            {{ scope.row.isRegistered==1?'是':scope.row.isRegistered==2?'否':'未知'}}
        </template>
      </el-table-column>
      <el-table-column label="投票开始时间" align="center" prop="startTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.startTime) }}</span>
            </template>
      </el-table-column>
      <el-table-column label="投票截止时间" align="center" prop="endTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.endTime) }}</span>
            </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:vote:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:vote:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改投票对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="152px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="可选候选项的上限" prop="optionNum" label-width="152px">
            <el-input-number v-model="form.optionNum" controls-position="right" :min="1" />
        </el-form-item>
        <el-form-item label="用户提交自定义候选项" prop="isCustomOption" label-width="152px">
              <el-select v-model="form.isCustomOption" placeholder="请选择">
                <el-option
                  v-for="item in customoPtions"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
        <el-form-item label="记名投票" prop="isRegistered">
            <el-select v-model="form.isRegistered" placeholder="请选择">
                <el-option
                  v-for="item in customoPtions"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value"
                >
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="投票开始时间" prop="startTime">
          <el-date-picker clearable size="small"
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择投票开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="投票结截止时间" prop="endTime">
          <el-date-picker clearable size="small"
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择投票截止时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="设置允许投票用户">
          <el-tree
            class="tree-border"
            :data="userTreeOptions"
            show-checkbox
            ref="user"
            node-key="id"
            empty-text="加载中，请稍后"
            :props="defaultProps"
          ></el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listVote, getVote, delVote, addVote, updateVote, exportVote } from "@/api/vote/vote";
import { treeselect as userTreeselect, voteUserTreeselect } from "@/api/system/user";
export default {
  name: "Vote",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      id: '',
      customoPtions: [
        {
            value: 1,
            name: "是"
        },
        {
            value: 2,
            name: "否"
        }
      ],
      // 用户树列表
      userTreeOptions: [],
      // 用户集合
      userOptions: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 投票表格数据
      voteList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        optionNum: null,
        isCustomOption: null,
        isRegistered: null,
        startTime: null,
        endTime: null,
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询投票列表 */
    getList() {
      this.loading = true;
      listVote(this.queryParams).then(response => {
        this.voteList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询用户树结构 */
    getUserTreeselect() {
      userTreeselect().then(response => {
        this.userTreeOptions = response.data;
      });
    },
    /** 根据投票id查询用户树结构 */
    getUserVoteTreeselect(voteId) {
      return voteUserTreeselect(voteId).then(response => {
        this.userTreeOptions = response.users;
        return response;
      });
    },
     // 所有用户节点数据
    getUserAllCheckedKeys() {
      // 目前被选中的用户节点
      let checkedKeys = this.$refs.user.getCheckedKeys();
      // 半选中的用户节点
      let halfCheckedKeys = this.$refs.user.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
    dateChange(data){
      if(data){
        const startStr = (this.moment(data[0]).format("YYYY-MM-DD") + " 00:00:00").replace(/-/g,"/");
        const endStr = (this.moment(data[1]).format("YYYY-MM-DD") + " 23:59:59").replace(/-/g,"/");

        this.queryParams.startTime = Date.parse(startStr)/1000;
        this.queryParams.endTime = Date.parse(endStr)/1000;
      } else{
        this.queryParams.startTime = null;
        this.queryParams.endTime = null;
      }

    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        title: null,
        optionNum: null,
        isCustomOption: null,
        isRegistered: null,
        startTime: null,
        endTime: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getUserTreeselect()
      this.userOptions = [];
      this.open = true;
      this.title = "添加投票";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      const userVote = this.getUserVoteTreeselect(id);
      getVote(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.$nextTick(() => {
          userVote.then(res => {
            let checkedKeys = res.checkedKeys
            checkedKeys.forEach((v) => {
                this.$nextTick(()=>{
                    this.$refs.user.setChecked(v, true ,false);
                })
            })
          });
        });
        this.title = "修改投票";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.form.userIds = this.getUserAllCheckedKeys();
            updateVote(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.userIds = this.getUserAllCheckedKeys();
            addVote(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除【请填写功能名称】编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delVote(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    }
  }
};
</script>
