

package com.example.demo.core.entity;

import javax.persistence.Transient;

/**
 * 基础信息
 *
 * @author liuzh
 * @since 2016-01-31 21:42
 */
public class BaseEntity {
    /*@Id
    @Column(name = "Id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;*/

    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 10;

    /*public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
