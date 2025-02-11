package org.jeewx.api.wxstore.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.common.JSONHelper;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.wxstore.order.model.OrderDelivery;
import org.jeewx.api.wxstore.order.model.OrderInfo;
import org.jeewx.api.wxstore.order.model.OrderPara;
import org.jeewx.api.wxstore.order.model.OrderRtnInfo;

import java.util.List;


/**
 * 微信小店 - 订单
 * @author zhangdaihao
 *
 */
public class JwOrderManagerAPI {
	// 根据订单ID获取订单详情
	private static String getid_order_url = "https://api.weixin.qq.com/merchant/order/getbyid?access_token=ACCESS_TOKEN";
	// 根据订单状态/创建时间获取订单详情
	private static String getfilter_order_url = "https://api.weixin.qq.com/merchant/order/getbyfilter?access_token=ACCESS_TOKEN";
	// 设置订单发货信息
	private static String setdelivery_order_url = "https://api.weixin.qq.com/merchant/order/setdelivery?access_token=ACCESS_TOKEN";
	// 关闭订单
	private static String close_order_url = "https://api.weixin.qq.com/merchant/order/close?access_token=ACCESS_TOKEN";
		
	/**
	 * 根据订单ID获取订单详情
	 * @param order_id
	 * @return
	 */
	public static OrderInfo getByOrderId(String newAccessToken, String order_id) {
		if (newAccessToken != null) {
			String requestUrl = getid_order_url.replace("ACCESS_TOKEN", newAccessToken);
			String json = "{\"order_id\": \""+order_id+"\"}";
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", json);
			// 正常返回
			OrderInfo orderInfo = null;
			JSONObject info = result.getJSONObject("order");
			orderInfo = (OrderInfo)JSONObject.toJavaObject(info, OrderInfo.class);
			return orderInfo;
		}
		return null;
	}
	
	/**
	 * 根据订单状态/创建时间获取订单详情
	 * @param orderPara
	 * @return
	 */
	public static List<OrderInfo> getByFilter(String newAccessToken, OrderPara orderPara) {
		if (newAccessToken != null) {
			String requestUrl = getfilter_order_url.replace("ACCESS_TOKEN", newAccessToken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(orderPara));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "GET", obj.toString());
			// 正常返回
			List<OrderInfo> orderInfos = null;
			JSONArray info = result.getJSONArray("order_list");
			orderInfos = JSONHelper.toList(info, OrderInfo.class);
			return orderInfos;
		}
		return null;
	}
	
	/**
	 * 设置订单发货信息
	 * @param orderDelivery
	 * @return
	 */
	public static OrderRtnInfo doDelivery(String newAccessToken, OrderDelivery orderDelivery) {
		if (newAccessToken != null) {
			String requestUrl = setdelivery_order_url.replace("ACCESS_TOKEN", newAccessToken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(orderDelivery));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			OrderRtnInfo orderRtnInfo = (OrderRtnInfo)JSONObject.toJavaObject(result, OrderRtnInfo.class);
			return orderRtnInfo;
		}
		return null;
	}
	
	/**
	 * 关闭订单
	 * @param order_id
	 * @return
	 */
	public static OrderRtnInfo doCloseOrder(String newAccessToken, String order_id) {
		if (newAccessToken != null) {
			String requestUrl = close_order_url.replace("ACCESS_TOKEN", newAccessToken);
			String json = "{\"order_id\": \""+order_id+"\"}";
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", json);
			OrderRtnInfo orderRtnInfo = (OrderRtnInfo)JSONObject.toJavaObject(result, OrderRtnInfo.class);
			return orderRtnInfo;
		}
		return null;
	}
}
 