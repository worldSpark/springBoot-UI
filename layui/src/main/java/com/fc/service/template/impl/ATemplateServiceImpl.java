package com.fc.service.template.impl;

import com.fc.common.base.PageInfo;
import com.fc.common.domain.*;
import com.fc.mapper.SqlMapper;
import com.fc.model.template.ATemplate;
import com.fc.service.common.CommonFileUploadService;
import com.fc.service.common.CommonService;
import com.fc.service.template.ATemplateService;
import com.fc.util.GlobalFunc;
import com.fc.util.Sequence;
import com.fc.util.SqlUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fc.annotation.CommonProcessedInterface;
import com.fc.annotation.CommonScheduleInterface;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 实体类模板实现层
 * @author xxx
 * @date 2020年1月6日09:59:31
 * @version 1.0.0
 */
@Service
@Transactional
public class ATemplateServiceImpl implements ATemplateService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private SqlMapper sqlMapper;

    @Autowired
    private CommonFileUploadService commonFileUploadService;

    @Override
    public int insert(ATemplate aTemplate) {
        return commonService.insert(aTemplate);
    }

    @Override
    public int update(ATemplate aTemplate) {
        return commonService.update(aTemplate);
    }

    @Override
    public ATemplate getById(String id) {
        return commonService.getById(ATemplate.class, id);
    }

    @Override
    public int delete(String id) {
        return commonService.delete(ATemplate.class, id);
    }

    @Override
    public Map<String, Object> getATemplateList(HttpServletRequest request) {
        // 第n页
        int page = GlobalFunc.parseInt(request.getParameter("page"));
        // 每页显示n条
        int rows = GlobalFunc.parseInt(request.getParameter("rows"));

        // 查询条件
        String searchNormalInput = GlobalFunc.toString(request.getParameter("searchNormalInput"));
        String searchNormalSingleSelect = GlobalFunc.toString(request.getParameter("searchNormalSingleSelect"));
        String searchIntInputStart = GlobalFunc.toString(request.getParameter("searchIntInputStart"));
        String searchIntInputEnd = GlobalFunc.toString(request.getParameter("searchIntInputEnd"));
        String searchDateInputYMDStart = GlobalFunc.toString(request.getParameter("searchDateInputYMDStart"));
        String searchDateInputYMDEnd = GlobalFunc.toString(request.getParameter("searchDateInputYMDEnd"));

        // 第一种方式，单表查询，直接用封装方法
        SqlCondition sqlCondition = new SqlCondition();
        if (StringUtils.isNotBlank(searchNormalInput)) {
            sqlCondition.andLikeTo("normal_input", "%" + searchNormalInput + "%");
        }
        if (StringUtils.isNotBlank(searchNormalSingleSelect)) {
            sqlCondition.andEqualTo("normal_single_select", searchNormalSingleSelect);
        }
        if (StringUtils.isNotBlank(searchIntInputStart)) {
            sqlCondition.andEqualOrBiggerThan("int_input_start", searchIntInputStart);
        }
        if (StringUtils.isNotBlank(searchIntInputEnd)) {
            sqlCondition.andEqualOrLessThan("int_input_end", searchIntInputEnd);
        }
        if (StringUtils.isNotBlank(searchDateInputYMDStart)) {
            sqlCondition.andEqualOrBiggerThan("date_input_ymd", searchDateInputYMDStart);
        }
        if (StringUtils.isNotBlank(searchDateInputYMDEnd)) {
            sqlCondition.andEqualOrBiggerThan("date_input_ymd", searchDateInputYMDEnd);
        }
        Map<String, Object> data = commonService.getByConditionWithEasyUI(ATemplate.class, page, rows, sqlCondition);

        // 第二种方式，多表关联，自己拼接sql
       /* StringBuffer sql = new StringBuffer();
        sql.append(" select a.*");
        sql.append(" from wp_a_template as a");
        sql.append(" where 1=1");
        if (StringUtils.isNotBlank(searchNormalInput)) {
            sql.append(" and a.normal_input like '%" + SqlUtil.transferValue(searchNormalInput) + "%'");
        }
        if (StringUtils.isNotBlank(searchNormalSingleSelect)) {
            sql.append(" and a.normal_single_select = '" + SqlUtil.transferValue(searchNormalSingleSelect) + "'");
        }
        if (StringUtils.isNotBlank(searchIntInputStart)) {
            sql.append(" and a.int_input_start >= '" + SqlUtil.transferValue(searchIntInputStart) + "'");
        }
        if (StringUtils.isNotBlank(searchIntInputEnd)) {
            sql.append(" and a.int_input_end <= '" + SqlUtil.transferValue(searchIntInputEnd) + "'");
        }
        if (StringUtils.isNotBlank(searchDateInputYMDStart)) {
            sql.append(" and a.date_input_ymd >= '" + SqlUtil.transferValue(searchDateInputYMDStart) + "'");
        }
        if (StringUtils.isNotBlank(searchDateInputYMDEnd)) {
            sql.append(" and a.date_input_ymd <= '" + SqlUtil.transferValue(searchDateInputYMDEnd) + "'");
        }
        // 设置分页位置，使下一次查询分页
        PageHelper.startPage(page, rows);
        // 查询
        List<Map<String, Object>> dataList = sqlMapper.sqlQueryList(sql.toString());
        // 获取分页数据
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(dataList);
        List<Map<String, Object>> pageList = pageInfo.getList();
        long total = pageInfo.getTotal();
        // 下划线转驼峰
        pageList = commonService.convertMapKeyFromUnderlineToUpperCase(pageList);
        // 组装
//        Map<String, Object> data = new HashMap<>();
        data.put("rows", pageList);
        data.put("total", total);*/

        return data;
    }

    @Override
    public List<Map<String, Object>> getDynamicData() {
        // 更多的是获取数据库的数据，这里用静态数据代替
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", "1");
        map.put("value", "动态1");
        list.add(map);map = new HashMap<>();
        map.put("id", "2");
        map.put("value", "动态2");
        list.add(map);map = new HashMap<>();
        map.put("id", "3");
        map.put("value", "动态3");
        list.add(map);
        return list;
    }

    @Override
    public boolean checkRepeat(String id, String normalInput) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.andEqualTo("normalInput", normalInput);
        if(StringUtils.isNotBlank(id)){
            sqlCondition.andNotEqualTo("id", id);
        }
        int count = commonService.getCount(ATemplate.class, sqlCondition);
        return count > 0;
    }

    @Override
    public Map<String, Object> save(HttpServletRequest request) {
        String id = GlobalFunc.toString(request.getParameter("id"));
        String normalInput = GlobalFunc.toString(request.getParameter("normalInput"));
        String intInput = GlobalFunc.toString(request.getParameter("intInput"));
        String intInputStart = GlobalFunc.toString(request.getParameter("intInputStart"));
        String intInputEnd = GlobalFunc.toString(request.getParameter("intInputEnd"));
        String dateInputY = GlobalFunc.toString(request.getParameter("dateInputY"));
        String dateInputYM = GlobalFunc.toString(request.getParameter("dateInputYM"));
        String dateInputYMD = GlobalFunc.toString(request.getParameter("dateInputYMD"));
        String dateInputYMDHMS = GlobalFunc.toString(request.getParameter("dateInputYMDHMS"));
        String dateInputStart = GlobalFunc.toString(request.getParameter("dateInputStart"));
        String dateInputEnd = GlobalFunc.toString(request.getParameter("dateInputEnd"));
        String normalSingleSelect = GlobalFunc.toString(request.getParameter("normalSingleSelect"));
        String normalMultipleSelect = GlobalFunc.toString(request.getParameter("normalMultipleSelect"));
        String dynamicNormalMultipleSelect = GlobalFunc.toString(request.getParameter("dynamicNormalMultipleSelect"));
        String enabled = GlobalFunc.toString(request.getParameter("enabled"));
        String pkWpUser = GlobalFunc.toString(request.getParameter("pkWpUser"));
        String pkWpDepartment = GlobalFunc.toString(request.getParameter("pkWpDepartment"));
        String normalSingleCheck = GlobalFunc.toString(request.getParameter("normalSingleCheck"));
        String normalMultipleCheck = GlobalFunc.toString(request.getParameter("normalMultipleCheck"));
        String groupNormalMultipleCheck = GlobalFunc.toString(request.getParameter("groupNormalMultipleCheck"));
        String normalTextarea = GlobalFunc.toString(request.getParameter("normalTextarea"));
        String mainId = GlobalFunc.toString(request.getParameter("mainId"));

        ATemplate aTemplate;
        if(StringUtils.isBlank(id)){
            aTemplate = new ATemplate();
            aTemplate.setId(Sequence.getSequence());
        }else{
            aTemplate = this.getById(id);
        }
        aTemplate.setNormalInput(normalInput);
        aTemplate.setIntInput(intInput);
        aTemplate.setIntInputStart(intInputStart);
        aTemplate.setIntInputEnd(intInputEnd);
        aTemplate.setDateInputY(dateInputY);
        aTemplate.setDateInputYM(dateInputYM);
        aTemplate.setDateInputYMD(dateInputYMD);
        aTemplate.setDateInputYMDHMS(dateInputYMDHMS);
        aTemplate.setDateInputStart(dateInputStart);
        aTemplate.setDateInputEnd(dateInputEnd);
        aTemplate.setNormalSingleSelect(normalSingleSelect);
        aTemplate.setNormalMultipleSelect(normalMultipleSelect);
        aTemplate.setDynamicNormalMultipleSelect(dynamicNormalMultipleSelect);
        aTemplate.setEnabled(enabled);
        aTemplate.setPkWpUser(pkWpUser);
        aTemplate.setPkWpDepartment(pkWpDepartment);
        aTemplate.setNormalSingleCheck(normalSingleCheck);
        aTemplate.setNormalMultipleCheck(normalMultipleCheck);
        aTemplate.setGroupNormalMultipleCheck(groupNormalMultipleCheck);
        aTemplate.setNormalTextarea(normalTextarea);

        if(StringUtils.isBlank(id)){
            this.insert(aTemplate);
            // 附件上传，更新mainId
            commonFileUploadService.updateMainId(mainId, aTemplate.getId());
        }else{
            this.update(aTemplate);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("result", "success");
        map.put("message", "保存成功！");
        return map;
    }

    @Override
    @CommonScheduleInterface(modelName = CommonScheduleConstants.NAME_TEMPLATE,
            dataType = CommonScheduleConstants.DATA_TYPE_COUNT)
    public int getScheduleCount() {
        // 这里用固定数据代替，待办统计自己实现
        return 3;
    }

    @Override
    @CommonScheduleInterface(modelName = CommonScheduleConstants.NAME_TEMPLATE,
            dataType = CommonScheduleConstants.DATA_TYPE_DATA)
    public List<CommonScheduleData> getScheduleData() {
        // 这里用固定数据代替，待办数据自己实现
        List<CommonScheduleData> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CommonScheduleData data = new CommonScheduleData();
            // 子模块名
            data.setSubModelName("");
            // 待办内容
            data.setContent("模板数据" + (i + 1));
            // 新页面标题名称
            data.setOperationTitle("模板");
            // 新tab
            data.setOperationType(CommonScheduleConstants.OPERATION_TYPE_TAB);
            // 打开地址
            data.setOperationUrl("/template/aTemplate/list");
            // 上一步操作时间
            data.setScheduleTime("2020-01-22 12:01:0" + i);
            list.add(data);
        }
        return list;
    }

    @Override
    @CommonScheduleInterface(modelName = CommonScheduleConstants.NAME_TEMPLATE,
            dataType = CommonScheduleConstants.DATA_TYPE_COUNT,
            systemStates = CommonScheduleConstants.SYSTEM_STATE_EMERGENCY)
    public int getScheduleCountOfEmergency() {
        // 这里用固定数据代替，待办统计自己实现
        return 4;
    }

    @Override
    @CommonScheduleInterface(modelName = CommonScheduleConstants.NAME_TEMPLATE,
            dataType = CommonScheduleConstants.DATA_TYPE_DATA,
            systemStates = CommonScheduleConstants.SYSTEM_STATE_EMERGENCY)
    public List<CommonScheduleData> getScheduleDataOfEmergency() {
        // 这里用固定数据代替，待办数据自己实现
        List<CommonScheduleData> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CommonScheduleData data = new CommonScheduleData();
            // 子模块名
            data.setSubModelName("");
            // 待办内容
            data.setContent("应急模板数据" + (i + 1));
            // 新页面标题名称
            data.setOperationTitle("模板");
            // 新tab
            data.setOperationType(CommonScheduleConstants.OPERATION_TYPE_TAB);
            // 打开地址
            data.setOperationUrl("/template/aTemplate/list");
            // 上一步操作时间
            data.setScheduleTime("2020-01-22 12:01:0" + i);
            list.add(data);
        }
        return list;
    }

    @Override
    @CommonProcessedInterface(modelName = CommonProcessedConstants.NAME_TEMPLATE_A,
            subModelName = CommonProcessedConstants.SUB_NAME_TEMPLATE_A_FIRST,
            dataType = CommonProcessedConstants.DATA_TYPE_SELECT,
            systemStates = {CommonProcessedConstants.SYSTEM_STATE_NORMAL, CommonProcessedConstants.SYSTEM_STATE_EMERGENCY})
    public CommonProcessedConfig getProcessedConfig() {
        // 查询配置
        CommonProcessedConfig processedConfig = new CommonProcessedConfig();
        List<CommonProcessedSearchUnit> searchUnitList = processedConfig.getSearchUnitList();
        List<CommonProcessedButtonUnit> buttonUnitList = processedConfig.getButtonUnitList();
        List<CommonProcessedFieldUnit> fieldUnitList = processedConfig.getFieldUnitList();

        // 查询条件可为空，searchFieldName建议以search开头，命名避免过于简单，防止重复
        // 查询条件：输入框
        CommonProcessedSearchUnit searchUnit = new CommonProcessedSearchUnit();
        searchUnit.setSearchShowName("输入框查询");
        searchUnit.setSearchFieldName("searchInput");
        searchUnit.setSearchType(CommonProcessedConstants.SEARCH_TYPE_INPUT);
        searchUnitList.add(searchUnit);

        // 查询条件：单个日期框
        searchUnit = new CommonProcessedSearchUnit();
        searchUnit.setSearchShowName("单个日期查询");
        searchUnit.setSearchFieldName("searchSingleDate");
        searchUnit.setSearchType(CommonProcessedConstants.SEARCH_TYPE_SINGLE_DATEBOX);
        searchUnitList.add(searchUnit);

        // 查询条件：两个日期框
        searchUnit = new CommonProcessedSearchUnit();
        searchUnit.setSearchShowName("两个日期查询");
        searchUnit.setSearchFieldName("searchMultipleDate");
        searchUnit.setSearchType(CommonProcessedConstants.SEARCH_TYPE_MULTIPLE_DATEBOX);
        searchUnitList.add(searchUnit);

        // 查询条件：单选下拉框（多选同理，这里就不展示了）
        searchUnit = new CommonProcessedSearchUnit();
        searchUnit.setSearchShowName("单个下拉框查询");
        searchUnit.setSearchFieldName("searchSingleSelect");
        searchUnit.setSearchType(CommonProcessedConstants.SEARCH_TYPE_SINGLE_COMBOBOX);
        // 下拉数据
        searchUnit.setSelectValueName("id");
        searchUnit.setSelectTextName("userName");
        // 这里用固定数据代替，当然也可以查数据库的内容，如果是对象可以用CommonService转List<Map<...>>
        List<Map<String, Object>> selectList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", "");
        map.put("userName", "请选择");
        selectList.add(map);
        map = new HashMap<>();
        map.put("id", "1");
        map.put("userName", "张三");
        selectList.add(map);
        map = new HashMap<>();
        map.put("id", "2");
        map.put("userName", "李四");
        selectList.add(map);
        map = new HashMap<>();
        map.put("id", "3");
        map.put("userName", "王五");
        selectList.add(map);
        searchUnit.setSelectList(selectList);
        searchUnitList.add(searchUnit);

        // 操作按钮可为空
        // 操作按钮：查看
        CommonProcessedButtonUnit buttonUnit = new CommonProcessedButtonUnit();
        buttonUnit.setText("查看");
        buttonUnit.setTitle("查看数据");
        buttonUnit.setOperationType(CommonProcessedConstants.OPERATION_TYPE_WINDOW);
        buttonUnit.setOperationURL("/template/aTemplate/addOrEditATemplate");
        buttonUnit.addOperationURLParam("id");
        buttonUnitList.add(buttonUnit);

        // 操作按钮：跳转到模板
        buttonUnit = new CommonProcessedButtonUnit();
        buttonUnit.setText("跳转到模板");
        buttonUnit.setTitle("增删改查模板");
        buttonUnit.setOperationType(CommonProcessedConstants.OPERATION_TYPE_TAB);
        buttonUnit.setOperationURL("/template/aTemplate/list");
        buttonUnitList.add(buttonUnit);

        // 列表的字段，这个必须有
        CommonProcessedFieldUnit fieldUnit = new CommonProcessedFieldUnit();
        fieldUnit.setField("id");
        fieldUnit.setHidden(true);
        fieldUnitList.add(fieldUnit);

        fieldUnit = new CommonProcessedFieldUnit();
        fieldUnit.setTitle("普通输入框");
        fieldUnit.setField("normalInput");
        // 此字段在首页上显示
        // 首页上显示的建议设置两个fieldUnit，第一个为内容，第二个为时间，时间格式：yyyy-MM-dd HH:mm:ss
        fieldUnit.setShowOnHomePage(true);
        fieldUnitList.add(fieldUnit);

        fieldUnit = new CommonProcessedFieldUnit();
        fieldUnit.setTitle("年月日时分秒");
        fieldUnit.setField("dateInputYMDHMS");
        fieldUnit.setShowOnHomePage(true);
        fieldUnitList.add(fieldUnit);

        fieldUnit = new CommonProcessedFieldUnit();
        fieldUnit.setTitle("文本框");
        fieldUnit.setField("normalTextarea");
        fieldUnitList.add(fieldUnit);

        return processedConfig;
    }

    @Override
    @CommonProcessedInterface(modelName = CommonProcessedConstants.NAME_TEMPLATE_A,
            subModelName = CommonProcessedConstants.SUB_NAME_TEMPLATE_A_FIRST,
            dataType = CommonProcessedConstants.DATA_TYPE_DATA)
    public Map<String, Object> getProcessedDataGrid(HttpServletRequest request, int page, int rows) {

        // 查询条件，对应已办查询配置
        String searchInput = GlobalFunc.toString(request.getParameter("searchInput"));
        String searchSingleDate = GlobalFunc.toString(request.getParameter("searchSingleDate"));
        String searchMultipleDateStart = GlobalFunc.toString(request.getParameter("searchMultipleDateStart"));
        String searchMultipleDateEnd = GlobalFunc.toString(request.getParameter("searchMultipleDateEnd"));
        String searchSingleSelect = GlobalFunc.toString(request.getParameter("searchSingleSelect"));

        // 第一种方式，单表查询，直接用封装方法
        SqlCondition sqlCondition = new SqlCondition();
        if (StringUtils.isNotBlank(searchInput)) {
            sqlCondition.andLikeTo("normal_input", "%" + searchInput + "%");
        }
        // 多个查询条件自行扩展...
        Map<String, Object> data = commonService.getByConditionWithEasyUI(ATemplate.class, page, rows, sqlCondition);

        return data;
    }

    @Override
    @CommonProcessedInterface(modelName = CommonProcessedConstants.NAME_TEMPLATE_A,
            subModelName = CommonProcessedConstants.SUB_NAME_TEMPLATE_A_FIRST,
            dataType = CommonProcessedConstants.DATA_TYPE_DATA,
            systemStates = CommonProcessedConstants.SYSTEM_STATE_EMERGENCY)
    public Map<String, Object> getProcessedDataGridOfEmergency(HttpServletRequest request, int page, int rows) {

        // 查询条件，对应已办查询配置
        String searchInput = GlobalFunc.toString(request.getParameter("searchInput"));
        String searchSingleDate = GlobalFunc.toString(request.getParameter("searchSingleDate"));
        String searchMultipleDateStart = GlobalFunc.toString(request.getParameter("searchMultipleDateStart"));
        String searchMultipleDateEnd = GlobalFunc.toString(request.getParameter("searchMultipleDateEnd"));
        String searchSingleSelect = GlobalFunc.toString(request.getParameter("searchSingleSelect"));

        // 第一种方式，单表查询，直接用封装方法
        SqlCondition sqlCondition = new SqlCondition();
        if (StringUtils.isNotBlank(searchInput)) {
            sqlCondition.andLikeTo("normal_input", "%" + searchInput + "%");
        }
        // 多个查询条件自行扩展...
        Map<String, Object> data = commonService.getByConditionWithEasyUI(ATemplate.class, page, rows, sqlCondition);

        return data;
    }
}
