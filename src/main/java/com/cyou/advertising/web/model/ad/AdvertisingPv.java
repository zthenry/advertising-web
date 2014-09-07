/*
 * 文 件 名:  AdvertisingPv.java
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
public class AdvertisingPv extends Advertising {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 8460604016256307782L;
	
	private int checked;
	
	private String adFlagName;
	
	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}

	public String getAdFlagName() {
		return adFlagName;
	}

	public void setAdFlagName(String adFlagName) {
		this.adFlagName = adFlagName;
	}
	
	

}
