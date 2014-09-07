package com.cyou.advertising.web.controller.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cyou.advertising.web.common.Constants;
import com.cyou.advertising.web.model.security.Roles;
import com.cyou.advertising.web.model.security.Users;
import com.cyou.advertising.web.service.security.RoleService;
import com.cyou.advertising.web.service.security.UserService;
import com.cyou.advertising.web.utils.JsonUtil;
import com.cyou.advertising.web.utils.SecurityUtil;
import com.cyou.advertising.web.utils.Utils;

/**
 * 
 * @author zhangtao
 * @version [版本号, 2014-8-14]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("/user/manage")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Resource
	private RoleService roleService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		return new ModelAndView("/login");
	}

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("userName") String userName,
			@RequestParam("password") String password,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/index");
		try {
			Map<String, Object> map = userService.login(userName, password);
			if ((Integer) map.get("status") == Constants.SUCCESS) {
				Users user = (Users) map.get(Constants.LOGIN_USER);
				request.getSession().setAttribute(Constants.LOGIN_USER, user);
				mv.addObject(Constants.OPERATIONS,
						JsonUtil.toJson(map.get(Constants.OPERATIONS)));
				mv.addObject(Constants.MODULES,
						JsonUtil.toJson(map.get(Constants.MODULES)));
				logger.info(userName + "登录成功");
				return mv;
			} else {
				request.setAttribute("msg", map.get("msg"));
				return new ModelAndView("/login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request) {
		Users user = (Users) request.getSession().getAttribute(
				Constants.LOGIN_USER);
		if (user != null) {
			logger.info(user.getUserName() + "退出系统");
			request.getSession().removeAttribute(Constants.LOGIN_USER);
		}
		return new ModelAndView("/logout");
	}

	@RequestMapping(value = "/toEditPwd", method = RequestMethod.GET)
	public ModelAndView toEditPwd(HttpServletRequest request) {
		return new ModelAndView("/security/pwdEdit");
	}

	@RequestMapping(value = "/editPwd", method = RequestMethod.POST)
	public ModelAndView editPwd(HttpServletRequest request, String password)
			throws Exception {
		Users user = (Users) request.getSession().getAttribute(
				Constants.LOGIN_USER);
		userService.editPwd(user.getId(), SecurityUtil.encryptMD5(password));
		request.setAttribute("msg", "操作成功");
		return new ModelAndView("/security/pwdEdit");
	}

	@RequestMapping(value = "/listUser", method = RequestMethod.GET)
	public ModelAndView listUser(HttpServletRequest request) throws Exception {
		List<Users> userList = userService.listUser();
		request.setAttribute("userList", userList);

		List<Roles> rolesList = roleService.list();
		request.setAttribute("rolesList", rolesList);
		return new ModelAndView("/security/userList");
	}

	@RequestMapping(value = "/toAddUser", method = RequestMethod.GET)
	public ModelAndView toAdd(HttpServletRequest request) {
		List<Roles> rolesList = roleService.list();
		request.setAttribute("rolesList", rolesList);
		return new ModelAndView("/security/userAdd");
	}

	@RequestMapping(value = "/toEditUser", method = RequestMethod.GET)
	public ModelAndView toEditUser(HttpServletRequest request, Integer id) {
		Users user = userService.findById(id);
		request.setAttribute("user", user);

		List<Roles> rolesList = roleService.list();
		request.setAttribute("rolesList", rolesList);
		return new ModelAndView("/security/userEdit");
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request, Users user) throws Exception {
		user.setPassword(SecurityUtil.encryptMD5(user.getPassword()));
		userService.saveUser(user);
		return "redirect:/user/manage/listUser";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request, Users user) throws Exception {
		userService.updateUser(user);
		return "redirect:/user/manage/listUser";
	}

	@RequestMapping(value = "/resetPwd")
	public void resetPwd(HttpServletRequest request,
			HttpServletResponse response, Integer id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userService.editPwd(id,
					SecurityUtil.encryptMD5(Constants.DEFAULT_PWD));
			map.put("status", 1);
			map.put("msg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "操作失败");
		}
		Utils.ajaxPrint(response, map);
	}

	@RequestMapping(value = "/updateStatus")
	public String updateStatus(HttpServletRequest request,
			HttpServletResponse response, Integer id, Integer status)
					throws Exception {
		Integer flag = 1;
		try {
			userService.updateStatus(id, status);
		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
		}
		return "redirect:/user/manage/listUser?flag=" + flag;
	}

}
