package com.cyou.advertising.web.controller.ad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.cyou.advertising.web.common.Constants;
import com.cyou.advertising.web.common.Pagination;
import com.cyou.advertising.web.model.ad.Advertising;
import com.cyou.advertising.web.model.ad.AdvertisingPv;
import com.cyou.advertising.web.service.ad.AdvertisingService;
import com.cyou.advertising.web.utils.Page;

/**
 * 
 * @author  zhangtao
 * @version  [版本号, 2014-8-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/ad")
public class AdvertisingController {

	@Autowired
	private AdvertisingService advertisingService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listAdPage(HttpServletRequest request, Integer curPage) {
		try {
			curPage = curPage == null ? 1 : curPage;
			List<Advertising> adList = advertisingService.list(curPage, 1);
			//for (Advertising advertising : adList) {
				//advertising.setPicUrl(PrivateUtil.getPicUrl(pictureService.getPicture(advertising.getPicId())));
			//}
			Integer total = advertisingService.getTotalCount();
			Page page = new Page(curPage, total, 1);
			request.setAttribute("page", page);
			request.setAttribute("adList", adList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ad/adList";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request, Advertising advertising, Integer curPage) {
		int flag = 1;
		try {
			if(advertising.getId()==null){
				advertisingService.insert(advertising);
			}else{
				advertisingService.update(advertising);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
		}
		return "redirect:/ad/list?flag=" + flag + "&curPage=" + curPage;
	}
	
	@RequestMapping(value = "/toEdit", method = RequestMethod.GET)
	public String toEdit(HttpServletRequest request, Integer id) {
		if(id != null){
			Advertising ad = advertisingService.findById(id);
			request.setAttribute("ad", ad);
		}
		return "ad/adEdit";
	}
	
	@RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
	public String updateStatus(HttpServletRequest request, HttpServletResponse response, Integer id, Integer status, Integer curPage) throws IOException {
		int flag = 1;
		try {
			Advertising ad = advertisingService.findById(id);
			ad.setStatus(status);
			advertisingService.update(ad);
		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
		}
		return "redirect:/ad/list?flag=" + flag + "&curPage=" + curPage;
	}
	
	/**
	 * 供选择的广告列表
	 * 在配置广告位的广告时，弹出该页面，进行广告的选择
	 * @param request
	 * @param name    广告名称 搜索框内容 支持模糊查询
	 * @param adIds   已选择的广告 ，在页面展示的时候需要将已选择的广告勾选
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/adListForConf",method=RequestMethod.GET)
	public ModelAndView getAdListForAdSpace(HttpServletRequest request,@RequestParam(required=false,defaultValue="1") Integer curPage,@RequestParam("name") String name,@RequestParam(required=false,defaultValue="") String adIds){
		ModelAndView mv = new ModelAndView("/adspaceConf/adListForConf");
		List<String> adIdList = new ArrayList<String>();
		if (curPage == null || curPage == 0) {
			curPage =  1;
		}
		Integer totalCount = advertisingService.countAdvertisingsByName(name);
		Pagination page = new Pagination(curPage, totalCount, 5);
		mv.addObject("page", page);
		mv.addObject("curPage", curPage);
		try {
			List<AdvertisingPv> result = advertisingService.getAdvertisingsByNameByPage(name,page);
			
			mv.addObject("adList", result);
			if (adIds.equals("")) {
				
			}else {
				String[] adIdArray = adIds.split(",");
				for (String adId : adIdArray) {
					if(!adId.trim().equals("")){
						//将已选择的广告id传入到该广告选择页，进行勾选标记
						adIdList.add(adId);
					}
				}
				for (AdvertisingPv advertisingPv : result) {
					for (String adId : adIdArray) {
						if(!adId.trim().equals("") && Integer.parseInt(adId)==advertisingPv.getId().intValue()){
							advertisingPv.setChecked(1);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		mv.addObject("name", name);
		mv.addObject("adIdList", adIdList);
		mv.addObject("adIds", adIds);
		return mv;
	}

}
