weixin4j  | 微信和钉钉开发SDK
===============

  
最新版本： 2.0.0（发布日期：2025-02-11）

[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/zhangdaiscott/jeecg-boot/blob/master/LICENSE)
[![](https://img.shields.io/badge/Author-北京国炬软件-orange.svg)](http://jeecg.com/aboutusIndex)
[![](https://img.shields.io/badge/Blog-官方博客-blue.svg)](https://jeecg.blog.csdn.net)
[![](https://img.shields.io/badge/version-2.0.0-brightgreen.svg)](https://github.com/zhangdaiscott/jeecg-boot)
[![GitHub stars](https://img.shields.io/github/stars/zhangdaiscott/jeecg-boot.svg?style=social&label=Stars)](https://github.com/zhangdaiscott/jeecg-boot)
[![GitHub forks](https://img.shields.io/github/forks/zhangdaiscott/jeecg-boot.svg?style=social&label=Fork)](https://github.com/zhangdaiscott/jeecg-boot)






WeiXin4j 项目说明
-----------------------------------

微信和钉钉开发JAVA版SDK,主要提供微信、企业微信、钉钉的JAVA封装，降低集成难度，让API变简单
支持微信公众号、微信企业号、微信小程序、钉钉、支付宝生活号和微博SDK。你可以基于她，快速的傻瓜化的进行微信开发、钉钉、支付窗和微博开发。

* 	技术官网： www.jeecg.com
* 	反馈bug或需求： [github发issue](https://github.com/jeecgboot/weixin4j/issues)

weixin4j 快速集成
-----------------------------------
    在pom.xml 添加 weixin4j 依赖
    <dependency>
		<groupId>org.jeecgframework</groupId>
		<artifactId>weixin4j</artifactId>
		<version>2.0.0</version>
	</dependency>
	



单元测试API
-----------------------------------

    public static void main(String[] args) {
		try {
			String accesstoken = "?";
			String user_openid = "o8QKAuAyDxxfyuBZ9ugSMR4SR5XQ";
			JwUserAPI.getWxuser(accesstoken, user_openid);
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}