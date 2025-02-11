package org.jeewx.api.wxsendmsg.model;

import java.util.Date;

/**
 * 上传图文消息素材返回结果
 * 
 * @author LIAIJUN
 * 
 */
public class WxArticlesResponse {

	/** news，即图文消息 */
	private String type;
	/** 媒体文件/图文消息上传后获取的唯一标识 */
	private String media_id;
	/** 媒体文件上传时间 */
	private Date created_at;
	/** 图片上传成功之后的路径 */
	private String url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "WxArticlesResponse [type=" + type + ", media_id=" + media_id + ", created_at=" + created_at + "]";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
