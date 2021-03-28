package com.fc.controller;

import com.fc.common.domain.SqlCondition;
import com.fc.model.logistics.CodeRuleSub;
import com.fc.model.logistics.CodeRules;
import com.fc.service.common.CodeRulesService;
import com.fc.service.common.CommonService;
import com.fc.service.SysUserService;
import com.fc.util.GlobalFunc;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Description:    编号规则维护实体类
* @Author:         zhangxh
* @CreateDate:     2019/1/18 9:08
* @Version:        1.0
*/
@Controller
@RequestMapping("/logistics/codeRules")
public class CodeRulesController {

    @Autowired
    private CodeRulesService codeRulesService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private SysUserService userService;

    /**
     * 跳转列表页面
     * @return
     */
    @RequestMapping(value="/list",method= RequestMethod.GET)
    public String list(Model model){
        //判断当前登录用户是否包含系统管理员角色
        //model.addAttribute("system", userService.judgeIfHavaSystemRole());
        return "/logistics/listCodeRules";
    }

    /**
     * 获取编号规则数据
     */
    @RequestMapping(value = "/getListData",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void getListData(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map map = codeRulesService.getListData(request);
        Gson gson = new Gson();
        response.getWriter().print(gson.toJson(map));
    }

    /**
    * @Description:    添加/修改页面
    * @Author:         zhangxh
    * @CreateDate:     2019/1/18 13:59
    * @Version:        1.0
    */
    @RequestMapping(value = "/goToAddOrUpdatePage",method = {RequestMethod.GET,RequestMethod.POST})
    public String goToAddOrUpdatePage(Model model, HttpServletRequest request){
        //查询编辑信息
        String id = GlobalFunc.toString(request.getParameter("id"));
        model.addAttribute("id",id);
        return "/logistics/addCodeRules";
    }

    /**
     * @Description:    保存或修改编号规则数据
     * @Author:         zhangxh
     * @CreateDate:     2019/1/18 14:39
     * @Version:        1.0
     */
    @ResponseBody
    @RequestMapping(value = "/addOrUpdate",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> addOrUpdate(HttpServletRequest request){
        // json 数据
        String data = GlobalFunc.toString(request.getParameter("data"));
        Map<String, Object> message = codeRulesService.addOrUpdate("[" + data + "]");
        return message;
    }

    /**
     * @Description 获得规则数据
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = "/getData",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> getData(HttpServletRequest request){
        String id = GlobalFunc.toString(request.getParameter("id"));
        // 主表
        CodeRules codeRules = codeRulesService.getById(id);
        // 子表
        List<CodeRuleSub> subList = codeRulesService.getSubList(id);

        // 转Map
        Map<String, Object> codeRulesMap = commonService.convertObjectToMap(codeRules);
        List<Map<String, Object>> subListMap = commonService.convertObjectListToMapList(subList);
        codeRulesMap.put("subList", subListMap);

        return codeRulesMap;
    }

    /**
     * @Description:    删除 编号规则
     * @Author:         zhangxh
     * @CreateDate:     2019/1/18 15:16
     * @Version:        1.0
     */
    @ResponseBody
    @RequestMapping(value = "/delRule",method = {RequestMethod.GET,RequestMethod.POST})
    public Map delRule( HttpServletResponse response, HttpServletRequest request)
            throws Exception{
        //是否成功
        String flag = "success";
        String id= request.getParameter("id");

        //查询是否有添加子表数据
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.andEqualTo("pk_wp_code_rules",id);
        List<CodeRuleSub> subList = commonService.getByCondition(CodeRuleSub.class,sqlCondition);

        if(subList.size()>0){
            for (int i=0;i<subList.size();i++){
                commonService.delete(CodeRuleSub.class,subList.get(i).getId());
            }
        }
        commonService.delete(CodeRules.class,id);
        Map map = new HashMap();
        map.put("flag",flag);
        return map;
    }
}
