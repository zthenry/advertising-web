/*
 * 文 件 名:  AdvertisingServiceImpl.java
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-8-11
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cyou.advertising.web.service.ad.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyou.advertising.web.common.Pagination;
import com.cyou.advertising.web.dao.ad.AdClientDAO;
import com.cyou.advertising.web.dao.ad.AdvertisingDAO;

import com.cyou.advertising.web.model.ad.AdClient;
import com.cyou.advertising.web.model.ad.AdClientVersion;
import com.cyou.advertising.web.model.ad.Advertising;
import com.cyou.advertising.web.model.ad.AdvertisingPv;
import com.cyou.advertising.web.service.ad.AdvertisingService;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author Administrator
 * @version [版本号, 2014-8-11]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service("advertisingService")
public class AdvertisingServiceImpl implements AdvertisingService {

	@Autowired
	private AdvertisingDAO advertisingDAO;
	@Autowired
	private AdClientDAO adClientDAO;

	@Override
	public List<Advertising> list(Integer curPage, Integer pageSize) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("offset", (curPage - 1) * pageSize);
		map.put("limit", pageSize);
		return advertisingDAO.list(map);
	}


	@Override
	public void update(Advertising advertising) {
		advertisingDAO.update(advertising);
	}

	@Override
	public void insert(Advertising advertising) {
		advertisingDAO.insert(advertising);
	}
	
	@Override
	public Integer getTotalCount() {
		return advertisingDAO.getTotalCount();
	}

	@Override
	public List<AdvertisingPv> getAdvertisingsByName(String name) {
		// TODO Auto-generated method stub
		return advertisingDAO.findByName(name);
	}

	@Override
	public Advertising findById(Integer id) {
		return advertisingDAO.findById(id);
	}


	@Override
	public Integer countAdvertisingsByName(String name) {
		
		return advertisingDAO.countByName(name);
	}


	@Override
	public List<AdvertisingPv> getAdvertisingsByNameByPage(String name,Pagination page) {
		return advertisingDAO.findByNameByPage(name, page);
	}
}
