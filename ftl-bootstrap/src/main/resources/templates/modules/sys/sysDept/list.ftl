<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>部门管理</title>
    <#include "/common/base.ftl"/>
    <link href="/static/hPlus/css/plugins/jsTree/style.min.css" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-4">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5><i class="fa fa-list"></i> 部门列表</h5>
                </div>
                <div class="ibox-content">
                    <div style="margin: 0px">
                        <button class="btn btn-white btn-xm" onclick="resetDeptInfo()"><i class="glyphicon glyphicon-plus"></i> 添加新部门</button>
                        <button class="btn btn-white btn-xm" onclick="showAllot()"><i class="fa 	fa-user"></i> 分配用户</button>
                    </div>
                    <div class="hr-line-dashed" style="margin: 10px 0"></div>
                    <div id="using_json"></div>
                </div>
            </div>
        </div>
        <div class="col-sm-8">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5><i class="fa fa-graduation-cap"></i> 部门信息</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal" id="dept_form">
                        <div class="form-group" style="display: none">
                            <label class="col-sm-2 control-label">ID</label>
                            <div class="col-sm-10">
                                <input id="id" type="text" name="id" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">上级部门</label>

                            <div class="col-sm-10">
                                <select id="parentId" class="form-control m-b" name="parentId">
                                    <option value="null">--请选择--</option>
                                </select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>部门名称</label>
                            <div class="col-sm-10" >
                                <input type="text" placeholder="请输入部门名称" name="deptName" class="form-control">
                            </div>

                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">图标</label>
                            <div class="col-sm-10" style="width: 68%;">
                                <input type="text" placeholder="请输入图标" name="icon" class="form-control">
                            </div>
                            <div class="input-group-append">
                                <button type="button" onclick="selIcons()" class="btn btn-warning"><i class="fa fa-search"></i> 选择图标</button>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">排序号</label>
                            <div class="col-sm-10">
                                <input type="text" placeholder="请输入排序号" name="sort" class="form-control">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group text-right" style="width: 100%;position: relative">
                            <div class="col-sm-4 col-sm-offset-2" style="width: 100%;position: absolute;right: 0px;bottom: -30px;">
                                <button id="saveUserBtn" class="btn btn-primary" type="submit"><i class="glyphicon glyphicon-floppy-saved"></i> 保存</button>
                                <button class="btn btn-danger" onclick="DeleteByIds()" type="button"><i class="glyphicon glyphicon-remove"></i> 删除</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<#--<script src="js/jquery.min.js?v=2.1.4"></script>
<script src="js/bootstrap.min.js?v=3.3.6"></script>
<script src="js/content.min.js?v=1.0.0"></script>-->
<script src="/static/hPlus/js/plugins/jsTree/jstree.min.js"></script>
<style>
    .jstree-open>.jstree-anchor>.fa-folder:before{content:"\f07c"}.jstree-default .jstree-icon.none{width:0}
</style>
<script src="/static/modules/sys/dept/list.js"></script>
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
                    $('#dept_form input[name="icon"]').val(text);
                    layer.close(iconLayer);
                })
            }
        });
    }
</script>
</body>

</html>
