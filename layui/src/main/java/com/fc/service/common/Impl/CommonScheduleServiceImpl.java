package com.fc.service.common.Impl;

import com.fc.annotation.CommonScheduleInterface;
import com.fc.common.conf.CommonConfig;
import com.fc.common.domain.CommonScheduleConstants;
import com.fc.common.domain.CommonScheduleData;
import com.fc.common.domain.SqlCondition;
import com.fc.common.domain.SystemCategoryEnum;
import com.fc.mapper.SqlMapper;
import com.fc.model.auto.TsysUser;
import com.fc.model.template.CommonScheduleCatalog;
import com.fc.model.template.CommonScheduleSetting;
import com.fc.service.common.CommonScheduleService;
import com.fc.service.common.CommonService;
import com.fc.service.common.CommonWebSocketService;
import com.fc.shiro.util.ShiroUtils;
import com.fc.util.ArrayUtil;
import com.fc.util.GlobalFunc;
import com.fc.util.Sequence;
import com.fc.util.SpringContextUtils;
import com.fc.util.security.SecurityUser;
import com.gitee.sunchenbin.mybatis.actable.utils.ClassTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fc.model.template.CommonScheduleSettingSub;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 通用待办实现层
 * @author luoy
 * @Date 2020年1月22日11:25:27
 * @version 1.0.0
 */
