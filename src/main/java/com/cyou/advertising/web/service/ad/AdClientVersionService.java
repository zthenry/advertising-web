package com.cyou.advertising.web.service.ad;

import java.util.List;

import com.cyou.advertising.web.model.ad.AdClientVersion;

public interface AdClientVersionService {

	void insertAdClientVersion(AdClientVersion adClientVersion);
	
	List<AdClientVersion> findByAdClientId(Integer adClientId);

	void updateAdClientVersion(AdClientVersion adClientVersion);

}
