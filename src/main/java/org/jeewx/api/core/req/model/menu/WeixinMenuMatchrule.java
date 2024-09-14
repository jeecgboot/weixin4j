package org.jeewx.api.core.req.model.menu;

import java.util.List;

public class WeixinMenuMatchrule {

	private String group_id;
	@Deprecated
	private String sex;
	@Deprecated
	private String country;
	@Deprecated
	private String province;
	@Deprecated
	private String city;
	@Deprecated
	private String client_platform_type;
	@Deprecated
	private String language;

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getClient_platform_type() {
		return client_platform_type;
	}

	public void setClient_platform_type(String client_platform_type) {
		this.client_platform_type = client_platform_type;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	
	
}
