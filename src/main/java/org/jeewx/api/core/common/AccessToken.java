package org.jeewx.api.core.common;

import com.alibaba.fastjson.JSONObject;

public class AccessToken {

	private String appid;
	private String appscret;
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppscret() {
		return appscret;
	}

	public void setAppscret(String appscret) {
		this.appscret = appscret;
	}

	public AccessToken(String appid, String appscret) {
		this.setAppid(appid);
		this.setAppscret(appscret);
	}

	public String getNewAccessToken() {
		String token = null;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET".replace("APPID", this.getAppid()).replace("APPSECRET", this.getAppscret());
		JSONObject jsonObject = WxstoreUtils.httpRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				token = jsonObject.getString("access_token");
			} catch (Exception e) {
				token = null;
			}
		}
		return token;
	}

}
