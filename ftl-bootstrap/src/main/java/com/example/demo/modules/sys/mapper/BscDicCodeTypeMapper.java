package com.example.demo.modules.sys.mapper;

import com.example.demo.core.mapper.MyMapper;
import com.example.demo.modules.sys.model.BscDicCodeType;

public interface BscDicCodeTypeMapper extends MyMapper<BscDicCodeType> {
    int myInsertUseGeneratedKeys(BscDicCodeType bscDicCodeType);
}