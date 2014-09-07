/*
 * 文 件 名:  Constants.java
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-8-15
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cyou.advertising.web.common;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author zhangtao
 * @version [版本号, 2014-8-15]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Constants {
	public static final String SESSION_MANAGER = "user_name";
	public static final String OPERATIONS = "operations";
	public static final String MODULES = "modules";
	public static final int SUCCESS = 1;
	public static final int FAIL = 0;
	public static final int USING = 1;// 启用
	public static final int BLOCK = 0;// 停用
	public static final String LOGIN_USER = "loginUser";
	public static final String DEFAULT_PWD = "password";
	public static final int PAGE_SIZE = 20;

	public static final int CONF_CUURENT_STATUS_EXPIRE_0 = 0;
	public static final int CONF_CUURENT_STATUS_ACTIVE_1 = 1;
	public static final int CONF_CUURENT_STATUS_FUTURE_CURRENT_20 = 20;
	public static final int CONF_CUURENT_STATUS_FUTURE_AFTER_21 = 21;
	
	public static final int OP_TYPE_ADD=1;
	public static final int OP_TYPE_DELETE=2;
	public static final int OP_TYPE_UPDATE=3;
}
