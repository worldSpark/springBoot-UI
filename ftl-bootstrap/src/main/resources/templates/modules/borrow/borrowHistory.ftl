<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>借阅记录详情</title>

    <link rel="shortcut icon" href="favicon.ico">
    <link href="/static/hPlus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/static/hPlus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="/static/hPlus/css/animate.min.css" rel="stylesheet">
    <link href="/static/hPlus/css/style.min.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
<div class="row">
    <div class="col-sm-12">
        <div class="wrapper wrapper-content">
            <div class="row animated fadeInRight">
                <div class="col-sm-12">
                    <div class="ibox float-e-margins">
                        <div class="text-center float-e-margins p-md">
                            <a href="#" class="btn btn-xs btn-primary" id="leftVersion">布局切换</a>
                        </div>
                        <div class="" id="ibox-content">
                            <div id="vertical-timeline" class="vertical-container light-timeline">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<script src="/static/hPlus/js/jquery.min.js?v=2.1.4"></script>
<script src="/static/hPlus/js/bootstrap.min.js?v=3.3.6"></script>
<script src="/static/hPlus/js/content.min.js?v=1.0.0"></script>
<script>
    $(document).ready(function(){$("#lightVersion").click(function(event){event.preventDefault();$("#ibox-content").removeClass("ibox-content");$("#vertical-timeline").removeClass("dark-timeline");$("#vertical-timeline").addClass("light-timeline")});$("#darkVersion").click(function(event){event.preventDefault();$("#ibox-content").addClass("ibox-content");$("#vertical-timeline").removeClass("light-timeline");$("#vertical-timeline").addClass("dark-timeline")});$("#leftVersion").click(function(event){event.preventDefault();$("#vertical-timeline").toggleClass("center-orientation")})});
</script>
</body>

</html>
