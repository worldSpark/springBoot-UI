package com.fc.common.domain;

import com.fc.util.GlobalFunc;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * @Description 通用文件上传配置对象
 * 1、下载文件如果是多个，则会将文件复制到temp文件夹，打包成zip再下载
 * @author luoy
 * @Date 2019年8月10日13:40:34
 * @version 1.0.0
 * */
@Data
public class CommonFileUploadConfig {

    // 最小文件数量，默认1
    private int minFileCount;
    // 最大文件数量，默认1，最大支持20
    private int maxFileCount;
    // 单文件最大容量（KB），默认0，<=0表示不限制，最大1GB
    private int maxSingleFileSize;
    // 所有文件最大容量（KB），默认0，<=0表示不限制，最大1GB
    private int maxMultipleFileSize;

    // 文件上传的文件夹，默认"/unknown"
    private String uploadPath;
    // 文件上传限制的类型，多个用","分隔，默认不限制，忽略大小写，但建议全小写
    private String exts;

    // 显示文件上传按钮，默认true
    private boolean showImport;
    // 显示关闭窗口按钮，默认true
    private boolean showClose;
    // 显示删除文件按钮，默认true
    private boolean showDelete;
    // 显示删除所有文件按钮，默认true
    private boolean showDeleteAll;
    // 显示下载单个文件按钮，默认true
    private boolean showDownload;
    // 显示下载所有文件按钮，默认true
    private boolean showDownloadAll;
    // 删除时同时删除文件，默认false
    private boolean deleteWithFile;
    // 上传完毕后自动关闭窗口，默认false
    private boolean closeAfterUpload;
    // 关闭前回调js函数，默认空，格式：xxx()
    // 注意：1、如果返回false，则会阻止窗口关闭，其它情况不会阻止窗口关闭
    //      2、注意如果是url打开，通过layer右上角关闭不会触发此方法，需要自己在layer.open事件中写方法，如end: function(){...}
    private String beforeCloseWindow;

    // minFileCount
    public void setMinFileCount(int minFileCount) {
        if(minFileCount <= 0){
            minFileCount = 1;
        }
        this.minFileCount = minFileCount;
    }
    public void setMinFileCount(String minFileCount) {
        if(StringUtils.isBlank(minFileCount)){
            minFileCount = "1";
        }
        int count = GlobalFunc.parseInt(minFileCount);
        this.setMinFileCount(count);
    }

    // maxFileCount
    public void setMaxFileCount(int maxFileCount) {
        if(maxFileCount <= 0){
            maxFileCount = 1;
        }
        if(maxFileCount > 20){
            maxFileCount = 20;
        }
        this.maxFileCount = maxFileCount;
    }
    public void setMaxFileCount(String maxFileCount) {
        if(StringUtils.isBlank(maxFileCount)){
            maxFileCount = "1";
        }
        int count = GlobalFunc.parseInt(maxFileCount);
        this.setMaxFileCount(count);
    }

    // maxSingleFileSize
    public void setMaxSingleFileSize(int maxSingleFileSize) {
        maxSingleFileSize = Math.min(maxSingleFileSize, 1024 * 1024);
        this.maxSingleFileSize = maxSingleFileSize;
    }
    public void setMaxSingleFileSize(String maxSingleFileSize) {
        if(StringUtils.isBlank(maxSingleFileSize)){
            maxSingleFileSize = "0";
        }
        int size = GlobalFunc.parseInt(maxSingleFileSize);
        this.setMaxSingleFileSize(size);
    }

    // maxMultipleFileSize
    public void setMaxMultipleFileSize(int maxMultipleFileSize) {
        maxMultipleFileSize = Math.min(maxMultipleFileSize, 1024 * 1024);
        this.maxMultipleFileSize = maxMultipleFileSize;
    }
    public void setMaxMultipleFileSize(String maxMultipleFileSize) {
        if(StringUtils.isBlank(maxMultipleFileSize)){
            maxMultipleFileSize = "0";
        }
        int size = GlobalFunc.parseInt(maxMultipleFileSize);
        this.setMaxMultipleFileSize(size);
    }

    // uploadPath
    public void setUploadPath(String uploadPath) {
        if(uploadPath == null || "".equals(uploadPath)){
            uploadPath = "/unknown";
        }
        if(uploadPath.endsWith("/") || uploadPath.endsWith("\\")){
            uploadPath = uploadPath.substring(0, uploadPath.length() - 1);
        }
        this.uploadPath = uploadPath;
    }

    // showImport
    public void setShowImport(boolean showImport) {
        this.showImport = showImport;
    }
    public void setShowImport(String showImport) {
        if(StringUtils.isBlank(showImport)){
            showImport = "true";
        }
        boolean b = "true".equals(showImport);
        this.setShowImport(b);
    }

    // showClose
    public void setShowClose(boolean showClose) {
        this.showClose = showClose;
    }
    public void setShowClose(String showClose) {
        if(StringUtils.isBlank(showClose)){
            showClose = "true";
        }
        boolean b = "true".equals(showClose);
        this.setShowClose(b);
    }

    // showDelete
    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
    }
    public void setShowDelete(String showDelete) {
        if(StringUtils.isBlank(showDelete)){
            showDelete = "true";
        }
        boolean b = "true".equals(showDelete);
        this.setShowDelete(b);
    }

    // showDeleteAll
    public void setShowDeleteAll(boolean showDeleteAll) {
        this.showDeleteAll = showDeleteAll;
    }
    public void setShowDeleteAll(String showDeleteAll) {
        if(StringUtils.isBlank(showDeleteAll)){
            showDeleteAll = "true";
        }
        boolean b = "true".equals(showDeleteAll);
        this.setShowDeleteAll(b);
    }

    // showDownload
    public void setShowDownload(boolean showDownload) {
        this.showDownload = showDownload;
    }
    public void setShowDownload(String showDownload) {
        if(StringUtils.isBlank(showDownload)){
            showDownload = "true";
        }
        boolean b = "true".equals(showDownload);
        this.setShowDownload(b);
    }

    // showDownloadAll
    public void setShowDownloadAll(boolean showDownloadAll) {
        this.showDownloadAll = showDownloadAll;
    }
    public void setShowDownloadAll(String showDownloadAll) {
        if(StringUtils.isBlank(showDownloadAll)){
            showDownloadAll = "true";
        }
        boolean b = "true".equals(showDownloadAll);
        this.setShowDownloadAll(b);
    }

    // deleteWithFile
    public void setDeleteWithFile(boolean deleteWithFile) {
        this.deleteWithFile = deleteWithFile;
    }
    public void setDeleteWithFile(String deleteWithFile) {
        if(StringUtils.isBlank(deleteWithFile)){
            deleteWithFile = "false";
        }
        boolean b = "true".equals(deleteWithFile);
        this.setDeleteWithFile(b);
    }

    // closeAfterUpload
    public void setCloseAfterUpload(boolean closeAfterUpload) {
        this.closeAfterUpload = closeAfterUpload;
    }
    public void setCloseAfterUpload(String closeAfterUpload) {
        if(StringUtils.isBlank(closeAfterUpload)){
            closeAfterUpload = "false";
        }
        boolean b = "true".equals(closeAfterUpload);
        this.setCloseAfterUpload(b);
    }

    // beforeCloseWindow
    public void set(String beforeCloseWindow) {
        this.beforeCloseWindow = beforeCloseWindow;
    }
}
