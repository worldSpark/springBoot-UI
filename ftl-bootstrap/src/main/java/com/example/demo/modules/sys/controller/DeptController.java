
package com.example.demo.modules.sys.controller;

import com.example.demo.core.annotation.SysLog;
import com.example.demo.core.entity.ProcessResult;
import com.example.demo.core.result.PageResult;
import com.example.demo.modules.sys.model.SysDept;
import com.example.demo.modules.sys.model.SysDeptUser;
import com.example.demo.modules.sys.model.SysUser;
import com.example.demo.modules.sys.service.DeptService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static com.example.demo.core.entity.ProcessResult.ERROR;

/**
 * @author qjp
 * @since 2015-12-19 11:10
 */
@Api(value ="部门管理模块", description = "部门管理Api",tags = {"部门管理操作接口"})
@RestController
@RequestMapping("/sys/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @ApiOperation(value = "部门列表视图",notes = "部门列表视图")
    @GetMapping("/list")
    public ModelAndView list() {
        return new ModelAndView("/modules/sys/sysDept/list");
    }

    @ApiOperation(value = "获取部门列表",notes = "获取部门列表")
    @PostMapping
    public List<SysDept> getAll() {
        return deptService.getAll();
    }

    @ApiOperation(value = "获取树状部门列表",notes = "获取树状部门列表")
    @PostMapping("/treeList")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String,Object> treeList() {
        return deptService.treeList();
    }

    @ApiOperation(value = "获取部门信息",notes = "获取部门信息")
    @GetMapping("/getDeptById/{id}")
    public SysDept getDeptById(@ApiParam(name = "id",value="部门ID",required = true) @PathVariable Integer id){
        return deptService.getDeptById(id);
    }

    @ApiOperation(value = "保存部门信息",notes = "保存部门信息")
    @SysLog("保存部门信息")
    @PostMapping(value = "/saveOrUpdate")
    public ProcessResult saveOrUpdate(@ApiParam(name = "sysDept",value="部门实体",required = true) SysDept sysDept) {
        try {
            deptService.saveOrUpdate(sysDept);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "删除部门",notes = "删除部门")
    @SysLog("删除部门")
    @PostMapping(value = "/delete/{id}")
    public ProcessResult delete(@ApiParam(name = "id",value="部门ID",required = true) @PathVariable Integer id) {
        try {
            deptService.deleteById(id);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    //已分配列表
    @ApiOperation(value = "已分配部门的用户列表",notes = "已分配部门的用户列表")
    @GetMapping("/isAllotUserList")
    public PageResult<SysUser> isAllotUserList(@ApiParam(name = "sysUser",value="用户实体",required = true) SysUser sysUser,
                                               @ApiParam(name = "deptId",value="部门ID",required = true) Integer deptId){
        List<SysUser> list= deptService.isAllotUserList(sysUser,deptId);
        if(list.size()>0){
            for (SysUser user:list){
                SysDeptUser deptUser = deptService.getSysDeptUser(user.getId(), deptId);
                if(deptUser!=null){
                    user.setIsmaster(deptUser.getIsmaster());
                }
            }
        }
        return new PageResult<SysUser>(new PageInfo<>(list));
    }

    //未分配列表
    @ApiOperation(value = "未分配部门的用户列表",notes = "未分配部门的用户列表")
    @GetMapping("/isUnAllotUserList")
    public PageResult<SysUser> isUnAllotUserList(@ApiParam(name = "sysUser",value="用户实体",required = true) SysUser sysUser,
                                                 @ApiParam(name = "deptId",value="部门ID",required = true) Integer deptId){
        List<SysUser> list= deptService.isUnAllotUserList(sysUser,deptId);
        if(list.size()>0){
            for (SysUser user:list){
                SysDeptUser deptUser = deptService.getSysDeptUser(user.getId(), deptId);
                if(deptUser!=null){
                    user.setIsmaster(deptUser.getIsmaster());
                }
            }
        }
        return new PageResult<SysUser>(new PageInfo<>(list));
    }

    //分配或移除 type 0分配 ,1移除
    @ApiOperation(value = "分配或移除部门的用户",notes = "分配或移除部门的用户")
    @PostMapping(value = "/allotUserByDept")
    public ProcessResult allotUserByDept(@ApiParam(name = "userId",value="用户ID",required = true) Integer userId,
                                         @ApiParam(name = "deptId",value="部门ID",required = true) Integer deptId,
                                         @ApiParam(name = "type",value="操作类型：type 0分配 ,1移除",required = true) Integer type) {
        try {
            deptService.allotUserByDept(userId,deptId,type);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    //设置部门负责人
    @ApiOperation(value = "设置部门负责人",notes = "设置部门负责人")
    @PostMapping(value = "/setMasterUserByDept")
    public ProcessResult setMasterUserByDept(@ApiParam(name = "userId",value="用户ID",required = true) Integer userId,
                                             @ApiParam(name = "deptId",value="部门ID",required = true) Integer deptId,
                                             @ApiParam(name = "type",value="操作类型：0直接取消管理员，1先把该部门的所有用户都清除管理员再设定管理员",required = true) Integer type) {
        try {
            deptService.setMasterUserByDept(userId,deptId,type);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "分配用户视图",notes = "分配用户视图")
    @GetMapping("/showAllot")
    public ModelAndView showAllot() {
        return new ModelAndView("/modules/sys/sysDept/allotUser");
    }

}
