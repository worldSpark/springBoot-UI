package com.fc.service.common.Impl;

import com.fc.mapper.SqlMapper;
import com.fc.service.common.ChangeRelationService;
import com.fc.service.common.CommonService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Description:    转换单位维护实现层
* @Author:         zhangxh
* @CreateDate:     2019/1/25 9:32
* @Version:        1.0
*/
@Service
@Transactional
public class ChangeRelationServiceImpl implements ChangeRelationService {
    @Autowired
    private SqlMapper sqlMapper;
    @Autowired
    private CommonService commonService;

    /**
    * @Description:    获取转换系数列表数据
    * @Author:         zhangxh
    * @CreateDate:     2019/1/25 9:35
    * @Version:        1.0
    */
    @Override
    public Map getListData(HttpServletRequest request) {
        int limit = Integer.parseInt(request.getParameter("limit"));
        int offset = Integer.parseInt(request.getParameter("offset"));
        PageHelper.startPage(offset,limit);

        String sql = " select * from wp_change_relation where 1=1 ";
        String searchName = request.getParameter("searchName");

        StringBuffer where = new StringBuffer();
        if(StringUtils.isNotBlank(searchName)){
            where.append(" and relation_name like '%"+searchName+"%'");
        }
        List<Map<String,Object>> rulesList = sqlMapper.sqlQueryList(sql+where);

        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(rulesList);
        Map<String,Object> map = new HashMap<>();
        map.put("rows",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return map;
    }

    /**
    * @Description:    获取子表数据
    * @Author:         zhangxh
    * @CreateDate:     2019/1/26 10:19
    * @Version:        1.0
    */
    @Override
    public List<Map<String, Object>> getSubList(String id) {
        String sql = " select rs.*,lu.unit_name as baseName,lv.unit_name as changeName from wp_change_relation_sub rs " +
                " left join wp_logistics_unit lu on rs.pk_wp_logistics_unit_base=lu.id " +
                " left join wp_logistics_unit lv on rs.pk_wp_logistics_unit_change=lv.id " +
                " where rs.pk_wp_change_relation='"+id+"'";
        List<Map<String,Object>> sunList = sqlMapper.sqlQueryList(sql);
        return sunList;
    }

    @Override
    public List<Map<String, Object>> convertIdsToNames(List<Map<String, Object>> list, String fieldFrom, String fieldTo, String c) {
        return commonService.convertMapKeyToAnotherMapKey(list,"wp_change_relation","id","relation_name", fieldFrom, fieldTo, c);
    }
}
