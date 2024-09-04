package org.jeewx.api.core.common;

import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.core.exception.WexinReqException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 
 * @author xiaoqingfeng
 * 微信url请求公用方法
 * @
 */
public class WxstoreUtils {
	private static Logger logger = LoggerFactory.getLogger(WxstoreUtils.class);

	public static JSONObject httpRequest(String requestUrl,
										 String requestMethod, String outputStr) {
		logger.debug("*********HTTPREQUEST START********");
		logger.debug("*********requestUrl is "+
				requestUrl+" END AND requestMethod IS"
				+requestMethod + " END AND  outputStr" 
				+outputStr +" END ********");
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());

			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}

			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();

				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();

			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
			if (jsonObject.containsKey("errcode") && jsonObject.getInteger("errcode") != 0) {
				logger.debug("********* ERROR********{}",buffer.toString());
				logger.debug("*********HTTPREQUEST END********");
				throw new WexinReqException("httpRequest Method！errcode="
						+ jsonObject.getString("errcode") + ",errmsg = "
						+ jsonObject.getString("errmsg"));
			} else {
				logger.debug("********* SUCCESS END********");
			}
		} catch (ConnectException ce) {
			System.out.println("Weixin server connection timed out.");
		} catch (Exception e) {
			System.out.println("https request error:{}" + e.getMessage());
		}
		return jsonObject;
	}

	public static JSONObject httpRequest2(String requestUrl,
			String requestMethod, byte[] outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());

			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}

			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();

				outputStream.write(outputStr);
				outputStream.close();
			}

			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();

			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			System.out.print("Weixin server connection timed out.");
		} catch (Exception e) {
			System.out.print("https request error:{}" + e.getMessage());
		}
		return jsonObject;
	}

	public static void saveHttpImage(String requestUrl, String requestMethod,
			String outputStr, File target) {
		try {
			URL url = new URL(requestUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod(requestMethod);

			conn.setConnectTimeout(5000);

			InputStream inStream = conn.getInputStream();

			byte[] data = readInputStream(inStream);

			FileOutputStream outStream = new FileOutputStream(target);

			outStream.write(data);

			outStream.close();
		} catch (Exception e) {
		}
	}

	public static String encode(byte[] bstr) {
		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(bstr);
	}

	public static byte[] decode(String str) {
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] buf = decoder.decode(str);
		return buf;
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outstream.write(buffer, 0, len);
		}
		outstream.close();
		inStream.close();

		return outstream.toByteArray();
	}

	/**
	 * 上传多媒体文件到微信公众平台
	 *
	 * @param requestUrl
	 * 
	 * @param file
	 *            文件
	 * @param content_type
	 *            文件类型
	 * @return 返回的字符串
	 * @throws Exception
	 */
	public static JSONObject uploadMediaFile(String requestUrl, File file,
			String content_type) {

		JSONObject jsonObject = null;
		StringBuffer bufferStr = new StringBuffer();
		String end = "\r\n";
		String twoHyphens = "--"; // 用于拼接
		String boundary = "*****"; // 用于拼接 可自定义
		URL submit = null;
		DataOutputStream dos = null;
		// FileInputStream fis = null;
		BufferedInputStream bufin = null;
		BufferedReader bufferedReader = null;
		try {
			submit = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) submit.openConnection();
//			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			
			conn.setConnectTimeout(10000); //连接超时为10秒
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);

			// 获取输出流对象，准备上传文件
			dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			dos.writeBytes("Content-Disposition: form-data; name=\"" + file+ "\";filename=\"" + file.getName() + ";Content-Type=\""+ content_type + end);
			dos.writeBytes(end);
			// 对文件进行传输
			bufin = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			while ((count = bufin.read(buffer)) != -1) {
				dos.write(buffer, 0, count);
			}

			bufin.close(); // 关闭文件流

			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();

			// 读取URL链接返回字符串
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				bufferStr.append(str);
			}

			jsonObject = JSONObject.parseObject(bufferStr.toString());
			// System.out.println("-------------读取URL链接返回字符串--------------" +
			// bufferStr.toString());

		} catch (Exception e) {
			//e.printStackTrace();
			System.err.println("异常错误:" + e.toString());
			System.err.println("连接地址是:" + requestUrl);
			// throw new Exception("微信服务器连接错误！" + e.toString());
		} finally {

			try {
				if (dos != null) {
					dos.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}

			} catch (Exception e2) {

			}
		}
		// 获取到返回Json请自行根据返回码获取相应的结果
		return jsonObject;
	}
	
	//-- update-begin--Author:gengjiajia  Date:2016-11-28 for:TASK #1583 【图文管理】重写上传永久图片接口
	/**
	 * 上传多媒体文件到微信公众平台  图文素材上传使用
	 *
	 * @param requestUrl
	 * 
	 * @param file
	 *            文件
	 * @param content_type
	 *            文件类型
	 * @return 返回的字符串
	 * @throws Exception
	 */
	public static JSONObject uploadMediaFileNews(String requestUrl, File file,
			String content_type) {
		String result = null;
		JSONObject jsonObject = null;
		URL submit = null;
		if (!file.exists() || !file.isFile()) {
			logger.info("------------文件不存在------------------------");
		} else {
			try {
				submit = new URL(requestUrl);
				HttpURLConnection conn = (HttpURLConnection) submit.openConnection();
	//			conn.setDoInput(true);
				conn.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false); // post方式不能使用缓存
				conn.setRequestProperty("Connection", "Keep-Alive");// 设置请求头信息
				conn.setRequestProperty("Charset", "UTF-8");
				String BOUNDARY = "----------" + System.currentTimeMillis();// 设置边界
				conn.setRequestProperty("Content-Type",
						"multipart/form-data; boundary=" + BOUNDARY);
				// 请求正文信息
				// 第一部分：
				StringBuilder sb = new StringBuilder();
				sb.append("--"); // 必须多两道线
				sb.append(BOUNDARY);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data;name=\"media\";filename=\""
						+ file.getName() + "\"\r\n");
				sb.append("Content-Type:application/octet-stream\r\n\r\n");
				byte[] head = sb.toString().getBytes("utf-8");
				// 获得输出流
				OutputStream out = new DataOutputStream(conn.getOutputStream());
				// 输出表头
				out.write(head);
				// 文件正文部分
				// 把文件已流文件的方式 推入到url中
				DataInputStream in = new DataInputStream(new FileInputStream(
						file));
				int bytes = 0;
				byte[] bufferOut = new byte[1024];
				while ((bytes = in.read(bufferOut)) != -1) {
					out.write(bufferOut, 0, bytes);
				}
				//out.write(("--" + BOUNDARY + "\r\n").getBytes());
				//out.write("Content-Disposition: form-data; name=\"description\";\r\n\r\n".getBytes());
				//out.write(String.format("{\"title\":\"%s\", \"introduction\":\"%s\"}", title,introduction).getBytes());
				in.close();
				// 结尾部分
				byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n")
						.getBytes("utf-8");// 定义最后数据分隔线
				out.write(foot);
				out.flush();
				out.close();
				StringBuffer buffer = new StringBuffer();
				BufferedReader reader = null;
				try {
					// 定义BufferedReader输入流来读取URL的响应
					reader = new BufferedReader(new InputStreamReader(
							conn.getInputStream()));
					String line = null;
					while ((line = reader.readLine()) != null) {
						buffer.append(line);
					}
					if (result == null) {
						result = buffer.toString();
					}
				} catch (IOException e) {
					logger.info("发送POST请求出现异常！"
							+ e);
					e.printStackTrace();
					throw new IOException("数据读取异常");
				} finally {
					if (reader != null) {
						reader.close();
					}
				}
				jsonObject = JSONObject.parseObject(result);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}
	//-- update-end--Author:gengjiajia  Date:2016-11-28 for:TASK #1583 【图文管理】重写上传永久图片接口
	public static void main(String[] args) {
		String url = "https://api.weibo.com/oauth2/access_token?client_id=3016130400&client_secret=70f22fb9106282e6b5fb55a354f6da43&grant_type=authorization_code&code=9c15c07ea807274c7c413e1d9055d92d&redirect_uri=http://open.weibo.com";
		JSONObject httpRequest = WxstoreUtils.httpRequest(url, "POST", null);
		System.err.println(httpRequest);
	}
}
