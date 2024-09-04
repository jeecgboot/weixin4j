package org.jeewx.api.report.datastatistics.interfacesummary;

import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.common.WxstoreUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwInterfaceSummary {
	
	private static Logger logger = LoggerFactory.getLogger(JwInterfaceSummary.class);
	
	/**
	 * 获取接口分析数据
	 */
	private static final String getinterfacesummary = "https://api.weixin.qq.com/datacube/getinterfacesummary?access_token=ACCESS_TOKEN";

	/**
	 * 获取接口分析数据（最大时间跨度：30）
	 * @param accessToken 调用接口凭证
	 * @param beginDate 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
	 * @param endDate 获取数据的结束日期，end_date允许设置的最大值为昨日
	 * @return
	 * 		参数	说明
	 *<br>	ref_date	数据的日期
	 *<br>	ref_hour	数据的小时
	 *<br>	callback_count	通过服务器配置地址获得消息后，被动回复用户消息的次数
	 *<br>	fail_count	上述动作的失败次数
	 *<br>	total_time_cost	总耗时，除以callback_count即为平均耗时
	 *<br>	max_time_cost	最大耗时
	 */
	public static JSONObject getinterfacesummary(String accessToken, String beginDate, String endDate){
		if(accessToken==null){
			logger.error("传入的accessToken为空");
			return null;
		}
		JSONObject param = new JSONObject();
		param.put("access_token", accessToken);
		param.put("begin_date", beginDate);
		param.put("end_date", endDate);
		logger.info("--接口分析数据请求参数--"+param.toString());
		JSONObject json = WxstoreUtils.httpRequest(getinterfacesummary, "POST", param.toString());
		logger.info("--接口分析数据结果返回--"+json.toString());
		return json;
	}
}
