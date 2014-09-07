/*
 * 文 件 名:  LogServiceImpl.java
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-9-1
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cyou.advertising.web.service.log.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cyou.advertising.web.common.Constants;
import com.cyou.advertising.web.dao.security.OperationLogDAO;
import com.cyou.advertising.web.model.security.OperationLog;
import com.cyou.advertising.web.model.security.Users;
import com.cyou.advertising.web.service.log.LogService;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014-9-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("logService")
public class LogServiceImpl implements LogService{
	
	@Autowired
	private OperationLogDAO operationLogDAO;
	@Override
	public void addLog(OperationLog operationLog) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Users users = (Users) request.getSession().getAttribute(Constants.LOGIN_USER);
		if (users!=null) {
			operationLog.setUserId(new Long(users.getId().intValue()));
			operationLog.setOpUsername(users.getUserName());
		}else {
			operationLog.setUserId(0L);
			operationLog.setOpUsername("");
		}
		operationLogDAO.insert(operationLog);
		
	}

}
