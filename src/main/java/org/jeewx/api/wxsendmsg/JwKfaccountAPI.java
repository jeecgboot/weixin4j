package org.jeewx.api.wxsendmsg;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.WeiXinReqService;
import org.jeewx.api.core.req.model.kfaccount.*;
import org.jeewx.api.core.util.WeiXinConstant;
import org.jeewx.api.wxsendmsg.model.WxKfaccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信客服管理处理
 * 
 * @author lizr
 * 
 */
public class JwKfaccountAPI {

	private static Logger logger = LoggerFactory
			.getLogger(JwKfaccountAPI.class);

	/**
	 * 添加客服
	 *  kf_account	是	完整客服账号，格式为：账号前缀@公众号微信号，账号前缀最多10个字符，必须是英文或者数字字符。如果没有公众号微信号，请前往微信公众平台设置。
		nickname	是	客服昵称，最长6个汉字或12个英文字符
		password	是	客服账号登录密码，格式为密码明文的32位加密MD5值
	 * @param accessToken
	 * @param kf_account
	 * @param nickname
	 * @param password
	 * @return
	 * @throws WexinReqException
	 * 
	 */
	public static String addKfacount(String accessToken, String kf_account,
			String nickname, String password) throws WexinReqException {
		KfaccountAdd kf = new KfaccountAdd();
		kf.setAccess_token(accessToken);
		kf.setKf_account(kf_account);
		kf.setNickname(nickname);
		kf.setPassword(password);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(kf);
		String msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
		return msg;
	}
	
	/**
	 * 修改客服账号
	 * @param accessToken
	 * @param kf_account
	 * @param nickname
	 * @param password
	 * @return
	 * @throws WexinReqException
	 */
	public static String modifyKfaccount(String accessToken, String kf_account,
			String nickname, String password) throws WexinReqException{
		KfaccountUpdate kfUp = new KfaccountUpdate();
		kfUp.setAccess_token(accessToken);
		kfUp.setKf_account(kf_account);
		kfUp.setNickname(nickname);
		kfUp.setPassword(password);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(kfUp);
		String msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
		return msg;
	}

	/**
	 * 删除客服账号
	 * @param accessToken
	 * @param kf_account
	 * @param nickname
	 * @param password
	 * @return
	 * @throws WexinReqException
	 */
	public static String deleteKfaccount(String accessToken, String kf_account,
			String nickname, String password) throws WexinReqException{
		KfaccountDel kfdel = new KfaccountDel();
		kfdel.setAccess_token(accessToken);
		kfdel.setKf_account(kf_account);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(kfdel);
		String msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
		return msg;
	}
	
	/**
	 * 修改客服头像
	 * @param accessToken
	 * @param kf_account
	 * @param filePathName
	 * @return
	 * @throws WexinReqException
	 */
	public static String uploadKfaccountHeadimg(String accessToken,String kf_account,String filePathName ) throws WexinReqException{
		KfaccountUploadheadimg kfUpload = new KfaccountUploadheadimg();
		kfUpload.setAccess_token(accessToken);
		kfUpload.setFilePathName(filePathName);
		kfUpload.setKf_account(kf_account);
		kfUpload.setType("image");
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(kfUpload);
		String msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
		return msg;
	}
	
	/**
	 * 获取所有的客服账号信息
	 * @param accessToken
	 * @return
	 * @throws WexinReqException
	 */
	public static List<WxKfaccount> getAllKfaccount(String accessToken) throws WexinReqException{
		KfaccountList kfGet = new KfaccountList();
		kfGet.setAccess_token(accessToken);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(kfGet);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		List<WxKfaccount> lstWxKfaccount = null;
		JSONArray kf_list = result.getJSONArray("kf_list");
		lstWxKfaccount = new ArrayList<WxKfaccount>();
		WxKfaccount kfaccount = null;
		for(int i = 0; i < kf_list.size() ; i++){
			kfaccount = (WxKfaccount) JSONObject.toJavaObject( kf_list.getJSONObject(i), WxKfaccount.class);
			lstWxKfaccount.add(kfaccount);
		}
		return lstWxKfaccount;
	}
	/**
	 * 
	 * 发送客服信息
	 * access_token	是	调用接口凭证
		touser	是	普通用户openid
		msgtype	是	消息类型，文本为text，图片为image，语音为voice，视频消息为video，音乐消息为music，图文消息为news
		content	是	文本消息内容
		media_id	是	发送的图片/语音/视频的媒体ID
		thumb_media_id	是	缩略图的媒体ID
		title	否	图文消息/视频消息/音乐消息的标题
		description	否	图文消息/视频消息/音乐消息的描述
		musicurl	是	音乐链接
		hqmusicurl	是	高品质音乐链接，wifi环境优先使用该链接播放音乐
		url	否	图文消息被点击后跳转的链接
		picurl	否	图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80
	 * @throws WexinReqException 
	 */
	public static String sendKfMessage(KfcustomSend kfcustomSend) throws WexinReqException{
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(kfcustomSend);
		String msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
		return msg;
	}
	
	/**
	 * 获取在线客服信息
	 * kf_account	完整客服账号，格式为：账号前缀@公众号微信号
		status	客服在线状态 1：pc在线，2：手机在线。若pc和手机同时在线则为 1+2=3
		kf_id	客服工号
		auto_accept	客服设置的最大自动接入数
		accepted_case	客服当前正在接待的会话数
	 * @param accessToken
	 * @return
	 * @throws WexinReqException
	 */
	public static List<WxKfaccount> getAllOnlineKfaccount(String accessToken) throws WexinReqException{
		KfOnlineAccountList kfGet = new KfOnlineAccountList();
		kfGet.setAccess_token(accessToken);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(kfGet);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		List<WxKfaccount> lstWxKfaccount = null;
		JSONArray kf_list = result.getJSONArray("kf_online_list");
		lstWxKfaccount = new ArrayList<WxKfaccount>();
		WxKfaccount kfaccount = null;
		for (int i = 0; i < kf_list.size(); i++) {
			kfaccount = (WxKfaccount) JSONObject.toJavaObject(
					kf_list.getJSONObject(i), WxKfaccount.class);
			lstWxKfaccount.add(kfaccount);
		}
		return lstWxKfaccount;
	}
	public static void main(String[] args){
		try {
			String s = "qQo8f2B0D0ZnlTP-8TKOMWoDcGiCoAhICn09S_QKxMgpSVp0VG8rgg_8PAJhy893z4lU-kY89DsZAsC3M54zxQBxuwTehg2nC_dO75VEGqw";
			//JwTokenAPI.getAccessToken("wx00737224cb9dbc7d","b9479ebdb58d1c6b6efd4171ebe718b5");
			List<WxKfaccount> ls = JwKfaccountAPI.getAllOnlineKfaccount(s);
			for(WxKfaccount a : ls){
				System.out.println(a.getKf_account()+"---"+a.getKf_id()+a.getKf_nick());
				System.out.println(a.getKf_headimgurl());
			}
			
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
