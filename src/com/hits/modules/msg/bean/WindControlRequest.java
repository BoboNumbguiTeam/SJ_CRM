package com.hits.modules.msg.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 接口请求参数
 *
 */
public class WindControlRequest {
	@JSONField(name="mobile")
	private String mobile;
	@JSONField(name="operCode")
	private String operCode;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOperCode() {
		return operCode;
	}
	public void setOperCode(String operCode) {
		this.operCode = operCode;
	} 
	
	
}
