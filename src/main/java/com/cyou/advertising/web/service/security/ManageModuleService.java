package com.cyou.advertising.web.service.security;

import java.util.List;

import com.cyou.advertising.web.model.security.ManageModule;

public interface ManageModuleService {

	List<ManageModule> list();

	void insert(ManageModule manageModule);

	void update(ManageModule manageModule);

	void delete(Integer id);

}
