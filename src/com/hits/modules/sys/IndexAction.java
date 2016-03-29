package com.hits.modules.sys;

import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import com.hits.common.action.BaseAction;
import com.hits.common.util.DecodeUtil;
import com.hits.common.util.StringUtil;
import com.hits.common.filter.*;
import com.hits.modules.sys.bean.Cms_user_style;
import com.hits.modules.sys.bean.Sys_resource;
import com.hits.modules.sys.bean.Sys_user;

/**
 * @author Wizzer.cn
 * @time 2012-9-13 上午10:54:04
 * 
 */
@IocBean
@At("/private")
@Filters({ @By(type = GlobalsFilter.class), @By(type = UserLoginFilter.class) })
public class IndexAction extends BaseAction {
	@Inject
	protected Dao dao;
	@At
	public void dolock(HttpServletRequest req, HttpSession session) {
		session.setAttribute("validate", "openLockWindow();");
	}
	@At
	@Ok("->:/private/lock.html")
	public void lock(HttpServletRequest req, HttpSession session) {
		
	}
	
	/**
	 * 打开系统帮助页面
	 * @param req
	 * @param session
	 */
	@At
	@Ok("->:/private/system_help.html")
	public void sysHelp(HttpServletRequest req, HttpSession session) {
		
	}
	/**
	 * 打开系统帮助pdf浏览界面
	 * @param req
	 * @param session
	 */
	@At
	@Ok("->:/private/helpPdf.html")
	public void helppdf(HttpServletRequest req, HttpSession session) {
		
	}
	
