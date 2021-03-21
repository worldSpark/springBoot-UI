package com.vote.domain;

import com.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: Vote
 * @description: 投票许可表
 * @author: Mr.Wan
 * @create: 2021-03-17 15:22
 **/
@Data
public class VotePermission extends BaseEntity {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("投票id")
    private Long voteId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("删除标志 （0代表存在 2代表删除）")
    private String delFlag;

}
