<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>文件上传</title>
    <link href="/static/hPlus/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/bootstrap-fileinput-master/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
    <script src="/static/js/jquery-1.11.1.min.js"></script>
    <script src="/static/bootstrap-fileinput-master/js/fileinput.min.js" type="text/javascript"></script>
    <#--<script src="https://cdn.bootcss.com/bootstrap-fileinput/4.3.5/js/locales/zh.min.js"></script>-->
    <script src="/static/bootstrap-fileinput-master/js/locales/zh.js"></script>
    <script src="/static/hPlus/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
<div class="container">
    <form enctype="multipart/form-data" id="uploadForm">
        <div class="form-group">
            <input id="upload_file" type="file" name="file" multiple=false  data-preview-file-type="any">
        </div>
    </form>
</div>
</body>
<script>
    $("#upload_file").fileinput({
        initialPreview: [],
        overwriteInitial: false,
        maxFileSize: 1000,
        maxFilesNum: 10,
        uploadUrl:"/myfile/upload", //上传的地址
        enctype:'multipart/form-data',
        uploadAsync:true
    });

    //异步上传错误结果处理
    $('#upload_file').on('fileerror', function(event, data, msg) {
        debugger
        console.log("data:"+data);
        console.log("msg:"+msg);
    });

    //异步上传成功结果处理
    $("#upload_file").on("fileuploaded", function (event, data, previewId, index) {
        debugger
        console.log("data:"+data);
        console.log("previewId:"+previewId);
        console.log("index:"+index);
    })

    //同步上传错误结果处理
    $('#uploadfile').on('filebatchuploaderror', function(event, data, msg) {
    });

   // 同步上传成功结果处理
    $('#uploadfile').on('filepreupload', function(event, data, previewId, index) {
    });
</script>
</html>