package org.jeewx.api.wxsendmsg;

import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.WeiXinReqService;
import org.jeewx.api.core.req.model.message.*;
import org.jeewx.api.core.util.WeiXinConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模板消息接口
 * 
 * @author lizr
 * 
 */
public class JwTemplateMessageAPI {

	private static Logger logger = LoggerFactory
			.getLogger(JwTemplateMessageAPI.class);

	/**
	 * 设置行业信息
	 * @param accessToken
	 * @param industry_id1
	 * @param industry_id2
	 * @return
	 * @throws WexinReqException
	 */
	public static String setIndustry(String accessToken,String industry_id1,String industry_id2) throws WexinReqException{
		IndustryTemplateSet s = new IndustryTemplateSet();
		s.setAccess_token(accessToken);
		s.setIndustry_id1(industry_id1);
		s.setIndustry_id2(industry_id2);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(s);
		String msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
		return msg;
	}
	
	/**
	 * 添加模板信息
	 * @param accessToken
	 * @param template_id_short
	 * @return
	 * @throws WexinReqException
	 */
	public static String addTemplate(String accessToken,String template_id_short) throws WexinReqException{
		IndustryTemplateAdd t = new IndustryTemplateAdd();
		t.setAccess_token(accessToken);
		t.setTemplate_id_short(template_id_short);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(t);
		String msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
		if("ok".equalsIgnoreCase(msg)){
			msg = result.getString("template_id");
		}
		
		return msg;
	}
	
	/**
	 * 发送客服模板消息
	 * @param industryTemplateMessageSend
	 * @return
	 * @throws WexinReqException
	 */
	public static String sendTemplateMsg(IndustryTemplateMessageSend industryTemplateMessageSend) throws WexinReqException{
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(industryTemplateMessageSend);
		String msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
		return msg;
	}
	
	public static void main(String[] args){
		try {
			String s = "qCU9cEJzhGSJxncRzuxC2Yx5zB4LNysB1_GVTYeRwWtsRydB7c7C6q2WlRFeX7XQg7edLiOQcO5juf0BcMXcWMgR8lEe3qutVMwR88WVCy0";
			//JwTokenAPI.getAccessToken("wx00737224cb9dbc7d","b9479ebdb58d1c6b6efd4171ebe718b5");
			System.out.println(s);
			//"qQo8f2B0D0ZnlTP-8TKOMWoDcGiCoAhICn09S_QKxMgpSVp0VG8rgg_8PAJhy893z4lU-kY89DsZAsC3M54zxQBxuwTehg2nC_dO75VEGqw";
			//JwTokenAPI.getAccessToken("wx00737224cb9dbc7d","b9479ebdb58d1c6b6efd4171ebe718b5");
			IndustryTemplateMessageSend industryTemplateMessageSend = new IndustryTemplateMessageSend();
			industryTemplateMessageSend.setAccess_token(s);
			industryTemplateMessageSend.setTemplate_id("4m3vrpiSA-CPyL9YqHw2jKDlZSX6Sz65SoMKvA9BV1s");
			industryTemplateMessageSend.setTouser("oR0jFtxn8q_UsSXsKT395GVaG8q0");
			industryTemplateMessageSend.setUrl("www.baidu.com");
			industryTemplateMessageSend.setTopcolor("#ffAADD");
			TemplateMessage data = new TemplateMessage();
			TemplateData first = new TemplateData();
			first.setColor("#173177");
			first.setValue("恭喜你购买成2323功！");
			
			
			TemplateData keynote1= new TemplateData();
			keynote1.setColor("#173177");
			keynote1.setValue("巧克22力");
			
			TemplateData keynote2= new TemplateData();
			keynote2.setColor("39.8元");
			keynote2.setValue("恭喜你购买成功！");
			
			TemplateData keynote3= new TemplateData();
			keynote3.setColor("#173177");
			keynote3.setValue("2014年9月16日");
			
			TemplateData remark= new TemplateData();
			remark.setColor("#173177");
			remark.setValue("欢迎再次购买！");
			data.setFirst(first);
			data.setKeynote1(keynote1);
			data.setKeynote2(keynote2);
			data.setKeynote3(keynote3);
			data.setRemark(remark);
			industryTemplateMessageSend.setData(data);
			
			s  = JwTemplateMessageAPI.sendTemplateMsg(industryTemplateMessageSend);
			
			System.out.println(s);
			// 4m3vrpiSA-CPyL9YqHw2jKDlZSX6Sz65SoMKvA9BV1s
			
			
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
