package com.vote.service;

import com.vote.domain.Vote;

import java.util.List;
import java.util.Map;

/**
 * 投票Service接口
 *
 * @author admin
 * @date 2021-03-17
 */
public interface IVoteService
{
    /**
     * 查询投票
     *
     * @param id 投票ID
     * @return 投票
     */
    public Vote selectVoteById(Long id);

    /**
     * 查询投票列表
     *
     * @param vote 投票
     * @return 投票集合
     */
    public List<Vote> selectVoteList(Vote vote);

    /**
     * 查询所有投票列表
     */
    public List<Vote> getAllList();

    /**
     * 新增投票
     *
     * @param vote 投票
     * @return 结果
     */
    public int insertVote(Vote vote);

    /**
     * 修改投票
     *
     * @param vote 投票
     * @return 结果
     */
    public int updateVote(Vote vote);

    /**
     * 批量删除投票
     *
     * @param ids 需要删除的投票ID
     * @return 结果
     */
    public int deleteVoteByIds(Long[] ids);

    /**
     * 删除投票信息
     *
     * @param id 投票ID
     * @return 结果
     */
    public int deleteVoteById(Long id);

    /**
     * 标题是否存在
     * @return
     */
    public boolean existTitle(String title);

    /**
     * 标题是否存在且不等于id
     */
    public boolean existTitleWithId(Long id, String title);

    /**
     * 首页的参与总人寿和投票总数
     * @return
     */
    public Map<String,Object> statisticsVote();

    /**
     * 获取前5的用户总投票数
     * @return
     */
    public List<Map<String,Object>> getDataList();

    public List<Vote> selectVoteListByTime(Vote vote);

}
