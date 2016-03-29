package com.hits.modules.sjygl;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hits.modules.sys.bean.Sys_resource;
import com.hits.modules.sys.bean.Sys_role_resource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.sql.Sql;
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
import com.hits.common.util.StringUtil;
import com.hits.modules.sjygl.bean.T_dmjfl;
import com.hits.modules.sys.bean.Sys_user;
import com.hits.util.DateUtil;

/**
 * @author 
 * @time 2014-08-04 14:00:26
 * 
 */
@IocBean
@At("/private/dmjfl/t_dmjfl")
@Filters({ @By(type = GlobalsFilter.class), @By(type = UserLoginFilter.class) })
public class T_dmjflAction extends BaseAction {
	@Inject
	protected Dao dao;

	@At("")
	@Ok("->:/private/sjygl/T_dmjfl.html")
	public void index(HttpSession session,HttpServletRequest req) {
		Sql sql = Sqls.create("select flbh from t_dmjfl order by flbh asc");
		req.setAttribute("list",daoCtl.getStrRowValues(dao,sql));
		req.setAttribute("flList", getFlTable());

		Sys_user user = (Sys_user) session.getAttribute("userSession");
		String[] mp = StringUtil.null2String(
				user.getBtnmap().get("/private/dmjfl/t_dmjfl")).split(";");
		req.setAttribute("btnmap", mp);

		List<Sys_role_resource> reslist = daoCtl.list(dao,Sys_role_resource.class, Cnd.wrap("resourceid = '000100020001'"));
		HashSet<String> set = new HashSet<String>();
		for(Sys_role_resource resource:reslist){
			if(user.getRolelist().contains(resource.getRoleid()+"")){
				String button = resource.getButton();
				if(!"".equals(button)&&button!=null){
					String[] buttons =button.split(",");
					for(int i=0;i<buttons.length;i++){
						set.add(buttons[i]);
					}
				}
			}
		}
		req.setAttribute("buttonset", set);
	}
	
	@At
	@Ok("->:/private/sjygl/T_dmjflAdd.html")
	public void toadd(HttpSession session,HttpServletRequest req) {
		Sql sql = Sqls.create("select flbh from t_dmjfl order by flbh asc");
		req.setAttribute("list", daoCtl.getStrRowValues(dao, sql));
		req.setAttribute("flList", getFlTable());
	}
	
	/**
	 * 新增功能
	 * @param t_dmjfl
	 * @param session
	 * @param request
	 * @return
	 */
	@At
	@Ok("raw")
	public boolean add(@Param("..") T_dmjfl t_dmjfl,HttpSession session, HttpServletRequest request) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		t_dmjfl.setFlbh(daoCtl.getSubMenuId(dao, "t_dmjfl", "flbh", t_dmjfl.getFlbh()));
		t_dmjfl.setCjr(user.getLoginname());
		t_dmjfl.setCjsj(DateUtil.getCurDateTime());
		return daoCtl.add(dao,t_dmjfl);
	}
	
	@At
	@Ok("->:/private/sjygl/T_dmjflView.html")
	public T_dmjfl view(@Param("id") int id) {
		return daoCtl.detailById(dao, T_dmjfl.class, id);
	}
	
	@At
	@Ok("->:/private/sjygl/T_dmjflUpdate.html")
	public T_dmjfl toupdate(@Param("id") int id, HttpServletRequest req) {
		return daoCtl.detailById(dao, T_dmjfl.class, id);
	}
	
	@At
	@Ok("raw")
	public boolean update(@Param("..") T_dmjfl t_dmjfl) {
		return daoCtl.update(dao, t_dmjfl);
	}
	
	@At
	public boolean delete(@Param("id") int id) {
		return daoCtl.deleteById(dao, T_dmjfl.class, id);
	}
	
	@At
	public boolean deleteIds(@Param("ids") String ids) {
		String[] id = StringUtil.null2String(ids).split(",");
		return daoCtl.deleteByIds(dao, T_dmjfl.class, id);
	}
	
	@At
	@Ok("raw")
	public String list(HttpServletRequest req, HttpSession session, @Param("sjyfl") String sjyfl){
		return getJSON(dao,sjyfl).toString();
	}

	private JSONArray getJSON(Dao dao, String id) {
		Criteria cri = Cnd.cri();
		if (null == id || "".equals(id)) {
			cri.where().and("flbh", "like", "____");
		} else {
			cri.where().and("flbh", "like", id + "____");
		}
//		cri.getOrderBy().asc("location");
		cri.getOrderBy().asc("flbh");
		List<T_dmjfl> list = daoCtl.list(dao, T_dmjfl.class, cri);
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			T_dmjfl dmjfl = list.get(i);
			JSONObject jsonobj = new JSONObject();
			String pid = dmjfl.getFlbh().substring(0, dmjfl.getFlbh().length() - 4);
			if (i == 0 || "".equals(pid))
				pid = "0";
			int num = daoCtl.getRowCount(dao, T_dmjfl.class,
					Cnd.where("flbh", "like", dmjfl.getFlbh() + "____"));
			jsonobj.put("flbh", dmjfl.getFlbh());
			jsonobj.put("id", dmjfl.getId());
			jsonobj.put("name", dmjfl.getFlmc());
			jsonobj.put("flbm", dmjfl.getFlbm());
			jsonobj.put("_parentId", pid);
			if (num > 0) {
				jsonobj.put("children", getJSON(dao, dmjfl.getFlbh()));
			}
			array.add(jsonobj);
		}
		return array;
	}

	/**
	 * 获取分类下拉列表
	 * @return
	 */
	private Hashtable<String,String> getFlTable() {
		Sql sql = Sqls.create("select flbh,flmc from t_dmjfl order by flbh asc");
		return daoCtl.getHTable(dao,sql);
	}
}