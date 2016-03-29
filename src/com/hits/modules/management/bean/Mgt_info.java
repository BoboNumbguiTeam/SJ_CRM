package com.hits.modules.management.bean;

import com.hits.common.util.DateUtil;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Name;
/**
* @author Numbgui
* @time   2015-05-23 14:21:13
*/
@Table("mgt_info")
public class Mgt_info 
{
	@Column
	@Name
	private String id;
	@Column
	private String add_date;
	@Column
	private String phone;
	@Column
	private String loginname;
	@Column
	private String fromname;
	@Column
	private String management;
	@Column
	private String zb_management;
	@Column
	private String address;
	@Column
	private String phone_type;
	@Column
	private String repairs_type;
	@Column
	private String title;
	@Column
	private String note;
	@Column
	private String state;
	@Column
	private String repairs_project;
	@Column
	private String is_banjie;
	@Column
	private String flowid;
	@Column
	private String record_path;
	@Column
	private int status;
	@Column
	private int is_huifang;
	@Column
	private String link_tel;
	@Column
	private String zb_note;
	@Column
	private String fromtime;

	public String getZb_management() {
		return zb_management;
	}

	public void setZb_management(String zb_management) {
		this.zb_management = zb_management;
	}

	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	public String getAdd_date()
	{
		return add_date;
	}
	public void setAdd_date(String add_date)
	{
		this.add_date=add_date;
	}
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone=phone;
	}
	public String getLoginname()
	{
		return loginname;
	}
	public void setLoginname(String loginname)
	{
		this.loginname=loginname;
	}
	public String getFromname()
	{
		return fromname;
	}
	public void setFromname(String fromname)
	{
		this.fromname=fromname;
	}
	public String getManagement()
	{
		return management;
	}
	public void setManagement(String management)
	{
		this.management=management;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address=address;
	}
	public String getPhone_type()
	{
		return phone_type;
	}
	public void setPhone_type(String phone_type)
	{
		this.phone_type=phone_type;
	}
	public String getRepairs_type()
	{
		return repairs_type;
	}
	public void setRepairs_type(String repairs_type)
	{
		this.repairs_type=repairs_type;
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
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state=state;
	}
	public String getRepairs_project()
	{
		return repairs_project;
	}
	public void setRepairs_project(String repairs_project)
	{
		this.repairs_project=repairs_project;
	}
	public String getIs_banjie()
	{
		return is_banjie;
	}
	public void setIs_banjie(String is_banjie)
	{
		this.is_banjie=is_banjie;
	}
	public String getFlowid() {
		return flowid;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}

	public String getRecord_path() {
		return record_path;
	}

	public void setRecord_path(String record_path) {
		this.record_path = record_path;
	}

	public int getIs_huifang() {
		return is_huifang;
	}

	public void setIs_huifang(int is_huifang) {
		this.is_huifang = is_huifang;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLink_tel() {
		return link_tel;
	}

	public void setLink_tel(String link_tel) {
		this.link_tel = link_tel;
	}

	public String getZb_note() {
		return zb_note;
	}

	public void setZb_note(String zb_note) {
		this.zb_note = zb_note;
	}

	public String getFromtime() {
		return fromtime;
	}

	public void setFromtime(String fromtime) {
		this.fromtime = fromtime;
	}

}