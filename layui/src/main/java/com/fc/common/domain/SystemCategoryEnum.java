package com.fc.common.domain;

/**
 * @Description 此枚举用于系统分类信息
 * @author weizh
 * @Date 2020/4/24
 * @version 1.0.0
 * */
public enum SystemCategoryEnum {

    CATEGORY_INSIDE(1,"内部");

    // 分类下标
    private int index;
    // 系统分类名称
    private String categoryName;

    SystemCategoryEnum(int index, String categoryName) {
        this.index = index;
        this.categoryName = categoryName;
    }

    public int index() {
        return this.index;
    }

    public String categoryName(){
        return this.categoryName;
    }
}
