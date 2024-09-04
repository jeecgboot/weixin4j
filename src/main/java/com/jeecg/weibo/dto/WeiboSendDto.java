package com.jeecg.weibo.dto;

import com.alipay.api.internal.util.StringUtils;

import java.net.URLEncoder;

/**
 * 发布微博   
 * @author Administrator
 *
 */
public class WeiboSendDto {
	//采用OAuth授权方式为必填参数，OAuth授权后获得
	String access_token;
	//要发布的微博文本内容，必须做URLencode，内容不超过140个汉字。
	String status;
	//图片的URL地址，必须以http开头。
	String url;
	//微博id
	String id;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getStatus() {
		String encode = "";
		if(!StringUtils.isEmpty(status)){
			encode = URLEncoder.encode(status);
		}
		return encode;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WeiboSendDto [access_token=");
		builder.append(access_token);
		builder.append(", status=");
		builder.append(status);
		builder.append(", url=");
		builder.append(url);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
	
}
