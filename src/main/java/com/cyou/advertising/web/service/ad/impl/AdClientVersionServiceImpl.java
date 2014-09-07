package com.cyou.advertising.web.service.ad.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cyou.advertising.web.dao.ad.AdClientVersionDAO;
import com.cyou.advertising.web.model.ad.AdClientVersion;
import com.cyou.advertising.web.service.ad.AdClientVersionService;
@Service("adClientVersionService")
public class AdClientVersionServiceImpl implements AdClientVersionService{

	@Resource
	private AdClientVersionDAO adClientVersionDAO;
	
	@Override
	public void insertAdClientVersion(AdClientVersion adClientVersion) {
		adClientVersionDAO.insert(adClientVersion);
	}

	@Override
	public List<AdClientVersion> findByAdClientId(Integer adClientId) {
		return adClientVersionDAO.findByAdClientId(adClientId);
	}

	@Override
	public void updateAdClientVersion(AdClientVersion adClientVersion) {
		adClientVersionDAO.update(adClientVersion);
	}

}
