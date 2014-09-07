package com.cyou.advertising.web.service.security.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cyou.advertising.web.dao.security.RoleOperationRelaDAO;
import com.cyou.advertising.web.model.security.RoleOperationRela;
import com.cyou.advertising.web.service.security.RoleOperationRelaService;

@Service("roleOperationRelaService")
public class RoleOperationRelaServiceImpl implements RoleOperationRelaService {

	@Resource
	private RoleOperationRelaDAO roleOperationRelaDAO;

	@Override
	public void insert(RoleOperationRela roleOperationRela) {
		roleOperationRelaDAO.insert(roleOperationRela);
	}

	@Override
	public RoleOperationRela find(Integer roleId, Integer operationId) {
		return roleOperationRelaDAO.find(roleId, operationId);
	}

	@Override
	public void deleteByRoleId(Integer roleId) {
		roleOperationRelaDAO.deleteByRoleId(roleId);
	}

}
