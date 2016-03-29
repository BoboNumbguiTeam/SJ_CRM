package com.hits.modules.nbjl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hits.modules.sys.bean.Sys_role_resource;
import net.sf.json.JSONObject;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;

import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
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
import com.hits.common.filter.GlobalsFilter;
import com.hits.common.filter.UserLoginFilter;
import com.hits.util.EmptyUtils;
import com.hits.util.DateUtil;
import com.hits.common.util.StringUtil;
import com.hits.modules.nbjl.bean.Msg_fj;
import com.hits.modules.nbjl.bean.Msg_info;
import com.hits.modules.nbjl.bean.Msg_user;
import com.hits.modules.sys.bean.Sys_user;

/**
 * @author
 * @time 2014-05-06 13:33:35
 * 
 */
@IocBean
@At("/private/msg/msgInfo")
@Filters( { @By(type = GlobalsFilter.class), @By(type = UserLoginFilter.class) })
public class Msg_infoAction extends BaseAction {
	@Inject
	protected Dao dao;

	/**
	 * 转到(发给自己的)公告查看列表
	 * @param session
	 * @param req
	 */
	@At
	@Ok("->:/private/msg/tzggList.html")
	public void msg_info(HttpSession session, HttpServletRequest req) {

		Sys_user user = (Sys_user) session.getAttribute("userSession");
		String[] mp = StringUtil.null2String(
				user.getBtnmap().get("/private/msg/msgInfo/msg_info")).split(";");
		req.setAttribute("btnmap", mp);

		List<Sys_role_resource> reslist = daoCtl.list(dao,Sys_role_resource.class, Cnd.wrap("resourceid = '0002001100010002'"));
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

		req.setAttribute("jstate", 0);
	}
	
	/**
	 * 转到自己发布的公告列表里
	 * @param session
	 * @param req
	 */
	@At
	@Ok("->:/private/msg/mytzggList.html")
	public void mymsg_info(HttpSession session, HttpServletRequest req) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		String[] mp = StringUtil.null2String(
				user.getBtnmap().get("/private/msg/msgInfo/mymsg_info")).split(";");
		req.setAttribute("btnmap", mp);

