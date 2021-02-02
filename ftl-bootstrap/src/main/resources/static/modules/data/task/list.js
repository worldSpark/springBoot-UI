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
    $table = $('#task_list_table').bootstrapTable('destroy').bootstrapTable({
        url: '/data/scheduler/pages',                      //请求后台的URL（*）
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
        pageList: [5,10,20,50,100],        //可供选择的每页的行数（*）
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
        columns: [/*{
            checkbox: true,
            visible: true                  //是否显示复选框
        },*/ {
            field: 'jobName',
            title: '任务名',
            align: 'center',
            valign: 'middle',
            minWidth:100
        },{
            field: 'beanClass',
            title: 'Bean名称',
            valign: 'center',
            minWidth:160
        }, {
            field: 'jobGroup',
            title: '分组',
            align: 'center',
            valign: 'middle',
            minWidth:100
        }, {
            field: 'cronExpression',
            title: 'cron表达式',
            align: 'center',
            valign: 'middle',
            minWidth:160
        }, {
            field: 'jobStatus',
            title: '状态',
            align: 'center',
            valign: 'middle',
            minWidth:100,
            formatter:statusFormatter
        }, {
            field: 'description',
            title: '备注',
            align: 'center',
            valign: 'middle',
            minWidth:100
        }, {
            field:'id',
            title: '操作',
            minWidth: 240,
            align: 'center',
            valign: 'middle',
            formatter:actionFormatter
        }, ]
    });
}

//操作栏的格式化
function actionFormatter(value, row, index) {
    var id = row.id;
    var result = "";
    if(row.jobStatus==1){
        result += "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"changeStatus('" + id + "',1)\" title='暂停'><span class='fa fa-pause'></span> 暂停</a>";
    }else {
        result += "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"changeStatus('" + id + "',0)\" title='启动'><span class='fa fa-play'></span> 启动</a>";
    }
    //result += "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"addForm('" + id + "', '查看用户')\" title='查看'><span class='glyphicon glyphicon-search'></span> 查看</a>";
    result += "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"addForm('" + id + "','编辑任务')\" title='编辑'><span class='fa fa-pencil'></span> 编辑</a>";
    result += "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"DeleteByIds('" + id + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span> 删除</a>";

    return result;
}

function statusFormatter(value,row,index) {
    if(value==1){
        return "启动";
    }else if(value==0){
        return "暂停";
    }
}

function reloadTable() {
    $table.bootstrapTable('refresh');
}

function DeleteByIds(id) {
    if(!id){
        layer.alert("请选择删除数据");
        return;
    }
    layer.confirm('你确定删除该任务？', {
        time: 0 //不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function(index){
            $.ajax({
                type: "POST",
                dataType: "json",
                cache: false,
                url: "/data/scheduler/deleteOnlyOne/"+id,
                success: function (result) {
                    if (result.resultStat=="SUCCESS") {
                        layer.msg('删除成功！', {
                            time: 2000 //20s后自动关闭
                        });
                        layer.close(index);
                        $('#task_list_table').bootstrapTable('refresh');
                    } else {
                        layer.alert("删除失败，"+result.mess);
                    }
                },
                error: function (result) {
                    layer.alert("删除失败，"+result.mess);
                }
            });
        }
    });
    
}

function batchDelete() {
    var data=$("#task_list_table").bootstrapTable('getSelections');
    var ids=[];
    if(!data||data.length==0){
        layer.alert("请选择删除的数据！");
        return;
    }else{
        for(var i=0;i<data.length;i++){
            var row=data[i];
            ids.push(row.id);
        }
    }
    console.log(data);
    layer.confirm('你确定删除所选的用户？', {
        time: 0 //不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function(index){
            $.ajax({
                type: "POST",
                url: "/data/scheduler/delete",
                data:{ids:ids},
                success: function (result) {
                    if (result.resultStat=="SUCCESS") {
                        layer.msg('删除成功！', {
                            time: 2000 //20s后自动关闭
                        });
                        layer.close(index);
                        $('#task_list_table').bootstrapTable('refresh');
                    } else {
                        layer.alert("删除失败，"+result.mess);
                    }
                },
                error: function (result) {
                    layer.alert("删除失败，"+result.mess);
                }
            });
        }
    });
}

