package com.hits.modules.management.bean;

import com.hits.common.util.DateUtil;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;import org.nutz.dao.DB;
/**
* @author Numbgui
* @time   2015-05-23 14:21:13
*/
@Table("mgt_zhuanban")
public class Mgt_zhuanban 
{
	@Column
	@Id(auto=true)
//	@Prev({
//		@SQL(db = DB.ORACLE, value="SELECT MGT_ZHUANBAN_S.nextval FROM dual")
//	})
	private int id;
	@Column
	private String info_id;
	@Column
	private String zb_date;
	@Column
	private String get_date;
	@Column
	private String zb_note;
	@Column
	private String management;
	@Column
	private String zb_management;
	@Column
	private String zb_user;
	@Column
	private String state;
	@Column
	private String need_date;
	@Column
	private String message_note;
	@Column
	private String management_tel;
	@Column
	private String seed_type;
	@Column
	private int history;
	@Column
	private int send_cbmsg;

	public String getManagement() {
		return management;
	}

	public void setManagement(String management) {
		this.management = management;
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
	public String getZb_date()
	{
		return zb_date;
	}
	public void setZb_date(String zb_date)
	{
		this.zb_date=zb_date;
	}
	public String getGet_date()
	{
		return get_date;
	}
	public void setGet_date(String get_date)
	{
		this.get_date=get_date;
	}
	public String getZb_note()
	{
		return zb_note;
	}
	public void setZb_note(String zb_note)
	{
		this.zb_note=zb_note;
	}
	public String getZb_management()
	{
		return zb_management;
	}
	public void setZb_management(String zb_management)
	{
		this.zb_management=zb_management;
	}
	public String getZb_user()
	{
		return zb_user;
	}
	public void setZb_user(String zb_user)
	{
		this.zb_user=zb_user;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state=state;
	}

	public String getNeed_date() {
		return need_date;
	}

	public void setNeed_date(String need_date) {
		this.need_date = need_date;
	}

	public String getMessage_note() {
		return message_note;
	}

	public void setMessage_note(String message_note) {
		this.message_note = message_note;
	}

	public String getManagement_tel() {
		return management_tel;
	}

	public void setManagement_tel(String management_tel) {
		this.management_tel = management_tel;
	}

	public String getSeed_type() {
		return seed_type;
	}

	public void setSeed_type(String seed_type) {
		this.seed_type = seed_type;
	}

	public int getSend_cbmsg() {
		return send_cbmsg;
	}

	public void setSend_cbmsg(int send_cbmsg) {
		this.send_cbmsg = send_cbmsg;
	}
}