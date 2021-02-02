package com.example.demo.modules.interf.model;

import com.example.demo.core.entity.BaseEntity;

import javax.persistence.*;

@Table(name = "sys_interface")
public class SysInterface extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 接口名称
     */
    @Column(name = "interface_Name")
    private String interfaceName;

    /**
     * 接口链接
     */
    @Column(name = "interface_Url")
    private String interfaceUrl;

    /**
     * 分组
     */
    @Column(name = "interface_group")
    private String interfaceGroup;

    /**
     * 排序
     */
    @Column(name = "interface_sort")
    private Integer interfaceSort;

    /**
     * 备注
     */
    private String description;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取接口名称
     *
     * @return interface_Name - 接口名称
     */
    public String getInterfaceName() {
        return interfaceName;
    }

    /**
     * 设置接口名称
     *
     * @param interfaceName 接口名称
     */
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    /**
     * 获取接口链接
     *
     * @return interface_Url - 接口链接
     */
    public String getInterfaceUrl() {
        return interfaceUrl;
    }

    /**
     * 设置接口链接
     *
     * @param interfaceUrl 接口链接
     */
    public void setInterfaceUrl(String interfaceUrl) {
        this.interfaceUrl = interfaceUrl;
    }

    /**
     * 获取分组
     *
     * @return interface_group - 分组
     */
    public String getInterfaceGroup() {
        return interfaceGroup;
    }

    /**
     * 设置分组
     *
     * @param interfaceGroup 分组
     */
    public void setInterfaceGroup(String interfaceGroup) {
        this.interfaceGroup = interfaceGroup;
    }

    /**
     * 获取排序
     *
     * @return interface_sort - 排序
     */
    public Integer getInterfaceSort() {
        return interfaceSort;
    }

    /**
     * 设置排序
     *
     * @param interfaceSort 排序
     */
    public void setInterfaceSort(Integer interfaceSort) {
        this.interfaceSort = interfaceSort;
    }

    /**
     * 获取备注
     *
     * @return description - 备注
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置备注
     *
     * @param description 备注
     */
    public void setDescription(String description) {
        this.description = description;
    }
}