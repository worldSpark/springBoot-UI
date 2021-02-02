package com.example.demo.core.util;/*
package com.example.demo.core.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

*/
/**
 * Created by Niko on 2017/06/26.
 *//*

public class FileUtil implements Serializable {
    */
/**
     * 下载文件
     * @param response
     * @param file
     *//*

    public static void downloadFile(HttpServletResponse response , File file) {

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

*/
