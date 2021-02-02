<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>基本表单</title>
    <#include "/common/base.ftl"/>
    <#--<link href="/static/hPlus/css/plugins/iCheck/custom.css" rel="stylesheet">-->
    <style type="text/css">
        .show{
            display: inline-block;
        }
        .hide{
            display: none;
        }
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <#--<div class="ibox-title">
                    <h5><i class="fa fa-user"></i>&nbsp;用户信息</h5>
                </div>-->
                <div class="ibox-content">
                    <form class="form-horizontal" id="role_form">
                        <div class="form-group" style="display: none">
                            <label class="col-sm-2 control-label">ID</label>
                            <div class="col-sm-10">
                                <input type="text" name="id" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>角色名称</label>
                            <div class="col-sm-10">
                                <input type="text" placeholder="请输入角色名称" name="roleName" class="form-control">
                            </div>
                        </div>
                        <#--<div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>角色类型</label>
                            <div class="col-sm-10">
                                <select class="form-control m-b" name="roleType">
                                    <option value="">--请选择--</option>
                                    <option value="0">系统角色</option>
                                    <option value="1">其他</option>
                                </select>
                            </div>
                        </div>-->
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>角色状态</label>

                            <div class="col-sm-10">
                                <select class="form-control m-b" name="status">
                                    <option value="">--请选择--</option>
                                    <option value="0">启用</option>
                                    <option value="1">停用</option>
                                </select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">备注</label>

                            <div class="col-sm-10">
                                <textarea id="remark" name="remark" class="form-control"></textarea>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group text-right" style="width: 100%;position: relative">
                            <div class="col-sm-4 col-sm-offset-2" style="width: 100%;position: absolute;right: 0px;bottom: -30px;">
                                <button id="saveUserBtn" class="btn btn-primary" type="submit">保存</button>
                                <button class="btn btn-white" onclick="closeLayer()" type="button">关闭</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<#--<script src="/static/hPlus/plugins/iCheck/icheck.min.js"></script>
<script>
    $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
</script>-->

<script type="text/javascript">
    var userFormValidator;
    userFormValidator = $("#role_form").validate({
        // 定义校验规则
        rules: {
            roleName: {
                required: true
            },
            status: {
                required: true
            }
        },
        messages: {
            roleName: {
                required: "该输入项为必输项！"
            },
            status: {
                required: " 该输入项为必输项！"
            }
        },
        // 提交表单
        submitHandler: function (form) {
            //var params = new FormData(document.getElementById("user_form"));
            var params=$(form).serialize();
            // 异步保存
            $.ajax({
                type: "POST",
                dataType: "json",
                cache: false,
                url: "/sys/role/saveOrUpdate",
                data: params,
                success: function (result) {
                    if (result.resultStat=="SUCCESS") {
                        //var mylay = parent.layer.getFrameIndex("userFormLayer");
                       // parent.layer.close(mylay);
                        layer.msg('保存成功！', {
                            time: 2000 //20s后自动关闭
                        });
                        closeLayer();
                    } else {
                        layer.alert("保存失败，"+result.mess);
                    }
                },
                error: function (result) {
                    layer.alert("保存失败，"+result.mess);
                }
            });
        }
    });
    
    function closeLayer() {
        parent.colseLayer();
    }

</script>
</body>

</html>
