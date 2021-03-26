package com.fc.service.common.Impl;

import com.fc.common.domain.CommonClassInfo;
import com.fc.common.domain.SqlCondition;
import com.fc.mapper.SqlMapper;
import com.fc.model.common.CodeRuleSub;
import com.fc.model.common.CodeRules;
import com.fc.service.common.CodeRulesService;
import com.fc.service.common.CommonService;
import com.fc.util.ChineseConvertToPinYin;
import com.fc.util.GlobalFunc;
import com.fc.util.Pinyin4JHelper;
import com.fc.util.Sequence;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* @Description:    编号规则维护Impl
* @Author:         zhangxh
* @CreateDate:     2019/1/18 9:12
* @Version:        1.0
*/
@Service
@Transactional
public class CodeRulesServiceImpl implements CodeRulesService {

    @Autowired
    private SqlMapper sqlMapper;

    @Autowired
    private CommonService commonService;

    @Override
    public void insert(CodeRules codeRules) {
        commonService.insert(codeRules);
    }

    @Override
    public void update(CodeRules codeRules) {
        commonService.updateSelective(codeRules);
    }

    @Override
    public void insertSub(CodeRuleSub sub) {
        commonService.insert(sub);
    }

    @Override
    public void updateSub(CodeRuleSub sub) {
        commonService.updateSelective(sub);
    }

    @Override
    public CodeRules getById(String id) {
        CodeRules codeRules = commonService.getById(CodeRules.class,id);
        return codeRules;
    }

    @Override
    public Map<String, Object> addOrUpdate(String data) {
        JSONArray jsonArray = JSONArray.fromObject(data);
        Iterator it = jsonArray.iterator();
        while (it.hasNext()){
            JSONObject jsonObject = (JSONObject) it.next();
            String id = jsonObject.getString("id");
            String modularName = jsonObject.getString("modularName");
            String isEnable = jsonObject.getString("isEnable");

            // 组装主表数据
            CodeRules codeRules = new CodeRules();
            codeRules.setId(id);
            codeRules.setModularName(modularName);
            codeRules.setIsEnable(isEnable);
            if(StringUtils.isEmpty(id)){
                id = Sequence.getSequence();
                codeRules.setId(id);
                this.insert(codeRules);
            }else {
                this.update(codeRules);
            }

            // 子数据
            JSONArray subList = jsonObject.getJSONArray("subList");
            Iterator subIt = subList.iterator();
            List<String> codeRuleSubIdList = new ArrayList<>();
            // 序号
            int orderNo = 1;
            while(subIt.hasNext()){
                JSONObject subJson = (JSONObject) subIt.next();
                String subId = subJson.getString("id");
                String type = subJson.getString("type");
                String defaultValue = subJson.getString("defaultValue");

                // 组装子数据
                CodeRuleSub sub = new CodeRuleSub();
                sub.setId(subId);
                sub.setType(type);
                sub.setDefaultValue(defaultValue);
                sub.setPkWpCodeRules(codeRules.getId());
                sub.setOrderNo(orderNo + "");
                orderNo++;

                if(StringUtils.isBlank(subId)){
                    subId = Sequence.getSequence();
                    sub.setId(subId);
                    this.insertSub(sub);
                }else{
                    this.updateSub(sub);
                }
                codeRuleSubIdList.add(sub.getId());
            }

            // 删除数据库中多余数据
            SqlCondition sqlCondition = new SqlCondition();
            sqlCondition.andEqualTo("pk_wp_code_rules", codeRules.getId());
            sqlCondition.andNotIn("id", codeRuleSubIdList);
            commonService.delete(CodeRuleSub.class, sqlCondition);
        }

        Map<String,Object> message = new HashedMap();
        message.put("flag","true");
        return message;
    }

    /**
    * @Description:    获取数据
    * @Author:         zhangxh
    * @CreateDate:     2019/1/18 9:40
    * @Version:        1.0
    */
    @Override
    public Map getListData(HttpServletRequest request) {
        int limit = Integer.parseInt(request.getParameter("limit"));
        int offset = Integer.parseInt(request.getParameter("offset"));
        PageHelper.startPage(offset,limit);

        String sql = " select * from wp_code_rules where 1=1";
        String searchName = request.getParameter("searchName");

        StringBuffer where = new StringBuffer();
        if(StringUtils.isNotBlank(searchName)){
            where.append(" and modular_name like '%"+searchName+"%'");
        }
        List<Map<String,Object>> rulesList = sqlMapper.sqlQueryList(sql+where);

        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(rulesList);
        Map<String,Object> map = new HashMap<>();
        map.put("rows",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return map;
    }

    /**
    * @Description:    根据id获取规则数据
    * @Author:         zhangxh
    * @CreateDate:     2019/1/18 14:02
    * @Version:        1.0
    */
    @Override
    public List<Map<String,Object>> getListById(String id) {
        String sql = "select * from wp_code_rules where id='"+id+"'";
        List<Map<String,Object>> rList = sqlMapper.sqlQueryList(sql);
        return rList;
    }

    @Override
    public List<CodeRuleSub> getSubList(String id) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.andEqualTo("pk_wp_code_rules",id);
        sqlCondition.orderByASC("order_no");
        List<CodeRuleSub> subList = commonService.getByCondition(CodeRuleSub.class,sqlCondition);
        return subList;
    }

