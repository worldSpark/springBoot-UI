package com.fc.service.common.Impl;

import com.fc.common.conf.CommonConfig;
import com.fc.common.domain.SqlCondition;
import com.fc.model.auto.TsysUser;
import com.fc.model.template.CommonFileUpload;
import com.fc.service.common.CommonFileUploadService;
import com.fc.service.common.CommonService;
import com.fc.shiro.util.ShiroUtils;
import com.fc.util.FileUtils;
import com.fc.util.GlobalFunc;
import com.fc.util.Sequence;
import com.fc.util.security.SecurityUser;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 通用文件上传实现层
 * @author luoy
 * @Date 2019年8月10日 13:56:41
 * @version 1.0.0
 **/
@Service
@Transactional
public class CommonFileUploadServiceImpl implements CommonFileUploadService {

    @Autowired
    private CommonService commonService;

    @Override
    public int insert(CommonFileUpload upload) {
        int count =  commonService.insert(upload);
        return count;
    }

    @Override
    public int update(CommonFileUpload upload) {
        int count =  commonService.update(upload);
        return count;
    }

    @Override
    public int delete(String id) {
        int count = this.delete(id, true);
        return count;
    }

    @Override
    public int delete(String id, boolean deleteFile) {
        CommonFileUpload upload = this.getById(id);
        int count = commonService.delete(CommonFileUpload.class, id);
        if(deleteFile && upload != null){
            // 删除文件
            // 绝对路径
            String realPath = SecurityUser.getCurrentSession().getServletContext().getRealPath(upload.getFilePath());
            File file = new File(realPath);
            if(file.exists()){
                file.delete();
            }
        }
        return count;
    }

    @Override
    public CommonFileUpload getById(String id) {
        CommonFileUpload upload = commonService.getById(CommonFileUpload.class, id);
        return upload;
    }

