package com.hits.modules.management.bean;

import com.hits.common.util.PinyinUtil;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Id;
/**
* @author yhb
* @time   2015-06-01 15:56:09
*/
@Table("mgt_handle")
public class Mgt_handle 
{
	@Column
	@Id(auto=true)
	private int id;
	@Column
	private String info_id;
	@Column
	private String reply_date;
	@Column
	private String reply_note;
	@Column
	private String reply_user;
	@Column
	private int state;
	@Column
	private String unitid;
	@Column
	private int history;
	@Column
	private int zb_id;

	public int getZb_id() {
		return zb_id;
	}
	public void setZb_id(int zbId) {
		zb_id = zbId;
	}
	public int getHistory() {
		return history;
	}
	public void setHistory(int history) {
		this.history = history;
	}
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
	public String getReply_date()
	{
		return reply_date;
	}
	public void setReply_date(String reply_date)
	{
		this.reply_date=reply_date;
	}
	public String getReply_note()
	{
		return reply_note;
	}
	public void setReply_note(String reply_note)
	{
		this.reply_note=reply_note;
	}
	public String getReply_user()
	{
		return reply_user;
	}
	public void setReply_user(String reply_user)
	{
		this.reply_user=reply_user;
	}
	public int getState()
	{
		return state;
	}
	public void setState(int state)
	{
		this.state=state;
	}
	public String getUnitid()
	{
		return unitid;
	}
	public void setUnitid(String unitid)
	{
		this.unitid=unitid;
	}
}