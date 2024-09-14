package org.jeewx.api.custservice.multicustservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.common.JSONHelper;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.custservice.multicustservice.model.ChatRecord;
import org.jeewx.api.custservice.multicustservice.model.CustService;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 客户服务- 多客户
 * 
 * @author caojm
 */
public class JwMultiCustomerAPI {

    //转发多客户端类型
    private final static String TRANSFER_CUSTOMER_SERVICE = "transfer_customer_service";
    
    // 获取在线客服接待信息
    private static String GET_ONLINE_CUSTSEVICE_URL = "https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist?access_token=ACCESS_TOKEN";
    
    //获取客服记录列表
    private static String GET_CUSTSEVICE_ROCORD_LIST_URL = "https://api.weixin.qq.com/cgi-bin/customservice/getrecord?access_token=ACCESS_TOKEN";

    /**
     * 获取转发多客服的响应消息
     * @param touser
     * @param fromuser
     * @return
     */
    public String getMultiCustServcieMessage(String toUserName, String fromUserName) {
        StringBuilder custServiceMessage = new StringBuilder();  
        custServiceMessage.append("<xml>");  
        custServiceMessage.append("<ToUserName><![CDATA["+toUserName+"]]></ToUserName>");  
        custServiceMessage.append("<FromUserName><![CDATA["+fromUserName+"]]></FromUserName>");  
        custServiceMessage.append("<CreateTime>"+new Date().getTime()+"</CreateTime>");  
        custServiceMessage.append("<MsgType><![CDATA["+TRANSFER_CUSTOMER_SERVICE+"]]></MsgType>"); 
        custServiceMessage.append("</xml>");  
        return custServiceMessage.toString(); 
    }
    
    /**
     * 获取指定客服的响应消息
     * @param accessToken
     * @param toUserName
     * @param fromUserName
     * @param kfAccount
     * @return
     */
    public String getSpecCustServcie(String accessToken,String toUserName,String fromUserName,String kfAccount) {
        if(isOnlineCustServiceAvailable(accessToken,kfAccount)){
            StringBuilder custServiceMessage = new StringBuilder();  
            custServiceMessage.append("<xml>");  
            custServiceMessage.append("<ToUserName><![CDATA["+toUserName+"]]></ToUserName>");  
            custServiceMessage.append("<FromUserName><![CDATA["+fromUserName+"]]></FromUserName>");  
            custServiceMessage.append("<CreateTime>"+new Date().getTime()+"</CreateTime>");  
            custServiceMessage.append("<MsgType><![CDATA["+TRANSFER_CUSTOMER_SERVICE+"]]></MsgType>"); 
            custServiceMessage.append("<TransInfo>");  
            custServiceMessage.append("<KfAccount><![CDATA["+kfAccount+"]]></KfAccount>");
            custServiceMessage.append("</TransInfo>");  
            custServiceMessage.append("</xml>");  
            return custServiceMessage.toString();             
        }else{
            return null;
        }

    }
    
    /**
     * 判断指定客服是否在线可用
     * @param accessToken
     * @param kfAccount
     * @return
     */
    public boolean isOnlineCustServiceAvailable(String accessToken,String kfAccount) {
        List<CustService> custServices = null;
        if (accessToken != null) {
            String requestUrl = GET_ONLINE_CUSTSEVICE_URL.replace("ACCESS_TOKEN", accessToken);
            JSONObject result = WxstoreUtils.httpRequest(requestUrl, "GET", null);
            if(result != null){
                JSONArray info = result.getJSONArray("kf_online_list");
                custServices = JSONHelper.toList(info, CustService.class);
            }
        }
       
        if(custServices!=null&&!custServices.isEmpty()){
            for(Iterator<CustService> it = custServices.iterator();it.hasNext();){
                CustService custService = (CustService)it.next();
                //不在线、没有开启自动接入或者自动接入已满,都返回不可用
                //不再返回自动接入参数
                if (custService != null && custService.getKfAccount().equals(kfAccount)
                        && custService.getStatus() > 0){
                    return true;
                }
            }
        }

        return false;
        
    }

    /**
     * 获取客服聊天记录列表
     * @param accessToken
     * @param openId
     * @param startTime
     * @param endTime
     * @param pageSize
     * @param pageIndex
     * @return
     */
    public List<ChatRecord> getCustServiceRecordList(String accessToken,String openId,long startTime,long endTime,int pageSize,int pageIndex) {
        List<ChatRecord> chatRecods = null;
        if (accessToken != null && startTime >= 0 && endTime >=0 && pageSize>=0 && pageIndex>0 ) {
            String requestUrl = GET_CUSTSEVICE_ROCORD_LIST_URL.replace("ACCESS_TOKEN", accessToken);
            StringBuilder jsonStrBuilder = new StringBuilder(); 
            
            jsonStrBuilder.append("{");  
            jsonStrBuilder.append("\"starttime\":"+startTime+",");  
            jsonStrBuilder.append("\"endtime\":"+endTime+",");  
            if(openId!=null && !openId.equals("")){
                jsonStrBuilder.append("\"openid\":\""+openId+"\",");     
            }
            jsonStrBuilder.append("\"pagesize\":"+pageSize+",");  
            jsonStrBuilder.append("\"pageindex\":"+pageIndex+",");  
            jsonStrBuilder.append("}");              
            
            JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", jsonStrBuilder.toString());
            if(result != null){
                JSONArray info = result.getJSONArray("recordlist");
                chatRecods = JSONHelper.toList(info, ChatRecord.class);
            }
            return chatRecods;
        }
        return null;
    }
}
