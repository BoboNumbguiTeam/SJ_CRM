package com.hits.modules.sys.bean;

import org.nutz.dao.DB;
import org.nutz.dao.entity.annotation.*;

/**
 * @author Wizzer.cn
 * @time   2012-9-20 下午1:33:32
 *
 */
@Table("sys_role")
public class Sys_role {
	@Column 
	@Id(auto=true)
//	@Prev({
//		@SQL(db = DB.ORACLE, value="SELECT SYS_ROLE_S.nextval FROM dual")
//	})
	private int id;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 50)
	private String name;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 100)
	private String unitid;
	@Column
	@ColDefine(type = ColType.VARCHAR , width = 100)
	private String descript;
	@Column
	@ColDefine(type = ColType.INT)
	private int location;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnitid() {
		return unitid;
	}
	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	

}


