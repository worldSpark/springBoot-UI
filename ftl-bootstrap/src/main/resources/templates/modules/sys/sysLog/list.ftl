<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>日志列表</title>
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
    <script src="/static/hPlus/js/plugins/layer/laydate/laydate.js"></script>
    <script>
        laydate({elem:"#hello",event:"focus"});var start={elem:"#start",format:"YYYY/MM/DD hh:mm:ss",min:laydate.now(),max:"2099-06-16 23:59:59",istime:true,istoday:false,choose:function(datas){end.min=datas;end.start=datas}};var end={elem:"#end",format:"YYYY/MM/DD hh:mm:ss",min:laydate.now(),max:"2099-06-16 23:59:59",istime:true,istoday:false,choose:function(datas){start.max=datas}};laydate(start);laydate(end);
    </script>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <!-- Panel Other -->
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5><i class="fa fa-list"></i> 日志列表</h5>
        </div>
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <div class="example-wrap">
                        <div class="example">
                            <div class="btn-group hidden-xs" id="toolbar" role="group">
                                <div class="col-sm-2">
                                    <button type="button" class="btn btn-outline btn-white" title="导出日志" onclick="exportLog()">
                                        <i class="glyphicon glyphicon-save" aria-hidden="true"> 导出</i>
                                    </button>
                                </div>
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <input id="logDate" class="form-control layer-date"  placeholder="日志时间" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                    </div>
                                </div>

                               <#-- <div class="input-group">-->
                                        <#--<label class="laydate-icon"></label>-->
                               <#-- </div>-->
                                <div class="col-sm-6 input-group">
                                    <input type="text" name="keyword" class="form-control" style="width:60%;margin-left: 4px;" placeholder="请输入查询内容"  onkeydown="onKeyDown(event)"/>
                                    <button type="button" onclick="querySearch()" title="查询" class="btn btn-outline btn-white">
                                        <i class="glyphicon glyphicon-search"></i>
                                    </button>
                                    <button title="清空" type="button" onclick="clearSearchListener()" class="btn btn-outline btn-white">
                                        <i class="fa fa-eraser"></i>
                                    </button>
                                </div>
                            </div>
                            <table id="log_list_table" data-height="670" data-mobile-responsive="true">
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- End Panel Other -->
</div>
<script type="text/javascript" src="/static/modules/sys/log/list.js"></script>
<script type="text/javascript">
    var logFormLayer;


    function colseLayer() {
        layer.close(logFormLayer);
    }

    function querySearch() {
        var keyword=$('input[name="keyword"]').val();
        var logDate=$('#logDate').val();
        var paramas={keyword:keyword,logDate:logDate};
        $.ajax({
            type: "post",
            url: "/sys/log",
            data: paramas,
            dataType:"json",
            success : function(json) {
                $("#log_list_table").bootstrapTable('load', json);//主要是要这种写法
            }
        });
    }
    
    function clearSearchListener() {
        $('input[name="keyword"]').val("");
        $('#logDate').val("");
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
    function exportLog() {
        var keyword=$('input[name="keyword"]').val();
        var logDate=$('#logDate').val();
        window.location.href="/sys/log/export?keyword="+keyword+"&logDate="+logDate;
        /*$.ajax({
            type: "post",
            url: "/sys/log/export",
            data: {keyword:keyword},
            dataType:"json",
            success : function(json) {
                if(json.state==1){
                    layer.msg('导出失败！'+json.message, {
                        time: 2000 //20s后自动关闭
                    });
                }else{
                    layer.msg('导出成功！', {
                        time: 2000 //20s后自动关闭
                    });
                }
            }
        });*/
    }

</script>
</body>

</html>
