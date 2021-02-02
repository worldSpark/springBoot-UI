
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>修改密码</title>
    <link rel="shortcut icon" href="favicon.ico">
    <#include "common/base.ftl"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form id="passwordMD" class="form-horizontal">
                        <div class="form-group">
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                    <span class="input-group-addon" style="width: 100px;"><font color="red">*</font>旧密码</span>
                                    <input type="password" name="oldpwd" id="oldpwd" placeholder="旧密码" class="form-control" style="width: 300px;">
                                </div>
                                <div class="input-group m-b">
                                    <span class="input-group-addon" style="width: 100px;"><font color="red">*</font>新密码</span>
                                    <input type="password" name="password" id="password" placeholder="新密码" class="form-control" style="width: 300px;">
                                </div>
                                <div class="input-group m-b">
                                    <span class="input-group-addon" style="width: 100px;"><font color="red">*</font>确认密码</span>
                                    <input type="password" placeholder="确认密码" name="confirmpwd" id="confirmpwd" class="form-control" style="width: 300px;">
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>
