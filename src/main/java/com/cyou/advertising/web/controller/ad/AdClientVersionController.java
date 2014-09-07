package com.cyou.advertising.web.controller.ad;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cyou.advertising.web.model.ad.AdClient;
import com.cyou.advertising.web.model.ad.AdClientVersion;
import com.cyou.advertising.web.service.ad.AdClientService;
import com.cyou.advertising.web.service.ad.AdClientVersionService;
@Controller
@RequestMapping("/adClientVersion")
public class AdClientVersionController {
	
	@Resource
	private AdClientService adClientService;
	
	@Resource
	private AdClientVersionService adClientVersionService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String adClientVersionlist(HttpServletRequest request, Integer adClientId) throws Exception {
		List<AdClientVersion> adClientVersionList = adClientVersionService.findByAdClientId(adClientId);
		request.setAttribute("adClientVersionList", adClientVersionList);
		AdClient adClient = adClientService.findById(adClientId);
		request.setAttribute("adClient", adClient);
		return "ad/adClientVersionList";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editVersion(HttpServletRequest request, AdClientVersion adClientVersion, Integer adClientId) throws Exception {
		int flag = 1;
		try {
			if (adClientVersion.getId() == null) {
				adClientVersionService.insertAdClientVersion(adClientVersion);
			}else{
				adClientVersionService.updateAdClientVersion(adClientVersion);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
		}
		return "redirect:/adClientVersion/list?adClientId=" + adClientId + "flag=" + flag;
	}
	
	@RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
	public String updateStatus(HttpServletRequest request, Integer id, String version, Integer status, Integer adClientId) throws Exception {
		int flag = 1;
		try {
			AdClientVersion adClientVersion = new AdClientVersion();
			adClientVersion.setId(id);
			adClientVersion.setStatus(status);
			adClientVersion.setVersion(version);
			adClientVersionService.updateAdClientVersion(adClientVersion);
		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
		}
		return "redirect:/adClientVersion/list?adClientId=" + adClientId + "flag=" + flag;
	}
	
}
