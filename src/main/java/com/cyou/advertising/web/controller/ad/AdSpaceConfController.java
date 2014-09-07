/*
 * 文 件 名:  AdConfController.java
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-8-22
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cyou.advertising.web.controller.ad;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cyou.advertising.web.common.Constants;
import com.cyou.advertising.web.common.Pagination;
import com.cyou.advertising.web.model.ad.AdClient;
import com.cyou.advertising.web.model.ad.AdClientVersion;
import com.cyou.advertising.web.model.ad.AdSpace;
import com.cyou.advertising.web.model.ad.AdSpaceConfPv;
import com.cyou.advertising.web.model.ad.AdSpacePv;
import com.cyou.advertising.web.model.ad.Advertising;
import com.cyou.advertising.web.model.ad.AdvertisingPv;
import com.cyou.advertising.web.model.security.OperationLog;
import com.cyou.advertising.web.service.ad.AdClientService;
import com.cyou.advertising.web.service.ad.AdspaceConfService;
import com.cyou.advertising.web.service.ad.AdspaceService;
import com.cyou.advertising.web.service.log.LogService;
/**
 * 广告位及广告位配置管理Controller
 * <功能详细描述>
 * 
 * @author  zhangtao
 * @version  [版本号, 2014-8-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/adspace")
public class AdSpaceConfController{
	
	private final Logger logger = LoggerFactory.getLogger(AdSpaceConfController.class);
	@Autowired
	private AdClientService adClientService;
	
	@Autowired
	private AdspaceService adspaceService;
	
	@Autowired
	private AdspaceConfService adspaceConfService;
	
	@Autowired
	private LogService logService;
	/**
	 * 首次进入广告位页面
	 * <功能详细描述>
	 * @param request
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/listIndex",method=RequestMethod.GET)
	public ModelAndView adSpaceListView(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("adspaceConf/adSpaceList");
		//TODO 根据用户id获取用户角色，然后获取对应的平台
		List<AdClient> adClientList = adClientService.queryAll();
		mv.addObject("adClientList", adClientList);
		return mv;
	}
	
	/**
	 * 广告位管理检索
	 * <功能详细描述>
	 * @param request
	 * @param appClientId
	 * @param appClientVersion
	 * @param adSpaceId 广告位id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/adspaceList",method=RequestMethod.POST)
	public ModelAndView getAdSpaceList(HttpServletRequest request,@RequestParam("appClientId") Integer appClientId,@RequestParam("appClientVersion") String appClientVersion,@RequestParam("adSpaceId") Integer adSpaceId,Integer curPage){
		ModelAndView mv = new ModelAndView("adspaceConf/adSpaceList");
		
		//查询结果
		if (appClientId!=null && appClientId.intValue()!=0) {
			if (curPage == null || curPage == 0) {
				curPage =  1;
			}
			Integer totalCount = adspaceService.count(appClientId, appClientVersion, adSpaceId);
			Pagination page = new Pagination(curPage, totalCount, 5);
		    List<AdSpacePv> adSpaces = adspaceService.queryByPage(appClientId, appClientVersion,adSpaceId,page);
		    for (AdSpacePv adSpacePv : adSpaces) {
		    	AdClient adClient = adClientService.queryByAppClientId(adSpacePv.getAppClientId().intValue());
		    	adSpacePv.setAppClientName(adClient.getName());
			}
		    mv.addObject("page", page);
		    mv.addObject("curPage", curPage);
		    mv.addObject("adSpaceList", adSpaces);
		}
		//TODO 根据用户id获取用户角色，然后获取对应的平台
		List<AdClient> adClientList = adClientService.queryAll();
		mv.addObject("adClientList", adClientList);
		mv.addObject("appClientId", appClientId);
		List<AdClientVersion>  adClientVersions = adClientService.queryVersionByAppClientId(appClientId);
		mv.addObject("versionList", adClientVersions);
		mv.addObject("appClientVersion", appClientVersion);
		
		if (appClientVersion!=null && !appClientVersion.trim().equals("") && !appClientVersion.equals("0")) {
			mv.addObject("appClientVersion", appClientVersion);
			List<AdSpacePv> adSpaceList = adspaceService.query(appClientId, appClientVersion, null);
			mv.addObject("selectAdSpaceList", adSpaceList);
			if (adSpaceId!=null && adSpaceId.intValue()!=0) {
				mv.addObject("adSpaceId", adSpaceId);
			}
			
		}
		
		return mv;
	}
	
	@RequestMapping(value="/adspaceConfList",method=RequestMethod.GET)
	public ModelAndView getAdSpaceConfListByAdspaceId(HttpServletRequest request,@RequestParam("adspaceId") Long adspaceId){
		ModelAndView mv = new ModelAndView("adspaceConf/adSpaceConfList");
		return mv;
	}
	
	@RequestMapping(value="/toAddAdSpace",method=RequestMethod.GET)
	public ModelAndView toAddAdSpace(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/adspaceConf/addAdSpace");
		List<AdClient> adClientList = adClientService.queryAll();
		mv.addObject("adClientList", adClientList);
		mv.addObject("adSpaceId", 0);
		return mv;
	}
	
	/**
	 * to 修改广告位 页面
	 * <功能详细描述>
	 * @param request
	 * @param adSpaceId 广告位id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/toEditAdSpace",method=RequestMethod.GET)
	public ModelAndView toEditAdSpace(HttpServletRequest request,@RequestParam("adSpaceId") Integer adSpaceId){
		ModelAndView mv = new ModelAndView("/adspaceConf/addAdSpace");
		AdSpacePv adSpacePv = adspaceService.queryAdSpaceById(adSpaceId);
		mv.addObject("adSpaceId", adSpaceId);
		mv.addObject("adSpace", adSpacePv);
		return mv;
	}
	
	/**
	 * 保存广告位
	 * <功能详细描述>
	 * @param request
	 * @param adSpace
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/addAdspace",method=RequestMethod.POST)
	public ModelAndView addAdspace(HttpServletRequest request,AdSpace adSpace){
		ModelAndView mv = new ModelAndView("/adspaceConf/addAdSpace");
		mv.addObject("success", true);
		
		try {
			adspaceService.add(adSpace);
			mv.addObject("msg", "保存成功");
		} catch (Exception e) {
			logger.error(e.toString());
			mv.addObject("success", false);
			mv.addObject("msg", "保存失败,系统异常");
		}
		List<AdClient> adClientList = adClientService.queryAll();
		mv.addObject("adClientList", adClientList);
		return mv;
	}
	
	/**
	 * 根据平台id获取版本号
	 * <功能详细描述>
	 * @param request
	 * @param appClientId
	 * @param model
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/getVersion/{appClientId}",method=RequestMethod.GET)
	public ModelMap getVersionListByAdClient(HttpServletRequest request, @PathVariable("appClientId") Integer appClientId, ModelMap model){
		List<AdClientVersion>  adClientVersions = adClientService.queryVersionByAppClientId(appClientId);
		model.addAttribute("versionList", adClientVersions);
		return model;
	}
	
	/**
	 * 根据平台id和版本号获取广告位编号
	 * <功能详细描述>
	 * @param request
	 * @param appClientId
	 * @param version
	 * @param model
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/getAdspaceNum/{appClientId}",method=RequestMethod.GET)
	public ModelMap getAdspaceNumByAdClientAndVersion(HttpServletRequest request, @PathVariable("appClientId") Integer appClientId, @RequestParam("version") String version,ModelMap model){
		try {
			List<AdSpacePv> adSpaceList = adspaceService.query(appClientId, version, null);
			model.addAttribute("adSpaceList", adSpaceList);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		
		
		return model;
	}
	
	/**
	 * 进入广告位的  ---配置信息--- 列表页
	 * 检索
	 * <功能详细描述>
	 * @param request
	 * @param id 广告位id：自增
	 * @param appClientId
	 * @param version
	 * @param adspaceNum 广告位编号：用户自己输入
	 * @param confType 配置状态:1:当前生效，2:过期，3:预配置--启用,4:预配置--禁用
	 * @param model
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/conf",method=RequestMethod.GET)
	public ModelAndView toAdspaceConfListView(HttpServletRequest request, @RequestParam("id") Integer id,@RequestParam("appClientId") Integer appClientId, @RequestParam("version") String version,@RequestParam("adspaceNum") String adspaceNum, @RequestParam(required=false,defaultValue="0") Integer confType,Integer curPage, ModelMap model){
		ModelAndView mv = new ModelAndView("/adspaceConf/adSpaceConfList");
	    //广告位配置信息
		Integer status = adspaceService.queryStatus(id);
		int totalCount = adspaceConfService.countAdSpaceConfsByAdSpaceId(id,confType);
		if (curPage == null || curPage == 0) {
			curPage =  1;
		}
		Pagination page = new Pagination(curPage, totalCount, 5);
		mv.addObject("page", page);
	    mv.addObject("curPage", curPage);
		List<AdSpaceConfPv> adSpaceConfs = adspaceConfService.queryAdSpaceConfsByAdSpaceIdByPage(id,confType,page);
		for (AdSpaceConfPv adSpaceConfPv : adSpaceConfs) {
		    
		    //区别广告当前的状态:0过期，1当前生效，20预配置当天  21预配置以后
		    int currentStatus = adspaceConfService.getAdSpaceConfCurrentStatus(adSpaceConfPv.getId());
		    adSpaceConfPv.setCurrentStatus(currentStatus);
		}
		
	    
		AdClient adClient = adClientService.queryByAppClientId(appClientId);
		mv.addObject("id", id);
		mv.addObject("appClientId", appClientId);
		mv.addObject("appClientName", adClient.getName());
		mv.addObject("version", version);
		mv.addObject("adspaceNum", adspaceNum);
		mv.addObject("confType", confType);
		mv.addObject("adSpaceConfs", adSpaceConfs);
		mv.addObject("status", status);
		return mv;
	}
	
	/**
	 * to 新增广告位配置信息
	 * <功能详细描述>
	 * @param request
	 * @param adspaceId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/toAddAdSpaceConf",method=RequestMethod.GET)
	public ModelAndView toAddAdSpaceConf(HttpServletRequest request,@RequestParam("adspaceId") Integer adspaceId){
		ModelAndView mv = new ModelAndView("/adspaceConf/addAdSpaceConf");
		AdSpacePv adSpacePv = adspaceService.queryAdSpaceById(adspaceId);
		mv.addObject("supportAdlist", adSpacePv.getSupportAdlist());
		List<AdClient> adClientList = adClientService.queryAll();
		mv.addObject("adClientList", adClientList);
		mv.addObject("adspaceId", adspaceId);
		mv.addObject("adspaceConfId", 0);
		mv.addObject("currentExpire", -1);
		
		return mv;
	}
	
	/**
	 * to 修改广告位配置信息
	 * <功能详细描述>
	 * @param request
	 * @param adspaceConfId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/toEditAdSpaceConf",method=RequestMethod.GET)
	public ModelAndView toEditAdSpaceConf(HttpServletRequest request,@RequestParam("adspaceConfId") Integer adspaceConfId){
		ModelAndView mv = new ModelAndView("/adspaceConf/addAdSpaceConf");
		mv.addObject("adspaceConfId", adspaceConfId);
		
		//获取该配置信息
		AdSpaceConfPv adSpaceConf = adspaceConfService.queryAdSpaceConfById(adspaceConfId);
		Date start = adSpaceConf.getStartTime();
		Date end = adSpaceConf.getEndTime();
		Integer status = adSpaceConf.getStatus();
		if (status.intValue()==1 && start.before(new Date()) && end.after(new Date())) {
			mv.addObject("currentExpire", 1);
		}else {
			mv.addObject("currentExpire", -1);
		}
		List<AdvertisingPv> advertisings = adspaceConfService.queryAdvertisingsByAdSapceConfId(adspaceConfId);
		mv.addObject("adSpaceConf", adSpaceConf);
		mv.addObject("advertisings", advertisings);
		String adIds="";
		if (advertisings!=null && advertisings.size()!=0) {
			for (Advertising advertising : advertisings) {
			    if (adIds.equals(""))
                {
			        adIds = advertising.getId()+"";
                }
			    else {
			        adIds = adIds+","+advertising.getId();
                }
				
			}
			mv.addObject("adIds", adIds);
		}
		
		AdSpacePv adSpacePv = adspaceService.queryAdSpaceById(adSpaceConf.getAdSpaceId().intValue());
		mv.addObject("supportAdlist", adSpacePv.getSupportAdlist());
		mv.addObject("adspaceId", adSpaceConf.getAdSpaceId().intValue());
		return mv;
	}
	
	/**
	 * 新增/修改 配置
	 * <功能详细描述>
	 * @param request
	 * @param adSpaceConf
	 * @param adIds
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/addAdSpaceConf",method=RequestMethod.POST)
	public ModelMap addAdSpaceConf(HttpServletRequest request,AdSpaceConfPv adSpaceConfPv,ModelMap model){
		adSpaceConfPv.setCreateTime(new Date());
		model.addAttribute("success", true);
		if (adSpaceConfPv.getAdIds()==null || adSpaceConfPv.getAdIds().trim().equals("")) {
			//无广告信息，提示异常
			model.addAttribute("success", false);
			model.addAttribute("msg", "无关联广告,无法保存");
			return model;
		}
		//进行一系列的数据校验
		String msg = adspaceConfService.check(adSpaceConfPv);
		if (msg!=null) {
			model.addAttribute("success", false);
			model.addAttribute("msg", msg);
			return model;
		}
		Integer adSpaceConfId = adSpaceConfPv.getId();
		if (adSpaceConfId.intValue()==0) {
			//新增
			String[] adIdArray = adSpaceConfPv.getAdIds().split(",");
			try {
				if (adIdArray.length == 1) {
					//一条广告
					//Long adId = Long.parseLong(adIdArray[0]);
					adspaceConfService.save(adSpaceConfPv, adIdArray[0]);
				} else {
					//多条广告
					List<String> adIdList = new ArrayList<String>();
					for (String adIdStr : adIdArray) {
						adIdList.add(adIdStr);
					}
					adspaceConfService.save(adSpaceConfPv, adIdList);
				}
				OperationLog operationLog = new OperationLog();
				operationLog.setOpTime(new Date());
				operationLog.setAdSpaceConfId(new Long(adSpaceConfPv.getId()));
				operationLog.setOpType(Constants.OP_TYPE_ADD);
				operationLog.setOpDetail("新增广告位配置");
				logService.addLog(operationLog);
				
				model.addAttribute("msg", "创建成功");
			} catch (Exception e) {
				logger.error(e.toString());
				model.addAttribute("success", false);
				model.addAttribute("msg", "系统异常:" + e);
			}
				
		}else if (adSpaceConfId.intValue()>0) {
			//修改
			List<String> adIds = new ArrayList<String>();
			String[] adIdArray = adSpaceConfPv.getAdIds().split(",");
			if (adIdArray.length == 1) {
				adIds.add(adIdArray[0]);
			}else {
				for (String adIdStr : adIdArray) {
					adIds.add(adIdStr);
				}
			}
			
			adspaceConfService.updateAdSpaceConf(adSpaceConfPv, adIds);
			model.addAttribute("msg", "更新成功");
			
			OperationLog operationLog = new OperationLog();
			operationLog.setOpTime(new Date());
			operationLog.setAdSpaceConfId(new Long(adSpaceConfPv.getId()));
			operationLog.setOpType(Constants.OP_TYPE_UPDATE);
			operationLog.setOpDetail("修改广告位配置");
			logService.addLog(operationLog);
		}
		
		
		return model;
	}
	
	/**
	 * 进入广告配置信息详情页
	 * 过期的配置不能出现修改按钮
	 * @param request
	 * @param adSpaceConfId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/adSpaceConf/adList/{adSpaceConfId}/{appClientName}/{version}/{adspaceNum}",method=RequestMethod.GET)
	public ModelAndView adListView(HttpServletRequest request,@PathVariable("adSpaceConfId") Integer adSpaceConfId,@PathVariable("appClientName") String appClientName,@PathVariable("version") String version,@PathVariable("adspaceNum") String adspaceNum){
		ModelAndView mv = new ModelAndView("/adspaceConf/adSpaceConfDetail");
		List<AdvertisingPv> advertisings = adspaceConfService.queryAdvertisingsByAdSapceConfId(adSpaceConfId);
		AdSpaceConfPv adSpaceConf = adspaceConfService.queryAdSpaceConfById(adSpaceConfId);
		Date endTime = adSpaceConf.getEndTime();
		if (endTime.before(new Date())) {
			mv.addObject("isExpire", 0);
		}else {
			mv.addObject("isExpire", 1);
		}
		mv.addObject("advertisingList", advertisings);
		mv.addObject("adSpaceConf", adSpaceConf);
		mv.addObject("appClientName", appClientName);
		mv.addObject("version", version);
		mv.addObject("adspaceNum", adspaceNum);
		
		return mv;
	}
	
	/**
	 * 修改广告位的启用禁用
	 * <功能详细描述>
	 * @param request
	 * @param adSpaceId
	 * @param target
	 * @param model
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/changAdSpaceStatus",method=RequestMethod.GET)
	public ModelMap changAdSpaceStatus(HttpServletRequest request,@RequestParam("adSpaceId") Integer adSpaceId,@RequestParam("target") Integer target,ModelMap model){
		if (target==1) {
			adspaceService.updateStatus(adSpaceId,1);
		}else if (target==0) {
			adspaceService.updateStatus(adSpaceId,0);
		}else {
			model.addAttribute("msg", "操作失败");
			return model;
		}
		model.addAttribute("msg", "操作成功");
		return model;
	}
	
	/**
	 * 更新广告位配置状态 启用禁用
	 * <功能详细描述>
	 * @param request
	 * @param adSpaceConfId
	 * @param target
	 * @param model
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/changAdSpaceConfStatus",method=RequestMethod.GET)
	public ModelMap changAdSpaceConfStatus(HttpServletRequest request,@RequestParam("adSpaceConfId") Integer adSpaceConfId,@RequestParam("target") Integer target,ModelMap model){
		//进行状态判断
	    int curentStatus = adspaceConfService.getAdSpaceConfCurrentStatus(adSpaceConfId);
        if (Constants.CONF_CUURENT_STATUS_FUTURE_AFTER_21!=curentStatus && Constants.CONF_CUURENT_STATUS_FUTURE_CURRENT_20!=curentStatus)
        {
            model.addAttribute("msg", "操作失败，预配置的配置信息才能进行该操作！");
            return model;
        }
	    if (target==1) {
		    //变为启用
			adspaceConfService.updateStatus(adSpaceConfId,1);
		}else if (target==0) {
		    //变为禁用
			adspaceConfService.updateStatus(adSpaceConfId,0);
		}else {
			model.addAttribute("msg", "操作失败:"+target);
			return model;
		}
		model.addAttribute("msg", "操作成功");
		return model;
	}
	
	/**
	 * 配置列表页刷新广告位配置缓存
	 * <功能详细描述>
	 * @param request
	 * @param adSpaceConfId
	 * @param model
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/refreshRedis",method=RequestMethod.GET)
	public ModelMap refreshRedis(HttpServletRequest request,@RequestParam("adSpaceConfId") Integer adSpaceConfId,ModelMap model){
		adspaceConfService.updateRedis(adSpaceConfId);
		model.addAttribute("msg", "操作成功");
		return model;
	}
	
	@RequestMapping(value="/getDataFromRedis",method=RequestMethod.GET)
	public ModelMap getDataFromRedis(HttpServletRequest request,@RequestParam("adSpaceConfId") Integer adSpaceConfId,ModelMap model){
		String result = adspaceConfService.updateRedis(adSpaceConfId);
		model.addAttribute("result", result);
		return model;
	}
	
	
	
	/**
	 * form表单提交 Date类型数据绑定
	 * <功能详细描述>
	 * @param binder
	 * @see [类、类#方法、类#成员]
	 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    dateFormat.setLenient(false);  
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
	}
	
	@RequestMapping(value="/checkBeforeSaveAdspace",method=RequestMethod.GET)
	public ModelMap checkBeforeAddAdspace(HttpServletRequest request,@RequestParam("id") Long id,@RequestParam("version") String version,@RequestParam("num") String num,ModelMap model){
		model.addAttribute("success", true);
		AdSpace adSpace= new AdSpace();
		adSpace.setAppClientId(id);
		adSpace.setAppClientVersion(version);
		adSpace.setAdSpaceNumber(num);
		String result = adspaceService.checkBeforeAddAdspace(adSpace);
		if (result!=null && !result.equals("")) {
			model.addAttribute("success", false);
			
		}
		return model;
	}
}
