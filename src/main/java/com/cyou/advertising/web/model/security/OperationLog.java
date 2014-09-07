package com.cyou.advertising.web.model.security;

import java.util.Date;

/** null (请继承改DAO，而不要直接用该DAO区操作，防止代码需要重新生成的时候覆盖自定义的方法) */
public class OperationLog {

	/** id */
	protected Long id;

	protected Long userId;
	
	/** opUsername */
	protected String opUsername;

	/** opTime */
	protected Date opTime;

	/** opType */
	protected Integer opType;

	/** opDetail */
	protected String opDetail;

	/** adSpaceId */
	protected Long adSpaceId;

	/** advertisingId */
	protected Long advertisingId;

	/** adSpaceConfId */
	protected Long adSpaceConfId;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOpUsername() {
		return opUsername;
	}
	public void setOpUsername(String opUsername) {
		this.opUsername = opUsername;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public Integer getOpType() {
		return opType;
	}
	public void setOpType(Integer opType) {
		this.opType = opType;
	}
	public String getOpDetail() {
		return opDetail;
	}
	public void setOpDetail(String opDetail) {
		this.opDetail = opDetail;
	}
	public Long getAdSpaceId() {
		return adSpaceId;
	}
	public void setAdSpaceId(Long adSpaceId) {
		this.adSpaceId = adSpaceId;
	}
	public Long getAdvertisingId() {
		return advertisingId;
	}
	public void setAdvertisingId(Long advertisingId) {
		this.advertisingId = advertisingId;
	}
	public Long getAdSpaceConfId() {
		return adSpaceConfId;
	}
	public void setAdSpaceConfId(Long adSpaceConfId) {
		this.adSpaceConfId = adSpaceConfId;
	}
  public Long getUserId() {
    return userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }
	
	
}
