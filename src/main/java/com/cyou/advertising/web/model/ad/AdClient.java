package com.cyou.advertising.web.model.ad;

import java.io.Serializable;

/**
 * 广告平台
 * @author guowei
 *
 */
public class AdClient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1115768076062792849L;

	/** id */
	protected Integer id;

	/** appName */
	protected String name;

	/** appStatus */
	protected Integer status;
	
	private String adClientVersionStr;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAdClientVersionStr() {
		return adClientVersionStr;
	}

	public void setAdClientVersionStr(String adClientVersionStr) {
		this.adClientVersionStr = adClientVersionStr;
	}
	
}
