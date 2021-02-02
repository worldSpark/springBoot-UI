<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>分配用户</title>
    <#include "/common/base.ftl"/>
    <link href="/static/hPlus/css/plugins/jsTree/style.min.css" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-3">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5><i class="fa fa-list"></i> 部门列表</h5>
                </div>
                <div class="ibox-content">
                    <div id="using_json"></div>
                </div>
            </div>
        </div>
        <div class="col-sm-9" style="position: relative">
            <div class="ibox float-e-margins" style="position:absolute;left:10px;top:8px;width:46%;height: 420px;">
               <#-- <div class="ibox-title">
                    <h5>已分配用户</h5>
                </div>-->
                <div class="ibox-content" style="padding: 0px;">
                    <div class="row" style="height: 70px;margin: 0px;padding: 0px;">
                        <div style="background-color: #83a1b4;height: 70px;margin: 0px;padding: 4px;position: relative;color: white">
                            <h2 style="font-weight: 400" id="masterName">暂无部长</h2>
                            <p id="orgName"></p>
                            <i class="fa fa-user" style="position: absolute;top: 8px;left: 43%;font-size: 4em;color: #676a6c;" id="sex"></i>
                            <span id="totalPer" style="position: absolute;right: 6px;bottom: 0px;"></span>
                        </div>
                        <hr>
                    </div>
                    <div class="row row-lg" style="margin: 8px;">
                        <div class="col-sm-12">
                            <!-- Example Events -->
                            <div class="example-wrap">
                                <div class="example">
                                    <div style="height: 300px;">
                                        <table  id="allot_list_table"></table>
                                    </div>
                                </div>
                            </div>
                            <!-- End Example Events -->
                        </div>
                    </div>
                </div>
            </div>
            <div class="ibox float-e-margins" style="position:absolute;right:10px;top:8px;width:50%;height: 420px;">
                <div class="ibox-title">
                    <h5>可加入该部门的用户</h5>
                </div>
                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <!-- Example Events -->
                            <div class="example-wrap">
                                <div class="example">
                                    <div style="height: 300px;">
                                        <table  id="unAllot_list_table"></table>
                                    </div>
                                </div>
                            </div>
                            <!-- End Example Events -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#--<script src="js/jquery.min.js?v=2.1.4"></script>
<script src="js/bootstrap.min.js?v=3.3.6"></script>
<script src="js/content.min.js?v=1.0.0"></script>-->
<script src="/static/hPlus/js/plugins/jsTree/jstree.min.js"></script>
<style>
    .jstree-open>.jstree-anchor>.fa-folder:before{content:"\f07c"}.jstree-default .jstree-icon.none{width:0}
</style>
<script src="/static/modules/sys/dept/allot.js"></script>
</body>

</html>
