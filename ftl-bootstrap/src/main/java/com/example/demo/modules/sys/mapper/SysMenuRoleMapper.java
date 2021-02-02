package com.example.demo.modules.sys.mapper;

import com.example.demo.core.mapper.MyMapper;
import com.example.demo.modules.sys.model.SysMenuRole;

public interface SysMenuRoleMapper extends MyMapper<SysMenuRole> {
    void deleteByRoleId(Integer roleId);
}