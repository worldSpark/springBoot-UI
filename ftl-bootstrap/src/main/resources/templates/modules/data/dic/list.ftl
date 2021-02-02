<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>数据字典管理</title>
    <#include "/common/base.ftl"/>
    <style>
        .btn-default{
            color: grey;
            background-color: transparent;
        }
        .btn-default:hover{
            color: #767676;
            background-color: #eeeeee;
        }
    </style>
</head>

<body class="gray-bg">


<div class="wrapper wrapper-content animated" style="position: relative" >
    <!-- Panel Other -->
    <div class="ibox float-e-margins" style="width: 48%;position: absolute;left:10px;top:10px;">
        <div class="ibox-title">
            <h5><i class="fa fa-list"></i> 字典类型列表</h5>
        </div>
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <!-- Example Events -->
                    <div class="example-wrap">
                        <#--<h4 class="example-title">事件</h4>-->
                        <div class="example">
                            <div class="btn-group hidden-xs" id="toolbar" role="group">
                                <button type="button" class="btn btn-outline btn-white" title="新增类型" onclick="addFormType(null,'新增类型')">
                                    <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                                </button>
                                <div class="input-group">
                                    <input type="text" name="keyword" class="form-control" style="width:80%;margin-left: 4px;" placeholder="请输入查询内容"/>

                                    <button type="button" onclick="querySearchType(this)" title="查询" class="btn btn-outline btn-white">
                                        <i class="glyphicon glyphicon-search"></i>
                                    </button>

                                </div>
                            </div>
                            <table id="dicType_list_table" data-height="430" data-mobile-responsive="true">
                            </table>
                        </div>
                    </div>
                    <!-- End Example Events -->
                </div>
            </div>
        </div>
    </div>
    <div class="ibox float-e-margins" style="width: 48%;position: absolute;right:10px;top:10px;">
        <div class="ibox-title">
            <h5><i class="fa fa-pie-chart"></i> <span id="dataTitle"></span></h5>
        </div>
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <!-- Example Events -->
                    <div class="example-wrap">
                    <#--<h4 class="example-title">事件</h4>-->
                        <div class="example">
                        <#--<div class="alert alert-success" id="examplebtTableEventsResult" role="alert">
                            事件结果
                        </div>-->
                            <div class="btn-group hidden-xs" id="toolbar" role="group">
                                <button type="button" class="btn btn-outline btn-white" title="新增数据" onclick="addFormItem(null,null,'新增数据')">
                                    <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                                </button>
                                <div class="input-group">
                                    <input type="text" name="keyword" class="form-control" style="width:80%;margin-left: 4px;" placeholder="请输入查询内容"/>

                                    <button type="button" onclick="querySearchItem(this)" title="查询" class="btn btn-outline btn-white">
                                        <i class="glyphicon glyphicon-search"></i>
                                    </button>

                                </div>

                            </div>
                            <table id="dicItem_list_table" data-height="430" data-mobile-responsive="true">
                            </table>
                        </div>
                    </div>
                    <!-- End Example Events -->
                </div>
            </div>
        </div>
    </div>
    <!-- End Panel Other -->
</div>

<!--模态框新增编辑-->


<div class="modal fade" id="dicTypeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
    <div class="modal-dialog" style="width:600px;">
        <form  id="dicTypeForm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel2"></h4>
                </div>
                <div class="modal-body" style="height: 300px;overflow-y: scroll">
                    <div class="form-group row" style="display: none">
                        <label class="col-sm-2 control-label">ID:</label>
                        <div class="col-sm-10">
                            <input type="text" name="id" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label"><font color="red">*</font>类型编码:</label>
                        <div class="col-sm-10">
                            <input type="text" placeholder="请输入类型编码" name="typeCode" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label"><font color="red">*</font>类型名称:</label>
                        <div class="col-sm-10">
                            <input type="text" placeholder="请输入类型名称" name="typeName" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label">是否激活:</label>

                        <div class="col-sm-10">
                            <select class="form-control m-b" name="isActive">
                                <option value="0">是</option>
                                <option value="1">否</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label">备注:</label>
                        <div class="col-sm-10">
                            <textarea type="text" placeholder="请输入备注" name="typeMemo" class="form-control"></textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary">保存</button>
                </div>
            </div><!-- /.modal-content -->
        </form>
    </div><!-- /.modal -->
</div>

<div class="modal fade" id="dicItemModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog" style="width:600px;">
        <form  id="dicItemForm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel1"></h4>
                </div>
                <div class="modal-body" style="height: 330px;overflow-y: scroll">
                    <div class="form-group row" style="display: none">
                        <label class="col-sm-2 control-label">ID:</label>
                        <div class="col-sm-10">
                            <input type="text" name="id" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row" style="display: none">
                        <label class="col-sm-2 control-label">typeId:</label>
                        <div class="col-sm-10">
                            <input type="text" name="typeId" class="form-control" id="typeId">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label"><font color="red">*</font>数据编码:</label>
                        <div class="col-sm-10">
                            <input type="text" placeholder="请输入数据编码" name="itemCode" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label"><font color="red">*</font>数据名称:</label>
                        <div class="col-sm-10">
                            <input type="text" placeholder="请输入数据名称" name="itemName" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label">排序号:</label>
                        <div class="col-sm-10">
                            <input type="text" placeholder="请输入排序号" name="sortNo" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label">是否激活:</label>

                        <div class="col-sm-10">
                            <select class="form-control m-b" name="isActive">
                                <option value="0">是</option>
                                <option value="1">否</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label">备注:</label>
                        <div class="col-sm-10">
                            <textarea type="text" placeholder="请输入备注" name="itemMemo" class="form-control"></textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeItemModal()">关闭</button>
                    <button type="submit" class="btn btn-primary">保存</button>
                </div>
            </div><!-- /.modal-content -->
        </form>
    </div><!-- /.modal -->
</div>

<script type="text/javascript" src="/static/modules/data/dic/list.js"></script>
<script type="text/javascript">
    var dicTypeFormValidator;
    var dicItemFormValidator;
    var dicItem_list_table;

    function querySearchType(obj) {
        var keyword=$(obj).siblings('input[name="keyword"]').val();
        var paramas={keyword:keyword,rows:5};
        console.log(dicItem_list_table.queryParams);
        $.ajax({
            type: "get",
            url: "/data/dic/listType",
            data: paramas,
            dataType:"json",
            success : function(json) {
                $("#dicType_list_table").bootstrapTable('load', json);//主要是要这种写法
            }
        });
    }

    function querySearchItem(obj) {
        var keyword=$(obj).siblings('input[name="keyword"]').val();
        var typeId=$('#dicItemForm #typeId').val();
        var paramas={keyword:keyword,rows:5};
        $.ajax({
            type: "get",
            url: "/data/dic/listItem/"+typeId,
            data: paramas,
            dataType:"json",
            success : function(json) {
                $("#dicItem_list_table").bootstrapTable('load', json);//主要是要这种写法
            }
        });
    }

</script>
</body>

</html>