		List<Sys_role_resource> reslist = daoCtl.list(dao,Sys_role_resource.class, Cnd.wrap("resourceid = '0002001100010001'"));
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
		req.setAttribute("status", 1);
	}

	/**
	 * 转到(发给自己的)站内消息查看列表
	 * @param session
	 * @param req
	 */
	@At
	@Ok("->:/private/msg/msgMessage.html")
	public void msgMessage(HttpSession session, HttpServletRequest req) {

		Sys_user user = (Sys_user) session.getAttribute("userSession");
		String[] mp = StringUtil.null2String(
				user.getBtnmap().get("/private/msg/msgInfo/msgMessage")).split(";");
		req.setAttribute("btnmap", mp);

		List<Sys_role_resource> reslist = daoCtl.list(dao,Sys_role_resource.class, Cnd.wrap("resourceid = '0002001100030001'"));
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

		req.setAttribute("jstate", 0);
	}
	
	@At
	@Ok("->:/private/msg/myMsgMessage.html")
	public void myMsgMessage(HttpSession session, HttpServletRequest req) {

		Sys_user user = (Sys_user) session.getAttribute("userSession");
		String[] mp = StringUtil.null2String(
				user.getBtnmap().get("/private/msg/msgInfo/myMsgMessage")).split(";");
		req.setAttribute("btnmap", mp);

		List<Sys_role_resource> reslist = daoCtl.list(dao,Sys_role_resource.class, Cnd.wrap("resourceid = '0002001100010002'"));
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

		req.setAttribute("status", 1);
	}
	
	@At
	@Ok("raw")
	public JSONObject list(HttpServletRequest req, HttpSession session,
			@Param("page") int curPage, @Param("rows") int pageSize,
			@Param("infotype") Integer infotype,
			@Param("jstate") Integer jstate, @Param("status") Integer status,
			@Param("keys") String keys) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		String sql = null;
		Criteria cri = Cnd.cri();
		Cnd cnd = null;
		if (EmptyUtils.isEmpty(status)) {// 查看发给自己的公告
			req.setAttribute("jstate", jstate);
			sql="select  i.*,u.jstate  from msg_info i,msg_user u where i.id=u.msgid and i.infotype=" + infotype + "  and u.jlogin = '"
					+ user.getLoginname()
					+ "' and u.jstate!=2";
//			cnd.j
			if (keys != null && !keys.equals("")) {
				sql += " and (i.title like '%" + keys + "%' or i.content like '%"
						+ keys + "%' or i.subtitle like '%" + keys + "%')";
			}
			sql +=" order by u.jstate";
		} else {// 查看自己发送的公告
			req.setAttribute("status", status);
			sql = "select * from MSG_INFO t where flogin = '"
					+ user.getLoginname() + "' and infotype = " + infotype;
			cnd = Cnd.where("infotype", "=", infotype).and("flogin", "=", user.getLoginname());
			if (keys != null && !keys.equals("")) {
				sql += " and (title like '%" + keys + "%' or content like '%"
						+ keys + "%' or subtitle like '%" + keys + "%')";
				cnd = Cnd.where(Cnd.exps("title","LIKE","%"+keys+"%").or("content","LIKE","%"+keys+"%")).
						and("infotype", "=", infotype).and("flogin", "=", user.getLoginname());
			}
		}
		Pager pager = new Pager();
		pager.setPageNumber(curPage);
		pager.setPageSize(pageSize);

		req.setAttribute("infotype", infotype);
		System.out.println("" + cnd.toString());
		return daoCtl.listPageJson(dao, Msg_info.class,cnd , pager);
	}

	@At
	@Ok("->:/private/msg/msgfj.html")
	public void msgFj() {
	}

	@At
	@Ok("->:/private/msg/tzggAdd.html")
	public void toadd(HttpSession session, HttpServletRequest request,
			@Param("infotype") Integer infotype) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		request.setAttribute("unitname", user.getUnitname());
		request.setAttribute("flogin", user.getLoginname());
		request.setAttribute("unitid", user.getUnitid());
		request.setAttribute("infotype", infotype);
		request.setAttribute("nowDate", DateUtil.getCurDateTime().substring(0,10));
	}

	@At
	@Ok("raw")
	public boolean add(@Param("::msginfo") final Msg_info info,
			@Param("::msguser") final Msg_user user, @Param("fileno") final String fileno,
			@Param("fjmc") final String fjmc, @Param("fjurl") final String fjurl,
			@Param("title") final String title) {
		try {
            final ThreadLocal<Boolean> re = new ThreadLocal<Boolean>();
            Trans.exec(new Atom() {
                public void run() {
					info.setFileno(fileno);
					info.setTitle(title);
					info.setFlogin(user.getFlogin());
					if (EmptyUtils.isNotEmpty(fjmc) && EmptyUtils.isNotEmpty(fjurl)) {
						info.setExt3(fjmc);
					}
					if(info.getInfotype()!=0){
						info.setCtime(DateUtil.getCurDateTime());
					}
					Msg_info msginfo = dao.insert(info);
					if (EmptyUtils.isNotEmpty(msginfo)) {
						user.setMsgid(msginfo.getId());
						if (EmptyUtils.isNotEmpty(msginfo.getCtime()))
							user.setFtime(msginfo.getCtime());
						String[] jlogin = StringUtil.null2String(user.getJlogin()).split(";");
						for (int i = 0; i < jlogin.length; i++) {
							String jjlogin = jlogin[i].substring(
									jlogin[i].indexOf("-") + 1, jlogin[i].length());
							user.setJlogin(jjlogin);
							user.setJstate(0);
							user.setJsign(0);
							dao.insert(user);
						}
						if (EmptyUtils.isNotEmpty(fjmc) && EmptyUtils.isNotEmpty(fjurl)) {
							Msg_fj msgfj = new Msg_fj();
							msgfj.setFjmc(fjmc);
							msgfj.setFjurl(fjurl);
							msgfj.setMsgid(msginfo.getId());
							dao.insert(msgfj);
						}
						System.out.println("保存true");
						re.set(true);
					}else{
						re.set(false);
					}
                }
            });
            return re.get();
    	}catch (Exception e) {
    		System.out.println("添加错误");
    		return false;
    	}
	}
	/**
	 * 跳转到消息添加页面
	 * @param session
	 * @param request
	 */
	@At
	@Ok("->:/private/msg/msgAdd.html")
	public void toaddMess(HttpSession session, HttpServletRequest request) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		request.setAttribute("unitname", user.getUnitname());
		request.setAttribute("flogin", user.getLoginname());
		request.setAttribute("unitid", user.getUnitid());
		request.setAttribute("infotype", 2);
	}
	
	/**
	 * 跳转到消息修改页面
	 * @param id
	 * @param req
	 * @param session
	 */
	@At
	@Ok("->:/private/msg/msgUpdate.html")
	public void toupdateMess(@Param("id") int id, HttpServletRequest req,
			HttpSession session) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		Msg_info msg_info = daoCtl.detailById(dao, Msg_info.class, id);
		List<Msg_user> listUser = daoCtl.list(dao, Msg_user.class, Sqls
				.create("select * from Msg_user where msgid = "
						+ msg_info.getId()));
		List<Msg_fj> listFj = daoCtl.list(dao, Msg_fj.class, Sqls
				.create("select * from Msg_fj where msgid = "
						+ msg_info.getId()));
		List<String> objUser = new ArrayList<String>();
		List<String> objFj = new ArrayList<String>();
		for (int i = 0; i < listUser.size(); i++) {
			Sys_user obj = daoCtl.detailByName(dao, Sys_user.class,
					"loginname", listUser.get(i).getJlogin());
			objUser.add("{realname:'" + obj.getRealname() + "',loginname:'"
					+ obj.getLoginname() + "'}");
		}
		for (Msg_fj fj : listFj) {
			objFj.add("{fjmc:'" + fj.getFjmc() + "',fjurl:'" + fj.getFjurl()
					+ "'}");
		}
		req.setAttribute("obj", msg_info);
		req.setAttribute("listUser", objUser);
		req.setAttribute("listFj", listFj);
		req.setAttribute("fjStr", objFj);
		req.setAttribute("user", user);
	}
	// @At
	// @Ok("raw")
	// public int add(@Param("..") Msg_info msg_info) {
	// return daoCtl.addT(dao,msg_info).getId();
	// }

	@At
	@Ok("->:/private/msg/msgView.html")
	public void view(@Param("id") Integer id, HttpServletRequest request,HttpSession session) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		Msg_info info = daoCtl.detailById(dao, Msg_info.class, id);
		if (EmptyUtils.isNotEmpty(info)) {
			request.setAttribute("obj", info);
			Msg_fj msgFj = daoCtl.detailByName(dao, Msg_fj.class, "msgid", info.getId());
			if (EmptyUtils.isNotEmpty(msgFj)) {
				request.setAttribute("fj", msgFj);
			}
			Msg_user msgUser = daoCtl.detailBySql(dao, Msg_user.class, Sqls.create("select * from msg_user where msgid="+info.getId()+" and jlogin='"+user.getLoginname()+"'"));
			if(msgUser!=null && msgUser.getJstate()==0){//如果是未读消息则，修改其查看状态为“已读”
				msgUser.setJstate(1);
				daoCtl.update(dao, msgUser);
			}
		}
	}

	@At
	@Ok("->:/private/msg/tzggUpdate.html")
	public void toupdate(@Param("id") int id, HttpServletRequest req,
			HttpSession session) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		Msg_info msg_info = daoCtl.detailById(dao, Msg_info.class, id);
		List<Msg_user> listUser = daoCtl.list(dao,Msg_user.class,Cnd.where("msgid", "=", msg_info.getId()));
		List<Msg_fj> listFj = daoCtl.list(dao,Msg_fj.class,Cnd.where("msgid", "=", msg_info.getId()));
		List<String> objUser = new ArrayList<String>();
		List<String> objFj = new ArrayList<String>();
		for (int i = 0; i < listUser.size(); i++) {
			Sys_user obj = daoCtl.detailByName(dao, Sys_user.class,
					"loginname", listUser.get(i).getJlogin());
			objUser.add("{realname:'" + obj.getRealname() + "',loginname:'"
					+ obj.getLoginname() + "'}");
		}
		for (Msg_fj fj : listFj) {
			objFj.add("{fjmc:'" + fj.getFjmc() + "',fjurl:'" + fj.getFjurl()
					+ "'}");
		}
		req.setAttribute("obj", msg_info);
		req.setAttribute("listUser", objUser);
		req.setAttribute("listFj", listFj);
		req.setAttribute("fjStr", objFj);
		req.setAttribute("user", user);
	}

	@At
	public boolean update(@Param("::msginfo") final Msg_info info,
			@Param("::msguser") final Msg_user user, @Param("fileno") final String fileno,
			@Param("fjmc") final String fjmc, @Param("fjurl") final String fjurl,
			@Param("title") final String title) {
		try {
            final ThreadLocal<Boolean> re = new ThreadLocal<Boolean>();
            Trans.exec(new Atom() {
                public void run() {
					Integer msgid = info.getId();
					// 删除数据表中原接收用户和原附件
					dao.execute(Sqls.create("delete msg_fj where msgid = " + msgid));
					dao.execute(Sqls.create("delete msg_user where msgid = " + msgid));
					info.setTitle(title);
					info.setFileno(fileno);
					info.setFlogin(user.getFlogin());
					if (EmptyUtils.isNotEmpty(fjmc) && EmptyUtils.isNotEmpty(fjurl)) {
						info.setExt3(fjmc);
					}
					if(info.getInfotype()!=0){
						info.setCtime(DateUtil.getCurDateTime());
					}
					int ret=dao.update(info);
					System.out.println("ret="+ret);
					if(ret<0){
						re.set(false);
						return;
					}
					Msg_fj msgfj = new Msg_fj();
					if (EmptyUtils.isNotEmpty(info)) {
						user.setMsgid(msgid);
						if (EmptyUtils.isNotEmpty(info.getCtime()))
							user.setFtime(info.getCtime());
						String[] jlogin = StringUtil.null2String(user.getJlogin()).split(";");
						for (int i = 0; i < jlogin.length; i++) {
							String jjlogin = jlogin[i].substring(
									jlogin[i].indexOf("-") + 1, jlogin[i].length());
							user.setJlogin(jjlogin);
							user.setJstate(0);
							user.setJsign(0);
							dao.insert(user);
						}
					}
					if (EmptyUtils.isNotEmpty(fjmc) && EmptyUtils.isNotEmpty(fjurl)) {
						String[] fjmcs = StringUtil.null2String(fjmc).split(",");
						String[] fjurls = StringUtil.null2String(fjurl).split(",");
						msgfj.setMsgid(msgid);
						for (int i = 0; i < fjmcs.length; i++) {
							String fjmc_1 = fjmcs[i];
							String fjurl_1 = fjurls[i];
							msgfj.setFjmc(fjmc_1);
							msgfj.setFjurl(fjurl_1);
							dao.insert(msgfj);
						}
					}
					re.set(true);
                }
            });
            return re.get();
    	}catch (Exception e) {
    		return false;
    	}
	}

	@At
	@Ok("raw")
	public boolean delete(@Param("ids") final String ids) {
		try {
            final ThreadLocal<Boolean> re = new ThreadLocal<Boolean>();
            Trans.exec(new Atom() {
                public void run() {
					String[] id = StringUtil.null2String(ids).split(",");
					boolean result = daoCtl.deleteByIds(dao, Msg_info.class, id);
					if(result){
						for (String pk : id) {
							String sql = "delete msg_user where msgid = " + pk+"";
							dao.execute(Sqls.create(sql));
						}
					}
					re.set(result);
				}
            });
            return re.get();
		}catch (Exception e) {
			return false;
		}
	}

	@At
	@Ok("raw")
	public void revocation(@Param("ids") String ids) {
		String[] id = StringUtil.null2String(ids).split(",");
		for (String pk : id) {
			Msg_info info = daoCtl.detailById(dao, Msg_info.class, Integer
					.parseInt(pk));
			info.setInfostate(2);
			daoCtl.update(dao, info);
		}
	}

	@At
	public boolean deleteIds(@Param("ids") String ids) {
		String[] id = StringUtil.null2String(ids).split(",");
		return daoCtl.deleteByIds(dao, Msg_info.class, id);
	}

	
	/**
	 * 转到自己的所有最新消息的综合查看界面
	 * @param session
	 * @param req
	 */
	@At
	@Ok("->:/private/msg/notices.html")
	public void showNotices(HttpSession session, HttpServletRequest req) {
		System.out.println("查看消息");
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		int ggnum=daoCtl.getIntRowValue(dao, Sqls.create("select count(i.id) from msg_info i where i.infotype=0 and i.id in (select u.msgid from MSG_USER u where u.jstate =0 and u.jlogin = '"+user.getLoginname()+"' )"));
		int znxxnum=daoCtl.getIntRowValue(dao, Sqls.create("select count(i.id) from msg_info i where i.infotype=2 and i.id in (select u.msgid from MSG_USER u where u.jstate =0 and u.jlogin = '"+user.getLoginname()+"' )"));
		int wjnum=daoCtl.getIntRowValue(dao, Sqls.create("select count(i.id) from msg_info i where i.infotype=1 and i.id in (select u.msgid from MSG_USER u where u.jstate =0 and u.jlogin = '"+user.getLoginname()+"' )"));
		req.setAttribute("ggnum", ggnum);
		req.setAttribute("znxxnum", znxxnum);
		req.setAttribute("wjnum", wjnum);
		String sql = null;
		sql="select  i.*  from msg_info i,msg_user u where i.id=u.msgid and u.jlogin = '"+ user.getLoginname()+ "' and u.jstate=0";
		sql +=" order by i.id";
		List<Msg_info> t=daoCtl.list(dao, Msg_info.class, Sqls.create(sql));
		req.setAttribute("list", t);
	}
}