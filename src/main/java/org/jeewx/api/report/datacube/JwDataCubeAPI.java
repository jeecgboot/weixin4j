package org.jeewx.api.report.datacube;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.common.JSONHelper;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.model.dataCube.*;
import org.jeewx.api.report.datacube.model.*;

import java.util.List;

/**
 * 分析数据接口service
 * @author luweichao
 *
 * 2015年1月27日
 */
public class JwDataCubeAPI {
	
	//获取消息发送概况数据 
	private static String GETUPSTREAMMSG_URL = "https://api.weixin.qq.com/datacube/getupstreammsg?access_token=ACCESS_TOKEN";
	//获取消息分送分时数据
	private static String GETUPSTREAMMSGHOUR_URL = "https://api.weixin.qq.com/datacube/getupstreammsghour?access_token=ACCESS_TOKEN";
	//获取消息发送周数据
	private static String GETUPSTREAMMSGWEEK_URL = "https://api.weixin.qq.com/datacube/getupstreammsgweek?access_token=ACCESS_TOKEN";
	//获取消息发送月数据
	private static String GETUPSTREAMMSGMONTH_URL = "https://api.weixin.qq.com/datacube/getupstreammsgmonth?access_token=ACCESS_TOKEN";
	//获取消息发送分布数据
	private static String GETUPSTREAMMSGDIST_URL = "https://api.weixin.qq.com/datacube/getupstreammsgdist?access_token=ACCESS_TOKEN";
	//获取消息发送分布周数据
	private static String GETUPSTREAMMSGDISTWEEK_URL = "https://api.weixin.qq.com/datacube/getupstreammsgdistweek?access_token=ACCESS_TOKEN";
	//获取消息发送分布月数据
	private static String GETUPSTREAMMSGDISTMONTH_URL = "https://api.weixin.qq.com/datacube/getupstreammsgdistmonth?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取消息发送概况数据
	 * @param bDate 起始时间
	 * @param eDate 结束时间
	 * @return
	 * @throws WexinReqException
	 */
	public static List<WxDataCubeStreamMsgInfo> getWxDataCubeStreamMsgInfo(String accesstoken,String bDate,String eDate) throws WexinReqException {
		if (accesstoken != null) {
			
			// 封装请求参数
			WxDataCubeStreamMsgParam msgParam = new WxDataCubeStreamMsgParam();
			msgParam.setAccess_token(accesstoken);
			msgParam.setBegin_date(bDate);
			msgParam.setEnd_date(eDate);
			// 调用接口
			String requestUrl = GETUPSTREAMMSG_URL.replace("ACCESS_TOKEN", accesstoken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(msgParam));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			Object error = result.get("errcode");
			
			// 无错误消息时 返回数据对象
			JSONArray arrayResult = result.getJSONArray("list");
				// 正常返回
			List<WxDataCubeStreamMsgInfo> msgInfoList = null;
			msgInfoList=JSONHelper.toList(arrayResult, WxDataCubeStreamMsgInfo.class);
			return msgInfoList;
		}
		return null;
	}
	

	/**
	 * 获取消息分送分时数据
	 * @param bDate 起始时间
	 * @param eDate 结束时间
	 * @return
	 * @throws WexinReqException
	 */
	public static List<WxDataCubeStreamMsgHourInfo> getWxDataCubeStreamMsgHourInfo(String accesstoken,String bDate,String eDate) throws WexinReqException {
		if (accesstoken != null) {
			
			// 封装请求参数
			WxDataCubeStreamMsgHourParam msgParam = new WxDataCubeStreamMsgHourParam();
			msgParam.setAccess_token(accesstoken);
			msgParam.setBegin_date(bDate);
			msgParam.setEnd_date(eDate);
			
			// 调用接口
			String requestUrl = GETUPSTREAMMSGHOUR_URL.replace("ACCESS_TOKEN", accesstoken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(msgParam));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			Object error = result.get("errcode");

			// 无错误消息时 返回数据对象
			JSONArray arrayResult = result.getJSONArray("list");
			// 正常返回
			List<WxDataCubeStreamMsgHourInfo> msgInfoList = null;
			msgInfoList=JSONHelper.toList(arrayResult, WxDataCubeStreamMsgHourInfo.class);
			return msgInfoList;
		}
		return null;
	}
	
