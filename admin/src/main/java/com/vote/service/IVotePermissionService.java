package com.vote.service;

import com.vote.domain.VotePermission;

import java.util.List;

/**
 * 许可Service接口
 *
 * @author admin
 * @date 2021-03-17
 */
public interface IVotePermissionService
{
    /**
     * 查询许可
     *
     * @param id 许可ID
     * @return 许可
     */
    public VotePermission selectVotePermissionById(Long id);

    /**
     * 查询许可列表
     *
     * @param votePermission 许可
     * @return 许可集合
     */
    public List<VotePermission> selectVotePermissionList(VotePermission votePermission);

    /**
     * 新增许可
     *
     * @param votePermission 许可
     * @return 结果
     */
    public int insertVotePermission(VotePermission votePermission);

    /**
     * 修改许可
     *
     * @param votePermission 许可
     * @return 结果
     */
    public int updateVotePermission(VotePermission votePermission);

    /**
     * 批量删除许可
     *
     * @param ids 需要删除的许可ID
     * @return 结果
     */
    public int deleteVotePermissionByIds(Long[] ids);

    /**
     * 删除许可信息
     *
     * @param id 许可ID
     * @return 结果
     */
    public int deleteVotePermissionById(Long id);

    public int getUserByPermission(Long userId,Long voteId);
}
