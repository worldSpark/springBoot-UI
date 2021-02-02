<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>菜单管理</title>
    <#include "/common/base.ftl"/>
    <style type="text/css">
        .form-group{
            margin: 4px;
        }
        .row{
            padding: 3px 0px ;
        }
        .control-label{
            margin-top: 8px;
            text-align: right;
        }
    </style>
    <link rel="stylesheet" href="https://cdn.bootcss.com/jquery-treegrid/0.2.0/css/jquery.treegrid.min.css">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <!-- Panel Other -->
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5><i class="fa fa-list"></i> 菜单列表</h5>
        </div>
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <!-- Example Events -->
                    <div class="example-wrap">
                        <div class="example">
                            <div class="btn-group hidden-xs" id="toolbar" role="group">
                                <button type="button" class="btn btn-white" title="新增菜单" onclick="addForm(null,'新增菜单')">
                                    <i class="glyphicon glyphicon-plus" aria-hidden="true"></i> 新增
                                </button>
                                <#--<div class="col-sm-3" id="so">-->
                                    <#--<div class="input-group">
                                        <input type="text" name="keyword" class="form-control" style="width:80%;margin-left: 4px;" placeholder="请输入查询内容"  onkeydown="onKeyDown(event)"/>

                                        <button type="button" onclick="querySearch()" title="查询" class="btn btn-outline btn-white">
                                            <i class="glyphicon glyphicon-search"></i>
                                        </button>

                                    </div>-->

                            </div>
                            <table id="menu_list_table" data-height="520" data-mobile-responsive="true">
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
<div class="modal fade" id="myModal" tabindex="9999" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:800px;">
        <form  id="sysMenuForm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel"></h4>
                </div>
                <div class="modal-body" style="height: 400px;overflow-y: scroll">
                    <div class="form-group row" style="display: none">
                        <label class="col-sm-2 control-label">ID:</label>
                        <div class="col-sm-10">
                            <input type="text" name="id" class="form-control">
                            <input type="text" name="isSysMenu" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label">父级菜单:</label>

                        <div class="col-sm-10">
                            <select class="form-control m-b" name="parentId">
                                <option value="xx">--请选择--</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label"><font color="red">*</font>菜单名称:</label>
                        <div class="col-sm-10">
                            <input type="text" placeholder="请输入菜单名称" name="menuName" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label"><font color="red">*</font>菜单类型:</label>

                        <div class="col-sm-10">
                            <select class="form-control m-b" name="menuType">
                                <option value="menu">菜单</option>
                                <option value="api">接口</option>
                                <option value="button">按钮</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label">请求路径:</label>
                        <div class="col-sm-10">
                            <input type="text" placeholder="请输入请求路径" name="url" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label">权限控制:</label>
                        <div class="col-sm-10">
                            <input type="text" placeholder="请输入权限控制" name="permission" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label">图标:</label>
                        <div class="col-sm-10" style="width: 68%;">
                            <input type="text" placeholder="请输入图标" name="icon" class="form-control">
                        </div>
                        <div class="input-group-append">
                            <button type="button" onclick="selIcons()" class="btn btn-warning"><i class="fa fa-search"></i> 选择图标</button>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label">排序号:</label>
                        <div class="col-sm-10">
                            <input type="text" placeholder="请输入排序号" name="sort" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 control-label"><font color="red">*</font>是否显示:</label>

                        <div class="col-sm-10">
                            <select class="form-control m-b" name="isShow">
                                <option value="0">显示</option>
                                <option value="1">隐藏</option>
                            </select>
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
<#--<script src="https://cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>-->
<script src="https://cdn.bootcss.com/bootstrap-table/1.12.1/bootstrap-table.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-table/1.12.0/extensions/treegrid/bootstrap-table-treegrid.js"></script>
<script src="https://cdn.bootcss.com/jquery-treegrid/0.2.0/js/jquery.treegrid.min.js"></script>
<script type="text/javascript" src="/static/modules/sys/menu/tree.js"></script>
<script type="text/javascript">
    var iconLayer;
    function selIcons() {
        iconLayer= layer.open({
            type: 2,
            title: "选择图标",
            area: ['880px', '500px'],
            maxmin: true,
            shade: 0.8,
            closeBtn: 1,
            shadeClose: true,
            content: '/sys/menu/selIcons',
            btn: ['关闭'],
            /*yes: function(){
                alert("yes");
            }*/
            end: function () {
                //window.location.reload();
                //$('#user_list_table').bootstrapTable('refresh');
            },
            success: function(layero, index){
                var body=layer.getChildFrame('body',index);
                var $form=$(body).find(".bs-glyphicons-list");
                $form.children("li").dblclick(function () {
                    var text=$(this).text().trim();
                    $('input[name="icon"]').val(text);
                    layer.close(iconLayer);
                })
            }
        });
    }
</script>
</body>

</html>
