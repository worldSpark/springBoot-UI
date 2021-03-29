package com.fc.model.common;

import com.fc.model.auto.TsysUser;
import lombok.Data;

import java.util.Date;

@Data
public class MachineryInfo {

    private TsysUser userItem;
    private Long machineryId;

    private Long monitorId;

    private String modelName;

    private String drivingNumber;
    //图片附件表ID
    private Long attachmentId;

    private String factoryNumber;

    private String pictureUrl;

    private String iotNumber;

    private String compCode;


    /**
     * 是否在大田平台注册过 1 合法 2 非法
     */
    private Integer flag;

    //用于判断是否这三天有作业面积
    private Integer areaStatus;

    /**
     * 设备生产厂家 1大田农社 2第三方企业 3设备号为空
     */
    private Integer equFactory;


    /*
     * 农机分配人
     * */
    private String operator;

    private Integer isSubsidy;

    private Long declareId;//申报id

    private Long workId;//作业记录Id
    private String reason;// 拒绝理由

    private String machineryEngine;//发动机号
    private String machineryModel;//机具型号
    private String dicName;
    private String deptName; // 设备生产厂家

    private Long dicId;

    private Long companyId;
    private String companyName;

    private String imgArr;//附件集合

    private String purl;//自定义属性

    private Integer userType;  //0 合作社  1农机手

    private Integer orgType;

    // (查询)评分
    private double starLevel;

    // (查询)条件
    private String keyword;
    // (查询)采集时间
    private Date reportDate;
    /**
     * 当前状态
     */
    private Integer online;
    // 计算经度
    private Double longitude;
    //计算纬度
    private Double latitude;

    private Long ownerId;

    private String ownerName;

    private String ownerIphone;
    //农机名称
    private String machineName;

    private String ownerIdcard;

    private String idCard;


    private String ownerPassword;

    private String regionName;
    private Long regionId;

    private String realName;
    //农机调度详情返回url
    private String returnUrl;

    private Long userId;
    private String parentName;

    private String comDeviceId;//出厂编号与物联网号组合

    private Long buyId;


    private String invoiceNo;

    private String buyTime;
    private String buyAmount;
    private String buyCompany;
    private String startTime;
    private String endTime;
    private String buyUsercard;
    private String buyUser;

    private String regionCode;

    private Integer trajectoryData;

    // 农机业面积
    private Double workArea;

    /**
     * 数据来源分类
     */
    private Integer dataSources;

    /**
     * 解绑判定
     */
    private Integer unbindState;

    /**
     * 状态判定
     */
    private Integer tmState;

    //农机列表页根据合作社名字查询的条件
    private String userRealName;

}
