package com.vote.service.impl;

import java.util.List;
import java.util.Map;

import com.common.utils.DateUtils;
import com.vote.domain.VoteOption;
import com.vote.mapper.VoteOptionMapper;
import com.vote.service.IVoteOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 候选项Service业务层处理
 *
 * @author admin
 * @date 2021-03-17
 */
@Service
public class VoteOptionServiceImpl implements IVoteOptionService
{
    @Autowired
    private VoteOptionMapper voteOptionMapper;

    /**
     * 查询候选项
     *
     * @param id 候选项ID
     * @return 候选项
     */
    @Override
    public VoteOption selectVoteOptionById(Long id)
    {
        return voteOptionMapper.selectVoteOptionById(id);
    }

    /**
     * 查询候选项(候选项模块)
     *
     * @param id 候选项ID
     * @return 候选项
     */
    @Override
    public VoteOption getVoteOptionById(Long id) {
        return voteOptionMapper.getVoteOptionById(id);
    }

    /**
     * 查询候选项列表
     *
     * @param voteOption 候选项
     * @return 候选项
     */
    @Override
    public List<VoteOption> selectVoteOptionList(VoteOption voteOption)
    {
        return voteOptionMapper.selectVoteOptionList(voteOption);
    }

    /**
     * 查询候选项列表(候选项模块)
     *
     * @param voteOption 候选项
     * @return 候选项集合
     */
    @Override
    public List<VoteOption> getVoteOptionList(VoteOption voteOption) {
        return voteOptionMapper.getVoteOptionList(voteOption);
    }

    /**
     * 新增候选项
     *
     * @param voteOption 候选项
     * @return 结果
     */
    @Override
    public int insertVoteOption(VoteOption voteOption)
    {
        voteOption.setCreateTime(DateUtils.getNowDate());
        return voteOptionMapper.insertVoteOption(voteOption);
    }

    /**
     * 修改候选项
     *
     * @param voteOption 候选项
     * @return 结果
     */
    @Override
    public int updateVoteOption(VoteOption voteOption)
    {
        voteOption.setUpdateTime(DateUtils.getNowDate());
        return voteOptionMapper.updateVoteOption(voteOption);
    }

    /**
     * 批量删除候选项
     *
     * @param ids 需要删除的候选项ID
     * @return 结果
     */
    @Override
    public int deleteVoteOptionByIds(Long[] ids)
    {
        return voteOptionMapper.deleteVoteOptionByIds(ids);
    }

    /**
     * 删除候选项信息
     *
     * @param id 候选项ID
     * @return 结果
     */
    @Override
    public int deleteVoteOptionById(Long id)
    {
        return voteOptionMapper.deleteVoteOptionById(id);
    }

    @Override
    public List<Map<String, Object>> voteOptionSelects(Long voteId) {
        return voteOptionMapper.voteOptionSelects(voteId);
    }
}
