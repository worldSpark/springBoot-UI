package com.fc.controller.common;

import com.fc.common.domain.CommonFileUploadConfig;
import com.fc.model.template.CommonFileUpload;
import com.fc.service.common.CommonFileUploadService;
import com.fc.service.common.CommonService;
import com.fc.util.ArrayUtil;
import com.fc.util.FileUtils;
import com.fc.util.GlobalFunc;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 通用文件上传控制器
 * @author luoy
 * @Date 2019年8月10日14:18:17
 * @version 1.0.0
 */
@Controller
@RequestMapping("/common/commonFileUpload")
public class CommonFileUploadController {

    @Autowired
    private CommonFileUploadService commonFileUploadService;

    @Autowired
    private CommonService commonService;

    /**
     * @Description 通用文件上传 - 建议配合layer弹窗使用
     * @param request request
     * @param model model
     * @return jsp
     */
    @RequestMapping(method = {RequestMethod.GET}, value = "/addUpload")
    public String addUpload(HttpServletRequest request, Model model){
        // 关联id
        String mainId = GlobalFunc.toString(request.getParameter("mainId"));
        // 配置信息 - 详情请去查看实体类：CommonFileUploadConfig
        String minFileCount = request.getParameter("minFileCount");
        String maxFileCount = request.getParameter("maxFileCount");
        String maxSingleFileSize = request.getParameter("maxSingleFileSize");
        String maxMultipleFileSize = request.getParameter("maxMultipleFileSize");
        String uploadPath = GlobalFunc.toString(request.getParameter("uploadPath"));
        String exts = GlobalFunc.toString(request.getParameter("exts"));
        String showImport = GlobalFunc.toString(request.getParameter("showImport"));
        String showClose = GlobalFunc.toString(request.getParameter("showClose"));
        String showDelete = GlobalFunc.toString(request.getParameter("showDelete"));
        String showDeleteAll = GlobalFunc.toString(request.getParameter("showDeleteAll"));
        String showDownload = GlobalFunc.toString(request.getParameter("showDownload"));
        String showDownloadAll = GlobalFunc.toString(request.getParameter("showDownloadAll"));
        String deleteWithFile = GlobalFunc.toString(request.getParameter("deleteWithFile"));
        String beforeCloseWindow = GlobalFunc.toString(request.getParameter("beforeCloseWindow"));
        String closeAfterUpload = GlobalFunc.toString(request.getParameter("closeAfterUpload"));

        //自加参数xiongxb,用于区分文件上传和附件下载，gis展示得相关文件
        String type = GlobalFunc.toString(request.getParameter("type"));//true/false
        String wordPath = GlobalFunc.toString(request.getParameter("wordPath"));
        //结束
        // 创建配置对象并赋值
        CommonFileUploadConfig config = new CommonFileUploadConfig();
        config.setMinFileCount(minFileCount);
        config.setMaxFileCount(maxFileCount);
        config.setMaxSingleFileSize(maxSingleFileSize);
        config.setMaxMultipleFileSize(maxMultipleFileSize);
        config.setUploadPath(uploadPath);
        config.setExts(exts);
        config.setShowImport(showImport);
        config.setShowClose(showClose);
        config.setShowDelete(showDelete);
        config.setShowDeleteAll(showDeleteAll);
        config.setShowDownload(showDownload);
        config.setShowDownloadAll(showDownloadAll);
        config.setDeleteWithFile(deleteWithFile);
        config.setBeforeCloseWindow(beforeCloseWindow);
        config.setCloseAfterUpload(closeAfterUpload);

        // 查询已上传的文件
        List<CommonFileUpload> uploadedFileList = commonFileUploadService.getByMainId(mainId);
        // 转Map
        List<Map<String, Object>> uploadedFileMapList = new ArrayList<>();
        // 查询文件是否已失效
        for (CommonFileUpload fileUpload : uploadedFileList) {
            // 转Map
            Map<String, Object> fileUploadMap = commonService.convertObjectToMap(fileUpload);
            String originalFileName = fileUpload.getOriginalFileName();
            boolean fileExist =false;
            // 判断真实文件是否存在
            fileExist = commonFileUploadService.isFileExist(fileUpload);

            // 放入map
            fileUploadMap.put("fileExist", fileExist);
            uploadedFileMapList.add(fileUploadMap);
        }

        Gson gson = new Gson();
        model.addAttribute("mainId", mainId);
        model.addAttribute("config", gson.toJson(config));
        model.addAttribute("uploadedFileList", gson.toJson(uploadedFileMapList));
        model.addAttribute("type",type);
        model.addAttribute("wordPath",wordPath);
        return "/common/addCommonFileUpload";
    }

