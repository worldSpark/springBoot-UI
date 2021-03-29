package com.fc.controller.template;

import com.fc.model.auto.TsysUser;
import com.fc.model.template.ATemplate;
import com.fc.service.common.CommonScheduleService;
import com.fc.service.template.ATemplateService;
import com.fc.shiro.util.ShiroUtils;
import com.fc.util.GlobalFunc;
import com.fc.util.ServletUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 实体类模板Controller
 * @author xxx
 * @date 2020年1月6日09:59:31
 * @version 1.0.0
 */
@Controller
@RequestMapping("/template/aTemplate")
public class ATemplateController {

    @Autowired
    private ATemplateService aTemplateService;

    @Autowired
    private CommonScheduleService commonScheduleService;

    /**
     * list页面
     * @return jsp
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(){
        return "/template/listATemplate";
    }

    /**
     * 分页获取模板信息
     * @param request request
     * @return data
     */
    @ResponseBody
    @RequestMapping(value="/getATemplateList",method= {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> getATemplateList(HttpServletRequest request) {
        Map<String, Object> map = aTemplateService.getATemplateList(request);
        return map;
    }

    /**
     * 新增、修改页面(弹窗)
     * @return jsp
     */
    @RequestMapping(value = "/addOrEditATemplate", method = {RequestMethod.GET, RequestMethod.POST})
    public String addOrEditATemplate(HttpServletRequest request, Model model){
        String id = GlobalFunc.toString(request.getParameter("id"));
        if(StringUtils.isNotBlank(id)){
            ATemplate aTemplate = aTemplateService.getById(id);
            model.addAttribute("aTemplate", aTemplate);
        }
        return "/template/addOrEditATemplate";
    }

    /**
     * 新增、修改页面(跳转)
     * @return jsp
     */
    @RequestMapping(value = "/addOrEditATemplateSecondWay", method = {RequestMethod.GET, RequestMethod.POST})
    public String addOrEditATemplateSecondWay(HttpServletRequest request, Model model){
        String id = GlobalFunc.toString(request.getParameter("id"));
        if(StringUtils.isNotBlank(id)){
            ATemplate aTemplate = aTemplateService.getById(id);
            model.addAttribute("aTemplate", aTemplate);
        }
        return "/template/addOrEditATemplateSecondWay";
    }

    /**
     * 获得动态下拉多选框数据
     * @return data
     */
    @ResponseBody
    @RequestMapping(value = "/getDynamicData", method = {RequestMethod.GET, RequestMethod.POST})
    public List<Map<String, Object>> getDynamicData(){
        List<Map<String, Object>> list = aTemplateService.getDynamicData();
        return list;
    }

    /**
     * 检查重复项
     * @param request request
     * @return data
     */
    @ResponseBody
    @RequestMapping(value = "/checkRepeat", method = {RequestMethod.GET, RequestMethod.POST})
    public boolean checkRepeat(HttpServletRequest request){
        String id = GlobalFunc.toString(request.getParameter("id"));
        String normalInput = GlobalFunc.toString(request.getParameter("normalInput"));
        boolean isRepeat = aTemplateService.checkRepeat(id, normalInput);
        return isRepeat;
    }

    /**
     * 保存数据
     * @param request request
     * @return data
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> save(HttpServletRequest request) throws InterruptedException {
        return aTemplateService.save(request);
    }

    /**
     * 删除数据
     * @param request request
     * @return data
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> delete(HttpServletRequest request) throws InterruptedException {
        String id = GlobalFunc.toString(request.getParameter("id"));
        int count = aTemplateService.delete(id);
        String message = count > 0 ? "删除成功！" : "操作失败！";
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        return map;
    }

    /**
     * 更改启用状态
     * @param request request
     * @return data
     */
    @ResponseBody
    @RequestMapping(value = "/changeStatus", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> changeStatus(HttpServletRequest request) throws InterruptedException {
        Map<String, Object> map = new HashMap<>();
        // id
        String id = GlobalFunc.toString(request.getParameter("id"));
        // 状态
        String enabled = GlobalFunc.toString(request.getParameter("enabled"));

        if(StringUtils.isBlank(id) || StringUtils.isBlank(enabled)){
            map.put("result", "error");
            map.put("message", "操作失败！");
            return map;
        }

        ATemplate aTemplate = aTemplateService.getById(id);
        int count = 0;
        if(aTemplate != null){
            aTemplate.setEnabled(enabled);
            count = aTemplateService.update(aTemplate);
        }

        String result = count > 0 ? "success" : "error";
        String message = count > 0 ? "操作成功！" : "操作失败！";
        map.put("result", result);
        map.put("message", message);
        return map;
    }

    /**
     * 刷新待办
     * @param request request
     * @return data
     */
    @ResponseBody
    @RequestMapping(value = "/refreshSchedule", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> refreshSchedule(HttpServletRequest request) throws InterruptedException {
//        UserVO user = SecurityUser.getCurrentUserVO();
        TsysUser user = ShiroUtils.getUser();
        boolean sendResult = commonScheduleService.refreshSchedule(user.getId());

        String result = sendResult ? "success" : "error";
        String message = sendResult ? "操作成功！" : "用户不在线！";

        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        map.put("message", message);
        return map;
    }
}
