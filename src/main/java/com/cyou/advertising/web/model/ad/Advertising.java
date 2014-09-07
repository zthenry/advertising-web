package com.cyou.advertising.web.model.ad;

import java.io.Serializable;
import java.util.*;
/**
 * 广告
 * @author guowei
 *
 */
public class Advertising implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** id */
	protected Integer id;

	/** picId */
	protected Integer picId;

	/** adName */
	protected String adName;

	/** adWord */
	protected String adWord;

	/** androidUrl */
	protected String androidUrl;

	/** iosUrl */
	protected String iosUrl;

	/** createTime */
	protected Date createTime;

	/** showTime */
	protected Integer showTime;

	/** status */
	protected Integer status;

	/** androidPackage */
	protected String androidPackage;

	/** androidMd5 */
	protected String androidMd5;

	protected Integer actionType;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPicId() {
		return picId;
	}
	public void setPicId(Integer picId) {
		this.picId = picId;
	}
	public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	public String getAdWord() {
		return adWord;
	}
	public void setAdWord(String adWord) {
		this.adWord = adWord;
	}
	public String getAndroidUrl() {
		return androidUrl;
	}
	public void setAndroidUrl(String androidUrl) {
		this.androidUrl = androidUrl;
	}
	public String getIosUrl() {
		return iosUrl;
	}
	public void setIosUrl(String iosUrl) {
		this.iosUrl = iosUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getShowTime() {
		return showTime;
	}
	public void setShowTime(Integer showTime) {
		this.showTime = showTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAndroidPackage() {
		return androidPackage;
	}
	public void setAndroidPackage(String androidPackage) {
		this.androidPackage = androidPackage;
	}
	public String getAndroidMd5() {
		return androidMd5;
	}
	public void setAndroidMd5(String androidMd5) {
		this.androidMd5 = androidMd5;
	}
	public Integer getActionType() {
    	return actionType;
	}
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}
	
}
