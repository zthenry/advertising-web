package com.cyou.advertising.web.dao.security;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.cyou.advertising.web.model.security.ManageModule;

public interface ManageModuleDAO {

	public static final String ALIGNNAME = "alias_manage_module0";

	public static final String VIEW = "alias_manage_module0.`id`, alias_manage_module0.`name`, alias_manage_module0.`url`, alias_manage_module0.`status`, alias_manage_module0.`order_num` as orderNum";

	public static final String TABLENAME = "manage_module";

	@Select("select " + VIEW + " from " + TABLENAME + " " + ALIGNNAME
			+ " where   " + ALIGNNAME + ".id=#{id}")
	public ManageModule findById(Integer id);

	@Select("select " + VIEW + " from " + TABLENAME + " " + ALIGNNAME)
	public List<ManageModule> findAll();

	@SelectProvider(type = SqlProvider.class, method = "selectModulesByIds")
	public List<ManageModule> findByIds(List<Long> ids);

	@Insert("insert into manage_module (name, url, status, order_num) values (#{name}, '', 1, #{orderNum})")
	public void insert(ManageModule manageModule);

	@Update("update manage_module set name=#{name}, order_num=#{orderNum} where id=#{id}")
	public void update(ManageModule manageModule);

	@Delete("delete from manage_module where id=#{id}")
	public void delete(Integer id);

}
