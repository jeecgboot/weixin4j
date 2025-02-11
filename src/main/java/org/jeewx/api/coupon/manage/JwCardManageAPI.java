package org.jeewx.api.coupon.manage;

import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.coupon.manage.model.*;


/**
 * 微信卡券 - 基础管理
 * @author mcl
 * @version v1.0
 */
public class JwCardManageAPI {
	// 删除卡券
	private static final String del_card_url = "https://api.weixin.qq.com/card/delete?access_token=ACCESS_TOKEN";
	// 查询卡券
	private static final String get_card_url = "https://api.weixin.qq.com/card/code/get?access_token=ACCESS_TOKEN";
	// 批量查询卡列表
	private static final String batchget_card_url = "https://api.weixin.qq.com/card/batchget?access_token=ACCESS_TOKEN";
	// 查询卡券详情
	private static final String get_card_detail_url = "https://api.weixin.qq.com/card/get?access_token=ACCESS_TOKEN";
	//更改code
	private static final String update_code_url = "https://api.weixin.qq.com/card/code/update?access_token=ACCESS_TOKEN";
	//设置卡券失效
	private static final String unavailable_code_url = "https://api.weixin.qq.com/card/code/unavailable?access_token=ACCESS_TOKEN";
	//更改卡券信息
	private static final String update_card_url = "https://api.weixin.qq.com/card/update?access_token=ACCESS_TOKEN";
	//更改库存
	private static final String modifystock_card_url = "https://api.weixin.qq.com/card/modifystock?access_token=ACCESS_TOKEN";
	
	/**
	 * 删除卡券
	 */
	public static DelRtnInfo doDelCard(String newAccessToken,String card_id) {
		if (newAccessToken != null) {
			String requestUrl = del_card_url.replace("ACCESS_TOKEN", newAccessToken);
			String json = "{\"card_id\": \""+card_id+"\"}";
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", json);
			DelRtnInfo delRtnInfo = (DelRtnInfo)JSONObject.toJavaObject(result, DelRtnInfo.class);
			return delRtnInfo;
		}
		return null;
	}

