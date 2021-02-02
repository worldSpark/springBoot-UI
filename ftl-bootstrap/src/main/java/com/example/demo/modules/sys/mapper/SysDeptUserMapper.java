package com.example.demo.modules.sys.mapper;

import com.example.demo.core.mapper.MyMapper;
import com.example.demo.modules.sys.model.SysDeptUser;
import org.apache.ibatis.annotations.Param;

public interface SysDeptUserMapper extends MyMapper<SysDeptUser> {
    void clearMasterByDept(@Param("userId") Integer userId,@Param("deptId") Integer deptId);

    void setMasterUserByDept(@Param("userId") Integer userId,@Param("deptId") Integer deptId);
}