package com.hits.modules.sjygl;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hits.modules.sys.bean.Sys_role_resource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.hits.common.action.BaseAction;
import com.hits.common.config.Globals;
import com.hits.common.filter.GlobalsFilter;
import com.hits.common.filter.UserLoginFilter;
import com.hits.common.util.StringUtil;
import com.hits.modules.sjygl.bean.T_daimjb;
import com.hits.modules.sjygl.bean.T_dmjfl;
import com.hits.modules.sys.bean.Sys_user;

/**
 * @author 
 * @time 2014-08-04 14:00:26
 * 
 */
@IocBean
@At("/private/dmj/t_daimjb")
@Filters({ @By(type = GlobalsFilter.class), @By(type = UserLoginFilter.class) })
public class T_daimjbAction extends BaseAction {
	@Inject
	protected Dao dao;
	
	@At("")
	@Ok("->:/private/sjygl/T_daimjb.html")
	public void daimjb(HttpSession session, HttpServletRequest req) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		String[] mp = StringUtil.null2String(
				user.getBtnmap().get("/private/dmj/t_daimjb")).split(";");
		req.setAttribute("btnmap", mp);

		List<Sys_role_resource> reslist = daoCtl.list(dao,Sys_role_resource.class, Cnd.wrap("resourceid = '000100020002'"));
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
	@Ok("->:/private/sjygl/T_daimjb_idx.html")
	public void index(HttpSession session, HttpServletRequest req) {
	}
	
	@At
	@Ok("raw")
	public String tree(@Param("flbh") String flbh, HttpSession session)
			throws Exception {
		flbh = StringUtil.null2String(flbh);
		JSONArray array = new JSONArray();
		/*if ("".equals(flbh)) {
			JSONObject jsonroot = new JSONObject();
			jsonroot.put("id", "");
			jsonroot.put("pId", "0");
			jsonroot.put("name", "代码集分类");
			jsonroot.put("url", "javascript:list(\"\")");
			jsonroot.put("target", "_self");
			jsonroot.put("icon", Globals.APP_BASE_NAME + "/images/icons/base.png");
			array.add(jsonroot);
		}*/
		Criteria cri = Cnd.cri();
		cri.where().and("flbh", "like", flbh + "____");
		cri.getOrderBy().asc("location");
		cri.getOrderBy().asc("flbh");
		List<T_dmjfl> dmjfllist = daoCtl.list(dao, T_dmjfl.class, cri);
		int i = 0;
		for (T_dmjfl dmjfl : dmjfllist) {
			String pid = dmjfl.getFlbh().substring(0, dmjfl.getFlbh().length() - 4);
			int num = daoCtl.getRowCount(dao, T_dmjfl.class, Cnd.wrap("flbh like '" + dmjfl.getFlbh() + "____'"));
			if (i == 0 || "".equals(pid))
				pid = "0";
			JSONObject obj = new JSONObject();
			obj.put("flbh", dmjfl.getFlbh());
			obj.put("pId", pid);
			obj.put("name", dmjfl.getFlmc());
			obj.put("target", "_self");
			obj.put("isParent", num > 0 ? true : false);
			if(num > 0){
				obj.put("iconSkin",  "pIcon01" );
			}else{
				obj.put("url", "javascript:list(\"" + dmjfl.getFlbh() + "\")");
				obj.put("icon", Globals.APP_BASE_NAME+ "/images/icons/bm.gif");
			}
			array.add(obj);
			i++;
		}
		return array.toString();
	}
	/**
	 * 查询全部
	 * */
	@At
	@Ok("raw")
	public JSONObject listAll(@Param("flbh") String flbh, @Param("page") int curPage,@Param("rows") int pageSize,  HttpServletRequest req) {
		Criteria cri = Cnd.cri();
		flbh = StringUtil.null2String(flbh);
		cri.where().and("ssfl", "=", flbh);
		cri.getOrderBy().asc("f_vc_daimz1");
		JSONObject json = daoCtl.listPageJson(dao, T_daimjb.class, curPage, pageSize, cri);
		req.setAttribute("flbh", flbh);
		return json;
		//return getJSON(dao, f_vc_daimz1 ).toString();
	}
	