	/**
	 * 查询卡券
	 */
	public static GetCardRtnInfo doGetCard(String newAccessToken,String code,String card_id) {
		if (newAccessToken != null) {
			String requestUrl = get_card_url.replace("ACCESS_TOKEN", newAccessToken);
			String json = emptyStrJson("code",code,"card_id",card_id);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", json);
			GetCardRtnInfo getCardRtnInfo = (GetCardRtnInfo)JSONObject.toJavaObject(result, GetCardRtnInfo.class);
			return getCardRtnInfo;
		}
		return null;
	}
	/**
	 * 批量查询卡列表
	 * @param offset 查询卡列表的起始偏移量，从0 开始，即offset: 5 是指从从列表里的第六个开始读取。
	 * @param count 需要查询的卡片的数量（数量最大50）。
	 */
	public static  BatchGetCardRtnInfo doBatchGetCard(String newAccessToken,int offset,int count) {
		if (newAccessToken != null) {
			String requestUrl = batchget_card_url.replace("ACCESS_TOKEN", newAccessToken);
			String json = "{\"offset\":"+offset+",\"count\": "+count+"}";
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", json);
			BatchGetCardRtnInfo batchGetCardRtnInfo = (BatchGetCardRtnInfo)JSONObject.toJavaObject(result, BatchGetCardRtnInfo.class);
			return batchGetCardRtnInfo;
		}
		return null;
	}
	/**
	 * 查询卡券详情
	 */
	public static  GetCardDetailRtnInfo doGetCardDetail(String newAccessToken,String card_id) {
		if (newAccessToken != null) {
			String requestUrl = get_card_detail_url.replace("ACCESS_TOKEN", newAccessToken);
			String json = "{\"card_id\":\""+card_id+"\"}";
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", json);
			GetCardDetailRtnInfo getCardDetailRtnInfo = (GetCardDetailRtnInfo)JSONObject.toJavaObject(result, GetCardDetailRtnInfo.class);
			return getCardDetailRtnInfo;
		}
		return null;
	}
	/**
	 * 更改code
	 *  为确保转赠后的安全性，微信允许自定义code的商户对已下发的code进行更改。
	 *  注：为避免用户疑惑，建议仅在发生转赠行为后（发生转赠后，微信会通过事件推送的方
	 *  式告知商户被转赠的卡券code）对用户的code进行更改。
	 * @param code 卡券的code 编码。
	 * @param card_id 卡券ID。
	 * @param new_code 新的卡券code 编码
	 */
	public static  CommCardRtnInfo doUpdateCode(String newAccessToken,String code,String card_id,String new_code) {
		if (newAccessToken != null) {
			String requestUrl = update_code_url.replace("ACCESS_TOKEN", newAccessToken);
			String json = emptyStrJson("code",code,"card_id",card_id,"new_code",new_code);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", json);
			CommCardRtnInfo commCardRtnInfo = (CommCardRtnInfo)JSONObject.toJavaObject(result, CommCardRtnInfo.class);
			return commCardRtnInfo;
		}
		return null;
	}
	/**
	 * 卡券设置为失效状态
	 *  为满足改票、退款等异常情况，可调用卡券失效接口将用户的卡券设置为失效状态。
	 *  注：设置卡券失效的操作不可逆，即无法将设置为失效的卡券调回有效状态，商家须慎重调用该接口。
	 * @param code 卡券的code 编码。
	 * @param card_id 自定义code卡券才填写。
	 */
	public static  CommCardRtnInfo doUnavailableCode(String newAccessToken,String code,String card_id) {
		if (newAccessToken != null) {
			String requestUrl = unavailable_code_url.replace("ACCESS_TOKEN", newAccessToken);
			String json = emptyStrJson("code",code,"card_id",card_id);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", json);
			CommCardRtnInfo commCardRtnInfo = (CommCardRtnInfo)JSONObject.toJavaObject(result, CommCardRtnInfo.class);
			return commCardRtnInfo;
		}
		return null;
	}
	/**
	 * 更改卡券信息
	 *  支持更新部分通用字段及特殊卡券（会员卡、飞机票、电影票、红包）中特定字段的信息。
	 *  注：更改卡券的部分字段后会重新提交审核
	 * @param code 卡券的code 编码。
	 * @param card_id 自定义code卡券才填写。
	 */
	public static  CommCardRtnInfo doUpdateCard(String newAccessToken,CardUpdate cardUpdate) {
		if (newAccessToken != null) {
			String requestUrl = update_card_url.replace("ACCESS_TOKEN", newAccessToken);
			JSONObject obj = JSONObject.parseObject(JSONObject.toJSONString(cardUpdate));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST",
					obj.toString());
			CommCardRtnInfo commCardRtnInfo = (CommCardRtnInfo)JSONObject.toJavaObject(result, CommCardRtnInfo.class);
			return commCardRtnInfo;
		}
		return null;
	}
	/**
	 * 库存修改
	 * 增减某张卡券的库存
	 * @param card_id 自定义code卡券才填写。
	 * @param increase_stock_value 增加多少库存，可以不填或填0。
	 * @param reduce_stock_value   减少多少库存，可以不填或填0
	 */
	public static  CommCardRtnInfo doModifystockCard(String newAccessToken,String card_id,int increase_stock_value,int reduce_stock_value) {
		if (newAccessToken != null) {
			String requestUrl = modifystock_card_url.replace("ACCESS_TOKEN", newAccessToken);
			String json = "{\"card_id\":\""+card_id+"\",\"increase_stock_value\": "+increase_stock_value+",\"reduce_stock_value\": "+reduce_stock_value+"}";
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", json);
			CommCardRtnInfo commCardRtnInfo = (CommCardRtnInfo)JSONObject.toJavaObject(result, CommCardRtnInfo.class);
			return commCardRtnInfo;
		}
		return null;
	}
	/**
	 * 主要解决传入参数为null或""生成json的问题。
	 */
	private static String emptyStrJson(String field1,String value1,String field2,String value2){
		String json = "";
		if(value1!=null && value1.trim().length() > 0){
			json = "{\""+field1+"\":\""+value1+"\"";
		}
		if(value2!=null && value2.trim().length() > 0){
			if(json.trim().length() > 0){
				json = json+",\""+field2+"\":\""+value1+"\"}";
			}else{
				json = "{\""+field2+"\":\""+value2+"\"}";
			}
		}
		if(json.trim().length() > 0 && !json.endsWith("}")){
			json = json + "}";
		}
		return json;
	}
	/**
	 * 主要解决传入参数为null或""生成json的问题。
	 */
	private static String emptyStrJson(String field1,String value1,String field2,String value2,String field3,String value3){
		String json = "";
		if(value1!=null && value1.trim().length() > 0){
			json = "{\""+field1+"\":\""+value1+"\"";
		}
		if(value2!=null && value2.trim().length() > 0){
			if(json.trim().length() > 0){
				json = json+",\""+field2+"\":\""+value2+"\"";
			}else{
				json = "{\""+field2+"\":\""+value2+"\"";
			}
		}
		if(value3!=null && value3.trim().length() > 0){
			if(json.trim().length() > 0){
				json = json+",\""+field3+"\":\""+value3+"\"";
			}else{
				json = "{\""+field3+"\":\""+value3+"\"";
			}
		}
		if(json.trim().length() > 0 && !json.endsWith("}")){
			json = json + "}";
		}
		return json;
	}
}
 