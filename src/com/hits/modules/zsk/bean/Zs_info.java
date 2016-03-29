package com.hits.modules.zsk.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;import org.nutz.dao.DB;
/**
* @author 
* @time   2013-12-05 14:56:35
*/
@Table("ZS_INFO")
public class Zs_info 
{
	@Column
	@Id
//	@Prev({
//		@SQL(db = DB.ORACLE, value="SELECT ZS_INFO_S.nextval FROM dual")
//	})
	private int id;
	@Column
	private String typeid;
	@Column
	private String unitid;
	@Column
	private String sbdate;
	@Column
	private String sbuser;
    @Column
	private String sbleader;
	@Column
	private String title;
	@Column
	private String note;
	@Column
	private int ext1;
	@Column
	private String ext2;
	@Column
	private String ext3;
	@Column
	private int zsstatus;
		public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public String getTypeid()
	{
		return typeid;
	}
	public void setTypeid(String typeid)
	{
		this.typeid=typeid;
	}
	public String getUnitid()
	{
		return unitid;
	}
	public void setUnitid(String unitid)
	{
		this.unitid=unitid;
	}
	public String getSbdate()
	{
		return sbdate;
	}
	public void setSbdate(String sbdate)
	{
		this.sbdate=sbdate;
	}
	public String getSbuser()
	{
		return sbuser;
	}
	public void setSbuser(String sbuser)
	{
		this.sbuser=sbuser;
	}
	public String getSbleader()
	{
		return sbleader;
	}
	public void setSbleader(String sbleader)
	{
		this.sbleader=sbleader;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title=title;
	}
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note=note;
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
	public int getZsstatus()
	{
		return zsstatus;
	}
	public void setZsstatus(int zsstatus)
	{
		this.zsstatus=zsstatus;
	}

    @Override
    public String toString() {
        return "Zs_info{" +
                "zsstatus=" + zsstatus +
                ", id=" + id +
                ", typeid='" + typeid + '\'' +
                ", unitid='" + unitid + '\'' +
                ", sbdate='" + sbdate + '\'' +
                ", sbuser='" + sbuser + '\'' +
                ", sbleader='" + sbleader + '\'' +
                ", title='" + title + '\'' +
                ", note='" + note + '\'' +
                ", ext1=" + ext1 +
                ", ext2='" + ext2 + '\'' +
                ", ext3='" + ext3 + '\'' +
                '}';
    }

}