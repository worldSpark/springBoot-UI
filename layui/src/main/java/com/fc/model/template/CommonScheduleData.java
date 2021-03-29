package com.fc.model.template;

import com.fc.common.domain.CommonScheduleConstants;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 通用待办数据实体
 * @author luoy
 * @Date 2020年1月22日10:35:04
 * @version 1.0.0
 * */
@Data
public class CommonScheduleData {

    // 子模块名，如果子模块名为空，总模块下有其它子模块，那么此子模块名会变成“其它”
    private String subModelName;
    // 待办内容
    private String content;
    // 上一步处理时间，yyyy-MM-dd HH:mm:ss
    private String scheduleTime;
    // 操作方式，新tab（默认）、新页面、窗口、不操作，见CommonScheduleConstants
    private int operationType;
    // 点击打开新窗口时的标题名称，如果为空，则显示模块名
    private String operationTitle;
    // 点击打开新窗口时的访问路径
    private String operationUrl;
    // 待办类型：待办（默认）、已办，见CommonScheduleConstants
    private int scheduleType;
    // 按钮名称，默认为“办理”，可以自行变更
    private String buttonName;
    //layer窗口大小[宽,高]
    private String[] layerIframe;
    // 其它参数
    private Map<String, Object> param;

    // ------------------------------- 自动处理参数开始 -------------------------------
    // 模块名
    private String modelName;
    // 已通知，true、false（默认），不需要自己处理，待办算法统一处理
    private boolean isInformed;
    // ------------------------------- 自动处理参数结束 -------------------------------

    // 初始化，设置默认值
    public CommonScheduleData() {
        this.operationType = CommonScheduleConstants.OPERATION_TYPE_TAB;
        this.isInformed = false;
        this.buttonName = "办理";
        this.param = new HashMap<>();
    }
}
