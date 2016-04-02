package com.hits.modules.sj.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;import org.nutz.dao.DB;
/**
* @author Numbgui
* @time   2016-04-02 14:47:56
*/
@Table("slj_zhuanban")
public class Slj_zhuanban 
{
	@Column
	@Id
	private int id;
	@Column
	private String letterid;
	@Column
	private String loginname;
	@Column
	private String unitid;
	@Column
	private String nibandate;
	@Column
	private String nibannote;
	@Column
	private String limitdate;
	@Column
	private int zbstate;
	@Column
	private int zbclass;
	@Column
	private int isls;
	@Column
	private String shdate;
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
	public String getNibandate()
	{
		return nibandate;
	}
	public void setNibandate(String nibandate)
	{
		this.nibandate=nibandate;
	}
	public String getNibannote()
	{
		return nibannote;
	}
	public void setNibannote(String nibannote)
	{
		this.nibannote=nibannote;
	}
	public String getLimitdate()
	{
		return limitdate;
	}
	public void setLimitdate(String limitdate)
	{
		this.limitdate=limitdate;
	}
	public int getZbstate()
	{
		return zbstate;
	}
	public void setZbstate(int zbstate)
	{
		this.zbstate=zbstate;
	}
	public int getZbclass()
	{
		return zbclass;
	}
	public void setZbclass(int zbclass)
	{
		this.zbclass=zbclass;
	}
	public int getIsls()
	{
		return isls;
	}
	public void setIsls(int isls)
	{
		this.isls=isls;
	}
	public String getShdate()
	{
		return shdate;
	}
	public void setShdate(String shdate)
	{
		this.shdate=shdate;
	}

}