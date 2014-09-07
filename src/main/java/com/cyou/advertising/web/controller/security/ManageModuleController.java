package com.cyou.advertising.web.controller.security;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cyou.advertising.web.model.security.ManageModule;
import com.cyou.advertising.web.service.security.ManageModuleService;
import com.cyou.advertising.web.service.security.OperationsService;

@Controller
@RequestMapping("/manageModule")
public class ManageModuleController {

	@Resource
	private ManageModuleService manageModuleService;
	@Resource
	private OperationsService operationsService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request) throws Exception {
		List<ManageModule> manageModuleList = manageModuleService.list();
		for (ManageModule manageModule : manageModuleList) {
			manageModule.setOperationsList(operationsService
					.findByModuleId(manageModule.getId()));
		}
		request.setAttribute("manageModuleList", manageModuleList);
		return new ModelAndView("/security/moduleList");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request,
			ManageModule manageModule) throws Exception {
		try {
			if (manageModule.getId() == null)
				manageModuleService.insert(manageModule);
			else
				manageModuleService.update(manageModule);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/manageModule/list";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest request, Integer id)
			throws Exception {
		try {
			operationsService.deleteByModuleId(id);
			manageModuleService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/manageModule/list";
	}

}
