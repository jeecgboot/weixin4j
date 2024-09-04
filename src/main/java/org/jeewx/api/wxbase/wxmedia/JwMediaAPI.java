package org.jeewx.api.wxbase.wxmedia;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.StringUtils;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.WeiXinReqService;
import org.jeewx.api.core.req.model.DownloadMedia;
import org.jeewx.api.core.req.model.UploadMedia;
import org.jeewx.api.core.util.WeiXinConstant;
import org.jeewx.api.core.util.WeiXinReqUtil;
import org.jeewx.api.wxbase.wxmedia.model.*;
import org.jeewx.api.wxsendmsg.JwSendMessageAPI;
import org.jeewx.api.wxsendmsg.model.WxArticle;
import org.jeewx.api.wxsendmsg.model.WxArticlesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 微信--token信息
 * 
 * @author lizr
 * 
 */
public class JwMediaAPI {
	private static Logger logger = LoggerFactory.getLogger(JwMediaAPI.class);
	// 新增永久图文素材
	private static String material_add_news_url ="https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN";
	// 新增其他类型永久素材
	private static String material_add_material_url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN";
	// 获取永久素材
	private static String material_get_material_url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
	// 获取素材总数
	private static String material_get_materialcount_url = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN";
	// 修改永久图文素材
	private static String material_update_news_url = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=ACCESS_TOKEN";
	// 获取素材列表
	private static String material_batchget_material_url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	//新增其他类型永久素材  图文素材上传专用
	private static String add_material = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";
	//删除永久图文素材
	private static String material_del_news_url = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";
	
