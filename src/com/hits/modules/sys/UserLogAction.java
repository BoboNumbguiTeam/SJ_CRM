package com.hits.modules.sys;

import net.sf.json.JSONObject;

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

import com.hits.common.action.BaseAction;
import com.hits.common.filter.GlobalsFilter;
import com.hits.common.filter.UserLoginFilter;
import com.hits.modules.sys.bean.Sys_user_log;

/** 
 * 类描述： 
 * 创建人：Wizzer 
 * 联系方式：www.wizzer.cn
 * 创建时间：2013-12-2 下午6:00:07 
 * @version 
 */
@IocBean
@At("/private/sys/user")
@Filters({ @By(type = GlobalsFilter.class), @By(type = UserLoginFilter.class) })
public class UserLogAction extends BaseAction {
	@Inject
	protected Dao dao;
	@At
	@Ok("->:/private/sys/userLog.html")
	public void log() {
		
	}
	@At
	@Ok("raw")
	public JSONObject loglist(@Param("page") int curPage,@Param("rows") int pageSize,
			@Param("SearchUserName") String SearchUserName) {
		Criteria cri = Cnd.cri();

		if (!"".equals(SearchUserName)) {
			SqlExpressionGroup exp = Cnd.exps("LOGINNAME", "LIKE", "%"+SearchUserName+"%").or("REALNAME", "LIKE", "%"+SearchUserName+"%");
			cri.where().and(exp);
		} 
		cri.getOrderBy().desc("LOGINTIME");
		return daoCtl.listPageJson(dao, Sys_user_log.class, curPage,
				pageSize, cri);
	}
}