@Service
@Transactional
public class CommonScheduleServiceImpl implements CommonScheduleService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private SqlMapper sqlMapper;

    @Autowired
    private CommonWebSocketService commonWebSocketService;

    // 待办数量接口集合，key是模块名
    private static final Map<String, Set<Method>> INTERFACE_UNDONE_COUNT_MAP = new HashMap<>();
    // 待办数据接口集合，key是模块名
    private static final Map<String, Set<Method>> INTERFACE_UNDONE_DATA_MAP = new HashMap<>();

    @Override
    public void loadInterfaceMap() {
        // 借用actable工具类，扫描包
        Set<Class<?>> classes = ClassTools.getClasses("com.fc.service");

        // 循环全部的接口
        for (Class<?> classObject : classes) {
            // 所有方法集合
            Method[] mothods = classObject.getDeclaredMethods();
            // 循环所有申明的方法
            for (Method mothod : mothods) {
                // 没有待办注解，则跳过
                CommonScheduleInterface annotation = mothod.getAnnotation(CommonScheduleInterface.class);
                if(annotation == null){
                    continue;
                }
                // 模块名
                String modelName = GlobalFunc.toString(annotation.modelName());
                // 数据类型
                int dataType = annotation.dataType();
                if(dataType == CommonScheduleConstants.DATA_TYPE_COUNT){
                    // 待办统计接口
                    this.putMethod(CommonScheduleServiceImpl.INTERFACE_UNDONE_COUNT_MAP, modelName, mothod);
                } else if(dataType == CommonScheduleConstants.DATA_TYPE_DATA){
                    // 待办数据接口
                    this.putMethod(CommonScheduleServiceImpl.INTERFACE_UNDONE_DATA_MAP, modelName, mothod);
                }
            }
        }
    }

    @Override
    public List<CommonScheduleCatalog> getScheduleCatalog(String systemState) {
        TsysUser user = ShiroUtils.getUser();
        // 当前用户对应的系统分类枚举
        List<SystemCategoryEnum> systemCategoryEnumList = new ArrayList<>();
        // 待办设置
        CommonScheduleSetting scheduleSetting = this.getScheduleSettingByUserId(user.getId(), systemState);
        // 置顶的模块名
        List<String> roofModelNameList = this.getRoofModelNameBySettingId(scheduleSetting.getId());
        // 隐藏的模块名
        List<String> hideModelNameList = this.getHiddenModelNameBySettingId(scheduleSetting.getId());

        // 模块目录集合
        List<CommonScheduleCatalog> catalogList = new ArrayList<>();
        // 模块名集合 = 待办统计接口 + 待办数据接口 - 用户隐藏接口
        Set<String> modelNameSet = new HashSet<>();
        // 遍历统计接口，如果有对应方法，则放入
        List<String> countKeyList = new ArrayList(CommonScheduleServiceImpl.INTERFACE_UNDONE_COUNT_MAP.keySet());
        for (String countKey : countKeyList) {
            Set<Method> methods = CommonScheduleServiceImpl.INTERFACE_UNDONE_COUNT_MAP.get(countKey);
            methods = this.getFilterMethodBySystemState(methods, systemState, systemCategoryEnumList);
            if(!methods.isEmpty()){
                // 放入
                modelNameSet.add(countKey);
            }
        }
        // 遍历数据接口，如果有对应方法，则放入
        List<String> dataKeyList = new ArrayList(CommonScheduleServiceImpl.INTERFACE_UNDONE_DATA_MAP.keySet());
        for (String dataKey : dataKeyList) {
            Set<Method> methods = CommonScheduleServiceImpl.INTERFACE_UNDONE_DATA_MAP.get(dataKey);
            methods = this.getFilterMethodBySystemState(methods, systemState, systemCategoryEnumList);
            if(!methods.isEmpty()){
                // 放入
                modelNameSet.add(dataKey);
            }
        }

        // 组装目录
        for (String modelName : modelNameSet) {
            if(hideModelNameList.contains(modelName)){
                // 隐藏的，跳过
                continue;
            }
            CommonScheduleCatalog catalog = new CommonScheduleCatalog();
            catalog.setModelName(modelName);
            catalogList.add(catalog);
        }

        // 拼音排序
        catalogList = commonService.orderByPinYin(catalogList, "modelName");

        // 置顶模块
        if(!roofModelNameList.isEmpty()){
            List<CommonScheduleCatalog> roofCatalogList = new ArrayList<>();
            for (int i = 0; i < catalogList.size(); i++) {
                CommonScheduleCatalog catalog = catalogList.get(i);
                String modelName = catalog.getModelName();
                if(roofModelNameList.contains(modelName)){
                    // 需要置顶
                    catalog.setRoof(true);
                    roofCatalogList.add(catalog);
                    // 移除
                    catalogList.remove(i);
                    i--;
                }
            }
            // 合并
            catalogList.addAll(0, roofCatalogList);
        }

        // 所有待办放第一位
        CommonScheduleCatalog catalog = new CommonScheduleCatalog();
        catalog.setModelName(CommonScheduleConstants.NAME_ALL_SCHEDULE);
        catalogList.add(0, catalog);

//        return catalogList;
        return null;
    }

    @Override
    public int getScheduleCount(String modelName, String systemState) {
        if(StringUtils.isBlank(modelName) || StringUtils.isBlank(systemState)){
            return 0;
        }
        TsysUser user = ShiroUtils.getUser();
        // 当前用户对应的系统分类枚举
        List<SystemCategoryEnum> systemCategoryEnumList = new ArrayList<>();
        // 待办设置
        CommonScheduleSetting scheduleSetting = this.getScheduleSettingByUserId(user.getId(), systemState);
        // 隐藏的模块名
        List<String> hideModelNameList = this.getHiddenModelNameBySettingId(scheduleSetting.getId());

        // 所有方法
        Set<Method> methods = null;
        if(CommonScheduleConstants.NAME_ALL_SCHEDULE.equals(modelName)){
            // 所有模块待办
            methods = new HashSet<>();
            // 所有模块名
            Set<String> names = CommonScheduleServiceImpl.INTERFACE_UNDONE_COUNT_MAP.keySet();
            for (String name : names) {
                if(hideModelNameList.contains(name)){
                    // 隐藏的，跳过
                    continue;
                }
                methods.addAll(CommonScheduleServiceImpl.INTERFACE_UNDONE_COUNT_MAP.get(name));
            }
        }else if(CommonScheduleServiceImpl.INTERFACE_UNDONE_COUNT_MAP.containsKey(modelName)){
            // 指定模块待办
            methods = CommonScheduleServiceImpl.INTERFACE_UNDONE_COUNT_MAP.get(modelName);
        }

        // 过滤
        methods = this.getFilterMethodBySystemState(methods, systemState, systemCategoryEnumList);

        // 执行方法
        int count = 0;
        if(methods != null){
            count = this.getScheduleCount(methods);
        }

        return count;
    }

    @Override
    public List<CommonScheduleData> getScheduleData(String modelName, String systemState) {
        List<CommonScheduleData> dataList = new ArrayList<>();
        if(StringUtils.isBlank(modelName)){
            return dataList;
        }
        TsysUser user = ShiroUtils.getUser();
        // 当前用户对应的系统分类枚举
        List<SystemCategoryEnum> systemCategoryEnumList = new ArrayList<>();
        // 待办设置
        CommonScheduleSetting scheduleSetting = this.getScheduleSettingByUserId(user.getId(), systemState);
        // 置顶的模块名
        List<String> roofModelNameList = this.getRoofModelNameBySettingId(scheduleSetting.getId());
        // 隐藏的模块名
        List<String> hideModelNameList = this.getHiddenModelNameBySettingId(scheduleSetting.getId());

        // 所有方法
        Set<Method> methods;
        if(CommonScheduleConstants.NAME_ALL_SCHEDULE.equals(modelName)){
            // 所有模块名
            List<String> nameList = new ArrayList<>(CommonScheduleServiceImpl.INTERFACE_UNDONE_DATA_MAP.keySet());
            // 拼音排序
            nameList = commonService.orderByPinYin(nameList);
            // 置顶排序
            if(!roofModelNameList.isEmpty()){
                List<String> roofNameList = new ArrayList<>();
                for (int i = 0; i < nameList.size(); i++) {
                    String name = nameList.get(i);
                    if(roofModelNameList.contains(name)){
                        // 需要置顶
                        roofNameList.add(name);
                        // 移除
                        nameList.remove(i);
                        i--;
                    }
                }
                // 合并
                nameList.addAll(0, roofNameList);
            }
            // 每个模块逐个执行
            for (String name : nameList) {
                if(hideModelNameList.contains(name)){
                    // 隐藏的，跳过
                    continue;
                }
                methods = CommonScheduleServiceImpl.INTERFACE_UNDONE_DATA_MAP.get(name);
                // 过滤
                methods = this.getFilterMethodBySystemState(methods, systemState, systemCategoryEnumList);
                // 执行方法
                List<CommonScheduleData> list = this.getScheduleData(methods);
                dataList.addAll(list);
            }
        }else if(CommonScheduleServiceImpl.INTERFACE_UNDONE_DATA_MAP.containsKey(modelName)){
            // 指定模块待办
            methods = CommonScheduleServiceImpl.INTERFACE_UNDONE_DATA_MAP.get(modelName);
            // 过滤
            methods = this.getFilterMethodBySystemState(methods, systemState, systemCategoryEnumList);
            // 执行方法
            dataList = this.getScheduleData(methods);
        }

        return dataList;
    }

    @Override
    public CommonScheduleSetting getScheduleSetting(String id) {
        return commonService.getById(CommonScheduleSetting.class, id);
    }

    @Override
    public CommonScheduleSetting getScheduleSettingByUserId(String userId, String systemState) {
        CommonScheduleSetting setting = null;
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.andEqualTo("pkWpUser", userId);
        sqlCondition.andEqualTo("systemState", systemState);
        List<CommonScheduleSetting> list = commonService.getByCondition(CommonScheduleSetting.class, sqlCondition);
        if(list.isEmpty()){
            // 没有创建一个
            setting = new CommonScheduleSetting();
//            setting.setId(Sequence.getSequence());
            setting.setPkWpUser(userId);
            setting.setSystemState(systemState);
            commonService.insert(setting);
        }else{
            // 获取第一个
            setting = list.get(0);
        }
        if(list.size() > 1){
            // 删除多余
            for (int i = 1; i < list.size(); i++) {
                commonService.delete(CommonScheduleSetting.class, list.get(i).getId());
                // 子表
                SqlCondition sqlSubCondition = new SqlCondition();
                sqlSubCondition.andEqualTo("pk_wp_common_schedule_setting", list.get(i).getId());
//                commonService.delete(CommonScheduleSettingSub.class, list.get(i).getId());
            }
        }
        return setting;
    }

    @Override
    public List<String> getRoofModelNameBySettingId(String settingId) {
        List<String> modelNameList = new ArrayList<>();
        CommonScheduleSetting scheduleSetting = this.getScheduleSetting(settingId);
        if(scheduleSetting != null){
            StringBuffer sql = new StringBuffer();
            sql.append(" select model_name");
            sql.append(" from wp_common_schedule_setting_sub");
            sql.append(" where pk_wp_common_schedule_setting = '" + settingId + "'");
            sql.append(" and model_setting = '1'");
            modelNameList = sqlMapper.sqlQueryWithUniqueColumnList(sql.toString());
        }
        return modelNameList;
    }

    @Override
    public List<String> getHiddenModelNameBySettingId(String settingId) {
        List<String> modelNameList = new ArrayList<>();
        CommonScheduleSetting scheduleSetting = this.getScheduleSetting(settingId);
        if(scheduleSetting != null){
            StringBuffer sql = new StringBuffer();
            sql.append(" select model_name");
            sql.append(" from wp_common_schedule_setting_sub");
            sql.append(" where pk_wp_common_schedule_setting = '" + settingId + "'");
            sql.append(" and model_setting = '2'");
            modelNameList = sqlMapper.sqlQueryWithUniqueColumnList(sql.toString());
        }
        return modelNameList;
    }

    @Override
    public int updateCommonScheduleSetting(CommonScheduleSetting commonScheduleSetting) {
        return commonService.update(commonScheduleSetting);
    }

    @Override
    public List<String> getModelNameList(String systemState) {
        TsysUser user = ShiroUtils.getUser();
        // 当前用户对应的系统分类枚举
//        List<SystemCategoryEnum> systemCategoryEnumList = user.getSystemCategoryEnumList();-------------------------------------------------------------请注意这里,是个学习点
        List<SystemCategoryEnum> systemCategoryEnumList =new ArrayList<>();
        // 模块名集合 = 待办统计接口 + 待办数据接口
        List<String> modelNameList = new ArrayList<>();
        // 遍历统计接口，如果有对应方法，则放入
        List<String> countKeyList = new ArrayList(CommonScheduleServiceImpl.INTERFACE_UNDONE_COUNT_MAP.keySet());
        for (String countKey : countKeyList) {
            Set<Method> methods = CommonScheduleServiceImpl.INTERFACE_UNDONE_COUNT_MAP.get(countKey);
            methods = this.getFilterMethodBySystemState(methods, systemState, systemCategoryEnumList);
            if(!methods.isEmpty()){
                // 放入
                modelNameList.add(countKey);
            }
        }
        // 遍历数据接口，如果有对应方法，则放入
        List<String> dataKeyList = new ArrayList(CommonScheduleServiceImpl.INTERFACE_UNDONE_DATA_MAP.keySet());
        for (String dataKey : dataKeyList) {
            Set<Method> methods = CommonScheduleServiceImpl.INTERFACE_UNDONE_DATA_MAP.get(dataKey);
            methods = this.getFilterMethodBySystemState(methods, systemState, systemCategoryEnumList);
            if(!methods.isEmpty()){
                // 放入
                modelNameList.add(dataKey);
            }
        }
        // 去重
        modelNameList = ArrayUtil.removeRepeat(modelNameList);
        // 排序
        modelNameList = commonService.orderByPinYin(modelNameList);
        return modelNameList;
    }

    @Override
    public Map<String, Object> roofModel(String userId, String modelName, String systemState, boolean type) {
        Map<String, Object> map = new HashMap<>();
        // 空校验
        if(StringUtils.isBlank(userId)){
            map.put("result", "false");
            map.put("message", "操作失败，数据异常！");
            return map;
        }
        // 待办设置（不需要校验 awa）
        CommonScheduleSetting scheduleSetting = this.getScheduleSettingByUserId(userId, systemState);

        List<String> roofModelNameList = this.getRoofModelNameBySettingId(scheduleSetting.getId());
        map.put("result", "true");
        if(type){
            // 置顶
            if(!roofModelNameList.contains(modelName)){
                CommonScheduleSettingSub settingSub = new CommonScheduleSettingSub();
                settingSub.setId(Sequence.getSequence());
                settingSub.setPkWpCommonScheduleSetting(scheduleSetting.getId());
                settingSub.setModelName(modelName);
                settingSub.setModelSetting("1");
                commonService.insert(settingSub);
                map.put("message", "置顶成功！");
            }else{
                // 已处于置顶状态
                map.put("result", "false");
                map.put("message", "操作失败，【" + modelName + "】已处于置顶状态！");
                return map;
            }
        }else{
            // 取消置顶
            if(roofModelNameList.contains(modelName)){
                SqlCondition sqlCondition = new SqlCondition();
                sqlCondition.andEqualTo("pk_wp_common_schedule_setting", scheduleSetting.getId());
                sqlCondition.andEqualTo("model_name", modelName);
                sqlCondition.andEqualTo("model_setting", "1");
//                commonService.delete(CommonScheduleSettingSub.class, sqlCondition);
                map.put("message", "取消置顶成功！");
            }else{
                // 已处于未置顶状态
                map.put("result", "false");
                map.put("message", "操作失败，【" + modelName + "】已处于未置顶状态！");
                return map;
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> toggleModel(String userId, String modelName, String systemState, boolean type) {
        Map<String, Object> map = new HashMap<>();
        // 空校验
        if(StringUtils.isBlank(userId)){
            map.put("result", "false");
            map.put("message", "操作失败，数据异常！");
            return map;
        }
        // 待办设置（不需要校验 awa）
        CommonScheduleSetting scheduleSetting = this.getScheduleSettingByUserId(userId, systemState);

        List<String> hideModelNameList = this.getHiddenModelNameBySettingId(scheduleSetting.getId());
        map.put("result", "true");
        if(type){
            // 显示
            if(hideModelNameList.contains(modelName)){
                SqlCondition sqlCondition = new SqlCondition();
                sqlCondition.andEqualTo("pk_wp_common_schedule_setting", scheduleSetting.getId());
                sqlCondition.andEqualTo("model_name", modelName);
                sqlCondition.andEqualTo("model_setting", "2");
                commonService.delete(CommonScheduleSettingSub.class, sqlCondition);
                // 刷新首页代办个数
                this.refreshSchedule(userId);
                map.put("message", "显示成功！");
            }else{
                // 已处于显示状态
                map.put("result", "false");
                map.put("message", "操作失败，【" + modelName + "】已处于显示状态！");
                return map;
            }
        }else{
            // 隐藏
            if(!hideModelNameList.contains(modelName)){
                CommonScheduleSettingSub settingSub = new CommonScheduleSettingSub();
                settingSub.setId(Sequence.getSequence());
                settingSub.setPkWpCommonScheduleSetting(scheduleSetting.getId());
                settingSub.setModelName(modelName);
                settingSub.setModelSetting("2");
                commonService.insert(settingSub);
                // 刷新首页代办个数
                this.refreshSchedule(userId);
                map.put("message", "隐藏成功！");
            }else{
                // 已处于隐藏状态
                map.put("result", "false");
                map.put("message", "操作失败，【" + modelName + "】已处于隐藏状态！");
                return map;
            }
        }
        return map;
    }

    @Override
    public boolean refreshSchedule(String userId) {
        return false;
        //以下用于socket发送消息用的
       /* WSCommonScheduleMessage scheduleMessage = new WSCommonScheduleMessage();
        scheduleMessage.setMessageData("refresh");
        boolean sendResult = commonWebSocketService.sendToUser(userId, scheduleMessage);
        return sendResult;*/
    }

    // -------------------------------------- 私有方法开始 --------------------------------------

    /**
     * 将方法放入map
     * @param map 数据
     * @param modelName 模块名
     * @param method 方法对象
     */
    private void putMethod(Map<String, Set<Method>> map, String modelName, Method method){
        if(map.containsKey(modelName)){
            Set<Method> methodSet = map.get(modelName);
            methodSet.add(method);
        } else {
            Set<Method> methodSet = new HashSet<>();
            methodSet.add(method);
            map.put(modelName, methodSet);
        }
    }

    /**
     * 通过方法，反射获得数据
     * @param methods 方法集
     * @return int
     */
    private int getScheduleCount(Set<Method> methods){
        int count = 0;
        for (Method method : methods) {
            // 获取类实例
            // 反射调用方法
            Class<?> declaringClass = method.getDeclaringClass();
            ApplicationContext applicationContext = SpringContextUtils.getApplicationContext();
            try {
                AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
                String simpleClassName = declaringClass.getSimpleName();
                Object bean;
                if(beanFactory.containsBean(simpleClassName)){
                    // 直接获取
                    bean = beanFactory.getBean(simpleClassName);
                }else{
                    // 创建
                    bean = beanFactory.createBean(declaringClass);
                }
                Class<?> beanClass = bean.getClass();
                // 相同方法
                Method beanMethod = beanClass.getDeclaredMethod(method.getName());
                if(beanMethod != null){
                    // 触发方法
                    count += GlobalFunc.parseInt(beanMethod.invoke(bean));
                }else{
                    // 一般不会有这种情况，但还是记录下吧
                    throw new Exception(declaringClass.getName() + "中方法[" + method.getName() + "]未找到！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    /**
     * 通过方法，反射获得数据
     * @param methods 模块中的方法集，禁止传递不属于同一个模块下的方法集
     * @return int
     */
    private List<CommonScheduleData> getScheduleData(Set<Method> methods){
        List<CommonScheduleData> dataList = new ArrayList<>();

        // 模块名
        String modelName = "";
        CommonScheduleInterface scheduleInterface = null;

        for (Method method : methods) {
            if(scheduleInterface == null){
                // 第一次加载
                scheduleInterface = method.getAnnotation(CommonScheduleInterface.class);
                if(scheduleInterface != null){
                    modelName = GlobalFunc.toString(scheduleInterface.modelName());
                }
            }

            // 获取类实例
            // 反射调用方法
            Class<?> declaringClass = method.getDeclaringClass();
            ApplicationContext applicationContext = SpringContextUtils.getApplicationContext();
            try {
                AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
                String simpleClassName = declaringClass.getSimpleName();
                Object bean;
                if(beanFactory.containsBean(simpleClassName)){
                    // 直接获取
                    bean = beanFactory.getBean(simpleClassName);
                }else{
                    // 创建
                    bean = beanFactory.createBean(declaringClass);
                }
                Class<?> beanClass = bean.getClass();
                // 相同方法
                Method beanMethod = beanClass.getDeclaredMethod(method.getName());
                if(beanMethod != null){
                    // 触发方法
                    List<CommonScheduleData> list = (List<CommonScheduleData>) beanMethod.invoke(bean);
                    if(list != null && !list.isEmpty()){
                        // 设置模块名
                        for (CommonScheduleData scheduleData : list) {
                            scheduleData.setModelName(modelName);
                        }
                        dataList.addAll(list);
                    }
                }else{
                    // 一般不会有这种情况，但还是记录下吧
                    throw new Exception(declaringClass.getName() + "中方法[" + method.getName() + "]未找到！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 判断是否存在任意一个子模块名，如果存在，则给没有子模块名的数据附加子模块名
        boolean hasSubModelName = false;
        for (CommonScheduleData commonScheduleData : dataList) {
            if(StringUtils.isNotBlank(commonScheduleData.getSubModelName())){
                hasSubModelName = true;
                break;
            }
        }
        if(hasSubModelName){
            for (CommonScheduleData commonScheduleData : dataList) {
                if(StringUtils.isBlank(commonScheduleData.getSubModelName())){
                    commonScheduleData.setSubModelName(CommonScheduleConstants.COMMON_SCHEDULE_DEFAULT_MODEL_NAME);
                }
            }
        }

        // 排序
        if (scheduleInterface != null){
            int autoOrder = scheduleInterface.autoOrder();
            if(autoOrder == CommonScheduleConstants.ORDER_TYPE_ASC){
                // 正序
                dataList = commonService.orderByField(dataList, "scheduleTime", true);
            }else if(autoOrder == CommonScheduleConstants.ORDER_TYPE_DESC){
                // 倒序
                dataList = commonService.orderByField(dataList, "scheduleTime", false);
            }
        }

        return dataList;
    }

    /**
     * 过滤常态、应急，系统分类
     * @param methods 方法集合（包含常态和应急）
     * @param systemState 要获取对应的集合
     * @param systemCategoryEnumList 系统分类枚举
     * @return methods
     */
    private Set<Method> getFilterMethodBySystemState(Set<Method> methods, String systemState,
                                                     List<SystemCategoryEnum> systemCategoryEnumList){
        Set<Method> resultMethods = new HashSet<>();
        Iterator<Method> iterator = methods.iterator();
        while (iterator.hasNext()){
            Method method = iterator.next();
            // 匹配成功
            boolean isFit = false;
            // 方法对应的注解
            CommonScheduleInterface annotation = method.getAnnotation(CommonScheduleInterface.class);
            if(annotation != null){
                // 系统类型
                String[] systemStates = annotation.systemStates();
                // 系统分类
                SystemCategoryEnum[] systemCategoryEnums = annotation.systemCategory();
                // 系统类型存在判断
                boolean isExist = false;
                if(systemStates != null){
                    for (String state : systemStates) {
                        if(systemState.equals(state)){
                            isExist = true;
                            break;
                        }
                    }
                }
                isFit = isExist;
                if(isFit && systemCategoryEnums != null){
                    // 系统分类存在判断
                    isExist = false;
                    categoryFor : for (SystemCategoryEnum aSystemCategoryEnum : systemCategoryEnums) {
                        for (SystemCategoryEnum bSystemCategoryEnum : systemCategoryEnumList) {
                            if(aSystemCategoryEnum.index() == bSystemCategoryEnum.index()){
                                isExist = true;
                                break categoryFor;
                            }
                        }
                    }
                    isFit = isExist;
                }
            }

            // 匹配成功，放入集合
            if(isFit){
                resultMethods.add(method);
            }
        }
        return resultMethods;
    }
    // -------------------------------------- 私有方法结束 --------------------------------------
}
