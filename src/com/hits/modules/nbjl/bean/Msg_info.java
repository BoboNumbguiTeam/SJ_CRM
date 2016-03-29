package com.hits.modules.nbjl.bean;

import org.nutz.dao.entity.annotation.*;
import org.nutz.dao.DB;
/**
* @author 
* @time   2014-05-06 13:33:35
*/
@Table("MSG_INFO")
public class Msg_info 
{
	@Column
	@Id(auto=true)
//	@Prev({
//		@SQL(db = DB.ORACLE, value="SELECT MSG_INFO_S.nextval FROM dual")
//	})
	private int id;
	@Column
	@ColDefine (type = ColType.INT , width = 1)
	private int infotype;
	@Column
	@ColDefine (type = ColType.INT , width = 1)
	private int imp;
	@Column
	@ColDefine (type = ColType.INT , width = 1)
	private int infostate;
	@Column
	@ColDefine (type = ColType.VARCHAR , width = 255)
	private String title;
	@Column
	@ColDefine (type = ColType.VARCHAR , width = 255)
	private String subtitle;
	@Column
	@ColDefine (type = ColType.VARCHAR , width = 20)
	private String fileno;
	@Column
	@ColDefine (type = ColType.TEXT )
	private String content;
	@Column
	@ColDefine (type = ColType.VARCHAR , width = 255)
	private String unitid;
	@Column
	@ColDefine (type = ColType.VARCHAR , width = 255)
	private String flogin;
	@Column
	@ColDefine (type = ColType.VARCHAR , width = 255)
	private String ctime;
	@Column
	@ColDefine (type = ColType.INT )
	private int ext1;
	@Column
	@ColDefine (type = ColType.VARCHAR , width = 255)
	private String ext2;
	@Column
	@ColDefine (type = ColType.VARCHAR , width = 255)
	private String ext3;
		public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public int getInfotype()
	{
		return infotype;
	}
	public void setInfotype(int infotype)
	{
		this.infotype=infotype;
	}
	public int getImp()
	{
		return imp;
	}
	public void setImp(int imp)
	{
		this.imp=imp;
	}
	public int getInfostate()
	{
		return infostate;
	}
	public void setInfostate(int infostate)
	{
		this.infostate=infostate;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title=title;
	}
	public String getSubtitle()
	{
		return subtitle;
	}
	public void setSubtitle(String subtitle)
	{
		this.subtitle=subtitle;
	}
	public String getFileno()
	{
		return fileno;
	}
	public void setFileno(String fileno)
	{
		this.fileno=fileno;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content=content;
	}
	public String getUnitid()
	{
		return unitid;
	}
	public void setUnitid(String unitid)
	{
		this.unitid=unitid;
	}
	public String getFlogin()
	{
		return flogin;
	}
	public void setFlogin(String flogin)
	{
		this.flogin=flogin;
	}
	public String getCtime()
	{
		return ctime;
	}
	public void setCtime(String ctime)
	{
		this.ctime=ctime;
	}
	public int getExt1()
	{
		return ext1;
	}
	public void setExt1(int ext1)
	{
		this.ext1=ext1;
	}
	public String getExt2()
	{
		return ext2;
	}
	public void setExt2(String ext2)
	{
		this.ext2=ext2;
	}
	public String getExt3()
	{
		return ext3;
	}
	public void setExt3(String ext3)
	{
		this.ext3=ext3;
	}

}