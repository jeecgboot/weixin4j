package org.jeewx.api.core.req;

import com.alibaba.fastjson.JSONObject;
import org.jdom.JDOMException;
import org.jeewx.api.core.annotation.ReqType;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.handler.WeiXinReqHandler;
import org.jeewx.api.core.req.model.DownloadMedia;
import org.jeewx.api.core.req.model.WeixinReqConfig;
import org.jeewx.api.core.req.model.WeixinReqParam;
import org.jeewx.api.core.util.WeiXinConstant;
import org.jeewx.api.core.util.WeiXinReqUtil;

import java.io.IOException;

/**
 * 获取微信接口的信息
 * 
 * @author liguo
 * 
 */
public class WeiXinReqService {

	private static WeiXinReqService weiXinReqUtil = null;


	private WeiXinReqService() {
		String realPath = WeiXinReqService.class.getClassLoader().getResource("").getFile();
		try {
			WeiXinReqUtil.initReqConfig("weixin-reqcongfig.xml");
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/**
	 * 获取公共的调用处理
	 * @return
	 */
	public static WeiXinReqService getInstance() {
		if (weiXinReqUtil == null) {
			// 同步块，线程安全的创建实例
			synchronized (WeiXinReqService.class) {
				// 再次检查实例是否存在，如果不存在才真正的创建实例
				if (weiXinReqUtil == null) {
					weiXinReqUtil = new WeiXinReqService();
				}
			}
		}
		return weiXinReqUtil;
	}

	/**
	 * 传入请求的参数，获取对应额接口返回信息
	 * @param weixinReqParam
	 * @return
	 * @throws WexinReqException
	 */
	public String doWeinxinReq(WeixinReqParam weixinReqParam)
			throws WexinReqException {
		String strReturnInfo = "";
		if (weixinReqParam.getClass().isAnnotationPresent(ReqType.class)) {
			ReqType reqType = weixinReqParam.getClass().getAnnotation(ReqType.class);
			WeixinReqConfig objConfig = WeiXinReqUtil.getWeixinReqConfig(reqType.value());
			WeiXinReqHandler handler = WeiXinReqUtil.getMappingHander(objConfig.getMappingHandler());
			strReturnInfo = handler.doRequest(weixinReqParam);
		}
		return strReturnInfo;
	}
	
	/**
	 * 返回json对象
	 * @param weixinReqParam
	 * @return
	 * @throws WexinReqException 
	 */
	public JSONObject doWeinxinReqJson(WeixinReqParam weixinReqParam) throws WexinReqException{
		String strResult = this.doWeinxinReq(weixinReqParam);
		JSONObject result = JSONObject.parseObject(strResult);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		if(error !=null && Integer.parseInt(error.toString())!=0){
			throw new WexinReqException(result.toString());
		}
		return result;
	}

	public static void main(String[] args) {
		String ddd = "";
		try {
			// GroupGet g = new GroupGet();
			/*
			 * GroupUpdate g = new GroupUpdate(); Group gg = new Group();
			 * gg.setId("111"); gg.setName("test修改"); g.setGroup(gg);*/
			DownloadMedia g = new DownloadMedia();
			g.setFilePath("H:/temp");
			g.setMedia_id("nH-tzebPcZY41Hlao3mjPHpUHHJSIbfP8hbGJy73LUj5BfvVDV9b84uIpZ8Yjlzw");
			g.setAccess_token("bbkXUUyC6F85R_vh7FOokDZn54P4jY6RVg8rvtzd0D10nIgd7Ksg7bBc8mncX6SZ1mMEI7v1q1OBtWoWG8--iR6ohe3kXbx5jUTHGAjGPAU");
			ddd = WeiXinReqService.getInstance().doWeinxinReq(g);
			// test.getKfaccountList("NG0cpHAPJix5bULT26Hvk9pX5ZOqleIObl9HNKUfPA2PIxJzf-X4U-YOGP4vo-rdwvCy3GCn7v9GNTXNWVM27qEQz-Xs3fgAnj0kdhL07gI");
			// test.getServieIp("QsLy729ukRchgw4O3bQvO2UwD0vn2zQ1I0TjZa2kx-dGX9TEFuVCGd7K9AsBhdfynUAaEWVILeDNS7OQXTKZdX1YxbnNqyVBfDmW9I63WWc");
			// test.getUploadMedia("QsLy729ukRchgw4O3bQvO2UwD0vn2zQ1I0TjZa2kx-dGX9TEFuVCGd7K9AsBhdfynUAaEWVILeDNS7OQXTKZdX1YxbnNqyVBfDmW9I63WWc");
			// ddd =
			// test.getDownMedia("QsLy729ukRchgw4O3bQvO2UwD0vn2zQ1I0TjZa2kx-dGX9TEFuVCGd7K9AsBhdfynUAaEWVILeDNS7OQXTKZdX1YxbnNqyVBfDmW9I63WWc");
			System.out.println(ddd);
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
