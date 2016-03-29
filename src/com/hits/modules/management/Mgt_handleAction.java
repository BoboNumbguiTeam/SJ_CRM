package com.hits.modules.management;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hits.modules.msg.SmsClient;
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
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import com.hits.common.action.BaseAction;
import com.hits.common.filter.GlobalsFilter;
import com.hits.common.filter.UserLoginFilter;
import com.hits.common.util.DateUtil;
import com.hits.common.util.StringUtil;

import com.hits.modules.management.bean.Mgt_callback;
import com.hits.modules.management.bean.Mgt_handle;
import com.hits.modules.management.bean.Mgt_info;
import com.hits.modules.management.bean.Mgt_message;
import com.hits.modules.management.bean.Mgt_zhuanban;
import com.hits.modules.sys.bean.Sys_user;
import com.hits.util.PageObj;

/**
 * @author yhb
 * @time 2015-06-01 15:56:09
 *
 */
@IocBean
@At("/private/handle")
@Filters({ @By(type = GlobalsFilter.class), @By(type = UserLoginFilter.class) })
public class Mgt_handleAction extends BaseAction {
	@Inject
	protected Dao dao;

	@At
	@Ok("->:/private/handle/dbllist.html")
	public void todeal(HttpServletRequest req) {

	}

	/**
	 * 待办理件查询
	 **/
	@At
	@Ok("raw")
	public String dcl(@Param("page") int curPage, @Param("rows") int pageSize,
					  HttpSession session, @Param("zb_date") String zb_date,
					  @Param("title") String title,HttpServletRequest req) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		Hashtable<String, String> repairsHT = daoCtl.
				getHTable(
						dao,
						Sqls
								.create("select CONCAT(ssfl,f_vc_daimz1),f_vc_daimmc from t_daimjb where ssfl like '00020002%'"));
		String sqlstr = "SELECT a.id,(UNIX_TIMESTAMP(CAST(need_date AS DATETIME)) - UNIX_TIMESTAMP(NOW()))/60 as light," +
				"a.id as info_id ,b.zb_date,a.repairs_type,b.need_date,b.seed_type,a.title," +
				"a.status ,a.phone_type FROM mgt_info a,mgt_zhuanban b WHERE a.id=b.info_id AND" +
				" a.state=2 AND a.status IN (2,3) AND b.state=0 and b.zb_management='"+user.getUnitid()+"'";
		if (notnull(zb_date)) {
			zb_date = zb_date.substring(0, 10);
			sqlstr += " and left(b.zb_date,10) = '"
					+ zb_date + "'";
		}
		if (notnull(title)) {
			sqlstr += " and a.title like '%" + title + "%'";
		}

