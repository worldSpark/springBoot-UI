package com.vote.controller;

import java.util.List;

import com.vote.domain.VotePermission;
import com.vote.service.IVotePermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.common.annotation.Log;
import com.common.core.controller.BaseController;
import com.common.core.domain.AjaxResult;
import com.common.enums.BusinessType;
import com.common.utils.poi.ExcelUtil;
import com.common.core.page.TableDataInfo;

/**
 * 许可Controller
 * 
 * @author admin
 * @date 2021-03-17
 */
@RestController
@RequestMapping("/vote/permission")
public class VotePermissionController extends BaseController
{
    @Autowired
    private IVotePermissionService votePermissionService;

    /**
     * 查询许可列表
     */
    @PreAuthorize("@ss.hasPermi('system:permission:list')")
    @GetMapping("/list")
    public TableDataInfo list(VotePermission votePermission)
    {
        startPage();
        List<VotePermission> list = votePermissionService.selectVotePermissionList(votePermission);
        return getDataTable(list);
    }

    /**
     * 导出许可列表
     */
    @PreAuthorize("@ss.hasPermi('system:permission:export')")
    @Log(title = "许可", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(VotePermission votePermission)
    {
        List<VotePermission> list = votePermissionService.selectVotePermissionList(votePermission);
        ExcelUtil<VotePermission> util = new ExcelUtil<VotePermission>(VotePermission.class);
        return util.exportExcel(list, "permission");
    }

    /**
     * 获取许可详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:permission:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(votePermissionService.selectVotePermissionById(id));
    }

    /**
     * 新增许可
     */
    @PreAuthorize("@ss.hasPermi('system:permission:add')")
    @Log(title = "许可", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VotePermission votePermission)
    {
        return toAjax(votePermissionService.insertVotePermission(votePermission));
    }

    /**
     * 修改许可
     */
    @PreAuthorize("@ss.hasPermi('system:permission:edit')")
    @Log(title = "许可", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VotePermission votePermission)
    {
        return toAjax(votePermissionService.updateVotePermission(votePermission));
    }

    /**
     * 删除许可
     */
    @PreAuthorize("@ss.hasPermi('system:permission:remove')")
    @Log(title = "许可", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(votePermissionService.deleteVotePermissionByIds(ids));
    }
}