	/**
	 * 打开系统帮助视频浏览界面
	 * @param req
	 * @param session
	 */
	@At
	@Ok("->:/private/helpMedia.html")
	public void helpmedia(HttpServletRequest req, HttpSession session) {
		
	}
	@At
	@Ok("raw")
	public boolean reload(@Param("resid") String resid,HttpSession session){
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		System.out.println(resid+"ddd");
		if(daoCtl.update(dao, Sys_user.class, Chain.make("loginresid", resid),Cnd.where("userid","=",user.getUserid()))){
			user.setLoginresid(resid);
			session.setAttribute("userSession", user);
			return true;
		}
		return false;
	}
	@At
	@Ok("raw")
	public String dounlock(@Param("password") String password,HttpServletRequest req, HttpSession session) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		if (!StringUtil.null2String(password).equals(
				DecodeUtil.Decrypt(user.getPassword()))) {
			return "密码不正确，请输入当前登陆用户密码！";
		}else{
			session.setAttribute("validate", "");
			return "true";
		}
		
	}
	@At
	@Ok("->:/private/index.html")
	public void index(@Param("fenjihao")String fenjihao,HttpServletRequest req, HttpSession session,ServletContext content) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		System.out.println("阿什顿");
		Sql sql = Sqls.create("select roleid from sys_user_role where userid=@userid");
		sql.params().set("userid", user.getUserid());
		List<String> rolelist = daoCtl.getStrRowValues(dao,sql);
		// 判断是否为系统管理员角色
		if (rolelist.contains("2")) {
			user.setSysrole(true);
		} else {
			user.setSysrole(false);
		}
		user.setRolelist(rolelist);

		//判断是否为话务员角色
		if(rolelist.contains("4")){
			req.setAttribute("cti","cti");
		}

		// 将用户所属角色塞入内存
		session.setAttribute("userSession", user);
		Sql sql1=Sqls
				.create("select distinct resourceid from sys_role_resource where ( roleid in(select roleid from sys_user_role where userid=@userid) or roleid=1) and resourceid not in(select id from sys_resource where state=1)");
		sql1.params().set("userid", user.getUserid());
		user.setReslist(daoCtl.getStrRowValues(dao,sql1));
		// 获取用户一级资源菜单
		List<Sys_resource> moduleslist = daoCtl.list(dao,
				Sys_resource.class,
				Cnd.wrap(" id like '____' and id in "
						+ StringUtil.getStrsplit(user.getReslist())
						+ " order by location "));
		req.setAttribute("moduleslist", moduleslist);
		String resid=StringUtil.null2String(user.getLoginresid());
		if("".equals(resid)){
			for(Sys_resource res : moduleslist){
				resid=res.getId();
				break;
			}
		}
		// 获取用户二级资源菜单
		List<Sys_resource> modulessublist = daoCtl.list(dao,
						Sys_resource.class,
						Cnd.wrap(" id like '"+resid+"____' and id in "
								+ StringUtil.getStrsplit(user.getReslist())
								+ " order by location "));
		req.setAttribute("modulessublist", modulessublist);
		req.setAttribute("resid", resid);
		// 获取用户资源button HashMap
		List<List<String>> reslist = daoCtl
				.getMulRowValue(dao,Sqls
						.create("SELECT a.url,b.button FROM sys_resource a,sys_role_resource b WHERE a.ID=b.RESOURCEID "
								+ " AND (b.button<>'' or b.button is not null) AND ( b.roleid IN(SELECT roleid FROM sys_user_role WHERE userid="
								+ user.getUserid()
								+ ") OR roleid=1) "
								+ " AND b.resourceid NOT IN(SELECT id FROM sys_resource WHERE state=1)"));
		Hashtable<String, String> btnmap = new Hashtable<String, String>();
		for (List<String> obj : reslist) {
			String key = StringUtil.null2String(obj.get(0));
			String value = StringUtil.null2String(btnmap.get(key))
					+ StringUtil.null2String(obj.get(1));
			btnmap.put(key, value);
		}
		user.setBtnmap(btnmap);
		req.setAttribute("validate", session.getAttribute("validate"));
		//查询目前该用户的未读消息
		int notices_num=daoCtl.getIntRowValue(dao, Sqls.create("select count(id) from msg_info i where id in (select u.msgid from msg_user u where u.jstate =0  and jlogin = '"+user.getLoginname()+"' )"));
		req.setAttribute("notices_num", notices_num);
		
		//返回工号
		req.setAttribute("user", user);
		//分机号
		req.setAttribute("fenjihao",fenjihao);
		// 获取当前用户配色方案
		Cms_user_style cmsUserStyle = daoCtl.detailByCnd(dao, Cms_user_style.class, Cnd.where("user_id","=",user.getUserid()));
		if(cmsUserStyle!=null){
			content.setAttribute("stylename", cmsUserStyle.getStylename());
		}else{
			content.setAttribute("stylename", "green");
		}
	}
	//去掉督办中心工作流程
	public void cancelDB(){
		Trans.exec(new Atom() {
			
			public void run() {
				daoCtl.update(dao, Sys_resource.class, Chain.make("state", "1"), Cnd.where("id", "in", "'0002000200010002','0002000200010006','0002000200010004','0002000200020001'"));
				daoCtl.update(dao, Sys_resource.class, Chain.make("id", "0002000200010008"), Cnd.where("id","=","0002000200020006"));
				daoCtl.update(dao, Sys_resource.class, Chain.make("id", "0002000200010009"), Cnd.where("id","=","0002000200020005"));
				daoCtl.update(dao, Sys_resource.class, Chain.make("id", "0002000200010010"), Cnd.where("id","=","0002000200020003"));
				daoCtl.update(dao, Sys_resource.class, Chain.make("id", "0002000200010011"), Cnd.where("id","=","0002000200020004"));
				daoCtl.update(dao, Sys_resource.class, Chain.make("id", "0002000200010012"), Cnd.where("id","=","0002000200020007"));
				daoCtl.update(dao, Sys_resource.class, Chain.make("id", "0002000200010013"), Cnd.where("id","=","0002000200020008"));
				daoCtl.update(dao, Sys_resource.class, Chain.make("id", "0002000200010014"), Cnd.where("id","=","0002000200020009"));
			}
		});
	}
	
	//保留督办中心流程
	public void useDB(){
		Trans.exec(new Atom() {
			
			public void run() {
				daoCtl.update(dao, Sys_resource.class, Chain.make("id", "0002000200020006"), Cnd.where("id","=","0002000200010008"));
				daoCtl.update(dao, Sys_resource.class, Chain.make("id", "0002000200020005"), Cnd.where("id","=","0002000200010009"));
				daoCtl.update(dao, Sys_resource.class, Chain.make("id", "0002000200020003"), Cnd.where("id","=","0002000200010010"));
				daoCtl.update(dao, Sys_resource.class, Chain.make("id", "0002000200020004"), Cnd.where("id","=","0002000200010011"));
				daoCtl.update(dao, Sys_resource.class, Chain.make("id", "0002000200020007"), Cnd.where("id","=","0002000200010012"));
				daoCtl.update(dao, Sys_resource.class, Chain.make("id", "0002000200020008"), Cnd.where("id","=","0002000200010013"));
				daoCtl.update(dao, Sys_resource.class, Chain.make("id", "0002000200020009"), Cnd.where("id","=","0002000200010014"));
				daoCtl.update(dao, Sys_resource.class, Chain.make("state", "0"), Cnd.where("id", "in", "'0002000200010002','0002000200010006','0002000200010004','0002000200020001','0002000200020008'"));
			}
		});
	}
	
	
	@At
	@Ok("->:/private/left.html")
	public void left(@Param("sys_menuid") String sys_menuid,
			HttpServletRequest req, HttpSession session) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		List<Sys_resource> menulist = daoCtl.list(dao,
				Sys_resource.class,
				Cnd.wrap(" id like '" + sys_menuid + "____' and id in "
						+ StringUtil.getStrsplit(user.getReslist())
						+ " order by LOCATION "));
		Hashtable<String, List<Sys_resource>> threemenu = new Hashtable<String, List<Sys_resource>>();
		for(int i=0;i<menulist.size();i++){
			List<Sys_resource> threemenulist = daoCtl.list(dao,
					Sys_resource.class,
					Cnd.wrap(" id like '" + menulist.get(i).getId() + "____' and id in "
							+ StringUtil.getStrsplit(user.getReslist())
							+ " order by LOCATION "));
			threemenu.put(menulist.get(i).getId(), threemenulist);
		} 
		req.setAttribute("menulist", menulist);
		req.setAttribute("threemenulist", threemenu);		
	}

	@At
	@Ok("->:/private/welcome.html")
	public void welcome() {

	}

	@At
	@Ok("->:/private/changeStyle.html")
	public void toChangeStyle(){}
	
	
	@At
	@Ok("->:/private/left.html")
	public String doChangeStyle(@Param("stylename") String stylename,HttpServletRequest req, HttpSession session,ServletContext content) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		Cms_user_style cmsUserStyle = daoCtl.detailByCnd(dao, Cms_user_style.class, Cnd.where("user_id","=",user.getUserid()));
		boolean flag = false;
		if(cmsUserStyle==null||cmsUserStyle.equals("")){
			cmsUserStyle = new Cms_user_style();
			cmsUserStyle.setStylename(stylename);
			cmsUserStyle.setUser_id(user.getUserid());
			flag = daoCtl.add(dao, cmsUserStyle);
		}else{
			cmsUserStyle.setStylename(stylename);
			flag = daoCtl.update(dao, cmsUserStyle);
		}
		content.setAttribute("stylename",cmsUserStyle.getStylename());
		return flag ? "true" : "false" ;
	}
	
	/**
	 * @author wanfly
	 * @time 2014-05-20 11:02:35 
	 * 描述：主要用于页面Ajax查询该登陆用户此时的未读消息（包括通知、站内消息、文件传送）
	 * 的总数。
	 */
	@At
	@Ok("raw")
	public String searchNotics(HttpServletRequest req, HttpSession session) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		int notices_num=daoCtl.getIntRowValue(dao, Sqls.create("select count(i.id) from msg_info i where i.id in (select u.msgid from MSG_USER u where u.jstate =0  and u.jlogin = '"+user.getLoginname()+"' )"));
		//System.out.println("查询消息="+notices_num);
		return notices_num+"";
	}

}
