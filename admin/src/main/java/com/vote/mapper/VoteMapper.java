package com.vote.mapper;

import com.vote.domain.Vote;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author admin
 * @date 2021-03-17
 */
public interface VoteMapper
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public Vote selectVoteById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param vote 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Vote> selectVoteList(Vote vote);

    /**
     * 新增【请填写功能名称】
     *
     * @param vote 【请填写功能名称】
     * @return 结果
     */
    public int insertVote(Vote vote);

    /**
     * 修改【请填写功能名称】
     *
     * @param vote 【请填写功能名称】
     * @return 结果
     */
    public int updateVote(Vote vote);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteVoteById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteVoteByIds(Long[] ids);

    /**
     * 根据标题查数量
     */
    public int selectCountByTitle(String title);

    /**
     * 根据标题且不等一id查数量
     */
    public int selectCountByTitleWithId(@Param("id") Long id, @Param("title") String title);

    /**
     * 总投票数
     * @return
     */
    public Integer getVoteTotal();

    /**
     * 总参与人数
     * @return
     */
    public Integer getPersonTotal();

    /**
     * 获取前5个用户总投票数
     * @return
     */
    public List<Map<String,Object>> getDataList();

    public List<Vote> selectVoteListByTime(Vote vote);

}
