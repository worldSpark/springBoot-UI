<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="head-container">
           <span class="permission-alert">
            投票列表
          </span>
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="dataTree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="投票内容" prop="ticketNum">
            <el-input
              v-model="queryParams.content"
              placeholder="请输入内容"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <el-table v-loading="loading" :data="optionList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column :index="number" type="index" label="序号" width="80"></el-table-column>
          <el-table-column label="内容" align="center" prop="content" />
          <el-table-column label="票数" align="center" prop="ticketNum" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleView(scope.row)"
              >查看</el-button>
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

      </el-col>
    </el-row>
    <!--  用于查看用户信息的弹窗   -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-row :gutter="20" ref="form" :model="form" >
        <el-col :span="24" :xs="24">
          <el-card class="box-card">
              <div class="text-center">
                <div class="user-info-head" v-show="form.avatar"><img v-bind:src="form.avatar"  class="img-circle img-lg" /></div>
              </div>
              <ul class="list-group list-group-striped">
                <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span style="font-weight: bold">用户投票列表</span>
                  </div>
                  <div v-for="formItem in thisList" :key="formItem.userName" class="text item" style="margin-top: 5px;border-bottom: 1px silver solid;">
                    <span>用户昵称: {{formItem.nickName}}</span>
                    <span style="margin-left: 36px">性别: {{formItem.sex=='1'?'女':'男'}}</span>
                    <span style="margin-left: 23px">邮箱: {{formItem.email}}</span>
                    <span style="margin-left: 30px">投票内容: {{formItem.content}}</span>
                  </div>
                </el-card>
              </ul>
          </el-card>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import { listOption,treeselect, exportOption,getUserInfoByVote,getUserListByVote } from "@/api/vote/option";
import Editor from '@/components/Editor';
import store from "@/store";

export default {
  name: "Option",
  components: {
    Editor,
  },
  data() {
    return {
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      defExpandedKeys: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 表单参数
      form: {},
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 候选项表格数据
      optionList: [],
      // 弹出层标题
      title: "",
      //投票标题
      voteTitle: undefined,
      //选项
      deptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        voteId: null,
        content: null,
        ticketNum: null,
      },
      thisList: [],
      alphabeticalNumber:['A','B','C','D','E','F','G','H','I','J','K','L','M'
      ,'N','O','P','Q','R','S','T','U','V','W','X','Y','Z']
    };
  },
  created() {
    this.getVoteList().then((res) => {
      this.getList();
    })
  },
  methods: {
    /*查询投票列表*/
    getVoteList(){
      this.loading = true;
      return Promise.resolve(
          treeselect().then(res => {
            this.deptOptions = res.data;
            if(res.data!=undefined&&res.data.length>0){
              this.queryParams.voteId = res.data[0].id;
            }
          })
        );
    },
    /** 查询用户候选列表 */
    getList() {
      this.loading = true;
      listOption(this.queryParams).then(response => {
        this.optionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        ticketNum: null,//投票数
        userName: null,//用户名称
        nickName: null,//昵称
        dept: null,//部门对象
        email: null,//邮箱
        phonenumber: null,//电话
        sex: null,//性别
        avatar: null,//头像
        roles: null,//角色集合
        userList:[],//投票用户
      };
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
          pageSize: 10,
          voteId: null,
          content: null,
          ticketNum: null,
      };
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.reset();
      this.open = true;
      const that = this;
      this.title = "查看用户投票详情";
      getUserListByVote(that.queryParams.voteId,row.id).then(res => {
        that.thisList = res.data;
      }).catch(() => {
        console.log("获取失败");
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有候选项数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportOption(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.voteId = data.id;
      this.getList();
    },
    number(index) {
      return this.alphabeticalNumber[index + (this.queryParams.pageNum - 1) * this.queryParams.pageSize] ;
    },
  }
};
</script>
<style>
.permission-alert {
  width: 150px;
  margin-top: 15px;
  background-color: #f0f9eb;
  color: #67c23a;
  padding: 8px 16px;
  border-radius: 4px;
  display: inline-block;
}
</style>
