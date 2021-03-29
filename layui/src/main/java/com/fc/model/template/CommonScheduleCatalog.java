package com.fc.model.template;

import com.fc.common.domain.CommonScheduleData;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 通用待办目录实体
 * @author luoy
 * @Date 2020年1月22日10:35:04
 * @version 1.0.0
 * */
@Data
public class CommonScheduleCatalog {

    // 模块名
    private String modelName;
    // 模块数据
    private List<CommonScheduleData> scheduleDataList;
    // 排序号
    private String orderNo;
    // 新待办个数
    private int newScheduleCount;
    // 置顶状态，true置顶，false置顶
    private boolean isRoof;
    // 其它参数
    private Map<String, Object> param;

    // 初始化，设置默认值
    public CommonScheduleCatalog() {
        scheduleDataList = new ArrayList<>();
        newScheduleCount = 0;
        param = new HashMap<>();
        isRoof = false;
    }
}
