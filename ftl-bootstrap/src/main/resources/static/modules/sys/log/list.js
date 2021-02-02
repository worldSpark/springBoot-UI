/**
 * 初始化事件
 */
$(function () {
    InitMainTable();
})

var $table;
//初始化bootstrap-table的内容
function InitMainTable () {
    //记录页面bootstrap-table全局变量$table，方便应用
    $table = $('#log_list_table').bootstrapTable('destroy').bootstrapTable({
        url: '/sys/log',                      //请求后台的URL（*）
        method: 'GET',                      //请求方式（*）
        toolbar: '#toolbar',              //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 10,                     //每页的记录行数（*）
        pageList: [10,20,50,100],        //可供选择的每页的行数（*）
        search: false,                      //是否显示表格搜索
        strictSearch: true,
        showColumns: true,                  //是否显示所有的列（选择显示的列）
        showRefresh: true,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        //height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
        showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                  //是否显示父子表
        //得到查询的参数
        queryParams : function (params) {
            //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
            var temp = {
                rows: params.limit,                         //页面大小
                page: (params.offset / params.limit) + 1,   //页码
                sort: params.sort,      //排序列名
                sortOrder: params.order, //排位命令（desc，asc）
                keyword:$('input[name="keyword"]').val(),
                logDate:$('#logDate').val()
            };
            return temp;
        },
        columns: [{
            checkbox: true,
            visible: true                  //是否显示复选框
        }, {
            field: 'username',
            title: '用户名',
            width: 120,
            minWidth:100
        }, {
            field: 'operation',
            title: '用户操作',
            width: 120,
            minWidth:100
        }, {
            field: 'method',
            title: '请求方法',
            width: 240,
            minWidth:100
        }, {
            field: 'params',
            title: '请求参数',
            width: 240,
            minWidth:100
        }, {
            field: 'time',
            title: '执行时长(毫秒)',
            width: 80,
            minWidth:60
        }, {
            field: 'ip',
            title: 'IP地址',
            width: 120,
            minWidth:100
        }, {
            field: 'createDate',
            title: '日志时间',
            width: 180,
            minWidth:100,
            formatter:function (value,rows,index) {
                return datetimeFormat(value);
            }
        } ]
    });
    
    

}




