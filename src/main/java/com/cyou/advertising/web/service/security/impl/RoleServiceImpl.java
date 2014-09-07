package com.cyou.advertising.web.service.security.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cyou.advertising.web.dao.security.RolesDAO;
import com.cyou.advertising.web.model.security.Roles;
import com.cyou.advertising.web.service.security.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource
	private RolesDAO rolesDAO;

	@Override
	public List<Roles> list() {
		return rolesDAO.list();
	}

	@Override
	public void insert(Roles roles) {
		rolesDAO.insert(roles);
	}

	@Override
	public void update(Roles roles) {
		rolesDAO.update(roles);
	}

	@Override
	public void delete(Integer id) {
		rolesDAO.delete(id);
	}

}
