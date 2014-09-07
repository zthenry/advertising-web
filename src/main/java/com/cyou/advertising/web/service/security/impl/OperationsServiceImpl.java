package com.cyou.advertising.web.service.security.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cyou.advertising.web.dao.security.OperationsDAO;
import com.cyou.advertising.web.model.security.Operations;
import com.cyou.advertising.web.service.security.OperationsService;

@Service("operationsService")
public class OperationsServiceImpl implements OperationsService {

	@Resource
	private OperationsDAO operationsDAO;

	@Override
	public List<Operations> findByModuleId(int moduleId) {
		return operationsDAO.findByModuleId(moduleId);
	}

	@Override
	public void insert(Operations operations) {
		operationsDAO.insert(operations);
	}

	@Override
	public void update(Operations operations) {
		operationsDAO.update(operations);
	}

	@Override
	public void delete(Integer id) {
		operationsDAO.delete(id);
	}

	@Override
	public void deleteByModuleId(Integer moduleId) {
		operationsDAO.deleteByModuleId(moduleId);
	}

	@Override
	public List<Operations> findByRoleId(int roleId) {
		return operationsDAO.findByRoleId(roleId);
	}

}
