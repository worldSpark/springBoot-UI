package com.fc.common.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description 通用已办查询条件单元
 * @author luoy
 * @Date 2020年02月09日20:28:53
 * @version 1.0.0
 * */
@Data
public class CommonProcessedSearchUnit {

    // 显示的名字
    private String searchShowName;

    // 后台传递的字段名，注：如果为日期范围查询，自动一分二，尾部分别拼接Start、End
    private String searchFieldName;

    // 查询类型，见CommonProcessedConstants
    private int searchType;

    // 下拉框text名，前台显示用
    private String selectTextName;

    // 下拉框value名，后台传递用
    private String selectValueName;

    // 下拉框的查询数据
    private List<Map<String, Object>> selectList;
}
