<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>基本表单</title>
    <#include "/common/base.ftl"/>
    <style type="text/css">
        .show{
            display: inline-block;
        }
        .hide{
            display: none;
        }
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form class="form-horizontal" id="book_form">
                        <div class="form-group" style="display: none">
                            <label class="col-sm-2 control-label">ID</label>
                            <div class="col-sm-10">
                                <input type="text" name="id" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-3">
                                <img id="img" src="/static/img/1.jpg" height="250" width="250"/>
                            </div>
                            <div class="col-sm-9">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">图书名称</label>
                                    <div class="col-sm-4">
                                        <input type="text" placeholder="请输入图书名称" name="bookName" class="form-control">
                                    </div>
                                    <label class="col-sm-2 control-label">作者信息</label>
                                    <div class="col-sm-4">
                                        <input type="text" placeholder="请输入作者信息" name="author" class="form-control">
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">出版社</label>
                                    <div class="col-sm-4">
                                        <input type="text" placeholder="请输入出版社" name="publish" class="form-control">
                                    </div>
                                    <label class="col-sm-2 control-label">出版日期</label>
                                    <div class="col-sm-4">
                                        <input type="text"  placeholder="请输入出版日期" name="publishTime" class="form-control">
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">图书价格(元)</label>
                                    <div class="col-sm-4">
                                        <input type="text" placeholder="请输入图书价格(元)" name="price" class="form-control">
                                    </div>
                                    <label class="col-sm-2 control-label">语种</label>
                                    <div class="col-sm-4">
                                        <input type="text" placeholder="请输入语种" name="languageClassification" class="form-control">
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">所属行业</label>
                                    <div class="col-sm-4">
                                        <input type="text" placeholder="请输入所属行业" name="industry" class="form-control">
                                    </div>
                                    <label class="col-sm-2 control-label">标准ISBN</label>
                                    <div class="col-sm-4">
                                        <input type="text" placeholder="请输入标准ISBN" name="isbn" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">内容简介</label>
                            <div class="col-sm-10">
                                <textarea type="text" placeholder="请输入内容简介" name="content" class="form-control" style="height: 200px"></textarea>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">目录内容</label>
                            <div class="col-sm-10">
                                <textarea type="text" placeholder="请输入目录内容" name="catalog" class="form-control" style="height: 200px"></textarea>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group text-right" style="width: 100%;position: relative">
                            <div class="col-sm-4 col-sm-offset-2" style="width: 100%;position: absolute;right: 0px;bottom: -30px;">
                                <button id="closeBtn" class="btn btn-white" onclick="closeLayer()" type="button">关闭</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    function closeLayer() {
        parent.colseLayer();
    }
</script>
<script src="/static/hPlus/js/plugins/iCheck/icheck.min.js"></script>
<script>
    $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
</script>
</body>

</html>
