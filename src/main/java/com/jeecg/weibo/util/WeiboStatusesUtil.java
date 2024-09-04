package com.jeecg.weibo.util;

import com.alipay.api.internal.util.StringUtils;

import com.jeecg.weibo.dto.WeiBoMentionsDto;
import com.jeecg.weibo.dto.WeiboUserTimelineDto;
import com.jeecg.weibo.exception.BusinessException;

public class WeiboStatusesUtil {
	/*
	 * 
	 * 验证获取用户发布的微博的请求必填参数验证
	 */
	public static void getUserTimelineParmValidate (WeiboUserTimelineDto userTimeline){
		if(StringUtils.isEmpty(userTimeline.getAccess_token())){
			throw new BusinessException("access_token 不能 为空");
		}
	}
	/*
	 * 
	 * 拼接获取用户发布的微博的请求路径
	 */
	public static String getUserTimelineUrl (String interUrl,WeiboUserTimelineDto userTimeline){
		StringBuilder requestUrl=new StringBuilder();
		requestUrl.append(interUrl);
		if(!StringUtils.isEmpty(userTimeline.getAccess_token())){
			requestUrl.append("&access_token="+userTimeline.getAccess_token());
		}
		if(!StringUtils.isEmpty(userTimeline.getUid())){
			requestUrl.append("&uid="+userTimeline.getUid());
		}
		if(!StringUtils.isEmpty(userTimeline.getScreen_name())){
			requestUrl.append("&screen_name="+userTimeline.getScreen_name());
		}
		if(!StringUtils.isEmpty(userTimeline.getSince_id())){
			requestUrl.append("&since_id="+userTimeline.getSince_id());
		}
		if(!StringUtils.isEmpty(userTimeline.getMax_id())){
			requestUrl.append("&max_id="+userTimeline.getMax_id());
		}
		if(!StringUtils.isEmpty(userTimeline.getCount())){
			requestUrl.append("&count="+userTimeline.getCount());
		}
		if(!StringUtils.isEmpty(userTimeline.getPage())){
			requestUrl.append("&page="+userTimeline.getPage());
		}
		if(!StringUtils.isEmpty(userTimeline.getBase_app())){
			requestUrl.append("&base_app="+userTimeline.getBase_app());
		}
		if(!StringUtils.isEmpty(userTimeline.getFeature())){
			requestUrl.append("&feature="+userTimeline.getFeature());
		}
		if(!StringUtils.isEmpty(userTimeline.getTrim_user())){
			requestUrl.append("trim_user="+userTimeline.getTrim_user());
		}
		return requestUrl.toString();
	}
	/*
	 * 
	 * 验证获取用户发布的微博的请求必填参数验证
	 */
	public static void getUserTimelineIdsParmValidate (WeiboUserTimelineDto userTimeline){
		if(StringUtils.isEmpty(userTimeline.getAccess_token())){
			throw new BusinessException("access_token不能为空");
		}
		if(StringUtils.isEmpty(userTimeline.getUid())&&(StringUtils.isEmpty(userTimeline.getScreen_name()))){
			throw new BusinessException("uid与screen_name二者不能全为空");
		}
		if(!StringUtils.isEmpty(userTimeline.getUid())&&(!StringUtils.isEmpty(userTimeline.getScreen_name()))){
			throw new BusinessException("uid与screen_name二者只能选其一");
		}
	}
	/*
	 * 
	 * 验证批量获取指定微博的转发数评论数的请求必填参数验证
	 */
	public static void getCountParmValidate(String access_token,String ids){
		if(StringUtils.isEmpty(access_token)){
			throw new BusinessException("access_token不能为空");
		}
		if(StringUtils.isEmpty(ids)){
			throw new BusinessException("微博ID不能为空");
		}else{
			String [] idArry=ids.split(",");
			if(idArry.length>100){
				throw new BusinessException("微博ID个数不能超过100");
			}
		}
		
		
	}
	/*
	 * 
	 * 拼接获取用户发布的微博的请求路径
	 */
	public static String getCountUrl (String interUrl,String access_token,String ids){
		StringBuilder requestUrl=new StringBuilder();
		requestUrl.append(interUrl);
		if(!StringUtils.isEmpty(access_token)){
			requestUrl.append("&access_token="+access_token);
		}
		if(!StringUtils.isEmpty(ids)){
			requestUrl.append("&ids="+ids);
		}
		return requestUrl.toString();
	}
	/*
	 * 
	 * 根据ID获取单条微博信息的请求必填参数验证
	 */
	public static void getShowParmValidate(String access_token,String id){
		if(StringUtils.isEmpty(access_token)){
			throw new BusinessException("access_token不能为空");
		}
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("微博ID不能为空");
		}else{
			String [] idArry=id.split(",");
			if(idArry.length>1){
				throw new BusinessException("微博ID个数只能为1");
			}
		}
	}
	/*
	 * 
	 * 根据ID获取单条微博信息的请求路径
	 */
	public static String getShowUrl (String interUrl,String access_token,String id){
		StringBuilder requestUrl=new StringBuilder();
		requestUrl.append(interUrl);
		if(!StringUtils.isEmpty(access_token)){
			requestUrl.append("&access_token="+access_token);
		}
		if(!StringUtils.isEmpty(id)){
			requestUrl.append("&id="+id);
		}
		return requestUrl.toString();
	}
	/*
	 * 
	 * 获取@当前用户的最新微博的请求必填参数验证
	 * 
	 */
	public static void getMentionsParmValidate(WeiBoMentionsDto mentions){
		if(StringUtils.isEmpty(mentions.getAccess_token())){
			throw new BusinessException("access_token不能为空");
		}
	}
	/*
	 * 
	 * 获取@当前用户的最新微博的请求路径
	 */
	public static String getMentionsUrl (String interUrl,WeiBoMentionsDto mentions){
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
		if(!StringUtils.isEmpty(mentions.getFilter_by_type())){
			requestUrl.append("&filter_by_type="+mentions.getFilter_by_type());
		}
		return requestUrl.toString();
	}
}
