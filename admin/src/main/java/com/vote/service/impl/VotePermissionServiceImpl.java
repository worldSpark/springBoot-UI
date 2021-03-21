package com.vote.service.impl;

import java.util.List;
import com.common.utils.DateUtils;
import com.vote.domain.VotePermission;
import com.vote.mapper.VotePermissionMapper;
import com.vote.service.IVotePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 许可Service业务层处理
 *
 * @author admin
 * @date 2021-03-17
 */
@Service
public class VotePermissionServiceImpl implements IVotePermissionService
{
    @Autowired
    private VotePermissionMapper votePermissionMapper;

    /**
     * 查询许可
     *
     * @param id 许可ID
     * @return 许可
     */
    @Override
    public VotePermission selectVotePermissionById(Long id)
    {
        return votePermissionMapper.selectVotePermissionById(id);
    }

    /**
     * 查询许可列表
     *
     * @param votePermission 许可
     * @return 许可
     */
    @Override
    public List<VotePermission> selectVotePermissionList(VotePermission votePermission)
    {
        return votePermissionMapper.selectVotePermissionList(votePermission);
    }

    /**
     * 新增许可
     *
     * @param votePermission 许可
     * @return 结果
     */
    @Override
    public int insertVotePermission(VotePermission votePermission)
    {
        votePermission.setCreateTime(DateUtils.getNowDate());
        return votePermissionMapper.insertVotePermission(votePermission);
    }

    /**
     * 修改许可
     *
     * @param votePermission 许可
     * @return 结果
     */
    @Override
    public int updateVotePermission(VotePermission votePermission)
    {
        votePermission.setUpdateTime(DateUtils.getNowDate());
        return votePermissionMapper.updateVotePermission(votePermission);
    }

    /**
     * 批量删除许可
     *
     * @param ids 需要删除的许可ID
     * @return 结果
     */
    @Override
    public int deleteVotePermissionByIds(Long[] ids)
    {
        return votePermissionMapper.deleteVotePermissionByIds(ids);
    }

    /**
     * 删除许可信息
     *
     * @param id 许可ID
     * @return 结果
     */
    @Override
    public int deleteVotePermissionById(Long id)
    {
        return votePermissionMapper.deleteVotePermissionById(id);
    }

    @Override
    public int getUserByPermission(Long userId, Long voteId) {
        return votePermissionMapper.getUserByPermission(userId,voteId);
    }
}
