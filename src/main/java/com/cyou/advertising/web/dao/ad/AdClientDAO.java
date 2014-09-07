package com.cyou.advertising.web.dao.ad;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cyou.advertising.web.model.ad.AdClient;

public interface AdClientDAO {

	public static final String ALIGNNAME ="alias_ad_client0";
	
	public static final String VIEW ="alias_ad_client0.`id`, alias_ad_client0.`name`, alias_ad_client0.`status`";
	
	public static final String TABLENAME ="ad_client";

	@Select("select "+VIEW+" from "+TABLENAME+" "+ALIGNNAME+" where "+ALIGNNAME+".id=#{id} ")
	public AdClient findById (Integer id);
	
	@Select("select "+VIEW+" from "+TABLENAME+" "+ALIGNNAME+"")
	public List<AdClient> findAll ();
	
	@Select("select count(1) from "+TABLENAME)
	public int countNum();

	@Insert("insert into ad_client (name, status) values (#{name}, #{status})")
	public void insert(AdClient adClient);
	
	@Update("update ad_client set name=#{name}, status=#{status} where id=#{id}")
	public void update(AdClient adClient);
	
	@Select("select * from ad_client order by id desc")
	public List<AdClient> list();

	@Update("update ad_client set status=#{status} where id=#{id}")
	public void updateStatus(@Param("id")Integer id, @Param("status")Integer status);
}
