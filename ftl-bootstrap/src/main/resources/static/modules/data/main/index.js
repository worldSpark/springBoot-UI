
$(function () {
    loadCalendar();
    loadHoliday();
    loadConstellation();
    loadWeather();
    jokeList(1,3);
    onloadNewspaper();
})


/**
 * 节假日列表
 */
function loadHoliday() {
    $('#holiday').empty();
    var today=new Date();
    $.ajax({
        async: false,
        type:'GET',
        url: "/calendar/year",
        success: function (json) {
            if(json && json.error_code==0){
                var data=json.result.data;
                if(data){
                    var list=data.holiday_list;
                    if(list && list.length>0){
                        for (var i=0;i<list.length;i++){
                            var holiday=list[i];
                            var state="进行中";
                            if(today<new Date(Date.parse(holiday.startday))){
                                state="未完成";
                            }else if(today>new Date(Date.parse(holiday.startday))){
                                state="已完成";
                            }
                            var tr='<tr>\n' +
                                '   <td><small>'+state+'</small></td>\n' +
                                '   <td><i class="fa fa-clock-o"></i> '+holiday.startday+'</td>\n' +
                                '   <td>'+holiday.name+'</td>\n' +
                                '</tr>';
                            $('#holiday').append(tr);
                        }
                    }
                }
            }
        }
    });
}


/**
 * 万年历
 */

function loadCalendar(){
    var $form=$('#todayCalendar');
    $.ajax({
        async: false,
        type:'GET',
        url: "/calendar/day",
        success: function (json) {
            if(json && json.error_code==0){
                var data=json.result.data;
                if(data){
                    for (i in data){
                        $form.find("#"+i).text(data[i]);
                    }
                }
                var val='🐶';
                if(data.animalsYear=="鼠"){
                    val='🐀';
                }else if(data.animalsYear=="牛"){
                    val='🐮';
                }else if(data.animalsYear=="虎"){
                    val='🐯';
                }else if(data.animalsYear=="兔"){
                    val='🐇';
                }else if(data.animalsYear=="龙"){
                    val='🐉';
                }else if(data.animalsYear=="蛇"){
                    val='🐍';
                }else if(data.animalsYear=="马"){
                    val='🐎';
                }else if(data.animalsYear=="羊"){
                    val='🐏';
                }else if(data.animalsYear=="猴"){
                    val='🐵';
                }else if(data.animalsYear=="鸡"){
                    val='🐔';
                }else if(data.animalsYear=="猪"){
                    val='🐖';
                }

                $form.find('#animalIcon').html(val);
            }
        }
    });
}


/**
 * 星座运势
 */
function loadConstellation(){
    var $form=$('#constellation');
    $.ajax({
        async: false,
        type: 'GET',
        url: "/constellation/getInfoByConstellation",
        success: function (json) {
            if (json && json.error_code == 0) {
                for (i in json){
                    $form.find("#"+i).text(json[i]);
                }
                var val="♐";
                if(json.name=="白羊座"){
                    val="♈";
                }else if(json.name=="金牛座"){
                    val="♉";
                }else if(json.name=="双子座"){
                    val="♊";
                }else if(json.name=="巨蟹座"){
                    val="♋";
                }else if(json.name=="狮子座"){
                    val="♌";
                }else if(json.name=="处女座"){
                    val="♍";
                }else if(json.name=="天秤座"){
                    val="♎";
                }else if(json.name=="天蝎座"){
                    val="♏";
                }else if(json.name=="摩羯座"){
                    val="♑";
                }else if(json.name=="水瓶座"){
                    val="♒";
                }else if(json.name=="双鱼座"){
                    val="♓";
                }
                $form.find("#nameIcon").html(val);
            }
        }
    });
}

/**
 * 天气
 */
function loadWeather() {
    var $form=$('#weather');
    $.ajax({
        async: false,
        type: 'GET',
        url: "/weather/getLocalWeather",
        success: function (json) {
            if (json && json.desc == "OK") {
                var data=json.data;
                $form.find("#city").text(data.city);
                $form.find("#wendu").text(data.wendu);
                var forecast=data.forecast;
                if(forecast && forecast.length>0){
                    var date=forecast[0];
                    $form.find("#fengxiang").text(date.fengxiang);
                    $form.find("#high").text(date.high);
                    $form.find("#low").text(date.low);
                    $form.find("#type").text(date.type);
                    var val="🌪";
                    if(date.type.indexOf("阴")!=-1){
                        val="🌫";
                    }else if(date.type.indexOf("冰")!=-1){
                        val="❄";
                    }else if(date.type.indexOf("霜")!=-1){
                        val="🌨";
                    }else if(date.type.indexOf("雷")!=-1){
                        val="🌩";
                    }else if(date.type.indexOf("云")!=-1){
                        val="⛅";
                    }else if(date.type.indexOf("晴")!=-1){
                        val="🌤";
                    }else if(date.type.indexOf("雨")!=-1){
                        val="⛈";
                    }
                    $form.find("#weatherIcon").html(val);

                    $form.attr("title",data.ganmao);
                }
            }
        }
    });
}


/**
 * 周公解梦
 *
 */
function searchDream() {
   var keyword= $('#keyword').val();
    $.ajax({
        async: true,
        type: 'POST',
        data:{keyword:keyword},
        url: "/dream/getDreamInfo",
        success: function (json) {
            if(json && json.error_code==0){
                var result=json.result;
                if(result.length>0){
                    $('#dreamContent').html(result[0].title+"："+result[0].des);
                }
            }
        }
    });
}

/**
 * 笑话
 */
function jokeList(page,pagesize) {
    $('#jokeList').empty();
    $.ajax({
        async: true,
        type: 'POST',
        data:{page:page,pagesize:pagesize},
        url: "/joke/getRecentJokeList",
        success: function (json) {
            if(json && json.error_code==0){
                var result=json.result.data;
                if(result && result.length>0){
                    for (var i=0;i<result.length;i++){
                        var joke=result[i];
                        var div='<div style="margin: 12px;line-height: 23px;">\n' +
                            '       <small class="pull-right text-navy">'+joke.updatetime+'</small>\n' +
                            '       <strong>笑话'+(i+1+(page-1)*pagesize)+'</strong>\n' +
                            '       <div>'+joke.content+'</div>\n' +
                            '   </div>';
                        $('#jokeList').append(div);
                    }
                }
            }

        }
    });
}

function nextPage() {
    var currentPage=$('#page').text();
    if(currentPage<20){
        jokeList(Number(currentPage)+1,3);
        $('#page').text(Number(currentPage)+1);
    }
}

function prePage() {
    var currentPage=$('#page').text();
    if(currentPage>1){
        jokeList(Number(currentPage)-1,3);
        $('#page').text(Number(currentPage)-1);
    }
}

/**
 * 每日新闻头条
 */
function onloadNewspaper() {
    $('#newspaper').find("ul").empty();
    $.ajax({
        async: true,
        type: 'GET',
        url: "/news/getNewsList",
        success: function (json) {
            if(json && json.error_code==0){
                var result=json.result.data;
                if(result.length>0){
                    for (var i=0; i<result.length;i++){
                        var ti=result[i].title;
                        if(ti.length>12){
                            ti=ti.substring(0,12)+"...";
                        }
                        var li='<li><a onclick="window.open(\''+result[i].url+'\')">'+ti+'</a><span style="float: right">'+result[i].date+'</span></li>';
                        $('#newspaper').find("ul").append(li);
                    }
                }
            }
        }
    });
}






