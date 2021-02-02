package com.example.demo.modules.file.service;

import com.example.demo.conf.FtpAttConfig;

import com.example.demo.core.util.FileProperties;
import com.example.demo.core.util.FtpAttUtil;
import com.example.demo.modules.file.mapper.SysFileMapper;
import com.example.demo.modules.file.model.SysFile;
import com.example.demo.modules.sys.model.SysUser;
import com.example.demo.modules.sys.service.UserService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class FileService {
    Logger logger= LoggerFactory.getLogger(FileService.class);
    @Autowired
    private FtpAttConfig ftpAttConfig;
    @Autowired
    private UserService userService;

    @Autowired
    private SysFileMapper sysFileMapper;

    @Resource
    private FileProperties fileProperties;

    public void upload(MultipartFile file, HttpServletRequest request) throws Exception {
        logger.info("开始上传文件"+file.getName());
        FTPClient ftpClient = FtpAttUtil.getFTPClient(ftpAttConfig.getFtpHost(), ftpAttConfig.getFtpPort(), ftpAttConfig.getFtpUserName(), ftpAttConfig.getFtpPassWord());
        String imgName=System.currentTimeMillis()+file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        if(ftpClient==null){//ftp未连接，则上传本地

        }else{//上传到ftp
            FtpAttUtil.uploadFile(ftpClient, "", ftpAttConfig.getFtpDefaultPath(), imgName, file.getInputStream());
            FtpAttUtil.disconnectFTPClient(ftpClient);
        }
        logger.info("结束上传文件"+file.getName());
        SysUser sysUser=(SysUser) request.getSession().getAttribute("user");
        SysFile sysFile=new SysFile();
        sysFile.setFileName(file.getOriginalFilename());
        sysFile.setFileChname(imgName);
        sysFile.setFileUrl(ftpAttConfig.getFtpDefaultPath()+"/"+imgName);
        sysFile.setFileType("1");
        saveSysFile(sysFile,sysUser);
    }



    public void uploadAvatarByBase64(MultipartFile file, HttpServletRequest request) throws IOException {
        String fileSavePath = fileProperties.getFileUploadPath();
        String name=file.getOriginalFilename();
        file.transferTo(new File(fileSavePath + name));

        SysUser sysUser=(SysUser) request.getSession().getAttribute("user");
        sysUser.setAvatar(name);
        userService.saveAvatar(sysUser);
    }

    public void saveSysFile(SysFile sysFile,SysUser sysUser){
        sysFile.setCreateTime(new Date());
        sysFile.setCreateUser(sysUser.getUsername());
        sysFileMapper.insertSelective(sysFile);
    }

    public void deleteSysFile(Integer id){
        sysFileMapper.deleteByPrimaryKey(id);
    }


    public List<SysFile> getAll(SysFile sysFile, String keyword) {
        PageHelper.startPage(sysFile.getPage(),sysFile.getRows());
        Example example=new Example(SysFile.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(keyword)){
            keyword="%"+keyword+"%";
            criteria.andLike("fileName",keyword);
        }
        if(StringUtils.isNotBlank(sysFile.getCreateUser())){
            criteria.andEqualTo("createUser",sysFile.getCreateUser());
        }
        return sysFileMapper.selectByExample(example);
    }

    public void batchDelete(Integer[] ids) throws Exception {
        if(ids.length>0){
            FTPClient ftpClient = FtpAttUtil.getFTPClient(ftpAttConfig.getFtpHost(), ftpAttConfig.getFtpPort(), ftpAttConfig.getFtpUserName(), ftpAttConfig.getFtpPassWord());
            for (int i=0;i<ids.length;i++){
                SysFile fileEntity = sysFileMapper.selectByPrimaryKey(ids[i]);
                if(fileEntity!=null){
                    sysFileMapper.deleteByPrimaryKey(ids[i]);
                    FtpAttUtil.deleteFile(ftpClient,ftpAttConfig.getFtpDefaultPath(),fileEntity.getFileChname());
                }
            }
            FtpAttUtil.disconnectFTPClient(ftpClient);
        }
    }

    public void downLoad(HttpServletResponse response, Integer fileId) throws Exception {
        if(fileId==null){
            SysFile fileEntity = sysFileMapper.selectByPrimaryKey(fileId);
            if(fileEntity!=null){
                FTPClient ftpClient = FtpAttUtil.getFTPClient(ftpAttConfig.getFtpHost(), ftpAttConfig.getFtpPort(), ftpAttConfig.getFtpUserName(), ftpAttConfig.getFtpPassWord());
                FtpAttUtil.downloadFileForBrowser(ftpClient,ftpAttConfig.getFtpDefaultPath(),fileEntity.getFileChname(),response);
                FtpAttUtil.disconnectFTPClient(ftpClient);
            }
        }else {
            throw new Exception("参数fileId为空！");
        }
    }

    public SysFile getFileById(Integer fileId) {
        return sysFileMapper.selectByPrimaryKey(fileId);
    }
}
