/*
 * 文 件 名:  AdspaceConfService.java
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-8-26
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cyou.advertising.web.service.ad;

import java.util.List;

import com.cyou.advertising.web.common.Pagination;
import com.cyou.advertising.web.model.ad.AdSpaceConf;
import com.cyou.advertising.web.model.ad.AdSpaceConfPv;
import com.cyou.advertising.web.model.ad.Advertising;
import com.cyou.advertising.web.model.ad.AdvertisingPv;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014-8-26]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface AdspaceConfService {

	
	void save(AdSpaceConf adSpaceConf,String adId);
	
	
	void save(AdSpaceConf adSpaceConf,List<String> adIds);
	
	/**
	 * 根据广告位id查询广告位配置信息
	 * <功能详细描述>
	 * @param adSpaceId
	 * @param confType  配置状态:1:当前生效，2:过期，3:预配置--启用,4:预配置--禁用
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<AdSpaceConfPv> queryAdSpaceConfsByAdSpaceIdByPage(Integer adSpaceId,Integer confType,Pagination page);
	
	
	Integer countAdSpaceConfsByAdSpaceId(Integer adSpaceId,Integer confType);
	/**
	 * 根据广告位配置信息id查询广告信息
	 * <功能详细描述>
	 * @param adSapceConfId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<AdvertisingPv> queryAdvertisingsByAdSapceConfId(Integer adSpaceConfId);
	
	AdSpaceConfPv queryAdSpaceConfById(Integer id);
	
	/**
	 * 删除配置
	 * <功能详细描述>
	 * @param adSpaceConfId
	 * @see [类、类#方法、类#成员]
	 */
	void deleteAdSpaceConf(Integer adSpaceConfId);
	/**
	 * 更新广告位 广告配置
	 * <功能详细描述>
	 * @param adSpaceConf 广告位配置  对象
	 * @param adIds  广告id
	 * @see [类、类#方法、类#成员]
	 */
	void updateAdSpaceConf(AdSpaceConf adSpaceConf,List<String> adIds);
	
	/**
	 * 添加广告位配置id与广告id直接的关联关系
	 * 
	 * @param adSpaceConfId
	 * @param adIds
	 * @see [类、类#方法、类#成员]
	 */
	void addAdSpaceConfAdRela(Long adSpaceConfId, List<String> adIds);
	
	/**
	 * 更新缓存信息
	 * key: clientName_version_adSpaceNum
	 * value:广告信息
	 * 刷新机制:
	 * 	首先根据adSpaceConfId 查询 配置信息，以及广告位信息。根据时间来判断该配置是否是生效的配置
	 *  if:生效 且广告位状态为启用   则刷新缓存
	 *  else 删除缓存数据
	 *  备选方案:有可能会根据配置，提前一定的时间刷入缓存中
	 * @param adSpaceConfId 广告位配置Id
	 * @see [类、类#方法、类#成员]
	 */
	String updateRedis(Integer adSpaceConfId);
	
	void updateStatus(Integer adSpaceConfId,Integer target);
	
	/**
	 * 提交或者修改广告位的某条配置时的数据校验
	 * <功能详细描述>
	 * @param adSpaceConf
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	String check(AdSpaceConfPv adSpaceConfPv);
	
	/**
	 * 判断是否当前生效的广告配置
	 * <功能详细描述>
	 * @param adSpaceConf
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean isCurrentUsing(AdSpaceConf adSpaceConf);
	
	/**
	 * 判断配置信息当前的状态:过期，生效，预配置
	 * <功能详细描述>
	 * @param adSpaceConfId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int getAdSpaceConfCurrentStatus(Integer adSpaceConfId);
}
