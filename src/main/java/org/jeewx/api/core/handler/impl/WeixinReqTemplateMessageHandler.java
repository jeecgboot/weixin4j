package org.jeewx.api.core.handler.impl;

import com.alibaba.fastjson.JSON;
import org.jeewx.api.core.annotation.ReqType;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.handler.WeiXinReqHandler;
import org.jeewx.api.core.req.model.WeixinReqConfig;
import org.jeewx.api.core.req.model.WeixinReqParam;
import org.jeewx.api.core.req.model.message.IndustryTemplateMessageSend;
import org.jeewx.api.core.req.model.message.TemplateMessage;
import org.jeewx.api.core.util.HttpRequestProxy;
import org.jeewx.api.core.util.WeiXinReqUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板消息发送
 * @author sfli.sir
 *
 */
public class WeixinReqTemplateMessageHandler implements WeiXinReqHandler {

	private static Logger logger = LoggerFactory.getLogger(WeixinReqTemplateMessageHandler.class);
	
	@SuppressWarnings("rawtypes")
	public String doRequest(WeixinReqParam weixinReqParam) throws WexinReqException{
		// TODO Auto-generated method stub
		String strReturnInfo = "";
		if(weixinReqParam.getClass().isAnnotationPresent(ReqType.class)){
			ReqType reqType = weixinReqParam.getClass().getAnnotation(ReqType.class);
			WeixinReqConfig objConfig = WeiXinReqUtil.getWeixinReqConfig(reqType.value());
			if(objConfig != null){
				String reqUrl = objConfig.getUrl();
				IndustryTemplateMessageSend mc = (IndustryTemplateMessageSend) weixinReqParam;
				Map<String, String> parameters = new HashMap<String, String>();
				parameters.put("access_token", mc.getAccess_token());
				String jsonData = getMsgJson(mc) ;
				logger.info("处理模板消息"+jsonData);
				strReturnInfo = HttpRequestProxy.doJsonPost(reqUrl, parameters, jsonData);
			}
		}else{
			logger.info("没有找到对应的配置信息");
		}
		return strReturnInfo;
	}

	/**
	 * 单独处理 json信息
	 * @param name
	 * @param b
	 * @return
	 */
	private  String getMsgJson(IndustryTemplateMessageSend mc){
		StringBuffer json = new StringBuffer();
		TemplateMessage tm = mc.getData();
		mc.setData(null);
		String objJson = JSON.toJSONString(mc);
		mc.setData(tm);
		json.append(objJson);
		json.setLength(json.length()-1);
		json.append(",");
		json.append("\"data\":{");
		
		objJson = JSON.toJSONString(tm.getFirst());
		json.append(" \"first\":");
		json.append(objJson);
		json.append(",");
		objJson = JSON.toJSONString(tm.getKeynote1());
		json.append(" \"keynote1\":");
		json.append(objJson);
		json.append(",");
		objJson = JSON.toJSONString(tm.getKeynote2());
		json.append(" \"keynote2\":");
		json.append(objJson);
		json.append(",");
		objJson = JSON.toJSONString(tm.getKeynote3());
		json.append(" \"keynote3\":");
		json.append(objJson);
		json.append(",");
		objJson = JSON.toJSONString(tm.getRemark());
		json.append(" \"remark\":");
		json.append(objJson);
		json.append("}}");
		return json.toString();
	}
	
}