    /**
     * 上传单个文件
     * @param request request
     * @param file 文件
     * @return result
     */
    @ResponseBody
    @RequestMapping(method = {RequestMethod.POST}, value = "/uploadSingleFile")
    public Map<String, Object> uploadSingleFile(HttpServletRequest request,
                                                @RequestParam(value = "newFile", required = false) MultipartFile file){
        String mainId = GlobalFunc.toString(request.getParameter("mainId"));
        String uploadPath = GlobalFunc.toString(request.getParameter("uploadPath"));
        Map<String, Object> result = commonFileUploadService.uploadSingleFile(mainId, file, uploadPath);
        return result;
    }

    /**
     * 删除附件
     * @param request request
     * @return message
     */
    @ResponseBody
    @RequestMapping(method = {RequestMethod.POST}, value = "/deleteByMainId")
    public boolean deleteByMainId(HttpServletRequest request){
        boolean deleteWithFile = "true".equals(request.getParameter("deleteWithFile"));
        String mainId = GlobalFunc.toString(request.getParameter("mainId"));
        String fileId = GlobalFunc.toString(request.getParameter("fileId"));
        List<String> fileIdList = ArrayUtil.split(fileId, ",");
        int count = commonFileUploadService.deleteByMainId(deleteWithFile, mainId, fileIdList);
        return count > 0;
    }

    /**
     * 下载单个文件
     * @param request request
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/downloadSingleFile")
    public void downloadSingleFile(HttpServletRequest request, HttpServletResponse response){
        String fileId = GlobalFunc.toString(request.getParameter("fileId"));
        commonFileUploadService.downloadSingleFile(request, response, fileId);
    }

    /**
     * 下载单个文件（只适用于一个mainId对应一个文件）
     * @param request request
     * @param response response
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/downloadSingleFileByMainId")
    public void downloadSingleFileByMainId(HttpServletRequest request, HttpServletResponse response){
        String mainId = GlobalFunc.toString(request.getParameter("mainId"));
        commonFileUploadService.downloadSingleFileByMainId(request, response, mainId);
    }

    /**
     * 打包多个文件
     * @param request request
     * @return message
     */
    @ResponseBody
    @RequestMapping(method = {RequestMethod.POST}, value = "/packageByMainId")
    public Map<String, Object> packageByMainId(HttpServletRequest request){
        String mainId = GlobalFunc.toString(request.getParameter("mainId"));
        Map<String, Object> map = commonFileUploadService.packageByMainId(mainId);
        return map;
    }

    /**
     * 下载打包后的压缩文件
     * @param request request
     * @param response response
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/downloadZipFile")
    public void downloadZipFile(HttpServletRequest request, HttpServletResponse response){
        String zipName = GlobalFunc.toString(request.getParameter("zipName"));
        // 压缩包完整路径
        String zipPath = commonFileUploadService.getCommonRealRootPath() + File.separator + "temp"
                + File.separator + zipName + ".zip";
        File file = new File(zipPath);
        if (!file.exists()) {
            return;
        }
        // 下载时，压缩包文件名
        String downloadZipName = GlobalFunc.getChinaDateNow() + ".zip";
        // 调用下载文件公共方法
        FileUtils.downloadSingleFile(request, response, file, downloadZipName);
    }

    /**
     * 回调方法获取关联的文件信息集合
     * @param request request
     * @param response response
     */
    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/getFileInforByMainId")
    public List<CommonFileUpload> getFileInforByMainId(HttpServletRequest request, HttpServletResponse response){
        String id = GlobalFunc.toString(request.getParameter("id"));
        List<CommonFileUpload> list = commonFileUploadService.getByMainId(id);
        return list;
    }

    /**
     * 预览单个文件
     * @param request request
     */
    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/previewSingleFile")
    public Map<String,Object> previewSingleFile(HttpServletRequest request, HttpServletResponse response){
        String fileId = GlobalFunc.toString(request.getParameter("fileId"));
        Map<String,Object> map = commonFileUploadService.previewSingleFile(request, response, fileId);
        return map;
    }

    /**
     * 获取复制文件的路径
     * */
    @ResponseBody
    @RequestMapping(value = "/copyToTemporaryFile",method = {RequestMethod.POST,RequestMethod.GET})
    public Map<String,Object> copyToTemporaryFile(HttpServletRequest request, HttpServletResponse response){
        String mainId = GlobalFunc.toString(request.getParameter("mainId"));
        Map<String,Object> map = commonFileUploadService.copyToTemporaryFile(request, response, mainId);
        return map;
    }
}
