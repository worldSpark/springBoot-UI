package com.vote.domain;

import com.common.annotation.Excel;
import com.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: Vote
 * @description: 用户投票
 * @author: Mr.Wan
 * @create: 2021-03-17 15:22
 **/
@Data
public class UserVote extends BaseEntity {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("投票id")
    private Long voteId;

    @ApiModelProperty("候选项id")
    private Long voteOptionId;

    @ApiModelProperty("删除标志 （0代表存在 2代表删除）")
    private String delFlag;

    @Excel(name = "用户投票内容")
    @ApiModelProperty("用户投票内容")
    private String content;

}
