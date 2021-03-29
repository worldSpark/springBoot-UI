package com.fc.model.template;

import com.fc.annotation.Column;
import com.fc.annotation.FieldName;
import com.fc.annotation.ModelName;
import com.fc.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * @Description 通用文件上传实体类
 * @author luoy
 * @Date 2019年8月10日 13:25:13
 * @version 1.0.0
 * */
@Data
@ModelName(name = "通用文件上传")
@Table(name="wp_common_file_upload")
public class CommonFileUpload {

    @FieldName(name = "主键")
    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, isKey = true)
    private String id;

    @FieldName(name = "关联id")
    @Column(name = "main_id", type = MySqlTypeConstant.VARCHAR, length = 8000)
    private String mainId;

    @FieldName(name = "用户id")
    @Column(name = "pk_wp_user", type = MySqlTypeConstant.VARCHAR,  length = 50)
    private String pkWpUser;

    @FieldName(name = "文件名称（文件上传时的名称），包括后缀名称")
    @Column(name = "original_file_name", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String originalFileName;

    @FieldName(name = "文件名称（保存在服务器的名称），包括后缀名称")
    @Column(name = "real_file_name", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String realFileName;

    @FieldName(name = "文件相对路径")
    @Column(name = "file_path", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String filePath;

    // 如：doc、pdf等，全小写，不带"."
    @FieldName(name = "文件类型")
    @Column(name = "file_type", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String fileType;

    @FieldName(name = "文件大小（KB）")
    @Column(name = "file_size", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String fileSize;

    // yyyy-MM-dd HH:mm:ss
    @FieldName(name = "上传时间")
    @Column(name = "create_time", type = MySqlTypeConstant.VARCHAR, length = 50)
    private String createTime;

    // 是、否
    @FieldName(name = "是否存在")
    @Column(name = "is_exist", type = MySqlTypeConstant.VARCHAR, length = 50)
    private String isExist;
}
