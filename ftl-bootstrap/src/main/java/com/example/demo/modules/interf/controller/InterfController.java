
package com.example.demo.modules.interf.controller;

import com.example.demo.core.entity.ProcessResult;
import com.example.demo.core.result.PageResult;
import com.example.demo.modules.interf.model.SysInterface;
import com.example.demo.modules.interf.service.InterfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;


/**
 * 接口管理
 *
 */
@RestController
@RequestMapping("/data/interface")
public class InterfController {
	@Autowired
	private InterfService interfService;


	@RequestMapping("/list")
	public ModelAndView list() {
		return new ModelAndView("/modules/interf/list");
	}

	@RequestMapping("/form")
	public ModelAndView form() {
		return new ModelAndView("/modules/interf/form");
	}
	
	/**
	 * 接口管理列表
	 */
	@RequestMapping("/pages")
	//@RequiresPermissions("sys:scheduler:list")
	public PageResult list(SysInterface sysInterface,String keyword){
		List<SysInterface> page = interfService.queryPage(sysInterface,keyword);
		page.sort(new Comparator<SysInterface>() {
			public int compare(SysInterface arg0, SysInterface arg1) {
				if (arg0.getInterfaceSort() == null) {
					return 1;
				} else if (arg1.getInterfaceSort() == null) {
					return -1;
				}else {
					return (arg0.getInterfaceSort()).compareTo((arg1.getInterfaceSort()));
				}
			}
		});

		return new PageResult(page);
	}
	
	/**
	 * 接口信息
	 */
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("sys:scheduler:info")
	public ProcessResult info(@PathVariable("id") Integer id){
		SysInterface sysInterface = interfService.get(id);
		return new ProcessResult(sysInterface);
	}
	
	/**
	 * 保存接口
	 */
	//@SysLog("保存接口")
	@RequestMapping("/saveOrUpdate")
	//@RequiresPermissions("sys:scheduler:save")
	public ProcessResult saveOrUpdate(SysInterface sysInterface){
		if(sysInterface.getId()!=null){
			interfService.update(sysInterface);
		}else{
			interfService.save(sysInterface);
		}
		return new ProcessResult();
	}
	
	/**
	 * 批量删除接口
	 */
	//@SysLog("删除接口")
	@RequestMapping("/delete")
	//@RequiresPermissions("sys:scheduler:delete")
	public ProcessResult delete(@RequestParam("ids[]")Integer[] ids){
		interfService.batchRemove(ids);
		return new ProcessResult();
	}

	/**
	 * 删除接口
	 */
	@RequestMapping("/deleteOnlyOne/{id}")
	//@RequiresPermissions("sys:scheduler:delete")
	public ProcessResult deleteOnlyOne(@PathVariable Integer id){
		interfService.remove(id);
		return new ProcessResult();
	}

}
