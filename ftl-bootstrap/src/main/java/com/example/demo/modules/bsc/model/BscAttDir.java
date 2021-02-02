package com.example.demo.modules.bsc.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "bsc_att_dir")
public class BscAttDir {
    /**
     * 主键
     */
    @Id
    @Column(name = "DIR_ID")
    private String dirId;

    /**
     * 所属顶级组织ID
     */
    @Column(name = "ORG_ID")
    private String orgId;

    /**
     * 是否根文件夹，0表示普通文件夹，1表示根文件夹
     */
    @Column(name = "IS_ROOT")
    private String isRoot;

    /**
     * 文件夹编号
     */
    @Column(name = "DIR_CODE")
    private String dirCode;

    /**
     * 文件夹名称
     */
    @Column(name = "DIR_NAME")
    private String dirName;

    /**
     * 父文件夹ID
     */
    @Column(name = "PARENT_ID")
    private String parentId;

    /**
     * 文件夹序列
     */
    @Column(name = "DIR_SEQ")
    private String dirSeq;

    /**
     * 备注说明
     */
    @Column(name = "DIR_MEMO")
    private String dirMemo;

    /**
     * 目录级别(0为虚拟机业务域、1为业务模块对象、2为对象分类属性、3为业务对象主键、4为业务对象下用户自定义目录)
     */
    @Column(name = "DIR_LEVEL")
    private String dirLevel;

    /**
     * 数据创建人
     */
    @Column(name = "CREATER")
    private String creater;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 修改人
     */
    @Column(name = "MODIFIER")
    private String modifier;

    /**
     * 修改时间
     */
    @Column(name = "MODIFY_TIME")
    private Date modifyTime;

    /**
     * 目录权限(是否采用777方式还没确定,备用字段)
     */
    @Column(name = "DIR_PRIVILEGE")
    private String dirPrivilege;

    /**
     * 文件夹名称序列
     */
    @Column(name = "DIR_NAME_SEQ")
    private String dirNameSeq;

    /**
     * 文件存储类型，包括mongodb、hdfs、windows_disk、linux_disk等，来自于数据字典
     */
    @Column(name = "STORE_TYPE")
    private String storeType;

    @Column(name = "STORE_URL")
    private String storeUrl;

    /**
     * 获取主键
     *
     * @return DIR_ID - 主键
     */
    public String getDirId() {
        return dirId;
    }

    /**
     * 设置主键
     *
     * @param dirId 主键
     */
    public void setDirId(String dirId) {
        this.dirId = dirId;
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
     * 获取是否根文件夹，0表示普通文件夹，1表示根文件夹
     *
     * @return IS_ROOT - 是否根文件夹，0表示普通文件夹，1表示根文件夹
     */
    public String getIsRoot() {
        return isRoot;
    }

    /**
     * 设置是否根文件夹，0表示普通文件夹，1表示根文件夹
     *
     * @param isRoot 是否根文件夹，0表示普通文件夹，1表示根文件夹
     */
    public void setIsRoot(String isRoot) {
        this.isRoot = isRoot;
    }

    /**
     * 获取文件夹编号
     *
     * @return DIR_CODE - 文件夹编号
     */
    public String getDirCode() {
        return dirCode;
    }

    /**
     * 设置文件夹编号
     *
     * @param dirCode 文件夹编号
     */
    public void setDirCode(String dirCode) {
        this.dirCode = dirCode;
    }

    /**
     * 获取文件夹名称
     *
     * @return DIR_NAME - 文件夹名称
     */
    public String getDirName() {
        return dirName;
    }

    /**
     * 设置文件夹名称
     *
     * @param dirName 文件夹名称
     */
    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    /**
     * 获取父文件夹ID
     *
     * @return PARENT_ID - 父文件夹ID
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置父文件夹ID
     *
     * @param parentId 父文件夹ID
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取文件夹序列
     *
     * @return DIR_SEQ - 文件夹序列
     */
    public String getDirSeq() {
        return dirSeq;
    }

    /**
     * 设置文件夹序列
     *
     * @param dirSeq 文件夹序列
     */
    public void setDirSeq(String dirSeq) {
        this.dirSeq = dirSeq;
    }

    /**
     * 获取备注说明
     *
     * @return DIR_MEMO - 备注说明
     */
    public String getDirMemo() {
        return dirMemo;
    }

    /**
     * 设置备注说明
     *
     * @param dirMemo 备注说明
     */
    public void setDirMemo(String dirMemo) {
        this.dirMemo = dirMemo;
    }

    /**
     * 获取目录级别(0为虚拟机业务域、1为业务模块对象、2为对象分类属性、3为业务对象主键、4为业务对象下用户自定义目录)
     *
     * @return DIR_LEVEL - 目录级别(0为虚拟机业务域、1为业务模块对象、2为对象分类属性、3为业务对象主键、4为业务对象下用户自定义目录)
     */
    public String getDirLevel() {
        return dirLevel;
    }

    /**
     * 设置目录级别(0为虚拟机业务域、1为业务模块对象、2为对象分类属性、3为业务对象主键、4为业务对象下用户自定义目录)
     *
     * @param dirLevel 目录级别(0为虚拟机业务域、1为业务模块对象、2为对象分类属性、3为业务对象主键、4为业务对象下用户自定义目录)
     */
    public void setDirLevel(String dirLevel) {
        this.dirLevel = dirLevel;
    }

    /**
     * 获取数据创建人
     *
     * @return CREATER - 数据创建人
     */
    public String getCreater() {
        return creater;
    }

    /**
     * 设置数据创建人
     *
     * @param creater 数据创建人
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
     * 获取修改人
     *
     * @return MODIFIER - 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置修改人
     *
     * @param modifier 修改人
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
     * 获取目录权限(是否采用777方式还没确定,备用字段)
     *
     * @return DIR_PRIVILEGE - 目录权限(是否采用777方式还没确定,备用字段)
     */
    public String getDirPrivilege() {
        return dirPrivilege;
    }

    /**
     * 设置目录权限(是否采用777方式还没确定,备用字段)
     *
     * @param dirPrivilege 目录权限(是否采用777方式还没确定,备用字段)
     */
    public void setDirPrivilege(String dirPrivilege) {
        this.dirPrivilege = dirPrivilege;
    }

    /**
     * 获取文件夹名称序列
     *
     * @return DIR_NAME_SEQ - 文件夹名称序列
     */
    public String getDirNameSeq() {
        return dirNameSeq;
    }

    /**
     * 设置文件夹名称序列
     *
     * @param dirNameSeq 文件夹名称序列
     */
    public void setDirNameSeq(String dirNameSeq) {
        this.dirNameSeq = dirNameSeq;
    }

    /**
     * 获取文件存储类型，包括mongodb、hdfs、windows_disk、linux_disk等，来自于数据字典
     *
     * @return STORE_TYPE - 文件存储类型，包括mongodb、hdfs、windows_disk、linux_disk等，来自于数据字典
     */
    public String getStoreType() {
        return storeType;
    }

    /**
     * 设置文件存储类型，包括mongodb、hdfs、windows_disk、linux_disk等，来自于数据字典
     *
     * @param storeType 文件存储类型，包括mongodb、hdfs、windows_disk、linux_disk等，来自于数据字典
     */
    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    /**
     * @return STORE_URL
     */
    public String getStoreUrl() {
        return storeUrl;
    }

    /**
     * @param storeUrl
     */
    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }
}