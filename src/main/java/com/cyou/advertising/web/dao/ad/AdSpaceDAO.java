package com.cyou.advertising.web.dao.ad;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.cyou.advertising.web.common.Pagination;
import com.cyou.advertising.web.dao.provider.AdSpaceDAOProvider;
import com.cyou.advertising.web.model.ad.AdSpace;
import com.cyou.advertising.web.model.ad.AdSpacePv;


public interface AdSpaceDAO {

public static final String ALIGNNAME ="alias_ad_space2";

public static final String VIEW ="alias_ad_space2.`id`, alias_ad_space2.`ad_space_number` as adSpaceNumber, alias_ad_space2.`description`, alias_ad_space2.`app_client_id` as appClientId, alias_ad_space2.`app_client_version` as appClientVersion, alias_ad_space2.`support_adList` as supportAdList, alias_ad_space2.`status`";

public static final String TABLENAME ="ad_space";


@Insert("insert into ad_space (ad_space_number,description,app_client_id,app_client_version,support_adList,status) values (#{adSpaceNumber}, #{description} ,#{appClientId}, #{appClientVersion}, #{supportAdlist}, #{status})")
@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id", useCache = false, flushCache = true)  
public void insert(AdSpace adSpace);

@Select("select id,ad_space_number as adSpaceNumber,description,app_client_id as appClientId,app_client_version as appClientVersion,support_adList as supportAdList,status from ad_space where app_client_id=#{appClientId} and app_client_version=#{version}")
public List<AdSpace> findByAdClientIdAndVersion(@Param("appClientId") Integer appClientId, @Param("version") String version);

@Select("select count(1) from ad_space where app_client_id=#{appClientId} and app_client_version=#{version} and ad_space_number=#{num}")
public Integer countByAdClientIdAndVersionAndNum(@Param("appClientId") Integer appClientId, @Param("version") String version,@Param("num") String num);

/**
 * 分页查询
 * <功能详细描述>
 * @param appClientId
 * @param version
 * @param adspaceId
 * @param page
 * @return
 * @see [类、类#方法、类#成员]
 */
@SelectProvider(type=AdSpaceDAOProvider.class, method="selectByClientIdVersionAdSpaceIdPage")
public List<AdSpacePv> findByIdVerNumByPage(@Param("appClientId") Integer appClientId, @Param("version") String version,@Param("adspaceId") Integer adspaceId,@Param("page") Pagination page);

@SelectProvider(type=AdSpaceDAOProvider.class, method="selectByClientIdVersionAdSpaceId")
public List<AdSpacePv> findByIdVerNum(@Param("appClientId") Integer appClientId, @Param("version") String version,@Param("adspaceId") Integer adspaceId);


@SelectProvider(type=AdSpaceDAOProvider.class, method="countByappClientIdVersionAdSpaceNum")
public Integer countByIdVerNum(@Param("appClientId") Integer appClientId, @Param("version") String version,@Param("adspaceId") Integer adspaceId);

/**
 * 广告位在redis缓存中的key
 * clientName_version_adSpaceNum
 * @param id
 * @return
 * @see [类、类#方法、类#成员]
 */
@Select("SELECT CONCAT(ac.name,':',sp.app_client_version,':',sp.ad_space_number) FROM ad_space sp LEFT JOIN ad_client ac ON ac.id=sp.app_client_id WHERE sp.id=#{id}")
public String queryKeyById(Long id);

/**
 * 某一广告应用平台某版本的所有当前生效广告在缓存中存储的key
 * clientName_version_adSpaceNum
 * @param id
 * @return
 * @see [类、类#方法、类#成员]
 */
@Select("SELECT CONCAT(ac.name,':',sp.app_client_version) FROM ad_space sp LEFT JOIN ad_client ac ON ac.id=sp.app_client_id WHERE sp.id=#{id}")
public String queryGlobalKeyById(Long id);

@Select("SELECT status FROM ad_space  WHERE id=#{id}")
public Integer queryStatusById(Integer id);

@Select("SELECT "+VIEW+",c.name as appClientName FROM "+TABLENAME+" "+ALIGNNAME+" LEFT JOIN ad_client c  ON c.id="+ALIGNNAME+".app_client_id WHERE "+ALIGNNAME+".id=#{id}")
public AdSpacePv queryAdSpaceById(Integer id);

@Update("UPDATE ad_space SET description=#{description},support_adList=#{supportAdlist},`status`=#{status} WHERE id=#{id}")
public void update(AdSpace adSpace);

@Update("UPDATE ad_space SET `status`=#{status} WHERE id=#{id}")
public void updateStatus(@Param("id") Integer id,@Param("status") Integer status);

}
