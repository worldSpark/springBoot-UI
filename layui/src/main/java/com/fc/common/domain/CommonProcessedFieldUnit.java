package com.fc.common.domain;

import lombok.Data;

/**
 * @Description 通用已办查询条件单元
 * @author luoy
 * @Date 2020年02月09日20:28:53
 * @version 1.0.0
 * */
@Data
public class CommonProcessedFieldUnit {

    // 显示的名字
    private String title;

    // 字段名
    private String field;

    // 隐藏，默认false
    private boolean hidden;

    // 宽度，默认100
    private int width;

    // 宽度自适应，默认true
    private boolean sortable;

    // 居中样式，默认center，还有：left、right等
    private String align;

    // 标记此字段显示到首页上，默认false，true显示在首页，false不显示在首页
    private boolean showOnHomePage;

    // 如果需要扩展，在这里加吧


    public CommonProcessedFieldUnit() {
        super();
        this.hidden = false;
        this.width = 100;
        this.sortable = true;
        this.align = "center";
        this.showOnHomePage = false;
    }
}
