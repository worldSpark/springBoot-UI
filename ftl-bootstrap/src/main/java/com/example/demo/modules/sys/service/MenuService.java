package com.example.demo.modules.sys.service;




import com.example.demo.modules.sys.model.SysMenu;
import com.example.demo.modules.sys.model.SysUser;
import com.example.demo.modules.sys.vo.SysMenuVo;

import java.util.List;
import java.util.Set;

public interface MenuService {
    List<SysMenuVo> getTreeList();

    List<SysMenu> getAll(SysMenu sysMenu);

    SysMenu getById(Integer id);

    void saveOrUpdate(SysMenu sysMenu) throws Exception;

    void deleteById(Integer id);

    List<SysMenu> getSelMenuPermission(Integer roleId);

    void saveMenuPermission(Integer roleId, Integer[] ids);

    List<SysMenuVo> treeListPermission(SysUser sysUser);

    public List<SysMenuVo> treeListPermissionByRoleId(Integer roleId);

    public Set<SysMenuVo> treeSetPermissionByRoleId(Integer roleId);
}