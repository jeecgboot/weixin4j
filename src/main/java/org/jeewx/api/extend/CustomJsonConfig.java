package org.jeewx.api.extend;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
/**
 * 过滤不需要转换的属性
 * @author luobaoli
 *
 */
public class CustomJsonConfig extends FastJsonConfig {
	@SuppressWarnings("rawtypes")
	private Class clazz;
	public CustomJsonConfig(){
		
	}
	
	public CustomJsonConfig(Class clazz,final String exclude){
		this.clazz = clazz;
		setSerializeFilters(new PropertyFilter() {
			public boolean apply(Object arg0, String param, Object arg2) {
				if(param.equals(exclude))return true;
				return false;
			}
		});
	}
	
	public CustomJsonConfig(Class clazz,final String[] excludes){
		this.clazz = clazz;
		setSerializeFilters(new PropertyFilter() {
			public boolean apply(Object arg0, String param, Object arg2) {
				for(String exclude:excludes){
					if(param.equals(exclude))return true;
				}
				return false;
			}
		});
	}
}
