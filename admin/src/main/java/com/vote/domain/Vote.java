package com.vote.domain;

import com.common.annotation.Excel;
import com.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: Vote
 * @description: 投票表
 * @author: Mr.Wan
 * @create: 2021-03-17 15:22
 **/
@Data
public class Vote extends BaseEntity {

    @ApiModelProperty("id")
    private Long id;

    @Excel(name = "标题")
    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("允许用户选择的候选项的上限")
    private Integer optionNum;

    @ApiModelProperty("是否允许用户提交自定义候选项(1=是，2=否)")
    private Integer isCustomOption;

    @ApiModelProperty("是否记名投票(1=是，2=否)")
    private Long isRegistered;

    @Excel(name = "投票开始时间")
    @ApiModelProperty("投票开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty("投票结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty("删除标志 （0代表存在 2代表删除）")
    private String delFlag;

    @ApiModelProperty("用户id数组")
    private Long[] userIds;

    @ApiModelProperty("候选项内容(数据库不存入)")
    private List<String> voteContents;

    @ApiModelProperty("候选项id(数据库不存入)")
    private List<Long> voteOptionIds;

    @ApiModelProperty("用户投票内容(数据库不存入)")
    private String content;
}
