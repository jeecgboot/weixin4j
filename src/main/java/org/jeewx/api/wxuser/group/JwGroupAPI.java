package org.jeewx.api.wxuser.group;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.WeiXinReqService;
import org.jeewx.api.core.req.model.user.*;
import org.jeewx.api.core.util.WeiXinConstant;
import org.jeewx.api.wxbase.wxtoken.JwTokenAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信--用户
 * 
 * @author lizr
 * 
 */
public class JwGroupAPI {
	//测试使用
	private static String TOKEN = "GHFs1OHxNiwi9cKT_oUr53enRhSStZ7QpAOjoheRWKvJFR9JtPzEkO5KnepHOT7CnDzWghTjpMyEKsWy4IGiHxv-VS9f2BN7tF6sge-H8lo";

	public static String RETURN_INFO_NAME = "groups";
	
	/**
	 * 创建分组信息
	 * @param accesstoken
	 * @param groupName
	 * @return
	 * @throws WexinReqException
	 */
	public static GroupCreate createGroup(String accesstoken ,String groupName ) throws WexinReqException{
		GroupCreate c = new GroupCreate();
		c.setAccess_token(accesstoken);
		Group g = new Group();
		g.setName(groupName);
		c.setGroup(g);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(c);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		GroupCreate groupCreate = null;
		groupCreate = (GroupCreate) JSONObject.toJavaObject(result, GroupCreate.class);
		return groupCreate;
	}
	
	/**
	 * 获取所有的分组信息
	 * @param accesstoken
	 * @return
	 * @throws WexinReqException
	 */
	public static List<Group> getAllGroup(String accesstoken) throws WexinReqException{
		GroupGet c = new GroupGet();
		c.setAccess_token(accesstoken);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(c);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		List<Group> lstGroup = null;
		JSONArray infoArray = result.getJSONArray(RETURN_INFO_NAME);
		lstGroup = new ArrayList<Group>(infoArray.size());
		for(int i=0;i<infoArray.size();i++){
			Group group = (Group)JSONObject.toJavaObject(infoArray.getJSONObject(i), Group.class);
			if(!"未分组".equals(group.getName())){
				lstGroup.add(group);
			}
		}
		return lstGroup;
	}
	
	/**
	 * 获取用户分组id
	 * @param accesstoken
	 * @param openid
	 * @return
	 * @throws WexinReqException
	 */
	public static String getUserGroup(String accesstoken,String openid) throws WexinReqException{
		GroupGetId g = new GroupGetId();
		g.setAccess_token(accesstoken);
		g.setOpenid(openid);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(g);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		String groupId = "";
		groupId = result.getString("groupid");
		return groupId;
	}
	
	/**
	 * 更新分组    正常返回ok
	 * @param accesstoken
	 * @param groupId
	 * @param groupNewName
	 * @throws WexinReqException 
	 */
	public static String updateGroup(String accesstoken,String groupId,String groupNewName) throws WexinReqException{
		GroupUpdate groupUpdate = new GroupUpdate();
		groupUpdate.setAccess_token(accesstoken);
		Group g = new Group();
		g.setId(groupId);
		g.setName(groupNewName);
		groupUpdate.setGroup(g);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(groupUpdate);
		return result.getString("errmsg");
	}
	
	/**
	 * 修改用户分组
	 * @param accesstoken
	 * @param openid
	 * @param to_groupid
	 * @return
	 * @throws WexinReqException
	 */
	public static String groupMemberMove(String accesstoken,String openid,String to_groupid) throws WexinReqException{
		GroupMembersUpdate u = new GroupMembersUpdate();
		u.setAccess_token(accesstoken);
		u.setOpenid(openid);;
		u.setTo_groupid(to_groupid);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(u);
		return result.getString("errmsg");
	}
	
