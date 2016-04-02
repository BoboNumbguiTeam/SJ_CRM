package com.hits.modules.sj.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;import org.nutz.dao.DB;
/**
* @author Numbgui
* @time   2016-04-02 14:47:55
*/
@Table("slj_callinfo")
public class Slj_callinfo 
{
	@Column
	@Id
	private int id;
	@Column
	private String reason;
	@Column
	private String flowid;
	@Column
	private String fromnum;
	@Column
	private String fromtime;
	@Column
	private int isletter;
	@Column
	private String ssqy;
		public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public String getReason()
	{
		return reason;
	}
	public void setReason(String reason)
	{
		this.reason=reason;
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
	public int getIsletter()
	{
		return isletter;
	}
	public void setIsletter(int isletter)
	{
		this.isletter=isletter;
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