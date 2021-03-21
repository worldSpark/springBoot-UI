package com.vote.service;

import com.common.core.domain.entity.SysUser;
import com.vote.domain.UserVote;
import com.vote.domain.Vote;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author admin
 * @date 2021-03-17
 */
public interface IUserVoteService
{
    /**
     * 查询用户
     *
     * @param id 用户ID
     * @return 用户
     */
    public UserVote selectUserVoteById(Long id);

    /**
     * 查询用户列表
     *
     * @param userVote 用户
     * @return 用户集合
     */
    public List<UserVote> selectUserVoteList(UserVote userVote);

    /**
     * 新增用户
     *
     * @param userVote 用户
     * @return 结果
     */
    public int insertUserVote(UserVote userVote);

    /**
     * 修改用户
     *
     * @param userVote 用户
     * @return 结果
     */
    public int updateUserVote(UserVote userVote);

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserVoteByIds(Long[] ids);

    /**
     * 删除用户信息
     *
     * @param id 用户ID
     * @return 结果
     */
    public int deleteUserVoteById(Long id);

    /**
     * 根据用户id查询相关的投票信息
     * @param userId
     * @return
     */
    public List<Vote> getUserVoteList(Long userId);

    public List<SysUser> selectUserListVoteById(Long voteId,Long optionId);

    public int addUserVoteByList(List<UserVote> userVotes);

    public Integer getUserVoteTotal(Long userId,Long voteId);

}