	/**
	 * 批量更新用户分组
	 * @param accesstoken
	 * @param openid_list
	 * @param to_groupid
	 * @return
	 * @throws WexinReqException
	 */
	public static String batchGroupMemberMove(String accesstoken,List<String> openid_list,String to_groupid) throws WexinReqException{
		BatchGroupMembersUpdate u = new BatchGroupMembersUpdate();
		u.setAccess_token(accesstoken);
		u.setOpenid_list(openid_list);
		u.setTo_groupid(to_groupid);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(u);
		return result.getString("errmsg");
	}
	/**
	 * 删除用户分组
	 * @param accesstoken 访问凭证
	 * @param deletegGroupid 删除的分组ID
	 */
	public static String groupDelete(String accesstoken,String deletegGroupid) throws WexinReqException{
		//步骤一、api调用参数准备.
		GroupDelete group = new GroupDelete();
		Group g = new Group();
		g.setId(deletegGroupid);
		group.setAccess_token(accesstoken);
		group.setGroup(g);
		//步骤二、发起调用
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(group);
		//步骤三、对返回结果进行处理.
		//author machaolin by 20150715 for 很奇怪微信服务器没有按接口说明返回{errcode:xxx,errmsg:xxx},而是返回了{}。
		if("{}".equals(result.toString().trim())){
			return "ok";
		}else {
			return result.getString("errmsg");
		}
	}
	
	public static void main(String[] args){
		try {
			getAccessToken();
			//testCreateGroup();
			//testUpdateGroup();
			//testGetAllGroup();
			//testGetUserGroup();
			//testGroupMemberMove();
			//testBatchGroupMemberMove();
			//testGroupDelete();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取tocken
	 */
	private static void getAccessToken(){
		try {
			System.out.println(TOKEN=JwTokenAPI.getAccessToken("wxd2b52b8f4bd5af7f","1b982dba2c3f853c3396babcdfa6cb1e"));
		} catch (WexinReqException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 1.0 创建分组
	 */
	private static void testCreateGroup(){
		GroupCreate group;
		try {
			group = JwGroupAPI.createGroup(TOKEN, "分组2017");
			System.out.println("创建的分组ID:"+group.getGroup().getId());
		} catch (WexinReqException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 2.0 更新分组名称
	 */
	private static void testUpdateGroup(){
		String errmsg;
		try {
			errmsg = JwGroupAPI.updateGroup(TOKEN,"106","分组20172");
			System.out.println("更新分组信息:"+errmsg);
		} catch (WexinReqException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 3.0 获取所有分组信息
	 */
	private static void testGetAllGroup(){
		List<Group> groupList;
		try {
			groupList = JwGroupAPI.getAllGroup(TOKEN);
			for(Group g : groupList){
				System.out.println(g.getName());
			}
			//System.out.println("分组列表信息:"+groupList);
		} catch (WexinReqException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 4.0 获取用户所在分组
	 */
	private static void testGetUserGroup(){
		String openid = "oSmm-vhtoFwRo0kaqIxVnw8INSFg";
		try {
			//JwUserAPI.getAllWxuser(TOKEN, "");
			String groupid = JwGroupAPI.getUserGroup(TOKEN,openid);
			System.out.println("该粉丝所在分组为:"+groupid);
		} catch (WexinReqException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 5.0 更改用户所在分组
	 */
	private static void testGroupMemberMove(){
		String openid = "oSmm-vjkfKrkIw94VKciV5JSG8io";
		try {
			String groupid = JwGroupAPI.groupMemberMove(TOKEN,openid,"2");
			System.out.println("该粉丝所在分组为:"+groupid);
		} catch (WexinReqException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 6.0 批量更改用户所在分组
	 */
	private static void testBatchGroupMemberMove(){
		List<String> openidList = new ArrayList<String>();
		String openid = "oSmm-vjkfKrkIw94VKciV5JSG8io";
		try {
			openidList.add(openid);
			String groupid = JwGroupAPI.batchGroupMemberMove(TOKEN,openidList,"101");
			System.out.println("该粉丝所在分组为:"+groupid);
		} catch (WexinReqException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 7.0 删除分组
	 */
	private static void testGroupDelete(){
		try {
			String errmsg = JwGroupAPI.groupDelete(TOKEN,"129");
			System.out.println("删除分组:"+errmsg);
		} catch (WexinReqException e) {
			e.printStackTrace();
		}
	}
}