	@At
	@Ok("->:/private/sjygl/T_daimjbAdd.html")
	public void toAdd(@Param("flbh") String flbh, HttpServletRequest req){
		Sql sql = Sqls.create("select flbh from t_dmjfl order by flbh asc");
		req.setAttribute("list", daoCtl.getStrRowValues(dao, sql));
		req.setAttribute("flbh", flbh);
		req.setAttribute("flList", getFlTable());
	}
	
	@At
	public String add(@Param("..") T_daimjb t_dmjb, HttpSession session) {
		boolean result = false;
		try {
			Sys_user user = (Sys_user) session.getAttribute("userSession");
			t_dmjb.setF_vc_caozr(user.getLoginname());//DateUtil.str2date(
			result = daoCtl.add(dao, t_dmjb);
			System.out.println("result : "+result);
			if(result){
				return t_dmjb.getSsfl();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return "";
	}
	
	@At
	@Ok("->:/private/sjygl/T_daimjbUpdate.html")
	public void toupdate(@Param("id") long id,HttpSession session, HttpServletRequest req){
		T_daimjb obj = daoCtl.detailById(dao, T_daimjb.class, id);
		req.setAttribute("obj", obj);
		Sql sql = Sqls.create("select flbh from t_dmjfl order by flbh asc");
		req.setAttribute("list", daoCtl.getStrRowValues(dao, sql));
		req.setAttribute("flList", getFlTable());
	}
	@At
	public boolean update(@Param("..") T_daimjb t_dmjb) {
		boolean result = false;
		try {
			result = daoCtl.update(dao, t_dmjb);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		return result;
	}
	
	@At
	public boolean delete(@Param("ids") String ids) {
		String[] id = StringUtil.null2String(ids).split(",");
		return daoCtl.deleteByIds(dao, T_daimjb.class, id);
	}
	
	private JSONArray getJSON(Dao dao, String f_vc_daimz1) {
		Criteria cri = Cnd.cri();
		f_vc_daimz1="".equals(f_vc_daimz1)?"11":f_vc_daimz1;
		cri.where().and("ssfl", "like", "000200020001");
		cri.where().and("f_vc_daimz1", "like", f_vc_daimz1+"__");
		cri.getOrderBy().asc("location");
		cri.getOrderBy().asc("f_vc_daimz1");
		List<T_daimjb> list = daoCtl.list(dao, T_daimjb.class, cri);
		/*daoCtl.listPageJson(dao, Sys_user.class, curPage,
				pageSize, cri);*/
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			T_daimjb dmj = list.get(i);
			JSONObject jsonobj = new JSONObject();
			String pid = "";
			if(dmj.getF_vc_daimz1().length()>=2){
				pid = dmj.getF_vc_daimz1().substring(0, dmj.getF_vc_daimz1().length() - 2);
			}
			if (i == 0 || "".equals(pid))
				pid = "0";
			int num = daoCtl.getRowCount(dao, T_daimjb.class, Cnd.where("f_vc_daimz1", "like", dmj.getF_vc_daimz1() + "__"));
			jsonobj.put("f_vc_daimz1", dmj.getF_vc_daimz1());
			jsonobj.put("f_vc_daimmc", dmj.getF_vc_daimmc());
			if("1".equals(dmj.getF_vc_daimjbj())){
				jsonobj.put("f_vc_daimjbj", "激活");
			} else {
				jsonobj.put("f_vc_daimjbj", "冻结");
			}
			jsonobj.put("f_vc_daimz2", dmj.getF_vc_daimz2());
			jsonobj.put("_parentId", pid);
			if (num > 0) {
				jsonobj.put("children", getJSON(dao, dmj.getF_vc_daimz1()));
			}
			System.out.println(jsonobj.toString());
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