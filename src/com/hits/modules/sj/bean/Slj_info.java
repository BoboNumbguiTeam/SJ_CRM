package com.hits.modules.sj.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;
/**
* @author Numbgui
* @time   2016-04-02 14:47:55
*/
@Table("slj_info")
public class Slj_info 
{
	@Column
	private String id;
	@Column
	private String lettertype;
	@Column
	private int lettersrc;
	@Column
	private String flowid;
	@Column
	private String fromnum;
	@Column
	private String fromtime;
	@Column
	private String fromname;
	@Column
	private String fromadd;
	@Column
	private String sex;
	@Column
	private String linkcode;
	@Column
	private String linkmail;
	@Column
	private int useropen;
	@Column
	private String zt;
	@Column
	private String letternote;
	@Column
	private String bz;
	@Column
	private int letterstate;
	@Column
	private String applytime;
	@Column
	private String handleunit;
	@Column
	private String handlenote;
	@Column
	private String handletime;
	@Column
	private int handlepj;
	@Column
	private int urgency;
	@Column
	private String loginname;
	@Column
	private String dbloginname;
	@Column
	private int ishf;
	@Column
	private String createtime;
	@Column
	private String ssqy;
		public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	public String getLettertype()
	{
		return lettertype;
	}
	public void setLettertype(String lettertype)
	{
		this.lettertype=lettertype;
	}
	public int getLettersrc()
	{
		return lettersrc;
	}
	public void setLettersrc(int lettersrc)
	{
		this.lettersrc=lettersrc;
	}
	public String getFlowid()
	{
		return flowid;
	}
	public void setFlowid(String flowid)
	{
		this.flowid=flowid;
	}
	public String getFromnum()
	{
		return fromnum;
	}
	public void setFromnum(String fromnum)
	{
		this.fromnum=fromnum;
	}
	public String getFromtime()
	{
		return fromtime;
	}
	public void setFromtime(String fromtime)
	{
		this.fromtime=fromtime;
	}
	public String getFromname()
	{
		return fromname;
	}
	public void setFromname(String fromname)
	{
		this.fromname=fromname;
	}
	public String getFromadd()
	{
		return fromadd;
	}
	public void setFromadd(String fromadd)
	{
		this.fromadd=fromadd;
	}
	public String getSex()
	{
		return sex;
	}
	public void setSex(String sex)
	{
		this.sex=sex;
	}
	public String getLinkcode()
	{
		return linkcode;
	}
	public void setLinkcode(String linkcode)
	{
		this.linkcode=linkcode;
	}
	public String getLinkmail()
	{
		return linkmail;
	}
	public void setLinkmail(String linkmail)
	{
		this.linkmail=linkmail;
	}
	public int getUseropen()
	{
		return useropen;
	}
	public void setUseropen(int useropen)
	{
		this.useropen=useropen;
	}
	public String getZt()
	{
		return zt;
	}
	public void setZt(String zt)
	{
		this.zt=zt;
	}
	public String getLetternote()
	{
		return letternote;
	}
	public void setLetternote(String letternote)
	{
		this.letternote=letternote;
	}
	public String getBz()
	{
		return bz;
	}
	public void setBz(String bz)
	{
		this.bz=bz;
	}
	public int getLetterstate()
	{
		return letterstate;
	}
	public void setLetterstate(int letterstate)
	{
		this.letterstate=letterstate;
	}
	public String getApplytime()
	{
		return applytime;
	}
	public void setApplytime(String applytime)
	{
		this.applytime=applytime;
	}
	public String getHandleunit()
	{
		return handleunit;
	}
	public void setHandleunit(String handleunit)
	{
		this.handleunit=handleunit;
	}
	public String getHandlenote()
	{
		return handlenote;
	}
	public void setHandlenote(String handlenote)
	{
		this.handlenote=handlenote;
	}
	public String getHandletime()
	{
		return handletime;
	}
	public void setHandletime(String handletime)
	{
		this.handletime=handletime;
	}
	public int getHandlepj()
	{
		return handlepj;
	}
	public void setHandlepj(int handlepj)
	{
		this.handlepj=handlepj;
	}
	public int getUrgency()
	{
		return urgency;
	}
	public void setUrgency(int urgency)
	{
		this.urgency=urgency;
	}
	public String getLoginname()
	{
		return loginname;
	}
	public void setLoginname(String loginname)
	{
		this.loginname=loginname;
	}
	public String getDbloginname()
	{
		return dbloginname;
	}
	public void setDbloginname(String dbloginname)
	{
		this.dbloginname=dbloginname;
	}
	public int getIshf()
	{
		return ishf;
	}
	public void setIshf(int ishf)
	{
		this.ishf=ishf;
	}
	public String getCreatetime()
	{
		return createtime;
	}
	public void setCreatetime(String createtime)
	{
		this.createtime=createtime;
	}
	public String getSsqy()
	{
		return ssqy;
	}
	public void setSsqy(String ssqy)
	{
		this.ssqy=ssqy;
	}

}