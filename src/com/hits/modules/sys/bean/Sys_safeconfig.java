package com.hits.modules.sys.bean;

import org.nutz.dao.entity.annotation.*;

/**
 * @author Wizzer.cn
 * @time   2012-9-13 上午10:54:04
 *
 */
@Table("sys_safeconfig")
public class Sys_safeconfig {
	@Column
	@Id
	private int id;
	@Column
	@ColDefine(type = ColType.INT)
	private int type;
	@Column
	@ColDefine(type = ColType.INT)
	private int state;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 1000)
	private String note;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	

}
