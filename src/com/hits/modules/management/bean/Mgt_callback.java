package com.hits.modules.management.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;import org.nutz.dao.DB;
/**
* @author yhb
* @time   2015-06-05 13:49:21
*/
@Table("mgt_callback")
public class Mgt_callback 
{
	@Column
	@Id
//	@Prev({
//		@SQL(db = DB.ORACLE, value="SELECT MGT_CALLBACK_S.nextval FROM dual")
//	})
	private int id;
	@Column
	private String info_id;
	@Column
	private String hf_date;
	@Column
	private String hf_note;
	@Column
	private String hf_user;
	@Column
	private int hf_pleased;
	@Column
	private String remark;
		public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public String getInfo_id()
	{
		return info_id;
	}
	public void setInfo_id(String info_id)
	{
		this.info_id=info_id;
	}
	public String getHf_date()
	{
		return hf_date;
	}
	public void setHf_date(String hf_date)
	{
		this.hf_date=hf_date;
	}
	public String getHf_note()
	{
		return hf_note;
	}
	public void setHf_note(String hf_note)
	{
		this.hf_note=hf_note;
	}
	public String getHf_user()
	{
		return hf_user;
	}
	public void setHf_user(String hf_user)
	{
		this.hf_user=hf_user;
	}
	public int getHf_pleased()
	{
		return hf_pleased;
	}
	public void setHf_pleased(int hf_pleased)
	{
		this.hf_pleased=hf_pleased;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark=remark;
	}

}