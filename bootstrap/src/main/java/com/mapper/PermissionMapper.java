package com.mapper;

import com.entity.SysPermission;

import java.util.List;

/**
 * @description: 权限
 * @author: Mr.Wan
 * @create: 2021-01-26 16:09
 **/
public interface PermissionMapper {

    /**
     * 查询全部权限
     * @return
     */
    List<SysPermission> findAll();

    /**
     * 根据用户id查询出用户的所有权限
     * @param userId
     * @return
     */
    List<SysPermission> findByAdminUserId(String userId);

    /**
     * 根据角色id查询权限
     * @param roleid
     * @return
     */
    List<SysPermission> findRoleId(String roleid);

}
