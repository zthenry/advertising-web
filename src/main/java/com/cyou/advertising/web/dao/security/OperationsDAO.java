package com.cyou.advertising.web.dao.security;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cyou.advertising.web.model.security.Operations;

public interface OperationsDAO {

	public static final String ALIGNNAME = "alias_operations2";

	public static final String VIEW = "alias_operations2.`id`, alias_operations2.`name`, alias_operations2.`url`, alias_operations2.`module_id` as moduleId, alias_operations2.`assignable`";

	public static final String TABLENAME = "operations";

	@Select("select " + VIEW + " from " + TABLENAME + " " + ALIGNNAME
			+ " where   " + ALIGNNAME + ".id=#{id}")
	public Operations findById(Long id);

	@Select("select " + VIEW + " from " + TABLENAME)
	public List<Operations> list(int curPage, int pageSize);

	@Select("select " + VIEW + " from " + TABLENAME + " " + ALIGNNAME
			+ " where   " + ALIGNNAME + ".module_id=#{moduleId}")
	public List<Operations> findByModuleId(int moduleId);

	@Insert("insert into operations (name, url, module_id) values (#{name}, #{url}, #{moduleId})")
	public void insert(Operations operations);

	@Update("update operations set name=#{name}, url=#{url}, module_id=#{moduleId} where id=#{id}")
	public void update(Operations operations);

	@Delete("delete from operations where id=#{id}")
	public void delete(Integer id);

	@Delete("delete from operations where module_id=#{moduleId}")
	public void deleteByModuleId(Integer moduleId);

	@Select("select b.id, b.name, b.url, b.module_id as moduleId from role_operation_rela a, operations b where a.operation_id=b.id and a.role_id=#{roleId}")
	public List<Operations> findByRoleId(int roleId);
}
