package com.fc.service.common;

import com.fc.model.template.CommonFileUpload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description 通用文件上传接口
 * 最后更新时间：2019年8月10日14:04:20
 * @author luoy
 * @Date 2019年8月10日 13:56:41
 * @version 1.0.0
 **/
public interface CommonFileUploadService {

    /**
     * @Description 新增一条数据
     * @param upload 文件上传实体类
     * @return count
     */
    int insert(CommonFileUpload upload);

    /**
     * @Description 更新一条数据
     * @param upload 文件上传实体类
     * @return count
     */
    int update(CommonFileUpload upload);

    /**
     * @Description 删除一条数据，会删除文件
     * @param id 文件上传实体类id
     * @return count
     */
    int delete(String id);

    /**
     * @Description 删除一条数据，可选择删除、保留文件
     * @param id 文件上传实体类id
     * @param deleteFile 是否删除文件
     * @return count
     */
    int delete(String id, boolean deleteFile);

    /**
     * @Description 根据id获得文件上传实体类
     * @param id 文件上传实体类id
     * @return upload
     */
    CommonFileUpload getById(String id);

    /**
     * @Description 根据关联id获得多个文件上传实体类
     * @param mainId 关联id
     * @return list
     */
    List<CommonFileUpload> getByMainId(String mainId);

    /**
     * @Description 根据关联id获得多个文件上传实体类
     * @param mainIdList 关联id集合
     * @return list
     */
    List<CommonFileUpload> getByMainId(List<String> mainIdList);

    /**
     * 获得文件真实根路径
     * @return 真实根路径，如：D:/upload
     */
    String getCommonRealRootPath();

    /**
     * 获得完整的相对路径
     * @param uploadPath 相对项目路径
     * @param appendDate 是否在路径后再拼接日期(格式：相对路径/yyyy-MM-dd)
     * @return uploadPath
     */
    String getCommonUploadPath(String uploadPath, boolean appendDate);

    /**
     * 单文件上传
     * @param mainId 关联主键id
     * @param file 文件实体
     * @param uploadPath 上传的路径
     * @return result
     */
    Map<String, Object> uploadSingleFile(String mainId, MultipartFile file, String uploadPath);

    /**
     * app单文件上传,返回路径
     * @param mainId 关联主键id
     * @param file 文件实体
     * @param uploadPath 上传的路径
     * @return result
     */
    Map<String, Object> uploadSingleFileByApp(String mainId, MultipartFile file, String uploadPath);

    /**
     * 根据mainId删除附件
     * @param deleteWithFile 删除时删除关联文件
     * @param mainId 关联id
     * @param fileIdList 附件id，如果为空，则删除所有
     * @return count
     */
    int deleteByMainId(boolean deleteWithFile, String mainId, List<String> fileIdList);

    /**
     * 判断文件是否存在
     * @param fileUpload 文件实体
     * @return true存在 false不存在
     */
    boolean isFileExist(CommonFileUpload fileUpload);

    /**
     * 获得文件的真实完整路径
     * @param fileUpload 文件实体
     * @return 真实路径
     */
    String getRealFullFilePath(CommonFileUpload fileUpload);

    /**
     * 下载单个文件
     * @param request request
     * @param response response
     * @param fileId 文件id
     */
    void downloadSingleFile(HttpServletRequest request, HttpServletResponse response, String fileId);

    /**
     * 预览单个文件
     * @param request request
     * @param response response
     * @param fileId 文件id
     */
    Map<String,Object> previewSingleFile(HttpServletRequest request, HttpServletResponse response, String fileId);

    /**
     * 复制文件存储到临时文件
     * */
    Map<String,Object> copyToTemporaryFile(HttpServletRequest request, HttpServletResponse response, String mainId);

    /**
     * 下载单个文件（只适用于一个mainId对应一个文件）
     * @param request request
     * @param response response
     * @param mainId 文件id
     */
    void downloadSingleFileByMainId(HttpServletRequest request, HttpServletResponse response, String mainId);

    /**
     * 打包多个文件
     * @param mainId 关联主键id
     * @return result
     */
    Map<String, Object> packageByMainId(String mainId);

    /**
     * 更新mainId，多用于新增，没有预先生成id，保存后再更新正确的id
     * @param oldMainId 原先自动生成的id
     * @param newMainId 新关联的id
     * @return count
     */
    int updateMainId(String oldMainId, String newMainId);
}
