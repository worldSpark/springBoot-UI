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
    $table = $('#usa_list_table').bootstrapTable('destroy').bootstrapTable({
        url: '/stock/getStockListByType/usa',                      //请求后台的URL（*）
        method: 'GET',                      //请求方式（*）
        toolbar: '#toolbar',              //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 20,                     //每页的记录行数（*）
        pageList: [20],        //可供选择的每页的行数（*）
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
                keyword:$('input[name="keyword"]').val()
            };
            return temp;
        },
        columns: [{
            checkbox: true,
            visible: true                  //是否显示复选框
        }, {
            field: 'symbol',
            title: '股票代码'
        }, {
            field: 'cname',
            title: '股票名称'
        }, {
            field: 'preclose',
            title: '昨收'
            /*,
                formatter: emailFormatter*/
        }, {
            field: 'open',
            title: '今开'
        }, {
            field: 'price',
            title: '当前股价',
            formatter:textFormat
        }, {
            field: 'high',
            title: '最高'
        }, {
            field: 'low',
            title: '最低'
        }, {
            field: 'volume',
            title: '成交量'
        }, {
            field: 'mktcap',
            title: '成交额'
        }, {
            field: 'chg',
            title: '涨跌幅',
            formatter:isZero
        }, {
            field: 'diff',
            title: '涨跌额',
            formatter:isZero
        } , {
            field: 'amplitude',
            title: '振幅',
            formatter:isZero
        }, {
            field: 'category',
            title: '行业',
            formatter:isZero
        } ]
    });
}

function isZero(value,row,index) {
    if(value>0){
        return '<span class="text-danger">'+value+'</span>';
    }else if(value<0){
        return '<span class="text-info">'+value+'</span>';
    }else {
        return value;
    }
}


function textFormat(value,row,index) {
    var val=row.diff;
    if(val>0){
        return '<span class="text-danger">'+value+'</span>';
    }else if(val<0){
        return '<span class="text-info">'+value+'</span>';
    }else {
        return value;
    }
}




