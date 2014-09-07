/*
 * 文 件 名:  SecurityService.java
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-8-15
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cyou.advertising.web.service.security;

import java.util.List;
import java.util.Map;

import com.cyou.advertising.web.model.security.Users;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014-8-15]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface UserService {

	Map<String, Object> login(String username,String password) throws Exception;
  
	void editPwd(int id, String password);
  
	public List<Users> listUser();
	
	void saveUser(Users user);
	
	void updateUser(Users user);

	Users findById(Integer id);

	void updateStatus(Integer id, Integer status);
	
}
