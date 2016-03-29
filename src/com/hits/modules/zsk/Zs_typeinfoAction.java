package com.hits.modules.zsk;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
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

import com.hits.common.action.BaseAction;
import com.hits.common.filter.GlobalsFilter;
import com.hits.common.filter.UserLoginFilter;
import com.hits.common.util.StringUtil;
import com.hits.modules.zsk.bean.Zs_typeinfo;

/*******************************************************************************
 * @author lxy
 * @time 2013-12-05 14:56:35 
 * modify by Frigyes 2014-5-14 增加了类注释
 * 描述：主要用户知识分类的管理
 * 非必要时情况禁止写where 1=1，请用其他条件代替。
 ******************************************************************************/
 
@IocBean
@At("/private/zsk/type")
@Filters( { @By(type = GlobalsFilter.class), @By(type = UserLoginFilter.class) })
public class Zs_typeinfoAction extends BaseAction {
	@Inject
	protected Dao dao;

	@At("")
	@Ok("->:/private/zsk/Zs_typeinfo.html")
	public void zs_typeinfo(HttpSession session, HttpServletRequest req) {

	}
	
	/**
	 * 返回分类添加页面
	 * @return
	 */
	@At
	@Ok("->:/private/zsk/Zs_typeinfoAdd.html")
	public List<Zs_typeinfo> toAdd() {
		return this.daoCtl.list(this.dao, Zs_typeinfo.class, Cnd.orderBy().asc("id"));
	}

	/**
	 * 分类添加保存
	 */
	@At
	@Ok("raw")
	public boolean addSave(@Param("..")
	Zs_typeinfo type) {
		type.setId(this.daoCtl.getSubMenuId(this.dao, "Zs_typeinfo", "id", type.getId()));
		return daoCtl.add(dao, type);
	}

	/**
	 * 返回分类查询页面值
	 */
	@At
	@Ok("json")
	public Zs_typeinfo view(@Param("id")
	String id) {
		return daoCtl.detailByName(dao, Zs_typeinfo.class, id);
	}

	/**
	 * 返回分类修改页面
	 */
	@At
	@Ok("->:/private/zsk/Zs_typeinfoUpdate.html")
	public void toupdate(@Param("id")
	String id, HttpServletRequest req) {
		Zs_typeinfo zs_typeinfo = daoCtl.detailByName(dao, Zs_typeinfo.class,id);
		req.setAttribute("obj", zs_typeinfo);
	}

	/**
	 * 根据对象修改分类
	 */
	@At
	public boolean update(@Param("..")
	Zs_typeinfo zs_typeinfo) {
		return daoCtl.update(dao, zs_typeinfo);
	}

	/**
	 * 根据ID删除分类
	 */
	@At
	public boolean delete(@Param("id")
	String id) {
		return daoCtl.deleteByName(dao, Zs_typeinfo.class, id);
	}

	/*
	 * 根据多个ID删除分类
	 */
	@At
	@Ok("raw")
	public boolean del(@Param("id")
	String ids) {
		boolean result = false;
		String[] id = StringUtil.null2String(ids).split(",");
		result = this.daoCtl.deleteByNames(this.dao, Zs_typeinfo.class, id);
		return result;
	}

	/**
	 * 根据多个ID判断是否有上级分类
	 */
	@At
	@Ok("raw")
	public boolean getCount(@Param("id")
	String ids) {
		boolean result = false;
		String[] id = StringUtil.null2String(ids).split(",");
		for (int i = 0; i < id.length; i++) {
			int num = this.daoCtl.getRowCount(dao, Zs_typeinfo.class, Cnd.where("id", "like", id[i] + "____"));
			if (num > 0) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * 返回分页LIST数据
	 */
	@At
	@Ok("raw")
	public JSONObject list(@Param("page")
	int curPage, @Param("rows")
	int pageSize) {
		Criteria cri = Cnd.cri();
		// 非必要时情况禁止写where 1=1，请用其他条件代替。
		// cri.where().and("1","=","1");
		cri.getOrderBy().desc("id");
		return daoCtl.listPageJson(dao, Zs_typeinfo.class, curPage, pageSize,cri);
	}

	/**
	 * 根据ID获取JSON字符串
	 */
	@At
	@Ok("raw")
	public String listAll(@Param("id")
	String id) {
		return getJSON(this.dao, id).toString();
	}

	/**
	 * 返回JSON字符串
	 */
	private JSONArray getJSON(Dao dao, String id) {
		Criteria cri = Cnd.cri();
		if ((id == null) || ("".equals(id))) {
			cri.where().and("id", "like", "____");
		} else {
			cri.where().and("id", "like", id + "____");
		}
		cri.getOrderBy().asc("id");
		List<Zs_typeinfo> list = this.daoCtl.list(dao, Zs_typeinfo.class, cri);
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Zs_typeinfo res = (Zs_typeinfo) list.get(i);
			JSONObject jsonobj = new JSONObject();
			String pid = res.getId().substring(0, res.getId().length() - 4);
			if ((i == 0) || ("".equals(pid)))
				pid = "0";
			int num = this.daoCtl.getRowCount(dao, Zs_typeinfo.class, Cnd.where("id", "like", res.getId() + "____"));
			jsonobj.put("id", res.getId());
			jsonobj.put("name", res.getName());
			jsonobj.put("_parentId", pid);
			if (num > 0) {
				jsonobj.put("children", getJSON(dao, res.getId()));
			}
			array.add(jsonobj);
		}
		return array;
	}

}