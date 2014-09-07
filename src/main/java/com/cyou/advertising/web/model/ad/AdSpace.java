package com.cyou.advertising.web.model.ad;

import java.io.Serializable;


/**
 * 广告位
 * @author guowei
 *
 */
public class AdSpace implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9175948314855773350L;

	/** id */
	protected Integer id;

	/** adSpaceNumber */
	protected String adSpaceNumber;

	/** desc */
	protected String description;

	/** appClientId */
	protected Long appClientId;

	/** appClientVersion */
	protected String appClientVersion;

	/** supportAdlist */
	protected Integer supportAdlist;

	/** status */
	protected Integer status;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAdSpaceNumber() {
		return adSpaceNumber;
	}
	public void setAdSpaceNumber(String adSpaceNumber) {
		this.adSpaceNumber = adSpaceNumber;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getAppClientId() {
		return appClientId;
	}
	public void setAppClientId(Long appClientId) {
		this.appClientId = appClientId;
	}
	public String getAppClientVersion() {
		return appClientVersion;
	}
	public void setAppClientVersion(String appClientVersion) {
		this.appClientVersion = appClientVersion;
	}
	public Integer getSupportAdlist() {
		return supportAdlist;
	}
	public void setSupportAdlist(Integer supportAdlist) {
		this.supportAdlist = supportAdlist;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
