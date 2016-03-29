package com.hits.modules.msg.bean;


/**
 * 接口返回实体
 *
 */
public class WindControlMsg {

	private String retCode;//返回码
	private String retMsg;//返回说明
	private String phone;
	private String email;
	private String acctType;
	private String loginAcct;
	private String currentPage;
	
	public interface RETCODE{
		/** 认证状态0000：检查通过 */
		public static final String CHECK_BY = "0000";
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public String getLoginAcct() {
		return loginAcct;
	}
	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	
	
}
