layui.use(["element", "layer"], function () {
    var $ = layui.jquery,
        element = layui.element,
        layer = layui.layer;
        $(function () {
        var l_o = $(".left-menu"),
            tab = "top-tab",
            l_m = "left-menu",
            t_m = "top-menu";
        var mainHeight = $(window).height() - 60;
        l_o.on("click", "li", function () {
            $(this).siblings().removeClass("layui-nav-itemed")
        });

        // 本地存储
        l_o.find("a[data-id]").click(function () {
            sessionStorage .dataId = $(this).attr('data-id')
        });

        $(".menu-flexible").click(function () {
            $(".left-menu-all").toggle();
            $(".layui-body,.layui-footer").css("left", ($(".left-menu-all").is(":hidden")) ? "0" : "200px")
        });

        // 判断是否有本地存储
        if (sessionStorage .getItem('dataId')) {
            var dataId = sessionStorage .getItem('dataId')
            var getNowId = l_o.find("a[data-id=" + dataId + "]")
            var getNowParent = $(getNowId).parent()

            $(getNowParent).addClass('layui-this')
            console.log($(getNowParent)[0]);
            // 判断是否需要展开
            if ($(getNowParent)[0].tagName == 'DD') {
                $(getNowId).parents('.layui-nav-item').addClass('layui-nav-itemed');
            }
        }
        // 目录
        $(document).on("click", ".mulu,.masked", function () {
            $('body').toggleClass('mulu-hide');
        });

        document.body.addEventListener('touchstart', function () { });

    });



    // 搜索
    $(document).on('click', '#search-btn', function () {
        $('.search-res-mask').show();
        $(this).parent().siblings('.search-fix').addClass('cur');

    })

    $(document).on('click', '#search-close', function () {
        $('.search-res-mask').hide();
        $(this).parents('.search-fix').removeClass('cur');

    })

    $(document).on('click', '.search-res-mask', function () {
        $('#search-close').trigger('click');
    })

    // 移动端下拉
    $(document).on('click', '.layui-table .layui-table-first', function () {
        if ($(window).width() > 992) {
            return;
        }
        if ($(this).hasClass('cur')) {
            $(this).siblings().hide();
            $(this).removeClass('cur');
        } else {
            $($(this).siblings()).css('display', '-webkit-box');
            $(this).addClass('cur');
        }
    })

    
});


var header = {
    template:`  
        <!-- 头部 -->
        <div class="layui-header">
            <div class="layui-main">
                <div class="top-left">
                    <!-- logo -->
                    <a href="javascript:;" class="mulu"><img src="/static/img/mulu.png" alt=""></a>
                    <a href="javascript:;" class="logo">商户管理平台</a>
                    <a href="javascript:;" class="menu-flexible ml10">
                        <i class="layui-icon">&#xe635;</i> 
                    </a>
                </div>
                <!-- 头部右侧操作 -->
                <ul class="layui-nav operate">
                    <li class="layui-nav-item">
                        <a href="javascript:;">操作</a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="/modifyPassword" class="edit-password">修改密码</a>
                            </dd>
                            <dd>
                                <a href="/toLogin">退出登录</a>
                            </dd>
                        </dl>
                    </li>
                </ul>
            </div>
        </div>
        `
};


var leftSlide =
    {
        template: `   
        <!-- 左侧菜单 -->
        <div class="layui-side layui-bg-black left-menu-all ">
            <div class="layui-side-scroll">
                <div class="info">
                    <div class="name">13800138000，欢迎您！</div>
                    <div class="oper box">
                        <a class="flex edit-password" href="/modifyPassword">修改密码</a>
                        <a class="flex" href="/toLogin">退出登录</a>
                    </div>
                </div>
                <ul class="layui-nav layui-nav-tree left-menu" lay-filter="left-menu">
                    <li class="layui-nav-item ">
                        <a href="javascript:location.reload();" data-id="1">后台首页</a>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;">商品管理</a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="/goods/toGoodsList" data-id="2">平台商品</a>
                            </dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;">订单管理</a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="待发货.html" data-id="4">待发货</a>
                            </dd>
                            <dd>
                                <a href="订单列表.html" data-id="5">订单列表</a>
                            </dd>
                            <dd>
                                <a href="退货订单.html" data-id="6">退货订单</a>
                            </dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;">运营管理</a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="轮播图.html" data-id="7">轮播图</a>
                            </dd>
                            <dd>
                                <a href="热卖/新品推荐.html" data-id="8">热卖/新品推荐</a>
                            </dd>
                            <dd>
                                <a href="首页分类推荐.html" data-id="9">首页分类推荐</a>
                            </dd>
                            <dd>
                                <a href="公告管理.html" data-id="10">公告管理</a>
                            </dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;">账号管理</a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="运营管理账号.html" data-id="11">运营管理账号</a>
                            </dd>
                            <dd>
                                <a href="会员账号.html" data-id="12">会员账号</a>
                            </dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;">财务管理</a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="财务统计.html" data-id="13">财务统计</a>
                            </dd>
                            <dd>
                                <a href="提现操作.html" data-id="14">提现管理</a>
                            </dd>
                            <dd>
                                <a href="分类统计.html" data-id="15">分类统计</a>
                            </dd>
                            <dd>
                                <a href="商品统计.html" data-id="16">商品统计</a>
                            </dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;">统计查询</a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="会员统计.html" data-id="17">会员统计</a>
                            </dd>
                            <dd>
                                <a href="品牌查询.html" data-id="18">品牌查询</a>
                            </dd>
                            <dd>
                                <a href="商品查询.html" data-id="19">商品查询</a>
                            </dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;">系统设置</a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="操作日志.html" data-id="20">操作日志</a>
                            </dd>
                            <dd>
                                <a href="商品类型管理.html" data-id="21">商品类型管理</a>
                            </dd>
                            <dd>
                                <a href="商品属性管理.html" data-id="22">商品属性管理</a>
                            </dd>
                            <dd>
                                <a href="品牌管理.html" data-id="23">品牌管理</a>
                            </dd>
                            <dd>
                                <a href="导航设计.html" data-id="24">导航设计</a>
                            </dd>
                            <dd>
                                <a href="系统参数设置.html" data-id="25">系统参数设置</a>
                            </dd>
                        </dl>
                    </li>
                </ul>
            </div>
        </div>
        `
    }

// 创建根实例
new Vue({
    el: '#header',
    components: {
        'header-component': header
    }

})

new Vue({
    el: '#leftSlide',
    components: {
        'leftslide-component': leftSlide
    }
})


