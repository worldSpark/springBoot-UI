package com.example.demo.modules.bsc.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "bsc_att_detail")
public class BscAttDetail {
    /**
     * 主健
     */
    @Id
    @Column(name = "DETAIL_ID")
    private String detailId;

    /**
     * 附件编号
     */
    @Column(name = "ATT_CODE")
    private String attCode;

    /**
     * 附件名称
     */
    @Column(name = "ATT_NAME")
    private String attName;

    /**
     * 附件大小。以字节为单位。
     */
    @Column(name = "ATT_SIZE")
    private Double attSize;

    /**
     * 附件格式
     */
    @Column(name = "ATT_FORMAT")
    private String attFormat;

    /**
     * 所属顶级组织ID
     */
    @Column(name = "ORG_ID")
    private String orgId;

    /**
     * 文件存储类型，包括mongodb、minio、windows_disk、linux_disk等，来自于数据字典
     */
    @Column(name = "STORE_TYPE")
    private String storeType;

    /**
     * 是否相对路径，适用于文件系统存储（如disk、minio），支持相对和绝对路径【适用于文件系统】
     */
    @Column(name = "IS_RELATIVE_PATH")
    private String isRelativePath;

    /**
     * 附件文件路径，适用于文件系统存储（如disk、minio），支持相对和绝对路径【适用于文件系统】
     */
    @Column(name = "ATT_PATH")
    private String attPath;

    /**
     * 附件存在文件系统上的名称【适用于文件系统】
     */
    @Column(name = "ATT_DISK_NAME")
    private String attDiskName;

    /**
     * 附件对象ID【适用于非文件系统】
     */
    @Column(name = "OBJECT_ID")
    private String objectId;

    /**
     * 桶名称【适用于分布式对象文件系统】
     */
    @Column(name = "BUCKET_NAME")
    private String bucketName;

    /**
     * 文件来源
     */
    @Column(name = "ATT_SOURCE")
    private String attSource;

    /**
     * 是否加密存储。0表示非加密存储，1表示加密存储。
     */
    @Column(name = "IS_ENCRYPT")
    private String isEncrypt;

    /**
     * 创建人姓名
     */
    @Column(name = "CREATER")
    private String creater;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 修改人姓名
     */
    @Column(name = "MODIFIER")
    private String modifier;

    /**
     * 修改时间
     */
    @Column(name = "MODIFY_TIME")
    private Date modifyTime;

    /**
     * 加解密类名
     */
    @Column(name = "ENCRYPT_CLASS")
    private String encryptClass;

    /**
     * 所属目录ID
     */
    @Column(name = "DIR_ID")
    private String dirId;

    /**
     * 是否加盖签章（名）
     */
    @Column(name = "IS_SIGN")
    private String isSign;

    /**
     * 是否需要转换PDF，0表示不是，1表示是
     */
    @Column(name = "IS_NEED_PDF")
    private String isNeedPdf;

    /**
     * 转换PDF状态，0表示没有转，1表示已转换，2表示转换中
     */
    @Column(name = "IS_CONVERT_PDF")
    private String isConvertPdf;

    /**
     * PDF转换结果，0表示转换失败，1表示转换成功
     */
    @Column(name = "CONVERT_PDF_RESULT")
    private String convertPdfResult;

    /**
     * PDF转换时间
     */
    @Column(name = "CONVERT_PDF_TIME")
    private Date convertPdfTime;

    /**
     * 是否使用版本管理，0表示无版本管理，1表示有版本管理
     */
    @Column(name = "IS_VERSION_MGMT")
    private String isVersionMgmt;

    /**
     * 获取主健
     *
     * @return DETAIL_ID - 主健
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * 设置主健
     *
     * @param detailId 主健
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    /**
     * 获取附件编号
     *
     * @return ATT_CODE - 附件编号
     */
    public String getAttCode() {
        return attCode;
    }

    /**
     * 设置附件编号
     *
     * @param attCode 附件编号
     */
    public void setAttCode(String attCode) {
        this.attCode = attCode;
    }

    /**
     * 获取附件名称
     *
     * @return ATT_NAME - 附件名称
     */
    public String getAttName() {
        return attName;
    }

    /**
     * 设置附件名称
     *
     * @param attName 附件名称
     */
    public void setAttName(String attName) {
        this.attName = attName;
    }

    /**
     * 获取附件大小。以字节为单位。
     *
     * @return ATT_SIZE - 附件大小。以字节为单位。
     */
    public Double getAttSize() {
        return attSize;
    }

