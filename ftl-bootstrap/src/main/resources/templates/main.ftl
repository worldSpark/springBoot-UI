<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>首页</title>
    <link rel="shortcut icon" href="favicon.ico">
    <link href="/static/hPlus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/static/hPlus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <!-- Morris -->
    <link href="/static/hPlus/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
    <!-- Gritter -->
    <link href="/static/hPlus/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
    <link href="/static/hPlus/css/animate.min.css" rel="stylesheet">
    <link href="/static/hPlus/css/style.min.css?v=4.1.0" rel="stylesheet">
    <style>
        li{ list-style-type: none;height: 33px;}
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-4">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <span class="label label-danger pull-right">今日</span>
                    <h5><i class="fa fa-bolt"> </i> 天气</h5>
                </div>
                <div class="ibox-content" style="height: 270px;" id="weather">
                    <div class="stat-percent font-bold text-danger" style="font-size: 16px;font-family: 楷体;"><span id="city">广州</span><span id="wendu">14</span>℃
                    </div>
                    <h1 class="no-margins" style="font-weight: 600;padding: 4px;" id="type">多云</h1>
                    <span style="font-size: 18px;margin-right: 3px;" id="fengxiang">东北风</span>
                    <span style="font-size: 18px;margin-right: 3px;"  class="text-danger" id="high">低温 13℃</span>
                    <span style="font-size: 18px;margin-right: 3px;"  class="text-danger" id="low">高温 19℃</span>
                    <div style="font-size: 120px;text-align: center">
                        <span id="weatherIcon">🌦</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <span class="label label-success pull-right">今日</span>
                    <h5><i class="fa fa-calendar"> </i> 万年历</h5>
                </div>
                <div class="ibox-content" style="height: 270px;" id="todayCalendar">
                    <div class="stat-percent font-bold text-success"><span id="weekday"></span> <i class="fa fa-calendar-o"> </i>
                        <p style="font-size: 20px;margin-top: 4px;" class="text-success" id="date"></p>
                    </div>
                    <h1 class="no-margins"><span id="animalIcon"></span> <span class="text-danger" style="font-weight: bolder;font-size: 50px;" id="animalsYear">狗</span>年</h1>
                    <p></p>
                    <p><span id="lunarYear"></span> <span style="color: #0a6aa1;font-weight: bolder;" id="lunar"></span></p>

                    <p><label><span class="text-info" style="font-size: 30px;margin-right: 4px;">宜</span><small id="suit"></small></label></p>
                    <p><label><span class="text-danger" style="font-size: 30px;margin-right: 4px;">忌</span><small id="avoid"></small></label></p>

                </div>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <span class="label label-info pull-right">今日</span>
                    <h5><i class="fa fa-star"> </i> 星座运势</h5>
                </div>
                <div class="ibox-content" style="height: 270px;" id="constellation">
                    <div class="stat-percent font-bold text-info" style="font-size: 34px;"><span id="name"></span> <span id="nameIcon"></span>
                    </div>
                    <p><label class="label label-success" style="font-size: 20px;">幸运颜色</label><span class="text-success" style="margin-left: 4px;font-size: 18px;" id="color"></span></p>
                    <p><label class="label label-success" style="font-size: 20px;">幸运数字</label><span class="text-success" style="margin-left: 4px;font-size: 18px;" id="number"></span></p>
                    <p><label class="label label-success" style="font-size: 20px;">契合星座</label><span class="text-success" style="margin-left: 4px;font-size: 18px;" id="QFriend"></span></p>
                    <p><small id="summary"></small></p>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5><i class="glyphicon glyphicon-king"> </i> 大笑园</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content" style="height: 370px;overflow-y: scroll">
                    <div class="feed-activity-list">
                        <div class="feed-element" id="jokeList">

                        </div>
                        <div>
                            <span class="pull-left">第<span id="page">1</span>页</span>
                            <#--<button  class="pull-right btn btn-sm btn-white" onclick="nextPage()">下一页</button>
                            <button  class="pull-right btn btn-sm btn-white" onclick="prePage()">上一页</button>-->
                            <a  class="pull-right btn btn-sm btn-white" onclick="nextPage()" title="下一页"><i class="glyphicon glyphicon-forward"></i></a>
                            <a  class="pull-right btn btn-sm btn-white" onclick="prePage()" title="上一页"><i class="glyphicon glyphicon-backward"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-sm-6">

            <div class="row">
                <div class="col-sm-6">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5><i class="glyphicon glyphicon-tasks"> </i> 节假日列表</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                                <a class="close-link">
                                    <i class="fa fa-times"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content" style="height: 370px;" >
                            <table class="table table-hover no-margins">
                                <thead>
                                <tr>
                                    <th>状态</th>
                                    <th>日期</th>
                                    <th>节日</th>
                                </tr>
                                </thead>
                                <tbody id="holiday">

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5><i class="glyphicon glyphicon-question-sign"> </i> 今日新闻</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                                <a class="close-link">
                                    <i class="fa fa-times"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content" style="height: 370px;overflow:hidden;line-height:22px;" id="newspaper">
                            <ul>

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/static/hPlus/js/jquery.min.js?v=2.1.4"></script>
<script src="/static/hPlus/js/bootstrap.min.js?v=3.3.6"></script>
<script src="/static/hPlus/js/plugins/flot/jquery.flot.js"></script>
<script src="/static/hPlus/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
<script src="/static/hPlus/js/plugins/flot/jquery.flot.spline.js"></script>
<script src="/static/hPlus/js/plugins/flot/jquery.flot.resize.js"></script>
<script src="/static/hPlus/js/plugins/flot/jquery.flot.pie.js"></script>
<script src="/static/hPlus/js/plugins/flot/jquery.flot.symbol.js"></script>
<script src="/static/hPlus/js/plugins/peity/jquery.peity.min.js"></script>
<script src="/static/hPlus/js/demo/peity-demo.min.js"></script>
<script src="/static/hPlus/js/content.min.js?v=1.0.0"></script>
<script src="/static/hPlus/js/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="/static/hPlus/js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
<script src="/static/hPlus/js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
<script src="/static/hPlus/js/plugins/easypiechart/jquery.easypiechart.js"></script>
<script src="/static/hPlus/js/plugins/sparkline/jquery.sparkline.min.js"></script>
<script src="/static/hPlus/js/demo/sparkline-demo.min.js"></script>
<script src="/static/js/dateFormat.js"></script>
<script src="/static/modules/data/main/index.js"></script>
<script>
    $(document).ready(function(){$(".chart").easyPieChart({barColor:"#f8ac59",scaleLength:5,lineWidth:4,size:80});$(".chart2").easyPieChart({barColor:"#1c84c6",scaleLength:5,lineWidth:4,size:80});var data2=[[gd(2012,1,1),7],[gd(2012,1,2),6],[gd(2012,1,3),4],[gd(2012,1,4),8],[gd(2012,1,5),9],[gd(2012,1,6),7],[gd(2012,1,7),5],[gd(2012,1,8),4],[gd(2012,1,9),7],[gd(2012,1,10),8],[gd(2012,1,11),9],[gd(2012,1,12),6],[gd(2012,1,13),4],[gd(2012,1,14),5],[gd(2012,1,15),11],[gd(2012,1,16),8],[gd(2012,1,17),8],[gd(2012,1,18),11],[gd(2012,1,19),11],[gd(2012,1,20),6],[gd(2012,1,21),6],[gd(2012,1,22),8],[gd(2012,1,23),11],[gd(2012,1,24),13],[gd(2012,1,25),7],[gd(2012,1,26),9],[gd(2012,1,27),9],[gd(2012,1,28),8],[gd(2012,1,29),5],[gd(2012,1,30),8],[gd(2012,1,31),25]];var data3=[[gd(2012,1,1),800],[gd(2012,1,2),500],[gd(2012,1,3),600],[gd(2012,1,4),700],[gd(2012,1,5),500],[gd(2012,1,6),456],[gd(2012,1,7),800],[gd(2012,1,8),589],[gd(2012,1,9),467],[gd(2012,1,10),876],[gd(2012,1,11),689],[gd(2012,1,12),700],[gd(2012,1,13),500],[gd(2012,1,14),600],[gd(2012,1,15),700],[gd(2012,1,16),786],[gd(2012,1,17),345],[gd(2012,1,18),888],[gd(2012,1,19),888],[gd(2012,1,20),888],[gd(2012,1,21),987],[gd(2012,1,22),444],[gd(2012,1,23),999],[gd(2012,1,24),567],[gd(2012,1,25),786],[gd(2012,1,26),666],[gd(2012,1,27),888],[gd(2012,1,28),900],[gd(2012,1,29),178],[gd(2012,1,30),555],[gd(2012,1,31),993]];var dataset=[{label:"订单数",data:data3,color:"#1ab394",bars:{show:true,align:"center",barWidth:24*60*60*600,lineWidth:0}},{label:"付款数",data:data2,yaxis:2,color:"#464f88",lines:{lineWidth:1,show:true,fill:true,fillColor:{colors:[{opacity:0.2},{opacity:0.2}]}},splines:{show:false,tension:0.6,lineWidth:1,fill:0.1},}];var options={xaxis:{mode:"time",tickSize:[3,"day"],tickLength:0,axisLabel:"Date",axisLabelUseCanvas:true,axisLabelFontSizePixels:12,axisLabelFontFamily:"Arial",axisLabelPadding:10,color:"#838383"},yaxes:[{position:"left",max:1070,color:"#838383",axisLabelUseCanvas:true,axisLabelFontSizePixels:12,axisLabelFontFamily:"Arial",axisLabelPadding:3},{position:"right",clolor:"#838383",axisLabelUseCanvas:true,axisLabelFontSizePixels:12,axisLabelFontFamily:" Arial",axisLabelPadding:67}],legend:{noColumns:1,labelBoxBorderColor:"#000000",position:"nw"},grid:{hoverable:false,borderWidth:0,color:"#838383"}};function gd(year,month,day){return new Date(year,month-1,day).getTime()}var previousPoint=null,previousLabel=null;$.plot($("#flot-dashboard-chart"),dataset,options);var mapData={"US":298,"SA":200,"DE":220,"FR":540,"CN":120,"AU":760,"BR":550,"IN":200,"GB":120,};$("#world-map").vectorMap({map:"world_mill_en",backgroundColor:"transparent",regionStyle:{initial:{fill:"#e4e4e4","fill-opacity":0.9,stroke:"none","stroke-width":0,"stroke-opacity":0}},series:{regions:[{values:mapData,scale:["#1ab394","#22d6b1"],normalizeFunction:"polynomial"}]},})});
    var speed = 100;
    var delay = 2000;
    var height = 21;
    var time;
    window.onload = function(){
        var area = document.getElementById("newspaper");
        area.innerHTML += area.innerHTML;
        function scroll(){
            if(area.scrollTop % height == 0){
                clearInterval(time);
                setTimeout(start,delay);
            }else{
                area.scrollTop++;
                if(area.scrollTop >= area.scrollHeight/2){
                    area.scrollTop = 0;
                }
            }
        }
        function start(){
            time = setInterval(scroll,speed);
            area.scrollTop++;
        }
        setTimeout(start,delay);
    }
</script>

</body>

</html>
