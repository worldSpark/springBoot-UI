<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>基本表单</title>
    <#include "../../common/base.ftl"/>
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
                    <form class="form-horizontal" id="task_form">
                        <div class="form-group" style="display: none">
                            <label class="col-sm-2 control-label">ID</label>
                            <div class="col-sm-10">
                                <input type="text" name="id" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>Bean名称</label>
                            <div class="col-sm-10">
                                <input type="text" placeholder="请输入Bean名称" name="beanClass" class="form-control">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>任务名</label>
                            <div class="col-sm-10">
                                <input type="text" placeholder="请输入任务名" name="jobName" class="form-control">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">任务分组</label>
                            <div class="col-sm-10">
                                <input type="text" placeholder="请输入任务分组" name="jobGroup" class="form-control">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>cron表达式</label>
                            <div class="col-sm-10" style="width: 68%;">
                                <input type="text" placeholder="请输入cron表达式" name="cronExpression" class="form-control">
                            </div>
                            <div class="input-group-append">
                                <button type="button" onclick="cronCreate()" class="btn btn-warning"><i class="fa fa-search"></i> cron生成器</button>
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

<#--<script src="/static/hPlus/plugins/iCheck/icheck.min.js"></script>
<script>
    $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
</script>-->

<script type="text/javascript">
    var taskFormValidator;
    taskFormValidator = $("#task_form").validate({
        // 定义校验规则
        rules: {
            beanClass: {
                required: true
            },
            jobName: {
                required: true
            },
            cronExpression:{
                required: true
            }
        },
        messages: {
            beanClass: {
                required: "该输入项为必输项！"
            },
            jobName: {
                required: " 该输入项为必输项！"
            },
            cronExpression: {
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
                url: "/data/scheduler/saveOrUpdate",
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
    
    function cronCreate() {
        parent.window.open('http://cron.qqe2.com/');
    }

</script>
<script src="/static/hPlus/js/plugins/iCheck/icheck.min.js"></script>
<script>
    $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
</script>
</body>

</html>
