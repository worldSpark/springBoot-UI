package com.example.demo.modules.sys.service;


import com.example.demo.core.entity.ProcessResult;
import com.example.demo.modules.sys.model.SysUser;

import java.util.List;

public interface UserService {
    List<SysUser> getAll(SysUser sysUser,String keyword);

    SysUser getById(Integer id);

    int deleteById(Integer id);

    int save(SysUser sysUser);

    SysUser getUser(SysUser sysUser);

    ProcessResult showVerify(String email);

    ProcessResult saveUser(SysUser vo);

    void saveOrUpdate(SysUser sysUser);

    void batchDelete(Integer[] list);

    List<SysUser> getIsNotOwner(Integer roleId,String keyword);

    List<SysUser> getIsOwner(Integer roleId,String keyword);

    void allotUserById(Integer userId, Integer roleId, Integer type);

    SysUser findByUserName(String username);

    void saveAvatar(SysUser sysUser);
}