package com.cyou.advertising.web.model.ad;

import java.io.Serializable;
import java.util.Date;
/**
 * 广告位配置
 * @author guowei
 *
 */
public class AdSpaceConf implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -613809907854143355L;

	/** id */
	protected Integer id;

	/** adSpaceId */
	protected Long adSpaceId;

	/** createTime */
	protected Date createTime;

	/** startTime */
	protected Date startTime;

	/** endTime */
	protected Date endTime;

	/** status */
	protected Integer status;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getAdSpaceId() {
		return adSpaceId;
	}
	public void setAdSpaceId(Long adSpaceId) {
		this.adSpaceId = adSpaceId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
