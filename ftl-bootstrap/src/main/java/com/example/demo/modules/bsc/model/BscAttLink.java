package com.example.demo.modules.bsc.model;

import javax.persistence.*;

@Table(name = "bsc_att_link")
public class BscAttLink {
    /**
     * 主键
     */
    @Id
    @Column(name = "LINK_ID")
    private String linkId;

    /**
     * 业务表名。如：NOTICE或ads.NOTICE。存在多个业务表时只需指定主业务表名即可。
     */
    @Column(name = "TABLE_NAME")
    private String tableName;

    /**
     * 业务表主键字段名
     */
    @Column(name = "PK_NAME")
    private String pkName;

    /**
     * 业务记录ID
     */
    @Column(name = "RECORD_ID")
    private String recordId;

    /**
     * 附件文件记录ID
     */
    @Column(name = "DETAIL_ID")
    private String detailId;

    /**
     * 附件文件夹ID
     */
    @Column(name = "DIR_ID")
    private String dirId;

    /**
     * 连接类型，a表示附件，d代表文件夹
     */
    @Column(name = "LINK_TYPE")
    private String linkType;

    /**
     * 获取主键
     *
     * @return LINK_ID - 主键
     */
    public String getLinkId() {
        return linkId;
    }

    /**
     * 设置主键
     *
     * @param linkId 主键
     */
    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    /**
     * 获取业务表名。如：NOTICE或ads.NOTICE。存在多个业务表时只需指定主业务表名即可。
     *
     * @return TABLE_NAME - 业务表名。如：NOTICE或ads.NOTICE。存在多个业务表时只需指定主业务表名即可。
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 设置业务表名。如：NOTICE或ads.NOTICE。存在多个业务表时只需指定主业务表名即可。
     *
     * @param tableName 业务表名。如：NOTICE或ads.NOTICE。存在多个业务表时只需指定主业务表名即可。
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 获取业务表主键字段名
     *
     * @return PK_NAME - 业务表主键字段名
     */
    public String getPkName() {
        return pkName;
    }

    /**
     * 设置业务表主键字段名
     *
     * @param pkName 业务表主键字段名
     */
    public void setPkName(String pkName) {
        this.pkName = pkName;
    }

    /**
     * 获取业务记录ID
     *
     * @return RECORD_ID - 业务记录ID
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * 设置业务记录ID
     *
     * @param recordId 业务记录ID
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    /**
     * 获取附件文件记录ID
     *
     * @return DETAIL_ID - 附件文件记录ID
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * 设置附件文件记录ID
     *
     * @param detailId 附件文件记录ID
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    /**
     * 获取附件文件夹ID
     *
     * @return DIR_ID - 附件文件夹ID
     */
    public String getDirId() {
        return dirId;
    }

    /**
     * 设置附件文件夹ID
     *
     * @param dirId 附件文件夹ID
     */
    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

    /**
     * 获取连接类型，a表示附件，d代表文件夹
     *
     * @return LINK_TYPE - 连接类型，a表示附件，d代表文件夹
     */
    public String getLinkType() {
        return linkType;
    }

    /**
     * 设置连接类型，a表示附件，d代表文件夹
     *
     * @param linkType 连接类型，a表示附件，d代表文件夹
     */
    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }
}