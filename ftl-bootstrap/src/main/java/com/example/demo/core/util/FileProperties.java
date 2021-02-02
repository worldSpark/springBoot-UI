package com.example.demo.core.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.sun.jna.platform.win32.Kernel32Util.getTempPath;
import static org.aspectj.util.LangUtil.isEmpty;

/**
 *
 */
@Component
@ConfigurationProperties(prefix = FileProperties.PREFIX)
public class FileProperties {

    public static final String PREFIX = "avatar";
    @Value("${avatar.fileUploadPath}")
    private String fileUploadPath;
    private Boolean haveCreatePath = false;


    public String getFileUploadPath() {
        //如果没有写文件上传路径,保存到临时目录
        if (isEmpty(fileUploadPath)) {
            return getTempPath();
        } else {
            //判断有没有结尾符,没有得加上
            if (!fileUploadPath.endsWith(File.separator)) {
                fileUploadPath = fileUploadPath + File.separator;
            }
            //判断目录存不存在,不存在得加上
            if (haveCreatePath == false) {
                File file = new File(fileUploadPath);
                file.mkdirs();
                haveCreatePath = true;
            }
            return fileUploadPath;
        }
    }

    /**
     * 获取临时目录
     *
     */
    public static String getTempPath(){
        return System.getProperty("java.io.tmpdir");
    }

}
