package com.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 用户
 * @author: Mr.Wan
 * @create: 2021-01-26 16:10
 **/
@Data
//@TableName(value = "t_sys_user")
public class SysUser {

    /** 主键 **/
    @ApiModelProperty(value = "主键")
    private String id;

    /** 用户账号 **/
    @ApiModelProperty(value = "用户账号")
    private String username;

    /** 用户密码 **/
    @ApiModelProperty(value = "用户密码")
    private String password;

    /** 昵称 **/
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /** 部门id **/
    @ApiModelProperty(value = "部门id")
    private Integer depId;

    /** 岗位id **/
    @ApiModelProperty(value = "岗位id")
    private String posId;

    /**部门名称**/
    private String depName;
    /**岗位名称**/
    private String posName;
}
