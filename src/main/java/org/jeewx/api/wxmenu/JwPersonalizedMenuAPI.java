package org.jeewx.api.wxmenu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.core.req.model.menu.PersonalizedMenu;
import org.jeewx.api.core.req.model.menu.WeixinButton;
import org.jeewx.api.core.req.model.menu.WeixinMenuMatchrule;
import org.jeewx.api.core.util.WeiXinConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信个性化菜单--menu
 * 
 * @author pit
 * 
 */
public class JwPersonalizedMenuAPI {
	// 创建菜单
	private static String create_menu = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=ACCESS_TOKEN";
	// 删除个性化菜单
	private static String delete_menu = "https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=ACCESS_TOKEN";
	// 测试个性化菜单匹配结果
	private static String test_matchrule = "https://api.weixin.qq.com/cgi-bin/menu/trymatch?access_token=ACCESS_TOKEN";
	/**
	 * 创建个性化菜单
	 *  button	是	一级菜单数组，个数应为1~3个
		sub_button	否	二级菜单数组，个数应为1~5个
		type	是	菜单的响应动作类型
		name	是	菜单标题，不超过16个字节，子菜单不超过40个字节
		key	click等点击类型必须	菜单KEY值，用于消息接口推送，不超过128字节
		url	view类型必须	网页链接，用户点击菜单可打开链接，不超过256字节
		matchrule	是	菜单匹配规则
		group_id	否	用户分组id，可通过用户分组管理接口获取
		sex	否	性别：男（1）女（2），不填则不做匹配
		client_platform_type	否	客户端版本，当前只具体到系统型号：IOS(1), Android(2),Others(3)，不填则不做匹配
		country	否	国家信息，是用户在微信中设置的地区，具体请参考地区信息表
		province	否	省份信息，是用户在微信中设置的地区，具体请参考地区信息表
		city	否	城市信息，是用户在微信中设置的地区，具体请参考地区信息表
		language	否	语言信息，是用户在微信中设置的语言，具体请参考语言表：
		1、简体中文 "zh_CN" 2、繁体中文TW "zh_TW" 3、繁体中文HK "zh_HK" 4、英文 "en" 5、印尼 "id" 6、马来 "ms" 7、西班牙 "es" 8、韩国 "ko" 9、意大利 "it" 10、日本 "ja" 11、波兰 "pl" 12、葡萄牙 "pt" 13、俄国 "ru" 14、泰文 "th" 15、越南 "vi" 16、阿拉伯语 "ar" 17、北印度 "hi" 18、希伯来 "he" 19、土耳其 "tr" 20、德语 "de" 21、法语 "fr"
	 * @param accessToken
	 * @param menu  的json字符串
	 * @return menuid
	 */
	public static String createMenu(String accessToken,PersonalizedMenu menu){
		String msg = "";
		if (accessToken != null) {
			String requestUrl = create_menu.replace("ACCESS_TOKEN", accessToken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(menu));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
			if(error == null){
				msg = result.getString("menuid");
			}else{
				msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
			}
		}
		return msg;
	}//402676956
	
	/**
	 * 删除个性化菜单
	 * @param accessToken
	 * @return errormsg
	 */
	public static String deleteMenu(String accessToken,Integer menu_id){
		String msg = "";
		if (accessToken != null) {
			String requestUrl = delete_menu.replace("ACCESS_TOKEN", accessToken);
			String json = "{\"menuid\": "+menu_id+"}";
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", json);
			//Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
				msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
		}
		return msg;
	}
	/**
	 * 测试个性化菜单匹配结果
	 * @param accessToken
	 *  @param user_id user_id可以是粉丝的OpenID，也可以是粉丝的微信号。
	 * @return
	 */
	public static List<WeixinButton> testMatchrule(String accessToken,String user_id){
		if (accessToken != null) {
			String requestUrl = test_matchrule.replace("ACCESS_TOKEN", accessToken);
			String json = "{\"user_id\": \""+user_id+"\"}";
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", json);
			Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
			if(error == null){
				JSONObject menu = result.getJSONObject("menu");
				JSONArray button = menu.getJSONArray("button");
				List<WeixinButton> btns = (List<WeixinButton>)button.toJavaObject(List.class);
				return btns;
			}
		}
		return null;
	}

	public static void main(String[] args) {
			WeixinMenuMatchrule matchrule = new WeixinMenuMatchrule();
			matchrule.setSex("1");
			List<WeixinButton> testsUb = new ArrayList<WeixinButton>();
			WeixinButton w = new WeixinButton();
			w.setName("测试菜单");
			w.setType("click");
			w.setKey("V1001_TODAY_MUSIC");
			testsUb.add(w);
			PersonalizedMenu menu = new PersonalizedMenu();
			menu.setButton(testsUb);
			menu.setMatchrule(matchrule);
			String s = JwPersonalizedMenuAPI.createMenu("UpIJXAUIzENJnR9WDD3kxCErxtxnFHVT_Sd3kHmtiauJwUi2PU6u5YFUHgKibd2_wDBlR6xjmHzXfO5zvYEV9ljeJGrwCZOcokZOXpiu6V1gjEf4oOzMUliKFYUdukJzNBEiABAIWZ",menu);
			List<WeixinButton> s3 = JwPersonalizedMenuAPI.testMatchrule("UpIJXAUIzENJnR9WDD3kxCErxtxnFHVT_Sd3kHmtiauJwUi2PU6u5YFUHgKibd2_wDBlR6xjmHzXfO5zvYEV9ljeJGrwCZOcokZOXpiu6V1gjEf4oOzMUliKFYUdukJzNBEiABAIWZ","oGCDRjooOuBCr7MM0cs1-KqAh_aU");
		    System.out.println(s);
		    System.out.println(s3);
	}
	
	 
}
