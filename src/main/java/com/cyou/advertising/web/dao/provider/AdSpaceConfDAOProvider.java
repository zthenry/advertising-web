/*
 * 文 件 名:  AdSpaceConfDAOProvider.java
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-8-28
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cyou.advertising.web.dao.provider;

import java.util.Map;

import com.cyou.advertising.web.common.Pagination;
import com.cyou.advertising.web.dao.ad.AdSpaceConfDAO;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014-8-28]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AdSpaceConfDAOProvider {

	public static final int SQL_CONF_TYPE_ALL_0=0;
	public static final int SQL_CONF_TYPE_ACTIVE_USE_1=1;
	public static final int SQL_CONF_TYPE_ACTIVE_NOT_USE_5=5;
	public static final int CONF_TYPE_EXPIRE_2=2;
	public static final int CONF_TYPE_FUTURE_USE_3=3;
	public static final int CONF_TYPE_FUTURE_NOT_USE_4=4;
	
	public String selectByAdSpaceIdAndConfStatus(Map map){
		//@Select("SELECT "+VIEW+" FROM "+TABLENAME+"  "+ALIGNNAME+" WHERE "+ALIGNNAME+".ad_space_id=#{adSpaceId}")
		//confType  配置状态:0:全部 1:当前生效启用，2:过期，3:预配置--启用,4:预配置--禁用,5:当前生效禁用
		Integer confType = (Integer) map.get("confType");
		Integer adSpaceId = (Integer) map.get("adSpaceId");
		
		Pagination page = (Pagination) map.get("page");
		
		StringBuilder sb = new StringBuilder();  
        sb.append("SELECT "+AdSpaceConfDAO.VIEW+" FROM "+AdSpaceConfDAO.TABLENAME+"  "+AdSpaceConfDAO.ALIGNNAME+" WHERE "+AdSpaceConfDAO.ALIGNNAME+".ad_space_id="+adSpaceId+" ");  
        
        /*
         * 首先从时间上判断：
         * 过期:结束时间在比当前时间小 或者 status=2
         * 当前生效:当前时间位于开始时间和结束时间之间，并且status=1
         * 预配置:开始时间大于当前时间
         *      1.启用：status=1
         *      2.禁用: status=0
         */
        if (confType.intValue()==1) {
        	//当前生效
			sb.append(" and "+AdSpaceConfDAO.ALIGNNAME+".status=1 and "+AdSpaceConfDAO.ALIGNNAME+".start_time<=now() and "+AdSpaceConfDAO.ALIGNNAME+".end_time>=now()");
		}else if (confType.intValue()==2) {
			//过期:结束时间在比当前时间小 或者 status=2
			sb.append(" and "+AdSpaceConfDAO.ALIGNNAME+".end_time<now())");
		}else if (confType.intValue()==3) {
			//预配置:开始时间大于当前时间     启用：status=1
			sb.append(" and "+AdSpaceConfDAO.ALIGNNAME+".status=1 and "+AdSpaceConfDAO.ALIGNNAME+".start_time>now()");
			
		}else if (confType.intValue()==4) {
			//预配置:开始时间大于当前时间     禁用: status=0
			sb.append(" and "+AdSpaceConfDAO.ALIGNNAME+".status=0 and "+AdSpaceConfDAO.ALIGNNAME+".start_time>now()");
		}else if (confType.intValue()==5) {
		    sb.append(" and "+AdSpaceConfDAO.ALIGNNAME+".status=0 and "+AdSpaceConfDAO.ALIGNNAME+".start_time<=now() and "+AdSpaceConfDAO.ALIGNNAME+".end_time>=now() ");
        }
        
       
        sb.append(" order by "+AdSpaceConfDAO.ALIGNNAME+".start_time desc");
        if (page!=null) {
        	sb.append(" LIMIT "+page.getStartIndex()+","+page.getPageSize()+" ");
		}
        return sb.toString(); 
	}
	
	public String countByAdSpaceIdAndConfStatus(Map map){
		//@Select("SELECT "+VIEW+" FROM "+TABLENAME+"  "+ALIGNNAME+" WHERE "+ALIGNNAME+".ad_space_id=#{adSpaceId}")
		//confType  配置状态:0:全部 1:当前生效启用，2:过期，3:预配置--启用,4:预配置--禁用,5:当前生效禁用
		Integer confType = (Integer) map.get("confType");
		Integer adSpaceId = (Integer) map.get("adSpaceId");
		StringBuilder sb = new StringBuilder();  
        sb.append("SELECT count(1) FROM "+AdSpaceConfDAO.TABLENAME+"  "+AdSpaceConfDAO.ALIGNNAME+" WHERE "+AdSpaceConfDAO.ALIGNNAME+".ad_space_id="+adSpaceId+" ");  
        
        /*
         * 首先从时间上判断：
         * 过期:结束时间在比当前时间小 或者 status=2
         * 当前生效:当前时间位于开始时间和结束时间之间，并且status=1
         * 预配置:开始时间大于当前时间
         *      1.启用：status=1
         *      2.禁用: status=0
         */
        if (confType.intValue()==1) {
        	//当前生效
			sb.append(" and "+AdSpaceConfDAO.ALIGNNAME+".status=1 and "+AdSpaceConfDAO.ALIGNNAME+".start_time<=now() and "+AdSpaceConfDAO.ALIGNNAME+".end_time>=now()");
		}else if (confType.intValue()==2) {
			//过期:结束时间在比当前时间小 或者 status=2
			sb.append(" and "+AdSpaceConfDAO.ALIGNNAME+".end_time<now())");
		}else if (confType.intValue()==3) {
			//预配置:开始时间大于当前时间     启用：status=1
			sb.append(" and "+AdSpaceConfDAO.ALIGNNAME+".status=1 and "+AdSpaceConfDAO.ALIGNNAME+".start_time>now()");
			
		}else if (confType.intValue()==4) {
			//预配置:开始时间大于当前时间     禁用: status=0
			sb.append(" and "+AdSpaceConfDAO.ALIGNNAME+".status=0 and "+AdSpaceConfDAO.ALIGNNAME+".start_time>now()");
		}else if (confType.intValue()==5) {
		    sb.append(" and "+AdSpaceConfDAO.ALIGNNAME+".status=0 and "+AdSpaceConfDAO.ALIGNNAME+".start_time<=now() and "+AdSpaceConfDAO.ALIGNNAME+".end_time>=now() ");
        }
        sb.append(" order by "+AdSpaceConfDAO.ALIGNNAME+".create_time desc");
        return sb.toString(); 
	}
	
}
