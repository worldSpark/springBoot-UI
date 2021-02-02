<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>分配菜单</title>
    <#--<#include "/common/base.ftl"/>-->
    <link rel="shortcut icon" href="favicon.ico">
    <link href="/static/hPlus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/static/hPlus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="/static/hPlus/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="/static/hPlus/css/animate.min.css" rel="stylesheet">
    <link href="/static/hPlus/css/style.min.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row animated fadeInRight">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <#--<div class="ibox-title">
                    &lt;#&ndash;<h5><i class="glyphicon glyphicon-gift"></i> 分配菜单</h5>&ndash;&gt;
                </div>-->

                <div class="ibox-content timeline" id="menuList">

                </div>
            </div>
        </div>
    </div>
</div>

<script src="/static/hPlus/js/jquery.min.js?v=2.1.4"></script>
<script src="/static/hPlus/js/bootstrap.min.js?v=3.3.6"></script>
<script src="/static/hPlus/js/plugins/peity/jquery.peity.min.js"></script>
<script src="/static/hPlus/js/content.min.js?v=1.0.0"></script>
<script src="/static/hPlus/js/demo/peity-demo.min.js"></script>
<script src="/static/hPlus/js/plugins/iCheck/icheck.min.js"></script>
<script src="/static/layer-v3.1.1/layer/layer.js"></script>
<script>

    $(function () {
        console.log(${roleId});
        menuTreeList();
        setMenuSel();
    })


    function menuTreeList() {
        $.ajax({
            type: "post",
            url: "/sys/menu/treeList",
            dataType: "json",
            async:false,
            success: function (data) {
                $('#menuList').empty();
                if(data && data.length>0){
                    for (var i=0;i<data.length;i++){
                        var row=data[i];
                        var childs='';
                        if(row.children && row.children.length>0){
                            for (var j=0;j<row.children.length;j++){
                                var c=row.children[j];
                                var child='<div class="checkbox i-checks">' +
                                        '    <label><input type="checkbox" name="id" value="'+c.id+'"/> <i class="'+c.icon+'"></i> '+c.menuName+'</label>' +
                                        '  </div>';
                                childs+=child;
                            }
                        }
                        var div='<div class="timeline-item">\n' +
                                '                        <div class="row">\n' +
                                '                            <div class="col-xs-4 date" style="width: 16%;">\n' +
                                '                                <label style="cursor: pointer;">\n' +
                                '                                <div class="checkbox i-checks">\n' +
                                '\n' +
                                '                                        <input type="checkbox" name="id" value="'+row.id+'">\n' +
                                '                                </div>\n' +
                                '                                <span class="text-navy"><b class="'+row.icon+'"></b> '+row.menuName+'</span>\n' +
                                '                                </label>\n' +
                                '                            </div>\n' +
                                '                            <div class="col-xs-6 content" style="width: 84%;border:1px solid #eee">\n' +
                                '                                <div class="form-group">\n' +
                                '                                    <div class="col-sm-10">\n' + childs+
                                '                                    </div>\n' +
                                '                                </div>\n' +
                                '                            </div>\n' +
                                '                        </div>\n' +
                                '                    </div>';
                        $('#menuList').append(div);
                    }
                }
            }
        });
    }

    function setMenuSel() {
        //var roleId=$('#roleId').val();
        $.ajax({
            type: "POST",
            dataType: "json",
            async:false,
            cache: false,
            url: "/sys/menu/getSelMenuPermission/"+${roleId},
            success: function (result) {
                if(result && result.length){
                    for (var i=0;i<result.length;i++){
                        var value=result[i];
                        $('#menuList').find('input[value="'+value.id+'"]').prop("checked",true);
                        $('#menuList').find('input[value="'+value.id+'"]').parent("div").addClass("checked");
                    }
                }
            },
            error: function () {
                layer.alert("数据加载失败!");
            }
        });
    }
</script>
<script>
    window.onload=function(){
        $(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})
    }
</script>


</body>

</html>
