<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <title>注册</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <link rel="stylesheet" href="/static/layui/css/layui.css" />
    <link rel="stylesheet" href="/static/css/admin.css" />
    <link rel="stylesheet" type="text/css" media="screen and (max-width:992px)" href="/static/css/admin.mobile.css">
</head>

<body>
<!-- 布局容器 -->
<div class="layui-layout layui-layout-admin">
    <!-- 主体 -->
    <div class="layui-body">
        <!-- 主要内容 -->
        <div class="container">
            <form class="layui-form" action="">
                <table class="layui-table layui-table-add layui-table-detail" lay-skin="nob">
                    <tbody>
                    <tr>
                        <th>用户名</th>
                        <td>
                            <div>
                                <input type="text" name="username" lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input w350">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>真实姓名</th>
                        <td>
                            <div>
                                <input type="text" name="realName" placeholder="请输入真实姓名" autocomplete="off" class="layui-input w350">
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <th>手机号</th>
                        <td>
                            <div class="table-input">
                                <input type="text" name="phone" lay-verify="required|phone"  placeholder="请输入手机号" autocomplete="off" class="layui-input">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>城市</th>
                        <td>
                            <div class="table-input">
                                <input type="text" name="city" lay-verify="required"  placeholder="请输入城市" autocomplete="off" class="layui-input">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>星座</th>
                        <td>
                            <div class="table-input">
                                <input type="text" name="constellation" lay-verify="required"  placeholder="请输入星座(如:射手座)" autocomplete="off" class="layui-input">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>原始密码</th>
                        <td>
                            <div class="table-input">
                                <input type="password" name="password" lay-verify="required" placeholder="请输入原始密码" autocomplete="off" class="layui-input">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>确认密码</th>
                        <td>
                            <div class="table-input">
                                <input type="password" name="confirm" placeholder="请输入确认密码" autocomplete="off" class="layui-input">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>邮箱</th>
                        <td>
                            <div class="table-input">
                                <input type="text" name="email" placeholder="请输入邮箱" lay-verify="email" autocomplete="off" class="layui-input">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>验证码</th>
                        <td>
                            <div>
                                <input type="text" name="verify" placeholder="请输入验证码" autocomplete="off" class="layui-input w350">
                                <span class="yzm layui-btn layui-btn-normal" lay-submit="" lay-filter="showVerify">获取邮箱验证码</span>
                                <span class="yzm layui-btn layui-btn-gray" style="display: none;">重新发送(120秒)</span>
                            </div>
                        </td>
                    </tr>

                    </tbody>
                </table>
                <div class="layui-table-btn2 ">
                    <div class="layui-btn" lay-submit="" lay-filter="submitBtn">确 定</div>
                    <div class="layui-btn layui-btn-primary cancel">返 回</div>
                </div>
            </form>
        </div>
    </div>
</div>


<!-- 可不引入 -->
<script type="text/javascript" src="/static/js/vue.min.js"></script>

<script type="text/javascript" src="/static/layui/layui.js"></script>
<#--<script type="text/javascript" src="/static/js/fastclick.js"></script>-->
<#--<script type="text/javascript" src="/static/js/admin.js"></script>-->
<script>

</script>
<script>
    layui.use(['jquery','form', 'layedit', 'laydate'], function(){
        var $ = layui.jquery,
                form = layui.form
                ,layer = layui.layer
                ,layedit = layui.layedit
                ,laydate = layui.laydate;
       /* $(function () {
            FastClick.attach(document.body);
        })*/
        $('.cancel').click(function () {
            window.history.back();
        })
        form.verify({
            username: function(value){
                if(value.length < 5){
                    return '用户名至少得5个字符啊';
                }
            }
            ,password: [/(.+){6,12}$/, '密码必须6到12位']
            /*,content: function(value){
                layedit.sync(editIndex);
            }*/
        });

        form.on('submit(showVerify)', function(data){
            var email =$('input[name="email"]').val();
            if(email){
                $.ajax({
                    async: true,
                    data:{email:email},
                    type:'post',
                    url: "/sys/login/showVerify",
                    success: function (result) {
                        layer.msg(result.mess);
                    }
                });
            }else{
                layer.alert("请输入邮箱",{
                    title:"警告"
                })
            }
            return false;
        });

        form.on('submit(submitBtn)', function(data){
            var beans=data.field;
            var password=beans.password;
            var confirm=beans.confirm;
            if(password!=confirm){
                layer.alert("密码输入不一致！");
                return;
            }
            $.ajax({
                async: true,
                data:data.field,
                type:'post',
                url: "/sys/login/register",
                success: function (result) {
                    if(result.resultStat=="SUCCESS"){
                        layer.confirm('注册成功，返回登陆页面？', function(index){
                            layer.close(index);
                            location.href="/";
                        });
                    }else{
                        layer.alert("注册失败，"+result.mess);
                    }
                }
            });
            return false;
        });
    });

</script>
</body>

</html>