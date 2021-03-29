package com.fc.util;

import com.fc.util.security.SecurityUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Description 文件工具
 * @author #
 * @Date 2018/3/11
 * @version 1.0.0
 */
public class FileUtils {

    /**
     * 保存文件方法
     * @param file          文件源
     * @param fullFileName  文件名   如"123.doc"
     * @param path          相对路径 如"lab/report"
     * @param mainId        主键id   可以为空
     * @return 文件路径
     */
    public static String saveFile(MultipartFile file, String fullFileName, String path, String mainId){
        //当前session
        HttpSession session = SecurityUser.getCurrentSession();
        //上传文件的根目录
        String uploadPath = session.getServletContext().getRealPath("upload");
        //上传文件的文件夹路径
        String dirPath = uploadPath + File.separator + path;
        //文件名
        String fileName = "";
        //扩展名
        String ext = "";

        if(dirPath.startsWith("/")){
            dirPath = dirPath.substring(1,dirPath.length());
        }
        if(StringUtils.isNotBlank(mainId)){
            dirPath = dirPath + File.separator + mainId;
        }

        if(fileName.contains(".")){
            //取得文件名
            fileName = fileName.substring(0,fileName.lastIndexOf("."));
            //取得扩展名
            ext = fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length());
        }else{
            fileName = fullFileName;
        }

