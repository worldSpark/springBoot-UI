package com.example.demo.modules.sys.mapper;

import com.example.demo.core.mapper.MyMapper;
import com.example.demo.modules.sys.model.SysMenu;

import java.util.List;

public interface SysMenuMapper extends MyMapper<SysMenu> {
    List<SysMenu> getTopList();

    List<SysMenu> getChildDeptList(Integer id);

    List<SysMenu> getSelMenuPermission(Integer roleId);
}