package com.fc.common.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 通用已办查询配置
 * @author luoy
 * @Date 2020年02月09日20:28:53
 * @version 1.0.0
 * */
@Data
public class CommonProcessedConfig {

    // 操作按钮集合
    private List<CommonProcessedButtonUnit> buttonUnitList;

    // 查询单元集合
    private List<CommonProcessedSearchUnit> searchUnitList;

    // 字段单元集合
    private List<CommonProcessedFieldUnit> fieldUnitList;

    public CommonProcessedConfig() {
        super();
        // 初始化
        buttonUnitList = new ArrayList<>();
        searchUnitList = new ArrayList<>();
        fieldUnitList = new ArrayList<>();
    }
}
