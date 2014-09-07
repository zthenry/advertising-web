package com.cyou.advertising.web.controller.ad;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cyou.advertising.web.model.ad.AdClient;
import com.cyou.advertising.web.model.ad.AdClientVersion;
import com.cyou.advertising.web.service.ad.AdClientService;
import com.cyou.advertising.web.service.ad.AdClientVersionService;

@Controller
@RequestMapping("/adClient")
public class AdClientController {

	@Resource
	private AdClientService adClientService;
	
	@Resource
	private AdClientVersionService adClientVersionService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request) throws Exception {
		List<AdClient> adClientList = adClientService.list();
		for (AdClient adClient : adClientList) {
			List<AdClientVersion> adClientVersions = adClientVersionService.findByAdClientId(adClient.getId());
			String adClientVersionStr = "";
			if(!adClientVersions.isEmpty()){
				for (AdClientVersion adClientVersion : adClientVersions) {
					adClientVersionStr += adClientVersion.getVersion() + " ";
				}
			}
			adClient.setAdClientVersionStr(adClientVersionStr);
		}
		request.setAttribute("adClientList", adClientList);
		return "ad/adClientList";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request, AdClient adClient) throws Exception {
		int flag = 1;
		try {
			if (adClient.getId() == null) {
				adClientService.insert(adClient);
			}else{
				adClientService.update(adClient);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
		}
		return "redirect:/adClient/list?flag=" + flag;
	}
	
	@RequestMapping(value = "/updateStatus")
	public String updateStatus(HttpServletRequest request,
			HttpServletResponse response, Integer id, Integer status)
					throws Exception {
		Integer flag = 1;
		try {
			adClientService.updateStatus(id, status);
		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
		}
		return "redirect:/adClient/list?flag=" + flag;
	}
	
	
	
	
}
