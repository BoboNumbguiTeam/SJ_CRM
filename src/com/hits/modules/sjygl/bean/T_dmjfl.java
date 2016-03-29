package com.hits.modules.sjygl.bean;

import org.nutz.dao.entity.annotation.*;
import org.nutz.dao.DB;
/**
* @author 
* @time   2014-08-04 14:00:26
*/
@Table("T_DMJFL")
public class T_dmjfl 
{
	@Column
	@Id(auto = true)
//	@Prev({
//		@SQL(db = DB.ORACLE, value="SELECT T_DMJFL_S.nextval FROM dual")
//	})
	private int id;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String flmc;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String flbm;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String flbh;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String cjr;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String cjsj;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String bz;
	@Column
	@ColDefine(type = ColType.INT)
	private int ext1;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String ext2;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String ext3;
	@Column
	@ColDefine(type = ColType.INT)
	private int location;
	@Column
	@ColDefine(type = ColType.INT)
	private int location2;

	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public String getFlmc()
	{
		return flmc;
	}
	public void setFlmc(String flmc)
	{
		this.flmc=flmc;
	}
	public String getFlbm()
	{
		return flbm;
	}
	public void setFlbm(String flbm)
	{
		this.flbm=flbm;
	}
	public String getFlbh()
	{
		return flbh;
	}
	public void setFlbh(String flbh)
	{
		this.flbh=flbh;
	}
	public String getCjr()
	{
		return cjr;
	}
	public void setCjr(String cjr)
	{
		this.cjr=cjr;
	}
	public String getCjsj()
	{
		return cjsj;
	}
	public void setCjsj(String cjsj)
	{
		this.cjsj=cjsj;
	}
	public String getBz()
	{
		return bz;
	}
	public void setBz(String bz)
	{
		this.bz=bz;
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
	public int getLocation()
	{
		return location;
	}
	public void setLocation(int location)
	{
		this.location=location;
	}
	public int getLocation2()
	{
		return location2;
	}
	public void setLocation2(int location2)
	{
		this.location2=location2;
	}

}