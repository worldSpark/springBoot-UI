<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>后台主页</title>
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->

   <#include "common/base.ftl"/>
</head>

<body  class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <#--<div class="nav-close">
            <i class="fa fa-times-circle"></i>
        </div>-->
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">

            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                    <form role="search" class="navbar-form-custom" method="post" action="search_results.html">
                        <div class="form-group">
                           <input style="width: 860px;" type="text" value="温馨提示：47.112.26.69的库是部署在Linux系统中，请Windows用户勿在本地调用该库。可自己在本地执行doc/mysql/product.sql脚本" class="form-control text-danger" name="top-search" id="top-search">
                        </div>
                    </form>
                </div>
            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="/main">首页</a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a>
                    </li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                    </li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                    </li>
                </ul>
            </div>
            <a href="/sys/login/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="/main" frameborder="0" data-id="/main" seamless></iframe>
        </div>
        <div class="footer">
            <div class="pull-right">&copy; 2018 <a href="https://gitee.com/qinjianping/" target="_blank">@Apple_QJP</a>
            </div>
        </div>
    </div>
</div>
<script src="/static/hPlus/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="/static/hPlus/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="/static/hPlus/js/hplus.min.js?v=4.1.0"></script>
<script type="text/javascript" src="/static/hPlus/js/contabs.min.js"></script>
<script src="/static/hPlus/js/plugins/pace/pace.min.js"></script>
<script>
    $(function () {
        onloadMenuTree();
    })
    
    function onloadMenuTree() {
        $('#side-menu').empty();
        var head='<li class="nav-header">\n' +
                '                    <div class="dropdown profile-element">\n' +
                '                        <span><img alt="image" style="width:64px;height:64px;" class="img-circle" src="<#if user.avatar??>/myfile/${user.avatar}<#else>/static/hPlus/img/profile_small.jpg</#if>"  /></span>\n' +
                '                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">\n' +
                '                                <span class="clear">\n' +
                '                                <span class="block m-t-xs"><strong class="font-bold">${user.username?if_exists}</strong></span>\n' +
                '                                    <span class="text-muted text-xs block">${user.roles[0].roleName?if_exists}<b class="caret"></b></span>\n' +
                '                                </span>\n' +
                '                        </a>\n' +
                '                        <ul class="dropdown-menu animated fadeInRight m-t-xs">\n' +
                '                            <li><a class="J_menuItem" onclick="avatar()">修改头像</a>\n' +
                '                            </li>\n' +
                '                            <li><a class="J_menuItem" onclick="addForm()">个人资料</a>\n' +
                '                            </li>\n' +
                '                            <li><a class="J_menuItem" onclick="modifyPWD()">修改密码</a>\n' +
                '                            </li>\n' +
                '                            <li class="divider"></li>\n' +
                '                            <li><a href="/">安全退出</a>\n' +
                '                            </li>\n' +
                '                        </ul>\n' +
                '                    </div>\n' +
                '                    <div class="logo-element">@Apple\n' +
                '                    </div>\n' +
                '                </li>';
        $('#side-menu').append(head);

        $.ajax({
            type: "post",
            url: "/sys/menu/treeListPermission",
            dataType: "json",
            success: function(data){
                if(data && data.length>0){
                    var str='';
                    for (var i=0;i<data.length;i++){
                        var menu=data[i];
                        var cla=(i==0)?'class="active"':"";
                        var li='<li '+cla+' onclick="toggleShow(this)">';
                        var url=menu.url?menu.url:'#';
                        var op='';
                        var arrow='';
                        if(url && url!='#'){
                            op='onclick="page(\''+url+'\',\''+menu.menuName+'\',event)"';
                        }else{
                            arrow='<span class="fa arrow"></span>\n';
                            op=' href="'+url+'">';
                        }
                        var a='<a data-index=\''+i+'\' '+ op +
                                '<i class="'+menu.icon+'"></i>\n' +
                                '<span class="nav-label">'+menu.menuName+'</span>\n' + arrow +
                                '</a>';
                        li+=a;
                        var cList=menu.children;
                        var inclass=(i==0)?"in":"";
                        if(menu.children && cList.length>0){
                            var ul='<ul class="nav nav-second-level collapse '+inclass+'" aria-expanded="true">';
                            for (var j=0;j<cList.length;j++){

                                var c=cList[j];
                                var cUrl=c.url?c.url:'#';
                                var cLi=' <li>\n' +
                                        '    <a class="J_menuItem" onclick="page(\''+cUrl+'\',\''+c.menuName+'\',event)" data-index='+j+'><i class="'+c.icon+'"></i>'+c.menuName+'</a>\n' +
                                        ' </li>';
                                ul+=cLi;
                            }
                            ul+='</ul>';
                            li+=ul;
                        }

                        li+='</li>';
                        str+=li;
                    }
                    $('#side-menu').append(str);
                    console.log(str);
                }
            }
        });

    }
    


    function page(url, title,e) {
        e.stopPropagation();
        var nav = $(window.parent.document).find('.J_menuTabs .page-tabs-content ');
        $(window.parent.document).find('.J_menuTabs .page-tabs-content ').find(".J_menuTab.active").removeClass("active");
        $(window.parent.document).find('.J_mainContent').find("iframe").css("display", "none");
        var iframe = '<iframe class="J_iframe" name="iframe10000" width="100%" height="100%" src="' + url + '" frameborder="0" data-id="' + url
                + '" seamless="" style="display: inline;"></iframe>';
        var dataIdF=$(window.parent.document).find('.J_mainContent').find('iframe[data-id="'+url+'"]');
        var dataIdA=$(window.parent.document).find('.J_menuTabs .page-tabs-content ').find('a[data-id="'+url+'"]');
        //$(dataIdF).remove();

        if(dataIdA.length>0 && dataIdF.length>0 ){
            $(window.parent.document).find('.J_menuTabs .page-tabs-content ').find('a').removeClass("active");
            $(dataIdA).addClass("active");
            $(dataIdF).remove();
        }else{
            $(window.parent.document).find('.J_menuTabs .page-tabs-content ').append(
                    ' <a href="javascript:;" class="J_menuTab active" data-id="'+url+'">' + title + ' <i class="fa fa-times-circle"></i></a>');
        }
        $(window.parent.document).find('.J_mainContent').append(iframe);

    }
    
    function toggleShow(obj) {
        var bool=$(obj).hasClass("active");
        if(bool){
            $(obj).removeClass("active");
            $(obj).children("ul").removeClass("in");
        }else{
            $(obj).addClass("active");
            $(obj).siblings("li").removeClass("active");
            $(obj).siblings("li").children("ul").removeClass("in");
            $(obj).children("ul").addClass("in");
        }
    }

    var $passwordForm;
    function modifyPWD() {
        layer.open({
            type: 2,
            title: "修改密码",
            area: ['520px', '360px'],
            maxmin: true,
            shade: 0.8,
            closeBtn: 1,
            shadeClose: true,
            content: '/modifyPassword',
            btn: ['保存', '关闭'],
            yes: function(index){
                //var params=$('#passwordMD').serialize();

                var confirmpwd=$passwordForm.find('#confirmpwd').val();
                var password=$passwordForm.find('#password').val();
                var oldpwd=$passwordForm.find('#oldpwd').val();
                if(!oldpwd){
                    layer.alert("旧密码不能为空！");
                    return;
                }
                if(!password){
                    layer.alert("新密码不能为空！");
                    return;
                }
                if(password!=confirmpwd){
                    layer.alert("密码输入不一致！");
                    return;
                }

                $.ajax({
                    type : "POST", //提交方式
                    url : "/sys/login/modifyPWD",//路径
                    data : {
                        oldpwd:oldpwd,
                        password:password
                    },//数据，这里使用的是Json格式进行传输
                    success : function(result) {//返回数据根据结果进行相应的处理
                        if(result.resultStat=="SUCCESS"){
                            layer.msg('保存成功！', {
                                time: 200 //2s后自动关闭
                            });
                            layer.close(index);
                        }else{
                            layer.alert("密码修改失败，"+result.mess);
                        }
                    }
                });
            },
            success: function(layero, index) {
                var body = layer.getChildFrame('body', index);
                $passwordForm = $(body).find("#passwordMD");
            }
        });
    }

    var $form;
    function addForm() {
        layer.open({
            type: 2,
            title: "个人信息",
            area: ['880px', '500px'],
            maxmin: true,
            shade: 0.8,
            closeBtn: 1,
            shadeClose: true,
            content: '/sys/user/form',
            btn: ['保存', '关闭'],
            yes: function(index){
                var params=$form.serialize();
                // 异步保存
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    cache: false,
                    url: "/sys/user/saveOrUpdate",
                    data: params,
                    success: function (result) {
                        if (result.resultStat=="SUCCESS") {
                            layer.msg('保存成功！', {
                                time: 200 //2s后自动关闭
                            });
                            layer.close(index);
                        } else {
                            layer.alert("保存失败，"+result.mess);
                        }
                    },
                    error: function () {
                        layer.alert("保存失败，"+result.mess);
                    }
                });
            },
            success: function(layero, index){
                var body=layer.getChildFrame('body',index);
                $form=$(body).find("#user_form");
                //$form.find("input").prop("readonly",true);
                $form.find("#closeBtn,#saveUserBtn").addClass("hide");
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    cache: false,
                    url: "/sys/user/view/"+${user.id},
                    success: function (result) {
                        if(result){
                            for (i in result){
                                $form.find("input[name=\'"+i+"\']").not("input:radio").val(result[i]);
                            }
                            $form.find("input[name='status'][value=\'"+result.status+"\']").prop("checked",true);
                            $form.find("input[name='sex'][value=\'"+result.sex+"\']").prop("checked",true);
                            $form.find('select[name="constellation"]').find("option[value=\'"+result.constellation+"\']").prop("selected",true);
                        }
                    },
                    error: function () {
                        layer.alert("数据加载失败，"+result.mess);
                    }
                });
            }

        });
    }

    var $avatarForm;
    function avatar() {
        layer.open({
            type: 2,
            title: "上传头像",
            area: ['940px', '650px'],
            maxmin: true,
            shade: 0.8,
            closeBtn: 1,
            shadeClose: true,
            content: '/avatar',
            btn: ['保存', '关闭'],
            yes: function(index){
                var img=$avatarForm.find('#finalImg');
                var va=$(img).prop("src");
                console.log(va);
                $.ajax({
                    type : "POST", //提交方式
                    url : "/myfile/uploadByBase64",//路径
                    data : {
                        img:va
                    },
                    success : function(result) {//返回数据根据结果进行相应的处理
                        window.location.reload();
                    }
                });
            },
            success: function(layero, index) {
                var body = layer.getChildFrame('body', index);
                $avatarForm = $(body).find("#imgForm");
            }
        });
    }
</script>
</body>

</html>
