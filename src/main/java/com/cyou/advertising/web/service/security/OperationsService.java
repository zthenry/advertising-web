package com.cyou.advertising.web.service.security;

import java.util.List;

import com.cyou.advertising.web.model.security.Operations;

public interface OperationsService {

	public List<Operations> findByModuleId(int moduleId);

	public List<Operations> findByRoleId(int roleId);

	public void insert(Operations operations);

	public void update(Operations operations);

	public void delete(Integer id);

	public void deleteByModuleId(Integer moduleId);

}
