# springBoot-UI
根据以往的layer,bootstrap,easyui,vue,html等进行整合,将功能进行封装

代码都是通过大佬引用的模板进行添加的,非本人所写,所以要感谢世界给予的帮助,物与我皆无尽也

1.bootstrap分为beetl和freemarker等模板,layui用的是thymeleaf,通过thymeleaf进行封装
账号登录 admin/1

2.bootstrap和easyui模块参考了shiro的使用,中间件引用了redis.统一了curd等方法,弹窗样式用的是layer,
里面也会参考使用bootstrap和easyui等弹窗样式.动态添加点击效果.参考以前的流程模板,将流程引擎进行封装.

3.html模块中引用模板引擎freemarker和thymeleaf,引用地图的使用,echarts和ANV系列等图表.

4.html模块中将使用security的方法进行封装,沿用redis中间件,调用influxdb和opentsdb等时序库,引用消息队列
kafka和rabbitmq.其中用kafka进行存储信息,以吞吐量大的车票或者车辆轨迹等等进行操作,rabbitmq用来实现
直播的聊天室.

5.html模块中将实现直播功能,利用srs和rtc进行推流,rtc为直播主要信息,srs进行连麦等等.聊天室使用springboot
中stomp,引用封装好的webosocket进行通讯.使用分库分表,http请求协议,IO,多线程Netity等等

6.vue模块中将引用bootstrap和element等等UI进行简单的封装,地图最近兼容百度地图和高德地图的vue进行封装.
引用些其他的UI设计进行参考,完善一些项目上可以用到的功能.

7.vue模块的前端参考了ANTV和echarts的html样式,引用一些支持vue方法的功能,来实现基本的图表

8.vue-admin模块中,可以支持spring-cloud项目,这里没有调用.参考了security,nginx等等,引用Elasticsearch
来查看日志,调用查询等等,实现分词功能,分词中也可以用索引lucene功能,dockerfile进行打包

