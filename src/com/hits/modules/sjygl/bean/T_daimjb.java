package com.hits.modules.sjygl.bean;

import org.nutz.dao.entity.annotation.*;
import org.nutz.dao.DB;
/**
* @author zhangzhen
* @time   2014-08-04 14:00:26
*/
@Table("T_DAIMJB")
public class T_daimjb 
{
	@Column
	@Id(auto=true)
//	@Prev({
//			@SQL(db = DB.ORACLE, value="SELECT T_DAIMJB_S.nextval FROM dual")
//	})
	private int id;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String f_vc_daimmc;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String f_vc_daimxh;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String f_vc_sxbh;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String f_vc_ysjbm;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String f_vc_daimz1;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String f_vc_daimz2;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String f_vc_daimjbh;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String f_vc_daimjmc;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String f_vc_biaozh;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String f_vc_daimjbj;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String f_vc_daimsm;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String f_vc_beiz;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String f_vc_caozr;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String f_dt_caozsj;
	@Column
	@ColDefine(type = ColType.INT)
	private int f_nb_caozlx;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String ssfl;
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

	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public String getF_vc_daimmc()
	{
		return f_vc_daimmc;
	}
	public void setF_vc_daimmc(String f_vc_daimmc)
	{
		this.f_vc_daimmc=f_vc_daimmc;
	}
	public String getF_vc_daimxh()
	{
		return f_vc_daimxh;
	}
	public void setF_vc_daimxh(String f_vc_daimxh)
	{
		this.f_vc_daimxh=f_vc_daimxh;
	}
	public String getF_vc_sxbh()
	{
		return f_vc_sxbh;
	}
	public void setF_vc_sxbh(String f_vc_sxbh)
	{
		this.f_vc_sxbh=f_vc_sxbh;
	}
	public String getF_vc_ysjbm()
	{
		return f_vc_ysjbm;
	}
	public void setF_vc_ysjbm(String f_vc_ysjbm)
	{
		this.f_vc_ysjbm=f_vc_ysjbm;
	}
	public String getF_vc_daimz1()
	{
		return f_vc_daimz1;
	}
	public void setF_vc_daimz1(String f_vc_daimz1)
	{
		this.f_vc_daimz1=f_vc_daimz1;
	}
	public String getF_vc_daimz2()
	{
		return f_vc_daimz2;
	}
	public void setF_vc_daimz2(String f_vc_daimz2)
	{
		this.f_vc_daimz2=f_vc_daimz2;
	}
	public String getF_vc_daimjbh()
	{
		return f_vc_daimjbh;
	}
	public void setF_vc_daimjbh(String f_vc_daimjbh)
	{
		this.f_vc_daimjbh=f_vc_daimjbh;
	}
	public String getF_vc_daimjmc()
	{
		return f_vc_daimjmc;
	}
	public void setF_vc_daimjmc(String f_vc_daimjmc)
	{
		this.f_vc_daimjmc=f_vc_daimjmc;
	}
	public String getF_vc_biaozh()
	{
		return f_vc_biaozh;
	}
	public void setF_vc_biaozh(String f_vc_biaozh)
	{
		this.f_vc_biaozh=f_vc_biaozh;
	}
	public String getF_vc_daimjbj()
	{
		return f_vc_daimjbj;
	}
	public void setF_vc_daimjbj(String f_vc_daimjbj)
	{
		this.f_vc_daimjbj=f_vc_daimjbj;
	}
	public String getF_vc_daimsm()
	{
		return f_vc_daimsm;
	}
	public void setF_vc_daimsm(String f_vc_daimsm)
	{
		this.f_vc_daimsm=f_vc_daimsm;
	}
	public String getF_vc_beiz()
	{
		return f_vc_beiz;
	}
	public void setF_vc_beiz(String f_vc_beiz)
	{
		this.f_vc_beiz=f_vc_beiz;
	}
	public String getF_vc_caozr()
	{
		return f_vc_caozr;
	}
	public void setF_vc_caozr(String f_vc_caozr)
	{
		this.f_vc_caozr=f_vc_caozr;
	}
	public String getF_dt_caozsj()
	{
		return f_dt_caozsj;
	}
	public void setF_dt_caozsj(String f_dt_caozsj)
	{
		this.f_dt_caozsj=f_dt_caozsj;
	}
	public int getF_nb_caozlx()
	{
		return f_nb_caozlx;
	}
	public void setF_nb_caozlx(int f_nb_caozlx)
	{
		this.f_nb_caozlx=f_nb_caozlx;
	}
	public String getSsfl()
	{
		return ssfl;
	}
	public void setSsfl(String ssfl)
	{
		this.ssfl=ssfl;
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

}