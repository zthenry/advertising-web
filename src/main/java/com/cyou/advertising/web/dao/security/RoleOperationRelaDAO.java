package com.cyou.advertising.web.dao.security;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cyou.advertising.web.model.security.RoleOperationRela;

public interface RoleOperationRelaDAO {

	public static final String ALIGNNAME ="alias_role_operation_rela1";

	public static final String VIEW ="alias_role_operation_rela1.`id`, alias_role_operation_rela1.`role_id` as roleId, alias_role_operation_rela1.`operation_id` as operationId";

	public static final String TABLENAME ="role_operation_rela";


	/** RoleOperationRela find by ID , */
	@Select("select "+VIEW+" from "+TABLENAME+" "+ALIGNNAME+" where  "+ALIGNNAME+".id=#{id}")
	public RoleOperationRela findById(Long id);

	@Insert("insert into role_operation_rela (role_id, operation_id) values (#{roleId}, #{operationId})")
	public void insert(RoleOperationRela roleOperationRela);

	@Select("select * from role_operation_rela where role_id=#{roleId} and operation_id=#{operationId}")
	public RoleOperationRela find(@Param("roleId") Integer roleId,
			@Param("operationId") Integer operationId);

	@Delete("delete from role_operation_rela where role_id=#{roleId}")
	public void deleteByRoleId(@Param("roleId") Integer roleId);

}
