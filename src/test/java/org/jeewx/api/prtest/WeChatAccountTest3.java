package org.jeewx.api.prtest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeecg.dingtalk.api.base.JdtBaseAPI;
import com.jeecg.dingtalk.api.user.vo.User;
import org.jeewx.api.ai.JwAIApi;
import org.jeewx.api.ai.model.Voice;
import org.jeewx.api.core.common.AccessToken;
import org.jeewx.api.core.common.JSONHelper;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.model.WeixinReqParam;
import org.jeewx.api.core.req.model.interfacesummary.InterfaceSummaryParam;
import org.jeewx.api.core.req.model.message.IndustryTemplateMessageSend;
import org.jeewx.api.core.req.model.message.TemplateData;
import org.jeewx.api.core.req.model.message.TemplateMessage;
import org.jeewx.api.core.util.WeiXinReqUtil;
import org.jeewx.api.custservice.multicustservice.JwMultiCustomerAPI;
import org.jeewx.api.custservice.multicustservice.model.ChatRecord;
import org.jeewx.api.extend.CustomJsonConfig;
import org.jeewx.api.report.datastatistics.graphicanalysis.JwGraphicAnalysisAPI;
import org.jeewx.api.report.datastatistics.graphicanalysis.model.GraphicAnalysisRtnInfo;
import org.jeewx.api.report.datastatistics.interfacesummary.JwInterfaceSummary;
import org.jeewx.api.report.datastatistics.useranalysis.JwUserAnalysisAPI;
import org.jeewx.api.report.datastatistics.useranalysis.model.UserAnalysisRtnInfo;
import org.jeewx.api.report.interfacesummary.JwInterfaceSummaryAPI;
import org.jeewx.api.report.interfacesummary.model.InterfaceSummary;
import org.jeewx.api.report.interfacesummary.model.InterfaceSummaryHour;
import org.jeewx.api.wxaccount.JwAccountAPI;
import org.jeewx.api.wxaccount.model.WxQrcode;
import org.jeewx.api.wxbase.wxmedia.JwMediaAPI;
import org.jeewx.api.wxbase.wxmedia.model.WxCountResponse;
import org.jeewx.api.wxbase.wxmedia.model.WxDwonload;
import org.jeewx.api.wxbase.wxmedia.model.WxNews;
import org.jeewx.api.wxbase.wxmedia.model.WxNewsArticle;
import org.jeewx.api.wxbase.wxserviceip.JwServiceIpAPI;
import org.jeewx.api.wxsendmsg.JwSendMessageAPI;
import org.jeewx.api.wxsendmsg.JwTemplateMessageAPI;
import org.jeewx.api.wxsendmsg.model.WxArticle;
import org.jeewx.api.wxsendmsg.model.WxArticlesResponse;
import org.jeewx.api.wxsendmsg.model.WxMediaResponse;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Description: 公众号测试
* 测试用例 敲敲云公众号
*
* @author: wangshuai
* @date: 2024/9/11 上午10:45
*/
public class WeChatAccountTest3 {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(WeChatAccountTest3.class);
    private static String appid = "wx9ad69ea024e8ee18";
    private static String appscret = "??";
    private static String openId = "oKMOd6TU0cOqlQalYtGCPD5jWfY8";

  
    
    @Test
    public void getToken(){
        System.out.println(getAccessToken());
    }

    //============= 过滤不需要转换的属性 ===========================================
    @Test
    public void filterTr(){
        CustomJsonConfig customJsonConfig = new CustomJsonConfig();
    }
    
    //=================== 客服 ==================
    @Test
    public void kefu(){
        JwMultiCustomerAPI api = new JwMultiCustomerAPI();
        boolean a = api.isOnlineCustServiceAvailable(getAccessToken(), "kf2001@qiaoqiaoyun1");
        log.info("~~~~~判断指定客服是否在线可用~~~~~"+a);
        String servcieMessage = api.getMultiCustServcieMessage(openId, appid);
        log.info("~~~~~获取转发多客服的响应消息~~~~~"+servcieMessage);
        String specCustServcie = api.getSpecCustServcie(getAccessToken(), openId, appid, "kf2001@qiaoqiaoyun1");
        log.info("~~~~~获取指定客服的响应消息~~~~~"+specCustServcie);
        List<ChatRecord> custServiceRecordList = api.getCustServiceRecordList(getAccessToken(), openId, 1726134970276L, 1726134963000L, 10, 0);
        log.info("~~~~~获取客服消息~~~~~"+JSONObject.toJSONString(custServiceRecordList));
    }
    //====================获取报表信息====================================

