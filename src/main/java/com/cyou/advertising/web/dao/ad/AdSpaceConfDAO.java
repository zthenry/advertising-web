package com.cyou.advertising.web.dao.ad;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.cyou.advertising.web.common.Pagination;
import com.cyou.advertising.web.dao.provider.AdSpaceConfDAOProvider;
import com.cyou.advertising.web.model.ad.AdSpaceConf;
import com.cyou.advertising.web.model.ad.AdSpaceConfPv;



public interface AdSpaceConfDAO {

public static final String ALIGNNAME ="alias_ad_space_conf1";

public static final String VIEW ="alias_ad_space_conf1.`id`, alias_ad_space_conf1.`ad_space_id` as adSpaceId, alias_ad_space_conf1.`create_time` as createTime, alias_ad_space_conf1.`start_time` as startTime, alias_ad_space_conf1.`end_time` as endTime, alias_ad_space_conf1.`status`";

public static final String TABLENAME ="ad_space_conf";


@Insert("insert into ad_space_conf (ad_space_id,create_time,start_time,end_time,status) values(#{adSpaceId},#{createTime},#{startTime},#{endTime},#{status})")
@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id", useCache = false, flushCache = true)  
public void insert(AdSpaceConf adSpaceConf);

/**
 * 根据广告位id查询广告位配置信息
 * <功能详细描述>
 * @param adSpaceId
 * @param confType 配置状态:0:全部 1:当前生效启用，2:过期，3:预配置--启用,4:预配置--禁用
 * @return
 * @see [类、类#方法、类#成员]
 */
@SelectProvider(type=AdSpaceConfDAOProvider.class, method="selectByAdSpaceIdAndConfStatus")
public List<AdSpaceConfPv> findAdSpaceConfs(@Param("adSpaceId")Integer adSpaceId,@Param("confType") Integer confType);

@SelectProvider(type=AdSpaceConfDAOProvider.class, method="selectByAdSpaceIdAndConfStatus")
public List<AdSpaceConfPv> findAdSpaceConfsByPage(@Param("adSpaceId")Integer adSpaceId,@Param("confType") Integer confType,@Param("page") Pagination page);

@SelectProvider(type=AdSpaceConfDAOProvider.class, method="countByAdSpaceIdAndConfStatus")
public Integer countAdSpaceConfs(@Param("adSpaceId")Integer adSpaceId,@Param("confType") Integer confType);

/**
 * 根据广告配置id查询对应的广告位状态
 * <功能详细描述>
 * @param adSpaceConfId
 * @return
 * @see [类、类#方法、类#成员]
 */
@Select("select sp.status from ad_space_conf conf left join ad_space sp on sp.id=conf.ad_space_id where conf.id=#{adSpaceConfId}")
public Integer findAdSpaceStatusByAdSpaceConfId(Integer adSpaceConfId);

/**
 * 根据广告配置id查询配置详情
 * <功能详细描述>
 * @param adSpaceConfId
 * @return
 * @see [类、类#方法、类#成员]
 */
@Select("SELECT "+VIEW+" FROM "+TABLENAME+" "+ALIGNNAME+" WHERE "+ALIGNNAME+".id=#{adSpaceConfId}")
public AdSpaceConfPv findByAdSpaceConfId(Integer adSpaceConfId);

@Update("UPDATE ad_space_conf SET start_time = #{startTime},end_time = #{endTime}, `status` = #{status} WHERE id = #{id}")
public void update(AdSpaceConf adSpaceConf);


@Update("UPDATE ad_space_conf SET `status`=#{status} WHERE id=#{id}")
public void updateStatus(@Param("id") Integer id,@Param("status") Integer status);



@Select("SELECT COUNT(1) FROM ad_space_conf WHERE `status`=1 AND  start_time<=#{start} AND end_time>=#{start} AND id!=#{id} and ad_space_id=#{adSpaceId}")
public Integer countCurrentUsing(@Param("id") Integer id,@Param("start") Date start,@Param("adSpaceId") Long adSpaceId);

@Select("SELECT COUNT(1) FROM ad_space_conf WHERE `status`=1 AND  start_time<=NOW() AND end_time>=NOW()")
public Integer countAllCurrentUsing();	

@Select("SELECT id FROM ad_space_conf WHERE `status`=1 AND  start_time<=NOW() AND end_time>=NOW()")
public List<Integer> findAllCurrentUsingAdSpaceConfId();  

/**
 * 查找与当前时间是一天，启用中的 并且是预配置的配置信息
 * <功能详细描述>
 * @return
 * @see [类、类#方法、类#成员]
 */
@Select("select id from ad_space_conf where (TIMESTAMPDIFF(day,now(),start_time)=0 or TIMESTAMPDIFF(day,start_time,now())=0) and status=1")
public List<Integer> findAllTodayAdSpaceConfs();

@Delete("DELETE FROM ad_space_conf WHERE id=#{adSpaceConfId}")
public void delete(Integer adSpaceConfId);

}
