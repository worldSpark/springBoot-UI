package com.vote.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.core.domain.entity.SysRole;
import com.common.utils.DateUtils;
import com.system.domain.SysRoleMenu;
import com.system.mapper.SysUserMapper;
import com.vote.domain.UserVote;
import com.vote.domain.Vote;
import com.vote.domain.VotePermission;
import com.vote.mapper.VoteMapper;
import com.vote.mapper.VotePermissionMapper;
import com.vote.service.IVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author admin
 * @date 2021-03-17
 */
@Service
public class VoteServiceImpl implements IVoteService
{
    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private VotePermissionMapper votePermissionMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public Vote selectVoteById(Long id)
    {
        return voteMapper.selectVoteById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param vote 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Vote> selectVoteList(Vote vote)
    {
        return voteMapper.selectVoteList(vote);
    }

    /**
     * 查询所有投票列表
     */
    @Override
    public List<Vote> getAllList() {
        Vote vote = new Vote();
        return voteMapper.selectVoteList(vote);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param vote 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertVote(Vote vote)
    {
        vote.setCreateTime(DateUtils.getNowDate());

        voteMapper.insertVote(vote);

        return insertVoteUser(vote);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param vote 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateVote(Vote vote)
    {
        vote.setUpdateTime(DateUtils.getNowDate());
        //修改投票信息
        voteMapper.updateVote(vote);
        // 删除投票与用户关联
        votePermissionMapper.deleteByRoleId(vote.getId());
        return insertVoteUser(vote);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteVoteByIds(Long[] ids)
    {
        return voteMapper.deleteVoteByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteVoteById(Long id)
    {
        return voteMapper.deleteVoteById(id);
    }

    /**
     * 标题是否存在
     * @return
     */
    @Override
    public boolean existTitle(String title) {
        return this.voteMapper.selectCountByTitle(title) > 0;
    }

    /**
     * 标题是否存在且不等于id
     */
    @Override
    public boolean existTitleWithId(Long id, String title) {
        return this.voteMapper.selectCountByTitleWithId(id, title) > 0;
    }

    /**
     * 首页的参与总人寿和投票总数
     * @return
     */
    @Override
    public Map<String, Object> statisticsVote() {
        Map<String, Object> map = new HashMap<>();
        map.put("voteTotal",voteMapper.getVoteTotal());
        map.put("personTotal",voteMapper.getPersonTotal());
        return map;
    }

    @Override
    public List<Map<String, Object>> getDataList() {
        return voteMapper.getDataList();
    }

    @Override
    public List<Vote> selectVoteListByTime(Vote vote) {
        return voteMapper.selectVoteListByTime(vote);
    }

    /**
     * 新增投票用户信息
     *
     * @param vote 投票对象
     */
    public int insertVoteUser(Vote vote)
    {
        int rows = 1;
        // 新增用户与角色管理
        List<VotePermission> list = new ArrayList<VotePermission>();
        for (Long userId : vote.getUserIds())
        {
            VotePermission rm = new VotePermission();
            rm.setVoteId(vote.getId());
            rm.setUserId(userId);
            list.add(rm);
        }
        if (list.size() > 0)
        {
            rows = this.votePermissionMapper.batchVotePermission(list);
        }
        return rows;
    }
}
