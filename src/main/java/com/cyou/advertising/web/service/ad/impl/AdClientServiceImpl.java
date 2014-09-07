package com.cyou.advertising.web.service.ad.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cyou.advertising.web.dao.ad.AdClientDAO;
import com.cyou.advertising.web.dao.ad.AdClientVersionDAO;
import com.cyou.advertising.web.model.ad.AdClient;
import com.cyou.advertising.web.model.ad.AdClientVersion;
import com.cyou.advertising.web.service.ad.AdClientService;
@Service("adClientService")
public class AdClientServiceImpl implements AdClientService {
	
	@Resource
	private AdClientDAO adClientDAO;
	
	@Resource
	private AdClientVersionDAO adClientVersionDAO;

	@Override
	public List<AdClient> list() {
		return adClientDAO.list();
	}

	@Override
	public void insert(AdClient adClient) {
		adClientDAO.insert(adClient);
	}

	@Override
	public void update(AdClient adClient) {
		adClientDAO.update(adClient);
	}

	@Override
	public void updateStatus(Integer id, Integer status) {
		adClientDAO.updateStatus(id, status);
	}

	@Override
	public AdClient findById(Integer id) {
		return adClientDAO.findById(id);
	}
	
	@Override
	public void insertAdClientVersion(AdClientVersion adClientVersion) {
		adClientVersionDAO.insert(adClientVersion);
	}

	@Override
	public List<AdClient> queryAll() {
		List<AdClient> adClients = adClientDAO.findAll();
		return adClients;
	}

	@Override
	public List<AdClientVersion> queryVersionByAppClientId(Integer appClientId) {
		List<AdClientVersion> adClientVersions = adClientVersionDAO.findByAdClientId(appClientId);
		return adClientVersions;
	}

	@Override
	public AdClient queryByAppClientId(Integer appClientId) {
		return adClientDAO.findById(appClientId);
	}

}
