/*
 * 文 件 名:  AdspaceConfServiceImpl.java
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-8-26
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cyou.advertising.web.service.ad.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.cyou.advertising.web.common.Constants;
import com.cyou.advertising.web.common.Pagination;
import com.cyou.advertising.web.dao.ad.AdSpaceConfAdvertisingRelaDAO;
import com.cyou.advertising.web.dao.ad.AdSpaceConfDAO;
import com.cyou.advertising.web.dao.ad.AdSpaceDAO;
import com.cyou.advertising.web.dao.ad.AdvertisingDAO;
import com.cyou.advertising.web.model.ad.AdSpaceConf;
import com.cyou.advertising.web.model.ad.AdSpaceConfAdRela;
import com.cyou.advertising.web.model.ad.AdSpaceConfPv;
import com.cyou.advertising.web.model.ad.AdSpacePv;
import com.cyou.advertising.web.model.ad.AdvertisingPv;
import com.cyou.advertising.web.service.ad.AdspaceConfService;
import com.cyou.advertising.web.task.AdSpaceConfQueue;
import com.cyou.advertising.web.task.RedisInitTask;
import com.cyou.advertising.web.utils.JsonUtil;
import com.cyou.advertising.web.utils.RedisTemplate;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author Administrator
 * @version [版本号, 2014-8-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service("adspaceConfService")
public class AdspaceConfServiceImpl implements AdspaceConfService {

	private final Logger logger = LoggerFactory.getLogger(AdspaceConfServiceImpl.class);
	
	@Autowired
	private AdSpaceConfDAO adSpaceConfDAO;

	@Autowired
	private AdSpaceConfAdvertisingRelaDAO adSpaceConfAdvertisingRelaDAO;

	@Autowired
	private AdvertisingDAO advertisingDAO;

	@Autowired
	private RedisTemplate redis;

	@Autowired
	private AdSpaceDAO adSpaceDAO;

	@Override
	public void save(AdSpaceConf adSpaceConf, String adId) {
		adSpaceConfDAO.insert(adSpaceConf);
		List<String> adIds = new ArrayList<String>();
		adIds.add(adId);
		addAdSpaceConfAdRela(new Long(adSpaceConf.getId().intValue()), adIds);
		//刷新缓存
		updateRedis(adSpaceConf.getId().intValue());
		
		

	}

	@Override
	public void save(AdSpaceConf adSpaceConf, List<String> adIds) {
		// TODO Auto-generated method stub
		adSpaceConfDAO.insert(adSpaceConf);
		addAdSpaceConfAdRela(new Long(adSpaceConf.getId().intValue()), adIds);
		//刷新缓存
		updateRedis(adSpaceConf.getId().intValue());
	}

	@Override
	public List<AdSpaceConfPv> queryAdSpaceConfsByAdSpaceIdByPage(Integer adSpaceId, Integer confType,Pagination page) {

		return adSpaceConfDAO.findAdSpaceConfsByPage(adSpaceId, confType,page);
	}

	@Override
	public List<AdvertisingPv> queryAdvertisingsByAdSapceConfId(Integer adSpaceConfId) {

		return advertisingDAO.findAdvertisingsByAdSpaceConfId(adSpaceConfId);
	}

	@Override
	public AdSpaceConfPv queryAdSpaceConfById(Integer id) {

		return adSpaceConfDAO.findByAdSpaceConfId(id);
	}

	@Override
	public void addAdSpaceConfAdRela(Long adSpaceConfId, List<String> adIds) {

		// 先删除，再添加
		adSpaceConfAdvertisingRelaDAO.deleteByAdSpaceConfId(adSpaceConfId);
		for (String adId : adIds) {
			String[] idArray = adId.split("_");
			AdSpaceConfAdRela adSpaceConfAdRela = new AdSpaceConfAdRela();
			adSpaceConfAdRela.setAdOrdernum(1);
			adSpaceConfAdRela.setAdSpaceConfId(adSpaceConfId);
			adSpaceConfAdRela.setAdvertisingId(Long.parseLong(idArray[0]));
			adSpaceConfAdRela.setCreateTime(new Date());
			adSpaceConfAdRela.setStatus(1);
			adSpaceConfAdRela.setUpdateTime(new Date());
			if (idArray.length>1) {
				adSpaceConfAdRela.setAdFlagName(idArray[1]);
			}else {
				adSpaceConfAdRela.setAdFlagName("");
			}
			adSpaceConfAdvertisingRelaDAO.insert(adSpaceConfAdRela);
		}
		
	}

	@Override
	public void updateAdSpaceConf(AdSpaceConf adSpaceConf, List<String> adIds) {
		adSpaceConfDAO.update(adSpaceConf);
		addAdSpaceConfAdRela(new Long(adSpaceConf.getId().intValue()), adIds);
		//刷新缓存
		updateRedis(adSpaceConf.getId().intValue());
	}

	@Override
	public String updateRedis(Integer adSpaceConfId) {
		AdSpaceConfPv adSpaceConf = adSpaceConfDAO.findByAdSpaceConfId(adSpaceConfId);
		
		//广告位状态 1为启用，0为禁用
		Integer adSpaceStatus = adSpaceConfDAO.findAdSpaceStatusByAdSpaceConfId(adSpaceConfId);

		Jedis jedis = redis.jedis();
		if (adSpaceConf != null) {
			Long adSpaceId = adSpaceConf.getAdSpaceId();
			
			//缓存中的key
			String key = adSpaceDAO.queryKeyById(adSpaceId);

			//配置启用，且广告位启用
			/**
			 * 逻辑：
			 * if 广告位启用
			 *     if 配置启用中
			 *        if 当前生效
			 *           立即刷新缓存 设置过期时间
			 *        else if 当天预配置
			 *                放入队列，等待时间到了刷新
			 *        else if 将来预配置
			 *                不做处理            
			 *     else if 配置禁用中
			 *             if 当前生效
			 *                不做处理
			 *             else if 预配置(不做区分)
			 *                     不做处理
			 *                   
			 *        
			 * else
			 *    删除缓存数据
			 */
			
			if(adSpaceStatus.intValue()==1){
			    int currnetStatus = this.getAdSpaceConfCurrentStatus(adSpaceConfId);
			    if (adSpaceConf.getStatus() != null && adSpaceConf.getStatus().intValue() == 1)
                {
                    
                    if (currnetStatus==Constants.CONF_CUURENT_STATUS_ACTIVE_1)
                    {
                     // 获取当前生效的广告信息
                        List<AdvertisingPv> advertisings = advertisingDAO.findAdvertisingsByAdSpaceConfId(adSpaceConfId);


                        
                        //String key = adSpaceDAO.queryKeyById(pv.getAdSpaceId());
                        String globalKey = adSpaceDAO.queryGlobalKeyById(adSpaceId);
                        System.out.println(key);
                        //Jedis jedis = redis.jedis();
                        //List<Advertising> advertisings = advertisingDAO.findAdvertisingsByAdSpaceConfId(adspaceConfId);
                        Map<String, Object> map = new TreeMap<String, Object>();
                        map.put("key", key);
                        map.put("startTime", adSpaceConf.getStartTime());
                        map.put("endTime", adSpaceConf.getEndTime());
                        map.put("data", advertisings);
                        jedis.set(key, JsonUtil.toJson(map));
                        jedis.expire(key, (int)((adSpaceConf.getEndTime().getTime()-new Date().getTime())/1000));
                        
                        jedis.sadd(globalKey, key);
                       
                        String ads = jedis.get(key);

                        logger.error(ads);
                        return ads;
                    }else if (currnetStatus==Constants.CONF_CUURENT_STATUS_FUTURE_CURRENT_20) {
                        //放入队列中
                        try
                        {
                            AdSpaceConfQueue.add(adSpaceConfId);
                            logger.error("[updateRedis] adSpaceConfId:"+adSpaceConfId+"放入队列");
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }else if (currnetStatus==Constants.CONF_CUURENT_STATUS_FUTURE_AFTER_21) {
                        //暂不做处理
                        
                    }
                }else if (adSpaceConf.getStatus() != null && adSpaceConf.getStatus().intValue() == 0) {
                    //配置禁用
                    if (currnetStatus==Constants.CONF_CUURENT_STATUS_ACTIVE_1)
                    {
                        //不应该出现此类情况
                    }else if (currnetStatus==Constants.CONF_CUURENT_STATUS_FUTURE_CURRENT_20 || currnetStatus==Constants.CONF_CUURENT_STATUS_FUTURE_AFTER_21){
                        //不做处理，在定时任务从队列中取数据的时候，判断广告位状态 或者配置状态 有一个为禁用时，删除即可，不去加载
                    }
                }
			    
			    
			}else {
			    jedis.del(key);
            }

		}
		return "";
	}

	@Override
	public void updateStatus(Integer adSpaceConfId, Integer target) {
	    
		adSpaceConfDAO.updateStatus(adSpaceConfId, target);
		//刷新缓存
		this.updateRedis(adSpaceConfId);
	}

	@Override
	public String check(AdSpaceConfPv adSpaceConfPv) {
		/**
		 * 能被修改的是未过期的配置，所以如下 关键点的校验：
		 * 1.开始时间与结束时间关系校验
		 * 2.新增 /修改预配置  开始时间与当前时间的关系
		 * 2.同一个广告位下 配置间 时间区间不能有交集
		 * 3.如果不支持广告组，那么也得校验
		 */
		Date start = adSpaceConfPv.getStartTime();
		Date end = adSpaceConfPv.getEndTime();
		Integer id = adSpaceConfPv.getId();
		if (start == null || end == null) {

			return "配置生效时间不能为空";
		} else {
			if (start.after(end)) {

				return "开始时间与结束时间 前后关系错误";
			}
		}
		
		if (id==null || id.intValue()==0 || !isCurrentUsing(adSpaceConfPv)) {
			if (start.before(new Date())) {
				return "开始生效时间不能早于当前时间!";
			}
		}
		
		
		String[] adIdArray = adSpaceConfPv.getAdIds().split(",");
		AdSpacePv adSpace = adSpaceDAO.queryAdSpaceById(adSpaceConfPv.getAdSpaceId().intValue());
		if (adIdArray.length>1 && adSpace!=null && adSpace.getSupportAdlist()!=null && adSpace.getSupportAdlist()==0) {
			return "该广告位当前不支持广告组,只能关联一个广告";
		}
		
		//状态为启用
		if (adSpaceConfPv.getStatus().intValue()==1) {
			//修改
			if (id != null && id.intValue() > 0) {
				//查看除此之外，同一时间段内有没有有效的广告配置
				Integer count = adSpaceConfDAO.countCurrentUsing(id,adSpaceConfPv.getStartTime(),adSpaceConfPv.getAdSpaceId());
				if (count!=null && count.intValue() > 0) {
					return "同一个广告位下 不同配置信息的生效 时间区间不能有交集!";
				}
			} else {
				//新增
				Integer count = adSpaceConfDAO.countCurrentUsing(0,adSpaceConfPv.getStartTime(),adSpaceConfPv.getAdSpaceId());
				if (count.intValue() > 0) {
					return "同一个广告位下 不同配置信息的生效 时间区间不能有交集!";
				}
			}
		}
		return null;
	}

	@Override
	public boolean isCurrentUsing(AdSpaceConf adSpaceConf){
		Date start = adSpaceConf.getStartTime();
		Date end = adSpaceConf.getEndTime();
		Integer status = adSpaceConf.getStatus();
		if (status.intValue()==1 && start.before(new Date()) && end.after(new Date())) {
			return true;
		}
		return false;
	}

    @Override
    public int getAdSpaceConfCurrentStatus(Integer adSpaceConfId)
    {
        AdSpaceConfPv adSpaceConfPv = adSpaceConfDAO.findByAdSpaceConfId(adSpaceConfId);
      //区别广告当前的状态:0过期，1当前生效，20预配置当天  21预配置以后
        Date endTime = adSpaceConfPv.getEndTime();
        Date currentDate = new Date();
        if (endTime.before(currentDate)) {
            //过期
//            adSpaceConfPv.setCurrentStatus(0);
            return Constants.CONF_CUURENT_STATUS_EXPIRE_0;
        }else {
            Date startTime = adSpaceConfPv.getStartTime();
            if (startTime.after(currentDate)) {
                //预配置
                long start = startTime.getTime()/(24*60*60*1000);
                long currtent = currentDate.getTime()/(24*60*60*1000);
                if (start==currtent)
                {
                    //当天预配置
                    //adSpaceConfPv.setCurrentStatus(20);
                    return Constants.CONF_CUURENT_STATUS_FUTURE_CURRENT_20;
                }else {
                    //将来预配置
//                    adSpaceConfPv.setCurrentStatus(21);
                    return Constants.CONF_CUURENT_STATUS_FUTURE_AFTER_21;
                }
                
            }else {
                //当前生效
//                adSpaceConfPv.setCurrentStatus(1);
                return Constants.CONF_CUURENT_STATUS_ACTIVE_1;
            }
            
            
        }
        
    }

	@Override
	public Integer countAdSpaceConfsByAdSpaceId(Integer adSpaceId, Integer confType) {
		
		return adSpaceConfDAO.countAdSpaceConfs(adSpaceId, confType);
	}

	@Override
	public void deleteAdSpaceConf(Integer adSpaceConfId) {
		AdSpaceConfPv adSpaceConfPv = adSpaceConfDAO.findByAdSpaceConfId(adSpaceConfId);
		Date start = adSpaceConfPv.getStartTime();
		if (start.after(new Date())) {
			adSpaceConfDAO.delete(adSpaceConfId);
			adSpaceConfAdvertisingRelaDAO.deleteByAdSpaceConfId(new Long(adSpaceConfId.intValue()));
		}
		
	}
}
