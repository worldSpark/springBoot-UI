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
    $table = $('#file_list_table').bootstrapTable('destroy').bootstrapTable({
        url: '/myfile/getTableList',                      //请求后台的URL（*）
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
        columns: [{
            checkbox: true,
            visible: true                  //是否显示复选框
        }, {
            field: 'fileName',
            title: '文件名',
            formatter:function (value,row,index) {
                return '<i class="fa fa-paperclip text-warning"> </i> &nbsp;<a onclick="loadHref(\''+row.fileUrl+'\')">'+value+'</a>';
            }
        }, {
            field: 'fileType',
            title: '文件类型',
            formatter: fileTypeFormatter
        }, {
            field:'id',
            title: '操作',
            width: 180,
            align: 'center',
            valign: 'middle',
            formatter:actionFormatter
        }, ],
        onLoadSuccess: function () {
        },
        onLoadError: function () {
            showTips("数据加载失败！");
        },
        onDblClickRow: function (row, $element) {
            var id = row.ID;
           // EditViewById(id, 'view');
        }
    });
}

function loadHref(url) {
    window.open("ftp://127.0.0.1/"+url);
}

//操作栏的格式化
function actionFormatter(value, row, index) {
    var id = row.id;
    return "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"downFile('" + id + "')\" title='下载'><span class='fa fa-cloud-download'></span> 下载</a>";
}

function fileTypeFormatter(value,row,index) {
    var val="";
    $.ajax({
        type: "post",
        dataType: "json",
        async: false,
        url: "/data/dic/getBscDicCodeItemListByTypeCode/FILE_TYPE/" + value,
        success: function (result) {
            console.log(result);
            val= result?result.itemName:'';
        }
    });
    return val;
}

function reloadTable() {
    $table.bootstrapTable('refresh');
}

function uploadFileModule() {
    var uploadFileFormLayer= layer.open({
        type: 2,
        title: "文件上传",
        area: ['880px', '500px'],
        maxmin: true,
        shade: 0.8,
        closeBtn: 1,
        shadeClose: true,
        content: '/myfile/form',
        btn: ['关闭'],
        yes: function(){
            layer.close(uploadFileFormLayer);
            $('#file_list_table').bootstrapTable('refresh');
        },
        end: function () {
            $('#file_list_table').bootstrapTable('refresh');
        }
    });
}


function batchDeletefile() {
    var data=$("#file_list_table").bootstrapTable('getSelections');
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
    layer.confirm('你确定删除所选的文件？', {
        time: 0 //不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function(index){
            $.ajax({
                type: "POST",
                url: "/myfile/batchDelete",
                data:{ids:ids},
                success: function (result) {
                    if (result.resultStat=="SUCCESS") {
                        layer.msg('删除成功！', {
                            time: 2000 //20s后自动关闭
                        });
                        layer.close(index);
                        $('#file_list_table').bootstrapTable('refresh');
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

function downFile(id) {
    window.open("/myfile/downLoad/"+id);
}