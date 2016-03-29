package com.hits.modules.zsk;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
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
import com.hits.modules.sys.bean.Sys_unit;
import com.hits.modules.zsk.bean.Zs_info;
import com.hits.modules.zsk.bean.Zs_typeinfo;


@IocBean
@At("/private/zsk/zs_info/infoSearch")
@Filters({ @By(type = GlobalsFilter.class), @By(type = UserLoginFilter.class) })
public class Zs_searchAction extends BaseAction{
	
	@Inject
	protected Dao dao;
	

	@At("")
	@Ok("->:/private/zsk/Zs_infoSearch.html")
	public void zs_infoSearch(HttpSession session,HttpServletRequest req) {
		Gson gson = new Gson();
		List<Zs_typeinfo> typelist = daoCtl.list(dao, Zs_typeinfo.class, Cnd.orderBy().asc("id"));
		req.setAttribute("typelist", typelist);

		Hashtable<String,String> infoMap = daoCtl.getHTable(dao, Sqls.create(" SELECT id,NAME FROM zs_typeinfo order by id "));
		req.setAttribute("infomap", gson.toJson(infoMap));
	}
	

	 @At
	 @Ok("raw")
	 public JSONObject list(@Param("page") int curPage, @Param("rows") int pageSize,@Param("titlekey")String title,@Param("infotype")String infotype){
		 	Criteria cri = Cnd.cri();
		 	if(!title.equals("")&&title!=null){
				cri.where().andLike("title","%"+title+"%");
		    }
		    if(!infotype.equals("")&&infotype!=null){
				cri.where().and("typeid","=",infotype);
		    }
	        return daoCtl.listPageJson(dao, Zs_info.class, curPage, pageSize, cri);
	 }

	
	

	
	@At
	@Ok("->:/private/zsk/Zs_infoDetail.html")
	public void detail(@Param("id") int id, HttpServletRequest req) {
		Zs_info zs_info = daoCtl.detailById(dao, Zs_info.class, id);
        req.setAttribute("dateNo", zs_info.getSbdate().split("\\-")[0]+zs_info.getSbdate().split("\\-")[1]+zs_info.getSbdate().split("\\-")[2]);
		req.setAttribute("obj", zs_info);
		req.setAttribute("unit", daoCtl.detailByName(dao, Sys_unit.class, "id", zs_info.getUnitid()).getName());
	}
	
	
}
