package org.jeewx.api.prtest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeecg.dingtalk.api.base.JdtBaseAPI;
import org.jeewx.api.core.common.AccessToken;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.model.menu.PersonalizedMenu;
import org.jeewx.api.core.req.model.menu.WeixinButton;
import org.jeewx.api.core.req.model.menu.WeixinMenuMatchrule;
import org.jeewx.api.core.req.model.menu.config.CustomWeixinButtonConfig;
import org.jeewx.api.core.req.model.user.Group;
import org.jeewx.api.core.req.model.user.GroupCreate;
import org.jeewx.api.wxbase.wxtoken.JwTokenAPI;
import org.jeewx.api.wxmenu.JwMenuAPI;
import org.jeewx.api.wxmenu.JwPersonalizedMenuAPI;
import org.jeewx.api.wxuser.group.JwGroupAPI;
import org.jeewx.api.wxuser.tag.JwTagAPI;
import org.jeewx.api.wxuser.tag.model.WxTag;
import org.jeewx.api.wxuser.tag.model.WxTagUser;
import org.jeewx.api.wxuser.user.JwUserAPI;
import org.jeewx.api.wxuser.user.model.Wxuser;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* @Description: 公众号测试  
* 测试用例 敲敲云公众号
* @author: wangshuai
* @date: 2024/9/11 上午10:45
*/
public class WeChatAccountTest {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(WeChatAccountTest.class);
    private static String appid = "wx9ad69ea024e8ee18";
    private static String appscret = "??";

    //============= 获取粉丝 ===========================================
    /**
     * 获取粉丝
     * 
     * @throws WexinReqException
     */
    @Test
    public void getUserInfo() throws WexinReqException {
        String accessToken = this.getAccessToken();
        Wxuser wxusers = JwUserAPI.getWxuser(accessToken, "oKMOd6TU0cOqlQalYtGCPD5jWfY8");
        log.info("~~~~~~~获取粉丝~~~~~~~~~"+JSONObject.toJSONString(wxusers));
        log.info(accessToken);
    }

    /**
     * 获取所有粉丝（一个一个获取，特别慢）
     *
     * @throws WexinReqException
     */
    @Test
    public void getAllUserInfo() throws WexinReqException {
        String accessToken = this.getAccessToken();
        List<Wxuser> allWxuser = JwUserAPI.getAllWxuser(accessToken, null);
        log.info("~~~~~~~获取粉丝列表~~~~~~~~~"+JSONObject.toJSONString(allWxuser));
        log.info(accessToken);
    }
    
    //============= 用户分组 ===========================================
    /**
     * 获取所有分组信息
     * 
     * @throws WexinReqException
     */
    @Test
    public void getAllGroup() throws WexinReqException {
        List<Group> allGroup = JwGroupAPI.getAllGroup(this.getAccessToken());
        System.out.println("~~~~~~~获取所有的分组信息~~~~~~~" + JSONObject.toJSONString(allGroup));
    }

    /**
     * 添加分组
     * @throws WexinReqException
     */
    @Test
    public void addGroup() throws WexinReqException {
        GroupCreate group = JwGroupAPI.createGroup(this.getAccessToken(), "2024分组测试");
        System.out.println("~~~~~~~获取所有的分组信息~~~~~~~" + JSONObject.toJSONString(group));
    }

    /**
     * 更新分组
     * @throws WexinReqException
     */
    @Test
    public void updateGroup() throws WexinReqException {
        String s = JwGroupAPI.updateGroup(this.getAccessToken(), "100", "2024分组测试-更新");
        System.out.println("~~~~~~~更新分组信息~~~~~~~" + s);
    } 
    
    /**
     * 删除分组
     * @throws WexinReqException
     */
    @Test
    public void deleteGroup() throws WexinReqException {
        String s = JwGroupAPI.groupDelete(this.getAccessToken(), "100");
        System.out.println("~~~~~~~删除分组信息~~~~~~~" + s);
    }
    
    
    //============= 标签 ===========================================
    /**
     * 创建标签
     * 
     * @throws WexinReqException
     */
    @Test
    public void createTag() throws WexinReqException {
        JSONObject object = JwTagAPI.createTag(this.getAccessToken(), "测试标签");
        System.out.println("~~~~~~~新增标签~~~~~~~" + object.toJSONString());
    } 
    
    /**
     * 获取标签
     * @throws WexinReqException
     */
    @Test
    public void getTag() {
        List<WxTag> tags = JwTagAPI.getTags(this.getAccessToken());
        System.out.println("~~~~~~~标签列表~~~~~~~" + JSONObject.toJSONString(tags));
    }

    /**
     * 获取标签
     * @throws WexinReqException
     */
    @Test
    public void updateTag(){
        JSONObject jsonObject = JwTagAPI.updateTag(this.getAccessToken(), 102, "20240911标签页");
        System.out.println("~~~~~~~更新标签~~~~~~~" + jsonObject.toJSONString());
    }

    /**
     * 根据标签页的id获取用户
     */
    @Test
    public void getTagUser() {
        WxTagUser tagUser = JwTagAPI.getTagUser(this.getAccessToken(), 2,"");
        System.out.println("~~~~~~~根据标签页的id获取用户~~~~~~~" + JSONObject.toJSONString(tagUser));
    }

