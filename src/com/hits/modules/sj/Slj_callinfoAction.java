package com.hits.modules.sj;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

import org.nutz.dao.*;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param; 

import cn.xuetang.common.action.BaseAction;
import cn.xuetang.common.filter.GlobalsFilter;
import cn.xuetang.common.filter.UserLoginFilter;

import cn.xuetang.modules.sj.bean.Slj_callinfo;

/**
 * @author Numbgui
 * @time 2016-04-02 14:47:55
 * 
 */
@IocBean
@At("/private/sj/slj_callinfo")
@Filters({ @By(type = GlobalsFilter.class), @By(type = UserLoginFilter.class) })
public class Slj_callinfoAction extends BaseAction {
	@Inject
	protected Dao dao;

	@At("")
	@Ok("vm:template.private.sj.Slj_callinfo")
	public void index(@Param("sys_menu") String sys_menu,HttpServletRequest req) {
		req.setAttribute("sys_menu",sys_menu);
	}
	
	@At
	@Ok("vm:template.private.sj.Slj_callinfoAdd")
	public void toadd() {
	
	}
	
	@At
	@Ok("raw")
	public boolean add(@Param("..") Slj_callinfo slj_callinfo) {
		return daoCtl.add(dao,slj_callinfo);
	}
	
	//@At
	//@Ok("raw")
	//public int add(@Param("..") Slj_callinfo slj_callinfo) {
	//	return daoCtl.addT(dao,slj_callinfo).getId();
	//}
	
	@At
	@Ok("json")
	public Slj_callinfo view(@Param("id") int id) {
		return daoCtl.detailById(dao,Slj_callinfo.class, id);
	}
	
	@At
	@Ok("vm:template.private.sj.Slj_callinfoUpdate")
	public Slj_callinfo toupdate(@Param("id") int id, HttpServletRequest req) {
		return daoCtl.detailById(dao, Slj_callinfo.class, id);//html:obj
	}
	
	@At
	public boolean update(@Param("..") Slj_callinfo slj_callinfo) {
		return daoCtl.update(dao, slj_callinfo);
	}
	
	@At
	public boolean delete(@Param("id") int id) {
		return daoCtl.deleteById(dao, Slj_callinfo.class, id);
	}
	
	@At
	public boolean deleteIds(@Param("ids") Integer[] ids) {
		return daoCtl.delete(dao, Slj_callinfo.class, Cnd.where("id", "in", ids)) > 0;
	}
	
	@At
	@Ok("raw")
	public String list(@Param("page") int curPage, @Param("rows") int pageSize){
		Criteria cri = Cnd.cri();
		cri.where().and("1","=",1);
		cri.getOrderBy().desc("id");
		return daoCtl.listPageJson(dao, Slj_callinfo.class, curPage, pageSize, cri);
	}

}