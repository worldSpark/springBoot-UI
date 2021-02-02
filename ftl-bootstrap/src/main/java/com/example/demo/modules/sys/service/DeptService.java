package com.example.demo.modules.sys.service;

import com.example.demo.modules.sys.model.SysDept;
import com.example.demo.modules.sys.model.SysDeptUser;
import com.example.demo.modules.sys.model.SysUser;

import java.util.List;
import java.util.Map;

public interface DeptService {

    List<SysDept> getAll();

    Map<String,Object> treeList();

    SysDept getDeptById(Integer id);

    void saveOrUpdate(SysDept sysDept) throws Exception;

    void deleteById(Integer id);

    SysUser getMasterUserByDept(Integer deptId);

    void allotUserByDept(Integer userId,Integer deptId, Integer type);

    List<SysUser> isUnAllotUserList(SysUser sysUser,Integer deptId);

    List<SysUser> isAllotUserList(SysUser sysUser,Integer deptId);

    void setMasterUserByDept(Integer userId, Integer deptId,Integer type);

    SysDeptUser getSysDeptUser(Integer userId, Integer deptId);
}