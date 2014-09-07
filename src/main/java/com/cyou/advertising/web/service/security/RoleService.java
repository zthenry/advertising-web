package com.cyou.advertising.web.service.security;

import java.util.List;

import com.cyou.advertising.web.model.security.Roles;

public interface RoleService {

	List<Roles> list();

	void insert(Roles roles);

	void update(Roles roles);

	void delete(Integer id);

}
