package org.jeewx.api.wxbase.wxserviceip;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.WeiXinReqService;
import org.jeewx.api.core.req.model.ServiceIP;
import org.jeewx.api.core.util.WeiXinConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信--token信息
 * 
 * @author lizr
 * 
 */
public class JwServiceIpAPI {

	/**
	 * 返回的信息名称
	 */
	public static String RETURN_INFO_NAME = "ip_list";
	
	/**
	 * 获取服务的ip列表信息
	 * @param accessToke
	 * @return
	 * @throws WexinReqException
	 */
	public static List<String> getServiceIpList(String accessToke) throws WexinReqException{
		ServiceIP param = new ServiceIP();
		param.setAccess_token(accessToke);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(param);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		List<String> lstServiceIp = null;
		JSONArray infoArray = result.getJSONArray(RETURN_INFO_NAME);
		lstServiceIp = new ArrayList<String>(infoArray.size());
		for (int i = 0; i < infoArray.size(); i++) {
			lstServiceIp.add(infoArray.getString(i));
		}
		return lstServiceIp;
	}
	 
	
	public static void main(String[] args){
		 
		try {
			List<String> s = JwServiceIpAPI.getServiceIpList("kY9Y9rfdcr8AEtYZ9gPaRUjIAuJBvXO5ZOnbv2PYFxox__uSUQcqOnaGYN1xc4N1rI7NDCaPm_0ysFYjRVnPwCJHE7v7uF_l1hI6qi6QBsA");
			System.out.println(s);
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
