package com.fc.common.domain;

import lombok.Data;

/**
 * @ClassName 参数类型
 * @Description TODO
 * @Version 1.0
 */
@Data
public class ParameterTypeEntity {

    /**
     * token
     */
    private String token;
    /**
     * 数据类型
     */
    private String interfaceSign;
    /**
     * 加密后的参数
     */
    private String businessParams;

}
