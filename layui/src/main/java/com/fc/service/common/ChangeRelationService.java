package com.fc.service.common;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
* @Description:    转换单位维护Service
* @Author:         zhangxh
* @CreateDate:     2019/1/25 9:31
* @Version:        1.0
*/
public interface ChangeRelationService {
    /**
    * @Description:    获取转换单位数据
    * @Author:         zhangxh
    * @CreateDate:     2019/1/25 9:31
    * @Version:        1.0
    */
    Map getListData(HttpServletRequest request);

    /**
    * @Description:    获取子表数据
    * @Author:         zhangxh
    * @CreateDate:     2019/1/26 10:18
    * @Version:        1.0
    */
    List<Map<String,Object>> getSubList(String id);

    List<Map<String, Object>> convertIdsToNames(List<Map<String, Object>> list, String fieldFrom, String fieldTo, String c);
}
