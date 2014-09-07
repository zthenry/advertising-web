/*
 * 文 件 名:  AdspaceService.java
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-8-25
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cyou.advertising.web.service.ad;

import java.util.List;

import com.cyou.advertising.web.common.Pagination;
import com.cyou.advertising.web.model.ad.AdSpace;
import com.cyou.advertising.web.model.ad.AdSpacePv;
import com.cyou.advertising.web.utils.Page;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014-8-25]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface AdspaceService {

	void add(AdSpace adSpace);
	
	/**
	 * 分页查询广告位
	 * <功能详细描述>
	 * @param appClientId 平台id
	 * @param version 平台版本
	 * @param adspaceId 广告位id
	 * @param curPage 当前页
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<AdSpacePv> queryByPage(Integer appClientId,String version,Integer adspaceId,Pagination page);
	
	
	/**
	 * 查询广告位
	 * <功能详细描述>
	 * @param appClientId 平台id
	 * @param version 平台版本
	 * @param adspaceId 广告位id
	 * @param curPage 当前页
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<AdSpacePv> query(Integer appClientId,String version,Integer adspaceId);
	
	/**
	 * 查询总数
	 * <功能详细描述>
	 * @param appClientId 平台id
	 * @param version 平台版本
	 * @param adspaceId 广告位id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	Integer count(Integer appClientId,String version,Integer adspaceId);
	
	/**
	 * 查询广告位状态
	 * @param adspaceId 广告位id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	Integer queryStatus(Integer adspaceId);
	
	/**
	 * 根据广告位id查询广告位信息
	 * <功能详细描述>
	 * @param adspaceId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	AdSpacePv queryAdSpaceById(Integer adspaceId);
	
	void updateStatus(Integer adspaceId,Integer status);
	
	/**
	 * 根据广告位id刷新缓存数据
	 * <功能详细描述>
	 * @param adspaceId
	 * @see [类、类#方法、类#成员]
	 */
	void updateRedisByAdSpaceId(Integer adspaceId);
	
	String checkBeforeAddAdspace(AdSpace adSpace);
}
