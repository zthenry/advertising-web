/*
 * 文 件 名:  AdspaceServiceImpl.java
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-8-25
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cyou.advertising.web.service.ad.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.cyou.advertising.web.common.Constants;
import com.cyou.advertising.web.common.Pagination;
import com.cyou.advertising.web.dao.ad.AdSpaceConfDAO;
import com.cyou.advertising.web.dao.ad.AdSpaceDAO;
import com.cyou.advertising.web.dao.ad.AdvertisingDAO;
import com.cyou.advertising.web.dao.provider.AdSpaceConfDAOProvider;
import com.cyou.advertising.web.model.ad.AdSpace;
import com.cyou.advertising.web.model.ad.AdSpaceConfPv;
import com.cyou.advertising.web.model.ad.AdSpacePv;
import com.cyou.advertising.web.model.ad.Advertising;
import com.cyou.advertising.web.model.ad.AdvertisingPv;
import com.cyou.advertising.web.model.security.OperationLog;
import com.cyou.advertising.web.service.ad.AdspaceService;
import com.cyou.advertising.web.service.log.LogService;
import com.cyou.advertising.web.utils.JsonUtil;
import com.cyou.advertising.web.utils.Page;
import com.cyou.advertising.web.utils.RedisTemplate;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014-8-25]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("adspaceService")
public class AdspaceServiceImpl implements AdspaceService {

	@Autowired 
	private AdSpaceDAO adSpaceDAO;
	@Autowired
	private AdSpaceConfDAO adSpaceConfDAO;
	
	@Autowired
	private AdvertisingDAO advertisingDAO;
	
	@Autowired
	private LogService logService;

    @Autowired
    private RedisTemplate redis;
    
	@Override
	public void add(AdSpace adSpace) {
		Integer id = adSpace.getId();
		OperationLog operationLog = new OperationLog();
		operationLog.setOpTime(new Date());
		
		
		if (id!=null && id.intValue()>0) {
			//修改
			operationLog.setAdSpaceId(new Long(id));
			operationLog.setOpType(Constants.OP_TYPE_UPDATE);
			operationLog.setOpDetail("修改广告位");
			adSpaceDAO.update(adSpace);
			logService.addLog(operationLog);
		}else {
			//新增
			operationLog.setOpType(Constants.OP_TYPE_ADD);
			operationLog.setOpDetail("新增广告位");
			adSpaceDAO.insert(adSpace);
			operationLog.setAdSpaceId(new Long(adSpace.getId()));
			logService.addLog(operationLog);
		}
		
	}
	
	@Override
	public List<AdSpacePv> queryByPage(Integer appClientId, String version, Integer adspaceId,Pagination page) {

		List<AdSpacePv> adSpaces = adSpaceDAO.findByIdVerNumByPage(appClientId, version, adspaceId,page);
		return adSpaces;
	}

	@Override
	public Integer queryStatus(Integer adspaceId) {
		
		return adSpaceDAO.queryStatusById(adspaceId);
	}

	@Override
	public AdSpacePv queryAdSpaceById(Integer adspaceId) {
		return adSpaceDAO.queryAdSpaceById(adspaceId);
	}

	@Override
	public void updateStatus(Integer adspaceId, Integer status) {
	    
		adSpaceDAO.updateStatus(adspaceId, status);
		//刷新缓存
		updateRedisByAdSpaceId(adspaceId);
	}

    @Override
    public void updateRedisByAdSpaceId(Integer adspaceId)
    {
        Integer status = adSpaceDAO.queryStatusById(adspaceId);
        //缓存中的key
        String key = adSpaceDAO.queryKeyById(new Long(adspaceId.intValue()));
        Jedis jedis = redis.jedis();
        if (status.intValue()==1)
        {
            //启用广告位
            List<AdSpaceConfPv> adSpaceConfPvs = adSpaceConfDAO.findAdSpaceConfs(adspaceId, AdSpaceConfDAOProvider.SQL_CONF_TYPE_ACTIVE_USE_1);
            if (adSpaceConfPvs!=null && adSpaceConfPvs.size()!=0)
            {
                
                
                List<AdvertisingPv> advertisings = advertisingDAO.findAdvertisingsByAdSpaceConfId(adSpaceConfPvs.get(0).getId());
                jedis.set(key, JsonUtil.toJson(advertisings));
            }
        }else if (status.intValue()==0)
        {
            jedis.del(key);
        }
        
    }

	@Override
	public Integer count(Integer appClientId, String version, Integer adspaceId) {
		
		return adSpaceDAO.countByIdVerNum(appClientId, version, adspaceId);
	}

	@Override
	public List<AdSpacePv> query(Integer appClientId, String version, Integer adspaceId) {
		List<AdSpacePv> adSpaces = adSpaceDAO.findByIdVerNum(appClientId, version, adspaceId);
		return adSpaces;
	}

	@Override
	public String checkBeforeAddAdspace(AdSpace adSpace) {
		try {
			Integer  result = adSpaceDAO.countByAdClientIdAndVersionAndNum(adSpace.getAppClientId().intValue(), adSpace.getAppClientVersion(), adSpace.getAdSpaceNumber());
			if (result!=null && result.intValue()>0) {
				return "该编号已存在";
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		
		}
		
		return null;
	}

}
