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
                <div class="ibox-content">
                    <form class="form-horizontal" id="interface_form">
                        <div class="form-group" style="display: none">
                            <label class="col-sm-2 control-label">ID</label>
                            <div class="col-sm-10">
                                <input type="text" name="id" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>接口名称</label>
                            <div class="col-sm-10">
                                <input type="text" placeholder="请输入接口名称" name="interfaceName" class="form-control">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>接口链接</label>
                            <div class="col-sm-10">
                                <input type="text" placeholder="请输入接口链接" name="interfaceUrl" class="form-control">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">分组</label>
                            <div class="col-sm-10">
                                <input type="text" placeholder="请输入分组" name="interfaceGroup" class="form-control">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">排序号</label>
                            <div class="col-sm-10" style="width: 68%;">
                                <input type="text" placeholder="请输入排序号" name="interfaceSort" class="form-control">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">备注</label>
                            <div class="col-sm-10">
                                <textarea type="text" placeholder="请输入备注" name="description" class="form-control"></textarea>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group text-right" style="width: 100%;position: relative">
                            <div class="col-sm-4 col-sm-offset-2" style="width: 100%;position: absolute;right: 0px;bottom: -30px;">
                                <button id="saveUserBtn" class="btn btn-primary" type="submit">保存</button>
                                <button id="closeBtn" class="btn btn-white" onclick="closeLayer()" type="button">关闭</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    var interfaceFormValidator;
    interfaceFormValidator = $("#interface_form").validate({
        // 定义校验规则
        rules: {
            interfaceName: {
                required: true
            },
            interfaceUrl: {
                required: true
            }
        },
        messages: {
            interfaceName: {
                required: "该输入项为必输项！"
            },
            interfaceUrl: {
                required: " 该输入项为必输项！"
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
                url: "/data/interface/saveOrUpdate",
                data: params,
                success: function (result) {
                    if (result.resultStat=="SUCCESS") {
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
<script src="/static/hPlus/js/plugins/iCheck/icheck.min.js"></script>
<script>
    $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
</script>
</body>

</html>
