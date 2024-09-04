package org.jeewx.api.report.datastatistics.graphicanalysis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.WeiXinReqService;
import org.jeewx.api.core.req.model.dataCube.*;
import org.jeewx.api.report.datastatistics.graphicanalysis.model.GraphicAnalysisRtnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: JwGraphicAnalysisAPI 
* @Description: 图文分析数据接口
* @author zhaoxl
*/
public class JwGraphicAnalysisAPI {
	
	private static Logger logger = LoggerFactory.getLogger(JwGraphicAnalysisAPI.class);
	
	private static Long getDays(String Date1,String Date2){
		return Date.valueOf(Date1).getTime() /86400000L - Date.valueOf(Date2).getTime() / 86400000L;
	}
	/**
	 * @throws UnsupportedEncodingException  
	* @Title: getArticleSummary 
	* @Description: 获取图文群发每日数据
	* @param @param accesstoken
	* @param @param begin_date
	* @param @param end_date
	* @param @return
	* @param @throws WexinReqException    设定文件 
	* @return List<GraphicAnalysisRtnInfo>    返回类型 
	* @throws 
	*/
	public static List<GraphicAnalysisRtnInfo> getArticleSummary(String accesstoken,String begin_date,String end_date) throws WexinReqException, UnsupportedEncodingException{
		if(accesstoken != null){
			if(getDays(end_date,begin_date) >= 1){
				logger.error("传入的日期间隔大于1天");
				return null;
			}else{
				WxDataCubeStreamArticleSummaryParam articleSummary = new WxDataCubeStreamArticleSummaryParam();
				articleSummary.setAccess_token(accesstoken);
				articleSummary.setBegin_date(begin_date);
				articleSummary.setEnd_date(end_date);
				
				JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(
						articleSummary);
				List<GraphicAnalysisRtnInfo> graphicAnalysisRtnInfoList = new ArrayList<GraphicAnalysisRtnInfo>();
				Object error = result.get("errcode");
				
				JSONArray array = null;
				GraphicAnalysisRtnInfo graphicAnalysisRtnInfo = null;
				array = (JSONArray) result.get("list");
				for (Object object : array) {
					graphicAnalysisRtnInfo = (GraphicAnalysisRtnInfo) JSONObject
							.toJavaObject((JSONObject) object,
									GraphicAnalysisRtnInfo.class);
					graphicAnalysisRtnInfoList.add(graphicAnalysisRtnInfo);
				}
				return graphicAnalysisRtnInfoList;
			}
		}
		return null;
	}
	/**
	 * @throws UnsupportedEncodingException  
	* @Title: getArticleTotal 
	* @Description: 获取图文群发总数据  最大时间跨度 1天
	* @param @param accesstoken
	* @param @param begin_date
	* @param @param end_date
	* @param @return
	* @param @throws WexinReqException    设定文件 
	* @return List<GraphicAnalysisRtnInfo>    返回类型 
	* @throws 
	*/
	public static List<GraphicAnalysisRtnInfo> getArticleTotal(String accesstoken,String begin_date,String end_date) throws WexinReqException, UnsupportedEncodingException{
		if(accesstoken != null){
			if(getDays(end_date,begin_date) >= 1){
				logger.error("传入的日期间隔大于1天");
				return null;
			}else{
				WxDataCubeStreamArticleTotalParam articleTotal = new WxDataCubeStreamArticleTotalParam();
				articleTotal.setAccess_token(accesstoken);
				articleTotal.setBegin_date(begin_date);
				articleTotal.setEnd_date(end_date);
				
				JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(articleTotal);
				List<GraphicAnalysisRtnInfo> graphicAnalysisRtnInfoList = new ArrayList<GraphicAnalysisRtnInfo>();
				Object error = result.get("errcode");
				
				JSONArray array = null;
				GraphicAnalysisRtnInfo graphicAnalysisRtnInfo = null;
				array = (JSONArray) result.get("list");
				//修复JSONObject不能解析details内容的BUG
				for (Object object : array) {
					graphicAnalysisRtnInfo = JSONObject.parseObject(object.toString(), GraphicAnalysisRtnInfo.class);
//					graphicAnalysisRtnInfo = (GraphicAnalysisRtnInfo) JSONObject
//							.toBean((JSONObject) object,
//									GraphicAnalysisRtnInfo.class);
					graphicAnalysisRtnInfoList.add(graphicAnalysisRtnInfo);
				}
				return graphicAnalysisRtnInfoList;
			}
		}
		return null;
	}
	/**
	 * @throws UnsupportedEncodingException  
	* @Title: getUserRead 
	* @Description: 获取图文统计数据  最大时间跨度 3
	* @param @param accesstoken
	* @param @param begin_date
	* @param @param end_date
	* @param @return
	* @param @throws WexinReqException    设定文件 
	* @return List<GraphicAnalysisRtnInfo>    返回类型 
	* @throws 
	*/
	public static List<GraphicAnalysisRtnInfo> getUserRead(String accesstoken,String begin_date,String end_date) throws WexinReqException{
		if(accesstoken != null){
			if(getDays(end_date,begin_date) >= 3){
				logger.error("传入的日期间隔大于3天");
				return null;
			}else{
				WxDataCubeStreamUserReadParam userRead = new WxDataCubeStreamUserReadParam();
				userRead.setAccess_token(accesstoken);
				userRead.setBegin_date(begin_date);
				userRead.setEnd_date(end_date);
				
				JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(userRead);
				List<GraphicAnalysisRtnInfo> graphicAnalysisRtnInfoList = new ArrayList<GraphicAnalysisRtnInfo>();
				Object error = result.get("errcode");
				
				JSONArray array = null;
				GraphicAnalysisRtnInfo graphicAnalysisRtnInfo = null;
				array = (JSONArray) result.get("list");
				for (Object object : array) {
					graphicAnalysisRtnInfo = ((JSONObject) object).toJavaObject(GraphicAnalysisRtnInfo.class);
					graphicAnalysisRtnInfoList.add(graphicAnalysisRtnInfo);
				}
				return graphicAnalysisRtnInfoList;
			}
		}
		return null;
	}
	/** 
	* @Title: getUserReadHour 
	* @Description: 获取图文统计分时数据  最大时间跨度1
	* @param @param accesstoken
	* @param @param begin_date
	* @param @param end_date
	* @param @return
	* @param @throws WexinReqException    设定文件 
	* @return List<GraphicAnalysisRtnInfo>    返回类型 
	* @throws 
	*/
	public static List<GraphicAnalysisRtnInfo> getUserReadHour(String accesstoken,String begin_date,String end_date) throws WexinReqException{
		if(accesstoken != null){
			if(getDays(end_date,begin_date) >= 1){
				logger.error("传入的日期间隔大于1天");
				return null;
			}else{
				WxDataCubeStreamUserReadHourParam userReadHour = new WxDataCubeStreamUserReadHourParam();
				userReadHour.setAccess_token(accesstoken);
				userReadHour.setBegin_date(begin_date);
				userReadHour.setEnd_date(end_date);
				
				JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(userReadHour);
				List<GraphicAnalysisRtnInfo> graphicAnalysisRtnInfoList = new ArrayList<GraphicAnalysisRtnInfo>();
				Object error = result.get("errcode");
				
				JSONArray array = null;
				GraphicAnalysisRtnInfo graphicAnalysisRtnInfo = null;
				array = (JSONArray) result.get("list");
				for (Object object : array) {
					graphicAnalysisRtnInfo = ((JSONObject) object).toJavaObject(GraphicAnalysisRtnInfo.class);
					graphicAnalysisRtnInfoList.add(graphicAnalysisRtnInfo);
				}
				return graphicAnalysisRtnInfoList;
			}
		}
		return null;
	}
	/** 
	* @Title: getUserShare 
	* @Description: 获取图文分享转发数据 最大时间跨度 7
	* @param @param accesstoken
	* @param @param begin_date
	* @param @param end_date
	* @param @return
	* @param @throws WexinReqException    设定文件 
	* @return List<GraphicAnalysisRtnInfo>    返回类型 
	* @throws 
	*/
	public static List<GraphicAnalysisRtnInfo> getUserShare(String accesstoken,String begin_date,String end_date) throws WexinReqException{
		if(accesstoken != null){
			if(getDays(end_date,begin_date) >= 7){
				logger.error("传入的日期间隔大于7天");
				return null;
			}else{
				WxDataCubeStreamUserShareParam userShare = new WxDataCubeStreamUserShareParam();
				userShare.setAccess_token(accesstoken);
				userShare.setBegin_date(begin_date);
				userShare.setEnd_date(end_date);
				
				JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(userShare);
				List<GraphicAnalysisRtnInfo> graphicAnalysisRtnInfoList = new ArrayList<GraphicAnalysisRtnInfo>();
				Object error = result.get("errcode");
				
				JSONArray array = null;
				GraphicAnalysisRtnInfo graphicAnalysisRtnInfo = null;
				array = (JSONArray) result.get("list");
				for (Object object : array) {
					graphicAnalysisRtnInfo = ((JSONObject) object).toJavaObject(GraphicAnalysisRtnInfo.class);

					graphicAnalysisRtnInfoList.add(graphicAnalysisRtnInfo);
				}
				return graphicAnalysisRtnInfoList;
			}
		}
		return null;
	}
	public static List<GraphicAnalysisRtnInfo> getUserShareHour(String accesstoken,String begin_date,String end_date) throws WexinReqException{
		if(accesstoken != null){
			if(getDays(end_date,begin_date) >= 1){
				logger.error("传入的日期间隔大于1天");
				return null;
			}else{
				WxDataCubeStreamUserShareHourParam userShareHour = new WxDataCubeStreamUserShareHourParam();
				userShareHour.setAccess_token(accesstoken);
				userShareHour.setBegin_date(begin_date);
				userShareHour.setEnd_date(end_date);
				
				JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(userShareHour);
				List<GraphicAnalysisRtnInfo> graphicAnalysisRtnInfoList = new ArrayList<GraphicAnalysisRtnInfo>();
				Object error = result.get("errcode");
				
				JSONArray array = null;
				GraphicAnalysisRtnInfo graphicAnalysisRtnInfo = null;
				array = (JSONArray) result.get("list");
				for (Object object : array) {
					graphicAnalysisRtnInfo = ((JSONObject) object).toJavaObject(GraphicAnalysisRtnInfo.class);
					graphicAnalysisRtnInfoList.add(graphicAnalysisRtnInfo);
				}
				return graphicAnalysisRtnInfoList;
			}
		}
		return null;
	}
}