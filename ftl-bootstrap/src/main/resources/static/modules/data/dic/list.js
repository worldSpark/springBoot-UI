/**
 * 初始化事件
 */
$(function () {
    InitMainTable();
})

//初始化bootstrap-table的内容
function InitMainTable () {
    //记录页面bootstrap-table全局变量$table，方便应用
    $('#dicType_list_table').bootstrapTable('destroy').bootstrapTable({
        url: '/data/dic/listType',                      //请求后台的URL（*）
        method: 'GET',                      //请求方式（*）
        toolbar: 'false',              //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 5,                     //每页的记录行数（*）
        pageList: [5,10,20],        //可供选择的每页的行数（*）
        search: false,                      //是否显示表格搜索
        strictSearch: true,
        showColumns: false,                  //是否显示所有的列（选择显示的列）
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        //height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
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
            title: '序号',
            field: '',
            width:40,
            formatter: function (value, row, index) {
                //return index+1;
                var pageSize=$('#dicType_list_table').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
                var pageNumber=$('#dicType_list_table').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    //返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号

            }
        }, {
            field: 'typeCode',
            title: '编码',
            sortable: true
        }, {
            field: 'typeName',
            title: '名称',
            sortable: true
        }, {
            field:'id',
            title: '操作',
            width: 180,
            align: 'center',
            valign: 'middle',
            formatter:actionFormatterType
        }, ],
        formatRecordsPerPage: function (pageNumber) {
            return '';
        },
        formatShowingRows: function (pageFrom, pageTo, totalRows) {
            //$('#total1').text();
            return  '共'+totalRows +'条数据';
        },
        onClickRow: function (row) {
            onloadItemListTable(row.id);
            $('#dicItemForm #typeId').val(row.id);
            $('#dataTitle').html("数据列表 / <small>"+row.typeName+"</small>");
        },
        onLoadSuccess: function (data) {
            var rows=data.rows;
            if(rows && rows.length>0){
                var typeId=rows[0].id;
                onloadItemListTable(typeId);
                $('#dicItemForm #typeId').val(typeId);
                $('#dataTitle').html("数据列表 / <small>"+rows[0].typeName+"</small>");
            }
        }
    });
}

function onloadItemListTable (id) {
    dicItem_list_table=$('#dicItem_list_table').bootstrapTable('destroy').bootstrapTable({
        url: '/data/dic/listItem/'+id,                      //请求后台的URL（*）
        method: 'GET',                      //请求方式（*）
        toolbar: 'false',              //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 5,                     //每页的记录行数（*）
        pageList: [5,10,20],        //可供选择的每页的行数（*）
        search: false,                      //是否显示表格搜索
        strictSearch: true,
        showColumns: false,                  //是否显示所有的列（选择显示的列）
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        //height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                  //是否显示父子表
        //得到查询的参数
        queryParams : function (params) {
            //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
            var temp = {
                rows: params.limit,                         //页面大小
                page: (params.offset / params.limit) + 1,   //页码
                sort: params.sort,      //排序列名
                sortOrder: params.order //排位命令（desc，asc）
            };
            return temp;
        },
        formatRecordsPerPage: function (pageNumber) {
            return '';
        },
        formatShowingRows: function (pageFrom, pageTo, totalRows) {
            //$('#total1').text();
            return  '共'+totalRows +'条数据';
        },
        columns: [{
            title: '序号',
            field: '',
            width:40,
            formatter: function (value, row, index) {
                //return index+1;
                var pageSize=$('#dicItem_list_table').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
                var pageNumber=$('#dicItem_list_table').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    //返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
            }
        }, {
            field: 'itemCode',
            title: '编码',
            sortable: true
        }, {
            field: 'itemName',
            title: '名称',
            sortable: true
        }, {
            field:'id',
            title: '操作',
            width: 180,
            align: 'center',
            valign: 'middle',
            formatter:actionFormatterItem
        }, ],
        onClickRow: function (row) {
            onloadItemListTable(row.id);
        }
    });
}

