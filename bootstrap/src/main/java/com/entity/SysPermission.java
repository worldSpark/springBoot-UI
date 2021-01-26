package com.entity;

import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * @description: 权限
 * @author: Mr.Wan
 * @create: 2021-01-26 16:11
 **/
@Data
//@TableName(value = "t_sys_permission")
public class SysPermission {
    private String id;

    private String name;

    private String descripion;

    private String url;

    private Integer isBlank;

    private String pid;

    private String perms;

    private Integer type;

    private String icon;

    private Integer orderNum;

    private Integer visible;

    private Integer childCount;
}
