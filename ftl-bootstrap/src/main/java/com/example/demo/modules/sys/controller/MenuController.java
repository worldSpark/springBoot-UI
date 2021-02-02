
package com.example.demo.modules.sys.controller;

import com.example.demo.core.annotation.SysLog;
import com.example.demo.core.entity.ProcessResult;
import com.example.demo.modules.sys.model.SysMenu;
import com.example.demo.modules.sys.model.SysUser;
import com.example.demo.modules.sys.service.MenuService;
import com.example.demo.modules.sys.vo.SysMenuVo;
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

@Api(value ="菜单管理模块", description = "菜单管理Api",tags = {"菜单管理操作接口"})
@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "菜单列表视图",notes = "菜单列表视图")
    @GetMapping("/list")
    public ModelAndView list() {
        return new ModelAndView("/modules/sys/sysMenu/list");
    }

    @ApiOperation(value = "选择图标视图",notes = "选择图标视图")
    @GetMapping("/selIcons")
    public ModelAndView selIcons() {
        return new ModelAndView("/modules/sys/sysMenu/icons");
    }

    @ApiOperation(value = "获取菜单树状列表",notes = "获取菜单树状列表")
    @PostMapping("/treeList")
    public List<SysMenuVo> treeList(){
        return menuService.getTreeList();
    }

    @ApiOperation(value = "获取当前登陆用户的权限菜单列表",notes = "获取当前登陆用户的权限菜单列表")
    @PostMapping("/treeListPermission")
    public List<SysMenuVo> treeListPermission(HttpServletRequest request){
        SysUser sysUser=(SysUser)request.getSession().getAttribute("user");
        return menuService.treeListPermission(sysUser);
    }

    @ApiOperation(value = "获取菜单列表",notes = "获取菜单列表")
    @PostMapping
    public List<SysMenu> getAll(@ApiParam(name = "sysMenu",value="菜单实体",required = true) SysMenu sysMenu) {
        List<SysMenu> sysMenuList = menuService.getAll(sysMenu);
       return sysMenuList;
    }

    @ApiOperation(value = "获取菜单信息",notes = "获取菜单信息")
    @PostMapping(value = "/selectMenuById/{id}")
    public SysMenu view(@ApiParam(name = "id",value="菜单ID",required = true) @PathVariable Integer id) {
        if(id!=null){
            SysMenu sysMenu = menuService.getById(id);
            return sysMenu;
        }
        return null;
    }

    @ApiOperation(value = "新增或修改菜单",notes = "新增或修改菜单")
    @SysLog("新增或修改菜单")
    @PostMapping(value = "/saveOrUpdate")
    public ProcessResult saveOrUpdate(@ApiParam(name = "sysMenu",value="菜单实体",required = true) SysMenu sysMenu) {
        try {
            menuService.saveOrUpdate(sysMenu);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "删除菜单",notes = "删除菜单")
    @SysLog("删除菜单")
    @PostMapping(value = "/delete/{id}")
    public ProcessResult delete(@ApiParam(name = "id",value="菜单ID",required = true) @PathVariable Integer id) {
        try {
            //----start----防止测试用户胡乱删除数据
            SysMenu menu = menuService.getById(id);
            if(menu!=null && "0".equals(menu.getIsSysMenu())){
                throw new Exception("请高抬贵手！验证【删除功能】可自己新增菜单再删除！");
            }
            //----end----防止测试用户胡乱删除数据
            menuService.deleteById(id);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "获取某角色拥有的菜单权限列表",notes = "获取某角色拥有的菜单权限列表")
    @PostMapping("/getSelMenuPermission/{roleId}")
    public List<SysMenu> getSelMenuPermission(@ApiParam(name = "roleId",value="角色ID",required = true) @PathVariable Integer roleId){
        return menuService.getSelMenuPermission(roleId);
    }

    @ApiOperation(value = "保存菜单权限",notes = "保存菜单权限")
    @SysLog("保存菜单权限")
    @PostMapping("/saveMenuPermission")
    public ProcessResult saveMenuPermission(@ApiParam(name = "roleId",value="角色ID",required = true) Integer roleId,
                                            @ApiParam(name = "ids",value="菜单ID数组",required = true) @RequestParam("ids[]") Integer[] ids){
        try {
            menuService.saveMenuPermission(roleId,ids);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

}
