/*
 * 文 件 名:  LogService.java
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-9-1
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cyou.advertising.web.service.log;

import com.cyou.advertising.web.model.security.OperationLog;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhangtao
 * @version  [版本号, 2014-9-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface LogService {
	
	void addLog(OperationLog operationLog);

}
