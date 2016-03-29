package com.hits.modules.zsk;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.hits.modules.sys.bean.Sys_user;
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
import com.hits.modules.zsk.bean.Zs_info;
import com.hits.modules.zsk.bean.Zs_typeinfo;

/**
 * @author lxy
 * @time 2013-12-05 14:56:35
 * 
 */
@IocBean
@At("/private/zsk/zs_info")
@Filters({ @By(type = GlobalsFilter.class), @By(type = UserLoginFilter.class) })
public class Zs_infoAction extends BaseAction {
	@Inject
	protected Dao dao;

	
	@At("")
	@Ok("->:/private/zsk/Zs_info.html")
	public void zs_info(HttpSession session,HttpServletRequest req) {
		Gson gson = new Gson();
		Hashtable<String,String> infoMap = daoCtl.getHTable(dao, Sqls.create(" SELECT id,NAME FROM zs_typeinfo order by id "));
		req.setAttribute("infomap", gson.toJson(infoMap));
	}

    /**
     *  跳转到Add页面
     * @return
     */
	@At
	@Ok("->:/private/zsk/Zs_infoAdd.html")
	public List<Zs_typeinfo> toadd() {
		return this.daoCtl.list(this.dao, Zs_typeinfo.class, Cnd.orderBy().asc("id"));
	}

    /**
     * 从页面获取新增知识的数据，并且保存到数据库里，返回是否保存成功
     * @param zs_info
     * @param session
     * @return
     */
	@At
	@Ok("raw")
	public boolean addSave(@Param("..") Zs_info zs_info,HttpSession session) {
		zs_info.setId(Integer.parseInt(this.daoCtl.getSubMenuId(this.dao, "Zs_info", "id", zs_info.getId() + "")));
        Sys_user user = (Sys_user) session.getAttribute("userSession");
        zs_info.setUnitid(user.getUnitid());
        zs_info.setSbuser(user.getLoginname());
        zs_info.setSbdate(new java.sql.Date(System.currentTimeMillis())+"");
		return daoCtl.add(dao,zs_info);
	}
	
	//@At
	//@Ok("raw")
	//public int add(@Param("..") Zs_info zs_info) {
	//	return daoCtl.addT(dao,zs_info).getId();
	//}
	
	@At
	@Ok("json")
	public Zs_info view(@Param("id") int id) {
		return daoCtl.detailById(dao,Zs_info.class, id);
	}

    /**
     *  根据ID获取当前知识的信息，并且返回给页面
     * @param id
     * @param req
     */
	@At
	@Ok("->:/private/zsk/Zs_infoUpdate.html")
	public void toupdate(@Param("id") int id, HttpServletRequest req) {
        Zs_info zs_info = daoCtl.detailById(dao, Zs_info.class, id);
        List typeList = daoCtl.list(dao,Zs_typeinfo.class,Cnd.orderBy().asc("id"));
		req.setAttribute("obj", zs_info);
        req.setAttribute("typObj",typeList);
	}

    /**
     *  更新知识内容
     * @param zs_info
     * @param req
     * @return
     */
	@At
	public boolean update(@Param("..") Zs_info zs_info,HttpServletRequest req) {
        String typeName = req.getParameter("typeid");
        zs_info.setTypeid(typeName);
		return daoCtl.update(dao, zs_info);
	}
	
	@At
	public boolean delete(@Param("id") int id) {
		return daoCtl.deleteById(dao, Zs_info.class, id);
	}

    /**
     *  批量删除
     * @param ids
     * @return
     */
	@At
	public boolean deleteIds(@Param("ids") String ids) {
		String[] id = StringUtil.null2String(ids).split(",");
		return daoCtl.deleteByIds(dao, Zs_info.class, id);
	}

