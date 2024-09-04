package org.jeewx.api.wxsendmsg;

import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.core.exception.WexinReqException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwMessageCommentAPI {
	private static Logger logger = LoggerFactory.getLogger(JwMessageCommentAPI.class);
	/**
	 * 打开评论
	 */
	private static String comment_open_url = "https://api.weixin.qq.com/cgi-bin/comment/open?access_token=ACCESS_TOKEN";

	/**
	 * 关闭评论
	 */
	private static String comment_close_url = "https://api.weixin.qq.com/cgi-bin/comment/close?access_token=ACCESS_TOKEN";
	
	/**
	 * 查看指定文章的评论数据
	 */
	private static String comment_list_url = "https://api.weixin.qq.com/cgi-bin/comment/list?access_token=ACCESS_TOKEN"; 
	
	/**
	 * 将评论标记精选
	 */
	private static String comment_markelect = "https://api.weixin.qq.com/cgi-bin/comment/markelect?access_token=ACCESS_TOKEN";
	/**
	 * 将评论取消精选
	 */
	private static String comment_unmarkelect = "https://api.weixin.qq.com/cgi-bin/comment/unmarkelect?access_token=ACCESS_TOKEN";
	
	/**
	 * 删除评论
	 */
	private static String comment_delete = "https://api.weixin.qq.com/cgi-bin/comment/delete?access_token=ACCESS_TOKEN";
	
	/**
	 * 回复评论
	 */
	private static String comment_reply_add = "https://api.weixin.qq.com/cgi-bin/comment/reply/add?access_token=ACCESS_TOKEN";
	
	/**
	 * 删除回复
	 */
	private static String comment_reply_delete = "https://api.weixin.qq.com/cgi-bin/comment/reply/delete?access_token=ACCESS_TOKEN";
	
	
	/**
	 * 开启评论
	 * @param accesstoken
	 * @param msg_data_id
	 * @param index
	 * @throws WexinReqException
	 * @return { “errcode”: ERRCODE, “errmsg” : ERRMSG }
	 */
	public static JSONObject openComment(String accesstoken,String msg_data_id,int index) throws WexinReqException {
		if (accesstoken != null) {
			String requestUrl = comment_open_url.replace("ACCESS_TOKEN", accesstoken);
			try {
				JSONObject obj = new JSONObject();
				obj.put("msg_data_id", msg_data_id);
				obj.put("index", index);
				JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
				System.out.println("微信返回的结果：" + result.toString());
				return result;
			} catch (Exception e) {
				throw new WexinReqException(e);
			}
		} else {
			throw new WexinReqException("accesstoken 为空，请检查！");
		}
	}

	public static JSONObject closeComment(String accesstoken,String msg_data_id,int index) throws WexinReqException {
		if (accesstoken != null) {
			String requestUrl = comment_close_url.replace("ACCESS_TOKEN", accesstoken);
			try {
				JSONObject obj = new JSONObject();
				obj.put("msg_data_id", msg_data_id);
				obj.put("index", index);
				JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
				System.out.println("微信返回的结果：" + result.toString());
				return result;
			} catch (Exception e) {

				throw new WexinReqException(e);
			}
		} else {
			throw new WexinReqException("accesstoken 为空，请检查！");
		}
	}
	
	/**
	 * 查询评论列表
	 * @param accesstoken
	 * @param params
	 * { “msg_data_id” :"" , “index” : 1, “begin”: , “count”: , “type” :  }
	 * <p>参数讲解<br>
	 * msg_data_id：群发返回的msg_data_id<br>
	 * index:多图文时，用来指定第几篇图文，从0开始，不带默认返回该msg_data_id的第一篇图文<br>
	 * begin:起始位置<br>
	 * count:获取数目（>=50会被拒绝）<br>
	 * type:type=0 普通评论&精选评论 type=1 普通评论 type=2 精选评论<br>
	 * 
	 * @return 
	 * <p>{ <br>
	 * “errcode”: 0, “errmsg” : “ok”, “total”: TOTAL,<br>
	 * “comment”: [{ <br>
	 * user_comment_id : "用户评论id",<br>
	 * aroundopenid :"openid",<br>
	 * aroundcreate_time : "评论时间",<br>
	 * aroundcontent :"评论内容",<br>
	 * aroundcomment_type :"是否精选评论，0为即非精选，1为true",<br>
	 * reply :{ content :"作者回复内容",create_time : "作者回复时间 "} <br>
	 * }]  <br>
	 * }
	 * <p>错误返回如下：<br>
	 * <br>{ “errcode” : 45009, “errmsg” : “reach max api daily quota limit”                  //没有剩余的调用次数 }
	 * <br>{ “errcode” : 88000， “errmsg” : “open comment without comment privilege” //没有留言权限 }
	 * <br>{ “errcode” : 88001, “errmsg” : “msg_data is not exists”                                   //该图文不存在 }
	 * <br>{ “errcode” : 88010, “errmsg” : “count range error. cout <= 0 or count > 50” //获取评论数目不合法 }
	 */
	public static JSONObject queuryComments(String accesstoken,JSONObject params) throws WexinReqException {
		if (accesstoken == null) {
			throw new WexinReqException("accesstoken 为空，请检查！");
		}
		String requestUrl = comment_list_url.replace("ACCESS_TOKEN", accesstoken);
		try {
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", params.toString());
			logger.info("查看文章的评论数据 结果：" + result.toString());
			return result;
		} catch (Exception e) {
			throw new WexinReqException(e);
		}
	}
	
	/**
	 *  将评论标记精选
	 * @param accesstoken
	 * @param msg_data_id 群发返回的msg_data_id
	 * @param index 多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
	 * @param user_comment_id 用户评论id
	 * @return 
	 * { “errcode”: ERRCODE, “errmsg” : ERRMSG }
	 * <p>
	 * <br>{ “errcode” : 45009, “errmsg” : “reach max api daily quota limit”                  //没有剩余的调用次数 }
	 * <br>{ “errcode” : 88000， “errmsg” : “open comment without comment privilege” //没有留言权限 }
	 * <br>{ “errcode” : 88001, “errmsg” : “msg_data is not exists”                                  //该图文不存在 }
	 * <br>{ “errcode” : 88003, “errmsg” : “elected comment upper limit”                      //精选评论数已达上限 }
	 * <br>{ “errcode” : 88004, “errmsg” : “comment was deleted by user”                     //已被用户删除，无法精选 }
	 * <br>{ “errcode” : 88008, “errmsg” : “comment is not exists”                                  //该评论不存在 }
	 * @throws WexinReqException 
	 */
	public static JSONObject markelectComment(String accesstoken,String msg_data_id,int index,String user_comment_id) throws WexinReqException {
		if (accesstoken == null) {
			throw new WexinReqException("accesstoken 为空，请检查！");
		}
		String requestUrl = comment_markelect.replace("ACCESS_TOKEN", accesstoken);
		try {
			JSONObject obj = new JSONObject();
			obj.put("msg_data_id", msg_data_id);
			obj.put("index", index);
			obj.put("user_comment_id", user_comment_id);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			logger.info("将评论标记精选：" + result.toString());
			return result;
		} catch (Exception e) {
			throw new WexinReqException(e);
		}
	}
	
	/**
	 * 将评论取消精选
	 * @param accesstoken
	 * @param msg_data_id 群发返回的msg_data_id
	 * @param index 多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
	 * @param user_comment_id 用户评论id
	 * @return 
	 * { “errcode”: ERRCODE, “errmsg” : ERRMSG }
	 * <p>
	 * <br>{ “errcode” : 45009, “errmsg” : “reach max api daily quota limit”                  //没有剩余的调用次数 }
	 * <br>{ “errcode” : 88000， “errmsg” : “open comment without comment privilege” //没有留言权限 }
	 * <br>{ “errcode” : 88001, “errmsg” : “msg_data is not exists”                                          //该图文不存在 }
	 * <br>{ “errcode” : 88008, “errmsg” : “comment is not exists”                                          //该评论不存在 }
	 * @throws WexinReqException
	 */
	public static JSONObject unmarkelectComment(String accesstoken,String msg_data_id,int index,String user_comment_id) throws WexinReqException {
		if (accesstoken == null) {
			throw new WexinReqException("accesstoken 为空，请检查！");
		}
		String requestUrl = comment_unmarkelect.replace("ACCESS_TOKEN", accesstoken);
		try {
			JSONObject obj = new JSONObject();
			obj.put("msg_data_id", msg_data_id);
			obj.put("index", index);
			obj.put("user_comment_id", user_comment_id);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			logger.info("将评论取消精选：" + result.toString());
			return result;
		} catch (Exception e) {
			throw new WexinReqException(e);
		}
	}
	
	/**
	 * 删除评论
	 * @param accesstoken
	 * @param msg_data_id 群发返回的msg_data_id
	 * @param index 多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
	 * @param user_comment_id 评论id
	 * @return 
	 * { “errcode”: ERRCODE, “errmsg” : ERRMSG }
	 * <p>
	 *<br>{ “errcode” : 45009, “errmsg” : “reach max api daily quota limit”                  //没有剩余的调用次数 }
     *<br>{ “errcode” : 88000， “errmsg” : “open comment without comment privilege” //没有留言权限 }
     *<br>{ “errcode” : 88001, “errmsg” : “msg_data is not exists”                                          //该图文不存在 } 
     *<br>{ “errcode” : 88008, “errmsg” : “comment is not exists”                                          //该评论不存在 }
	 * @throws WexinReqException
	 */
	public static JSONObject deleteComment(String accesstoken,String msg_data_id,int index,String user_comment_id) throws WexinReqException {
		if (accesstoken == null) {
			throw new WexinReqException("accesstoken 为空，请检查！");
		}
		String requestUrl = comment_delete.replace("ACCESS_TOKEN", accesstoken);
		try {
			JSONObject obj = new JSONObject();
			obj.put("msg_data_id", msg_data_id);
			obj.put("index", index);
			obj.put("user_comment_id", user_comment_id);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			logger.info("删除评论：" + result.toString());
			return result;
		} catch (Exception e) {
			throw new WexinReqException(e);
		}
	}
	
	/**
	 * 回复评论
	 * @param accesstoken
	 * @param msg_data_id 群发返回的msg_data_id
	 * @param index 多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
	 * @param user_comment_id 评论id
	 * @param content 回复内容
	 * @return 
	 * { “errcode”: ERRCODE, “errmsg” : ERRMSG }
	 * <p>
	 * <br>{ “errcode” : 45009, “errmsg” : “reach max api daily quota limit”                  //没有剩余的调用次数 }
	 * <br>{ “errcode” : 88000， “errmsg” : “open comment without comment privilege” //没有留言权限 } 
	 * <br>{ “errcode” : 88001, “errmsg” : “msg_data is not exists”                              //该图文不存在 } 
	 * <br>{ “errcode” : 88005, “errmsg” : “already reply”                                            //已经回复过了 } 
	 * <br>{ “errcode” : 88007, “errmsg” : “reply content beyond max len or content len is zero”//回复超过长度限制或为0 }
	 * <br>{ “errcode” : 88008, “errmsg” : “comment is not exists”                            //该评论不存在 }
	 * @throws WexinReqException
	 */
	public static JSONObject replyComment(String accesstoken,String msg_data_id,int index,String user_comment_id,String content) throws WexinReqException {
		if (accesstoken == null) {
			throw new WexinReqException("accesstoken 为空，请检查！");
		}
		String requestUrl = comment_reply_add.replace("ACCESS_TOKEN", accesstoken);
		try {
			JSONObject obj = new JSONObject();
			obj.put("msg_data_id", msg_data_id);
			obj.put("index", index);
			obj.put("user_comment_id", user_comment_id);
			obj.put("content", content);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			logger.info("回复评论：" + result.toString());
			return result;
		} catch (Exception e) {
			throw new WexinReqException(e);
		}
	}
	
	
	/**
	 * 删除回复
	 * @param accesstoken
	 * @param msg_data_id 群发返回的msg_data_id
	 * @param index 多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
	 * @param user_comment_id 评论id
	 * @return 
	 * { “errcode”: ERRCODE, “errmsg” : ERRMSG }
	 * <p>
	 *<br> { “errcode” : 45009, “errmsg” : “reach max api daily quota limit”                     //没有剩余的调用次数 }  
	 *<br> { “errcode” : 88000， “errmsg” : “open comment without comment privilege” //没有留言权限 }  
	 * <br>{ “errcode” : 88001, “errmsg” : “msg_data is not exists”                                   //该图文不存在 }
	 * <br>{ “errcode” : 88008, “errmsg” : “comment is not exists”                                   //该评论不存在 }
	 *<br> { “errcode” : 87009, “errmsg” : “reply is not exists”                                         //该回复不存在 }
	 * @throws WexinReqException
	 */
	public static JSONObject deleteReplyComment(String accesstoken,String msg_data_id,int index,String user_comment_id) throws WexinReqException {
		if (accesstoken == null) {
			throw new WexinReqException("accesstoken 为空，请检查！");
		}
		String requestUrl = comment_reply_delete.replace("ACCESS_TOKEN", accesstoken);
		try {
			JSONObject obj = new JSONObject();
			obj.put("msg_data_id", msg_data_id);
			obj.put("index", index);
			obj.put("user_comment_id", user_comment_id);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			logger.info("删除回复：" + result.toString());
			return result;
		} catch (Exception e) {
			throw new WexinReqException(e);
		}
	}

	public static void main(String[] args) throws WexinReqException {
		String accesstoken = "";
		String msg_data_id = "";
		int index = 0;
		openComment(accesstoken, msg_data_id, index);
	}

}
