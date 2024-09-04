package org.jeewx.api.wxaccount;

import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.WeiXinReqService;
import org.jeewx.api.core.req.model.account.QrcodeActionInfo;
import org.jeewx.api.core.req.model.account.QrcodeCreate;
import org.jeewx.api.core.req.model.account.QrcodeScene;
import org.jeewx.api.core.req.model.account.ShortUrlCreate;
import org.jeewx.api.core.util.WeiXinConstant;
import org.jeewx.api.wxaccount.model.WxQrcode;

/**
 * 微信--生成二维码和长短链接切换
 * 
 * @author lizr
 * 
 */
public class JwAccountAPI {

	/**
	 * 二维码类型，QR_SCENE为临时, QR_LIMIT_SCENE为永久, QR_LIMIT_STR_SCENE为永久的字符串参数值
	 */
	public static final String QRCODE_TYPE_SCENE = "QR_SCENE";
	public static final String QRCODE_TYPE_LIMIT = "QR_LIMIT_SCENE";
	public static final String QRCODE_TYPE_LIMIT_STR = "QR_LIMIT_STR_SCENE";
	
	/**
	 * 代表长链接转短链接
	 */
	public static final String SHORT_URL_ACTION = "long2short";

	/**
	 * 
	 * expire_seconds	该二维码有效时间，以秒为单位。 最大不超过1800。
		action_name	二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久,QR_LIMIT_STR_SCENE为永久的字符串参数值
		action_info	二维码详细信息
		scene_id	场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
		scene_str	场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段
	 * @param accessToken
	 * @param scene_str
	 * @param action_name
	 * @param expire_seconds
	 * @return
	 * @throws WexinReqException
	 */
	public static WxQrcode createQrcode(String accessToken, String scene_str,
			String action_name, String expire_seconds) throws WexinReqException {
		QrcodeCreate qrcodeCreate = new QrcodeCreate();
		qrcodeCreate.setAccess_token(accessToken);
		QrcodeActionInfo q = new QrcodeActionInfo();
		QrcodeScene ss = new QrcodeScene();
		ss.setScene_str(scene_str);
		q.setScene(ss);
		qrcodeCreate.setAction_info(q);
		qrcodeCreate.setExpire_seconds(expire_seconds);
		qrcodeCreate.setAction_name(action_name);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(qrcodeCreate);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		WxQrcode wxQrcode = null;
		wxQrcode = (WxQrcode) JSONObject.toJavaObject(result, WxQrcode.class);
		return wxQrcode;
	}

	/**
	 * 获取短链接信息
	 * @param accessToken
	 * @param longUrl
	 * @return
	 * @throws WexinReqException
	 */
	public static String getShortUrl(String accessToken,String longUrl) throws WexinReqException{
		ShortUrlCreate uc = new ShortUrlCreate();
		uc.setAccess_token(accessToken);
		uc.setLong_url(longUrl);
		uc.setAction(SHORT_URL_ACTION);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(uc);
		Object error = result.get("short_url");
		String shortUrl = "";
		if (error != null) {
			shortUrl = result.getString("short_url");
		}else{
			shortUrl = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
		}
		return shortUrl;
	}
	
}
