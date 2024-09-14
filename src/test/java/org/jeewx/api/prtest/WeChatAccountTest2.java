package org.jeewx.api.prtest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeecg.dingtalk.api.base.JdtBaseAPI;
import com.jeecg.dingtalk.api.user.vo.User;
import com.jeecg.qywx.api.menu.vo.Menu;
import org.jeewx.api.ai.JwAIApi;
import org.jeewx.api.ai.model.Voice;
import org.jeewx.api.core.common.AccessToken;
import org.jeewx.api.core.common.JSONHelper;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.model.WeixinReqParam;
import org.jeewx.api.core.req.model.menu.PersonalizedMenu;
import org.jeewx.api.core.req.model.menu.WeixinButton;
import org.jeewx.api.core.req.model.menu.WeixinMenuMatchrule;
import org.jeewx.api.core.req.model.menu.config.CustomWeixinButtonConfig;
import org.jeewx.api.core.req.model.message.IndustryTemplateMessageSend;
import org.jeewx.api.core.req.model.message.TemplateData;
import org.jeewx.api.core.req.model.message.TemplateMessage;
import org.jeewx.api.core.req.model.user.Group;
import org.jeewx.api.core.req.model.user.GroupCreate;
import org.jeewx.api.core.util.WeiXinReqUtil;
import org.jeewx.api.wxaccount.JwAccountAPI;
import org.jeewx.api.wxaccount.model.WxQrcode;
import org.jeewx.api.wxbase.wxmedia.JwMediaAPI;
import org.jeewx.api.wxbase.wxmedia.model.*;
import org.jeewx.api.wxbase.wxserviceip.JwServiceIpAPI;
import org.jeewx.api.wxmenu.JwMenuAPI;
import org.jeewx.api.wxmenu.JwPersonalizedMenuAPI;
import org.jeewx.api.wxsendmsg.JwSendMessageAPI;
import org.jeewx.api.wxsendmsg.JwTemplateMessageAPI;
import org.jeewx.api.wxsendmsg.model.WxArticle;
import org.jeewx.api.wxsendmsg.model.WxArticlesResponse;
import org.jeewx.api.wxsendmsg.model.WxMediaResponse;
import org.jeewx.api.wxuser.group.JwGroupAPI;
import org.jeewx.api.wxuser.tag.JwTagAPI;
import org.jeewx.api.wxuser.tag.model.WxTag;
import org.jeewx.api.wxuser.tag.model.WxTagUser;
import org.jeewx.api.wxuser.user.JwUserAPI;
import org.jeewx.api.wxuser.user.model.Wxuser;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
* @Description: 公众号测试
* 测试用例 敲敲云公众号
*
* @author: wangshuai
* @date: 2024/9/11 上午10:45
*/
public class WeChatAccountTest2 {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(WeChatAccountTest2.class);
    private static String appid = "wx9ad69ea024e8ee18";
    private static String appscret = "??";

    //============= 发送模板消息 ===========================================

