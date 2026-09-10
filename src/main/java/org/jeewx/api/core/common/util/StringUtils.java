package org.jeewx.api.core.common.util;

/**
 * 字符串工具类
 * 替代 com.alipay.api.internal.util.StringUtils，解除微博/企微/媒体等模块对 alipay-sdk 的伪依赖
 */
public class StringUtils {

	/**
	 * 判断字符串是否为空（null 或长度为0）
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
}
