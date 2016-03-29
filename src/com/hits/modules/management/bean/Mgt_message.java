package com.hits.modules.management.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;import org.nutz.dao.DB;
/**
* @author Numbgui
* @time   2015-06-02 16:45:02
*/
@Table("mgt_message")
public class Mgt_message 
{
	@Column
	@Id(auto=true)
//	@Prev({
//		@SQL(db = DB.ORACLE, value="SELECT MGT_MESSAGE_S.nextval FROM dual")
//	})
	private int id;
	@Column
	private String phone_number;
	@Column
	private String message;
	@Column
	private String message_info;
	@Column
	private String info_id;
	@Column
	private String user;
	@Column
	private String seed_date;

	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public String getPhone_number()
	{
		return phone_number;
	}
	public void setPhone_number(String phone_number)
	{
		this.phone_number=phone_number;
	}
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message=message;
	}
	public String getMessage_info()
	{
		return message_info;
	}
	public void setMessage_info(String message_info)
	{
		this.message_info=message_info;
	}

	public String getInfo_id() {
		return info_id;
	}

	public void setInfo_id(String info_id) {
		this.info_id = info_id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSeed_date() {
		return seed_date;
	}

	public void setSeed_date(String seed_date) {
		this.seed_date = seed_date;
	}
}