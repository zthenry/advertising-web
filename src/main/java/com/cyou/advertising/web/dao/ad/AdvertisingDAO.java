package com.cyou.advertising.web.dao.ad;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cyou.advertising.web.common.Pagination;
import com.cyou.advertising.web.model.ad.Advertising;
import com.cyou.advertising.web.model.ad.AdvertisingPv;


public interface AdvertisingDAO{

	public static final String ALIGNNAME ="alias_advertising0";
	
	public static final String VIEW ="alias_advertising0.`id`, alias_advertising0.`pic_id` as picId, alias_advertising0.`ad_name` as adName, alias_advertising0.`ad_word` as adWord, alias_advertising0.`android_url` as androidUrl, alias_advertising0.`ios_url`as iosUrl, alias_advertising0.`create_time` as createTime, alias_advertising0.`show_time` as showTime, alias_advertising0.`status`, alias_advertising0.`android_package` as androidPackage, alias_advertising0.`android_md5` as androidMd5";
	
	public static final String TABLENAME ="advertising";

	@Select("select "+VIEW+" from "+TABLENAME+" "+ALIGNNAME+" where id=#{id}")
	public Advertising findById(Integer id);
	
	@Select("select "+VIEW+" from "+TABLENAME+" "+ALIGNNAME+" where  "+ALIGNNAME+".ad_name like CONCAT('%',#{name},'%')")
	public List<AdvertisingPv> findByName(String name);
	
	@Select("select count(1) from "+TABLENAME+" "+ALIGNNAME+" where  "+ALIGNNAME+".ad_name like CONCAT('%',#{name},'%')")
	public Integer countByName(String name);
	
	/**
	 * 根据名称分页查询
	 * <功能详细描述>
	 * @param name
	 * @param page
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Select("select "+VIEW+" from "+TABLENAME+" "+ALIGNNAME+" where  "+ALIGNNAME+".ad_name like CONCAT('%',#{name},'%') LIMIT #{page.startIndex},#{page.pageSize} ")
	public List<AdvertisingPv> findByNameByPage(@Param("name") String name,@Param("page") Pagination page);
	
	@Insert("insert into "+TABLENAME+" (ad_type, pic_id, ad_name, ad_word, android_url, ios_url, create_time, show_time, status, android_package, android_md5) " +
			"values (#{adType}, #{picId}, #{adName}, #{adWord}, #{androidUrl}, #{iosUrl}, now(), #{showTime}, #{status}, #{androidPackage}, #{androidMd5})")
	public void insert(Advertising advertising);
	
	@Update("update "+TABLENAME+" set ad_type=#{adType}, pic_id=#{picId}, ad_name=#{adName}, ad_word=#{adWord}, android_url=#{androidUrl}, ios_url=#{iosUrl}, show_time=#{showTime}, " +
			"status=#{status}, android_package=#{androidPackage}, android_md5=#{androidMd5}")
	public void update(Advertising advertising);
	
	
	@Delete("delete from "+TABLENAME+" where id=#{id}")
	public void delete(Integer id);
	
	@Select("select "+VIEW+" from "+TABLENAME+" "+ALIGNNAME+" order by id desc LIMIT #{offset},#{limit}")
	public List<Advertising> list(Map<String, Integer> map);

	@Select("select count(id) from "+TABLENAME)
	public Integer getTotalCount();
	
	@Select("SELECT "+VIEW+",rela.ad_flag_name as adFlagName FROM ad_space_conf_ad_rela rela LEFT JOIN "+TABLENAME+" "+ALIGNNAME+" ON rela.advertising_id="+ALIGNNAME+".id WHERE rela.ad_space_conf_id=#{adSpaceConfId}")
	public List<AdvertisingPv> findAdvertisingsByAdSpaceConfId(Integer adSpaceConfId);

}
