package org.jeewx.api;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.jdom2.input.SAXBuilder;
import org.jeewx.api.core.common.util.FreemarkerUtil;
import org.jeewx.api.core.common.util.StringUtils;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.util.WeiXinReqUtil;
import org.jeewx.api.qrcode.QRCode;
import org.jeewx.api.wxsendmsg.JwSendMessageAPI;
import org.jeewx.api.wxsendmsg.model.SendMessageReport;
import org.junit.Assert;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 依赖升级冒烟测试：覆盖本次 jdom2 迁移、StringUtils、beanutils、freemarker、zxing、fastjson 关键路径
 */
public class SmokeTest {

	@Test
	public void testStringUtils() {
		Assert.assertTrue(StringUtils.isEmpty(null));
		Assert.assertTrue(StringUtils.isEmpty(""));
		Assert.assertFalse(StringUtils.isEmpty("abc"));
	}

	@Test
	public void testJdom2ParseSendReport() throws WexinReqException {
		// 覆盖 JwSendMessageAPI 中 jdom2 迁移 + XXE feature 设置后的正常解析路径
		String xml = "<xml>"
				+ "<ToUserName><![CDATA[gh_3e8adccde292]]></ToUserName>"
				+ "<FromUserName><![CDATA[oR5Gjjl_eiZoUpGozMo7dbBJ362A]]></FromUserName>"
				+ "<MsgType><![CDATA[event]]></MsgType>"
				+ "<Event><![CDATA[MASSSENDJOBFINISH]]></Event>"
				+ "<MsgID>1988</MsgID>"
				+ "<Status><![CDATA[sendsuccess]]></Status>"
				+ "<TotalCount>100</TotalCount>"
				+ "<FilterCount>80</FilterCount>"
				+ "<SentCount>75</SentCount>"
				+ "<ErrorCount>5</ErrorCount>"
				+ "</xml>";
		SendMessageReport report = JwSendMessageAPI.getReportBySendMessageReturnString(xml);
		Assert.assertEquals("gh_3e8adccde292", report.getToUserName());
		Assert.assertEquals("oR5Gjjl_eiZoUpGozMo7dbBJ362A", report.getFromUserName());
		Assert.assertEquals("1988", report.getMsgID());
		Assert.assertEquals("sendsuccess", report.getStatus());
	}

	@Test
	public void testJdom2RejectsXXE() {
		// 带 DOCTYPE 外部实体的 XML 应被拒绝（disallow-doctype-decl），验证 XXE 防护生效
		String evil = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<!DOCTYPE xml [<!ENTITY xxe SYSTEM \"file:///etc/passwd\">]>"
				+ "<xml><Status><![CDATA[&xxe;]]></Status></xml>";
		boolean rejected = false;
		try {
			JwSendMessageAPI.getReportBySendMessageReturnString(evil);
		} catch (WexinReqException e) {
			rejected = true;
		}
		Assert.assertTrue("带DOCTYPE的XML应当被拒绝", rejected);
	}

	@Test
	public void testWeiXinReqUtilInitConfig() throws Exception {
		// 覆盖 WeiXinReqUtil 中 jdom2 迁移 + XXE feature 设置后的配置文件正常解析路径
		WeiXinReqUtil.initReqConfig("weixin-reqcongfig.xml");
		Assert.assertNotNull(WeiXinReqUtil.getWeixinReqConfig("access_token"));
	}

	@Test
	public void testBeanUtilsSetProperty() throws Exception {
		// commons-beanutils 1.11.0 基本功能
		SendMessageReport report = new SendMessageReport();
		BeanUtils.setProperty(report, "status", "test-status");
		Assert.assertEquals("test-status", report.getStatus());
	}

	@Test
	public void testFreemarkerRender() throws IOException {
		// freemarker 2.3.34 模板渲染
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("name", "world");
		String result = FreemarkerUtil.parseTemplateContent("hello ${name}", paras);
		Assert.assertEquals("hello world", result);
	}

	@Test
	public void testZxingEncode() throws IOException {
		// zxing 3.5.3 二维码生成
		File file = File.createTempFile("jeewx-qrcode", ".png");
		QRCode.encode("http://www.jeecg.com", file, "png", com.google.zxing.BarcodeFormat.QR_CODE, 200, 200, null);
		Assert.assertTrue("二维码文件应生成", file.exists() && file.length() > 0);
		file.delete();
	}

	@Test
	public void testFastjson() {
		// fastjson 2.0.58 序列化/反序列化
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "jeewx");
		map.put("count", 3);
		String json = JSON.toJSONString(map);
		JSONObject obj = JSON.parseObject(json);
		Assert.assertEquals("jeewx", obj.getString("name"));
		Assert.assertEquals(Integer.valueOf(3), obj.getInteger("count"));
	}

	@Test
	public void testSaxBuilderDirect() throws Exception {
		// jdom2 2.0.6.1 直接使用（模拟项目内其他 SAXBuilder 用法）
		SAXBuilder builder = new SAXBuilder();
		org.jdom2.Document doc = builder.build(new java.io.StringReader("<root><child>v</child></root>"));
		Assert.assertEquals("v", doc.getRootElement().getChildText("child"));
	}
}