    @Override
    public List<CommonFileUpload> getByMainId(String mainId) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.andEqualTo("mainId", mainId);
        sqlCondition.andEqualTo("isExist", "是");
        List<CommonFileUpload> uploadList = commonService.getByCondition(CommonFileUpload.class, sqlCondition);
        return uploadList;
    }

    @Override
    public List<CommonFileUpload> getByMainId(List<String> mainIdList) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.andIn("mainId", mainIdList);
        sqlCondition.andEqualTo("isExist", "是");
        List<CommonFileUpload> uploadList = commonService.getByCondition(CommonFileUpload.class, sqlCondition);
        return uploadList;
    }

    @Override
    public String getCommonRealRootPath() {
        String rootPath = CommonConfig.FILE_ROOT_PATH;
        if(StringUtils.isBlank(rootPath)){
            // 项目根路径
            HttpSession session = SecurityUser.getCurrentSession();
            ServletContext servletContext = session.getServletContext();
            rootPath = servletContext.getRealPath("/");
        }
        return rootPath;
    }

    @Override
    public String getCommonUploadPath(String uploadPath, boolean appendDate){
        if(appendDate){
            // 获得当前日期
            String dateNow = GlobalFunc.getChinaDate();
            uploadPath = uploadPath + File.separator + dateNow;
        }
        if(uploadPath.startsWith("/") || uploadPath.startsWith("\\")){
            uploadPath = uploadPath.substring(1);
        }
        return uploadPath;
    }

    @Override
    public Map<String, Object> uploadSingleFile(String mainId, MultipartFile file, String uploadPath) {
        Map<String, Object> map = new HashMap<>();
        if(file == null || StringUtils.isBlank(mainId)){
            map.put("result", "error");
            map.put("message", "数据异常！");
            return map;
        }

        TsysUser user = ShiroUtils.getUser();
        // 获得文件存放绝对根路径
        String realRootPath = this.getCommonRealRootPath();
        // 获得文件相对路径(包含日期)
        String filePath = this.getCommonUploadPath(uploadPath, true);
        // 原文件名
        String originalFilename = file.getOriginalFilename();
        // 文件后缀名
        String extName = this.getExtName(originalFilename);
        // 保存时文件名
        String realFileName = Sequence.getSequence() + "." + extName;
        // 文件大小（单位Kb）
        long fileSize = this.getCommonFileSize(file);

        // 第一步，保存文件
        // 保存文件的完整路径
        String saveFilePath = realRootPath + File.separator + filePath;
        boolean saveSuccess = this.saveSingleFile(file, saveFilePath, realFileName);

        if(saveSuccess){
            // 第二步，创建实体类
            CommonFileUpload fileUpload = new CommonFileUpload();
            fileUpload.setId(Sequence.getSequence());
            fileUpload.setMainId(mainId);
            fileUpload.setPkWpUser(user.getId());
            fileUpload.setOriginalFileName(originalFilename);
            fileUpload.setRealFileName(realFileName);
            fileUpload.setFilePath(filePath);
            fileUpload.setFileType(extName);
            fileUpload.setFileSize(fileSize + "");
            fileUpload.setCreateTime(GlobalFunc.getChinaDateNow());
            fileUpload.setIsExist("是");
            this.insert(fileUpload);
            map.put("result", "success");
            map.put("message", "保存成功！");
            map.put("fileId", fileUpload.getId());
        }else{
            map.put("result", "error");
            map.put("message", "保存失败！");
        }

        return map;
    }

    @Override
    public Map<String, Object> uploadSingleFileByApp(String mainId, MultipartFile file, String uploadPath) {
        Map<String, Object> map = new HashMap<>();
        if(file == null || StringUtils.isBlank(mainId)){
            map.put("result", "error");
            map.put("message", "数据异常！");
            return map;
        }

        TsysUser user = ShiroUtils.getUser();
        // 获得文件存放绝对根路径
        String realRootPath = this.getCommonRealRootPath();
        // 获得文件相对路径
        String filePath = this.getCommonUploadPath(uploadPath, false);
        // 原文件名
        String originalFilename = file.getOriginalFilename();
        // 文件后缀名
        String extName = this.getExtName(originalFilename);
        // 保存时文件名
        String realFileName = Sequence.getSequence() + "." + extName;
        // 文件大小（单位Kb）
        long fileSize = this.getCommonFileSize(file);

        // 第一步，保存文件
        // 保存文件的完整路径
        boolean saveSuccess = this.saveSingleFile(file, filePath, realFileName);

        if(saveSuccess){
            // 第二步，创建实体类
            CommonFileUpload fileUpload = new CommonFileUpload();
            fileUpload.setId(Sequence.getSequence());
            fileUpload.setMainId(mainId);
            fileUpload.setPkWpUser(user.getId());
            fileUpload.setOriginalFileName(originalFilename);
            fileUpload.setRealFileName(realFileName);
            fileUpload.setFilePath(filePath);
            fileUpload.setFileType(extName);
            fileUpload.setFileSize(fileSize + "");
            fileUpload.setCreateTime(GlobalFunc.getChinaDateNow());
            fileUpload.setIsExist("是");
            this.insert(fileUpload);
            map.put("result", "success");
            map.put("message", "保存成功！");
            map.put("fileId", fileUpload.getId());
        }else{
            map.put("result", "error");
            map.put("message", "保存失败！");
        }
        return map;
    }

    @Override
    public int deleteByMainId(boolean deleteWithFile, String mainId, List<String> fileIdList) {
        int count = 0;
        if(StringUtils.isBlank(mainId)){
            return count;
        }
        // 查询条件
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.andEqualTo("main_id", mainId);
        sqlCondition.andEqualTo("is_exist", "是");
        if(fileIdList != null && !fileIdList.isEmpty()){
            sqlCondition.andIn("id", fileIdList);
        }
        if(deleteWithFile){
            // 删除文件
            List<CommonFileUpload> fileUploadList = commonService.getByCondition(CommonFileUpload.class, sqlCondition);
            // 文件绝对路径
            String realRootPath = this.getCommonRealRootPath();
            for(CommonFileUpload fileUpload : fileUploadList){
                // 文件相对路径
                String filePath = fileUpload.getFilePath();
                // 文件真实名称
                String realFileName = fileUpload.getRealFileName();
                File file = new File(realRootPath + File.separator + filePath + File.separator + realFileName);
                if(file.exists()){
                    file.delete();
                }
            }
        }
        // 删数据，假删除
        List<CommonFileUpload> fileUploadList = commonService.getByCondition(CommonFileUpload.class, sqlCondition);
        for(CommonFileUpload fileUpload : fileUploadList){
            fileUpload.setIsExist("否");
        }
        count = commonService.updateList(fileUploadList);
        return count;
    }

    @Override
    public boolean isFileExist(CommonFileUpload fileUpload) {
        boolean result = false;
        if(fileUpload == null){
            return result;
        }
        // 文件真实完整路径
        String realFullFilePath = this.getRealFullFilePath(fileUpload);
        File file = new File(realFullFilePath);
        if(file.exists()){
            result = true;
        }
        return result;
    }

    @Override
    public String getRealFullFilePath(CommonFileUpload fileUpload) {
        // 文件真实根目录
        String realFileRootPath = this.getCommonRealRootPath();
        // 文件相对路径
        String filePath = fileUpload.getFilePath();
        // 保存的文件名
        String saveFileName = fileUpload.getRealFileName();
        String realFullFilePath = realFileRootPath + File.separator + filePath + File.separator + saveFileName;
        return realFullFilePath;
    }

    @Override
    public void downloadSingleFile(HttpServletRequest request, HttpServletResponse response, String fileId) {
        // 文件实体
        CommonFileUpload fileUpload = this.getById(fileId);
        // 文件名称
        String originalFileName = fileUpload.getOriginalFileName();
        String realFullFilePath = "";
        if(originalFileName.indexOf("突发事件公共卫生") !=-1 || originalFileName.indexOf("媒体信息") !=-1){
            // 文件路径
            realFullFilePath= fileUpload.getFilePath();
        }else {
            // 文件路径
            realFullFilePath = this.getRealFullFilePath(fileUpload);
        }
        File file = new File(realFullFilePath);
        if (!file.exists()) {
            return;
        }
        // 调用下载文件公共方法
        FileUtils.downloadSingleFile(request, response, file, originalFileName);
    }

   @Override
   public Map<String,Object> previewSingleFile(HttpServletRequest request, HttpServletResponse response, String fileId) {
       // 文件实体
       CommonFileUpload fileUpload = this.getById(fileId);
       //判断后缀名
       String fileType = fileUpload.getFileType();
       //路径名
       String realFullFilePath = this.getRealFullFilePath(fileUpload);
       //转换结果
       String result = "success";
       Map<String,Object> map = new HashMap<>();
       String filePath = fileUpload.getFilePath();
       String realFileName = fileUpload.getRealFileName();

       // 项目根路径
       HttpSession session = SecurityUser.getCurrentSession();

       String tempPath = session.getServletContext().getRealPath("/upload/temp");
       // 缓存文件文件夹绝对路径
       String tempDirRealPath = tempPath + File.separator + filePath;
       // 缓存文件相对路径
       String tempFilePath = "/upload/temp" + File.separator + filePath + File.separator + realFileName;
       tempFilePath = tempFilePath.replaceAll("\\\\", "/");
       String tempFileDocPath = tempFilePath.replaceAll("\\\\","/");
       // 缓存文件绝对路径
       String tempFileRealPath = tempPath + File.separator + filePath + File.separator + realFileName;
       File tempDir = new File(tempDirRealPath);
       // 不存在则创建文件夹
       if(!tempDir.exists()){
           tempDir.mkdirs();
       }
       if (fileType.contains("docx") || fileType.contains("doc")){
           String name = tempFileRealPath.replace("."+fileType,"").concat(".pdf");
           File tempFile = new File(name);
           if(!tempFile.exists()){
               try{
                   File originalFile = new File(realFullFilePath);
                   File targetFile = new File(tempFileRealPath);
                   Files.copy(originalFile.toPath(),targetFile.toPath());
                   wToPdfChange(realFullFilePath,name);
               }catch (Exception e){
                   result = "fail";
                   e.printStackTrace();
               }
           }
           tempFilePath = tempFilePath.replace("."+fileType,"").concat(".pdf");
       }else{
           File originalFile = new File(realFullFilePath);
           File tempFile = new File(tempFileRealPath);
           // 缓存文件不存在则复制
           if(!tempFile.exists()){
               try {
                   Files.copy(originalFile.toPath(), tempFile.toPath());
               } catch (IOException e) {
                   result = "fail";
                   e.printStackTrace();
               }
           }
       }
       map.put("result",result);
       map.put("fileType",fileType);
       map.put("realFullFilePath",tempFilePath);
       map.put("tempFileDocPath",tempFileDocPath);
       return map;
   }
    public static void wToPdfChange(String wordFile,String pdfFile){

        ActiveXComponent app = null;
        try {
            // 打开word
            app = new ActiveXComponent("Word.Application");
            // 获得word中所有打开的文档
            Dispatch documents = app.getProperty("Documents").toDispatch();
            // 打开文档
            Dispatch document = Dispatch.call(documents, "Open", wordFile, false, true).toDispatch();
            // 如果文件存在的话，不会覆盖，会直接报错，所以我们需要判断文件是否存在
            File target = new File(pdfFile);
            if (target.exists()) {
                target.delete();
            }
            Dispatch.call(document, "SaveAs", pdfFile, 17);
            // 关闭文档
            Dispatch.call(document, "Close", false);
        }catch(Exception e) {
            System.out.println("转换失败"+e.getMessage());
        }finally {
            // 关闭office
            app.invoke("Quit", 0);
        }
    }

    @Override
    public Map<String, Object> copyToTemporaryFile(HttpServletRequest request, HttpServletResponse response, String mainId) {
        //获取文件数据
        List<CommonFileUpload> fileUploadList = this.getByMainId(mainId);
        CommonFileUpload fileUpload = null;
        // 一般会有文件，这里不做判断
        if (fileUploadList != null && fileUploadList.size() > 0) {
            fileUpload = fileUploadList.get(0);
        }
        // 文件真实路径
        String realFullFilePath = this.getRealFullFilePath(fileUpload);
        // 文件真实文件名
        String realFileName = fileUpload.getRealFileName();
        // 将本地文件复制到tomcat的目录下
        // 读取服务器的绝对路径
        String serverUrl = request.getSession().getServletContext().getRealPath("/");
        // 复制文件的路径
        String copyFilePath = serverUrl +  File.separator + "upload" + File.separator + "temp" + File.separator + GlobalFunc.getChinaDate();
        // 复制文件的完整路径
        String copyFileFullPath = copyFilePath + File.separator + realFileName;
        //获取复制文件的tomcat的路径
        String copyFileFullTwoPath = File.separator + "upload" + File.separator + "temp" + File.separator + GlobalFunc.getChinaDate()+File.separator + realFileName;
        // 文件夹不存在则创建
        File copyFile = new File(copyFilePath);
        if(!copyFile.exists()){
            copyFile.mkdirs();
        }
        FileUtils.copyFile(realFullFilePath, copyFileFullPath);
        Map<String,Object> map = new HashMap<>();
        map.put("copyFileFullTwoPath",copyFileFullTwoPath);
        return map;
    }

    @Override
    public void downloadSingleFileByMainId(HttpServletRequest request, HttpServletResponse response, String mainId) {
        // 文件实体
        CommonFileUpload fileUpload = null;
        List<CommonFileUpload> commonFileUploadList = this.getByMainId(mainId);
        if(!commonFileUploadList.isEmpty()){
            fileUpload = commonFileUploadList.get(0);
        }
        if(fileUpload != null){
            // 文件名称
            String originalFileName = fileUpload.getOriginalFileName();
            // 文件路径
            String realFullFilePath = this.getRealFullFilePath(fileUpload);
            File file = new File(realFullFilePath);
            if (!file.exists()) {
                return;
            }
            // 调用下载文件公共方法
            FileUtils.downloadSingleFile(request, response, file, originalFileName);
        }
    }

    @Override
    public Map<String, Object> packageByMainId(String mainId) {
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isBlank(mainId)){
            map.put("result", "error");
            map.put("message", "数据异常！");
            return map;
        }
        // 查询条件
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.andEqualTo("main_id", mainId);
        sqlCondition.andEqualTo("is_exist", "是");
        // 所有附件
        List<CommonFileUpload> fileUploadList = commonService.getByCondition(CommonFileUpload.class, sqlCondition);
        if(fileUploadList == null && fileUploadList.isEmpty()){
            map.put("result", "error");
            map.put("message", "数据异常！");
            return map;
        }
        // 开始打包
        // 第一步，准备临时文件夹
        // 压缩包名称，也是临时文件夹名称
        String zipName = Sequence.getSequence();
        // 文件夹根路径
        String realRootPath = this.getCommonRealRootPath();
        // 完整路径
        String realTempDirPath = realRootPath + File.separator + "temp" + File.separator + zipName;
        File tempDirFile = new File(realTempDirPath);
        if(!tempDirFile.exists()){
            tempDirFile.mkdirs();
        }
        // 第二步，将文件复制到临时文件夹
        for (CommonFileUpload fileUpload : fileUploadList) {
            String realFullFilePath = this.getRealFullFilePath(fileUpload);
            File file = new File(realFullFilePath);
            if(!file.exists()){
                continue;
            }
            // 复制到临时文件夹，使用真实文件名
            // 复制文件地址，包含文件名
            String newFileFullRealPath = realTempDirPath + File.separator + fileUpload.getOriginalFileName();
            FileUtils.copyFile(file.getAbsolutePath(), newFileFullRealPath);
        }
        // 第三步，调用压缩方法
        // 压缩包存储路径
        String zipPath = realRootPath + File.separator + "temp";
        try {
            // 打包并删除源文件
            FileUtils.packageFile(tempDirFile.getAbsolutePath(), zipPath, zipName + ".zip", true);
            map.put("result", "success");
            map.put("message", "打包成功，开始下载文件！");
            map.put("zipName", zipName);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "error");
            map.put("message", "打包失败！");
        }

        return map;
    }

    @Override
    public int updateMainId(String oldMainId, String newMainId) {
        List<CommonFileUpload> fileUploadList = this.getByMainId(oldMainId);
        for (CommonFileUpload fileUpload : fileUploadList) {
            fileUpload.setMainId(newMainId);
        }
        int count = commonService.updateList(fileUploadList);
        return count;
    }

    // ------------------------------------------ 私有方法 ------------------------------------------

    /**
     * 通过文件名获取文件扩展名 - 不带点
     * @param fileName 原文件名
     * @return extName
     */
    private String getExtName(String fileName){
        String extName = "";
        if(StringUtils.isBlank(fileName)){
            return extName;
        }
        if(fileName.contains(".")){
            extName = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        extName = extName.toLowerCase();
        return extName;
    }

    /**
     * 保存单个文件方法
     * @param file 文件实体
     * @param saveFilePath 保存文件真实路径（不包含文件名）
     * @param realFileName 保存时的文件名
     * @return result
     */
    private boolean saveSingleFile(MultipartFile file, String saveFilePath, String realFileName){
        boolean saveSuccess = true;
        // 第一步，判断文件夹是否存在
        File dir = new File(saveFilePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        // 第二步，创建文件并写入
        File saveFile = new File(saveFilePath + File.separator + realFileName);
        InputStream is = null;
        OutputStream os = null;
        try {
            // 输入流
            is = file.getInputStream();
            // 输出流
            os = new FileOutputStream(saveFile);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return saveSuccess;
    }

    /**
     * 获得文件大小（单位Kb）
     * @param file 文件实体
     * @return 文件大小
     */
    private long getCommonFileSize(MultipartFile file){
        // 获得文件大小
        long fileSize = file.getSize();
        BigDecimal bFileSize = new BigDecimal(fileSize);
        bFileSize = bFileSize.divide(new BigDecimal(1024), 2, BigDecimal.ROUND_UP);
        fileSize = bFileSize.longValue();
        return fileSize;
    }
}
