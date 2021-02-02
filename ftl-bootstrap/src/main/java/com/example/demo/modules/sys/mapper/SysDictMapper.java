package com.example.demo.modules.sys.mapper;

import com.example.demo.core.entity.ProcessResult;
import com.example.demo.core.mapper.MyMapper;
import com.example.demo.modules.sys.model.SysDict;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface SysDictMapper extends MyMapper<SysDict> {
    SysDict getDictValueChild(@Param("param") Map<String, Object> param);
}