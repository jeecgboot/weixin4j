package org.jeewx.api.ai;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;
import org.jeewx.api.ai.model.Voice;
import org.jeewx.api.core.util.WeiXinConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信--AI开放接口
 * 
 */
public class JwAIApi {

	private static Logger logger = LoggerFactory.getLogger(JwAIApi.class);

	/**
	 * 提交语音
	 */
	public static final String ADD_VOICE_URL = "https://api.weixin.qq.com/cgi-bin/media/voice/addvoicetorecofortext?";
	
	/**
	 * 获取语音翻译结果
	 */
	public static final String VOICE_RESULT_URL = "https://api.weixin.qq.com/cgi-bin/media/voice/queryrecoresultfortext?";
	
	
	/**
	 * 翻译接口
	 */
	public static final String TRANSLATE_TEXT_URL = "https://api.weixin.qq.com/cgi-bin/media/voice/translatecontent?";

	
	/**
	 * 微信翻译语音<br>
	 * 参数voice请用带参构造器创建对象,若用默认构造器则需要赋值属性有:
	 * accessToken，
	 * format（只支持mp3，16k，单声道，最大1M）,
	 * voice_id(语音唯一标识),
	 * file(文件地址),
	 * lang(语言，zh_CN 或 en_US，默认中文 非必填)
	 * @return
	 */
	public static String translateVoice(Voice voice){
		JSONObject json = uploadVoice(voice);
		if(json!=null && json.containsKey("errcode")&&"0".equals(json.getString("errcode"))){
			try {
				Thread.sleep(voice.getWaitMill());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return getVoiceResult(voice.getAccessToken(), voice.getVoice_id(),voice.getLang());
		}
		return null;
	}
	
	/**
	 * 微信翻译
	 * @param accessToken
	 * @param lfrom 源语言，zh_CN 或 en_US
	 * @param lto 目标语言，zh_CN 或 en_US
	 * @param text 需要翻译的文字
	 * @return
	 */
	public static String translateText(String accessToken,String lfrom,String lto,String text){
		String result = null;
		String requestUrl = TRANSLATE_TEXT_URL+"access_token="+accessToken+"&lfrom="+lfrom+"&lto="+lto;
		logger.info("------微信翻译地址----"+ requestUrl);
		logger.info("------微信翻译原文----"+ text);
		JSONObject obj = httpRequest(requestUrl, "POST", text);
		if(obj!=null && obj.containsKey("to_content")){
			result =  obj.getString("to_content");
		}
		logger.info("------微信翻译结果----"+ result);
		return result;
	}
	
	
	/**
	 * 提交语音
	 * @param voice
	 * @return
	 */
	private static JSONObject uploadVoice(Voice voice){
		JSONObject jsonobject = new JSONObject();
		String result = null;
		File file = new File(voice.getFile());
		if(!file.exists()||!file.isFile()){
			jsonobject = null;
			logger.info("--提交语音接口，文件不存在------");
		}else{
			HttpURLConnection con =null;
			OutputStream out =null;
			DataInputStream in = null;
			String requestUrl = voice.getReqestUrl(ADD_VOICE_URL);
			logger.info("--提交语音接口请求："+requestUrl);
			try {
				URL urlObj = new URL(requestUrl);  
				con = (HttpURLConnection) urlObj.openConnection();  
				con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式  
			    con.setDoInput(true);  
			    con.setDoOutput(true);  
			    con.setUseCaches(false); // post方式不能使用缓存  
			    
			    //设置网络超时
                con.setConnectTimeout(8000);  
                con.setReadTimeout(8000);
                
                con.setRequestProperty("Connection", "Keep-Alive");// 设置请求头信息
			    con.setRequestProperty("Charset", "UTF-8");  
			    String BOUNDARY = "----------" + System.currentTimeMillis();// 设置边界  
			    con.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ BOUNDARY);
			 // 请求正文信息  
			    // 第一部分：  
			    StringBuilder sb = new StringBuilder();  
			    sb.append("--"); // 必须多两道线  
			    sb.append(BOUNDARY);  
			    sb.append("\r\n");  
			    
			    sb.append("Content-Disposition: form-data;name=\"media\";filelength=\""+file.length()+"\";filename=\""+ file.getName() + "\"\r\n");  
			    sb.append("Content-Type:application/octet-stream\r\n\r\n");  
			    byte[] head = sb.toString().getBytes("utf-8");  
			    // 获得输出流  
			    out = new DataOutputStream(con.getOutputStream());  
			    // 输出表头  
			    out.write(head);  
			    // 文件正文部分  
			    // 把文件已流文件的方式 推入到url中  
			    in = new DataInputStream(new FileInputStream(file));  
			    int bytes = 0;  
			    byte[] bufferOut = new byte[1024];  
			    while ((bytes = in.read(bufferOut)) != -1) {  
			    	out.write(bufferOut, 0, bytes);  
			    }  
			    in.close();  
			    // 结尾部分  
			    byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线  
			    out.write(foot);  
			    out.flush();  
			    out.close();  
			    StringBuffer buffer = new StringBuffer();  
			    BufferedReader reader = null;
			    
			    try {
			    	// 定义BufferedReader输入流来读取URL的响应  
			        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));  
			        String line = null;  
			        while ((line = reader.readLine()) != null) {  
			        	buffer.append(line);  
			        } 
			       
			        if(result==null){  
			        	result = buffer.toString();  
			        }
			        logger.info("--提交语音接口返回："+buffer.toString());
				} catch (Exception e) {
					logger.info(e.getMessage());
					e.printStackTrace();
				}finally {  
			        if(reader!=null){  
			        	reader.close();  
			        }  
				}
			    jsonobject = JSONObject.parseObject(result);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}finally{
	        	try {
	        		if(in!=null){
	        			in.close();
					}
					if(out!=null){
						out.close();
					}
					if(con!=null){
						con.disconnect();
					}
				} catch (Exception e) {
				}
	        }
		}
		return jsonobject;
	}
	
	/**
	 * 获取语音识别结果
	 * @param accessToken
	 * @param voice_id
	 * @param lang
	 * @return
	 */
	private static String getVoiceResult(String accessToken,String voice_id,String lang){
		lang = (lang==null ||"".equals(lang))?"zh_CN":lang;
		String requestUrl = VOICE_RESULT_URL+"access_token="+accessToken+"&voice_id="+voice_id+"&lang="+lang;
		logger.info("--获取语音识别结果请求参数："+requestUrl);
		JSONObject result =httpRequest(requestUrl, "POST",null);
		if(result!=null){
			logger.info("--获取语音识别结果---"+result);
		}else{
			logger.info("--获取语音识别结果为空---");
		}
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		if(error == null){
			return result.getString("result");
		}
		return null;
	}
	
	private static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		HttpURLConnection httpUrlConn = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			URL url = new URL(requestUrl);
		    httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setConnectTimeout(500000);
     	//	httpUrlConn.setRequestProperty("content-type", "application/json");
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
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
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			logger.info(buffer.toString());
			//jsonObject = JSONObject.parseObject(buffer.toString());
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			ce.printStackTrace();
			logger
					.info("Weixin server connection timed out.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("https request error:{}"
					+ e.getMessage());
		}finally{
			try {
			httpUrlConn.disconnect();
			}catch (Exception e) {
				e.printStackTrace();
				logger.info("http close error:{}"+ e.getMessage());
			}
		}
		return jsonObject;
	}
	
	public static void main(String[] args) {
		String file = "D:\\taoyan\\temp\\wavecn\\abc.mp3";
		String accessToken = "7_VtDvFo4inD4pjPQdwftlMq9Bvj6mIw36gNRQPQFoencaE5oFu_7edpYdXojRvJPI9Nak1cCm5PgG5bLfoazkms81K-ri69QNr_a5AMGqH-sN3cmXQQRbb6ZBk7bGklo2j4OVH4oSBG3O8vvdIKJlCGADFE";
		String voice_id = "ceshi1230981fr4";
		String lang = "zh_CN";
		Voice voice = new Voice(accessToken, "mp3",voice_id, lang, file);
		String voiceContent = translateVoice(voice);
		System.out.println(voiceContent);
		
		String lfrom = "zh_CN",lto = "en_US";
		String text = "我是中国人啊";
		translateText(accessToken, lfrom, lto, text);
	}
	
}