	/**
	 * 获取消息发送周数据
	 * @param bDate 起始时间
	 * @param eDate 结束时间
	 * @return
	 * @throws WexinReqException
	 */
	public static List<WxDataCubeStreamMsgWeekInfo> getWxDataCubeStreamMsgWeekInfo(String accesstoken,String bDate,String eDate) throws WexinReqException {
		if (accesstoken != null) {
			
			// 封装请求参数
			WxDataCubeStreamMsgWeekParam msgParam = new WxDataCubeStreamMsgWeekParam();
			msgParam.setAccess_token(accesstoken);
			msgParam.setBegin_date(bDate);
			msgParam.setEnd_date(eDate);
			
			// 调用接口
			String requestUrl = GETUPSTREAMMSGWEEK_URL.replace("ACCESS_TOKEN", accesstoken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(msgParam));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			Object error = result.get("errcode");

			// 无错误消息时 返回数据对象
			JSONArray arrayResult = result.getJSONArray("list");
			// 正常返回
			List<WxDataCubeStreamMsgWeekInfo> msgInfoList = null;
			msgInfoList=JSONHelper.toList(arrayResult, WxDataCubeStreamMsgWeekInfo.class);
			return msgInfoList;
		}
		return null;
	}
	
	/**
	 * 获取消息发送月数据
	 * @param bDate 起始时间
	 * @param eDate 结束时间
	 * @return
	 * @throws WexinReqException
	 */
	public static List<WxDataCubeStreamMsgMonthInfo> getWxDataCubeStreamMsgMonthInfo(String accesstoken,String bDate,String eDate) throws WexinReqException {
		if (accesstoken != null) {
			
			// 封装请求参数
			WxDataCubeStreamMsgMonthParam msgParam = new WxDataCubeStreamMsgMonthParam();
			msgParam.setAccess_token(accesstoken);
			msgParam.setBegin_date(bDate);
			msgParam.setEnd_date(eDate);
			
			// 调用接口
			String requestUrl = GETUPSTREAMMSGMONTH_URL.replace("ACCESS_TOKEN", accesstoken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(msgParam));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			Object error = result.get("errcode");

			// 无错误消息时 返回数据对象
			JSONArray arrayResult = result.getJSONArray("list");
			// 正常返回
			List<WxDataCubeStreamMsgMonthInfo> msgInfoList = null;
			msgInfoList=JSONHelper.toList(arrayResult, WxDataCubeStreamMsgMonthInfo.class);
			return msgInfoList;
		}
		return null;
	}
	
