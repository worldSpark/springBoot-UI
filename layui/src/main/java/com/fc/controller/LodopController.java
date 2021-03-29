package com.fc.controller;

import com.alibaba.fastjson.JSONObject;
import com.fc.util.GlobalFunc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @program: 打印
 * @description:
 * @author: Mr.Wan
 * @create: 2021-03-29 11:18
 **/
public class LodopController {

    @RequestMapping("/printByMachineInfoAndUser")
    public String printByMachineInfoAndUser(HttpServletRequest request, HttpServletResponse response, Model model) {
        String thisMachineInfo = GlobalFunc.toString(request.getParameter("thisMachineInfo"));
        //转成json
        Map info = JSONObject.parseObject(thisMachineInfo,Map.class);
        model.addAttribute("info",info);
        return "common/printMachine";
    }

}
