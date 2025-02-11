package org.jeewx.api.wxsendmsg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.model.message.TemplateMessageSendResult;
import org.jeewx.api.core.util.WeiXinConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模板消息接口
 * 
 * 
 */
public class JwSendTemplateMsgAPI {

	//设置行业信息
	private static String set_industry = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
	
	//获取行业信息
	private static String get_industry = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN";
	
	//获得模板ID
	private static String get_template_id = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
	
	//获取模板列表
	private static String get_tamplate_list = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN";
	
	//删除模板
	private static String del_template = "https://api.weixin.qq.com/cgi-bin/template/del_private_template?access_token=ACCESS_TOKEN";
	
	//发送模板消息
	private static String send_template_msg = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	private static Logger logger = LoggerFactory.getLogger(JwSendTemplateMsgAPI.class);
	/**
	 * 设置行业信息
	 * @param accessToken
	 * @param industry_id1
	 * @param industry_id2
	 * @return
	 * @throws WexinReqException
	 */
	public static String setIndustry(String accessToken,String industry_id1,String industry_id2) throws WexinReqException{
		String msg = "";
		if (accessToken != null) {
			String requestUrl = set_industry.replace("ACCESS_TOKEN", accessToken);
			JSONObject obj = new JSONObject();
			obj.put("industry_id1", industry_id1);
			obj.put("industry_id2", industry_id2);
			logger.info("设置行业信息方法执行前json参数 obj: "+obj.toString());
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
			if(error == null){
				msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
			}else{
				msg = result.toString();
			}
			logger.info("设置行业信息方法执行后json参数 : "+result.toString());
		}
		return msg;
	}
	
	/**
	 * 获取行业信息
	 * @param accessToken
	 * @param industry_id1
	 * @param industry_id2
	 * @return
	 * @throws WexinReqException
	 */
	public static String getIndustry(String accessToken) throws WexinReqException{
		String msg = "";
		if (accessToken != null) {
			String requestUrl = get_industry.replace("ACCESS_TOKEN", accessToken);
			logger.info("获取行业信息方法执行前json参数 accessToken: "+accessToken);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "GET",null);
			Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
			if(error == null){
				msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
			}else{
				msg = result.toString();
			}
			logger.info("获取行业信息方法执行后json参数 : "+result.toString());
		}
		return msg;
	}
	
	/**
	 * 获取模板ID
	 * @param accessToken
	 * @return
	 * @throws WexinReqException
	 */
	public static String getTemplateId(String accessToken) throws WexinReqException{
		String msg = "";
		if (accessToken != null) {
			String requestUrl = get_template_id.replace("ACCESS_TOKEN", accessToken);
			logger.info("获取模板ID方法执行前json参数 accessToken: "+accessToken);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", null);
			Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
			if(error == null){
				msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
			}else{
				msg = result.toString();
			}
			logger.info("获取模板ID方法执行后json参数 : "+result.toString());
		}
		return msg;
	}
	
	/**
	 * 获取模板列表
	 * @param accessToken
	 * @return
	 * @throws WexinReqException
	 */
	public static String getTemplateList(String accessToken) throws WexinReqException{
		String msg = "";
		if (accessToken != null) {
			String requestUrl = get_tamplate_list.replace("ACCESS_TOKEN", accessToken);
			logger.info("获取模板列表方法执行前json参数 accessToken: "+accessToken);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "GET",null);
			Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
			if(error == null){
				msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
			}else{
				msg = result.toString();
			}
			logger.info("获取模板列表方法执行后json参数 : "+result.toString());
		}
		return msg;
	}
	
	/**
	 * 删除模板
	 */
	public static String delTemplate(String accessToken,String template_id) throws WexinReqException{
		String msg = "";
		if (accessToken != null) {
			String requestUrl = del_template.replace("ACCESS_TOKEN", accessToken);
			JSONObject obj = new JSONObject();
			obj.put("template_id", template_id);
			logger.info("删除模板方法执行前json参数 : "+obj.toString());
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
			if(error == null){
				msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
			}else{
				msg = result.toString();
			}
			logger.info("删除模板方法执行后json参数 : "+result.toString());
		}
		return msg;
	}
	
	/**
	 * 发送模板消息
	 * @param industryTemplateMessageSend
	 * @return
	 * @throws WexinReqException
	 */
	public static String sendTemplateMsg(String accessToken, TemplateMessageSendResult msgSend)
			throws WexinReqException {
		String msg = "";
		if (accessToken != null) {
			String requestUrl = send_template_msg.replace("ACCESS_TOKEN", accessToken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(msgSend));
			logger.info("发送模板消息方法执行前json参数 :{obj :" + obj.toString() + "}");
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
			if (error == null) {
				msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
			} else {
				msg = result.toString();
			}
			logger.info("发送模板消息方法执行后json参数 :{result :" + result.toString() + "}");
		}
		return msg;
	}
	
	
	public static void main(String[] args){
		String accessToken = "MAMJfbrGDKIGPiQMfeSTYimX6dyitcvSVoUPzOLVe75erBTO9lRnfad6ov5rXgAkTjX-4ZujQnmLH8ZBnTCq0QCEwLgoYAZSp_HKbeJ24zUXCLbAIADIR";
		try {
			JwSendTemplateMsgAPI.getIndustry(accessToken);
		} catch (WexinReqException e) {
			e.printStackTrace();
		}
	}
}
