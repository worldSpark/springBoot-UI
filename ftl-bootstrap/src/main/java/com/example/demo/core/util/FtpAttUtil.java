

package com.example.demo.core.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpAttUtil {
    private static Logger logger = LoggerFactory.getLogger(FtpAttUtil.class);
    //本地字符编码
    static String LOCAL_CHARSET = "GBK";

    // FTP协议里面，规定文件名编码为iso-8859-1
    static String SERVER_CHARSET = "ISO-8859-1";

    public FtpAttUtil() {
    }

    public static FTPClient getFTPClient(String ftpHost, int ftpPort, String ftpUserName, String ftppassWord) throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.connect(ftpHost, ftpPort);
        ftpClient.login(ftpUserName, ftppassWord);
        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            System.out.println("未连接到FTP，用户名或密码错误。");
            ftpClient.disconnect();
        } else {
            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
                LOCAL_CHARSET = "UTF-8";
            }
            ftpClient.setControlEncoding(LOCAL_CHARSET);
            System.out.println("FTP连接成功。");
        }

        return ftpClient;
    }

    public static void disconnectFTPClient(FTPClient ftpClient) throws IOException {
        if (ftpClient != null && ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
        }

    }

    public static boolean uploadFile(FTPClient ftpClient, String basePath, String filePath, String fileName, InputStream inputStream) throws Exception {
        BufferedInputStream input = null;

        boolean var7;
        try {
            boolean result = false;
            if (!StringUtils.isNotBlank(basePath) || ftpClient.changeWorkingDirectory(basePath) || ftpClient.makeDirectory(basePath)) {
                String[] dirs = filePath.split("/");
                String[] var8 = dirs;
                int var9 = dirs.length;

                for(int var10 = 0; var10 < var9; ++var10) {
                    String dir = var8[var10];
                    if (!StringUtils.isBlank(dir) && !ftpClient.changeWorkingDirectory(dir)) {
                        if (!ftpClient.makeDirectory(new String(dir.getBytes("utf-8"),"iso-8859-1"))) {
                            boolean var12 = result;
                            return var12;
                        }

                        ftpClient.changeWorkingDirectory(new String(dir.getBytes("utf-8"),"iso-8859-1"));
                    }
                }

                ftpClient.setControlEncoding("UTF-8");
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
                ftpClient.setFileType(2);
                ftpClient.setBufferSize(1048576);
                input = new BufferedInputStream(inputStream);
                boolean var31;
                if (!ftpClient.storeFile(new String (fileName.getBytes(LOCAL_CHARSET),
                        SERVER_CHARSET),  input)){
                    var31 = result;
                    return var31;
                }

                result = true;
                var31 = result;
                return var31;
            }

            var7 = result;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException var28) {
                    ;
                }
            }

            if (input != null) {
                try {
                    input.close();
                } catch (IOException var27) {
                    ;
                }
            }

        }

        return var7;
    }

    public static boolean uploadFileChangeName(FTPClient ftpClient, String basePath, String filePath, String fileName, String originfileName) throws Exception {
        boolean result = false;
        InputStream inputStream = new FileInputStream(new File(originfileName));
        result = uploadFile(ftpClient, basePath, filePath, fileName, inputStream);
        return result;
    }

    public static boolean uploadFileNotChangeName(FTPClient ftpClient, String basePath, String filePath, String originfileName) throws Exception {
        boolean result = false;
        String fileName = (new File(originfileName)).getName();
        InputStream inputStream = new FileInputStream(new File(originfileName));
        result = uploadFile(ftpClient, basePath, filePath, fileName, inputStream);
        return result;
    }

    public static boolean deleteFile(FTPClient ftpClient, String filePath, String fileName) throws Exception {
        boolean result = false;
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.setFileType(2);
        ftpClient.enterLocalPassiveMode();
        ftpClient.changeWorkingDirectory(filePath);
        ftpClient.dele(fileName);
        result = true;
        return result;
    }

    public static boolean downloadFileForLocal(FTPClient ftpClient, String filePath, String fileName, String localpath) throws Exception {
        boolean result = false;
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.setFileType(2);
        ftpClient.enterLocalPassiveMode();
        ftpClient.changeWorkingDirectory(filePath);
        FTPFile[] ftpFiles = ftpClient.listFiles();
        if (ftpFiles != null && ftpFiles.length > 0) {
            FTPFile[] var6 = ftpFiles;
            int var7 = ftpFiles.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                FTPFile file = var6[var8];
                if (fileName.equalsIgnoreCase(file.getName())) {
                    File localFile = new File(localpath + "/" + file.getName());
                    OutputStream os = new FileOutputStream(localFile);
                    result = ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                    break;
                }
            }
        }

        return result;
    }

    public static boolean downloadFileForBrowser(FTPClient ftpClient, String filePath, String fileName, HttpServletResponse response) throws Exception {
        boolean result = false;
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        OutputStream out = response.getOutputStream();
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(2);
        ftpClient.changeWorkingDirectory(filePath);
        FTPFile[] ftpFiles = ftpClient.listFiles();
        if (ftpFiles != null && ftpFiles.length > 0) {
            FTPFile[] var8 = ftpFiles;
            int var9 = ftpFiles.length;

            for(int var10 = 0; var10 < var9; ++var10) {
                FTPFile file = var8[var10];
                if (fileName.equalsIgnoreCase(file.getName())) {
                    result = ftpClient.retrieveFile(fileName, out);
                    out.close();
                    break;
                }
            }
        }

        return result;
    }

    public static byte[] getFtpFileContent(FTPClient ftpClient, String filePath, String fileName) throws Exception {
        byte[] content = null;
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.setFileType(2);
        ftpClient.enterLocalPassiveMode();
        ftpClient.changeWorkingDirectory(filePath);
        FTPFile[] ftpFiles = ftpClient.listFiles();
        if (ftpFiles != null && ftpFiles.length > 0) {
            FTPFile[] var5 = ftpFiles;
            int var6 = ftpFiles.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                FTPFile file = var5[var7];
                if (fileName.equalsIgnoreCase(file.getName())) {
                    InputStream in = ftpClient.retrieveFileStream(file.getName());
                    if (in != null) {
                        ByteArrayOutputStream output = new ByteArrayOutputStream();
                        IOUtils.copy(in, output);
                        content = output.toByteArray();
                        in.close();
                        output.close();
                        ftpClient.completePendingCommand();
                    }
                    break;
                }
            }
        }

        return content;
    }

    public static boolean renameFtpFile(FTPClient ftpClient, String filePath, String fileOriName, String fileNewName) throws Exception {
        boolean result = false;
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.setFileType(2);
        ftpClient.enterLocalPassiveMode();
        ftpClient.changeWorkingDirectory(filePath);
        FTPFile[] ftpFiles = ftpClient.listFiles();
        if (ftpFiles != null && ftpFiles.length > 0) {
            FTPFile[] var6 = ftpFiles;
            int var7 = ftpFiles.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                FTPFile file = var6[var8];
                if (fileOriName.equalsIgnoreCase(file.getName())) {
                    result = ftpClient.rename(fileOriName, fileNewName);
                }
            }
        }

        return result;
    }

    public static void main(String[] args)  {
        int port = 21;
        String hostName = "127.0.0.1";
        String userName = "ftp";
        String passWord = "123456";
        String basePath = "";
        String filePath = "/home/file";
        String fileName = "13333444.pdf";
        String originfileName = "C:/Users/My/Desktop/文件/EL手册.pdf";
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient(hostName, port, userName, passWord);
            boolean bool=uploadFileChangeName(ftpClient,basePath, filePath,fileName, originfileName);
            logger.info("返回结果为{}",bool);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                disconnectFTPClient(ftpClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
