package com.vote.service.impl;

import java.util.List;

import com.common.core.domain.entity.SysUser;
import com.common.utils.DateUtils;
import com.vote.domain.UserVote;
import com.vote.domain.Vote;
import com.vote.mapper.UserVoteMapper;
import com.vote.service.IUserVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户Service业务层处理
 *
 * @author admin
 * @date 2021-03-17
 */
@Service
public class UserVoteServiceImpl implements IUserVoteService
{
    @Autowired
    private UserVoteMapper userVoteMapper;

    /**
     * 查询用户
     *
     * @param id 用户ID
     * @return 用户
     */
    @Override
    public UserVote selectUserVoteById(Long id)
    {
        return userVoteMapper.selectUserVoteById(id);
    }

    /**
     * 查询用户列表
     *
     * @param userVote 用户
     * @return 用户
     */
    @Override
    public List<UserVote> selectUserVoteList(UserVote userVote)
    {
        return userVoteMapper.selectUserVoteList(userVote);
    }

    /**
     * 新增用户
     *
     * @param userVote 用户
     * @return 结果
     */
    @Override
    public int insertUserVote(UserVote userVote)
    {
        userVote.setCreateTime(DateUtils.getNowDate());
        return userVoteMapper.insertUserVote(userVote);
    }

    /**
     * 修改用户
     *
     * @param userVote 用户
     * @return 结果
     */
    @Override
    public int updateUserVote(UserVote userVote)
    {
        userVote.setUpdateTime(DateUtils.getNowDate());
        return userVoteMapper.updateUserVote(userVote);
    }

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的用户ID
     * @return 结果
     */
    @Override
    public int deleteUserVoteByIds(Long[] ids)
    {
        return userVoteMapper.deleteUserVoteByIds(ids);
    }

    /**
     * 删除用户信息
     *
     * @param id 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserVoteById(Long id)
    {
        return userVoteMapper.deleteUserVoteById(id);
    }

    @Override
    public List<Vote> getUserVoteList(Long userId) {
        return userVoteMapper.getUserVoteList(userId);
    }

    @Override
    public List<SysUser> selectUserListVoteById(Long voteId,Long optionId) {
        return userVoteMapper.selectUserListVoteById(voteId,optionId);
    }

    @Override
    public int addUserVoteByList(List<UserVote> userVotes) {
        int count = 0;
        if(userVotes!=null&&userVotes.size()>0){
            count = userVotes.stream().mapToInt(userVote -> userVoteMapper.insertUserVote(userVote)).sum();
        }
        return count;
    }

    @Override
    public Integer getUserVoteTotal(Long userId, Long voteId) {
        return userVoteMapper.getUserVoteTotal(userId,voteId);
    }
}
