/*
 * 文 件 名:  AdSpaceConfPv.java
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-8-28
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cyou.advertising.web.model.ad;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014-8-28]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AdSpaceConfPv extends AdSpaceConf {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -2775247523461143883L;
	
	/**
	 * 广告id,多个广告id用逗号隔开
	 */
	
	private String adIds;
	
	/**
	 * 根据当前时间，判断广告是否过期
	 * 0 过期 1 当前生效  2 预配置 
	 */
	private Integer currentStatus;
	
	
	public String getAdIds() {
		return adIds;
	}

	public void setAdIds(String adIds) {
		this.adIds = adIds;
	}

	public Integer getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Integer currentStatus) {
		this.currentStatus = currentStatus;
	}
	
	
	

}
