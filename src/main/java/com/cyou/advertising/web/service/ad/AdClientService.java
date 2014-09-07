package com.cyou.advertising.web.service.ad;

import java.util.List;

import com.cyou.advertising.web.model.ad.AdClient;
import com.cyou.advertising.web.model.ad.AdClientVersion;

public interface AdClientService {
	
	List<AdClient> list();
	List<AdClient> queryAll();
	
	void insert(AdClient adClient);
	
	void update(AdClient adClient);
	
	void updateStatus(Integer id, Integer status);
	
	AdClient findById(Integer id);
	
	void insertAdClientVersion(AdClientVersion adClientVersion);
	
	List<AdClientVersion> queryVersionByAppClientId(Integer appClientId);
	
	AdClient queryByAppClientId(Integer appClientId);
}
