package org.jeewx.api.wxsendmsg;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.WeiXinReqService;
import org.jeewx.api.core.req.model.message.AutoReplyRuleGet;
import org.jeewx.api.core.util.WeiXinConstant;
import org.jeewx.api.wxsendmsg.model.WxArticleConfig;
import org.jeewx.api.wxsendmsg.model.auto.AutoReplyInfoRule;
import org.jeewx.api.wxsendmsg.model.auto.KeyWordAutoReplyInfo;
import org.jeewx.api.wxsendmsg.model.auto.KeywordListInfo;
import org.jeewx.api.wxsendmsg.model.auto.ReplyListInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取自动回复规则
 * @author luobaoli
 *
 */
public class JwGetAutoReplyRuleAPI {
	private static Logger logger = LoggerFactory.getLogger(JwGetAutoReplyRuleAPI.class);
	
	/**
	 * 获取自动回复规则
	 * @param accessToken
	 * @return
	 */
	public static AutoReplyInfoRule getAutoReplyInfoRule(String accessToken) throws WexinReqException{
		AutoReplyRuleGet arr = new AutoReplyRuleGet();
		arr.setAccess_token(accessToken);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(arr);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		
//		AutoReplyInfoRule autoReplyInfoRule = (AutoReplyInfoRule) JSONObject.toJavaObject(result,new CustomJsonConfig(AutoReplyInfoRule.class, "keyword_autoreply_info"));
		SimplePropertyPreFilter keywordFilter = new SimplePropertyPreFilter();
		keywordFilter.getExcludes().add("keyword_autoreply_info");
		AutoReplyInfoRule autoReplyInfoRule = JSONObject.parseObject(JSONObject.toJSONString(result, keywordFilter), AutoReplyInfoRule.class);
		JSONObject keywordAutoReplyInfoJsonObj = result.getJSONObject("keyword_autoreply_info");
		if(keywordAutoReplyInfoJsonObj!=null && !keywordAutoReplyInfoJsonObj.isEmpty()){
			/**关键词自动回复的信息 */
			JSONArray keywordAutoReplyInfos =  keywordAutoReplyInfoJsonObj.getJSONArray("list");
			if(keywordAutoReplyInfos!=null){
				List<KeyWordAutoReplyInfo> listKeyWordAutoReplyInfo = new ArrayList<KeyWordAutoReplyInfo>();
				for(int i=0;i<keywordAutoReplyInfos.size();i++){
//					KeyWordAutoReplyInfo keyWordAutoReplyInfo = (KeyWordAutoReplyInfo) JSONObject.toJavaObject(keywordAutoReplyInfos.getJSONObject(i),new CustomJsonConfig(KeyWordAutoReplyInfo.class, new String[]{"keyword_list_info","reply_list_info"}));
					SimplePropertyPreFilter keywordReplyFilter = new SimplePropertyPreFilter();
					keywordReplyFilter.getExcludes().add("keyword_list_info");
					keywordReplyFilter.getExcludes().add("reply_list_info");
					KeyWordAutoReplyInfo keyWordAutoReplyInfo = JSONObject.parseObject(JSONObject.toJSONString(keywordAutoReplyInfos.getJSONObject(i), keywordReplyFilter), KeyWordAutoReplyInfo.class);
					/**处理关键词列表 */
					JSONArray keywordListInfos = keywordAutoReplyInfos.getJSONObject(i).getJSONArray("keyword_list_info");
					if(keywordListInfos!=null){
						List<KeywordListInfo> listKeywordListInfo = new ArrayList<KeywordListInfo>();
						for(int j=0;j<keywordListInfos.size();j++){
							KeywordListInfo keywordListInfo = (KeywordListInfo) JSONObject.toJavaObject(keywordListInfos.getJSONObject(j),KeywordListInfo.class);
							listKeywordListInfo.add(keywordListInfo);
						}
						keyWordAutoReplyInfo.setKeyword_list_info(listKeywordListInfo);
					}
					
					/**处理关键字回复信息 */
					JSONArray replyListInfos = keywordAutoReplyInfos.getJSONObject(i).getJSONArray("reply_list_info");
					if(replyListInfos!=null){
						List<ReplyListInfo> listReplyListInfo = new ArrayList<ReplyListInfo>();
						for(int j=0;j<replyListInfos.size();j++){
//							ReplyListInfo replyListInfo = (ReplyListInfo) JSONObject.toJavaObject(keywordListInfos.getJSONObject(j),new CustomJsonConfig(ReplyListInfo.class, "news_info"));
							SimplePropertyPreFilter newsInfoFilter = new SimplePropertyPreFilter();
							newsInfoFilter.getExcludes().add("news_info");
							ReplyListInfo replyListInfo = JSONObject.parseObject(JSONObject.toJSONString(keywordListInfos.getJSONObject(j),newsInfoFilter), ReplyListInfo.class);
							/**处理关键字回复相关图文消息 */
							JSONObject newsInfoJsonObj = replyListInfos.getJSONObject(j).getJSONObject("news_info");
							if(newsInfoJsonObj!=null && !newsInfoJsonObj.isEmpty()){
								JSONArray newsInfos = newsInfoJsonObj.getJSONArray("list");
								List<WxArticleConfig> listNewsInfo = new ArrayList<WxArticleConfig>();
								for (int k = 0; k < newsInfos.size(); k++) {
									WxArticleConfig wxArticleConfig = (WxArticleConfig) JSONObject.toJavaObject(newsInfos.getJSONObject(k), WxArticleConfig.class);
									listNewsInfo.add(wxArticleConfig);
								}
								replyListInfo.setNews_info(listNewsInfo);
							}
							listReplyListInfo.add(replyListInfo);
						}
						keyWordAutoReplyInfo.setReply_list_info(listReplyListInfo);
					}
					
					listKeyWordAutoReplyInfo.add(keyWordAutoReplyInfo);
				}
				autoReplyInfoRule.setKeyword_autoreply_info(listKeyWordAutoReplyInfo);
			}
		}
		
		return autoReplyInfoRule;
	}
	
	public static void main(String[] args) {
		try {
			//String s = JwTokenAPI.getAccessToken("wx298c4cc7312063df","fbf8cebf983c931bd7c1bee1498f8605");
			String s = "chsqpXVzXmPgqgZrrZnQzxqEi2L-1qStuVDOeZ-hKlY-Gkdlca3Q2HE9__BXc5hNoU1Plpc56UyZ1QoaDMkRbVSi0iUUVb27GTMaTDBfmuY";
			JwGetAutoReplyRuleAPI.getAutoReplyInfoRule(s);
		} catch (WexinReqException e) {
			e.printStackTrace();
		}
	}
}
