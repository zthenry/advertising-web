package com.cyou.advertising.web.model.security;

import java.util.List;

public class ManageModule {

	/** 管理项id */
	protected Integer id;

	/** 管理项名称 */
	protected String name;

	/** 求请页面地址 */
	protected String url;

	/** 管理项状态：1显示 0隐藏 */
	protected Integer status;

	/** 管理项顺序 */
	protected Integer orderNum;

	protected List<Operations> operationsList;

	public List<Operations> getOperationsList() {
		return operationsList;
	}

	public void setOperationsList(List<Operations> operationsList) {
		this.operationsList = operationsList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
}