	/**
	 * 
	 * @param accessToke
	 * @param type  媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @param fileNamePath  上传的文件目录
	 * @return
	 * @throws WexinReqException
	 */
	public static WxUpload uploadMedia(String accessToke,String type,String fileNamePath) throws WexinReqException{
		UploadMedia uploadMedia = new UploadMedia();
		uploadMedia.setAccess_token(accessToke);
		uploadMedia.setFilePathName(fileNamePath);
		uploadMedia.setType(type);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(uploadMedia);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		WxUpload wxMedia = null;
		wxMedia = (WxUpload) JSONObject.toJavaObject(result, WxUpload.class);
		return wxMedia;
	}
	
	
	/**
	 * 下载多媒体
	 * @param accessToke
	 * @param media_id
	 * @param filePath
	 * @return
	 * @throws WexinReqException
	 */
	public static WxDwonload downMedia(String accessToke,String media_id,String filePath) throws WexinReqException{
		DownloadMedia downloadMedia = new DownloadMedia();
		downloadMedia.setAccess_token(accessToke);
		downloadMedia.setFilePath(filePath);
		downloadMedia.setMedia_id(media_id);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(downloadMedia);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		WxDwonload wxMedia = null;
		wxMedia = (WxDwonload) JSONObject.toJavaObject(result, WxDwonload.class);
		return wxMedia;
	}
	
	
	public static void main(String[] args){
		 
		try {
			/*WxUpload s = WeixinMediaService.uploadMedia(
					"kY9Y9rfdcr8AEtYZ9gPaRUjIAuJBvXO5ZOnbv2PYFxox__uSUQcqOnaGYN1xc4N1rI7NDCaPm_0ysFYjRVnPwCJHE7v7uF_l1hI6qi6QBsA",
					"image","C:/Users/sfli.sir/Desktop/temp/2457331_160355071353_2.jpg");*/
			WxDwonload d = JwMediaAPI.downMedia(
					"kY9Y9rfdcr8AEtYZ9gPaRUjIAuJBvXO5ZOnbv2PYFxox__uSUQcqOnaGYN1xc4N1rI7NDCaPm_0ysFYjRVnPwCJHE7v7uF_l1hI6qi6QBsA",
					"wBSDL0sz3zqOSGEXG9kIht48V9W7pAQBK50rFKFx1dv6FXsVNROxcxLPMUa9L-yI",
					"C:/Users/sfli.sir/Desktop/temp/");
			System.out.println(d.getFileName());
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 上传新增永久图文素材 (经测试，该方法不可用)
	 * 
	 * @param accesstoken
	 * @param wxArticles
	 *            图文集合，数量不大于10
	 * @return WxArticlesResponse 上传图文消息素材返回结果
	 * @throws WexinReqException
	 */
	public static WxArticlesResponse uploadArticlesByMaterial(String accesstoken, List<WxArticle> wxArticles) throws WexinReqException {
		WxArticlesResponse wxArticlesResponse = null;
		if (wxArticles.size() == 0) {
			logger.error("没有上传的图文消息");
		} else if (wxArticles.size() > 10) {
			logger.error("图文消息最多为10个图文");
		} else {
			if (accesstoken != null) {
				String requestUrl = material_add_news_url.replace("ACCESS_TOKEN", accesstoken);

				for (WxArticle article : wxArticles) {
					if (article.getFileName() != null && article.getFileName().length() > 0) {
						try {
							String mediaId = JwSendMessageAPI.getFileMediaId(accesstoken, article);
							article.setThumb_media_id(mediaId);

						} catch (Exception e) {
							throw new WexinReqException(e);
						}
					}
				}
				WxArticlesRequest wxArticlesRequest = new WxArticlesRequest();
				wxArticlesRequest.setArticles(wxArticles);
				JSONObject obj = JSONObject.parseObject(JSON.toJSONString(wxArticlesRequest));
				JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
				//System.out.println("微信返回的结果：" + result.toString());
				if (result.containsKey("errcode")) {
					logger.error("上传图文消息失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
					throw new WexinReqException("上传图文消息失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
				} else {

					wxArticlesResponse = new WxArticlesResponse();
					wxArticlesResponse.setMedia_id(result.getString("media_id"));
					wxArticlesResponse.setType(result.getString("type"));
					wxArticlesResponse.setCreated_at(new Date(result.getLong("created_at") * 1000));
				}

			}
		}

		return wxArticlesResponse;
	}
	
	
	/**
	 * 获取素材总数
	 * 
	 * @param accesstoken
	 * @param accesstoken
	 *           
	 * @return WxCountResponse 素材数目返回结果
	 * @throws WexinReqException
	 */
	public static WxCountResponse getMediaCount(String accesstoken) throws WexinReqException {
		WxCountResponse wxCountResponse = null;
		if (accesstoken != null) {
			String requestUrl = material_get_materialcount_url.replace("ACCESS_TOKEN", accesstoken);

			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST",null);
			//System.out.println("微信返回的结果：" + result.toString());
			if (result.containsKey("errcode")) {
				logger.error("上传图文消息失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
				throw new WexinReqException("上传图文消息失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
			} else {

				wxCountResponse = new WxCountResponse();
				wxCountResponse.setImage_count(result.getString("image_count"));
				wxCountResponse.setNews_count(result.getString("news_count"));
				wxCountResponse.setVideo_count(result.getString("video_count"));
				wxCountResponse.setVoice_count(result.getString("voice_count"));
			}
		}
		return wxCountResponse;
	}
	
	/**
	 * 获取永久素材 (经测试，该方法调用接口返回时封装实体对象不可用)
	 * 
	 * @param accesstoken
	 * @param wxArticles
	 *            图文集合，数量不大于10
	 * @return WxArticlesResponse 上传图文消息素材返回结果
	 * @throws WexinReqException
	 */
	public static WxArticlesRespponseByMaterial getArticlesByMaterial(String accesstoken,String mediaId) throws WexinReqException {
		WxArticlesRespponseByMaterial wxArticlesRespponseByMaterial = null;
		
			if (accesstoken != null) {
				String requestUrl = material_get_material_url.replace("ACCESS_TOKEN", accesstoken);
				WxArticlesRequestByMaterial wxArticlesRequestByMaterial = new WxArticlesRequestByMaterial();
				wxArticlesRequestByMaterial.setMediaId(mediaId);
				JSONObject obj = JSONObject.parseObject(JSON.toJSONString(wxArticlesRespponseByMaterial));
				JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
				//System.out.println("微信返回的结果：" + result.toString());
				if (result.containsKey("errcode")) {
					logger.error("获得消息失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
					throw new WexinReqException("获得消息失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
				} else {
					wxArticlesRespponseByMaterial = (WxArticlesRespponseByMaterial)JSONObject.toJavaObject(result,WxArticlesRespponseByMaterial.class);
				}
		}

		return wxArticlesRespponseByMaterial;
	}
	
	/**
	 * 删除永久素材 (经测试，该方法调用接口地址不对)
	 * 
	 * @param accesstoken
	 * @param mediaId
	 *            图文集合，数量不大于10
	 * @return WxArticlesRespponseByMaterial 上传图文消息素材返回结果
	 * @throws WexinReqException
	 */
	public static void deleteArticlesByMaterial(String accesstoken,String mediaId) throws WexinReqException {
			if (accesstoken != null&& !StringUtils.isEmpty(mediaId)) {
				String requestUrl = material_get_material_url.replace("ACCESS_TOKEN", accesstoken);
				WxArticlesRequestByMaterial wxArticlesRequestByMaterial = new WxArticlesRequestByMaterial();
				wxArticlesRequestByMaterial.setMediaId(mediaId);
				JSONObject obj = JSONObject.parseObject(JSON.toJSONString(wxArticlesRequestByMaterial));
				JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
				//System.out.println("微信返回的结果：" + result.toString());
				if (result.containsKey("errcode")&&result.get("errcode")!="0") {
					logger.error("删除消息失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
					throw new WexinReqException("删除消息失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
				} 
		}
	}
	
	/**
	 * 修改永久素材 
	 * 
	 * @param accesstoken
	 * @param wxUpdateArticle
	 * @throws WexinReqException
	 */
	public static void updateArticlesByMaterial(String accesstoken,WxUpdateArticle wxUpdateArticle) throws WexinReqException {
		if (accesstoken != null) {
			String requestUrl = material_update_news_url.replace("ACCESS_TOKEN", accesstoken);
			
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(wxUpdateArticle));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			//System.out.println("微信返回的结果：" + result.toString());
			if (result.containsKey("errcode")&&result.get("errcode")!="0") {
				logger.error("消息失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
				throw new WexinReqException("消息消息失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
			} 
		}
	}
	
	/**
	 * 获取素材列表 (经测试，该方法调用接口后json数据转java对象报错)
	 * 
	 * @param accesstoken,type,offset,count
	 * @param WxNews
	 * @throws WexinReqException
	 */
	public static WxNews queryArticlesByMaterial(String accesstoken,String type,int offset,int count) throws WexinReqException {
		WxNews wn = null;
		if (accesstoken != null) {
			String requestUrl = material_batchget_material_url.replace("ACCESS_TOKEN", accesstoken);
			
			JSONObject obj = new JSONObject();
			obj.put("type", type);
			obj.put("offset", offset);
			obj.put("count", count);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			//System.out.println("微信返回的结果：" + result.toString());
			if (result.containsKey("errcode")&&result.get("errcode")!="0") {
				logger.error("消息失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
				throw new WexinReqException("消息消息失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
			} else{
				wn = (WxNews) JSONObject.toJavaObject(result, WxNews.class);
			}
		}
		return wn;
	}
	/**
	 * 新增永久图文素材  (经测试，该方法不可用)
	 * @param accesstoken
	 * @param wxArticles
	 * @return
	 * @throws WexinReqException
	 */
	public static String getMediaIdByMaterial(String accesstoken, List<WxArticle> wxArticles) throws WexinReqException {

		WxArticlesResponse response = uploadArticlesByMaterial(accesstoken, wxArticles);
		if (response == null) {
			throw new WexinReqException("获取图文的mediaId失败");
		}
		return response.getMedia_id();
	}
	/**
	 * 新增其他类型永久素材  (经测试，该方法不能上传永久图片素材)
	 * 
	 * @param filePath
	 * @param fileName
	 * @param type
	 *            媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @return
	 * @throws Exception
	 */
	public static WxMediaForMaterialResponse uploadMediaFileByMaterial(String accesstoken, WxMediaForMaterial wx) throws WexinReqException {
		WxMediaForMaterialResponse mediaResource = null;
		if (accesstoken != null) {
			String requestUrl = material_add_material_url.replace("ACCESS_TOKEN", accesstoken);

			File file = new File(wx.getFilePath() + wx.getFileName());
			String contentType = WeiXinReqUtil.getFileContentType(wx.getFileName().substring(wx.getFileName().lastIndexOf(".") + 1));
			JSONObject result = WxstoreUtils.uploadMediaFile(requestUrl, file, contentType);
			if("video"==wx.getType()){
				WxDescriptionRequest wr = new WxDescriptionRequest();
				wr.setDescription(wx.getWxDescription());
				JSONObject obj = JSONObject.parseObject(JSON.toJSONString(wr));
				WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			}
			
			//System.out.println("微信返回的结果：" + result.toString());
			if (result.containsKey("errcode")) {
				logger.error("上传媒体资源失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
			} else {
				// {"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789}
				mediaResource = new WxMediaForMaterialResponse();
				if("thumb".equals(wx.getType())){
					mediaResource.setMedia_id(result.getString("thumb_media_id"));
				}else{
					mediaResource.setMedia_id(result.getString("media_id"));
				}
				mediaResource.setUrl(result.getString("url"));
			}
			// return mediaResource;
		}
		return mediaResource;
	}
	/**
	 * 永久获取多媒体资源的mediaId
	 * 
	 * @param accesstoken
	 * @param wxMedia
	 * @return
	 * @throws WexinReqException
	 */
	public static String getMediaIdForMaterial(String accesstoken, WxMediaForMaterial wxMediaForMaterial) throws WexinReqException {

		WxMediaForMaterialResponse response = uploadMediaFileByMaterial(accesstoken, wxMediaForMaterial);
		if (response == null) {
			throw new WexinReqException("获取多媒体资源的mediaId失败");
		}
		return response.getMedia_id();

	}

	//-- update-begin--Author:gengjiajia  Date:2016-11-28 for:TASK #1583 【图文管理】重写管理永久素材的接口
	/**
	 * 新增其他永久素材   
	 * @param accesstoken
	 * @param wx
	 * @return
	 * @throws WexinReqException
	 */
	public static WxMediaForMaterialResponse addMediaFileByMaterialNews(String accesstoken, String type,String filePath,String fileName) throws WexinReqException {
		WxMediaForMaterialResponse mediaResource = null;
		if (accesstoken != null) {
			String requestUrl = add_material.replace("ACCESS_TOKEN", accesstoken);
			String url = requestUrl.replace("TYPE", type);
			File file = new File(filePath + fileName);
			String contentType = WeiXinReqUtil.getFileContentType(fileName.substring(fileName.lastIndexOf(".") + 1));
			JSONObject result = WxstoreUtils.uploadMediaFileNews(url, file, contentType);
			if (result.containsKey("errcode")) {
				logger.error("新增其他永久素材 失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
				throw new WexinReqException(result.getString("errcode"));
			} else {
				logger.info("====新增其他永久素材  成功====result："+result.toString());
				mediaResource = new WxMediaForMaterialResponse();
				if("thumb".equals(type)){
					mediaResource.setMedia_id(result.getString("thumb_media_id"));
				}else{
					mediaResource.setMedia_id(result.getString("media_id"));
				}
				mediaResource.setUrl(result.getString("url"));
			}
		}
		return mediaResource;
	}
	
	/**
	 * 上传新增永久图文素材
	 * 
	 * @param accesstoken
	 * @param wxArticles
	 *            图文集合，数量不大于10
	 * @return WxArticlesResponse 上传图文消息素材返回结果
	 * @throws WexinReqException
	 */
	public static WxArticlesResponse uploadArticlesByMaterialNews(String accesstoken, List<WxArticle> wxArticles) throws WexinReqException {
		WxArticlesResponse wxArticlesResponse = null;
		if (wxArticles.size() == 0) {
			logger.error("没有上传的图文消息");
		} else if (wxArticles.size() > 10) {
			logger.error("图文消息最多为10个图文");
		} else {
			if (accesstoken != null) {
				String requestUrl = material_add_news_url.replace("ACCESS_TOKEN", accesstoken);
				WxArticlesRequest wxArticlesRequest = new WxArticlesRequest();
				wxArticlesRequest.setArticles(wxArticles);
				JSONObject obj = JSONObject.parseObject(JSON.toJSONString(wxArticlesRequest));
				JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
				if (result.containsKey("errcode")) {
					logger.error("新增永久图文素材失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
					throw new WexinReqException(result.getString("errcode"));
				} else {
					logger.info("=====新增永久图文素材成功=====result："+result.toString());
					wxArticlesResponse = new WxArticlesResponse();
					wxArticlesResponse.setMedia_id(result.getString("media_id"));
				}
			}
		}
		return wxArticlesResponse;
	}
	
	/**
	 * 修改永久素材
	 * 
	 * @param accesstoken
	 * @param wxUpdateArticle
	 * @throws WexinReqException
	 */
	public static void updateArticlesByMaterialNews(String accesstoken,WxUpdateArticle wxUpdateArticle) throws WexinReqException {
		if (accesstoken != null) {
			String requestUrl = material_update_news_url.replace("ACCESS_TOKEN", accesstoken);
			
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(wxUpdateArticle));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			if (result.containsKey("errcode")&&result.getInteger("errcode")!=0) {
				logger.error("修改永久素材失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
				throw new WexinReqException(result.getString("errcode"));
			}else{
				logger.info("=====修改永久素材成功=====");
			} 
		}
	}
	
	/**
	 * 获取永久素材  
	 * 
	 * @param accesstoken
	 * @param wxArticles
	 *            图文集合，数量不大于10
	 * @return WxArticlesResponse 上传图文消息素材返回结果
	 * @throws WexinReqException
	 */
	public static List<WxNewsArticle> getArticlesByMaterialNews(String accesstoken,String mediaId) throws WexinReqException {
		List<WxNewsArticle> wxArticleList = null;
			if (accesstoken != null) {
				String requestUrl = material_get_material_url.replace("ACCESS_TOKEN", accesstoken);
				JSONObject obj = new JSONObject();
				obj.put("media_id", mediaId);
				JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
				if (result.containsKey("errcode")) {
					logger.error("获取永久素材 失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg"));
					throw new WexinReqException(result.getString("errcode"));
				} else {
					logger.info("====获取永久素材成功====result:"+result.toString());
					JSONArray newsItemJsonArr = result.getJSONArray("news_item");
					wxArticleList = newsItemJsonArr.toJavaList(WxNewsArticle.class);
				}
		}
		return wxArticleList;
	}
	
	/**
	 * 获取素材列表
	 * 
	 * @param accesstoken,type,offset,count
	 * @param WxNews
	 * @throws WexinReqException
	 */
	public static WxNews queryArticlesByMaterialNews(String accesstoken,String type,int offset,int count) throws WexinReqException {
		WxNews news = new WxNews();
		if (accesstoken != null) {
			String requestUrl = material_batchget_material_url.replace("ACCESS_TOKEN", accesstoken);
			
			JSONObject obj = new JSONObject();
			obj.put("type", type);
			obj.put("offset", offset);
			obj.put("count", count);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			if (result.containsKey("errcode")&&result.getInteger("errcode")!=0) {
				logger.error("=====获取素材列表失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg")+"=====");
				throw new WexinReqException(result.getString("errcode"));
			} else{
				logger.info("=====获取素材列表成功！result:"+result.toString()+"=====");
				JSONArray jsonArray = result.getJSONArray("item");
				Object[] itemArr = jsonArray.toArray();
				List<WxItem> wxItems = new ArrayList<WxItem>();
				for (int i = 0; i < itemArr.length; i++) {
					WxItem wxItem = new WxItem();
					Object itemObj = itemArr[i];
					JSONObject itemJson = JSONObject.parseObject(JSON.toJSONString(itemObj));
					String mediaId = itemJson.getString("media_id");
					Object newsItemObj = itemJson.get("content");
					JSONObject newsItemJson = JSONObject.parseObject(JSON.toJSONString(newsItemObj));
					JSONArray newsItemJsonArr = newsItemJson.getJSONArray("news_item");
					List<WxNewsArticle> wxArticleList = newsItemJsonArr.toJavaList(WxNewsArticle.class);
					wxItem.setContents(wxArticleList);
					wxItem.setMedia_id(mediaId);
					if(itemJson.containsKey("name")){
						wxItem.setName("name");
					}
					wxItem.setUpdate_time(itemJson.getString("update_time"));
					wxItems.add(wxItem);
				}
				news.setItems(wxItems);
			}
		}
		return news;
	}
	
	/**
	 * 删除永久素材
	 * 
	 * @param accesstoken
	 * @param mediaId
	 *            图文集合，数量不大于10
	 * @return WxArticlesRespponseByMaterial 上传图文消息素材返回结果
	 * @throws WexinReqException
	 */
	public static void deleteArticlesByMaterialNews(String accesstoken,String mediaId) throws WexinReqException {
			if (accesstoken != null&&!StringUtils.isEmpty(mediaId)) {
				String requestUrl = material_del_news_url.replace("ACCESS_TOKEN", accesstoken);
				JSONObject obj = new JSONObject();
				obj.put("media_id", mediaId);
				JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
				if (result.containsKey("errcode")&&result.getInteger("errcode")!=0) {
					logger.error("=====删除永久素材失败！errcode=" + result.getString("errcode") + ",errmsg = " + result.getString("errmsg")+"======");
					throw new WexinReqException(result.getString("errcode"));
				}else{
					logger.info("=====删除永久素材成功=====");
				}
		}
	}
	//-- update-end--Author:gengjiajia  Date:2016-11-28 for:TASK #1583 【图文管理】重写管理永久素材的接口
}
