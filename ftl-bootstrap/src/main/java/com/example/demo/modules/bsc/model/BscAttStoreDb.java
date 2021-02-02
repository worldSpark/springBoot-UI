package com.example.demo.modules.bsc.model;

import javax.persistence.*;

@Table(name = "bsc_att_store_db")
public class BscAttStoreDb {
    /**
     * 主键
     */
    @Id
    @Column(name = "STORE_DB_ID")
    private String storeDbId;

    /**
     * 文件记录ID
     */
    @Column(name = "DETAIL_ID")
    private String detailId;

    /**
     * 文件内容
     */
    @Column(name = "ATT_CONTENT")
    private byte[] attContent;

    /**
     * 获取主键
     *
     * @return STORE_DB_ID - 主键
     */
    public String getStoreDbId() {
        return storeDbId;
    }

    /**
     * 设置主键
     *
     * @param storeDbId 主键
     */
    public void setStoreDbId(String storeDbId) {
        this.storeDbId = storeDbId;
    }

    /**
     * 获取文件记录ID
     *
     * @return DETAIL_ID - 文件记录ID
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * 设置文件记录ID
     *
     * @param detailId 文件记录ID
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    /**
     * 获取文件内容
     *
     * @return ATT_CONTENT - 文件内容
     */
    public byte[] getAttContent() {
        return attContent;
    }

    /**
     * 设置文件内容
     *
     * @param attContent 文件内容
     */
    public void setAttContent(byte[] attContent) {
        this.attContent = attContent;
    }
}