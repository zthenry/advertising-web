package com.cyou.advertising.web.controller.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cyou.advertising.web.model.security.Operations;
import com.cyou.advertising.web.service.security.OperationsService;

@Controller
@RequestMapping("/operations")
public class OperationsController {

	@Resource
	private OperationsService operationsService;

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String listUser(HttpServletRequest request, Operations operations)
			throws Exception {
		try {
			if (operations.getId() == null)
				operationsService.insert(operations);
			else
				operationsService.update(operations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/manageModule/list";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest request, Integer id) {
		operationsService.delete(id);
		return "redirect:/manageModule/list";
	}

}
