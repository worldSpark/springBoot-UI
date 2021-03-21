package com.vote.controller;

import com.common.annotation.Log;
import com.common.core.controller.BaseController;
import com.common.core.domain.AjaxResult;
import com.common.core.domain.entity.SysDept;
import com.common.core.domain.entity.SysRole;
import com.common.core.domain.entity.SysUser;
import com.common.core.page.TableDataInfo;
import com.common.enums.BusinessType;
import com.common.utils.SecurityUtils;
import com.common.utils.StringUtils;
import com.common.utils.poi.ExcelUtil;
import com.system.service.ISysDeptService;
import com.system.service.ISysRoleService;
import com.system.service.ISysUserService;
import com.vote.domain.Vote;
import com.vote.domain.VoteOption;
import com.vote.service.IUserVoteService;
import com.vote.service.IVoteOptionService;
import com.vote.service.IVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 候选项Controller
 *
 * @author admin
 * @date 2021-03-17
 */
@RestController
@RequestMapping("/vote/option")
public class VoteOptionController extends BaseController
{
    @Autowired
    private IVoteOptionService voteOptionService;

    @Autowired
    private IUserVoteService userVoteService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private IVoteService voteService;

    /**
     * 查询候选项列表
     */
    @PreAuthorize("@ss.hasPermi('vote:option:list')")
    @GetMapping("/list")
    public TableDataInfo list(VoteOption voteOption)
    {
        startPage();
        List<VoteOption> list = voteOptionService.selectVoteOptionList(voteOption);
        return getDataTable(list);
    }

    /**
     * 查询候选项列表(候选项模块)
     */
    @PreAuthorize("@ss.hasPermi('vote:option:list')")
    @GetMapping("/voteOptionList")
    public TableDataInfo voteOptionList(VoteOption voteOption)
    {
        startPage();
        List<VoteOption> list = voteOptionService.getVoteOptionList(voteOption);
        return getDataTable(list);
    }

    /**
     * 导出候选项列表
     */
    @PreAuthorize("@ss.hasPermi('vote:option:export')")
    @Log(title = "候选项", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(VoteOption voteOption)
    {
        List<VoteOption> list = voteOptionService.selectVoteOptionList(voteOption);
        ExcelUtil<VoteOption> util = new ExcelUtil<VoteOption>(VoteOption.class);
        return util.exportExcel(list, "option");
    }

    /**
     * 获取候选项详细信息(候选项模块)
     */
    @PreAuthorize("@ss.hasPermi('vote:query')")
    @GetMapping(value = "/getVoteOption/{id}")
    public AjaxResult getVoteOption(@PathVariable("id") Long id)
    {
        return AjaxResult.success(voteOptionService.getVoteOptionById(id));
    }

    /**
     * 获取候选项详细信息
     */
    @PreAuthorize("@ss.hasPermi('vote:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(voteOptionService.selectVoteOptionById(id));
    }

    /**
     * 新增候选项
     */
    @PreAuthorize("@ss.hasPermi('vote:option:add')")
    @Log(title = "候选项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VoteOption voteOption)
    {
        return toAjax(voteOptionService.insertVoteOption(voteOption));
    }

    /**
     * 修改候选项
     */
    @PreAuthorize("@ss.hasPermi('vote:option:edit')")
    @Log(title = "候选项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VoteOption voteOption)
    {
        return toAjax(voteOptionService.updateVoteOption(voteOption));
    }

    /**
     * 删除候选项
     */
    @PreAuthorize("@ss.hasPermi('vote:option:remove')")
    @Log(title = "候选项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(voteOptionService.deleteVoteOptionByIds(ids));
    }

    /**
     * 获取树形结构信息
     * @return
     */
	@GetMapping("/treeselect")
    public AjaxResult treeselect()
    {

        List<Vote> voteList = voteService.selectVoteList(new Vote());

        voteList.forEach(vote -> {
            if (vote.getIsRegistered() == 1) {
                vote.setTitle(vote.getTitle() + "(记名)");
            } else if (vote.getIsRegistered() == 2){
                vote.setTitle(vote.getTitle() + "(匿名)");
            }
        });

        return AjaxResult.success(voteList);
    }

    /**
     * 根据userId获取到用户信息
     * @param userId
     * @return
     */
    @GetMapping("/getUserInfoByVote/{userId}")
    public AjaxResult getUserInfoByVote(@PathVariable("userId") Long userId)
    {
        //获取用户信息
        SysUser sysUser = userService.selectUserById(userId);
        //获取用户角色信息
        List<SysRole> sysRoles = roleService.selectRoleByUserId(sysUser.getUserId());
        sysUser.setRoles(sysRoles);
        //存入用户角色名称(拼接逗号)
        StringBuffer idsStr = new StringBuffer();
        String roleName = "";
        for (SysRole sysRole : sysRoles) {
            idsStr.append(sysRole.getRoleName()).append(",");
        }
        //不为空时减掉最后一个逗号
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            roleName = idsStr.substring(0, idsStr.length() - 1);
        }
        sysUser.setRoleName(roleName);
        //获取用户部门信息
        SysDept sysDept = deptService.selectDeptById(sysUser.getDeptId());
        sysUser.setDept(sysDept);
        return AjaxResult.success(sysUser);
    }

    /**
     * 根据投票id查询当前投票的用户信息
     * @return
     */
    @GetMapping("/getUserListByVote/{voteId}/{optionId}")
    public AjaxResult getUserListByVote(@PathVariable("voteId") Long voteId,@PathVariable("optionId") Long optionId)
    {
        Vote vote = voteService.selectVoteById(voteId);

        if (StringUtils.isNull(vote)) {
            return AjaxResult.success();
        }

        if (vote.getIsRegistered() == 2) {
            return AjaxResult.success(new ArrayList<SysUser>());
        }

        List<SysUser> sysUsers = userVoteService.selectUserListVoteById(voteId,optionId);
        return AjaxResult.success(sysUsers);
    }

    @GetMapping("/voteOptionSelects/{voteId}")
    public AjaxResult voteOptionSelects(@PathVariable("voteId") Long voteId)
    {
        return AjaxResult.success(voteOptionService.voteOptionSelects(voteId));
    }

}
