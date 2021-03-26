<template>
  <div class="dashboard-container">
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="peoples" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              参与投票总人数
            </div>
            <count-to :start-val="0" :end-val="totalMap.personTotal" :duration="1" class="card-panel-num"/>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-message">
            <svg-icon icon-class="form" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              总投票数
            </div>
            <count-to :start-val="0" :end-val="totalMap.voteTotal" :duration="1" class="card-panel-num"/>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="40" style="margin-left: 15px">
      <div id="barEcharts" style="height: 20rem;width: 70rem;"></div>
    </el-row>

    <el-row :gutter="40" style="margin-left: 15px">
      <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="投票标题" prop="title">
            <el-input
              v-model="queryParams.title"
              placeholder="投票标题"
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
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
            >投票</el-button>
          </el-col>

          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="voteList" highlight-current-row @row-click="rowClick">
          <el-table-column label="序号" type="index" align="center">
            <template slot-scope="scope">
              <span>{{(queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1}}</span>
            </template>
          </el-table-column>
          <el-table-column label="投票标题" align="center" prop="title" />
          <el-table-column label="开始时间" align="center" prop="startTime" />
          <el-table-column label="结束时间" align="center" prop="endTime" />
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />

        <!-- 投票框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="候选项" prop="contents">
              <el-checkbox-group v-model="form.contents" :min="1" :max="12" size="medium" >
                <el-checkbox v-for="(item, index) in contentOptions"
                             :key="index"  :label="item.content"
                ></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item :label="selRow.isRegistered=='1'?'记名投票':'匿名投票'" >
<!--              <el-switch id="thisRegistered"
                         style=""
                         v-model="selRow.isRegistered"
                         active-text="记名"
                         inactive-text="匿名" :active-value="1" :inactive-value="2">
              </el-switch>-->
            </el-form-item>
<!--            <el-form-item label="投票内容" prop="content">
              <el-input type="textarea" :rows="2" v-model="selRow.content" :min-height="192"/>
            </el-form-item>-->
            <el-form-item label="添加选项" v-show="selRow.isCustomOption==1" >
              <el-button
                style="padding-bottom: 0"
                icon="el-icon-circle-plus-outline"
                type="text"
                @click="addSelectItem"
              ></el-button>
            </el-form-item>
          </el-form>
          <div class="field-box" >
            <el-scrollbar class="right-scrollbar">
              <div v-for="(item, index) in tagList" :key="index" class="select-item">
                <el-input v-model="item.label" placeholder="选项名" size="small" />
                <div class="select-line-icon checkIcon" @click="deleteTagSelect(item,index)">
                  <i class="el-icon-remove-outline" />
                </div>
                <div class="el-icon-check checkIcon" @click="getTagSelect(item,index)">
                </div>
              </div>
            </el-scrollbar>
          </div>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>
      </div>
    </el-row>
  </div>
</template>

<script>
import CountTo from 'vue-count-to'
import { statisticsVote,getDataList,selectVoteListByTime,getUserByVoteRange } from "@/api/vote/vote";
import { voteOptionSelects,addUserVoteByList } from "@/api/vote/option";
import echarts from 'echarts';

export default {
  components:{CountTo},
  name: "index",
  data() {
    return {
      totalMap:{},
      thisData:[],
      // 遮罩层
      loading: true,
      // 表格数据
      voteList: [],
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        title: '',
      },
      // 弹出层标题
      title: "",
      // 显示搜索条件
      showSearch: true,
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {},
      selRow: {}, // 选中行数据
      // 表单校验
      rules: {
        contents: [
          { required: true, message: "候选项不能为空", trigger: "blur" }
        ],
      },
      queryVoteParams: {
        pageNum: 1,
        pageSize: 10,
        voteId:undefined
      },
      contentOptions:[],
      tagList:[],
    };
  },
  methods: {
    getInitByHtml(){
      statisticsVote().then(res => {
        this.totalMap = res.data;
      });
      getDataList().then(res => {
        this.thisData = res.data;
        if(this.thisData.length>0){
          this.initEcharts();
        }
      });
      this.getList();
    },
    rowClick(row) {
      this.selRow = row;
    },
    handleAdd() {
      this.reset();
      if(!this.selRow.id){
        this.msgError("请选择投票项目");
        return;
      }
      //判断是否在允许投票范围内
      getUserByVoteRange(this.selRow.id).then(res => {
        if(res.msg=="success"){
          this.open = true;
          this.title = "开始投票";
          //获取投票候选项
          voteOptionSelects(this.selRow.id).then(res => {
            this.contentOptions = res.data;
          })
        }else{
          this.msgError(res.msg);
        }
      }).catch(() => {
        console.log("获取您的投票权限失效");
      })
    },
    initEcharts(){
      var chartDom = document.getElementById('barEcharts');
      var myChart = echarts.init(chartDom);
      var option;
      var data = new Array();
      var dataNames = new Array();
      this.thisData.forEach(function (ele,inde){
        data.push(ele.total);
        dataNames.push(ele.name);
      });
      option = {
        title: {
          text: '前5名用户投票统计总数'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['投票数']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 5]
        },
        yAxis: {
          type: 'category',
          data: dataNames
        },
        series: [
          {
            name: '投票数',
            type: 'bar',
            data: data,
            itemStyle: {
              color: "#188df0"
            },
          }
        ]
      };
      option && myChart.setOption(option);
    },
    getList() {
      this.loading = true;
      selectVoteListByTime(this.queryParams).then(response => {
        if(response.rows.length>0){
          this.voteList = response.rows;
          this.total = response.total;
        }
        this.loading = false;
      })
    },
    submitForm() {
      if(this.form.contents.length>this.selRow.optionNum){
        this.msgError("已经达到上限候选项,上限数为:"+this.selRow.optionNum);
        return;
      }
      var contents = this.form.contents;
      this.$refs['form'].validate(valid => {
        if (!valid) return
        //过滤掉关于contentOptions中没有id的
        var thisVoteOptionIds = [];
        var thisVoteOptionValues = [];
        debugger;
        this.contentOptions.forEach(function (ele,index){
          contents.forEach(function (e,i){
            //有id说明是已存在的,判断content中是否内容相同,相同则添加,没有id说明是自定义选项中的,判断content中是否相等,相同则添加
            if(ele.content==e){
              if(ele.id){
                thisVoteOptionIds.push(ele.id);
              }else{
                thisVoteOptionValues.push(ele.content);
              }
              return;
            }
          })
        });
        debugger;

        var datas = {
          id:this.selRow.id,
          isCustomOption:this.selRow.isCustomOption,
          isRegistered:this.selRow.isRegistered,
          voteContents:thisVoteOptionValues,
          voteOptionIds:thisVoteOptionIds,
          content:this.selRow.content
        };
        addUserVoteByList(datas).then(res => {
          if(res.code==200){
            this.msgSuccess("投票成功");
            this.getInitByHtml();
          }
          //提交表单
          this.open = false;
          this.reset();
        }).catch(() => {
          //提交表单
          this.open = false;
          this.reset();
        })

      })
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.selRow.content = '';
      this.form = {
        contents:[],
      };
      this.resetForm("form");
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    addSelectItem() {
      //先判断是否达到上限
      /*if(this.form.contents.length>this.selRow.optionNum){
        this.msgError("已经达到上限候选项,上限数为:"+this.selRow.optionNum);
        return;
      }*/
      //再判断是否label已赋值,没有赋值不让新增
      if(!this.contentOptions[this.contentOptions.length-1].content){
        this.msgError("请先输入当前选项内容");
        return;
      }
      this.tagList.push({
        label: '',
        value: ''
      })
      this.contentOptions.push({
        label: '',
        value: ''
      })
    },
    getTagSelect(item, index) {
      //赋值label给当前选项值
      this.contentOptions[this.contentOptions.length-1].content = item.label;
      //删除当前行
      this.tagList.splice(index, 1);
    },
    deleteTagSelect(item, index){
      //移除当前checkBox
      this.contentOptions.splice(this.contentOptions.length-1,1);
      //删除当前行
      this.tagList.splice(index, 1);
    }
  },
  created() {
    this.getInitByHtml();
  }
};
</script>

<style scoped lang="scss">
.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3
      }
    }

    .icon-people {
      color: #42b983;
    }

    .icon-message {
      color: #409eff;
    }

    .icon-money {
      color: #ffba00;
    }

    .icon-shopping {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
.checkIcon{
  display: inline-block;
  margin-left: 5px;
}
</style>

