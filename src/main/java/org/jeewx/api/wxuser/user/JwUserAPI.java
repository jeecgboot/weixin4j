package org.jeewx.api.wxuser.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.WeiXinReqService;
import org.jeewx.api.core.req.model.user.UserInfoListGet;
import org.jeewx.api.wxuser.user.model.Wxuser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信--用户
 * 
 * @author lizr
 * 
 */
public class JwUserAPI {
	private static Logger logger = LoggerFactory.getLogger(JwUserAPI.class);
	//获取用户基本信息（包括UnionID机制）
    private static String GET_USER_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    
	/**
	 * 根据user_openid 获取关注用户的基本信息
	 * 
	 * @param shelf_id
	 * @return
	 * @throws WexinReqException
	 */
	public static Wxuser  getWxuser(String accesstoken,String user_openid) throws WexinReqException {
		if (accesstoken != null) {
			 String requestUrl = GET_USER_URL.replace("ACCESS_TOKEN", accesstoken).replace("OPENID", user_openid);
			 JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", null);
			logger.info(result.toString());
			// 正常返回
			Wxuser wxuser = null;
			Object error = result.get("errcode");
			wxuser = (Wxuser) JSONObject.toJavaObject(result, Wxuser.class);
			return wxuser;
		}
		return null;
	}

	/**
	 * 获取所有关注用户信息信息
	 * 
	 * @return
	 * @throws WexinReqException 
	 */
	public static List<Wxuser> getAllWxuser(String accesstoken,String next_openid) throws WexinReqException {
		if (accesstoken != null) {
			UserInfoListGet userInfoListGet = new UserInfoListGet();
			userInfoListGet.setAccess_token(accesstoken);
			userInfoListGet.setNext_openid(next_openid);
			JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(userInfoListGet);
			Object error = result.get("errcode");
			List<Wxuser> lstUser = null;
			Wxuser mxuser = null;
			int total = result.getInteger("total");
			int count = result.getInteger("count");
			String strNextOpenId = result.getString("next_openid");
			JSONObject data = result.getJSONObject("data");
			lstUser = new ArrayList<Wxuser>(total);
			if (count > 0) {
				JSONArray lstOpenid = data.getJSONArray("openid");
				int iSize = lstOpenid.size();
				for (int i = 0; i < iSize; i++) {
					String openId = lstOpenid.getString(i);
					mxuser = getWxuser(accesstoken, openId);
					lstUser.add(mxuser);
				}
				if (strNextOpenId != null) {
					lstUser.addAll(getAllWxuser(accesstoken, strNextOpenId));
				}
			}
			return lstUser;
		}
		return null;
	}
	
}
