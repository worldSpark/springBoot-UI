package com.mapper;

import com.entity.SysUser;

import java.util.List;

/**
 * @description: 用户
 * @author: Mr.Wan
 * @create: 2021-01-26 16:09
 **/
public interface SysUserMapper {

    /**
     * 根据用户名字查询用户
     * @param username
     * @return
     */
    public SysUser findUserName(String username);


    /**
     * 查询用户详情
     * String name 如果没用 注解@Param("") 它到mapper里面为_parameter
     * @return
     * @author fuce
     * @Date 2020年12月6日 下午9:02:20
     */
    public List<SysUser> findUserInfo(String username);

}