//操作栏的格式化
function actionFormatterType(value, row, index) {
    var id = row.id;
    var result = "";
    result += "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"addFormType('" + id + "','编辑类型')\" title='编辑'><span class='fa fa-pencil'></span> 编辑</a>";
    result += "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"DeleteByIdsType('" + id + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span> 删除</a>";

    return result;
}

//操作栏的格式化
function actionFormatterItem(value, row, index) {
    var id = row.id;
    var typeId=row.typeId;
    var result = "";
    result += "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"addFormItem('" + id + "','"+typeId+"','编辑类型')\" title='编辑'><span class='fa fa-pencil'></span> 编辑</a>";
    result += "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"DeleteByIdsItem('" + id + "','"+typeId+"')\" title='删除'><span class='glyphicon glyphicon-remove'></span> 删除</a>";

    return result;
}

function DeleteByIdsType(id) {
    if(!id){
        layer.alert("请选择删除数据");
        return;
    }
    layer.confirm('你确定删除该数据？', {
        time: 0 //不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function(index){
            $.ajax({
                type: "POST",
                dataType: "json",
                cache: false,
                url: "/data/dic/deleteType/"+id,
                success: function (result) {
                    if (result.resultStat=="SUCCESS") {
                        //var mylay = parent.layer.getFrameIndex("userFormLayer");
                        // parent.layer.close(mylay);
                        layer.msg('删除成功！', {
                            time: 2000 //20s后自动关闭
                        });
                        layer.close(index);
                        $('#dicType_list_table').bootstrapTable('refresh');
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

function DeleteByIdsItem(id,typeId) {
    if(!id){
        layer.alert("请选择删除数据");
        return;
    }
    layer.confirm('你确定删除该数据？', {
        time: 0 //不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function(index){
            $.ajax({
                type: "POST",
                dataType: "json",
                cache: false,
                url: "/data/dic/deleteItem/"+id,
                success: function (result) {
                    if (result.resultStat=="SUCCESS") {

                        layer.msg('删除成功！', {
                            time: 2000 //20s后自动关闭
                        });
                        layer.close(index);
                        onloadItemListTable (typeId);
                        //$('#dicItem_list_table').bootstrapTable('refresh');
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

function addFormItem(id,typeId,type) {
    if(!typeId){
        typeId=$('#dicItemForm #typeId').val();
        if(!typeId){
            layer.alert("请先选择数据字典类型!");
            return;
        }
    }
    $('#dicItemModal').modal("show");
    $('#myModalLabel1').text(type);
    resetForm('#dicItemForm');
    if(id){
        $.ajax({
            type: "GET",
            dataType: "json",
            cache: false,
            async:false,
            url: "/data/dic/view/"+id,
            success: function (result) {
                if(result){
                    for (i in result){
                        $('#dicItemForm').find("input[name=\'"+i+"\']").val(result[i]);
                    }
                    $('#dicItemForm').find("textarea[name='itemMemo']").val(result.itemMemo);

                    $('#dicItemForm').find("select[name='isActive']").find('option[value="'+result.isActive+'"]').prop("selected",true);
                }
            },
            error: function (result) {
                layer.alert("数据加载失败!");
            }
        });
    }
}

function addFormType(id,type) {
    $('#dicTypeModal').modal("show");
    $('#myModalLabel2').text(type);
    resetForm('#dicTypeForm');
    if(id){
        $.ajax({
            type: "GET",
            dataType: "json",
            cache: false,
            async:false,
            url: "/data/dic/viewType/"+id,
            success: function (result) {
                if(result){
                    for (i in result){
                        $('#dicTypeForm').find("input[name=\'"+i+"\']").val(result[i]);
                    }
                    $('#dicTypeForm').find("textarea[name='typeMemo']").val(result.typeMemo);

                    $('#dicTypeForm').find("select[name='isActive']").find('option[value="'+result.isActive+'"]').prop("selected",true);
                }
            },
            error: function (result) {
                layer.alert("数据加载失败!");
            }
        });
    }
}

function resetForm(form){
    $(form).find('input[type=text],select,input[type=hidden],textarea').not('#typeId').each(function() {
        $(this).val('');
    });
}

dicTypeFormValidator = $("#dicTypeForm").validate({
    // 定义校验规则
    rules: {
        typeCode: {
            required: true
        },
        typeName: {
            required: true
        }
    },
    messages: {
        typeCode: {
            required: "该输入项为必输项！"
        },
        typeName: {
            required: "该输入项为必输项！"
        }
    },
    // 提交表单
    submitHandler: function (form) {
        var params=$(form).serialize();
        // 异步保存
        $.ajax({
            type: "POST",
            dataType: "json",
            cache: false,
            url: "/data/dic/saveOrUpdateType",
            data: params,
            success: function (result) {
                if (result.resultStat=="SUCCESS") {
                    layer.msg('保存成功！', {
                        time: 2000 //20s后自动关闭
                    });
                    InitMainTable();
                    $('#dicTypeModal').modal("hide");
                    //触发当前类型的数据字典
                    var typeId=result.data;
                    onloadItemListTable(typeId);
                    $('#dicItemForm #typeId').val(typeId);
                } else {
                    layer.alert("保存失败，"+result.mess);
                }
            },
            error: function () {
                layer.alert("保存失败，"+result.mess);
            }
        });
    }
});

dicItemFormValidator= $("#dicItemForm").validate({
    // 定义校验规则
    rules: {
        itemCode: {
            required: true
        },
        itemName: {
            required: true
        }
    },
    messages: {
        itemCode: {
            required: "该输入项为必输项！"
        },
        itemName: {
            required: "该输入项为必输项！"
        }
    },
    // 提交表单
    submitHandler: function (form) {
        var params=$(form).serialize();
        // 异步保存
        $.ajax({
            type: "POST",
            dataType: "json",
            cache: false,
            url: "/data/dic/saveOrUpdateItem",
            data: params,
            success: function (result) {
                if (result.resultStat=="SUCCESS") {
                    layer.msg('保存成功！', {
                        time: 2000 //20s后自动关闭
                    });
                    onloadItemListTable(result.data);
                    $('#dicItemModal').modal("hide");
                } else {
                    layer.alert("保存失败，"+result.mess);
                }
            },
            error: function () {
                layer.alert("保存失败，"+result.mess);
            }
        });
    }
});

function closeItemModal() {
    var typeId=$('#dicItemForm #typeId').val();
    onloadItemListTable(typeId);
}