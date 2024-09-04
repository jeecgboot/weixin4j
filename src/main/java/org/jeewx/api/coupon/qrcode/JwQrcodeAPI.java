package org.jeewx.api.coupon.qrcode;

import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.WeiXinReqService;
import org.jeewx.api.coupon.qrcode.model.Getticket;
import org.jeewx.api.coupon.qrcode.model.GetticketRtn;
import org.jeewx.api.coupon.qrcode.model.QrcodeInfo;
import org.jeewx.api.coupon.qrcode.model.QrcodeRtnInfo;


/**
 * 微信卡券 - 卡券投放
 * @author lihongxuan
 *
 */
public class JwQrcodeAPI {

	/**
	 * 创建二维码
	 * @throws WexinReqException 
	 */
	public static QrcodeRtnInfo doAddQrcode(String accesstoken,QrcodeInfo qrcodeInfo) throws WexinReqException {
		if (accesstoken != null) {
			qrcodeInfo.setAccess_token(accesstoken);
			JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(qrcodeInfo);
			QrcodeRtnInfo qrcodeRtnInfo = (QrcodeRtnInfo)JSONObject.toJavaObject(result, QrcodeRtnInfo.class);
			return qrcodeRtnInfo;
		}
		return null;
	}

	/**
	 * 获取api_ticket
	 */
	public static GetticketRtn doGetticket(String accesstoken)throws WexinReqException {
		if (accesstoken != null) {
			Getticket gk = new Getticket();
			gk.setAccess_token(accesstoken);
			JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(gk);
			GetticketRtn getticketRtn = (GetticketRtn)JSONObject.toJavaObject(result, GetticketRtn.class);
			return getticketRtn;
		}
		return null;
	}

}
 