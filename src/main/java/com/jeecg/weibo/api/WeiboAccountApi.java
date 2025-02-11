package com.jeecg.weibo.api;

import com.alipay.api.internal.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jeecg.weibo.exception.BusinessException;
import com.jeecg.weibo.util.HttpUtil;

public class WeiboAccountApi {
	private static final Logger logger = LoggerFactory.getLogger(WeiboAccountApi.class);
	//1、获取授权用户的UID的url
	private static final String get_uid_url="https://api.weibo.com/2/account/get_uid.json?1=1";
	/**
	 * @param access_token  OAuth授权必填参数 
	 * 
	 * 获取授权用户的UID 
	 */
	public static JSONObject getUid(String access_token){
		JSONObject j=null;
		try {
			logger.info("获取授权用户的UID的参数为:access_token:"+access_token);
			//验证请求参数
			if(StringUtils.isEmpty(access_token)){
				throw new BusinessException("access_token不能为空");
			}
			StringBuilder uidUrl=new StringBuilder();
			uidUrl.append(get_uid_url);
			uidUrl.append("&access_token="+access_token);
			String requestUrl = uidUrl.toString();
			logger.info("获取授权用户的UID的路径为:"+requestUrl);
			j = HttpUtil.httpRequest(requestUrl, "GET", null);
			if(j!=null){
				logger.info("获取授权用户的UID的结果为:"+j.toString());
			}else{
				logger.info("获取授权用户的UID的结果为:null");
			}
		}catch(BusinessException e)	{
			logger.info(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}
	public static void main(String[] args) {
		//==================根据用户ID获取用户信息接口测试========start==================================
		/*String access_token="2.00rj8pTCRV_yBB5addd99887yjfcyC";
		//String uid="";
		getUid(access_token);*/
		 //===================根据用户ID获取用户信息接口测试========end===================================
	}
}
