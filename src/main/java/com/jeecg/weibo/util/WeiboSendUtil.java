package com.jeecg.weibo.util;

import java.net.URLEncoder;

import com.alipay.api.internal.util.StringUtils;

import com.jeecg.weibo.dto.WeiboSendDto;
import com.jeecg.weibo.exception.BusinessException;

public class WeiboSendUtil {
	/*
	 * 
	 * 获取@当前用户的最新微博的请求必填参数验证
	 * 
	 */
	public static void getSendParmValidate(WeiboSendDto send){
		if(StringUtils.isEmpty(send.getAccess_token())){
			throw new BusinessException("access_token不能为空");
		}
		if(StringUtils.isEmpty(send.getStatus())){
			throw new BusinessException("发布微博内容不能为空");
		}
	}
	/*
	 * 
	 * 获取@当前用户的最新微博的请求路径
	 */
	@SuppressWarnings("deprecation")
	public static String getSendUrl (String interUrl,WeiboSendDto send){
		StringBuilder requestUrl=new StringBuilder();
		requestUrl.append(interUrl);
		if(!StringUtils.isEmpty(send.getAccess_token())){
			requestUrl.append("&access_token="+send.getAccess_token());
		}
		if(!StringUtils.isEmpty(send.getStatus())){
			requestUrl.append("&status="+send.getStatus());
		}
		if(!StringUtils.isEmpty(send.getUrl())){
			String url = URLEncoder.encode(send.getUrl());
			requestUrl.append("&url="+url);
		}
		return requestUrl.toString();
	}
	
	public static void delParmValidate(WeiboSendDto send){
		if(StringUtils.isEmpty(send.getAccess_token())){
			throw new BusinessException("access_token不能为空");
		}
		if(StringUtils.isEmpty(send.getId())){
			throw new BusinessException("微博ID不能为空");
		}
	}
	
	@SuppressWarnings("deprecation")
	public static String getDelUrl (String interUrl,WeiboSendDto send){
		StringBuilder requestUrl=new StringBuilder();
		requestUrl.append(interUrl);
		if(!StringUtils.isEmpty(send.getAccess_token())){
			requestUrl.append("&access_token="+send.getAccess_token());
		}
		if(!StringUtils.isEmpty(send.getId())){
			requestUrl.append("&id="+send.getId());
		}
		return requestUrl.toString();
	}
}
