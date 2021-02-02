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
    $table = $('#interface_list_table').bootstrapTable('destroy').bootstrapTable({
        url: '/data/interface/pages',                      //请求后台的URL（*）
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
            field: 'interfaceName',
            title: '接口名称',
            align: 'center',
            valign: 'middle',
            minWidth:100
        },{
            field: 'interfaceUrl',
            title: '接口链接',
            valign: 'middle',
            width:300
        }/*, {
            field: 'interfaceGroup',
            title: '分组',
            align: 'center',
            valign: 'middle',
            minWidth:100
        }, {
            field: 'interfaceSort',
            title: '排序',
            align: 'center',
            valign: 'middle',
            minWidth:160
        }*/, {
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
    var url=row.interfaceUrl;
    var result = "";
    result += "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"toLogin('" + url + "')\" title='登录'><span class='glyphicon glyphicon-circle-arrow-right'></span> 登录</a>";
    result += "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"addForm('" + id + "','编辑接口')\" title='编辑'><span class='fa fa-pencil'></span> 编辑</a>";
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

function toLogin(url) {
    window.open(url,"_blank");
}

function DeleteByIds(id) {
    if(!id){
        layer.alert("请选择删除数据");
        return;
    }
    layer.confirm('你确定删除该接口？', {
        time: 0 //不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function(index){
            $.ajax({
                type: "POST",
                dataType: "json",
                cache: false,
                url: "/data/interface/deleteOnlyOne/"+id,
                success: function (result) {
                    if (result.resultStat=="SUCCESS") {
                        layer.msg('删除成功！', {
                            time: 2000 //20s后自动关闭
                        });
                        layer.close(index);
                        $('#interface_list_table').bootstrapTable('refresh');
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
    var data=$("#interface_list_table").bootstrapTable('getSelections');
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
    layer.confirm('你确定删除所选的接口？', {
        time: 0 //不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function(index){
            $.ajax({
                type: "POST",
                url: "/data/interface/delete",
                data:{ids:ids},
                success: function (result) {
                    if (result.resultStat=="SUCCESS") {
                        layer.msg('删除成功！', {
                            time: 2000 //20s后自动关闭
                        });
                        layer.close(index);
                        $('#interface_list_table').bootstrapTable('refresh');
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
    interfaceFormLayer= layer.open({
        type: 2,
        title: type,
        area: ['880px', '500px'],
        maxmin: true,
        shade: 0.8,
        closeBtn: 1,
        shadeClose: true,
        content: '/data/interface/form',
        end: function () {
            $('#interface_list_table').bootstrapTable('refresh');
        },
        success: function(layero, index){
            var body=layer.getChildFrame('body',index);
            var $form=$(body).find("#interface_form");
            if(type!="新增接口"){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    cache: false,
                    async:false,
                    url: "/data/interface/info/"+id,
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