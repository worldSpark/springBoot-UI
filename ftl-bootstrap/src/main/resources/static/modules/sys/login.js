
    $("#login_form").validate({
        rules: {
            username: {
                required: true,
                minlength: 2
            },
            password: {
                required: true,
                minlength: 5
            }
        },
        messages: {
            username: {
                required: "请输入用户名",
                minlength: "用户名必需由两个字母组成"
            },
            password: {
                required: "请输入密码",
                minlength: "密码长度不能小于 5 个字母"
            }
        },
        submitHandler:function(form){
            $.ajax({
                type: "post",
                url: "/sys/login/signIn",
                data: $(form).serialize(),
                dataType: "json",
                success: function(data){
                    if(data.resultStat=="SUCCESS"){
                        window.location.href="/toIndex";
                    }else{
                        layer.tips(data.mess, '#login_form', {
                            tips: [1, '#3595CC'],
                            time: 4000
                        });
                    }
                }
            });
        }
    })