    /**
     * 设置附件大小。以字节为单位。
     *
     * @param attSize 附件大小。以字节为单位。
     */
    public void setAttSize(Double attSize) {
        this.attSize = attSize;
    }

    /**
     * 获取附件格式
     *
     * @return ATT_FORMAT - 附件格式
     */
    public String getAttFormat() {
        return attFormat;
    }

    /**
     * 设置附件格式
     *
     * @param attFormat 附件格式
     */
    public void setAttFormat(String attFormat) {
        this.attFormat = attFormat;
    }

    /**
     * 获取所属顶级组织ID
     *
     * @return ORG_ID - 所属顶级组织ID
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 设置所属顶级组织ID
     *
     * @param orgId 所属顶级组织ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取文件存储类型，包括mongodb、minio、windows_disk、linux_disk等，来自于数据字典
     *
     * @return STORE_TYPE - 文件存储类型，包括mongodb、minio、windows_disk、linux_disk等，来自于数据字典
     */
    public String getStoreType() {
        return storeType;
    }

    /**
     * 设置文件存储类型，包括mongodb、minio、windows_disk、linux_disk等，来自于数据字典
     *
     * @param storeType 文件存储类型，包括mongodb、minio、windows_disk、linux_disk等，来自于数据字典
     */
    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    /**
     * 获取是否相对路径，适用于文件系统存储（如disk、minio），支持相对和绝对路径【适用于文件系统】
     *
     * @return IS_RELATIVE_PATH - 是否相对路径，适用于文件系统存储（如disk、minio），支持相对和绝对路径【适用于文件系统】
     */
    public String getIsRelativePath() {
        return isRelativePath;
    }

    /**
     * 设置是否相对路径，适用于文件系统存储（如disk、minio），支持相对和绝对路径【适用于文件系统】
     *
     * @param isRelativePath 是否相对路径，适用于文件系统存储（如disk、minio），支持相对和绝对路径【适用于文件系统】
     */
    public void setIsRelativePath(String isRelativePath) {
        this.isRelativePath = isRelativePath;
    }

    /**
     * 获取附件文件路径，适用于文件系统存储（如disk、minio），支持相对和绝对路径【适用于文件系统】
     *
     * @return ATT_PATH - 附件文件路径，适用于文件系统存储（如disk、minio），支持相对和绝对路径【适用于文件系统】
     */
    public String getAttPath() {
        return attPath;
    }

    /**
     * 设置附件文件路径，适用于文件系统存储（如disk、minio），支持相对和绝对路径【适用于文件系统】
     *
     * @param attPath 附件文件路径，适用于文件系统存储（如disk、minio），支持相对和绝对路径【适用于文件系统】
     */
    public void setAttPath(String attPath) {
        this.attPath = attPath;
    }

    /**
     * 获取附件存在文件系统上的名称【适用于文件系统】
     *
     * @return ATT_DISK_NAME - 附件存在文件系统上的名称【适用于文件系统】
     */
    public String getAttDiskName() {
        return attDiskName;
    }

    /**
     * 设置附件存在文件系统上的名称【适用于文件系统】
     *
     * @param attDiskName 附件存在文件系统上的名称【适用于文件系统】
     */
    public void setAttDiskName(String attDiskName) {
        this.attDiskName = attDiskName;
    }

    /**
     * 获取附件对象ID【适用于非文件系统】
     *
     * @return OBJECT_ID - 附件对象ID【适用于非文件系统】
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * 设置附件对象ID【适用于非文件系统】
     *
     * @param objectId 附件对象ID【适用于非文件系统】
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * 获取桶名称【适用于分布式对象文件系统】
     *
     * @return BUCKET_NAME - 桶名称【适用于分布式对象文件系统】
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * 设置桶名称【适用于分布式对象文件系统】
     *
     * @param bucketName 桶名称【适用于分布式对象文件系统】
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * 获取文件来源
     *
     * @return ATT_SOURCE - 文件来源
     */
    public String getAttSource() {
        return attSource;
    }

    /**
     * 设置文件来源
     *
     * @param attSource 文件来源
     */
    public void setAttSource(String attSource) {
        this.attSource = attSource;
    }

    /**
     * 获取是否加密存储。0表示非加密存储，1表示加密存储。
     *
     * @return IS_ENCRYPT - 是否加密存储。0表示非加密存储，1表示加密存储。
     */
    public String getIsEncrypt() {
        return isEncrypt;
    }

