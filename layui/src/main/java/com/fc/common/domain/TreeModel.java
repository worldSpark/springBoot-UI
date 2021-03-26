package com.fc.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description EASYUI左侧树节点模型
 * @author chencc
 * @Date 2015年04月01日 上午11:21:00
 * @version 1.0.0
 * */
@Data
public class TreeModel implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	private List<ComboTreeModel> children;
	private String state;
	private String parentNode;
	private String level;
	private String type;
}
