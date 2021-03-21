package com.vote.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.common.core.domain.entity.SysUser;
import com.common.utils.DateUtils;
import com.common.utils.SecurityUtils;
import com.vote.domain.UserVote;
import com.vote.domain.Vote;
import com.vote.domain.VoteOption;
import com.vote.mapper.UserVoteMapper;
import com.vote.mapper.VoteMapper;
import com.vote.service.IUserVoteService;
import com.vote.service.IVoteOptionService;
import com.vote.service.IVoteService;
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
 * 用户Controller
 *
 * @author admin
 * @date 2021-03-17
 */
@RestController
@RequestMapping("/vote/userVote")
public class UserVoteController extends BaseController
{
    @Autowired
    private IUserVoteService userVoteService;

    @Autowired
    private IVoteOptionService voteOptionService;

    @Autowired
    private IVoteService voteService;

    @Autowired
    private UserVoteMapper userVoteMapper;

    @Autowired
    private VoteMapper voteMapper;

    /**
     * 查询用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:vote:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserVote userVote)
    {
        startPage();
        List<UserVote> list = userVoteService.selectUserVoteList(userVote);
        return getDataTable(list);
    }

    /**
     * 导出用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:vote:export')")
    @Log(title = "用户", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(UserVote userVote)
    {
        List<UserVote> list = userVoteService.selectUserVoteList(userVote);
        ExcelUtil<UserVote> util = new ExcelUtil<UserVote>(UserVote.class);
        return util.exportExcel(list, "vote");
    }

    /**
     * 获取用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:vote:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(userVoteService.selectUserVoteById(id));
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('system:vote:add')")
    @Log(title = "用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserVote userVote)
    {
        return toAjax(userVoteService.insertUserVote(userVote));
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('system:vote:edit')")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserVote userVote)
    {
        return toAjax(userVoteService.updateUserVote(userVote));
    }

    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermi('system:vote:remove')")
    @Log(title = "用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userVoteService.deleteUserVoteByIds(ids));
    }

    @PostMapping("/addUserVoteByList")
    public AjaxResult addUserVoteByList(@RequestBody Vote vote)
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        //获取自定义选项
        List<String> voteContents = vote.getVoteContents();
        //获取候选项id
        List<Long> optionIds = vote.getVoteOptionIds();

        if (optionIds.size() > 0) {
            for (Long optionId: optionIds) {
                VoteOption voteOption = new VoteOption();
                voteOption.setId(optionId);
                if (voteOption.getTicketNum() != null) {
                    voteOption.setTicketNum(voteOption.getTicketNum() + 1);
                } else {
                    voteOption.setTicketNum(1);
                }

                this.voteOptionService.updateVoteOption(voteOption);
            }
        }

        if (!voteContents.isEmpty()) {
            //生成新的候选项
            for (String voteContent : voteContents) {
                VoteOption voteOption = new VoteOption();
                voteOption.setTicketNum(1);
                voteOption.setContent(voteContent);
                voteOption.setVoteId(vote.getId());
                voteOptionService.insertVoteOption(voteOption);
                //将生成的候选id放入
                optionIds.add(voteOption.getId());
            }
        }

        AtomicInteger count = new AtomicInteger();
        //循环遍历插入uservote
        optionIds.forEach(id -> {
            Date nowDate = DateUtils.getNowDate();
            UserVote userVote = new UserVote();
            userVote.setUserId(user.getUserId());
            userVote.setVoteId(vote.getId());
            userVote.setVoteOptionId(id);
            userVote.setContent(vote.getContent());
            userVote.setCreateTime(nowDate);
            vote.setUpdateTime(nowDate);
            //修改投票信息
            count.addAndGet(userVoteMapper.insertUserVote(userVote));
        });
        if(count.get() >0){
            return AjaxResult.success("成功");
        }
        return AjaxResult.error();
    }
}