    /**
     * 添加模板消息 （此接口已经废弃）
     */
    @Test
    public void addTemplateMessageSend(){
        try {
            String s = JwTemplateMessageAPI.addTemplate(getAccessToken(), "4m3vrpiSA-CPyL9YqHw2jKDlZSX6Sz65SoMKvA9BV1s");
            log.info("~~~~~~~~~~添加模板消息~~~~~~~~~~"+s);
        } catch (WexinReqException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 发送模板消息
     */
    @Test
    public void sendTemplateMessageSend(){
        IndustryTemplateMessageSend industryTemplateMessageSend = new IndustryTemplateMessageSend();
        industryTemplateMessageSend.setAccess_token(getAccessToken());
        industryTemplateMessageSend.setTemplate_id("4m3vrpiSA-CPyL9YqHw2jKDlZSX6Sz65SoMKvA9BV1s");
        industryTemplateMessageSend.setTouser("oKMOd6TU0cOqlQalYtGCPD5jWfY8");
        industryTemplateMessageSend.setUrl("www.baidu.com");
        industryTemplateMessageSend.setTopcolor("#ffAADD");
        TemplateMessage data = new TemplateMessage();
        TemplateData first = new TemplateData();
        first.setColor("#173177");
        first.setValue("恭喜你购买成2323功！");
        TemplateData keynote1= new TemplateData();
        keynote1.setColor("#173177");
        keynote1.setValue("巧克22力");

        TemplateData keynote2= new TemplateData();
        keynote2.setColor("39.8元");
        keynote2.setValue("恭喜你购买成功！");

        TemplateData keynote3= new TemplateData();
        keynote3.setColor("#173177");
        keynote3.setValue("2014年9月16日");

        TemplateData remark= new TemplateData();
        remark.setColor("#173177");
        remark.setValue("欢迎再次购买！");
        data.setFirst(first);
        data.setKeynote1(keynote1);
        data.setKeynote2(keynote2);
        data.setKeynote3(keynote3);
        data.setRemark(remark);
        industryTemplateMessageSend.setData(data);

        try {
            String s = JwTemplateMessageAPI.sendTemplateMsg(industryTemplateMessageSend);
            log.info("~~~~~~~~~~发送模板消息~~~~~~~~~~"+s);
        } catch (WexinReqException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void getToken(){
        System.out.println(getAccessToken());
    }
    
    public String getAccessToken() {
        return new AccessToken(appid, appscret).getNewAccessToken();
    }

    /**
     * 创建二维码
     */
    @Test
    public void createQrcode(){
        try {
            WxQrcode qrScene = JwAccountAPI.createQrcode(getAccessToken(), "", "QR_SCENE", "1800");
            log.info("~~~~~~~~~~创建二维码~~~~~~~~~~" + qrScene.getUrl());
        } catch (WexinReqException e) {
            throw new RuntimeException(e);
        }
    }
    //=============================== 智能语音翻译 ==========================
    @Test
    public void tranVoiceText(){
        String file = "C:\\Users\\Administrator\\Desktop\\image\\1.mp3";
        String accessToken = getAccessToken();
        String voice_id = "ceshi1230981fr4";
        String lang = "zh_CN";
        Voice voice = new Voice(accessToken, "mp3",voice_id, lang, file);
        String voiceContent = JwAIApi.translateVoice(voice);
        log.info("~~~~~翻译语音~~~~~"+voiceContent);
    }

    @Test
    public void tranText(){
        String accessToken = getAccessToken();
        String lfrom = "zh_CN",lto = "en_US";
        String text = "我是中国人啊";
        String s = JwAIApi.translateText(accessToken, lfrom, lto, text);
        log.info("~~~~~翻译文本~~~~~"+s);
    }

    @Test
    public void getServiceIpList(){
        try {
            List<String> serviceIpList = JwServiceIpAPI.getServiceIpList(getAccessToken());
            log.info("ip集合：：：" + JSONObject.toJSONString(serviceIpList));
        } catch (WexinReqException e) {
            throw new RuntimeException(e);
        }
    }
    //=============================== 素材 =================================

    /**
     * 获取素材数量
     */
    @Test
    public void getMediaCount(){
        try {
            WxCountResponse mediaCount = JwMediaAPI.getMediaCount(getAccessToken());
            log.info("~~~~~~~~~~~~获取素材数量~~~~~~~~~~~"+JSONObject.toJSONString(mediaCount));
        } catch (WexinReqException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 上传临时素材
     */
    @Test
    public void uploadMedia(){
        try {
            WxMediaResponse wxMediaResponse = JwSendMessageAPI.uploadMediaFile(getAccessToken(), "C://Users/Administrator/Desktop/image/", "logo.png", "image");
            log.info("~~~~~~~~~~~~临时素材返回结果~~~~~~~~~~~"+JSONObject.toJSONString(wxMediaResponse));
        } catch (WexinReqException e) {
            throw new RuntimeException(e);
        }
    }  
    
    /**
     * 下载素材
     */
    @Test
    public void downMedia(){
        try {
            WxDwonload wxDwonload = JwMediaAPI.downMedia(getAccessToken(), "Vdok_lDhoEEqi49K73_ul98p8mvRf6ijiR6OLGRSQYlKaydksfKUJ4ILL439b43E", "C://Users/Administrator/Desktop/image/");
            log.info("~~~~~~~~~~~~临时素材下载结果~~~~~~~~~~~"+JSONObject.toJSONString(wxDwonload));
        } catch (WexinReqException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 获取着指定素材
     */
    @Test
    public void getArticlesByMaterial(){
        try {
            //图片
            //WxNewsArticle wxNewsArticle = JwMediaAPI.getArticlesByMaterialNews(getAccessToken(), "69fCQC8lI3S1aU3solmpeB5hnqXu3r5OlQCHeUiE-0j2dnG6jALBLhpQIT6g1FQk");
            //视频
            WxNewsArticle wxNewsArticle = JwMediaAPI.getArticlesByMaterialNews(getAccessToken(), "69fCQC8lI3S1aU3solmpeJ8UacWsBRjyuztecwynv7UnH8-K14g67LmuSvoirB2l");
            //语音
            //WxNewsArticle wxNewsArticle = JwMediaAPI.getArticlesByMaterialNews(getAccessToken(), "69fCQC8lI3S1aU3solmpeOF4BnQlr-B0pSjPxnooPpxuFfAMgB4HlzuIxQzm4t4x");
            log.info("~~~~~~~~~~~~获取指定素材~~~~~~~~~~~"+JSONObject.toJSONString(wxNewsArticle));
        } catch (WexinReqException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取素材列表
     */
    @Test
    public void queryArticlesByMaterialNews(){
        try {
            WxNews image = JwMediaAPI.queryArticlesByMaterialNews(getAccessToken(), "video", 0, 10);
            log.info("~~~~~~~~~~~~获取素材列表~~~~~~~~~~~"+JSONObject.toJSONString(image));
        } catch (WexinReqException e) {
            throw new RuntimeException(e);
        }
    } 
    
    /**
     * 新增永久素材图片
     */
    @Test
    public void uploadImages() throws WexinReqException {
        List<WxArticle> wxArticles = new ArrayList<>();
        WxArticle wxArticle = new WxArticle();
        wxArticle.setFileName("敲敲云");
        wxArticle.setFilePath("C:\\Users\\Administrator\\Desktop\\image\\jeecg.jpg");
        wxArticles.add(wxArticle);
        WxArticlesResponse wxArticlesResponse = JwMediaAPI.uploadArticlesByMaterialNews(getAccessToken(), wxArticles,"image");
        log.info("~~~~~~~~~~~~上传永久素材图片~~~~~~~~~~~"+JSONObject.toJSONString(wxArticlesResponse));
    }

    /**
     * 新增永久素材视频
     */
    @Test
    public void uploadVideo() throws WexinReqException {
        List<WxArticle> wxArticles = new ArrayList<>();
        WxArticle wxArticle = new WxArticle();
        wxArticle.setTitle("测试");
        wxArticle.setFilePath("C:\\Users\\Administrator\\Desktop\\image\\1.mp4");
        wxArticle.setContent("测试");
        wxArticles.add(wxArticle);
        WxArticlesResponse wxArticlesResponse = JwMediaAPI.uploadArticlesByMaterialNews(getAccessToken(), wxArticles,"video");
        log.info("~~~~~~~~~~~~上传永久素材视频~~~~~~~~~~~"+JSONObject.toJSONString(wxArticlesResponse));
    }    
    
    /**
     * 新增永久素材语音
     */
    @Test
    public void uploadVoice() throws WexinReqException {
        List<WxArticle> wxArticles = new ArrayList<>();
        WxArticle wxArticle = new WxArticle();
        wxArticle.setTitle("测试");
        wxArticle.setFilePath("C:\\Users\\Administrator\\Desktop\\image\\1.mp3");
        wxArticle.setContent("测试");
        wxArticles.add(wxArticle);
        WxArticlesResponse wxArticlesResponse = JwMediaAPI.uploadArticlesByMaterialNews(getAccessToken(), wxArticles,"voice");
        log.info("~~~~~~~~~~~~上传永久素材语音~~~~~~~~~~~"+JSONObject.toJSONString(wxArticlesResponse));
    }

    /**
     * 删除永久素材
     */
    @Test
    public void deleteArticlesByMaterialNews(){
        try {
            log.info("~~~~~~~~~~~~删除永久素材~~~~~~~~~~~");
            JwMediaAPI.deleteArticlesByMaterialNews(getAccessToken(), "69fCQC8lI3S1aU3solmpeCVf32ajWHDG0E-5faBWG51V-_F8sl7IPacA1iiTuYMV");
        } catch (WexinReqException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    //=========================微信转换参数工具类=================================
    @Test
    public void WeiXinToParam(){
        WeixinReqParam reqParam = new WeixinReqParam();
        Map weixinReqParam = WeiXinReqUtil.getWeixinReqParam(reqParam);
        log.info("~~~~~~~~~~转成map~~~~~~~~~~~~~~"+JSONObject.toJSONString(weixinReqParam));
        String str = WeiXinReqUtil.getWeixinParamJson(reqParam);
        log.info("~~~~~~~~~~str~~~~~~~~~~~~~~"+str);
    }
    //=========================JSON 转换工具类=================================
    @Test
    public void toJson(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","张三");
        log.info(JSONHelper.bean2json(map));
        List<String> list = new ArrayList<>();
        list.add("list集合1");
        list.add("list集合2");
        log.info("list转json："+JSONHelper.toJSONString(list));
        log.info("map转json："+JSONHelper.toJSONString(map));
        log.info("map转JSONObject："+JSONHelper.toJSONObject(map));
        List<User> menuList = new ArrayList<>();
        User user = new User();
        user.setName("李四");
        menuList.add(user);
        log.info("将对象转换为List<Map<String,Object>>："+JSONHelper.toList(menuList));
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","张三");
        jsonArray.add(jsonObject);
        log.info("将JSON对象转换为传入类型的对象："+JSONHelper.toList(jsonArray,User.class));
        log.info("将对象转换为传入类型的List："+JSONHelper.toList(menuList,User.class));
        log.info("将JSONObject转换为传入类型的List："+JSONHelper.toBean(jsonObject,User.class));
        log.info("将map转换为传入类型的List："+JSONHelper.toBean(map,User.class));
        
    }

    /**
     * 将JSON文本反序列化为主从关系的实体
     */
    @Test
    public void jsonToEntity(){
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","张三");
        jsonObject.put("mobile","17610623333");
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name",jsonArray);
        String user = jsonObject1.toJSONString();
        User name = JSONHelper.toBean(user, User.class, "name", User.class);
        log.info(JSONHelper.toJSONString(name));
        JSONArray jsonArrayTwo = new JSONArray();
        JSONObject jsonObjectTwoa = new JSONObject();
        jsonObjectTwoa.put("name","张三");
        jsonArrayTwo.add(jsonObjectTwoa);
        JSONArray jsonArrayTwo1 = new JSONArray();
        JSONObject jsonObjectTwoa1 = new JSONObject();
        jsonObjectTwoa1.put("mobile","17777777777");
        jsonArrayTwo1.add(jsonObjectTwoa1);
        JSONObject jsonObjectTwo = new JSONObject();
        jsonObjectTwo.put("name",jsonArrayTwo);
        jsonObjectTwo.put("mobile",jsonArrayTwo1);
        String userTwo = jsonObjectTwo.toJSONString();
        User bean = JSONHelper.toBean(userTwo, User.class, "name", User.class, "mobile", User.class);
        log.info(JSONHelper.toJSONString(bean));
    }
    
    @Test
    public void jsonToEntity1(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","张三");
        jsonObject.put("mobile","17610623333");
        String user = jsonObject.toJSONString();
        HashMap<String, Class> detailClass = new HashMap<>();
        detailClass.put("user",User.class);
        User bean = JSONHelper.toBean(user, User.class, detailClass);
        log.info(JSONHelper.toJSONString(detailClass));
        log.info(JSONHelper.toJSONString(bean)); 
    }
    public String getAccessTokenCx() {
       return JdtBaseAPI.getAccessToken("wx8bf1a0e77c68b066","??").getAccessToken();
    }

}
