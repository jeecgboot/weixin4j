package org.jeewx.api.wxshop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.jeewx.api.core.common.JSONHelper;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.coupon.location.model.BaseInfo;
import org.jeewx.api.wxshop.model.Business;
import org.jeewx.api.wxshop.model.BusinessReq;
import org.jeewx.api.wxshop.model.PoiId;
import org.jeewx.api.wxshop.model.ShopRtnInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * 微信门店 	
 * @author lihongxuan
 *
 */
public class JwShopAPI {
	// 创建门店
	private static String create_shop_url = "https://api.weixin.qq.com/cgi-bin/poi/addpoi?access_token=TOKEN";
	// 查询门店信息
	private static String get_shop_url = "https://api.weixin.qq.com/cgi-bin/poi/getpoi?access_token=TOKEN";
	// 查询门店列表
	private static String search_shop_url = "https://api.weixin.qq.com/cgi-bin/poi/getpoilist?access_token=TOKEN";
	// 删除门店
	private static String del_shop_url = "https://api.weixin.qq.com/cgi-bin/poi/delpoi?access_token=TOKEN";
	// 上传图片
	private static String upload_img_shop_url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
	// 修改门店服务信息
	private static String updatebystatus_shop_url = "https://api.weixin.qq.com/cgi-bin/poi/updatepoi?access_token=TOKEN";
	// 门店类目表
	private static String shop_category_url = "https://api.weixin.qq.com/cgi-bin/api_getwxcategory?access_token=TOKEN";
		
	
	/**
	 * 增加门店
	 */
	public static String getShopCategorys(String newAccessToken) {
		if (newAccessToken != null) {
			String requestUrl = shop_category_url.replace("ACCESS_TOKEN", newAccessToken);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "GET",null);
			JSONArray info = result.getJSONArray("category_list");
			String str = null;
			str = JSONHelper.toBean(info, String.class);
			return str;
		}
		return null;
	}
	/**
	 * 增加门店
	 */
	public static ShopRtnInfo doAddshop(String newAccessToken, Business business) {
		if (newAccessToken != null) {
			String requestUrl = create_shop_url.replace("ACCESS_TOKEN", newAccessToken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(business));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			ShopRtnInfo shopRtnInfo = (ShopRtnInfo)JSONObject.toJavaObject(result, ShopRtnInfo.class);
			return shopRtnInfo;
		}
		return null;
	}
	
	/**
	 * 修改门店服务信息
	 */
	public static ShopRtnInfo updateShop(String newAccessToken,  Business business) {
		if (newAccessToken != null) {
			String requestUrl  = updatebystatus_shop_url.replace("ACCESS_TOKEN", newAccessToken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(business));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			ShopRtnInfo shopRtnInfo = (ShopRtnInfo)JSONObject.toJavaObject(result, ShopRtnInfo.class);
			return shopRtnInfo;
		}
		return null;
	}
	
	/**
	 * 删除门店
	 */
	public static ShopRtnInfo delShop(String newAccessToken,  PoiId poiId) {
		if (newAccessToken != null) {
			String requestUrl  = del_shop_url.replace("ACCESS_TOKEN", newAccessToken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(poiId));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			ShopRtnInfo shopRtnInfo = (ShopRtnInfo)JSONObject.toJavaObject(result, ShopRtnInfo.class);
			return shopRtnInfo;
		}
		return null;
	}


	/**
	 * 查询门店信息
	 */
	public static BaseInfo getshop(String newAccessToken, PoiId poiId) {
		if (newAccessToken != null) {
			String requestUrl = get_shop_url.replace("ACCESS_TOKEN", newAccessToken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(poiId));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			JSONObject info = result.getJSONObject("business");
			BaseInfo baseInfo = null;
			baseInfo = (BaseInfo)JSONObject.toJavaObject(info, BaseInfo.class);
			return baseInfo;
		}
		return null;
	}
	
	/**
	 * 查询门店列表
	 */
	public static List<BaseInfo> getshops(String newAccessToken, BusinessReq businessReq) {
		if (newAccessToken != null) {
			String requestUrl = search_shop_url.replace("ACCESS_TOKEN", newAccessToken);
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(businessReq));
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			// 正常返回
			List<BaseInfo> baseInfos = null;
			JSONArray info = result.getJSONArray("business_list");
			baseInfos = JSONHelper.toList(info, BaseInfo.class);
			return baseInfos;
		}
		return null;
	}
	
	
	
	
	/**
	 * 上传图片
	 * @param fileName
	 * @param accountid
	 * @return
	 */
	public static String uploadImg(String newAccessToken, String filePath, String fileName) {
		if (newAccessToken != null) {
			String requestUrl = upload_img_shop_url.replace("ACCESS_TOKEN", newAccessToken);
			byte[] fileByte;
			try {
				fileByte = fileData(filePath+fileName);
				JSONObject result = WxstoreUtils.httpRequest2(requestUrl, "POST", fileByte);
				if (result.getInteger("errcode") == 0) {
					return result.getString("url");
				}
				return "";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	private static byte[] fileData(String filePath) throws IOException {
		File file = new File(filePath);//存放照片的文件
		InputStream fis = null;
		byte[] imageByteArray = null;
		fis = new FileInputStream(file);
		imageByteArray= IOUtils.toByteArray(fis);
		return imageByteArray;
	}

	
	
}
 