package com.fc.controller;

import com.fc.common.domain.SqlCondition;
import com.fc.mapper.SqlMapper;
import com.fc.model.common.ChangeRelation;
import com.fc.model.common.ChangeRelationSub;
import com.fc.model.common.LogisticsUnit;
import com.fc.service.SysUserService;
import com.fc.service.common.ChangeRelationService;
import com.fc.service.common.CommonService;
import com.fc.util.Sequence;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
* @Description:    转换系数控制层
* @Author:         zhangxh
* @CreateDate:     2019/1/24 18:11
* @Version:        1.0
*/
@Controller
@RequestMapping("/logistics/relation")
public class ChangeRelationController {
    @Autowired
    private ChangeRelationService changeRelationService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private SqlMapper sqlMapper;

    @Autowired
    private SysUserService userService;

    /**
    * @Description:    跳转转换系数列表页面
    * @Author:         zhangxh
    * @CreateDate:     2019/1/25 9:36
    * @Version:        1.0
    */
    @RequestMapping(value="/list",method= RequestMethod.GET)
    public String list(Model model){
        //判断当前登录用户是否包含系统管理员角色
        model.addAttribute("system", userService.judgeIfHavaSystemRole());
        return "/logistics/listChangeRelation";
    }

    /**
    * @Description:    获取转换系数列表数据
    * @Author:         zhangxh
    * @CreateDate:     2019/1/25 9:38
    * @Version:        1.0
    */
    @RequestMapping(value = "/getListData",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void getListData(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map map = changeRelationService.getListData(request);
        Gson gson = new Gson();
        response.getWriter().print(gson.toJson(map));
    }

    /**
     * @Description:    转换系数维护 添加或修改页面
     * @Author:         zhangxh
     * @CreateDate:     2019/1/21 11:05
     * @Version:        1.0
     */
    @RequestMapping(value = "/goToAddOrUpdatePage",method = {RequestMethod.GET,RequestMethod.POST})
    public String goToAddOrUpdatePage(Model model, HttpServletRequest request){
        //查询编辑信息
        String id = request.getParameter("id");
        //获取规则信息
        if(StringUtils.isNotBlank(id)){
            //根据id获取主表信息
            ChangeRelation changeRelation = commonService.getById(ChangeRelation.class,id);
            model.addAttribute("changeRelation",changeRelation);
        }else{
            id = Sequence.getSequence();
            model.addAttribute("addId",id);
        }
        //获取可用的单位列表
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.andEqualTo("is_enable","是");
        List<LogisticsUnit> logisticsUnitList = commonService.getByCondition(LogisticsUnit.class,sqlCondition);
        model.addAttribute("logisticsUnitList",logisticsUnitList);

        return "/logistics/addChangeRelation";
    }

    /**
     * @Description:    获取子表数据
     * @Author:         zhangxh
     * @CreateDate:     2019/1/25 9:38
     * @Version:        1.0
     */
    @RequestMapping(value = "/getSubListData",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void getSubListData(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String id = request.getParameter("id");
        //根据主表id获取子表数据
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.andEqualTo("pk_wp_change_relation",id);
        sqlCondition.orderByASC("order_no");
        List<ChangeRelationSub> subList = commonService.getByCondition(ChangeRelationSub.class,sqlCondition);
        Map map = new HashMap();
        map.put("subList",subList);
        Gson gson = new Gson();
        response.getWriter().print(gson.toJson(map));
    }


    /**
     * @Description:    保存单位数据
     * @Author:         zhangxh
     * @CreateDate:     2019/1/21 11:17
     * @Version:        1.0
     */
    @RequestMapping(value = "/addOrUpdate",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void addOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String res = request.getParameter("res");
        JSONArray list = JSONArray.fromObject("[" + res + "]");
        Iterator<Object> it = list.iterator();

        while (it.hasNext()){
            JSONObject ob = (JSONObject) it.next();
            String relationName = ob.getString("relationName");
            String isEnable = ob.getString("isEnable");
            String addId = ob.getString("addId");
            if(StringUtils.isNotBlank(addId)){//如果是新增，则新增全部数据
                //添加主表数据
                ChangeRelation changeRelation = new ChangeRelation();
                changeRelation.setId(addId);
                changeRelation.setRelationName(relationName);
                changeRelation.setIsEnable(isEnable);
                commonService.insert(changeRelation);

                String subList = ob.getString("subList");

                JSONArray sub = JSONArray.fromObject(subList);
                Iterator<Object> subIt = sub.iterator();
                //迭代子表数据，并进行新增
                int i = 0;
                while(subIt.hasNext()){
                    JSONObject subOb = (JSONObject) subIt.next();
                    String pkWpLogisticsUnitBase = subOb.getString("pkWpLogisticsUnitBase");
                    String pChange = subOb.getString("pChange");
                    String pkWpLogisticsUnitChange = subOb.getString("pkWpLogisticsUnitChange");

                    //新增子表数据
                    ChangeRelationSub changeRelationSub = new ChangeRelationSub();
                    changeRelationSub.setId(Sequence.getSequence());
                    changeRelationSub.setPkWpLogisticsUnitBase(pkWpLogisticsUnitBase);
                    changeRelationSub.setPkWpLogisticsUnitChange(pkWpLogisticsUnitChange);
                    changeRelationSub.setChangeProportion(pChange);
                    changeRelationSub.setPkWpChangeRelation(addId);
                    changeRelationSub.setOrderNo(i+"");
                    commonService.insert(changeRelationSub);
                    i++;
                }
            }else{
                String id = ob.getString("id");
                ChangeRelation changeRelation = commonService.getById(ChangeRelation.class,id);
                changeRelation.setRelationName(relationName);
                changeRelation.setIsEnable(isEnable);
                commonService.updateSelective(changeRelation);

                //根据主表id获取子表数据
                SqlCondition sqlCondition = new SqlCondition();
                sqlCondition.andEqualTo("pk_wp_change_relation",id);
                List<ChangeRelationSub> sList = commonService.getByCondition(ChangeRelationSub.class,sqlCondition);

                String subList = ob.getString("subList");

                JSONArray sub = JSONArray.fromObject(subList);
                Iterator<Object> subIt = sub.iterator();
                List<String> idList = new ArrayList<>();
                int j = 0;
                while(subIt.hasNext()){
                    JSONObject subOb = (JSONObject) subIt.next();
                    String pkWpLogisticsUnitBase = subOb.getString("pkWpLogisticsUnitBase");
                    String pChange = subOb.getString("pChange");
                    String pkWpLogisticsUnitChange = subOb.getString("pkWpLogisticsUnitChange");
                    String subId = subOb.getString("subId");

                    if(StringUtils.isBlank(subId)){
                        //新增子表数据
                        ChangeRelationSub changeRelationSub = new ChangeRelationSub();
                        changeRelationSub.setId(Sequence.getSequence());
                        changeRelationSub.setPkWpLogisticsUnitBase(pkWpLogisticsUnitBase);
                        changeRelationSub.setPkWpLogisticsUnitChange(pkWpLogisticsUnitChange);
                        changeRelationSub.setChangeProportion(pChange);
                        changeRelationSub.setPkWpChangeRelation(id);
                        changeRelationSub.setOrderNo(j+"");
                        commonService.insert(changeRelationSub);

                        idList.add(changeRelationSub.getId());
                    }else{
                        for (int i = 0; i < sList.size(); i++) {
                            ChangeRelationSub crSub = sList.get(i);
                            if(crSub.getId().equals(subId)){
                                crSub.setPkWpLogisticsUnitBase(pkWpLogisticsUnitBase);
                                crSub.setPkWpLogisticsUnitChange(pkWpLogisticsUnitChange);
                                crSub.setChangeProportion(pChange);
                                //crSub.setPkWpChangeRelation(id);
                                crSub.setOrderNo(j+"");
                                commonService.updateSelective(crSub);
                            }
                        }
                        idList.add(subId);
                    }
                    j++;

                }
                // 删除数据库中多余数据
                SqlCondition sqlCondition1 = new SqlCondition();
                sqlCondition1.andEqualTo("pk_wp_change_relation", id);
                sqlCondition1.andNotIn("id", idList);
                commonService.delete(ChangeRelationSub.class, sqlCondition1);
            }

        }



        Map<String,Object> map = new HashMap<>();
        map.put("flag","flag");
        Gson gson = new Gson();
        response.getWriter().print(gson.toJson(map));
    }

    /**
     * @Description:    删除 编号规则
     * @Author:         zhangxh
     * @CreateDate:     2019/1/18 15:16
     * @Version:        1.0
     */
    @ResponseBody
    @RequestMapping(value = "/delRelation",method = {RequestMethod.GET,RequestMethod.POST})
    public Map delRelation( HttpServletResponse response, HttpServletRequest request)
            throws Exception{
        //是否成功
        String flag = "success";
        String id= request.getParameter("id");

        //查询是否有添加子表数据
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.andEqualTo("pk_wp_change_relation",id);
        List<ChangeRelationSub> subList = commonService.getByCondition(ChangeRelationSub.class,sqlCondition);

        if(subList.size()>0){
            for (int i=0;i<subList.size();i++){
                commonService.delete(ChangeRelationSub.class,subList.get(i).getId());
            }
        }
        commonService.delete(ChangeRelation.class,id);
        Map map = new HashMap();
        map.put("flag",flag);
        return map;
    }
}