    /**
    * @Description:    生成编号规则方法(通用)
    * @Author:         zhangxh
    * @CreateDate:     2019/1/20 17:24
    * @Version:        1.0
    */
    @Override
    public <T> String getNumber(String ruleName,String columnName,Class<T> objectClass) {
        // 类信息↓
//        Class objectClass = obj.getClass();
        CommonClassInfo classInfo = commonService.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();

        //根据规则名称查询
        SqlCondition nameCondition = new SqlCondition();
        nameCondition.andEqualTo("modular_name",ruleName);
        List<CodeRules> codeRulesList = commonService.getByCondition(CodeRules.class,nameCondition);

        if(codeRulesList.size()==0){
            return "请维护编号规则！";
        }
        CodeRules codeRules = codeRulesList.get(0);
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.andEqualTo("pk_wp_code_rules",codeRules.getId());
        sqlCondition.orderByASC("order_no");
        //获取规则子表数据
        List<CodeRuleSub> subList = commonService.getByCondition(CodeRuleSub.class,sqlCondition);

        //定义返回字符串(编号)
        StringBuffer code = new StringBuffer();
        int count = 0;
        if(subList.size()>0){
            for (int i = 0; i < subList.size(); i++) {
                String type = subList.get(i).getType();
                String defaultValue = subList.get(i).getDefaultValue();
                //如果type为"字符"
                if("字符".equals(type)){
                    code.append(defaultValue);
                }
                //如果type为"日期"
                if("日期".equals(type)){
                    SimpleDateFormat df = new SimpleDateFormat(defaultValue);//设置日期格式
                    code.append(df.format(new Date()));
                }
                //如果type为"流水号"
                if("流水号".equals(type)){
                    int len = Integer.parseInt(defaultValue);
                    String nextNumber = this.getNextFlowNumber(len,count,code,tableName,columnName);
                    code.append(nextNumber);
                }
            }
        }

        return code.toString();
    }

    @Override
    public String getContentByGenerateNumber(String columnName,String codeColumn, Class objectClass) {
        //定义返回字符串(编号)
        StringBuffer code = new StringBuffer();
        //获取年份
        int year = Calendar.getInstance().get(Calendar.YEAR);
        code.append(year);
        // 类信息
        CommonClassInfo classInfo = commonService.getCommonClassInfo(objectClass);
        // 表名
        String tableName = classInfo.getTableName();
        //将字段名称转换成拼音首字母大写
        String pinYinCode = this.getPinYinCode(columnName);
        //判断字母length是否小于等于4
        if(pinYinCode.length()<=4){
            code.append(ChineseConvertToPinYin.getPinym(pinYinCode).toUpperCase());
        }else {
            code.append(ChineseConvertToPinYin.getPinym(pinYinCode.substring(0,4)).toUpperCase());
        }
        //生成6位的流水号
        String nextFlowNumber = this.getNextFlowNumber(6, 0, code, tableName, codeColumn);
        code.append(nextFlowNumber);
        return code.toString();
    }

    /**
     * 生成拼音首字母大写
     * @param chinese
     * @return
     */
    private String getPinYinCode(String chinese){
        Pinyin4JHelper helper = new Pinyin4JHelper();
        char[] array = chinese.toCharArray();
        String pinYin = "";
        for (char c : array) {
            String p = GlobalFunc.toString(Pinyin4JHelper.getCharacterPinYin(c));
            // 取第一位
            if(p.length() >= 1){
                p = p.substring(0,1);
                pinYin += p;
            }
        }
        return pinYin;
    }

    /**
     * @Description 获得第一个编号
     * @param length 编号长度
     * @return firstNum
     */
    private String getFirstNumber(int length){
        String firstNum = String.format("%0"+length+"d",1);
        return firstNum;
    }


    /**
     * @Description 生成下一流水号
     * @param tableName 查询用的表名
     * @param columnName 查询的字段名
     * @return 下一流水号
     */
    private String getNextFlowNumber(int len,int count,StringBuffer code,String tableName,String columnName){
        StringBuffer underLine = new StringBuffer();
        for (int j=0;j<len;j++){
            underLine.append("_");
            count++;
        }
        String codeString = code.toString()+underLine;
        String nextCode = "";

            //获取第一个_开始的下标
        int begin = codeString.indexOf("_")+1;
        //int end = begin+count;

        //System.out.println(codeString.substring(begin,end));
        Map map = new HashMap();

        String sql = "SELECT max(cast(SUBSTRING(" + columnName + ", " + begin + ","+ count + ") as INT)) as last_number " +
                " from "+tableName+" where "+columnName+" like '"+code+underLine+"'";
        map = sqlMapper.sqlQueryOne(sql);

        if(map == null){
            nextCode = getFirstNumber(count);
        }else{
            int lastNumber = GlobalFunc.parseInt(map.get("last_number"));
            int newNumber = lastNumber + 1;
            //获取流水号长度，并转化正整数
            nextCode = String.format("%0" + count + "d",newNumber);
        }
        return nextCode;
    }

}