        //文件夹不存在
        File dir = new File(dirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        FileOutputStream fos = null;
        //文件绝对路径
        String filePath = dirPath + File.separator + fileName + "." + ext;
        try {
            //保存文件
            File targetFile = new File(filePath);
            int i = 1;
            while (targetFile.exists()){
                //存在则再取另外的名字
                filePath = dirPath + File.separator + fileName + "(" + i + ")" + "." + ext;
                targetFile = new File(filePath);
                i++;
            }
            fos = new FileOutputStream(targetFile);
            fos.write(file.getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return filePath;
    }

    /**
     * 保存文件方法
     * @param file          文件源
     * @param path          相对路径 如"lab/report"
     * @return 文件路径
     */
    public static String saveFile(MultipartFile file, String path) throws Exception {
        if(file.isEmpty()){
            throw new Exception("文件不存在！");
        }
        //当前session
        HttpSession session = SecurityUser.getCurrentSession();
        if(!path.startsWith("/")){
            path = path!=null?File.separator + path:"";
        }
        path = session.getServletContext().getRealPath("upload"+path);
        String fileName = file.getOriginalFilename();
        String folder = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String position = path + File.separator + folder + File.separator + fileName; // 文件绝对路径
        File targetFile = new File(path, folder);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        targetFile = new File(targetFile, fileName);
        //写入到文件
        file.transferTo(targetFile);
        return position;
    }

    /**
     *  删除单个文件
     * @param pictureUrl    图片路径地址例：/img/spreadPicture/xx.jpg
     */
    public static void deleteFile(String pictureUrl) {
        if(StringUtils.isNotBlank(pictureUrl)){
            String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static"+pictureUrl;
            File file = new File(filePath);
            // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
    }

    /**
     * @Description 获得文件夹下的所有文件
     * 1、文件夹除外
     * 2、包括所有子集
     * @param file 文件
     * @return list
     */
    public static List<File> getAllSubFile(File file){
        List<File> fileList = new ArrayList<>();
        if(file.isFile()){
            return fileList;
        }
        List<File> currentLevel = Arrays.asList(file.listFiles());
        List<File> nextLevel = new ArrayList<>();
        for(int i = 0;i < currentLevel.size();i++){
            File f = currentLevel.get(i);
            if(f.isFile()){
                nextLevel.add(f);
            }else{
                nextLevel.addAll(FileUtils.getAllSubFile(f));
            }
        }
        fileList.addAll(nextLevel);
        return fileList;
    }

    /**
     * 生成压缩文件
     * @param path 要压缩的文件路径
     * @param savePath 压缩包保存路径（不能与被压缩文件相同或子文件夹）
     * @param zipName 压缩包名，格式：xxx.zip或xxx.rar
     * @param deleteOriginalFile 压打包后是否删除源文件
     */
    public static void packageFile(String path, String savePath, String zipName, boolean deleteOriginalFile) throws Exception {
        File file = new File(path);
        // 压缩文件的路径不存在
        if (!file.exists()) {
            throw new Exception("路径 " + path + " 不存在文件，无法进行压缩...");
        }
        // 目的压缩文件
        String generateFileName = savePath + File.separator + zipName;

        // 输入流 表示从一个源读取数据
        // 输出流 表示向一个目标写入数据
        // 输出流
        FileOutputStream outputStream = new FileOutputStream(generateFileName);
        // 压缩输出流
        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream));
        // 开始压缩
        packageFile(zipOutputStream,file,"");
        System.out.println("源文件位置：" + file.getAbsolutePath() + "，目的压缩文件生成位置：" + generateFileName);
        // 关闭 输出流
        zipOutputStream.close();
        if(deleteOriginalFile){
            // 删除源文件
            FileUtils.deleteDir(file.getAbsolutePath());
        }
    }

    /**
     * 压缩文件具体实现方法
     * @param out  输出流
     * @param file 目标文件
     * @param dir  文件夹
     * @throws Exception e
     */
    private static void packageFile(ZipOutputStream out, File file, String dir) throws Exception {
        if (file.isDirectory()) {
            // 当前的是文件夹，则进行一步处理
            // 得到文件列表信息
            File[] files = file.listFiles();
            // 将文件夹添加到下一级打包目录
            out.putNextEntry(new ZipEntry(dir + "/"));
            dir = dir.length() == 0 ? "" : dir + "/";
            // 循环将文件夹中的文件打包
            for (int i = 0; i < files.length; i++) {
                packageFile(out, files[i], dir + files[i].getName());
            }
        } else {
            // 当前是文件
            // 输入流
            FileInputStream inputStream = new FileInputStream(file);
            // 标记要打包的条目
            out.putNextEntry(new ZipEntry(dir));
            // 进行写操作
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) > 0) {
                out.write(bytes, 0, len);
            }
            // 关闭输入流
            inputStream.close();
        }
    }

    /**
     * 复制文件，如果有同文件，则跳过
     * @param oldFilePath 旧文件路径（包含文件名）
     * @param newPath 新文件路径（包含文件名）
     */
    public static void copyFile(String oldFilePath, String newPath) {
        File oldFile = new File(oldFilePath);
        File newFile = new File(newPath);
        if (!oldFile.exists() || !oldFile.isFile()) {
            // 文件不存在
            return;
        }
        if (!newFile.exists()) {
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            // 输入流
            FileInputStream fin = new FileInputStream(oldFile);
            try {
                // 输出流
                FileOutputStream fout = new FileOutputStream(newFile, true);
                byte[] b = new byte[1024];
                try {
                    // 读取到末尾 返回-1 否则返回读取的字节个数
                    while ((fin.read(b)) != -1) {
                        fout.write(b);
                    }
                    fin.close();
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载单个文件
     * @param request request
     * @param response response
     * @param file file对象
     * @param fileName 下载时文件名
     */
    public static void downloadSingleFile(HttpServletRequest request, HttpServletResponse response, File file, String fileName){
        // 读到流中
        InputStream inStream;
        try {
            inStream = new FileInputStream(file);
            // 处理空格转为加号的问题
            String downloadFileName = "";
            if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE")>0){
                downloadFileName = URLEncoder.encode(fileName, "UTF-8");
            }else {
                downloadFileName = new String((fileName).getBytes("UTF-8"), "ISO8859-1");
            }
            downloadFileName = downloadFileName.replaceAll("\\+","%20");
            // 设置输出的格式
            response.reset();
            response.setContentType("bin");
            response.addHeader("Content-Disposition", "attachment;filename=\"" + downloadFileName + "\"");
            response.addHeader("Content-Length", "" + file.length());
            // 循环取出流中的数据
            byte[] b = new byte[200];
            int len;
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 迭代删除文件夹
     * @param dirPath 文件夹真实路径
     */
    public static void deleteDir(String dirPath) {
        // 读取
        File file = new File(dirPath);
        // 判断是否是文件夹
        if (file.isFile()) {
            // 删除文件
            file.delete();
        } else {
            // 文件夹，获取文件
            File[] files = file.listFiles();
            if (files == null) {
                // 删除
                file.delete();
            } else {
                for (int i = 0; i < files.length; i++) {
                    // 循环
                    FileUtils.deleteDir(files[i].getAbsolutePath());
                }
                // 删除
                file.delete();
            }
        }
    }

    /**
     * 获得显示的文件大小，xxKB、xxMB、xxGB
     * @param file 文件
     * @return fileSize
     */
    public static String getShowFileSize(File file){
        String fileSize = "";
        if(file == null || !file.exists()){
            return fileSize;
        }
        long size = file.length();
        // 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        // 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        // 因为还没有到达要使用另一个单位的时候
        // 接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            // 因为如果以MB为单位的话，要保留最后1位小数，
            // 因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "MB";
        } else {
            // 否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "GB";
        }
    }
}
