package com.fc.common.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 通用已办操作按钮单元
 * @author luoy
 * @Date 2020年02月09日20:28:53
 * @version 1.0.0
 * */
@Data
public class CommonProcessedButtonUnit {

    private String id;

    // 按钮的名字，默认"查看"
    private String text;

    // 打开窗口、标签页的标题（必须有）
    private String title;

    // 按钮操作方式，见CommonProcessedConstants
    private int operationType;

    // 按钮打开的URL
    private String operationURL;

    //layer窗口大小[宽,高]
    private String[] layerIframe;

    // 按钮打开的URL后拼接的参数名，如设置为id，那url自动拼接为：/xxx/xxx/xxx?id=xxx
    private List<String> operationURLParamList;

    public CommonProcessedButtonUnit() {
        super();
        text = "查看";
        operationURLParamList = new ArrayList<>();
    }

    /**
     * 添加URL参数
     * @param operationURLParam URL参数名
     */
    public void addOperationURLParam(String operationURLParam) {
        this.operationURLParamList.add(operationURLParam);
    }
}
