/*
 * 文 件 名:  SecurityServiceImpl.java
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-8-15
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cyou.advertising.web.service.security.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyou.advertising.web.common.Constants;
import com.cyou.advertising.web.dao.security.UsersDAO;
import com.cyou.advertising.web.model.security.ManageModule;
import com.cyou.advertising.web.model.security.Operations;
import com.cyou.advertising.web.model.security.Users;
import com.cyou.advertising.web.service.security.UserService;
import com.cyou.advertising.web.utils.SecurityUtil;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author Administrator
 * @version [版本号, 2014-8-15]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersDAO usersDAO;

	@Override
	public Map<String, Object> login(String username, String password)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Users user = usersDAO.findByUsername(username);
		if (null != user) {
			if (user.getPassword().equals(SecurityUtil.encryptMD5(password))) {
				if (user.getStatus() == Constants.USING) {
					List<Operations> operationList = usersDAO
							.findOperationsByRoleId(user.getRoleId());
					List<ManageModule> manageModulesList = usersDAO
							.findModulesByRoleId(user.getRoleId());
					result.put(Constants.OPERATIONS, operationList);
					result.put(Constants.MODULES, manageModulesList);
					result.put(Constants.LOGIN_USER, user);
					result.put("status", Constants.SUCCESS);
					result.put("msg", "ok");
				} else {
					result.put("status", Constants.FAIL);
					result.put("msg", "该用户已被禁用");
				}
			} else {
				result.put("status", Constants.FAIL);
				result.put("msg", "密码错误");
			}
		} else {
			result.put("status", Constants.FAIL);
			result.put("msg", "该用户不存在");
		}
		return result;
	}

	@Override
	public void editPwd(int id, String password) {
		usersDAO.editPwd(id, password);
	}

	@Override
	public List<Users> listUser() {
		return usersDAO.list();
	}

	@Override
	public void saveUser(Users user) {
		usersDAO.saveUser(user);
	}

	@Override
	public void updateUser(Users user) {
		usersDAO.updateUser(user);
	}

	@Override
	public Users findById(Integer id) {
		return usersDAO.findById(id);
	}

	@Override
	public void updateStatus(Integer id, Integer status) {
		usersDAO.updateStatus(id, status);
	}

}
