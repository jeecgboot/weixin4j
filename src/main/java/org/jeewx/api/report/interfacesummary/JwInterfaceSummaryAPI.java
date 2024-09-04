package org.jeewx.api.report.interfacesummary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.common.AccessToken;
import org.jeewx.api.core.common.JSONHelper;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.core.req.model.interfacesummary.InterfaceSummaryParam;
import org.jeewx.api.report.interfacesummary.model.InterfaceSummary;
import org.jeewx.api.report.interfacesummary.model.InterfaceSummaryHour;

import java.util.List;


/**
 * 接口分析数据接口
 * @author qimiao
 *
 */
public class JwInterfaceSummaryAPI {
	//获取接口分析数据 
	private static String getinterfacesummary_url = "https://api.weixin.qq.com/datacube/getinterfacesummary?access_token=ACCESS_TOKEN";
	//获取接口分析分时数据
	private static String getinterfacesummaryhour_url = "https://api.weixin.qq.com/datacube/getinterfacesummaryhour?access_token=ACCESS_TOKEN";
		
	/**
	 * 获取接口分析分时数据
	 * @param begin_date end_date
	 * @return
	 */
	public static List<InterfaceSummaryHour> getInterfaceSummaryHour(String accesstoken,InterfaceSummaryParam param) {
		if (accesstoken != null) {
			param.setAccess_token(accesstoken);
			String requestUrl = getinterfacesummaryhour_url.replace("ACCESS_TOKEN", accesstoken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(param));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "GET", obj.toString());
			// 正常返回
			List<InterfaceSummaryHour> list = null;
			JSONArray info = result.getJSONArray("list");
			list = JSONHelper.toList(info, InterfaceSummaryHour.class);
			return list;
		}
		return null;
	}
	
	/**
	 * 获取接口分析数据
	 * @param begin_date end_date
	 * @return
	 */
	public static List<InterfaceSummary> getInterfaceSummary(String accesstoken,InterfaceSummaryParam param) {
		if (accesstoken != null) {
			param.setAccess_token(accesstoken);
			String requestUrl = getinterfacesummary_url.replace("ACCESS_TOKEN", accesstoken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(param));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "GET", obj.toString());
			// 正常返回
			List<InterfaceSummary> list = null;
			JSONArray info = result.getJSONArray("list");
			list = JSONHelper.toList(info, InterfaceSummary.class);
			return list;
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		//getNewAccessToken("wx00737224cb9dbc7d","b9479ebdb58d1c6b6efd4171ebe718b5");
		
		JwInterfaceSummaryAPI s = new JwInterfaceSummaryAPI();
		InterfaceSummaryParam param=new InterfaceSummaryParam();
		param.setBegin_date("2015-01-01");
		param.setEnd_date("2015-01-01");
		
		//List<InterfaceSummary> list = s.getInterfaceSummary(param);
		List<InterfaceSummaryHour> list = s.getInterfaceSummaryHour("a8UEhkxmxN269afPJ7SuY9vVroHPYiwm9Yv5gu_yK4GGsA60IafJ3N9We4ubabgZ4lsl4kd0zqcH7JC70UsQ951Ndur4ejSIz7VvroBJn4g",param);
		
		for (InterfaceSummaryHour bean : list) {
			System.out.println(bean.getRef_date()); 
			System.out.println(bean.getRef_hour()); 
			System.out.println(bean.getTotal_time_cost());
		}
		
	}
	
	private static void getNewAccessToken(String appid,String appscret){
		AccessToken atoken = new AccessToken(appid, appscret);
		String newAccessToken = atoken.getNewAccessToken();
		System.out.println(newAccessToken);
	}
}
 