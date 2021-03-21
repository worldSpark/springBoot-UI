package com.vote.service;

import com.vote.domain.VoteOption;

import java.util.List;
import java.util.Map;

/**
 * 候选项Service接口
 *
 * @author admin
 * @date 2021-03-17
 */
public interface IVoteOptionService
{
    /**
     * 查询候选项
     *
     * @param id 候选项ID
     * @return 候选项
     */
    public VoteOption selectVoteOptionById(Long id);

    /**
     * 查询候选项(候选项模块)
     *
     * @param id 候选项ID
     * @return 候选项
     */
    public VoteOption getVoteOptionById(Long id);

    /**
     * 查询候选项列表
     *
     * @param voteOption 候选项
     * @return 候选项集合
     */
    public List<VoteOption> selectVoteOptionList(VoteOption voteOption);

    /**
     * 查询候选项列表(候选项模块)
     *
     * @param voteOption 候选项
     * @return 候选项集合
     */
    public List<VoteOption> getVoteOptionList(VoteOption voteOption);

    /**
     * 新增候选项
     *
     * @param voteOption 候选项
     * @return 结果
     */
    public int insertVoteOption(VoteOption voteOption);

    /**
     * 修改候选项
     *
     * @param voteOption 候选项
     * @return 结果
     */
    public int updateVoteOption(VoteOption voteOption);

    /**
     * 批量删除候选项
     *
     * @param ids 需要删除的候选项ID
     * @return 结果
     */
    public int deleteVoteOptionByIds(Long[] ids);

    /**
     * 删除候选项信息
     *
     * @param id 候选项ID
     * @return 结果
     */
    public int deleteVoteOptionById(Long id);

    public List<Map<String,Object>> voteOptionSelects(Long voteId);


}
