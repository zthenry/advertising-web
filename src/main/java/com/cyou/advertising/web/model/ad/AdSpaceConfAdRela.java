package com.cyou.advertising.web.model.ad;

import java.io.Serializable;
import java.util.Date;
/**
 * 广告位于广告关系表
 * @author guowei
 *
 */
public class AdSpaceConfAdRela implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3560891298444785876L;

	/** id */
	protected Integer id;

	/** adSpaceConfId */
	protected Long adSpaceConfId;

	/** advertisingId */
	protected Long advertisingId;

	/** createTime */
	protected Date createTime;

	/** updateTime */
	protected Date updateTime;

	/** opUser */
	protected Long opUser;

	/** 广告顺序 */
	protected Integer adOrdernum;

	protected String adFlagName;
	
	/** status */
	protected Integer status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getAdSpaceConfId() {
		return adSpaceConfId;
	}
	public void setAdSpaceConfId(Long adSpaceConfId) {
		this.adSpaceConfId = adSpaceConfId;
	}
	public Long getAdvertisingId() {
		return advertisingId;
	}
	public void setAdvertisingId(Long advertisingId) {
		this.advertisingId = advertisingId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getOpUser() {
		return opUser;
	}
	public void setOpUser(Long opUser) {
		this.opUser = opUser;
	}
	public Integer getAdOrdernum() {
		return adOrdernum;
	}
	public void setAdOrdernum(Integer adOrdernum) {
		this.adOrdernum = adOrdernum;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAdFlagName() {
		return adFlagName;
	}
	public void setAdFlagName(String adFlagName) {
		this.adFlagName = adFlagName;
	}
	
	
}
