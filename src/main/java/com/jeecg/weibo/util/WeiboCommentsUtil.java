package com.jeecg.weibo.util;

import com.alipay.api.internal.util.StringUtils;
import com.jeecg.weibo.dto.WeiBoMentionsDto;
import com.jeecg.weibo.exception.BusinessException;

public class WeiboCommentsUtil {
	/*
	 * 
	 * 获取@当前用户的最新微博的请求必填参数验证
	 * 
	 */
	public static void getBymeParmValidate(WeiBoMentionsDto mentions){
		if(StringUtils.isEmpty(mentions.getAccess_token())){
			throw new BusinessException("access_token不能为空");
		}
	}
	/*
	 * 
	 * 获取@当前用户的最新微博的请求路径
	 */
	public static String getBymeUrl (String interUrl,WeiBoMentionsDto mentions){
		StringBuilder requestUrl=new StringBuilder();
		requestUrl.append(interUrl);
		if(!StringUtils.isEmpty(mentions.getAccess_token())){
			requestUrl.append("&access_token="+mentions.getAccess_token());
		}
		if(!StringUtils.isEmpty(mentions.getSince_id())){
			requestUrl.append("&since_id="+mentions.getSince_id());
		}
		if(!StringUtils.isEmpty(mentions.getMax_id())){
			requestUrl.append("&max_id="+mentions.getMax_id());
		}
		if(!StringUtils.isEmpty(mentions.getCount())){
			requestUrl.append("&count="+mentions.getCount());
		}
		if(!StringUtils.isEmpty(mentions.getPage())){
			requestUrl.append("&page="+mentions.getPage());
		}
		if(!StringUtils.isEmpty(mentions.getFilter_by_source())){
			requestUrl.append("&filter_by_source="+mentions.getFilter_by_source());
		}
		return requestUrl.toString();
	}
	/*
	 * 
	 * 获取@当前用户的最新微博的请求路径
	 */
	public static String getTomeUrl (String interUrl,WeiBoMentionsDto mentions){
		StringBuilder requestUrl=new StringBuilder();
		requestUrl.append(interUrl);
		if(!StringUtils.isEmpty(mentions.getAccess_token())){
			requestUrl.append("&access_token="+mentions.getAccess_token());
		}
		if(!StringUtils.isEmpty(mentions.getSince_id())){
			requestUrl.append("&since_id="+mentions.getSince_id());
		}
		if(!StringUtils.isEmpty(mentions.getMax_id())){
			requestUrl.append("&max_id="+mentions.getMax_id());
		}
		if(!StringUtils.isEmpty(mentions.getCount())){
			requestUrl.append("&count="+mentions.getCount());
		}
		if(!StringUtils.isEmpty(mentions.getPage())){
			requestUrl.append("&page="+mentions.getPage());
		}
		if(!StringUtils.isEmpty(mentions.getFilter_by_author())){
			requestUrl.append("&filter_by_author="+mentions.getFilter_by_author());
		}
		if(!StringUtils.isEmpty(mentions.getFilter_by_source())){
			requestUrl.append("&filter_by_source="+mentions.getFilter_by_source());
		}
		return requestUrl.toString();
	}
	
}
