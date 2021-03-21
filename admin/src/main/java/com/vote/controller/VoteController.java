package com.vote.controller;

import com.common.annotation.Log;
import com.common.core.controller.BaseController;
import com.common.core.domain.AjaxResult;
import com.common.core.domain.entity.SysUser;
import com.common.core.page.TableDataInfo;
import com.common.enums.BusinessType;
import com.common.utils.SecurityUtils;
import com.common.utils.StringUtils;
import com.vote.domain.Vote;
import com.vote.service.IUserVoteService;
import com.vote.service.IVotePermissionService;
import com.vote.service.IVoteService;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 投票Controller
 *
 * @author admin
 * @date 2021-03-17
 */
@RestController
@RequestMapping("/vote/vote")
public class VoteController extends BaseController {
    @Autowired
    private IVoteService voteService;

    @Autowired
    private IVotePermissionService votePermissionService;

    @Autowired
    private IUserVoteService userVoteService;

    /**
     * 查询投票列表
     */
    @PreAuthorize("@ss.hasPermi('system:vote:list')")
    @GetMapping("/list")
    public TableDataInfo list(Vote vote) {
        startPage();
        List<Vote> list = voteService.selectVoteList(vote);
        return getDataTable(list);
    }

    /**
     * 查询所有投票列表
     */
    @PreAuthorize("@ss.hasPermi('system:vote:list')")
    @GetMapping("/voteList")
    public AjaxResult voteList() {
        List<Vote> list = voteService.getAllList();
        return AjaxResult.success(list);
    }

    /**
     * 获取投票详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:vote:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(voteService.selectVoteById(id));
    }

    /**
     * 新增投票
     */
    @PreAuthorize("@ss.hasPermi('system:vote:add')")
    @Log(title = "投票", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Vote vote) {

        if (StringUtils.isNotNull(vote.getId())) {
            vote.setId(null);
        }

        if (this.voteService.existTitle(vote.getTitle())) {
            return AjaxResult.error("标题已存在");
        }

        vote.setCreateBy(SecurityUtils.getUsername());
        return toAjax(voteService.insertVote(vote));
    }

    /**
     * 修改投票
     */
    @PreAuthorize("@ss.hasPermi('system:vote:edit')")
    @Log(title = "投票", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Vote vote) {

        if (this.voteService.existTitleWithId(vote.getId(), vote.getTitle())) {
            return AjaxResult.error("标题已存在");
        }
        vote.setUpdateBy(SecurityUtils.getUsername());

        return toAjax(voteService.updateVote(vote));
    }

    /**
     * 删除投票
     */
    @PreAuthorize("@ss.hasPermi('system:vote:remove')")
    @Log(title = "投票", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(voteService.deleteVoteByIds(ids));
    }

    /**
     * 查询统计数
     *
     * @return
     */
    @GetMapping("/statisticsVote")
    public AjaxResult statisticsVote() {
        return AjaxResult.success(voteService.statisticsVote());
    }

    /**
     * 获取前5用户总投票数
     *
     * @return
     */
    @GetMapping("/getDataList")
    public AjaxResult getDataList() {
        return AjaxResult.success(voteService.getDataList());
    }


    /**
     * 查询当前投票时间范围内的集合
     *
     * @param vote
     * @return
     */
    @GetMapping("/selectVoteListByTime")
    public TableDataInfo selectVoteListByTime(Vote vote) {
        startPage();
        vote.setCreateTime(new Date());
        List<Vote> votes = voteService.selectVoteListByTime(vote);
        return getDataTable(votes);
    }

    /**
     * 判断当前用户是否投票范围内
     *
     * @param voteId
     * @return
     */
    @GetMapping("/getUserByVoteRange/{voteId}")
    public AjaxResult getUserByVoteRange(@PathVariable("voteId") Long voteId) {
        String msg = "";
        SysUser user = SecurityUtils.getLoginUser().getUser();
        int userByPermission = votePermissionService.getUserByPermission(user.getUserId(), voteId);
        if(userByPermission<=0){
            msg = "您没有当前投票项目的权限";
            return AjaxResult.error(msg);
        }
        Integer userVoteTotal = userVoteService.getUserVoteTotal(user.getUserId(), voteId);
        if(userVoteTotal>0){
            msg = "您已经参与了投票";
            return AjaxResult.error(msg);
        }
        return AjaxResult.success("success");
    }

}
