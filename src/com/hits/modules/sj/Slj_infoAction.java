package com.hits.modules.sj;
import javax.servlet.http.HttpServletRequest;

import com.hits.common.action.BaseAction;
import com.hits.common.filter.GlobalsFilter;
import com.hits.common.filter.UserLoginFilter;
import com.hits.modules.sj.bean.Slj_info;
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
 * @time 2016-04-02 14:47:55
 * 
 */
@IocBean
@At("/private/sj/slj_info")
@Filters({ @By(type = GlobalsFilter.class), @By(type = UserLoginFilter.class) })
public class Slj_infoAction extends BaseAction {
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
	public boolean add(@Param("..") Slj_info slj_info) {
		return daoCtl.add(dao,slj_info);
	}
	
	//@At
	//@Ok("raw")
	//public String add(@Param("..") Slj_info slj_info) {
	//	return daoCtl.addT(dao,slj_info).getId();
	//}
	
	//@At
	//@Ok("json")
	//public Slj_info view(@Param("id") String id) {
		//return daoCtl.detailByName(dao,Slj_info.class, id);
	//}
	
	//@At
	//@Ok("vm:template.private.sj.Slj_infoUpdate")
	//public Slj_info toupdate(@Param("id") String id, HttpServletRequest req) {
		//return daoCtl.detailByName(dao, Slj_info.class, id);//html:obj
	//}
	
	@At
	public boolean update(@Param("..") Slj_info slj_info) {
		return daoCtl.update(dao, slj_info);
	}
	
	//@At
	//public boolean delete(@Param("id") String id) {
		//return daoCtl.deleteById(dao, Slj_info.class, id);
	//}
	
	@At
	public boolean deleteIds(@Param("ids") String[] ids) {
		return dao.clear("Slj_info", Cnd.where("id", "in", ids)) > 0;
	}
	
	@At
	@Ok("raw")
	public JSONObject list(@Param("page") int curPage, @Param("rows") int pageSize){
		Criteria cri = Cnd.cri();
		cri.where().and("1","=",1);
		cri.getOrderBy().desc("");
		return daoCtl.listPageJson(dao, Slj_info.class, curPage, pageSize, cri);
	}

}