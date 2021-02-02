<!DOCTYPE html>

<meta charset="utf-8">
<head>
     <#include "../../common/base.ftl"/>
</head>
<body class="gray-bg" id="test">
<div class="wrapper wrapper-content">
    <div class="col-sm-12">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><i class="fa fa-creative-commons"> </i> Actuator监控信息</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link"><i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="close-link"><i class="fa fa-times"></i></a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal">
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">请求</label>
                                <div class="col-sm-5">
                                    <input type="text" value="https://localhost:8088/monitor/" name="interfaceGroup" class="form-control" disabled>
                                </div>
                                <div class="col-sm-3">
                                    <select class="form-control" id="monitor">
                                        <option value="health">health</option>
                                        <option value="info">info</option>
                                        <option value="metrics">metrics</option>
                                    </select>
                                </div>
                                <div class="col-sm-2">
                                    <button class="btn btn-info" onclick="sendMonitor()">发送请求</button>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">结果(JSON)</label>
                                <div class="col-sm-10" style="width: 68%;height: 200px;">
                                    <textarea type="text" rows="3" cols="20" placeholder="返回结果" id="resultJson" class="form-control">

                                    </textarea>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    $(".modal").appendTo("body"), $("[data-toggle=popover]").popover(), $(".collapse-link").click(function() {
        var div_ibox = $(this).closest("div.ibox"),
                e = $(this).find("i"),
                i = div_ibox.find("div.ibox-content");
        i.slideToggle(200),
                e.toggleClass("fa-chevron-up").toggleClass("fa-chevron-down"),
                div_ibox.toggleClass("").toggleClass("border-bottom"),
                setTimeout(function() {
                    div_ibox.resize();
                }, 50);
    }), $(".close-link").click(function () {
        var div_ibox = $(this).closest("div.ibox");
        div_ibox.remove()
    });
</script>
<script type="text/javascript">
    function sendMonitor() {debugger
        var op=$('#monitor').find("option:selected").val();
        window.open("http://localhost:8088/monitor/"+op,"_blank");
        /*$.ajax({
            type: "GET",
            dataType: "json",
            cache: false,
            url: "http://localhost:8088/monitor/"+op,
            success: function (result) {
                if(result){
                    $('#resultJson').val(JSON.parse(result));
                }
            },
            error: function (result) {
                layer.alert("获取信息失败!");
            }
        });*/
    }
</script>
</html>
