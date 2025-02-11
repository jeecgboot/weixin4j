package com.jeecg.qywx.api.core.util;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.StringUtils;
import com.jeecg.qywx.api.base.JwAccessTokenAPI;
import com.jeecg.qywx.api.base.JwParamesAPI;
import com.jeecg.qywx.api.core.common.AccessToken;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class WXUpload {
	private static final String upload_wechat_url = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	/**
	 * 
	 * @param accessToken
	 * @param type 有image 类型
	 * @param fileUrl 一定要注意  这个类型为全路径名称哦
	 * @return
	 */
	public static JSONObject upload(String accessToken, String type, String fileUrl) {
		JSONObject jsonObject = null;
		String last_wechat_url = upload_wechat_url.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		// 定义数据分割符
		String boundary = "----------sunlight";
		try {
			URL uploadUrl = new URL(last_wechat_url);
			HttpURLConnection uploadConn = (HttpURLConnection) uploadUrl.openConnection();
			uploadConn.setDoOutput(true);
			uploadConn.setDoInput(true);
			uploadConn.setRequestMethod("POST");
			// 设置请求头Content-Type
			uploadConn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			// 获取媒体文件上传的输出流（往微信服务器写数据）
			OutputStream outputStream = uploadConn.getOutputStream();

			URL mediaUrl = new URL(fileUrl);
			HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl.openConnection();
			meidaConn.setDoOutput(true);
			meidaConn.setRequestMethod("GET");

			// 从请求头中获取内容类型
			String contentType = meidaConn.getHeaderField("Content-Type");
			String filename=getFileName(fileUrl,contentType);
			// 请求体开始
			outputStream.write(("--" + boundary + "\r\n").getBytes());
			outputStream.write(String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n", filename).getBytes());
			outputStream.write(String.format("Content-Type: %s\r\n\r\n", contentType).getBytes());

			// 获取媒体文件的输入流（读取文件）
			BufferedInputStream bis = new BufferedInputStream(meidaConn.getInputStream());
			byte[] buf = new byte[1024 * 8];
			int size = 0;
			while ((size = bis.read(buf)) != -1) {
				// 将媒体文件写到输出流（往微信服务器写数据）
				outputStream.write(buf, 0, size);
			}
			// 请求体结束
			outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
			outputStream.close();
			bis.close();
			meidaConn.disconnect();

			// 获取媒体文件上传的输入流（从微信服务器读数据）
			InputStream inputStream = uploadConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			StringBuffer buffer = new StringBuffer();
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			uploadConn.disconnect();
			// 使用json解析
//			jsonObject = JSONObject.fromObject(buffer.toString());
			jsonObject = JSONObject.parseObject(buffer.toString());
			System.out.println("jsonobject="+jsonObject);
		} catch (Exception e) {
			System.out.println("上传文件失败！");
			e.printStackTrace();
		}
		return jsonObject;
	}

	public static String getFileName(String fileUrl,String contentType) {
		String filename="";
		if (fileUrl != null && !"".equals(fileUrl)) {
			if(fileUrl.contains(".")){
				filename = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
			}else{
				if(contentType==null || "".equals(contentType)){
					return "";
				}
				String fileExt="";
				if ("image/jpeg".equals(contentType)) {
					fileExt = ".jpg";
				} else if ("audio/mpeg".equals(contentType)) {
					fileExt = ".mp3";
				} else if ("audio/amr".equals(contentType)) {
					fileExt = ".amr";
				} else if ("video/mp4".equals(contentType)) {
					fileExt = ".mp4";
				} else if ("video/mpeg4".equals(contentType)) {
					fileExt = ".mp4";
				} else if ("text/plain".equals(contentType)) {
					fileExt = ".txt";
				} else if ("text/xml".equals(contentType)) {
					fileExt = ".xml";
				} else if ("application/pdf".equals(contentType)) {
					fileExt = ".pdf";
				} else if ("application/msword".equals(contentType)) {
					fileExt = ".doc";
				} else if ("application/vnd.ms-powerpoint".equals(contentType)) {
					fileExt = ".ppt";
				} else if ("application/vnd.ms-excel".equals(contentType)) {
					fileExt = ".xls";
				}
				filename="Media文件"+fileExt;
			}
		}
		return filename;
	}
	
	/**
	 * 
	 * @param data
	 *            要写入的数据
	 * @param dir
	 *            目录
	 * @param filename
	 *            文件名称
	 * @param cover
	 *            是否覆盖
	 * @throws IOException
	 *             文件流错误
	 * @throws Exception
	 *             自定义异常
	 */
	public static void writeFile(byte[] data, String dir, String filename,
			boolean cover) {
		try {
			if (StringUtils.isEmpty(dir)) {
				throw new Exception("目录不能为空");
			}
			if (StringUtils.isEmpty(filename)) {
				throw new Exception("文件名称不能为空");
			}
			File dirfile = new File(dir);
			if (dirfile.isFile()) {
				throw new Exception("目录不能是文件");
			}
			if (!dirfile.exists()) {
				// 创建目录
				dirfile.mkdirs();
			}
			File file = new File(dir + "//" + filename);
			if (file.exists()) {
				if (cover) {
					file.setWritable(true);
					BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
							new FileOutputStream(file));
					bufferedOutputStream.write(data);
					bufferedOutputStream.close();
				} else {
					return;
				}
			} else {
				file.setWritable(true);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
						new FileOutputStream(file));
				bufferedOutputStream.write(data);
				bufferedOutputStream.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static void main(String[] args) {
		AccessToken accessToken = JwAccessTokenAPI.getAccessToken(JwParamesAPI.corpId,JwParamesAPI.secret);
//		JSONObject object = WXUpload.upload(accessToken.getAccesstoken(), "image", "file:///D:/shjjj.jpg");
		JSONObject object = WXUpload.upload(accessToken.getAccesstoken(), "image", "http://127.0.0.1:80/jeecg-p3-web/upload/shjjj.jpg");
		System.out.println(object.toString());
		
		/*try {
			URL mediaUrl = new URL("file://D:/shjjj.jpg");
			System.out.println( mediaUrl.openConnection());
			HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl.openConnection();
			meidaConn.setDoOutput(true);
			meidaConn.setRequestMethod("GET");
//			System.out.println(fileUrl.openConnection());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//jsonobject={"created_at":1461249116,"media_id":"1Njk4eIayIxlo9NK7o17ze3GAu1nrOd4Xz9qXCSUQ0UWl59e2jf4-RAIpq_iZyhbXFAI98cN2blBS0ltMg9i0eg","type":"image"}
//		{"created_at":1461249116,"media_id":"1Njk4eIayIxlo9NK7o17ze3GAu1nrOd4Xz9qXCSUQ0UWl59e2jf4-RAIpq_iZyhbXFAI98cN2blBS0ltMg9i0eg","type":"image"}

	}
	
}
