package com.cyou.advertising.web.service.security;

import com.cyou.advertising.web.model.security.RoleOperationRela;

public interface RoleOperationRelaService {

	public void insert(RoleOperationRela roleOperationRela);

	public RoleOperationRela find(Integer roleId, Integer operationId);

	public void deleteByRoleId(Integer roleId);

}
