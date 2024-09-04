package com.jeecg.qywx.api.conversation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeecg.qywx.api.conversation.vo.BaseMessage;
import com.jeecg.qywx.api.conversation.vo.Conversation;
import com.jeecg.qywx.api.conversation.vo.Conversation4Update;
import com.jeecg.qywx.api.conversation.vo.Mute;
import com.jeecg.qywx.api.core.common.MsgResponse;
import com.jeecg.qywx.api.core.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
/**
 * 
 * @author SunHaiFeng
 * 会话API
 */
public class ConversationAPI {
	private static final Logger logger = LoggerFactory.getLogger(ConversationAPI.class);
	/**创建会话信息URL*/
	private static  String conversation_create_url = "https://qyapi.weixin.qq.com/cgi-bin/chat/create?access_token=ACCESS_TOKEN";
	/**获取会话信息*/
	private static String conversation_get_url = "https://qyapi.weixin.qq.com/cgi-bin/chat/get?access_token=ACCESS_TOKEN&chatid=CHATID";
	/**修改会话信息*/
	private static String conversation_update_url = "https://qyapi.weixin.qq.com/cgi-bin/chat/update?access_token=ACCESS_TOKEN";
	/**退出会话*/
	private static String conversation_quit_url = "https://qyapi.weixin.qq.com/cgi-bin/chat/quit?access_token=ACCESS_TOKEN";
	/**清除未读状态*/
	private static String conversation_clearnotifay_url = "https://qyapi.weixin.qq.com/cgi-bin/chat/clearnotify?access_token=ACCESS_TOKEN";
	/**发送消息URL*/
	private static String conversation_send_url = "https://qyapi.weixin.qq.com/cgi-bin/chat/send?access_token=ACCESS_TOKEN";
	/**设置消息*/
	private static String conversation_setmute_url = "https://qyapi.weixin.qq.com/cgi-bin/chat/setmute?access_token=ACCESS_TOKEN";
	
	/**
	 * 创建会话
	 * @param conversation
	 * @return
	 */
	public static MsgResponse createConversation(Conversation conversation,String accessToken){
		MsgResponse msgResponse = new MsgResponse();
		String errmsg = "ok";
		int result = -1;  
		logger.info("[CREATECONVERSATION]", "createConversation param:conversation:{},accessToken:{}", new Object[]{conversation,accessToken});
	    String url = conversation_create_url.replace("ACCESS_TOKEN", accessToken);  
	    String jsonContent = JSONObject.toJSONString(conversation);  
	    logger.info("[CREATECONVERSATION]", "createConversation param:jsonUser:{}", new Object[]{jsonContent});
	    JSONObject jsonObject = HttpUtil.sendPost(url, jsonContent);
	    logger.info("[CREATECONVERSATION]", "createConversation response:{}", new Object[]{jsonObject.toJSONString()});
	    if (null != jsonObject) {  
	    	int errcode = jsonObject.getIntValue("errcode");
	    	if(jsonObject.containsKey("errmsg")){
	    		errmsg = jsonObject.getString("errmsg");
	    	}
	        result = errcode;
	    }  
	    msgResponse.setErrcode(result);
	    msgResponse.setErrmsg(errmsg);
		return msgResponse;
	}
	
