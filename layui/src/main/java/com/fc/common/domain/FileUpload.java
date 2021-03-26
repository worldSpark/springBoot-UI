package com.fc.common.domain;

import com.fc.annotation.Column;
import com.fc.annotation.FieldName;
import com.fc.annotation.ModelName;
import com.fc.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 系统附件信息表实体类
 * @author chencc
 * @Date 2015年05月19日 上午09:35:00
 * @version 1.0.0
 * */
@Data
@ModelName(name = "系统附件信息")
@Table(name="wp_file_upload")
public class FileUpload implements Serializable{

        @FieldName(name = "主键")
        @Column(name = "id", type = MySqlTypeConstant.VARCHAR, isKey = true)
        private String id;                            //主键

        @FieldName(name = "其他主键id")
        @Column(name = "main_id", type = MySqlTypeConstant.VARCHAR, length = 4000)
        private String mainId;                       //其他主键id

        @FieldName(name = "附件名称")
        @Column(name = "attachment_name", type = MySqlTypeConstant.VARCHAR, length = 4000)
        private String attachmentName;                //附件名称

        @FieldName(name = "附件URL地址")
        @Column(name = "attachment_url", type = MySqlTypeConstant.VARCHAR, length = 4000)
        private String attachmentUrl;               //附件URL地址

        @FieldName(name = "上传时间")
        @Column(name = "upload_time", type = MySqlTypeConstant.VARCHAR, length = 50)
        private String uploadTime;                  //上传时间 yyyy-MM-dd hh:mm:ss

        @FieldName(name = "上传人")
        @Column(name = "uploader", type = MySqlTypeConstant.VARCHAR,  length = 50)
        private String uploader;//上传人

        @FieldName(name = "是否可见")
        @Column(name = "is_open", type = MySqlTypeConstant.VARCHAR,  length = 50)
        private String isOpen;//是否可见

}
