package com.cyou.advertising.web.model.security;


public class Operations {

	/** id */
	protected Integer id;

	/** name */
	protected String name;

	/** url */
	protected String url;

	/** moduleId */
	protected Long moduleId;

	/** assignable */
	protected Integer assignable;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public Integer getAssignable() {
		return assignable;
	}
	public void setAssignable(Integer assignable) {
		this.assignable = assignable;
	}
}
