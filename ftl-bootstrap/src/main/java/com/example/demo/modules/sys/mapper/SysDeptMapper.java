package com.example.demo.modules.sys.mapper;

import com.example.demo.core.mapper.MyMapper;
import com.example.demo.modules.sys.model.SysDept;
import com.example.demo.modules.sys.model.SysUser;

import java.util.List;

public interface SysDeptMapper extends MyMapper<SysDept> {
    List<SysDept> ListTopDept();

    List<SysDept> getChildDeptList(Integer id);
}