		sqlstr += " order by b.zb_date desc";
		System.out.println("待办理" + sqlstr);
		QueryResult qr = daoCtl.listPageSql(dao, Sqls.create(sqlstr), curPage,
				pageSize);
		List<Map<String, Object>> list = (List<Map<String, Object>>) qr
				.getList();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("repairs_type",
					n2s(repairsHT.get(n2s(list.get(i).get("phone_type"))+n2s(list.get(i).get("repairs_type")))));
		}
		return PageObj.getPageJSON(qr);
	}

	/**
	 * 跳转到已反馈页面
	 * @param req
	 */
	@At
	@Ok("->:/private/handle/yfklist.html")
	public void answer(HttpServletRequest req) {

	}

	/**
	 * 已反馈件查询
	 **/
	@At
	@Ok("raw")
	public String yfk(@Param("page") int curPage, @Param("rows") int pageSize,
					  HttpSession session, @Param("zb_date") String zb_date,
					  @Param("title") String title,HttpServletRequest req) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		Hashtable<String, String> repairsHT = daoCtl.
				getHTable(
						dao,
						Sqls
								.create("select CONCAT(ssfl,f_vc_daimz1),f_vc_daimmc from t_daimjb where ssfl like '00020002%'"));
		String sqlstr = "SELECT a.id," +
				"a.id as info_id ,b.zb_date,a.repairs_type,b.need_date,b.seed_type,a.title," +
				"a.status ,a.phone_type FROM mgt_info a,mgt_zhuanban b WHERE a.id=b.info_id AND" +
				" a.state=2 AND a.status in (4,5) AND b.state=1 and b.zb_user='"+user.getLoginname()+"'";
		if (notnull(zb_date)) {
			zb_date = zb_date.substring(0, 10);
			sqlstr += " and left(b.zb_date,10) = '"
					+ zb_date + "'";
		}
		if (notnull(title)) {
			sqlstr += " and a.title like '%" + title + "%'";
		}

		sqlstr += " order by a.add_date desc";
		System.out.println("已反馈" + sqlstr);
		QueryResult qr = daoCtl.listPageSql(dao, Sqls.create(sqlstr), curPage,
				pageSize);
		List<Map<String, Object>> list = (List<Map<String, Object>>) qr
				.getList();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("repairs_type",
					n2s(repairsHT.get(n2s(list.get(i).get("phone_type"))+n2s(list.get(i).get("repairs_type")))));
		}
		return PageObj.getPageJSON(qr);
	}

	/**
	 * 跳转到反馈页面
	 */
	@At
	@Ok("->:/private/handle/tobl.html")
	public void tobl(@Param("id") String id, HttpSession session,
					 HttpServletRequest req,@Param("parentTab") String parentTab) {
		final Sys_user user = (Sys_user) session.getAttribute("userSession");
		req.setAttribute("ptHT", daoCtl.getHTable(dao, Sqls.create("select flbh,flmc from " +
				"t_dmjfl where flbh like '00020002%'")));
		req.setAttribute("sxHT", daoCtl.getHTable(dao, Sqls.create("select CONCAT(ssfl,f_vc_daimz1),f_vc_daimmc from t_daimjb where ssfl like '00020002%'")));
		final Mgt_info obj = daoCtl.detailByName(dao, Mgt_info.class, id);
		req.setAttribute("obj", obj);
		final Mgt_zhuanban zhuanban = daoCtl.detailByCnd(dao, Mgt_zhuanban.class, Cnd.wrap("info_id = '"+id+"' and state=0 and history=0"));
		req.setAttribute("zhuanban", zhuanban);
		List cbList =daoCtl.getMulRowValue(dao, Sqls.create("select reply_date,reply_note,reply_user from mgt_handle where "
				+ "info_id='"+id+"' and state=2 order by id"));
		req.setAttribute("cbList", cbList);

		Trans.exec(new Atom() {

			@Override
			public void run() {
				obj.setStatus(3);//已签收
				obj.setAdd_date(DateUtil.getCurDateTime());
				dao.update(obj);
				zhuanban.setGet_date(DateUtil.getCurDateTime());
				zhuanban.setZb_user(user.getLoginname());
				dao.update(zhuanban);
			}
		});
	}

	/**
	 * 执行反馈操作
	 *
	 * @return
	 */
	@At
	@Ok("raw")
	public boolean dofk(@Param("id") final String id,
						@Param("reply_note") final String note,@Param("status")final int status,
						HttpSession session) {
		final ThreadLocal<Boolean> tl = new ThreadLocal<Boolean>();
		final Sys_user user = (Sys_user) session.getAttribute("userSession");
		try {
			Trans.exec(new Atom() {

				public void run() {
					Mgt_info info =daoCtl.detailByName(dao, Mgt_info.class, id);
					info.setStatus(status);
					info.setAdd_date(DateUtil.getCurDateTime());
					dao.update(info);
					Mgt_zhuanban zhuanban = daoCtl.detailByCnd(dao, Mgt_zhuanban.class, Cnd.wrap("info_id = '"+id+"' and state=0 and history=0"));
					zhuanban.setState("1");
					dao.update(zhuanban);
					Mgt_handle handle =new Mgt_handle();
					handle.setInfo_id(id);
					handle.setReply_date(DateUtil.getCurDateTime());
					handle.setReply_note(note);
					handle.setReply_user(user.getLoginname());
					handle.setState(0);
					handle.setUnitid(user.getUnitid());
					handle.setZb_id(zhuanban.getId());
					dao.insert(handle);
					tl.set(true);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			tl.set(false);
		}
		return tl.get();
	}


	/**
	 * 跳转到回访页面
	 */
	@At
	@Ok("->:/private/handle/tohf.html")
	public void tohf(@Param("id") String id, HttpSession session,
					 HttpServletRequest req,@Param("parentTab") String parentTab) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		req.setAttribute("ptHT", daoCtl.getHTable(dao, Sqls.create("select flbh,flmc from " +
				"t_dmjfl where flbh like '00020002%'")));
		req.setAttribute("sxHT", daoCtl.getHTable(dao, Sqls.create("select CONCAT(ssfl,f_vc_daimz1),f_vc_daimmc from t_daimjb where ssfl like '00020002%'")));
		Mgt_info obj = daoCtl.detailByName(dao, Mgt_info.class, id);
		req.setAttribute("obj", obj);
		Mgt_zhuanban zhuanban = daoCtl.detailByCnd(dao, Mgt_zhuanban.class, Cnd.wrap("info_id = '"+id+"' and state=1 and history=0"));
		req.setAttribute("zhuanban", zhuanban);
		Mgt_handle handle =  daoCtl.detailByCnd(dao, Mgt_handle.class, Cnd.wrap("info_id='"+id+"' and state=0 and zb_id = '"+zhuanban.getId()+"' and history=0"));
		req.setAttribute("handle", handle);
		req.setAttribute("unitHT", daoCtl.getHTable(dao, Sqls.create("select id,name from sys_unit")));
		req.setAttribute("hisHandle", daoCtl.getMulRowValue(dao, Sqls.create("select * from mgt_handle where info_id='"+id+"' and state=0 and history=1")));
		req.setAttribute("curuser", user.getLoginname());
		req.setAttribute("unitList", daoCtl.getMulRowValue(dao, Sqls.create("select id,name from sys_unit where id like '0002____' order by id")));
		List cbList =daoCtl.getMulRowValue(dao, Sqls.create("select reply_date,reply_note,reply_user from mgt_handle where "
				+ "info_id='"+id+"' and state=2 order by id"));
		req.setAttribute("cbList", cbList);
		String tel = daoCtl.getStrRowValue(dao, Sqls.create("select telephone from sys_unit where id='"+obj.getZb_management()+"'"));
		req.setAttribute("tel", tel);
	}

	/**
	 * 执行最终回复操作
	 *
	 * @return
	 */
	@At
	@Ok("raw")
	public boolean dohf(@Param("id") final String id,
						@Param("::mgt_callback.") final Mgt_callback mgt_callback,
						HttpSession session) {
		final ThreadLocal<Boolean> tl = new ThreadLocal<Boolean>();
		final Sys_user user = (Sys_user) session.getAttribute("userSession");
		try {
			Trans.exec(new Atom() {

				public void run() {
					Mgt_info info =daoCtl.detailByName(dao, Mgt_info.class, id);
					info.setStatus(1);
					info.setAdd_date(DateUtil.getCurDateTime());
					dao.update(info);
					Mgt_handle handle =new Mgt_handle();
					handle.setInfo_id(id);
					handle.setReply_date(DateUtil.getCurDateTime());
					handle.setReply_note("此事已完结,可以正常关闭");
					handle.setReply_user(user.getLoginname());
					handle.setState(1);//办结
					handle.setUnitid(user.getUnitid());
					handle.setZb_id(0);
					dao.insert(handle);
					if(info.getIs_huifang()==1){
						mgt_callback.setInfo_id(id);
						mgt_callback.setHf_user(user.getLoginname());
						dao.insert(mgt_callback);
					}
					tl.set(true);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			tl.set(false);
		}
		return tl.get();
	}


	/**
	 * 执行发回重办操作
	 *
	 * 发回重办步骤。一.更新info表。二 ，修改转办历史状态，新增转办记录，三，修改handle表历史状态，新增转办记录。
	 * @return
	 */
	@At
	@Ok("raw")
	public boolean docb(@Param("id") final String id,
						@Param("cb_note") final String cb_note,@Param("need_date") final String need_date,
						@Param("zb_management") final String zb_management,@Param("tel") final String tel,
						@Param("message_note") final String message_note,HttpSession session) {
		final ThreadLocal<Boolean> tl = new ThreadLocal<Boolean>();
		final Sys_user user = (Sys_user) session.getAttribute("userSession");
		try {
			Trans.exec(new Atom() {

				public void run() {
					Mgt_info info =daoCtl.detailByName(dao, Mgt_info.class, id);
					info.setStatus(2);
					info.setAdd_date(DateUtil.getCurDateTime());
					dao.update(info);
					Mgt_zhuanban zhuanban = daoCtl.detailByCnd(dao, Mgt_zhuanban.class, Cnd.wrap("info_id = '"+id+"' and state=1 and history=0"));
					zhuanban.setHistory(1);
					dao.update(zhuanban);
					zhuanban.setHistory(0);
					zhuanban.setState("0");
					zhuanban.setZb_date(DateUtil.getCurDateTime());
					zhuanban.setNeed_date(need_date);
					zhuanban.setZb_management(zb_management);
					Mgt_handle handle = daoCtl.detailByCnd(dao, Mgt_handle.class, Cnd.wrap("info_id = '"+id+"' and state=0 and zb_id='"+zhuanban.getId()+"' and history=0"));
					dao.insert(zhuanban);
					handle.setHistory(1);
					dao.update(handle);
					handle.setReply_date(DateUtil.getCurDateTime());
					handle.setReply_note(cb_note);
					handle.setReply_user(user.getLoginname());
					handle.setState(2);//发回重办
					handle.setUnitid(user.getUnitid());
					dao.insert(handle);
					tl.set(true);
					SmsClient client = new SmsClient();
					Mgt_message message = new Mgt_message();
					String seedMsg = client.sendSms(tel.trim(),message_note);
					message.setPhone_number(tel);
					message.setMessage(zhuanban.getMessage_note());
					message.setMessage_info(seedMsg);
					message.setInfo_id(info.getId());
					message.setUser(info.getLoginname());
					message.setSeed_date(DateUtil.getCurDateTime());
					dao.insert(message);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			tl.set(false);
		}
		return tl.get();
	}
	/**
	 * null转变""
	 */
	private String n2s(Object obj) {
		return StringUtil.null2String(obj);
	}


	/**
	 * 判断字符串非空
	 *
	 * @param str
	 * @return
	 */
	private static boolean notnull(String str) {
		return str != null && !str.equals("") ? true : false;
	}
}