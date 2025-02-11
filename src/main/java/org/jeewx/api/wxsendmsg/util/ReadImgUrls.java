package org.jeewx.api.wxsendmsg.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jeewx.api.core.util.FileUtils;

public class ReadImgUrls {

	public static String[] getImgs(String content) {
		String img = "";
		Pattern p_image;
		Matcher m_image;
		String str = "";
		String[] images = null;
		String regEx_img = "(<img.*src\\s*=\\s*(.*?)[^>]*?>)";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(content);
		while (m_image.find()) {
			img = m_image.group();
			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
			while (m.find()) {
				String tempSelected = m.group(1);
				if ("".equals(str)) {
					str = tempSelected;
				} else {
					String temp = tempSelected;
					if(temp.indexOf("mmbiz.qpic.cn")==-1){
						str = str + "," + temp;
					}
				}
			}
		}
		//update-begin-author:taoYan date：20180531 for:TASK #2755 【图文素材】编辑器优化汇总-2
		if(content.indexOf("background-image")>0){
			String regEx_bg = "(background-image\\s*:\\s*url\\s*\\((&#39;)?http://\\S*\\);)";
			p_image = Pattern.compile(regEx_bg, Pattern.CASE_INSENSITIVE);
			m_image = p_image.matcher(content);
			while (m_image.find()) {
				img = m_image.group();
				if(img.indexOf("&#39;")>0){
					img = img.replace("&#39;", "");
				}
				Matcher m = Pattern.compile("\\(\\s*(http://\\S*)\\s*\\);").matcher(img);
				while (m.find()) {
					String tempSelected = m.group(1);
					if ("".equals(str)) {
						str = tempSelected;
					} else {
						String temp = tempSelected;
						if(temp.indexOf("mmbiz.qpic.cn")==-1){
							str = str + "," + temp;
						}
					}
				}
			}
		}
		//update-end-author:taoYan date：20180531 for:TASK #2755 【图文素材】编辑器优化汇总-2
		if (!"".equals(str)) {
			images = str.split(",");
		}
		return images;
	}
	
	public static void main(String[] args) {
		String baseImageUrl = System.getProperty("user.dir");
		String domainUrl = "http://www.jeewx.com/jeewx";
		try {
			String c = FileUtils.readFile("D:/workspace-JEECGONE/weixin4j/src/main/java/org/jeewx/api/wxsendmsg/uploadimg/1.html");
			String[] urls = getImgs(c);
			for(String url:urls){
				System.out.println(url);
				
//				url = url.replace(domainUrl, "");
//				url = baseImageUrl + url;
				
//				System.out.println(url);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
