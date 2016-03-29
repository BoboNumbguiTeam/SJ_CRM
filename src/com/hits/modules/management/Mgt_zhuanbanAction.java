package com.hits.modules.management;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hits.common.action.BaseAction;
import com.hits.common.filter.GlobalsFilter;
import com.hits.common.filter.UserLoginFilter;
import com.hits.common.util.StringUtil;
import com.hits.modules.management.bean.Mgt_zhuanban;
import com.hits.modules.nbjl.bean.Msg_fj;
import net.sf.json.JSONObject;
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

/**
 * @author Numbgui
 * @time 2015-05-23 14:21:13
 * 
 */
@IocBean
@At("/private/management/zhuanban")
@Filters({ @By(type = GlobalsFilter.class), @By(type = UserLoginFilter.class) })
public class Mgt_zhuanbanAction extends BaseAction {
	@Inject
	protected Dao dao;

	@At("")
	@Ok("->:/private/management/Mgt_zhuanban.html")
	public void index(@Param("sys_menu") String sys_menu,HttpServletRequest req) {
		req.setAttribute("sys_menu",sys_menu);
	}
	
	@At
	@Ok("->:/private/management/Mgt_zhuanbanAdd.html")
	public void toadd() {
	
	}
	
	@At
	@Ok("raw")
	public boolean add(@Param("..") Mgt_zhuanban mgt_zhuanban) {
		return daoCtl.add(dao,mgt_zhuanban);
	}
	
	//@At
	//@Ok("raw")
	//public int add(@Param("..") Mgt_zhuanban mgt_zhuanban) {
	//	return daoCtl.addT(dao,mgt_zhuanban).getId();
	//}
	
	@At
	@Ok("json")
	public Mgt_zhuanban view(@Param("id") int id) {
		return daoCtl.detailById(dao,Mgt_zhuanban.class, id);
	}
	
	@At
	@Ok("->:/private/management/Mgt_zhuanbanUpdate.html")
	public Mgt_zhuanban toupdate(@Param("id") int id, HttpServletRequest req) {
		return daoCtl.detailById(dao, Mgt_zhuanban.class, id);//html:obj
	}
	
	@At
	public boolean update(@Param("..") Mgt_zhuanban mgt_zhuanban) {
		return daoCtl.update(dao, mgt_zhuanban);
	}
	
	@At
	public boolean delete(@Param("id") int id) {
		return daoCtl.deleteById(dao, Mgt_zhuanban.class, id);
	}
	
	@At
	public boolean deleteIds(@Param("ids") Integer[] ids) {
		String[] id = StringUtil.null2String(ids).split(",");
		return daoCtl.deleteByIds(dao, Mgt_zhuanban.class, id);
	}
	
	@At
	@Ok("raw")
	public JSONObject list(@Param("page") int curPage, @Param("rows") int pageSize){
		Criteria cri = Cnd.cri();
		cri.where().and("1","=",1);
		cri.getOrderBy().desc("id");
		return daoCtl.listPageJson(dao, Mgt_zhuanban.class, curPage, pageSize, cri);
	}

}