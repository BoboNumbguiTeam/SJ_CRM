package com.hits.modules.nbjl.bean;

import org.nutz.dao.DB;
import org.nutz.dao.entity.annotation.*;

/**
* @author 
* @time   2014-05-06 13:33:35
*/
@Table("MSG_FJ")
public class Msg_fj 
{
	@Column
	@Id(auto=true)
//	@Prev({
//		@SQL(db = DB.ORACLE, value="SELECT MSG_FJ_S.nextval FROM dual")
//	})
	private int id;
	@Column
	@ColDefine(type = ColType.INT )
	private int msgid;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 255)
	private String fjmc;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 1000)
	private String fjurl;
	@Column
	@ColDefine(type = ColType.INT)
	private int ext1;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 100)
	private String ext2;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 100)
	private String ext3;

	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public int getMsgid()
	{
		return msgid;
	}
	public void setMsgid(int msgid)
	{
		this.msgid=msgid;
	}
	public String getFjmc()
	{
		return fjmc;
	}
	public void setFjmc(String fjmc)
	{
		this.fjmc=fjmc;
	}
	public String getFjurl()
	{
		return fjurl;
	}
	public void setFjurl(String fjurl)
	{
		this.fjurl=fjurl;
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