    /**
     * 设置是否加密存储。0表示非加密存储，1表示加密存储。
     *
     * @param isEncrypt 是否加密存储。0表示非加密存储，1表示加密存储。
     */
    public void setIsEncrypt(String isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    /**
     * 获取创建人姓名
     *
     * @return CREATER - 创建人姓名
     */
    public String getCreater() {
        return creater;
    }

    /**
     * 设置创建人姓名
     *
     * @param creater 创建人姓名
     */
    public void setCreater(String creater) {
        this.creater = creater;
    }

    /**
     * 获取创建时间
     *
     * @return CREATE_TIME - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改人姓名
     *
     * @return MODIFIER - 修改人姓名
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置修改人姓名
     *
     * @param modifier 修改人姓名
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * 获取修改时间
     *
     * @return MODIFY_TIME - 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取加解密类名
     *
     * @return ENCRYPT_CLASS - 加解密类名
     */
    public String getEncryptClass() {
        return encryptClass;
    }

    /**
     * 设置加解密类名
     *
     * @param encryptClass 加解密类名
     */
    public void setEncryptClass(String encryptClass) {
        this.encryptClass = encryptClass;
    }

    /**
     * 获取所属目录ID
     *
     * @return DIR_ID - 所属目录ID
     */
    public String getDirId() {
        return dirId;
    }

    /**
     * 设置所属目录ID
     *
     * @param dirId 所属目录ID
     */
    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

    /**
     * 获取是否加盖签章（名）
     *
     * @return IS_SIGN - 是否加盖签章（名）
     */
    public String getIsSign() {
        return isSign;
    }

    /**
     * 设置是否加盖签章（名）
     *
     * @param isSign 是否加盖签章（名）
     */
    public void setIsSign(String isSign) {
        this.isSign = isSign;
    }

    /**
     * 获取是否需要转换PDF，0表示不是，1表示是
     *
     * @return IS_NEED_PDF - 是否需要转换PDF，0表示不是，1表示是
     */
    public String getIsNeedPdf() {
        return isNeedPdf;
    }

    /**
     * 设置是否需要转换PDF，0表示不是，1表示是
     *
     * @param isNeedPdf 是否需要转换PDF，0表示不是，1表示是
     */
    public void setIsNeedPdf(String isNeedPdf) {
        this.isNeedPdf = isNeedPdf;
    }

    /**
     * 获取转换PDF状态，0表示没有转，1表示已转换，2表示转换中
     *
     * @return IS_CONVERT_PDF - 转换PDF状态，0表示没有转，1表示已转换，2表示转换中
     */
    public String getIsConvertPdf() {
        return isConvertPdf;
    }

    /**
     * 设置转换PDF状态，0表示没有转，1表示已转换，2表示转换中
     *
     * @param isConvertPdf 转换PDF状态，0表示没有转，1表示已转换，2表示转换中
     */
    public void setIsConvertPdf(String isConvertPdf) {
        this.isConvertPdf = isConvertPdf;
    }

    /**
     * 获取PDF转换结果，0表示转换失败，1表示转换成功
     *
     * @return CONVERT_PDF_RESULT - PDF转换结果，0表示转换失败，1表示转换成功
     */
    public String getConvertPdfResult() {
        return convertPdfResult;
    }

    /**
     * 设置PDF转换结果，0表示转换失败，1表示转换成功
     *
     * @param convertPdfResult PDF转换结果，0表示转换失败，1表示转换成功
     */
    public void setConvertPdfResult(String convertPdfResult) {
        this.convertPdfResult = convertPdfResult;
    }

    /**
     * 获取PDF转换时间
     *
     * @return CONVERT_PDF_TIME - PDF转换时间
     */
    public Date getConvertPdfTime() {
        return convertPdfTime;
    }

    /**
     * 设置PDF转换时间
     *
     * @param convertPdfTime PDF转换时间
     */
    public void setConvertPdfTime(Date convertPdfTime) {
        this.convertPdfTime = convertPdfTime;
    }

    /**
     * 获取是否使用版本管理，0表示无版本管理，1表示有版本管理
     *
     * @return IS_VERSION_MGMT - 是否使用版本管理，0表示无版本管理，1表示有版本管理
     */
    public String getIsVersionMgmt() {
        return isVersionMgmt;
    }

    /**
     * 设置是否使用版本管理，0表示无版本管理，1表示有版本管理
     *
     * @param isVersionMgmt 是否使用版本管理，0表示无版本管理，1表示有版本管理
     */
    public void setIsVersionMgmt(String isVersionMgmt) {
        this.isVersionMgmt = isVersionMgmt;
    }
}