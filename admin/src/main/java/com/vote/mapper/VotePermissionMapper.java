package com.vote.mapper;

import com.vote.domain.VotePermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 许可Mapper接口
 *
 * @author admin
 * @date 2021-03-17
 */
@Mapper
public interface VotePermissionMapper
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
     * 删除许可
     *
     * @param id 许可ID
     * @return 结果
     */
    public int deleteVotePermissionById(Long id);

    /**
     * 批量删除许可
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteVotePermissionByIds(Long[] ids);

    /**
     * 批量新增许可
     *
     * @param votePermissionList 许可列表
     * @return 结果
     */
    public int batchVotePermission(List<VotePermission> votePermissionList);

    /**
     * 根据投票id查用户id集合
     */
    public List<Integer> selectListByVoteId(Long voteId);

    /**
     * 通过投票ID删除投票和用户关联
     *
     * @param voteId 投票ID
     * @return 结果
     */
    public int deleteByRoleId(Long voteId);

    @Select(" select count(0) from vote_permission where user_id = #{userId} and vote_id =#{voteId}")
    public int getUserByPermission(@Param("userId") Long userId, @Param("voteId") Long voteId);

}
