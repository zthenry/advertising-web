package com.cyou.advertising.web.controller.security;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cyou.advertising.web.model.security.ManageModule;
import com.cyou.advertising.web.model.security.Operations;
import com.cyou.advertising.web.model.security.RoleOperationRela;
import com.cyou.advertising.web.model.security.Roles;
import com.cyou.advertising.web.service.security.ManageModuleService;
import com.cyou.advertising.web.service.security.OperationsService;
import com.cyou.advertising.web.service.security.RoleOperationRelaService;
import com.cyou.advertising.web.service.security.RoleService;

/**
 * 角色管理
 * @author guowei
 *
 */
@Controller
@RequestMapping("/roles")
public class RoleController {

	@Resource
	private RoleService roleService;

	@Resource
	private OperationsService operationsService;

	@Resource
	private ManageModuleService manageModuleService;

	@Resource
	private RoleOperationRelaService roleOperationRelaService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listUser(HttpServletRequest request) throws Exception {
		List<Roles> rolesList = roleService.list();
		request.setAttribute("rolesList", rolesList);
		return new ModelAndView("/security/rolesList");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request, Roles roles)
			throws Exception {
		try {
			if (roles.getId() == null) {
				roleService.insert(roles);
			} else {
				roleService.update(roles);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/roles/list";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest request, Integer id)
			throws Exception {
		try {
			roleService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/roles/list";
	}

	/**
	 * 根据角色id获得所有资源
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listOperations", method = RequestMethod.GET)
	public String listOperations(HttpServletRequest request, Integer id)
			throws Exception {
		request.setAttribute("roleId", id);

		List<Roles> rolesList = roleService.list();
		request.setAttribute("rolesList", rolesList);

		List<Operations> operationsList = operationsService.findByRoleId(id);
		request.setAttribute("operationsList", operationsList);

		List<ManageModule> manageModuleList = manageModuleService.list();
		for (ManageModule manageModule : manageModuleList) {
			manageModule.setOperationsList(operationsService
					.findByModuleId(manageModule.getId()));
		}
		request.setAttribute("manageModuleList", manageModuleList);
		return "/security/rolesList";
	}

	@RequestMapping(value = "/assignOperations", method = RequestMethod.POST)
	public String assignOperations(HttpServletRequest request, Integer roleId,
			String[] oids) throws Exception {
		int flag = 1;
		try {
			if (oids == null || oids.length == 0) {
				flag = 0;
			} else {
				roleOperationRelaService.deleteByRoleId(roleId);
				for (String str : oids) {
					Integer operationId = Integer.valueOf(str);
					RoleOperationRela roleOperationRela = new RoleOperationRela();
					roleOperationRela.setOperationId(operationId);
					roleOperationRela.setRoleId(roleId);
					roleOperationRelaService.insert(roleOperationRela);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
		}
		return "redirect:/roles/listOperations?id=" + roleId + "&flag=" + flag;
	}
}
