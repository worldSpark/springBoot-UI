package com.example.demo.modules.file.controller;

import com.example.demo.conf.FtpAttConfig;
import com.example.demo.conf.SysUserContext;
import com.example.demo.core.annotation.SysLog;
import com.example.demo.core.entity.ProcessResult;
import com.example.demo.core.result.JsonResult;
import com.example.demo.core.result.PageResult;
import com.example.demo.core.util.FileProperties;
import com.example.demo.core.util.FileUtil;
import com.example.demo.core.util.FtpAttUtil;
import com.example.demo.modules.file.model.SysFile;
import com.example.demo.modules.file.service.FileService;
import com.example.demo.modules.sys.model.SysUser;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.core.entity.ProcessResult.ERROR;


@Api(value ="文件上传", description = "文件上传Api",tags = {"文件上传接口"})
@RestController
@RequestMapping("/myfile")
public class MyFileController {
    Logger logger= LoggerFactory.getLogger(MyFileController.class);
    @Autowired
    private FileService fileService;

    @Autowired
    private FtpAttConfig ftpAttConfig;

    @Resource
    private FileProperties fileProperties;


    @ApiOperation(value = "文件上传表单视图",notes = "文件上传表单视图")
    @GetMapping("/form")
    public ModelAndView showFile(){
        return new ModelAndView("/modules/fileUpload/file");
    }


    @ApiOperation(value = "文件列表视图",notes = "文件列表视图")
    @GetMapping("/fileList")
    public ModelAndView fileList(){
        return new ModelAndView("/modules/fileUpload/list");
    }

    @ApiOperation(value = "获取文件列表",notes = "获取文件列表")
    @RequestMapping(value = "/getTableList",method = RequestMethod.GET)
    public PageResult<SysFile> getAll(@ApiParam(name = "sysFile",value="文件实体",required = true) SysFile sysFile,
                                      @ApiParam(name = "keyword",value="查询字段",required = false) String keyword,
                                      HttpServletRequest request) {
        SysUser sysUser= SysUserContext.getUser();
        sysFile.setCreateUser(sysUser.getUsername());
        List<SysFile> fileList = fileService.getAll(sysFile,keyword);
        return new PageResult(new PageInfo<SysFile>(fileList));
    }

    @ApiOperation(value = "文件上传",notes = "文件上传")
    @RequestMapping("/upload")
    public Map<String, Object> upload(HttpServletRequest request,
                                      @ApiParam(name = "file",value="上传文件",required = true) @RequestParam(value="file") MultipartFile file){
        Map<String, Object> mapJson=new HashMap<>();
        try {
            fileService.upload(file,request);
            mapJson.put("success","文件上传成功！");
            mapJson.put("url",file.getOriginalFilename());
            return mapJson;
        } catch (Exception e) {
            e.printStackTrace();
            mapJson.put("error","文件上传失败，"+e.getMessage());
            return mapJson;
        }
    }

    @ApiOperation(value = "头像上传",notes = "头像上传")
    @RequestMapping("/uploadByBase64")
    public void uploadAvatarByBase64(@ApiParam(name = "img",value="图片base64格式",required = true) String img,
                                     HttpServletRequest request){
        MultipartFile file = FileUtil.base64ToMultipart(img);
        try {
            fileService.uploadAvatarByBase64(file,request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @ApiOperation(value = "文件下载",notes = "文件下载")
    @GetMapping("/downLoad/{fileId}")
    public void downLoad(HttpServletResponse response, @PathVariable("fileId") Integer fileId) throws Exception {
        try {
            if(fileId==null) {
                throw new Exception("参数fileId为空！");
            }
            SysFile fileEntity=fileService.getFileById(fileId);
            if(fileEntity!=null){
                FTPClient ftpClient = FtpAttUtil.getFTPClient(ftpAttConfig.getFtpHost(), ftpAttConfig.getFtpPort(), ftpAttConfig.getFtpUserName(), ftpAttConfig.getFtpPassWord());
                boolean bool = FtpAttUtil.downloadFileForBrowser(ftpClient, ftpAttConfig.getFtpDefaultPath(), fileEntity.getFileChname(), response);
                logger.info("文件下载："+bool);
                FtpAttUtil.disconnectFTPClient(ftpClient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "批量删除文件",notes = "批量删除文件")
    @SysLog("批量删除文件")
    @PostMapping(value = "/batchDelete")
    public ProcessResult batchDelete(@ApiParam(name = "ids",value="文件ID数组",required = true) @RequestParam("ids[]") Integer[] ids) {
        try {
            fileService.batchDelete(ids);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }


    /**
     * 返回图片
     * 头像截图的格式均为png图片格式
     */
    @ApiOperation(value = "获取头像",notes = "获取头像")
    @RequestMapping("/{avatar}")
    public void renderPicture(@ApiParam(name = "avatar",value="图片路径",required = true) @PathVariable("avatar") String avatar,
                              HttpServletResponse response) {
        String path = fileProperties.getFileUploadPath() + avatar+".png";
        try {
            byte[] bytes = FileUtil.toByteArray(path);
            response.getOutputStream().write(bytes);
        }catch (Exception e){
            //如果找不到图片就返回一个默认图片
            try {
                response.sendRedirect("/static/hPlus/img/profile_small.jpg");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
