<!DOCTYPE html>

<meta charset="utf-8">
<head>
     <#include "../../common/base.ftl"/>
</head>
<body class="gray-bg" id="test">
    <div class="wrapper wrapper-content">
        <div class="col-sm-12">
            <div class="row">
                <div class="col-sm-6">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5><i class="fa fa-creative-commons"> </i> CPU</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link"><i class="fa fa-chevron-up"></i>
                                </a>
                                <a class="close-link"><i class="fa fa-times"></i></a>
                            </div>
                        </div>
                        <div class="ibox-content">
                            <table class="table table-hover no-margins">
                                <thead>
                                    <tr>
                                        <th>属性</th>
                                        <th>值</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>核心数</td>
                                        <td >${server.cpu.cpuNum}</td>
                                    </tr>
                                    <tr>
                                        <td>用户使用率</td>
                                        <td >${server.cpu.used + '%'}</td>
                                    </tr>
                                    <tr>
                                        <td>系统使用率</td>
                                        <td >${server.cpu.sys + '%'}</td>
                                    </tr>
                                    <tr>
                                        <td>当前空闲率</td>
                                        <td >${server.cpu.free + '%'}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                
                <div class="col-sm-6">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5><i class="fa fa-pie-chart"> </i> 内存</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                <a class="close-link"><i class="fa fa-times"></i></a>
                            </div>
                        </div>
                        <div class="ibox-content">
                            <table class="table table-hover no-margins">
                                <thead>
                                    <tr>
                                        <th>属性</th>
                                        <th>内存</th>
                                        <th>JVM</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>总内存</td>
                                        <td >${server.mem.total + 'G'}</td>
                                        <td >${server.jvm.total + 'M'}</td>
                                    </tr>
                                    <tr>
                                        <td>已用内存</td>
                                        <td >${server.mem.used + 'G'}</td>
                                        <td >${server.jvm.used + 'M'}</td>
                                    </tr>
                                    <tr>
                                        <td>剩余内存</td>
                                        <td >${server.mem.free + 'G'}</td>
                                        <td >${server.jvm.free + 'M'}</td>
                                    </tr>
                                    <tr>
                                        <td>使用率</td>
                                        <td >${server.mem.usage}%</td>
                                        <td >${server.jvm.usage}%</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="row">
                <div class="col-sm-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5><i class="fa fa-desktop"> </i> 服务器信息</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                                <a class="close-link">
                                    <i class="fa fa-times"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content">
                            <div class="row">
                                <div class="col-sm-12">
                                    <table class="table table-hover margin bottom">
                                        <tbody>
                                            <tr>
                                                <td>服务器名称</td>
                                                <td >${server.sys.computerName}</td>
                                                <td>操作系统</td>
                                                <td >${server.sys.osName}</td>
                                            </tr>
                                            <tr>
                                                <td>服务器IP</td>
                                                <td >${server.sys.computerIp}</td>
                                                <td>系统架构</td>
                                                <td >${server.sys.osArch}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="row">
                <div class="col-sm-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5><i class="fa fa-github-alt"> </i> Java虚拟机信息</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                                <a class="close-link">
                                    <i class="fa fa-times"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content">

                            <div class="row">
                                <div class="col-sm-12">
                                    <table class="table table-hover margin bottom">
                                        <tbody>
                                            <tr>
                                                <td>Java名称</td>
                                                <td >${server.jvm.name}</td>
                                                <td>Java版本</td>
                                                <td >${server.jvm.version}</td>
                                            </tr>
                                            <tr>
                                                <td>启动时间</td>
                                                <td >${server.jvm.startTime}</td>
                                                <td>运行时长</td>
                                                <td >${server.jvm.runTime}</td>
                                            </tr>
                                            <tr>
                                                <td colspan="1">安装路径</td>
                                                <td colspan="3" >${server.jvm.home}</td>
                                            </tr>
                                             <tr>
                                                <td colspan="1">项目路径</td>
                                                <td colspan="3">${server.sys.userDir}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="row">
                <div class="col-sm-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5><i class="fa fa-ioxhost"> </i> 磁盘状态</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                                <a class="close-link">
                                    <i class="fa fa-times"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content">

                            <div class="row">
                                <div class="col-sm-12">
                                    <table class="table table-hover margin bottom">
                                        <thead>
                                            <tr>
                                                <th>盘符路径</th>
                                                <th>文件系统</th>
                                                <th>盘符类型</th>
                                                <th>总大小</th>
                                                <th>可用大小</th>
                                                <th>已用大小</th>
                                                <th>已用百分比</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <#list server.sysFiles as sysFile>
                                                <tr>
                                                    <td >${sysFile.dirName}</td>
                                                    <td >${sysFile.sysTypeName}</td>
                                                    <td >${sysFile.typeName}</td>
                                                    <td >${sysFile.total}</td>
                                                    <td >${sysFile.free}</td>
                                                    <td >${sysFile.used}</td>
                                                    <td >${sysFile.usage}%</td>
                                                </tr>
                                            </#list>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
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
</html>