    /**
     * 批量设置标签
     */
    @Test
    public void batchtagging() {
        List<String> list = new ArrayList<>();
        list.add("oKMOd6TU0cOqlQalYtGCPD5jWfY8");
        JSONObject batchtagging = JwTagAPI.batchtagging(this.getAccessToken(), list, 102);
        System.out.println("~~~~~~~批量设置标签~~~~~~~" + JSONObject.toJSONString(batchtagging));
    }   
    
    /**
     * 批量取消标签
     */
    @Test
    public void batchuntagging() {
        List<String> list = new ArrayList<>();
        list.add("oKMOd6TU0cOqlQalYtGCPD5jWfY8");
        JSONObject batchtagging = JwTagAPI.batchuntagging(this.getAccessToken(), list, 102);
        System.out.println("~~~~~~~批量设置标签~~~~~~~" + JSONObject.toJSONString(batchtagging));
    } 
    
    /**
     * 获取用户身上的标签列表
     */
    @Test
    public void getidlist() {
        List<Integer> list = JwTagAPI.getidlist(this.getAccessToken(), "oKMOd6TU0cOqlQalYtGCPD5jWfY8");
        System.out.println("~~~~~~~批量设置标签~~~~~~~" + JSONObject.toJSONString(list));
    }

    //============= 菜单接口 ===========================================

    /**
     * 获取全部菜单
     * 
     * @throws WexinReqException
     */
    @Test
    public void getAllMenu() throws WexinReqException {
        List<WeixinButton> allMenu = JwMenuAPI.getAllMenu(getAccessToken());
        log.info("~~~~~~~~~~~菜单列表~~~~~~~~~~~"+ JSONObject.toJSONString(allMenu));
    }

    /**
     * 获取自定义菜单配置
     * 
     * @throws WexinReqException
     */
    @Test
    public void getAllMenuConfigure() throws WexinReqException {
        CustomWeixinButtonConfig allMenuConfigure = JwMenuAPI.getAllMenuConfigure(getAccessToken());
        log.info("~~~~~~~~~~~自定义菜单配置~~~~~~~~~~~"+ JSONObject.toJSONString(allMenuConfigure));
    }
    
    /**
     * 添加菜单 注意：此步骤会创建一个影响其他菜单
     * 
     * @throws WexinReqException
     */
    @Test
    public void createMenu() throws WexinReqException {
        List<WeixinButton> list = new LinkedList<>();
        List<WeixinButton> subList = new LinkedList<>();
        WeixinButton subButton = new WeixinButton();
        subButton.setName("JimuReport积木报表");
        subButton.setType("view");
        subButton.setUrl("http://jimureport.com");
        subButton.setKey("jimureport");
        WeixinButton subButton1 = new WeixinButton();
        subButton1.setName("JEECG低代码平台");
        subButton1.setType("view");
        subButton1.setUrl("http://boot3.jeecg.com/");
        subButton1.setKey("JeecgBoot");
        subList.add(subButton);
        subList.add(subButton1);
        WeixinButton weixinButton2 = new WeixinButton();
        weixinButton2.setName("APP版本");
        weixinButton2.setType("view");
        weixinButton2.setUrl("https://jeecgos.oss-cn-beijing.aliyuncs.com/app/jeecg20240903.apk");
        weixinButton2.setKey("app");
        list.add(weixinButton2);
        WeixinButton weixinButton1 = new WeixinButton();
        weixinButton1.setName("敲敲云官网");
        weixinButton1.setType("view");
        weixinButton1.setUrl("https://app.qiaoqiaoyun.com/");
        weixinButton1.setKey("qiaoqiaoyun");
        list.add(weixinButton1);
        WeixinButton weixinButton = new WeixinButton();
        weixinButton.setName("更多功能");
        weixinButton.setSub_button(subList);
        list.add(weixinButton);
        String menu = JwMenuAPI.createMenu(getAccessToken(), list);
        log.info("~~~~~~~~~~~添加菜单~~~~~~~~~~~" + menu);
    }

    /**
     * 创建个性化菜单 sex已被废弃 目前只能用标签
     */
    @Test
    public void PersonalizedMenu(){
        WeixinMenuMatchrule matchrule = new WeixinMenuMatchrule();
        matchrule.setGroup_id("2");
        List<WeixinButton> testsUb = new ArrayList<WeixinButton>();
        WeixinButton w = new WeixinButton();
        w.setName("测试菜单");
        w.setType("click");
        w.setKey("V1001_TODAY_MUSIC");
        testsUb.add(w);
        PersonalizedMenu menu = new PersonalizedMenu();
        menu.setButton(testsUb);
        menu.setMatchrule(matchrule);
        //个性化菜单创建
        String s = JwPersonalizedMenuAPI.createMenu(getAccessToken(),menu);
        log.info("个性化菜单创建结果"+ JSONObject.toJSONString(s));
        //个性化菜单匹配
        List<WeixinButton> s3 = JwPersonalizedMenuAPI.testMatchrule(getAccessToken(),"oKMOd6TU0cOqlQalYtGCPD5jWfY8");
        log.info("个性化菜单匹配结果"+ JSONObject.toJSONString(s3));
        //删除个性化菜单
//        String deleteMenu = JwPersonalizedMenuAPI.deleteMenu(getAccessToken(), Integer.valueOf(s));
//        log.info("个性化菜单删除结果"+ deleteMenu);
    }
    public String getAccessToken() {
        return new AccessToken(appid, appscret).getNewAccessToken();
    }

    public String getAccessTokenCx() {
       return JdtBaseAPI.getAccessToken("wx8bf1a0e77c68b066","??").getAccessToken();
    }

}
