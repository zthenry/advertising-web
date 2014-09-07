/*
 * 文 件 名:  RedisInitTask.java
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-8-13
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cyou.advertising.web.task;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

import com.cyou.advertising.web.dao.ad.AdSpaceConfDAO;
import com.cyou.advertising.web.dao.ad.AdSpaceDAO;
import com.cyou.advertising.web.dao.ad.AdvertisingDAO;
import com.cyou.advertising.web.model.ad.AdSpaceConfPv;
import com.cyou.advertising.web.model.ad.AdvertisingPv;
import com.cyou.advertising.web.utils.JsonUtil;
import com.cyou.advertising.web.utils.RedisTemplate;

/**
 * 定时任务
 * 
 * 
 * @author  Administrator
 * @version  [版本号, 2014-8-13]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component
public class RedisInitTask
{
    
    @Autowired
    private AdSpaceConfDAO adSpaceConfDAO;
    
    @Autowired
    private AdSpaceDAO adSpaceDAO;
    
    @Autowired
    private AdvertisingDAO advertisingDAO;
    
    @Autowired
    private RedisTemplate redis;
    
    private static boolean flag=false;
    private final Logger logger = LoggerFactory.getLogger(RedisInitTask.class);
    
   
    /**
     * 0点0分0秒 将这一天的预配置全部加载进来
     * 生产者
     * @see [类、类#方法、类#成员]
     */
    @Scheduled(cron="0 0 0 * * ?")
    public void loadTodayConf()
    {
        
        
    	logger.error("开始加载当天预配置广告....");
        List<Integer> adspaceConfIds = adSpaceConfDAO.findAllTodayAdSpaceConfs();
        if (adspaceConfIds==null || adspaceConfIds.size()==0)
        {
        	logger.error("无数据加载");
            return;
        }
        for (Integer adSpaceConfId: adspaceConfIds)
        {
            try
            {
            	logger.error(adSpaceConfId+"加载入队列");
                AdSpaceConfQueue.add(adSpaceConfId);
            }
            catch (InterruptedException e)
            {
                logger.error(e.toString());
            }
        }
        
        logger.error("加载结束");
    }
    

    /**
     * 间隔5分钟执行一次
     * 消费者
     * @see [类、类#方法、类#成员]
     */
    @Scheduled(fixedRate = 300000)
    void dealAdspaceConfFromQueue()
    {
        while (!flag)
        {
            try
            {
                Thread.sleep(5000L);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            logger.error("等待系统初始化缓存队列");
        }
        Integer adspaceConfIdFlag=0;
        while (true) {
        	Integer adspaceConfId = AdSpaceConfQueue.remove();
            if (adspaceConfId==null || adspaceConfId.intValue()==0)
            {
            	logger.error("队列为空,未获取到配置id");
                return;
            }
            logger.error("获取到配置id:"+adspaceConfId);
            Integer adSpaceStatus = adSpaceConfDAO.findAdSpaceStatusByAdSpaceConfId(adspaceConfId);
            if (adSpaceStatus==1)
            {
                AdSpaceConfPv pv = adSpaceConfDAO.findByAdSpaceConfId(adspaceConfId);
                if (pv!=null && pv.getStatus()==1)
                {
                	//还未到生效时间点，继续放入队列
                    if (pv.getStartTime().after(new Date()))
                    {
                        try
                        {
                            AdSpaceConfQueue.add(adspaceConfId);
                            if (adspaceConfIdFlag==0) {
                            	adspaceConfIdFlag=adspaceConfId;
							}else {
								if (adspaceConfIdFlag.intValue()==adspaceConfId.intValue()) {
									//已经轮询完一遍，任务停止
									logger.error("轮询结束");
									return;
								}
							}
                        }
                        catch (InterruptedException e)
                        {
                        	logger.error(e.toString());
                        }
                    }else {
                        //存储
                        String key = adSpaceDAO.queryKeyById(pv.getAdSpaceId());
                        String globalKey = adSpaceDAO.queryGlobalKeyById(pv.getAdSpaceId());
                        logger.error("key:"+key);
                        Jedis jedis = redis.jedis();
                        List<AdvertisingPv> advertisings = advertisingDAO.findAdvertisingsByAdSpaceConfId(adspaceConfId);
                        Map<String, Object> map = new TreeMap<String, Object>();
                        map.put("key", key);
                        map.put("startTime", pv.getStartTime());
                        map.put("endTime", pv.getEndTime());
                        map.put("data", advertisings);
                        jedis.set(key, JsonUtil.toJson(map));
                        jedis.expire(key, (int)((pv.getEndTime().getTime()-new Date().getTime())/1000));
                        String ads = jedis.get(key);
                        logger.error("key:"+key+" value:"+ads);
//                        jedis.lrem(globalKey, 0, key);
//                        jedis.lpush(globalKey, key);
                        jedis.sadd(globalKey, key);
                    }
                    
                }
            }else if (adSpaceStatus==0) {
                AdSpaceConfPv pv = adSpaceConfDAO.findByAdSpaceConfId(adspaceConfId);
                String key = adSpaceDAO.queryKeyById(pv.getAdSpaceId());
                Jedis jedis = redis.jedis();
                jedis.del(key);
            }
		}
        
    }
    
    @PostConstruct
    public void initBlockingQueue(){

    	//初始化:找出所有当前生效的广告配置，加载到队列中
    	logger.error("系统启动，初始化开始......");
        List<Integer> adspaceConfIdList = adSpaceConfDAO.findAllCurrentUsingAdSpaceConfId();
        if (adspaceConfIdList!=null && adspaceConfIdList.size()>0) {
        	for (Integer adSpaceConfId : adspaceConfIdList)
            {
                try
                {
                    AdSpaceConfQueue.add(adSpaceConfId);
                    logger.error("adSpaceConfId:"+adSpaceConfId+" 加入队列");
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        	logger.error("当前生效的广告配置已经加载完毕");
		}else {
			logger.error("当前无生效的广告配置");
		}
        
        logger.error("开始加载今天的预配置广告.....");
        List<Integer> adspaceConfIds = adSpaceConfDAO.findAllTodayAdSpaceConfs();
        if (adspaceConfIds==null || adspaceConfIds.size()==0)
        {
        	logger.error("无数据加载");
        }else {
        	for (Integer adSpaceConfId: adspaceConfIds)
            {
                try
                {
                	
                    AdSpaceConfQueue.add(adSpaceConfId);
                    logger.error("adSpaceConfId："+adSpaceConfId+"加载入队列");
                }
                catch (InterruptedException e)
                {
                    logger.error(e.toString());
                }
            }
            
            logger.error("预配置加载结束");
		}
        flag=true;
    }
    
    
}