function addForm(id,type) {
    taskFormLayer= layer.open({
        type: 2,
        title: type,
        area: ['880px', '500px'],
        maxmin: true,
        shade: 0.8,
        closeBtn: 1,
        shadeClose: true,
        content: '/data/scheduler/form',
        end: function () {
            $('#task_list_table').bootstrapTable('refresh');
        },
        success: function(layero, index){
            var body=layer.getChildFrame('body',index);
            var $form=$(body).find("#task_form");
            if(type!="新增任务"){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    cache: false,
                    async:false,
                    url: "/data/scheduler/info/"+id,
                    success: function (res) {
                        var result=res.data;
                        if(result){
                            for (i in result){
                                $form.find("input[name=\'"+i+"\']").val(result[i]);
                                $form.find("textarea[name=\'"+i+"\']").val(result[i]);
                            }
                        }
                    },
                    error: function (result) {
                        layer.alert("数据加载失败，"+result.mess);
                    }
                });
            }
        }
    });
}

/*//暂停
function pause() {
    var data=$("#task_list_table").bootstrapTable('getSelections');
    var ids=[];
    if(!data||data.length==0){
        layer.alert("请选择暂停的任务！");
        return;
    }else{
        for(var i=0;i<data.length;i++){
            var row=data[i];
            ids.push(row.id);
        }
    }
    layer.confirm('你确定暂停所选的任务？', {
        time: 0 //不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function(index){
            $.ajax({
                type: "POST",
                url: "/data/scheduler/pause",
                data:{ids:ids},
                success: function (result) {
                    if (result.resultStat=="SUCCESS") {
                        layer.msg('暂停成功！', {
                            time: 2000 //20s后自动关闭
                        });
                        layer.close(index);
                        $('#task_list_table').bootstrapTable('refresh');
                    } else {
                        layer.alert("暂停失败");
                    }
                },
                error: function (result) {
                    layer.alert("暂停失败，"+result.mess);
                }
            });
        }
    });
}

//恢复
function reback() {
    var data=$("#task_list_table").bootstrapTable('getSelections');
    var ids=[];
    if(!data||data.length==0){
        layer.alert("请选择恢复的任务！");
        return;
    }else{
        for(var i=0;i<data.length;i++){
            var row=data[i];
            ids.push(row.id);
        }
    }
    console.log(data);
    layer.confirm('你确定恢复所选的任务？', {
        time: 0 //不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function(index){
            $.ajax({
                type: "POST",
                url: "/data/scheduler/resume",
                data:{ids:ids},
                success: function (result) {
                    if (result.resultStat=="SUCCESS") {
                        layer.msg('恢复成功！', {
                            time: 2000 //20s后自动关闭
                        });
                        layer.close(index);
                        $('#task_list_table').bootstrapTable('refresh');
                    } else {
                        layer.alert("恢复失败");
                    }
                },
                error: function (result) {
                    layer.alert("恢复失败，"+result.mess);
                }
            });
        }
    });
}

//立即执行
function deal() {
    var data=$("#task_list_table").bootstrapTable('getSelections');
    var ids=[];
    if(!data||data.length==0){
        layer.alert("请选择立即执行的任务！");
        return;
    }else{
        for(var i=0;i<data.length;i++){
            var row=data[i];
            ids.push(row.id);
        }
    }
    console.log(data);
    layer.confirm('你确定立即执行所选的任务？', {
        time: 0 //不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function(index){
            $.ajax({
                type: "POST",
                url: "/data/scheduler/run",
                data:{ids:ids},
                success: function (result) {
                    if (result.resultStat=="SUCCESS") {
                        layer.msg('立即执行成功！', {
                            time: 2000 //20s后自动关闭
                        });
                        layer.close(index);
                        $('#task_list_table').bootstrapTable('refresh');
                    } else {
                        layer.alert("立即执行失败");
                    }
                },
                error: function (result) {
                    layer.alert("立即执行失败，"+result.mess);
                }
            });
        }
    });
}*/

function changeStatus(id, status) {
    var actCh;
    var cmd;
    if (status == 0) {
        cmd = 'start';
        actCh = "确认要开启任务吗？";
    } else {
        cmd = 'stop';
        actCh = "确认要停止任务吗？";
    }
    layer.confirm(actCh, {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: "/data/scheduler/changeJobStatus",
            type: "post",
            data: {
                'id': id,
                'cmd': cmd
            },
            success: function (result) {
                if (result.resultStat=="SUCCESS") {
                    layer.msg(result.mess, {
                        time: 2000 //20s后自动关闭
                    });
                    //layer.close(index);
                    $('#task_list_table').bootstrapTable('refresh');
                } else {
                    layer.alert(result.mess);
                }
            }
        });
    })
}