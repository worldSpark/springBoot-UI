package com.example.demo.modules.bsc.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "bsc_att_store_config")
public class BscAttStoreConfig {
    /**
     * 主键
     */
    @Id
    @Column(name = "STORE_CONFIG_ID")
    private String storeConfigId;

    /**
     * 根目录（文件库）ID
     */
    @Column(name = "ROOT_DIR_ID")
    private String rootDirId;

    /**
     * 配置键
     */
    @Column(name = "STORE_KEY")
    private String storeKey;

    /**
     * 配置键描述
     */
    @Column(name = "STORE_DESC")
    private String storeDesc;

    /**
     * 配置值
     */
    @Column(name = "STORE_VALUE")
    private String storeValue;

    /**
     * 创建人
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
     * 获取主键
     *
     * @return STORE_CONFIG_ID - 主键
     */
    public String getStoreConfigId() {
        return storeConfigId;
    }

    /**
     * 设置主键
     *
     * @param storeConfigId 主键
     */
    public void setStoreConfigId(String storeConfigId) {
        this.storeConfigId = storeConfigId;
    }

    /**
     * 获取根目录（文件库）ID
     *
     * @return ROOT_DIR_ID - 根目录（文件库）ID
     */
    public String getRootDirId() {
        return rootDirId;
    }

    /**
     * 设置根目录（文件库）ID
     *
     * @param rootDirId 根目录（文件库）ID
     */
    public void setRootDirId(String rootDirId) {
        this.rootDirId = rootDirId;
    }

    /**
     * 获取配置键
     *
     * @return STORE_KEY - 配置键
     */
    public String getStoreKey() {
        return storeKey;
    }

    /**
     * 设置配置键
     *
     * @param storeKey 配置键
     */
    public void setStoreKey(String storeKey) {
        this.storeKey = storeKey;
    }

    /**
     * 获取配置键描述
     *
     * @return STORE_DESC - 配置键描述
     */
    public String getStoreDesc() {
        return storeDesc;
    }

    /**
     * 设置配置键描述
     *
     * @param storeDesc 配置键描述
     */
    public void setStoreDesc(String storeDesc) {
        this.storeDesc = storeDesc;
    }

    /**
     * 获取配置值
     *
     * @return STORE_VALUE - 配置值
     */
    public String getStoreValue() {
        return storeValue;
    }

    /**
     * 设置配置值
     *
     * @param storeValue 配置值
     */
    public void setStoreValue(String storeValue) {
        this.storeValue = storeValue;
    }

    /**
     * 获取创建人
     *
     * @return CREATER - 创建人
     */
    public String getCreater() {
        return creater;
    }

    /**
     * 设置创建人
     *
     * @param creater 创建人
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
}