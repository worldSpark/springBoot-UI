package com.fc.model.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 定位实体
 *
 * @author 王昊
 * @date 2019-03-26 15:33
 */
@Data
public class PositionEntity {

    private static final long serialVersionUID = 1L;

    private Integer compCode;

    private String serialNumber;

    private Long reportDate;

    private double longitude;

    private double latitude;

    private Integer altitude;

    private Integer orientation;

    private double speed;

    private List<Short> _other = new ArrayList<Short>(0);
}
