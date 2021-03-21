package com.vote.domain;

import com.common.annotation.Excel;
import com.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: Vote
 * @description: 候选项表
 * @author: Mr.Wan
 * @create: 2021-03-17 15:22
 **/
@Data
public class VoteOption extends BaseEntity {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("投票id")
    private Long voteId;

    @Excel(name = "候选项内容")
    @ApiModelProperty("候选项内容")
    private String content;

    @Excel(name = "票数")
    @ApiModelProperty("票数")
    private Integer ticketNum;

    @ApiModelProperty("删除标志 （0代表存在 2代表删除）")
    private String delFlag;

    /**
     * 用于导出
     */
    @Excel(name = "标题")
    @ApiModelProperty("标题")
    private String title;

}
