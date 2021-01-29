package com.mapper;

import com.entity.SysRole;

import java.util.List;

/**
 * @description: 角色
 * @author: Mr.Wan
 * @create: 2021-01-26 16:10
 **/
public interface RoleMapper {

    /**
     * 根据用户id查询角色
     * @param userid
     * @return
     */
    public List<SysRole> findUserRole(String userid);

}