    /**
     * 获取接口分析分时数据
     */
    @Test
    public void getInterfaceSummaryHour(){
        JwInterfaceSummaryAPI s = new JwInterfaceSummaryAPI();
        InterfaceSummaryParam param=new InterfaceSummaryParam();
        param.setBegin_date("2024-09-11");
        param.setEnd_date("2024-09-11");
        //小时
        List<InterfaceSummaryHour> list = s.getInterfaceSummaryHour(getAccessToken(),param);
        log.info("~~~~~~~~~~获取接口分析分时数据~~~~~~~~~~~~"+JSONObject.toJSONString(list));
        //全天
        List<InterfaceSummary> interfaceSummary = s.getInterfaceSummary(getAccessToken(), param);
        log.info("~~~~~~~~~~获取接口分析数据~~~~~~~~~~~~"+JSONObject.toJSONString(interfaceSummary));
    }    
    
    
    /**
     * 用户分析数据接口
     */
    @Test
    public void getUserCumulate() throws WexinReqException {
        JwUserAnalysisAPI jua = new JwUserAnalysisAPI();
        List<UserAnalysisRtnInfo> userAnalysisList = jua.getUserSummary(getAccessToken(), "2024-09-08", "2024-09-11");
        log.info("~~~~~~~~~~获取用户增减数据~~~~~~~~~~~~"+JSONObject.toJSONString(userAnalysisList));

        List<UserAnalysisRtnInfo> userCumulate = jua.getUserCumulate(getAccessToken(), "2024-09-08", "2024-09-11");
        log.info("~~~~~~~~~~获取累计用户数据~~~~~~~~~~~~"+JSONObject.toJSONString(userCumulate));
    }  
    
    
    /**
     * 图文分析数据接口
     */
    @Test
    public void getinterfacesummary() throws WexinReqException, UnsupportedEncodingException {
        JwGraphicAnalysisAPI jua = new JwGraphicAnalysisAPI();
        List<GraphicAnalysisRtnInfo> articleSummary = jua.getArticleSummary(getAccessToken(), "2024-09-10", "2024-09-10");
        log.info("~~~~~~~~~~获取图文群发每日数据~~~~~~~~~~~~"+JSONObject.toJSONString(articleSummary));
    }   
    
    /**
     * 获取文章阅读数量
     */
    @Test
    public void getArticleTotal() throws WexinReqException, UnsupportedEncodingException {
        JwGraphicAnalysisAPI jua = new JwGraphicAnalysisAPI();
        List<GraphicAnalysisRtnInfo> articleSummary = jua.getArticleTotal(getAccessToken(), "2024-09-10", "2024-09-10");
        log.info("~~~~~~~~~~获取文章数量~~~~~~~~~~~~"+JSONObject.toJSONString(articleSummary));
    }    
    
    /**
     * 获取用户阅读
     */
    @Test
    public void getUserRead() throws WexinReqException, UnsupportedEncodingException {
        JwGraphicAnalysisAPI jua = new JwGraphicAnalysisAPI();
        List<GraphicAnalysisRtnInfo> articleSummary = jua.getUserRead(getAccessToken(), "2024-09-10", "2024-09-10");
        log.info("~~~~~~~~~~获取用户阅读~~~~~~~~~~~~"+JSONObject.toJSONString(articleSummary));
    }    
    
    /**
     * 获取用户小时阅读
     */
    @Test
    public void getUserReadHour() throws WexinReqException, UnsupportedEncodingException {
        JwGraphicAnalysisAPI jua = new JwGraphicAnalysisAPI();
        List<GraphicAnalysisRtnInfo> articleSummary = jua.getUserReadHour(getAccessToken(), "2024-09-10", "2024-09-10");
        log.info("~~~~~~~~~~获取用户小时~~~~~~~~~~~~"+JSONObject.toJSONString(articleSummary));
    }   
    
    /**
     * 获取用户分享
     */
    @Test
    public void getUserShare() throws WexinReqException, UnsupportedEncodingException {
        JwGraphicAnalysisAPI jua = new JwGraphicAnalysisAPI();
        List<GraphicAnalysisRtnInfo> articleSummary = jua.getUserShare(getAccessToken(), "2024-09-10", "2024-09-10");
        log.info("~~~~~~~~~~获取用户分享~~~~~~~~~~~~"+JSONObject.toJSONString(articleSummary));
    }

    /**
     * 获取用户小时分享
     */
    @Test
    public void getUserShareHour() throws WexinReqException, UnsupportedEncodingException {
        JwGraphicAnalysisAPI jua = new JwGraphicAnalysisAPI();
        List<GraphicAnalysisRtnInfo> articleSummary = jua.getUserShareHour(getAccessToken(), "2024-09-10", "2024-09-10");
        log.info("~~~~~~~~~~获取用户小时分享~~~~~~~~~~~~"+JSONObject.toJSONString(articleSummary));
    }
    
    
    public String getAccessToken() {
        return new AccessToken(appid, appscret).getNewAccessToken();
    }
    
    public String getAccessTokenCx() {
       return JdtBaseAPI.getAccessToken("wx8bf1a0e77c68b066","??").getAccessToken();
    }

}