	/**
	 * 获取消息发送分布数据
	 * @param bDate 起始时间
	 * @param eDate 结束时间
	 * @return
	 * @throws WexinReqException
	 */
	public static List<WxDataCubeStreamMsgDistInfo> getWxDataCubeStreamMsgDistInfo(String accesstoken,String bDate,String eDate) throws WexinReqException {
		if (accesstoken != null) {

			// 封装请求参数
			WxDataCubeStreamMsgDistParam msgParam = new WxDataCubeStreamMsgDistParam();
			msgParam.setAccess_token(accesstoken);
			msgParam.setBegin_date(bDate);
			msgParam.setEnd_date(eDate);

			// 调用接口
			String requestUrl = GETUPSTREAMMSGDIST_URL.replace("ACCESS_TOKEN",
					accesstoken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(msgParam));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST",
					obj.toString());

			// 无错误消息时 返回数据对象
			JSONArray arrayResult = result.getJSONArray("list");
			// 正常返回
			List<WxDataCubeStreamMsgDistInfo> msgInfoList = null;
			msgInfoList = JSONHelper.toList(arrayResult,
					WxDataCubeStreamMsgDistInfo.class);
			return msgInfoList;
		}
		return null;
	}
	
	/**
	 * 获取消息发送分布周数据
	 * @param bDate 起始时间
	 * @param eDate 结束时间
	 * @return
	 * @throws WexinReqException
	 */
	public static List<WxDataCubeStreamMsgDistWeekInfo> getWxDataCubeStreamMsgDistWeekInfo(String accesstoken,String bDate,String eDate) throws WexinReqException {
		if (accesstoken != null) {
			
			// 封装请求参数
			WxDataCubeStreamMsgDistWeekParam msgParam = new WxDataCubeStreamMsgDistWeekParam();
			msgParam.setAccess_token(accesstoken);
			msgParam.setBegin_date(bDate);
			msgParam.setEnd_date(eDate);
			
			
			// 调用接口
			String requestUrl = GETUPSTREAMMSGDISTWEEK_URL.replace("ACCESS_TOKEN", accesstoken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(msgParam));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			Object error = result.get("errcode");

			// 无错误消息时 返回数据对象
			JSONArray arrayResult = result.getJSONArray("list");
			// 正常返回
			List<WxDataCubeStreamMsgDistWeekInfo> msgInfoList = null;
			msgInfoList=JSONHelper.toList(arrayResult, WxDataCubeStreamMsgDistWeekInfo.class);
			return msgInfoList;
		}
		return null;
	}
	
	/**
	 * 获取消息发送分布月数据
	 * @param bDate 起始时间
	 * @param eDate 结束时间
	 * @return
	 * @throws WexinReqException
	 */
	public static List<WxDataCubeStreamMsgDistMonthInfo> getWxDataCubeStreamMsgDistMonthInfo(String accesstoken,String bDate,String eDate) throws WexinReqException {
		if (accesstoken != null) {
			
			// 封装请求参数
			WxDataCubeStreamMsgDistMonthParam msgParam = new WxDataCubeStreamMsgDistMonthParam();
			msgParam.setAccess_token(accesstoken);
			msgParam.setBegin_date(bDate);
			msgParam.setEnd_date(eDate);
			
			// 调用接口
			String requestUrl = GETUPSTREAMMSGDISTMONTH_URL.replace("ACCESS_TOKEN", accesstoken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(msgParam));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			Object error = result.get("errcode");

			// 无错误消息时 返回数据对象
			JSONArray arrayResult = result.getJSONArray("list");
			// 正常返回
			List<WxDataCubeStreamMsgDistMonthInfo> msgInfoList = null;
			msgInfoList=JSONHelper.toList(arrayResult, WxDataCubeStreamMsgDistMonthInfo.class);
			return msgInfoList;
		}
		return null;
	}
	public static void main(String[] args) {
		
		// 获取消息发送概况数据
		getWxDataCubeStreamMsgInfo();
		
		// 获取消息分送分时数据
		testGetWxDataCubeStreamMsgHourInfo();
		
		// 获取消息发送周数据
		testGetWxDataCubeStreamMsgWeekInfo();
		
		// 获取消息发送月数据
		testGetWxDataCubeStreamMsgMonthInfo();
		
		// 获取消息发送分布数据
		testGetWxDataCubeStreamMsgDistInfo();
		
		// 获取消息发送分布周数据
		testGetWxDataCubeStreamMsgDistWeekInfo();
		
		// 获取消息发送分布月数据
		testGetWxDataCubeStreamMsgDistMonthInfo();
	}  
	
	/**
	 * 获取消息发送概况数据-测试
	 */
	private static void getWxDataCubeStreamMsgInfo(){
		System.out.println("test getWxDataCubeStreamMsgInfo start ");
		JwDataCubeAPI s = new JwDataCubeAPI();
		try {
			List<WxDataCubeStreamMsgInfo> msgInfoList = s.getWxDataCubeStreamMsgInfo("a8UEhkxmxN269afPJ7SuY9vVroHPYiwm9Yv5gu_yK4GGsA60IafJ3N9We4ubabgZ4lsl4kd0zqcH7JC70UsQ951Ndur4ejSIz7VvroBJn4g","2015-01-01","2015-01-07");
			if(msgInfoList!=null&&msgInfoList.size()>0){
				for (WxDataCubeStreamMsgInfo wxDataCubeStreamMsgInfo : msgInfoList) {
					System.out.println(wxDataCubeStreamMsgInfo.getMsg_count()); 
					System.out.println(wxDataCubeStreamMsgInfo.getMsg_type());
				}
			}
			 
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test getWxDataCubeStreamMsgInfo end ");
	}
	
	/**
	 * 获取消息分送分时数据-测试
	 */
	private static void testGetWxDataCubeStreamMsgHourInfo(){
		System.out.println("test testGetWxDataCubeStreamMsgHourInfo start ");
		JwDataCubeAPI s = new JwDataCubeAPI();
		try {
			List<WxDataCubeStreamMsgHourInfo> msgInfoList = s.getWxDataCubeStreamMsgHourInfo("a8UEhkxmxN269afPJ7SuY9vVroHPYiwm9Yv5gu_yK4GGsA60IafJ3N9We4ubabgZ4lsl4kd0zqcH7JC70UsQ951Ndur4ejSIz7VvroBJn4g","2015-01-07","2015-01-07");
			if(msgInfoList!=null&&msgInfoList.size()>0){
				for (WxDataCubeStreamMsgHourInfo wxDataCubeStreamMsghourInfo : msgInfoList) {
					System.out.println(wxDataCubeStreamMsghourInfo.getMsg_count()); 
					System.out.println(wxDataCubeStreamMsghourInfo.getMsg_type());
					System.out.println(wxDataCubeStreamMsghourInfo.getRef_hour());
				}
			}
			 
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test testGetWxDataCubeStreamMsgHourInfo end ");
	}
	
	/**
	 * 获取消息发送周数据-测试
	 */
	private static void testGetWxDataCubeStreamMsgWeekInfo(){
		System.out.println("test testGetWxDataCubeStreamMsgWeekInfo start ");
		JwDataCubeAPI s = new JwDataCubeAPI();
		try {
			List<WxDataCubeStreamMsgWeekInfo> msgInfoList = s.getWxDataCubeStreamMsgWeekInfo("a8UEhkxmxN269afPJ7SuY9vVroHPYiwm9Yv5gu_yK4GGsA60IafJ3N9We4ubabgZ4lsl4kd0zqcH7JC70UsQ951Ndur4ejSIz7VvroBJn4g","2015-01-01","2015-01-07");
			if(msgInfoList!=null&&msgInfoList.size()>0){
				for (WxDataCubeStreamMsgWeekInfo wxDataCubeStreamMsgInfo : msgInfoList) {
					System.out.println(wxDataCubeStreamMsgInfo.getMsg_count()); 
					System.out.println(wxDataCubeStreamMsgInfo.getMsg_type()); 
				}
			}
			 
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test testGetWxDataCubeStreamMsgWeekInfo end ");
	}
	
	/**
	 * 获取消息发送月数据-测试
	 */
	private static void testGetWxDataCubeStreamMsgMonthInfo(){
		System.out.println("test testGetWxDataCubeStreamMsgMonthInfo start ");
		JwDataCubeAPI s = new JwDataCubeAPI();
		try {
			List<WxDataCubeStreamMsgMonthInfo> msgInfoList = s.getWxDataCubeStreamMsgMonthInfo("a8UEhkxmxN269afPJ7SuY9vVroHPYiwm9Yv5gu_yK4GGsA60IafJ3N9We4ubabgZ4lsl4kd0zqcH7JC70UsQ951Ndur4ejSIz7VvroBJn4g","2014-01-01","2015-01-30");
			if(msgInfoList!=null&&msgInfoList.size()>0){
				for (WxDataCubeStreamMsgMonthInfo wxDataCubeStreamMsgInfo : msgInfoList) {
					System.out.println(wxDataCubeStreamMsgInfo.getMsg_count()); 
					System.out.println(wxDataCubeStreamMsgInfo.getMsg_type()); 
				}
			}
			 
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test testGetWxDataCubeStreamMsgMonthInfo end ");
	}
	
	/**
	 * 获取消息发送分布数据-测试
	 */
	private static void testGetWxDataCubeStreamMsgDistInfo(){
		System.out.println("test testGetWxDataCubeStreamMsgDistInfo start ");
		JwDataCubeAPI s = new JwDataCubeAPI();
		try {
			List<WxDataCubeStreamMsgDistInfo> msgInfoList = s.getWxDataCubeStreamMsgDistInfo("a8UEhkxmxN269afPJ7SuY9vVroHPYiwm9Yv5gu_yK4GGsA60IafJ3N9We4ubabgZ4lsl4kd0zqcH7JC70UsQ951Ndur4ejSIz7VvroBJn4g","2015-01-01","2015-01-15");
			if(msgInfoList!=null&&msgInfoList.size()>0){
				for (WxDataCubeStreamMsgDistInfo wxDataCubeStreamMsgInfo : msgInfoList) {
					System.out.println(wxDataCubeStreamMsgInfo.getRef_date()); 
					System.out.println(wxDataCubeStreamMsgInfo.getCount_interval()); 
				}
			}
			 
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test testGetWxDataCubeStreamMsgDistInfo end ");
	}
	
	/**
	 * 获取消息发送分布周数据-测试
	 */
	private static void testGetWxDataCubeStreamMsgDistWeekInfo(){
		System.out.println("test testGetWxDataCubeStreamMsgDistWeekInfo start ");
		JwDataCubeAPI s = new JwDataCubeAPI();
		try {
			List<WxDataCubeStreamMsgDistWeekInfo> msgInfoList = s.getWxDataCubeStreamMsgDistWeekInfo("a8UEhkxmxN269afPJ7SuY9vVroHPYiwm9Yv5gu_yK4GGsA60IafJ3N9We4ubabgZ4lsl4kd0zqcH7JC70UsQ951Ndur4ejSIz7VvroBJn4g","2015-01-01","2015-01-15");
			if(msgInfoList!=null&&msgInfoList.size()>0){
				for (WxDataCubeStreamMsgDistWeekInfo wxDataCubeStreamMsgInfo : msgInfoList) {
					System.out.println(wxDataCubeStreamMsgInfo.getRef_date()); 
					System.out.println(wxDataCubeStreamMsgInfo.getCount_interval()); 
				}
			}
			 
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test testGetWxDataCubeStreamMsgDistWeekInfo end ");
	}
	
	/**
	 * 获取消息发送分布月数据-测试
	 */
	private static void testGetWxDataCubeStreamMsgDistMonthInfo(){
		System.out.println("test testGetWxDataCubeStreamMsgDistMonthInfo start ");
		JwDataCubeAPI s = new JwDataCubeAPI();
		try {
			List<WxDataCubeStreamMsgDistMonthInfo> msgInfoList = s.getWxDataCubeStreamMsgDistMonthInfo("a8UEhkxmxN269afPJ7SuY9vVroHPYiwm9Yv5gu_yK4GGsA60IafJ3N9We4ubabgZ4lsl4kd0zqcH7JC70UsQ951Ndur4ejSIz7VvroBJn4g","2015-01-01","2015-01-15");
			if(msgInfoList!=null&&msgInfoList.size()>0){
				for (WxDataCubeStreamMsgDistMonthInfo wxDataCubeStreamMsgInfo : msgInfoList) {
					System.out.println(wxDataCubeStreamMsgInfo.getRef_date()); 
					System.out.println(wxDataCubeStreamMsgInfo.getCount_interval()); 
				}
			}
			 
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test testGetWxDataCubeStreamMsgDistMonthInfo end ");
	}
}
