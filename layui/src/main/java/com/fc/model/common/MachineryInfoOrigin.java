package com.fc.model.common;


import lombok.Data;

import java.util.Date;

@Data
public class MachineryInfoOrigin {

    private Long id;

    private String dicName;

    private String modelName;

    private String drivingNumber;

    private String factoryNumber;

    private String iotNumber;

    private String machineryEngine;

    private String machineryModel;

    private String companyName;

    private String operator;

    private Integer regionCode;

    private String ownerName;

    private String ownerIphone;

    private String compCode;

    private Date createTime;

    /**
     * 状态：
     * 0：删除        1：无效       2：冻结        100：有效
     */
    private Integer state;

}
