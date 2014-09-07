package com.cyou.advertising.web.dao.security;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cyou.advertising.web.model.security.ManageModule;
import com.cyou.advertising.web.model.security.Operations;
import com.cyou.advertising.web.model.security.Users;

public interface UsersDAO {

	public static final String ALIGNNAME ="alias_users0";

	public static final String VIEW = "alias_users0.`id`, alias_users0.`userName` as userName, alias_users0.`password`, alias_users0.`status`,alias_users0.`email`,alias_users0.`roleId`";

	public static final String TABLENAME ="users";

	@Select("select "+VIEW+" from "+TABLENAME+" "+ALIGNNAME+" where   "+ALIGNNAME+".userName=#{username}")
	public Users findByUsername(String username);

	@Select("select op.name,op.url,op.module_id AS moduleId from operations op where op.id in "
			+ "(select operation_id r from role_operation_rela r where r.role_id=#{roleId}) order by op.id")
	public List<Operations> findOperationsByRoleId(
			@Param("roleId") Integer roleId);

	@Select("select mm.id,mm.name,mm.url,mm.status from manage_module mm where mm.id in "
			+ "(select DISTINCT op.module_id from operations op where op.id in (select operation_id r from role_operation_rela r where r.role_id=#{roleId}))"
			+ " order by mm.order_num")
	public List<ManageModule> findModulesByRoleId(
			@Param("roleId") Integer roleId);

	@Update("update users set password=#{password} where id=#{id}")
	public void editPwd(@Param("id")int id, @Param("password")String password);

	@Select("select * from users order by id desc")
	public List<Users> list();

	@Insert("insert into users (userName, password, status, email, roleId) values (#{userName}, #{password}, #{status}, #{email}, #{roleId})")
	public void saveUser(Users user);

	@Update("update users set userName=#{userName}, status=#{status}, email=#{email}, roleId=#{roleId} where id=#{id}")
	public void updateUser(Users user);

	@Select("select * from users where id=#{id}")
	public Users findById(Integer id);

	@Update("update users set status=#{status} where id=#{id}")
	public void updateStatus(@Param("id")Integer id, @Param("status")Integer status);

}
