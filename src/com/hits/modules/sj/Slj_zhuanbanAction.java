package com.hits.modules.sj;
import javax.servlet.http.HttpServletRequest;

import com.hits.common.action.BaseAction;
import com.hits.common.filter.GlobalsFilter;
import com.hits.common.filter.UserLoginFilter;

import com.hits.modules.sj.bean.Slj_zhuanban;
import net.sf.json.JSONObject;
import org.nutz.dao.*;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param; 


/**
 * @author Numbgui
 * @time 2016-04-02 14:47:56
 * 
 */
@IocBean
@At("/private/sj/slj_zhuanban")
@Filters({ @By(type = GlobalsFilter.class), @By(type = UserLoginFilter.class) })
public class Slj_zhuanbanAction extends BaseAction {
	@Inject
	protected Dao dao;

	@At("")
	public void index(@Param("sys_menu") String sys_menu,HttpServletRequest req) {
		req.setAttribute("sys_menu",sys_menu);
	}
	
	@At
	public void toadd() {
	
	}
	
	@At
	@Ok("raw")
	public boolean add(@Param("..") Slj_zhuanban slj_zhuanban) {
		return daoCtl.add(dao,slj_zhuanban);
	}
	
	//@At
	//@Ok("raw")
	//public int add(@Param("..") Slj_zhuanban slj_zhuanban) {
	//	return daoCtl.addT(dao,slj_zhuanban).getId();
	//}
	
	@At
	@Ok("json")
	public Slj_zhuanban view(@Param("id") int id) {
		return daoCtl.detailById(dao,Slj_zhuanban.class, id);
	}
	
	@At
	public Slj_zhuanban toupdate(@Param("id") int id, HttpServletRequest req) {
		return daoCtl.detailById(dao, Slj_zhuanban.class, id);//html:obj
	}
	
	@At
	public boolean update(@Param("..") Slj_zhuanban slj_zhuanban) {
		return daoCtl.update(dao, slj_zhuanban);
	}
	
	@At
	public boolean delete(@Param("id") int id) {
		return daoCtl.deleteById(dao, Slj_zhuanban.class, id);
	}
	
	@At
	public boolean deleteIds(@Param("ids") Integer[] ids) {
		return dao.clear("Slj_zhuanban", Cnd.where("id", "in", ids)) > 0;
	}
	
	@At
	@Ok("raw")
	public JSONObject list(@Param("page") int curPage, @Param("rows") int pageSize){
		Criteria cri = Cnd.cri();
		cri.where().and("1","=",1);
		cri.getOrderBy().desc("id");
		return daoCtl.listPageJson(dao, Slj_zhuanban.class, curPage, pageSize, cri);
	}

}