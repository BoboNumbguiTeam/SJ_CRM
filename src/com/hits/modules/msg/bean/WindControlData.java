package com.hits.modules.msg.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 *  短信接口调用实体  
 */
public class WindControlData {
	@JSONField(name="method")
	private String method;
	@JSONField(name="Request")
	private  WindControlRequest Request;
	@JSONField(name="sign")
	private String sign;
	@JSONField(name="operCode")
	private String operCode;
	@JSONField(name="content")
	private String content;
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public WindControlRequest getRequest() {
		return Request;
	}
	public void setRequest(WindControlRequest request) {
		Request = request;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getOperCode() {
		return operCode;
	}
	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
