package com.example.demo.modules.job.mapper;

import com.example.demo.core.mapper.MyMapper;
import com.example.demo.modules.job.model.SysTask;

public interface SysTaskMapper extends MyMapper<SysTask> {
    int batchRemove(Long[] ids);
}