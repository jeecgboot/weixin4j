package com.jeecg.qywx.api.department;

import java.util.List;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.jeecg.qywx.api.base.JwAccessTokenAPI;
import com.jeecg.qywx.api.base.JwParamesAPI;
import com.jeecg.qywx.api.core.common.AccessToken;
import com.jeecg.qywx.api.core.util.HttpUtil;
import com.jeecg.qywx.api.department.vo.DepartMsgResponse;
import com.jeecg.qywx.api.department.vo.Department;

/**
 * 企业微信--管理部门
 * 
 * @author zhoujf
 * 
 */
public class JwDepartmentAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(JwDepartmentAPI.class);

	//创建部门（POST）   
	private static String department_create_url = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=ACCESS_TOKEN";  
	//更新部门（POST）   
	private static String department_update_url = "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=ACCESS_TOKEN";  
	//删除部门（GET）   
	private static String department_delete_url = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=ACCESS_TOKEN&id=ID";  
	//获取部门列表（GET）   [获取特定部门]
	private static String department_list_url_get = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN&id=ID";  
	//获取部门列表（GET）   [获取全部组织机构]
	private static String department_list_url = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN";
	
	/**
	 * 创建部门
	 * @param department 部门数据,参见 {@linkplain Department}，必须赋值属性如下：
	 * <ul>
	 * <li>name			是	部门名称。长度限制为1~64个字节，字符不能包括\:*?"<>｜</li>
	 * <li>parentid		是	父亲部门id。根部门id为1</li>
	 * <li>order		否	在父部门中的次序值。order值小的排序靠前。</li>
	 * <li>id			否	部门id，整型。指定时必须大于1，不指定时则自动生成</li>
	 * </ul>
	 * @param accessToken 有效的access_token 
	 * @return DepartMsgResponse 响应数据,参见 {@linkplain DepartMsgResponse}，必须赋值属性如下：
	 * <ul>
	 * <li>errcode		返回码</li>
	 * <li>errmsg		对返回码的文本描述内容</li>
	 * <li>id			创建的部门id</li>
	 * </ul>
	 */
	public static DepartMsgResponse createDepartment(Department department, String accessToken) {  
		logger.info("[CREATEDEPARTMENT]", "createDepartment param:accessToken:{},menu:{}", new Object[]{accessToken,department});
		DepartMsgResponse res = null;  
	    // 拼装创建部门的url  
	    String url = department_create_url.replace("ACCESS_TOKEN", accessToken);  
	    // 将菜单对象转换成json字符串  
	    String jsonParam = JSONObject.toJSONString(department);  
	    logger.info("[CREATEDEPARTMENT]", "createDepartment param:jsonParam:{}", new Object[]{jsonParam});
	    // 调用接口创建部门
	    JSONObject jsonObject = HttpUtil.sendPost(url, jsonParam);  
	    
	    logger.info("[CREATEDEPARTMENT]", "createDepartment response:{}", new Object[]{jsonObject.toJSONString()});
	    if (null != jsonObject) { 
	    	res = new DepartMsgResponse();
	    	int errcode = jsonObject.getIntValue("errcode");
	    	String errmsg = jsonObject.getString("errmsg");
	    	System.out.println("errcode："+errcode+"，errmsg:"+errmsg);
	    	res.setErrcode(errcode);
	    	res.setErrmsg(errmsg);
	    	if(errcode==0){
	    		int id = jsonObject.getIntValue("id");
	    		res.setId(id);
	    	}
	    }  
	    return res;  
	}  
	
	
	/**
	 * 获取所有部门
	 * @param department
	 * @param accessToken
	 * @return
	 */
	public static List<Department> getAllDepartment(String accessToken) {
		logger.info("[CREATEDEPARTMENT]", "createDepartment param:accessToken:{},menu:{}", new Object[]{accessToken});
		DepartMsgResponse res = null;  
	    // 拼装创建部门的url  
	    String url = department_list_url.replace("ACCESS_TOKEN", accessToken);  
	    // 调用接口创建部门
	    JSONObject jsonObject = HttpUtil.sendPost(url);  
	    logger.info("[CREATEDEPARTMENT]", "createDepartment response:{}", new Object[]{jsonObject.toJSONString()});
	    if (null != jsonObject) { 
	    	int errcode = jsonObject.getIntValue("errcode");
	    	String errmsg = jsonObject.getString("errmsg");
	    	String departmentjson = jsonObject.getString("department");

	    	List<Department> ps = JSON.parseArray(departmentjson, Department.class);
	    	return ps;
	    }  
	    return null;
	}

	/**
	 * 获取指定部门
	 *
	 * @param depId
	 * @param accessToken
	 * @return
	 */
	public static List<Department> getDepartmentById(String depId, String accessToken) {
		logger.info("[JW_DEPARTMENT] getDepartmentById param:accessToken:{}", new Object[]{accessToken});
		// 拼装url
		String url = department_list_url_get.replace("ACCESS_TOKEN", accessToken).replace("ID", depId);
		// 调用接口查询部门
		JSONObject response = HttpUtil.sendPost(url);
		logger.info("[JW_DEPARTMENT] getDepartmentById response:{}", new Object[]{response.toJSONString()});
		return response.getJSONArray("department").toJavaList(Department.class);
	}

	/**
	 * 删除部门
	 * @param departId
	 * @param accessToken
	 * @return
	 */
	public static int deleteDepart(String departId,String accessToken) {
		 String url = department_delete_url.replace("ACCESS_TOKEN", accessToken).replace("ID", departId);
		 JSONObject jsonObject = HttpUtil.sendPost(url);
		 if (null != jsonObject) { 
		    	int errcode = jsonObject.getIntValue("errcode");
		    	String errmsg = jsonObject.getString("errmsg");
		    	System.out.println("errcode："+errcode+"，errmsg:"+errmsg);
		    	return errcode;
		    } 
		 return -1;
	}
	
	/**
	 * 修改部门
	 * @param department
	 * @param accessToken
	 * @return
	 */
	public static int updateDepart(Department department,String accessToken) {
		 String url = department_update_url.replace("ACCESS_TOKEN", accessToken);
		 String jsonParam = JSONObject.toJSONString(department);
		 JSONObject jsonObject = HttpUtil.sendPost(url,jsonParam);
		 if (null != jsonObject) { 
		    	int errcode = jsonObject.getIntValue("errcode");
		    	String errmsg = jsonObject.getString("errmsg");
		    	System.out.println("errcode："+errcode+"，errmsg:"+errmsg);
		    	return errcode;
		    } 
		 return -1;
	}
	 
	
	public static void main(String[] args){
		try {
			AccessToken accessToken = JwAccessTokenAPI.getAccessToken(JwParamesAPI.corpId,JwParamesAPI.secret);
			
			//查询部门
//			List<Department> ls = JwDepartmentAPI.getAllDepartment(accessToken.getAccesstoken());
//			for(Department po:ls){
//				System.out.println(po.toString());
//			}
			
			//删除部门
//			JwDepartmentAPI.deleteDepart(7,accessToken.getAccesstoken());
			
			//创建部门
			Department department = new Department();
			department.setId("cccddd");
			department.setName("cccddd");
			department.setOrder("2");
			department.setParentid("28A874914D2F4082AFCA1201E8B21E5B");
			JwDepartmentAPI.createDepartment(department, accessToken.getAccesstoken());
			
			
			//修改部门
//			Department department = new Department();
//			department.setId(8);
//			department.setName("人力资源1");
//			department.setOrder(1);
//			department.setParentid(1);
//			JwDepartmentAPI.updateDepart(department, accessToken.getAccesstoken());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
