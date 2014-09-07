package com.cyou.advertising.web.model.ad;

import java.io.Serializable;

/**
 * 广告平台版本
 * @author guowei
 *
 */
public class AdClientVersion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1462262182176576569L;
	protected Integer id;
	/** adClientId */
	protected Integer adClientId;

	/** version */
	protected String version;

	/** status */
	protected Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdClientId() {
		return adClientId;
	}

	public void setAdClientId(Integer adClientId) {
		this.adClientId = adClientId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
