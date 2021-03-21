package com.vote.mapper;

import com.common.core.domain.entity.SysUser;
import com.vote.domain.UserVote;
import com.vote.domain.Vote;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 用户Mapper接口
 *
 * @author admin
 * @date 2021-03-17
 */
public interface UserVoteMapper
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
     * 删除用户
     *
     * @param id 用户ID
     * @return 结果
     */
    public int deleteUserVoteById(Long id);

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserVoteByIds(Long[] ids);

    public List<Vote> getUserVoteList(Long userId);

    public List<SysUser> selectUserListVoteById(@Param("voteId") Long voteId,@Param("optionId") Long optionId);

    public Integer getUserVoteTotal(@Param("userId") Long userId,@Param("voteId") Long voteId);
}
