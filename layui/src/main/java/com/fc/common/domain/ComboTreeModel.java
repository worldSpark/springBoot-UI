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
public class ComboTreeModel implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	private String state;// open,closed
	private boolean checked = false;
	private Object attributes;
	private List<ComboTreeModel> children;
	private String iconCls;
	private String pid;
	private String flag;//用于标识是用户选择的是样品编号还是项目名称    no   name
	private String parentNode;
	private String level;
	private String str;
	private String uperNode;
}