	/**
	 * 获取会话信息
	 * @param chatid 会话ID
	 * @param accessToken TOKEN
	 * @return
	 */
	public static Conversation getConversation(String chatid, String accessToken){
		logger.info("[GETCONVERSATION]", "getConversation param:chatid:{},accessToken:{}", new Object[]{chatid,accessToken});
	    String url = conversation_get_url.replace("ACCESS_TOKEN", accessToken).replace("CHATID", chatid);  
	    JSONObject jsonObject = HttpUtil.sendPost(url); 
	    logger.info("[GETCONVERSATION]", "getConversation response:{}", new Object[]{jsonObject.toJSONString()});
	    if (null != jsonObject) {  
	    	int errcode = jsonObject.getIntValue("errcode");
	        if(errcode==0){
	        	Conversation conversation =	JSONObject.toJavaObject(jsonObject, Conversation.class);
	        	return conversation;
	        }
	    }  
		return null;
	}
	/**
	 * 修改会话信息
	 * @param chatid 会话ID
	 * @param opUser 操作用户ID
	 * @param name 修改为会话名称
	 * @param owner 修改管理员ID
	 * @param addUserlist 新增会话成员
	 * @param delUserlist 删除会话成员
	 * @param accessToken
	 * @return
	 */
	public static MsgResponse updateConversation(Conversation4Update converstation, String accessToken){
		MsgResponse msgResponse = new MsgResponse();
		String errmsg = "ok";
		int result =-1 ;
		String jsonContent = JSONObject.toJSONString(converstation);
		String url = conversation_update_url.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = HttpUtil.sendPost(url, jsonContent);
		try {
			if(jsonObject!=null) {  
		    	int errcode = jsonObject.getIntValue("errcode");
		    	errmsg = jsonObject.getString("errmsg");
		        result = errcode;
		     }  
		     msgResponse.setErrcode(result);
		     msgResponse.setErrmsg(errmsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 return msgResponse;
	}
	/**
	 * 退出会话
	 * @param chatid 会话ID
	 * @param opUser 操作员ID
	 * @param accessToken
	 * @return
	 */
	public static MsgResponse quit(String chatid,String opUser,String accessToken){
		MsgResponse msgResponse = new MsgResponse();
		String errmsg = "ok";
		int result = -1;
		JSONObject jsonContent = new JSONObject();
		jsonContent.put("chatid", chatid);
		jsonContent.put("op_user", opUser);
		String url = conversation_quit_url.replace("ACCESS_TOKEN", accessToken);  
		JSONObject jsonObject = HttpUtil.sendPost(url, jsonContent.toJSONString());
		if(null != jsonObject) {  
	    	int errcode = jsonObject.getIntValue("errcode");
	    	if(jsonObject.containsKey("errmsg")){
	    		errmsg = jsonObject.getString("errmsg");
	    	}
	        result = errcode;
	     }  
	     msgResponse.setErrcode(result);
	     msgResponse.setErrmsg(errmsg);
		return msgResponse;
	}
	
	/**
	 * 清除会话状态
	 * @param chatidOrUserid 会话ID或用户ID
	 * @param type 类型：single或group 即 群聊或单聊
	 * @param opUser 操作人ID
	 * @param accessToken
	 * @return
	 */
	public static MsgResponse clearnotify(String chatidOrUserid,String type,String opUser,String accessToken){
		MsgResponse msgResponse = new MsgResponse();
		String errmsg = "ok";
		int result = -1;
		JSONObject jsonContent = new JSONObject();
		JSONObject chat = new JSONObject();
		chat.put("id", chatidOrUserid);
		chat.put("type", type);
		jsonContent.put("chat", chat);
		jsonContent.put("op_user", opUser);
		String url = conversation_clearnotifay_url.replace("ACCESS_TOKEN", accessToken);  
		JSONObject jsonObject = HttpUtil.sendPost(url,jsonContent.toJSONString()); 
		if(null != jsonObject) {  
	    	int errcode = jsonObject.getIntValue("errcode");
	    	if(jsonObject.containsKey("errmsg")){
	    		errmsg = jsonObject.getString("errmsg");
	    	}
	        result = errcode;
	     }  
	     msgResponse.setErrcode(result);
	     msgResponse.setErrmsg(errmsg);
		return msgResponse;
	}
	/**
	 * 会话中发送消息
	 * @param textMessage 消息对象 发送不同消息，创建不同子类即可
	 * @param accessToken
	 * @return
	 */
	public static MsgResponse sendMessage(BaseMessage message,String accessToken){
		MsgResponse msgResponse = new MsgResponse();
		String errmsg = "ok";
		int result = -1;  
		logger.info("[SENDMESSAGE]", "sendMessage param:conversation:{},accessToken:{}", new Object[]{message,accessToken});
	    String url = conversation_send_url.replace("ACCESS_TOKEN", accessToken);  
	    String jsonContent = JSONObject.toJSONString(message);  
	    logger.info("[SENDMESSAGE]", "sendMessage param:jsonUser:{}", new Object[]{jsonContent});
	    JSONObject jsonObject = HttpUtil.sendPost(url, jsonContent);
	    logger.info("[SENDMESSAGE]", "sendMessage response:{}", new Object[]{jsonObject.toJSONString()});
	    if(null != jsonObject) {  
	    	int errcode = jsonObject.getIntValue("errcode");
	    	if(jsonObject.containsKey("errmsg")){
	    		errmsg = jsonObject.getString("errmsg");
	    	}
	        result = errcode;
	     }  
	     msgResponse.setErrcode(result);
	     msgResponse.setErrmsg(errmsg);
		return msgResponse;
	}
	/**
	 * 设置用户消息
	 * @param userlist
	 * @param accessToken
	 * @return
	 */
	public static MsgResponse setMute(List<Mute> userlist,String accessToken){
		MsgResponse msgResponse = new MsgResponse();
		String errmsg = "ok";
		int result = -1;  
		JSONObject jsonContent  =new JSONObject();
		if(userlist!=null){
			jsonContent.put("user_mute_list", JSONArray.toJSONString(userlist));
		    String url = conversation_setmute_url.replace("ACCESS_TOKEN", accessToken);  
			JSONObject jsonObject = HttpUtil.sendPost(url, jsonContent.toJSONString());
			if(null != jsonObject) {  
		    	int errcode = jsonObject.getIntValue("errcode");
		    	if(jsonObject.containsKey("errmsg")){
		    		errmsg = jsonObject.getString("errmsg");
		    	}
		        result = errcode;
		     }  
		     msgResponse.setErrcode(result);
		     msgResponse.setErrmsg(errmsg);
		}
		return msgResponse;
	}
}
