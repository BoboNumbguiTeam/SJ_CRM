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
@Table("slj_handle")
public class Slj_handle 
{
	@Column
	@Id
	private int id;
	@Column
	private String letterid;
	@Column
	private int nibanid;
	@Column
	private String loginname;
	@Column
	private String unitid;
	@Column
	private String handledate;
	@Column
	private int handletype;
	@Column
	private String handlenote;
	@Column
	private int isls;
	@Column
	private String limitdate;
		public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public String getLetterid()
	{
		return letterid;
	}
	public void setLetterid(String letterid)
	{
		this.letterid=letterid;
	}
	public int getNibanid()
	{
		return nibanid;
	}
	public void setNibanid(int nibanid)
	{
		this.nibanid=nibanid;
	}
	public String getLoginname()
	{
		return loginname;
	}
	public void setLoginname(String loginname)
	{
		this.loginname=loginname;
	}
	public String getUnitid()
	{
		return unitid;
	}
	public void setUnitid(String unitid)
	{
		this.unitid=unitid;
	}
	public String getHandledate()
	{
		return handledate;
	}
	public void setHandledate(String handledate)
	{
		this.handledate=handledate;
	}
	public int getHandletype()
	{
		return handletype;
	}
	public void setHandletype(int handletype)
	{
		this.handletype=handletype;
	}
	public String getHandlenote()
	{
		return handlenote;
	}
	public void setHandlenote(String handlenote)
	{
		this.handlenote=handlenote;
	}
	public int getIsls()
	{
		return isls;
	}
	public void setIsls(int isls)
	{
		this.isls=isls;
	}
	public String getLimitdate()
	{
		return limitdate;
	}
	public void setLimitdate(String limitdate)
	{
		this.limitdate=limitdate;
	}

}