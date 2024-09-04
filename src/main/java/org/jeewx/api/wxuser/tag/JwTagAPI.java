package org.jeewx.api.wxuser.tag;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.util.WeiXinConstant;
import org.jeewx.api.wxuser.tag.model.WxTag;
import org.jeewx.api.wxuser.tag.model.WxTagUser;
import org.jeewx.api.wxuser.tag.model.WxTagUserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwTagAPI {
	
	private static Logger logger = LoggerFactory.getLogger(JwTagAPI.class);
	
	//创建标签
	private static String create_tag = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN";
	
	//获取创建的标签
	private static String get_tag = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN";
	
	//编辑标签
	private static String update_tag = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN";
	
	//删除标签
	private static String delete_tag = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN";
	
	//获取标签下粉丝列表
	private static String get_tag_user = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN";
	
	//批量为用户打标签
	private static String batchtagging = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN";
	
	//批量为用户取消标签
	private static String batchuntagging = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=ACCESS_TOKEN";
	
	//获取用户身上的标签列表
	private static String getidlist= "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=ACCESS_TOKEN";
	/**
	 * 创建标签
	 * @param accessToken
	 * @param tagName
	 * @return
	 * @throws WexinReqException
	 */
	public static JSONObject createTag(String accessToken, String tagName) throws WexinReqException{
		JSONObject result = null;
		if (accessToken != null) {
			String requestUrl = create_tag.replace("ACCESS_TOKEN", accessToken);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("name", tagName);
			JSONObject obj = new JSONObject();
			obj.put("tag", map);
			logger.info("创建标签方法执行前json参数---obj: "+obj.toString());
			result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			logger.info("创建标签方法执行后json参数 : "+result.toString());
		}
		return result;
	}
	
	/**
	 * 获取标签
	 */
	public static List<WxTag> getTags(String accessToken){
		List<WxTag> list = null;
		if(accessToken != null){
			String requestUrl = get_tag.replace("ACCESS_TOKEN", accessToken);
			//logger.info("创建标签方法执行前json参数 obj: "+obj.toString());
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "GET", null);
			Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
			if(error == null){
				String tags = result.getString("tags");
				JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(tags));
				list = jsonArray.toJavaList(WxTag.class);
				return list;
			}
			logger.info("获取标签方法执行后json参数 : "+result.toString());
		}
		return list;
	}
	
	/**
	 * 编辑标签
	 */
	public static JSONObject updateTag(String accessToken,int id,String name){
		JSONObject result = null;
		if(accessToken != null){
			String requestUrl = update_tag.replace("ACCESS_TOKEN", accessToken);
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("id", id);
			data.put("name", name);
			JSONObject obj = new JSONObject();
			obj.put("tag", data);
			logger.info("编辑标签方法执行前json参数---obj: "+obj.toString());
			result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			logger.info("编辑标签方法执行后json参数 : "+result.toString());
		}
		return result;
	}
	
	/**
	 * 删除标签
	 */
	public static JSONObject delete(String accessToken,String id){
		JSONObject result = null;
		if(accessToken != null){
			String requestUrl = delete_tag.replace("ACCESS_TOKEN", accessToken);
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("id", id);
			JSONObject obj = new JSONObject();
			obj.put("tag", data);
			logger.info("删除标签方法执行前json参数---obj: "+obj.toString());
			result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			logger.info("删除标签方法执行后json参数 : "+result.toString());
		}
		return result;
	}
	
	/**
	 * 获取标签下粉丝列表
	 */
	public static WxTagUser getTagUser(String accessToken,int tagid,String next_openid){
		WxTagUser tagUser = null;
		WxTagUserList users = null;
		try {
			if(accessToken != null){
				String requestUrl = get_tag_user.replace("ACCESS_TOKEN", accessToken);
				Map<String,Object> data = new HashMap<String,Object>();
				data.put("tagid", tagid);
				data.put("next_openid", next_openid);
				JSONObject obj = JSONObject.parseObject(JSON.toJSONString(data));
				logger.info("获取标签下粉丝列表方法执行前json参数---obj: "+obj.toString());
				JSONObject result = WxstoreUtils.httpRequest(requestUrl, "GET", obj.toString());
				Integer error = (Integer) result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
				if(error == null){
					/*Integer count = (Integer) obj.get("count");
					String nextOpenid = obj.getString("next_openid");
					Object dataStr = obj.get("data");
					JSONObject dataJson = JSONObject.fromObject(dataStr);
					JSONArray jsonArray = dataJson.getJSONArray("openid");
					List<String> list = JSONArray.toList(jsonArray, String.class);
					users.setOpenid(list);
					tagUser.setCount(count);
					tagUser.setData(users);
					tagUser.setNext_openid(nextOpenid);*/
					tagUser  = (WxTagUser) JSONObject.toJavaObject(result, WxTagUser.class);
				}
				logger.info("获取标签下粉丝列表方法执行后json参数 : "+result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tagUser;
	}
	
	/**
	 * 批量为用户打标签
	 */
	public static JSONObject batchtagging(String accessToken,List<String> openid_list,int tagid){
		JSONObject result = null;
		if(accessToken != null){
			String requestUrl = batchtagging.replace("ACCESS_TOKEN", accessToken);
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("openid_list", openid_list);
			data.put("tagid", tagid);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(data));
			logger.info("批量为用户打标签 方法执行前json参数---obj: "+obj.toString());
			result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			logger.info("批量为用户打标签 方法执行后json参数 : "+result.toString());
		}
		return result;
	}
	
	/**
	 * 批量为用户取消标签
	 */
	public static JSONObject batchuntagging(String accessToken,List<String> openid_list,int tagid){
		JSONObject result = null;
		if(accessToken != null){
			String requestUrl = batchuntagging.replace("ACCESS_TOKEN", accessToken);
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("openid_list", openid_list);
			data.put("tagid", tagid);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(data));
			logger.info("批量为用户取消标签 方法执行前json参数---obj: "+obj.toString());
			result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			logger.info("批量为用户取消标签 方法执行后json参数 : "+result.toString());
		}
		return result;
	}
	
	/**
	 * 获取用户身上的标签列表
	 */
	public static List<Integer> getidlist(String accessToken,String openid){
		List<Integer> list = null;
		if(accessToken != null){
			String requestUrl = getidlist.replace("ACCESS_TOKEN", accessToken);
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("openid", openid);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(data));
			logger.info("获取用户身上的标签列表 方法执行前json参数---obj: "+obj.toString());
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
			if(error == null){
				JSONArray jsonArray = result.getJSONArray("tagid_list");
				list = jsonArray.toJavaList(Integer.class);
			}
			logger.info("获取用户身上的标签列表 方法执行后json参数 : "+result.toString());
		}
		return list;
	}
	
}
