package com.fc.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
* @Description:    Bootstrap树形结构模型
* @Author:         zhangxh
* @CreateDate:     2018/12/30 14:10
* @Version:        1.0
*/
@Data
public class BootstrapTreeModel implements Serializable {
    private String id;
    private String text;
    private String tags;
    private String href;
    private List<BootstrapTreeModel> nodes;
}
