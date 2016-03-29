package com.hits.modules.sys.bean;

import java.util.Hashtable;
import java.util.List;

import org.nutz.dao.DB;
import org.nutz.dao.entity.annotation.*;

/**
 * @author Wizzer.cn
 * @time 2012-9-13 上午10:54:04
 * 
 */
@Table("sys_user") 
public class Sys_user {
	@Column 
	@Id(auto=true)
//	@Prev({
//		@SQL(db = DB.ORACLE, value="SELECT SYS_USER_S.nextval FROM dual")
//	})
	private long userid;
	@Column
	@Name
	@ColDefine(type = ColType.VARCHAR , width = 120)
	private String loginname;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 100)
	private String realname;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 100)
	private String unitid;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 100)
	private String password;// transient 修饰符可让此字段不在对象里显示
	@Column
	@ColDefine(type = ColType.INT)
	private int state;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 100)
	private String descript;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 100)
	private String pozition;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 100)
	private String address;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 20)
	private String telephone;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 20)
	private String mobile;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 120)
	private String email;
	@Column
	@ColDefine(type = ColType.INT)
	private int location;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 10)
	private String style;
	@Column
	@ColDefine(type = ColType.INT)
	private int logintype;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 20)
	private String logintime;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 50)
	private String loginip;
	@Column
	@ColDefine(type = ColType.INT)
	private int logincount;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 100)
	private String loginresid;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 20)
	private String linkqq;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 100)
	private String linkweb;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 100)
	private String linkcity;
	@Column
	@ColDefine(type = ColType.INT)
	private String sina_uid;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 50)
	private String sina_nickname;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 20)
	private String sina_logintime;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 50)
	private String sina_access_token;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 50)
	private String sina_token_secret;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 20)
	private String sina_auth_end;

	private String unitname;

	// 是否超级管理员角色
	private boolean sysrole;

	private List<String> rolelist;

	private List<String> reslist;

	private Hashtable<String, String> btnmap;

	
	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getPozition() {
		return pozition;
	}

	public void setPozition(String pozition) {
		this.pozition = pozition;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int getLogintype() {
		return logintype;
	}

	public void setLogintype(int logintype) {
		this.logintype = logintype;
	}

	public String getLogintime() {
		return logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}

	public String getLoginip() {
		return loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	public int getLogincount() {
		return logincount;
	}

	public void setLogincount(int logincount) {
		this.logincount = logincount;
	}

	public String getLoginresid() {
		return loginresid;
	}

	public void setLoginresid(String loginresid) {
		this.loginresid = loginresid;
	}

	public String getLinkqq() {
		return linkqq;
	}

	public void setLinkqq(String linkqq) {
		this.linkqq = linkqq;
	}

	public String getLinkweb() {
		return linkweb;
	}

	public void setLinkweb(String linkweb) {
		this.linkweb = linkweb;
	}

	public String getLinkcity() {
		return linkcity;
	}

	public void setLinkcity(String linkcity) {
		this.linkcity = linkcity;
	}

	public String getSina_uid() {
		return sina_uid;
	}

	public void setSina_uid(String sina_uid) {
		this.sina_uid = sina_uid;
	}

	public String getSina_nickname() {
		return sina_nickname;
	}

	public void setSina_nickname(String sina_nickname) {
		this.sina_nickname = sina_nickname;
	}

	public String getSina_logintime() {
		return sina_logintime;
	}

	public void setSina_logintime(String sina_logintime) {
		this.sina_logintime = sina_logintime;
	}

	public String getSina_access_token() {
		return sina_access_token;
	}

	public void setSina_access_token(String sina_access_token) {
		this.sina_access_token = sina_access_token;
	}

	public String getSina_token_secret() {
		return sina_token_secret;
	}

	public void setSina_token_secret(String sina_token_secret) {
		this.sina_token_secret = sina_token_secret;
	}

	public String getSina_auth_end() {
		return sina_auth_end;
	}

	public void setSina_auth_end(String sina_auth_end) {
		this.sina_auth_end = sina_auth_end;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public List<String> getRolelist() {
		return rolelist;
	}

	public void setRolelist(List<String> rolelist) {
		this.rolelist = rolelist;
	}

	public List<String> getReslist() {
		return reslist;
	}

	public void setReslist(List<String> reslist) {
		this.reslist = reslist;
	}

	public boolean getSysrole() {
		return sysrole;
	}

	public void setSysrole(boolean sysrole) {
		this.sysrole = sysrole;
	}

	public Hashtable<String, String> getBtnmap() {
		return btnmap;
	}

	public void setBtnmap(Hashtable<String, String> btnmap) {
		this.btnmap = btnmap;
	}

}
