/*
 * 文 件 名:  AdSpaceDAOProvider.java
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-8-29
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cyou.advertising.web.dao.provider;

import java.util.Map;

import com.cyou.advertising.web.common.Pagination;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014-8-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AdSpaceDAOProvider {

	
	public String selectByClientIdVersionAdSpaceId(Map map){

		Integer appClientId = (Integer) map.get("appClientId");
		String version = (String) map.get("version");
		Integer adspaceId = (Integer) map.get("adspaceId");
		StringBuilder sb = new StringBuilder();  
        sb.append("select id,ad_space_number as adSpaceNumber,description,app_client_id as appClientId,app_client_version as appClientVersion,support_adList as supportAdList,status from ad_space where app_client_id="+appClientId+" ");  
       
        if (version!=null && !version.trim().equals("") && !version.equals("0")) {
			sb.append(" and app_client_version="+version+" ");
		}
       if (adspaceId!=null && adspaceId.intValue()!=0) {
			sb.append(" and id="+adspaceId+" ");
		}
        return sb.toString(); 
	}
	
	public String selectByClientIdVersionAdSpaceIdPage(Map map){

		Integer appClientId = (Integer) map.get("appClientId");
		String version = (String) map.get("version");
		Integer adspaceId = (Integer) map.get("adspaceId");
		
		Pagination page = (Pagination) map.get("page");
		
		StringBuilder sb = new StringBuilder();  
        sb.append("select id,ad_space_number as adSpaceNumber,description,app_client_id as appClientId,app_client_version as appClientVersion,support_adList as supportAdList,status from ad_space where app_client_id="+appClientId+" ");  
       
        if (version!=null && !version.trim().equals("") && !version.equals("0")) {
			sb.append(" and app_client_version="+version+" ");
		}
        if (adspaceId!=null && adspaceId.intValue()!=0) {
			sb.append(" and id="+adspaceId+" ");
		}
        sb.append(" LIMIT "+page.getStartIndex()+","+page.getPageSize()+" ");
        
        return sb.toString(); 
	}
	
	public String countByappClientIdVersionAdSpaceNum(Map map){

		Integer appClientId = (Integer) map.get("appClientId");
		String version = (String) map.get("version");
		Integer adspaceId = (Integer) map.get("adspaceId");
		StringBuilder sb = new StringBuilder();  
        sb.append("select count(1) from ad_space where app_client_id="+appClientId+" ");  
       
        if (version!=null && !version.trim().equals("") && !version.equals("0")) {
			sb.append(" and app_client_version="+version+" ");
		}
       if (adspaceId!=null && adspaceId.intValue()!=0) {
			sb.append(" and id="+adspaceId+" ");
		}
        return sb.toString(); 
	}
}
