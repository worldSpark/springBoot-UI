<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>接口管理</title>
    <#include "/common/base.ftl"/>
    <style>
        .btn-default{
            color: grey;
            background-color: transparent;
        }
        .btn-default:hover{
            color: #767676;
            background-color: #eeeeee;
        }
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <!-- Panel Other -->
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5><i class="fa fa-list"></i> 接口列表</h5>
        </div>
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <!-- Example Events -->
                    <div class="example-wrap">
                        <#--<h4 class="example-title">事件</h4>-->
                        <div class="example">
                            <#--<div class="alert alert-success" id="examplebtTableEventsResult" role="alert">
                                事件结果
                            </div>-->
                            <div class="btn-group hidden-xs" id="toolbar" role="group">
                                <button type="button" class="btn btn-outline btn-white" title="新增接口" onclick="addForm(null,'新增接口')">
                                    <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                                </button>
                                    <div class="input-group">
                                        <input type="text" name="keyword" class="form-control" style="width:60%;margin-left: 4px;" placeholder="请输入查询内容"  onkeydown="onKeyDown(event)"/>

                                        <button type="button" onclick="querySearch()" title="查询" class="btn btn-outline btn-white">
                                            <i class="glyphicon glyphicon-search"></i>
                                        </button>
                                        <button title="清空" type="button" onclick="clearSearchListener()" class="btn btn-outline btn-white">
                                            <i class="fa fa-eraser"></i>
                                        </button>
                                    </div>

                            </div>
                            <table id="interface_list_table" data-height="520" data-mobile-responsive="true">
                            </table>
                        </div>
                    </div>
                    <!-- End Example Events -->
                </div>
            </div>
        </div>
    </div>
    <!-- End Panel Other -->
</div>
<script type="text/javascript" src="/static/modules/data/interface/list.js"></script>
<script type="text/javascript">
    var interfaceFormLayer;

    function colseLayer() {
        layer.close(interfaceFormLayer);
    }

    function querySearch() {
        var keyword=$('input[name="keyword"]').val();
        var paramas={keyword:keyword};
        $.ajax({
            type: "get",
            url: "/data/interface/pages",
            data: paramas,
            dataType:"json",
            success : function(json) {
                $("#interface_list_table").bootstrapTable('load', json);//主要是要这种写法
            }
        });
    }

    function clearSearchListener() {
        $('input[name="keyword"]').val("");
        querySearch();
    }


    function onKeyDown(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==27){ // 按 Esc
            //要做的事情
        }
        if(e && e.keyCode==113){ // 按 F2
            //要做的事情
        }
        if(e && e.keyCode==13){ // enter 键
            querySearch();
        }
    }

</script>
</body>

</html>
