package com.jeecg.dingtalk.api.core.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson2.JSONFactory;
import com.jeecg.dingtalk.api.core.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author zhoujf
 *
 */
public class HttpUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);


	/**
	 * http get请求
	 * @param url
	 * @return
	 */
	public static JSONObject sendGet(String url) {
		JSONObject jsonObject = null;
		jsonObject = httpRequest(url,"GET",null);
		return jsonObject;
	}


	/**
	 * http post请求
	 * @param url
	 * @return
	 */
	public static JSONObject sendPost(String url) {
		JSONObject jsonObject = null;
		jsonObject = httpRequest(url,"POST",null);
		return jsonObject;
	}

	/**
	 * http post请求
	 * @param url
	 * @param output json串
	 * @return
	 */
	public static JSONObject sendPost(String url,String output) {
		JSONObject jsonObject = null;
		jsonObject = httpRequest(url,"POST",output);
		return jsonObject;
	}

	/**
	 * http post请求
	 * @param url 请求地址
	 * @param output json串
	 */
	public static <T> Response<T> post(String url, String output, Type... types) {
		JSONObject json = httpRequest(url, "POST", output);
		return toJavaObject(json, new TypeReference<Response<T>>(types){});
	}

	/**
	 * 重写fastjson的toJavaObject方法
	 * 解决问题： “ class com.alibaba.fastjson2.JSONObject cannot be cast to class com.alibaba.fastjson.JSONObject ”
	 * @param json
	 * @param typeReference
	 * @return
	 * @param <T>
	 */
	public static <T> T toJavaObject(JSONObject json, TypeReference<T> typeReference) {
		Type type = (Type)(typeReference != null ? typeReference.getType() : Object.class);
		if (type instanceof Class) {
			return json.toJavaObject(typeReference);
		} else {
			String str = com.alibaba.fastjson.JSON.toJSONString(json);
			return (T)com.alibaba.fastjson.JSON.parseObject(str, type);
		}
	}

	public static JSONObject httpRequest(String request, String requestMethod, String output) {
		return httpRequest(request, requestMethod, output, null);
	}

	/**
	 * 发起https请求并获取结果
	 *
	 * @param request       请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param output        提交的数据
	 * @param headers       请求头
	 * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String request, String requestMethod, String output, JSONObject headers) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		try {
			logger.debug("[HTTP] http请求request:{},method:{},output{}", new Object[]{request,requestMethod,output});
			//建立连接
			URL url = new URL(request);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(30000);
			connection.setUseCaches(false);
			connection.setRequestMethod(requestMethod);

			// begin 设置请求头
			if (headers != null && headers.size() > 0) {
				for (String key : headers.keySet()) {
					connection.setRequestProperty(key, headers.getString(key));
				}
			}
			if ("POST".equalsIgnoreCase(requestMethod)) {
				connection.setRequestProperty("Accept", "application/json;charset=UTF-8");
				connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			}
			// end 设置请求头

			if(output!=null){
				OutputStream out = connection.getOutputStream();
				out.write(output.getBytes("UTF-8"));
				out.close();
			}
			//流处理
			inputStream = connection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String line;
			while((line=reader.readLine())!=null){
				buffer.append(line);
			}
			//关闭连接、释放资源
			reader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			connection.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[HTTP] http请求error:{}", new Object[]{e.getMessage()});
		}finally {
			// 使用finally块来关闭输出流、输入流
			try {
				if (reader != null) {
					reader.close();
				}
				if(inputStreamReader!=null){
					inputStreamReader.close();
				}
				if(inputStream!=null){
					inputStream.close();
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("[HTTP] http请求error:{}", new Object[]{ex.getMessage()});
			}
		}
		return jsonObject;
	}
}