    /**
     *  分页查询，查询两张表从 Zs_typeinfo 表中获取类型信息 ，返回 JSONObject
     * @param curPage
     * @param pageSize
     * @return
     */
	@At
	@Ok("raw")
	public JSONObject list2(@Param("page") int curPage, @Param("rows") int pageSize){
		Criteria cri = Cnd.cri();
		cri.where().and("1","=","1");
		cri.getOrderBy().desc("id");
        Sql sql = Sqls.create("select id,name from $table ");
        sql.vars().set("table","zs_typeinfo");
        Map typeMap = daoCtl.getHTable(dao,sql);
        List lists = daoCtl.listPage(dao, Zs_info.class, curPage, pageSize, cri).getList();
        List listAll = daoCtl.list(dao,Zs_info.class,cri);
        JSONArray array = new JSONArray();
        for(int i=0,count=lists.size();i<count;i++){
            Zs_info zs = (Zs_info)lists.get(i);
            JSONObject jsonobj = new JSONObject();
            if(typeMap.get(zs.getTypeid())!=null){
                zs.setTypeid(typeMap.get(zs.getTypeid())+"");
            }else{
                zs.setTypeid("");
            }
            jsonobj.put("id", zs.getId());
            jsonobj.put("sbdate", zs.getSbdate());
            jsonobj.put("title", zs.getTitle());
            jsonobj.put("type", zs.getTypeid());
            array.add(jsonobj);
        }
        JSONObject obj = new JSONObject();
        obj.put("total",listAll.size());
        obj.put("rows",array);
        return obj;
	}


    /**
     *  分页查询，使用封装的多表查询方法
     * @param curPage
     * @param pageSize
     * @return
     */
    @At
    @Ok("raw")
    public JSONObject list(@Param("page") int curPage, @Param("rows") int pageSize,HttpServletRequest req){
		Criteria cri = Cnd.cri();
		cri.getOrderBy().asc("typeid").asc("id");
		return daoCtl.listPageJson(dao, Zs_info.class, curPage, pageSize,cri);
    }
    /**
     *  返回所有记录
     * @param id
     * @return
     */
	@At
	@Ok("raw")
	public String listAll(@Param("id") String id){
	    return getJSON(this.dao, id).toString();
	}

	private JSONArray getJSON(Dao dao, String id) {
	    Criteria cri = Cnd.cri();
	    cri.getOrderBy().asc("id");
	    List list = this.daoCtl.list(dao, Zs_info.class, cri);
	    JSONArray array = new JSONArray();
	    for (int i = 0; i < list.size(); i++) {
	      Zs_info res = (Zs_info)list.get(i);
	      JSONObject jsonobj = new JSONObject();
	      jsonobj.put("id", res.getId());
	      jsonobj.put("sbdate", res.getSbdate());
	      jsonobj.put("title", res.getTitle());
          try{
              jsonobj.put("type", daoCtl.detailByName(dao, Zs_typeinfo.class, "id", res.getTypeid()).getName());
          }catch(Exception e){
	            jsonobj.put("type", "");
          }
	      array.add(jsonobj);
	    }
	    return array;
	  }

	
	@At
	@Ok("->:/private/management/user.html")
	public void user(HttpSession session, HttpServletRequest req) {
	}
	
	@At
	@Ok("raw")
	public JSONObject userlist(@Param("unitid") String unitid,
			@Param("page") int curPage,@Param("rows") int pageSize,
			@Param("SearchUserName") String SearchUserName,HttpSession session) {
		Criteria cri = Cnd.cri();
		 
		cri.where().and("unitid","like","0002____");
		if (!"".equals(SearchUserName)) {
			SqlExpressionGroup exp = Cnd.exps("loginname", "LIKE", "%"+SearchUserName+"%").or("realname", "LIKE", "%"+SearchUserName+"%");
			cri.where().and(exp);
		} 
		cri.getOrderBy().asc("userid");
		return daoCtl.listPageJson(dao, Sys_user.class, curPage,
				pageSize, cri);
	}
}