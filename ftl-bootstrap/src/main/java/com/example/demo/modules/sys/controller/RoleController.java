
package com.example.demo.modules.sys.controller;

import com.example.demo.core.annotation.SysLog;
import com.example.demo.core.entity.ProcessResult;
import com.example.demo.core.result.PageResult;
import com.example.demo.modules.sys.model.SysRole;
import com.example.demo.modules.sys.service.RoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.demo.core.entity.ProcessResult.ERROR;

/**
 * @author qjp
 * @since 2015-12-19 11:10
 */
@Api(value ="角色管理模块", description = "角色管理Api",tags = {"角色管理操作接口"})
@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "角色列表视图",notes = "角色列表视图")
    @GetMapping("/list")
    public ModelAndView list() {
        return new ModelAndView("/modules/sys/sysRole/list");
    }

    @ApiOperation(value = "获取角色列表",notes = "获取角色列表")
    @PostMapping
    public PageResult<SysRole> getAll(SysRole sysRole, String keyword, HttpServletRequest request) {
        List<SysRole> roleList = roleService.getAll(sysRole,keyword);
        return new PageResult(new PageInfo<SysRole>(roleList));
    }

    @ApiOperation(value = "批量删除角色",notes = "批量删除角色")
    @SysLog("批量删除角色")
    @PostMapping(value = "/batchDelete")
    public ProcessResult batchDelete(@ApiParam(name = "ids",value="角色ID数组",required = true) @RequestParam("ids[]") Integer[] ids) {
        try {
            roleService.batchDelete(ids);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "保存角色",notes = "保存角色")
    @SysLog("保存角色")
    @PostMapping(value = "/saveOrUpdate")
    public ProcessResult saveOrUpdate(@ApiParam(name = "sysRole",value="角色实体",required = true) SysRole sysRole) {
        try {
            roleService.saveOrUpdate(sysRole);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "获取角色信息",notes = "获取角色信息")
    @PostMapping(value = "/view/{id}")
    public SysRole view(@ApiParam(name = "id",value="角色ID",required = true) @PathVariable Integer id) {
        SysRole sysRole = roleService.getById(id);
        return sysRole;
    }

    @ApiOperation(value = "删除角色",notes = "删除角色")
    @SysLog("删除角色")
    @PostMapping(value = "/delete/{id}")
    public ProcessResult delete(@ApiParam(name = "id",value="角色ID",required = true) @PathVariable Integer id) {
        try {
            roleService.deleteById(id);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "角色信息视图",notes = "角色信息视图")
    @GetMapping("/form")
    public ModelAndView form() {
        return new ModelAndView("/modules/sys/sysRole/form");
    }

    @ApiOperation(value = "分配用户视图",notes = "分配用户视图")
    @GetMapping("/allotUserLayer")
    public ModelAndView allotUserLayer() {
        return new ModelAndView("/modules/sys/sysRole/allotUser");
    }

    @ApiOperation(value = "分配菜单视图",notes = "分配菜单视图")
    @GetMapping("/allotMenuLayer/{id}")
    public ModelAndView allotMenuLayer(@ApiParam(name = "id",value="角色ID",required = true) @PathVariable Integer id,
                                       HttpServletRequest request) {
        request.getSession().setAttribute("roleId",id);
        return new ModelAndView("/modules/sys/sysRole/allotMenu");
    }
}
