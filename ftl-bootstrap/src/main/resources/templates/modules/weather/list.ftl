<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <script type="text/javascript" src="http://libs.baidu.com/jquery/1.7.2/jquery.min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=hTbz2yG9qhphm2yR6xBABXow"></script>
     <#include "../../common/base.ftl"/>
    <title>浏览器定位</title>

</head>
<body>
<div class="container">
    <div class="row text-success">
        <h2 id="load_geolocation" style="margin-top: 200px;">点击获取位置</h2>
    </div>
    <div class="row" id="weatherList">

    </div>
</div>
</body>

<script>
    $(function(){
        //$("#load_geolocation").onloadstart(function(ev){
          $('#load_geolocation').text("正在获取位置......");
            //创建百度地图控件
            var geolocation = new BMap.Geolocation();
            geolocation.getCurrentPosition(function(r){
                var adds =r.point;
                var latitude = adds.lat, longitude  = adds.lng;
                if(this.getStatus() == BMAP_STATUS_SUCCESS){
                    $.ajax({
                        url:'http://api.map.baidu.com/geocoder/v2/?ak=hTbz2yG9qhphm2yR6xBABXow&location=' + latitude + ',' + longitude + '&output=json&pois=1',
                        dataType: 'jsonp',
                        callback: 'BMap._rd._cbk43398',
                        success: function(res) {
                            console.log(res);
                            var result = res.result,
                                    addressComponent = result.addressComponent,
                                    adcode = addressComponent.adcode,
                                    city=addressComponent.city;
                            $('#load_geolocation').text(city)
                            getWeatherByCityName(city);
                           // $(ev.currentTarget).text('城市号码为'+ adcode);
                        }
                    });

                }
                else {
                   // $(ev.currentTarget).text('定位失败');
                }
            },{enableHighAccuracy: true})//指示浏览器获取高精度的位置，默认false
       // });
    });
    
    function getWeatherByCityName(cityName) {
        $.ajax({
            type : "GET", //提交方式
            url : "/weather/cityName/"+cityName,//路径
            async:false,
            success : function(result) {//返回数据根据结果进行相应的处理
                $('#weatherList').empty();
               if(result && result.desc=="OK"){
                   var json=result.data;
                   var weather=json.forecast;
                   if(weather && weather.length>0){
                       for (var i=0;i<weather.length;i++){
                           var entity=weather[i];
                           var div='<div class="col-sm-2" style="border-top:8px solid #ccc;border-bottom:8px solid #ccc;height:200px;width:200px;padding: 26px;margin-top: 50px;text-align: center;border-radius: 28px">\n' +
                                   '<p class="text-info">'+entity.date+'</p>' +
                                   '<p class="text-danger">'+entity.high+'</p>' +
                                   '<p>'+entity.fengli+'</p>' +
                                   '<p  class="text-danger">'+entity.low+'</p>' +
                                   '<p  class="text-success">'+entity.fengxiang+'</p>' +
                                   '<p  class="text-primary">'+entity.type+'</p>' +
                                   '                                   </div>'
                           $('#weatherList').append(div);
                       }
                   }
               }
            }
        });
    }



</script>
</html>