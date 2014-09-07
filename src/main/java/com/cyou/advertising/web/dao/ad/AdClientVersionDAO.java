package com.cyou.advertising.web.dao.ad;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cyou.advertising.web.model.ad.AdClientVersion;


public interface AdClientVersionDAO {

	public static final String ALIGNNAME ="alias_ad_client_version4";
	
	public static final String VIEW ="alias_ad_client_version4.`id` as id, alias_ad_client_version4.`ad_client_id` as adClientId, alias_ad_client_version4.`version`, alias_ad_client_version4.`status`";
	
	public static final String TABLENAME ="ad_client_version";
	
	@Insert("insert into ad_client_version (ad_client_id, version, status) values (#{adClientId}, #{version}, #{status})")
	public void insert(AdClientVersion adClientVersion);
	
	@Update("update ad_client_version set version=#{version}, status=#{status} where id=#{id})")
	public void update(AdClientVersion adClientVersion);

	@Select("select "+VIEW+" from "+TABLENAME+" "+ALIGNNAME+" where "+ALIGNNAME+".ad_client_id=#{adClientId} order by id")
	public List<AdClientVersion> findByAdClientId(@Param("adClientId") Integer adClientId);

}
