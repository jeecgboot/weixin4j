package com.jeecg.weibo.util;

import com.alipay.api.internal.util.StringUtils;
import com.jeecg.weibo.exception.BusinessException;

public class WeiboUsersUtil {
	/*
	 * 
	 * 获取@当前用户的最新微博的请求必填参数验证
	 * 
	 */
	public static void getShowParmValidate(String access_token,String uid,String screen_name){
		if(StringUtils.isEmpty(access_token)){
			throw new BusinessException("access_token不能为空");
		}
		if(StringUtils.isEmpty(uid)&&StringUtils.isEmpty(screen_name)){
			throw new BusinessException("uid与screen_name二者不能全为空");
		}
		if(!StringUtils.isEmpty(uid)&&!StringUtils.isEmpty(screen_name)){
			throw new BusinessException("uid与screen_name二者只能选其一");
		}
	}
	/*
	 * 
	 * 获取@当前用户的最新微博的请求路径
	 */
	public static String getShowUrl (String interUrl,String access_token,String uid,String screen_name){
		StringBuilder requestUrl=new StringBuilder();
		requestUrl.append(interUrl);
		if(!StringUtils.isEmpty(access_token)){
			requestUrl.append("&access_token="+access_token);
		}
		if(!StringUtils.isEmpty(uid)){
			requestUrl.append("&uid="+uid);
		}
		if(!StringUtils.isEmpty(screen_name)){
			requestUrl.append("&screen_name="+screen_name);
		}
		return requestUrl.toString();
	}
	/*
	 * 
	 * 批量获取用户的粉丝数、关注数、微博数的请求必填参数验证
	 * 
	 */
	public static void getCountsParmValidate(String access_token,String uids){
		if(StringUtils.isEmpty(access_token)){
			throw new BusinessException("access_token不能为空");
		}
		if(StringUtils.isEmpty(uids)){
			throw new BusinessException("需要获取数据的用户uids不能为空");
		}else{
			String [] uidArr=uids.split(",");
			if(uidArr.length>100){
				throw new BusinessException("需要获取数据的用户个数不能超过100");
			}
		}
	}
	/*
	 * 
	 * 批量获取用户的粉丝数、关注数、微博数的请求路径
	 */
	public static String getCountsUrl (String interUrl,String access_token,String uids){
		StringBuilder requestUrl=new StringBuilder();
		requestUrl.append(interUrl);
		if(!StringUtils.isEmpty(access_token)){
			requestUrl.append("&access_token="+access_token);
		}
		if(!StringUtils.isEmpty(uids)){
			requestUrl.append("&uids="+uids);
		}
		return requestUrl.toString();
	}
}
