package com.cyou.advertising.web.dao.security;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.cyou.advertising.web.model.security.Roles;

public interface RolesDAO {

	public static final String ALIGNNAME ="alias_roles3";

	public static final String VIEW ="alias_roles3.`id`, alias_roles3.`name`";

	public static final String TABLENAME ="roles";

	@Select("select "+VIEW+" from "+TABLENAME+" "+ALIGNNAME+" where "+ALIGNNAME+".id=#{id}")
	public Roles findById(Integer id);

	@Select("select * from roles order by id desc")
	public List<Roles> list();

	@Insert("insert into roles (name) values (#{name})")
	public void insert(Roles roles);

	@Insert("update roles set name=#{name} where id=#{id}")
	public void update(Roles roles);

	@Delete("delete from roles where id=#{id}")
	public void delete(Integer id);

}
