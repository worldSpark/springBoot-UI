package com.example.demo.modules.sys.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysDeptVo implements Serializable {
    private Integer id;
    private String text;
    private String icon;
    private Map<String,Map> state;

    private List<SysDeptVo> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<SysDeptVo> getChildren() {
        return children;
    }

    public void setChildren(List<SysDeptVo> children) {
        this.children = children;
    }

    public Map<String, Map> getState() {
        return state;
    }

    public void setState(Map<String, Map> state) {
        this.state = state;
    }
}
