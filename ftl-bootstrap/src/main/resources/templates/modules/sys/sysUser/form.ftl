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
                    <form class="form-horizontal" id="user_form">
                        <div class="form-group" style="display: none">
                            <label class="col-sm-2 control-label">ID</label>
                            <div class="col-sm-10">
                                <input type="text" name="id" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>用户名</label>
                            <div class="col-sm-10">
                                <input type="text" placeholder="请输入用户名" name="username" class="form-control">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">真实姓名</label>
                            <div class="col-sm-10">
                                <input type="text" name="realName" placeholder="请输入真实姓名" class="form-control">
                            </div>
                        </div>
                        <#--<div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">密码</label>
                            <div class="col-sm-10">
                                <input type="password"  class="form-control" name="password">
                            </div>
                        </div>-->
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>邮箱</label>

                            <div class="col-sm-10">
                                <input type="text" name="email" placeholder="请输入邮箱" class="form-control">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">电话</label>

                          <div class="col-sm-10">
                                <input type="text" name="phone"  placeholder="请输入电话" class="form-control">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">城市</label>

                            <div class="col-sm-10">
                                <input type="text" name="city"  placeholder="请输入城市" class="form-control">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">地址</label>

                            <div class="col-sm-10">
                                <input type="text" name="address"  placeholder="请输入地址" class="form-control">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">星座</label>

                            <div class="col-sm-10">
                                <select class="form-control m-b" name="constellation">

                                </select>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">性别</label>
                            <div class="col-sm-10">
                                <label  style="margin-left: 20px;" class="radio-inline"><input type="radio" value="1" name="sex">男</label>
                                <label  style="margin-left: 20px;" class="radio-inline"><input type="radio" checked="" value="0" name="sex">女</label>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">状态</label>
                            <div class="col-sm-10">
                                <label  style="margin-left: 20px;" class="radio-inline"><input type="radio" value="1" name="status">停用</label>
                                <label  style="margin-left: 20px;" class="radio-inline"><input type="radio" checked="" value="0" name="status">启用</label>
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
    var userFormValidator;
    userFormValidator = $("#user_form").validate({
        // 定义校验规则
        rules: {
            username: {
                required: true
            },
            email: {
                required: true
            }
        },
        messages: {
            username: {
                required: "该输入项为必输项！"
            },
            email: {
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
                url: "/sys/user/saveOrUpdate",
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

    $(function () {
        //constellation
        $('select[name="constellation"]').empty();
        $.ajax({
            type: "POST",
            dataType: "json",
            cache: false,
            async:false,
            url: "/data/dic/getBscDicCodeItemListByTypeCode/CONSTELLATION",
            success: function (result) {
                $('select[name="constellation"]').append('<option>--请选择--</option>');
                if(result && result.length>0){
                    for (var i=0;i<result.length;i++){
                        var val=result[i];
                        $('select[name="constellation"]').append('<option value="'+val.itemName+'">'+val.itemName+'</option>');
                    }
                }
            }
        });
    })

</script>
<script src="/static/hPlus/js/plugins/iCheck/icheck.min.js"></script>
<script>
    $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
</script>
</body>

</html>
