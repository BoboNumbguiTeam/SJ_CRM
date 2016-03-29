package com.hits.modules.management;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.hits.common.action.BaseAction;
import com.hits.common.filter.GlobalsFilter;
import com.hits.common.filter.UserLoginFilter;
import com.hits.common.util.DateUtil;
import com.hits.common.util.IdUtil;
import com.hits.common.util.PinyinUtil;
import com.hits.common.util.StringUtil;
import com.hits.modules.management.bean.*;

import com.hits.modules.msg.SmsClient;
import com.hits.modules.sjygl.bean.T_daimjb;
import com.hits.modules.sjygl.bean.T_dmjfl;
import com.hits.modules.sys.bean.Sys_unit;
import com.hits.modules.sys.bean.Sys_user;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.nutz.dao.*;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import java.io.IOException;
import java.util.*;


/**
 * @author Numbgui
 * @time 2015-05-23 14:21:13
 *
 */
@IocBean
@At("/private/management/info")
@Filters({ @By(type = GlobalsFilter.class), @By(type = UserLoginFilter.class) })
public class Mgt_infoAction extends BaseAction {
	@Inject
	protected Dao dao;
	@Inject
	protected Dao dao2;

	private Map<String,String> unitMap = null;

	private  List<Sys_unit> names = new ArrayList<Sys_unit>();
	private  List<T_dmjfl> dmjflList = new ArrayList<T_dmjfl>();
	private  List<T_daimjb> daimjbList = new ArrayList<T_daimjb>();


	@At("")
	@Ok("->:${obj == true ? '/private/management/Mgt_zbList.html' : '/private/management/Mgt_info.html'}")
	public boolean index(@Param("sys_menu") String sys_menu,@Param("start_date")String start_date,@Param("end_date")String end_date,HttpServletRequest req) {
		Gson gson=new Gson();
		Hashtable<String,String> typeMap = daoCtl.getHTable(dao,Sqls.create(" SELECT flbh,flmc FROM t_dmjfl WHERE flbh LIKE '00020002____' "));
		Hashtable<String, String> serverMap = daoCtl.getHTable(dao,Sqls.create(" SELECT CONCAT(ssfl,f_vc_daimz1),f_vc_daimmc FROM t_daimjb WHERE ssfl LIKE '00020002%' "));
		Hashtable<String,String> managementMap = daoCtl.getHTable(dao, Sqls.create(" SELECT id,NAME FROM sys_unit WHERE id LIKE '0002________' order by id "));
		List<T_dmjfl> typeList = daoCtl.list(dao, T_dmjfl.class, Cnd.where("flbh", "LIKE", "00020002____"));
		List<T_daimjb> serverList = new ArrayList<T_daimjb>();
		if(!typeList.isEmpty()) {
			serverList = daoCtl.list(dao, T_daimjb.class, Cnd.where("ssfl", "=", typeList.get(0).getFlbh()));
		}
		req.setAttribute("managementmap",gson.toJson(managementMap));
		req.setAttribute("typemap", gson.toJson(typeMap));
		req.setAttribute("servermap", gson.toJson(serverMap));
		req.setAttribute("sys_menu",sys_menu);
		req.setAttribute("typeList",typeList);
		req.setAttribute("serverList",serverList);

		start_date = StringUtil.isNull(StringUtil.null2String(start_date),DateUtil.getPreDayStr(DateUtil.getCurDateTime())+" 00:00:01");
		end_date = StringUtil.isNull(StringUtil.null2String(end_date),DateUtil.getCurDateTime());
		req.setAttribute("start_date",start_date);
		req.setAttribute("end_date",end_date);
		if("直办列表".equals(sys_menu)){
			return true;
		}else{
			return false;
		}
	}

	@At
	@Ok("->:/private/management/Mgt_infobdAdd.html")
	public void tobdadd(HttpSession session,HttpServletRequest req) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		req.setAttribute("fromtime", DateUtil.getCurDateTime());
		req.setAttribute("dmjflList", getListReq(req, ""));
		req.setAttribute("user", user);
	}

	@At
	@Ok("raw")
	public boolean bdadd(@Param("..") Mgt_info mgt_info,@Param("addressArr")String[] addressArr,@Param("zb_management")String zb_management,HttpSession session) {
		String management = daoCtl.getStrRowValue(dao,Sqls.create(" SELECT id FROM sys_unit WHERE NAME='"+zb_management+"'  "));
		String str = DateUtil.getDateStr8(new java.util.Date()) + mgt_info.getLoginname();
		String maxSN = daoCtl.getStrRowValue(dao,Sqls.create(" select id from mgt_info where id like '"+str+"%'  ORDER BY id DESC LIMIT 0,1 "));

		mgt_info.setFlowid("bd");
		mgt_info.setId(IdUtil.getInfoSN(str, maxSN));
		mgt_info.setAdd_date(DateUtil.getCurDateTime());
		mgt_info.setManagement(management);
		mgt_info.setZb_management(daoCtl.getStrRowValue(dao,Sqls.create(" SELECT id FROM sys_unit WHERE id='"+management.substring(0,8)+"'  ")));
		mgt_info.setState("0");//0表示草稿，1表示办结，2表示转办待受理，3表示转办受理中，4表示转办已回复
		mgt_info.setIs_banjie("0");//0表示未办结，1表示办结
		mgt_info.setAddress(addressArr[0] + "  @" + addressArr[1] + "  @" + addressArr[2] + "  ");
		return daoCtl.add(dao,mgt_info);
	}


	@At
	@Ok("->:/private/management/Mgt_infoAdd.html")
	public void toadd(@Param("flowid")String flowid,@Param("fromnum")String fromnum,@Param("appid")String appid,
					  HttpSession session,HttpServletRequest req) {
		Sys_user user = (Sys_user) session.getAttribute("userSession");
		req.setAttribute("flowid", flowid);
		req.setAttribute("fromnum", fromnum);
		req.setAttribute("appid", appid);
		req.setAttribute("fromtime", DateUtil.getCurDateTime());
		req.setAttribute("dmjflList", getListReq(req, ""));
		req.setAttribute("user", user);
		req.setAttribute("hisList", daoCtl.list(dao, Mgt_info.class, Cnd.where("phone", "=", fromnum)));
	}

	@At
	@Ok("raw")
	public boolean add(@Param("..") Mgt_info mgt_info,@Param("addressArr")String[] addressArr,@Param("zb_management")String zb_management,HttpSession session) {
		String management = daoCtl.getStrRowValue(dao,Sqls.create(" SELECT id FROM sys_unit WHERE NAME='"+zb_management+"'  "));
		String str = DateUtil.getDateStr8(new java.util.Date()) + mgt_info.getLoginname();
		String maxSN = daoCtl.getStrRowValue(dao,Sqls.create(" select id from mgt_info where id like '"+str+"%'  ORDER BY id DESC LIMIT 0,1 "));

		mgt_info.setId(IdUtil.getInfoSN(str, maxSN));
		mgt_info.setAdd_date(DateUtil.getCurDateTime());
		mgt_info.setManagement(management);
		mgt_info.setZb_management(daoCtl.getStrRowValue(dao, Sqls.create(" SELECT id FROM sys_unit WHERE id='" + management.substring(0, 8) + "'  ")));//小区所属物业
		mgt_info.setState("0");//0表示草稿，1表示办结，2表示转办待受理，3表示转办受理中，4表示转办已回复
		mgt_info.setIs_banjie("0");//0表示未办结，1表示办结
		mgt_info.setAddress(addressArr[0] + "  @" + addressArr[1] + "  @" + addressArr[2] + "  ");
		return daoCtl.add(dao,mgt_info);
	}


	@At
	@Ok("->:/private/management/Mgt_infoEdit.html")
	public void toEdit(@Param("id") String id,HttpServletRequest req) {
		Gson gson = new Gson();
		Mgt_info info = daoCtl.detailByName(dao, Mgt_info.class, id);
		String[] addrs = info.getAddress().split("@");
		req.setAttribute("obj", info);
		if(addrs.length!=0) {
			req.setAttribute("add_0", addrs[0]);
			req.setAttribute("add_1", addrs[1]);
			req.setAttribute("add_2", addrs[2]);
		}
		req.setAttribute("wy",daoCtl.detailByCnd(dao,Sys_unit.class,Cnd.where("id","=",info.getManagement().substring(0,8))));
		req.setAttribute("xqtel",daoCtl.getStrRowValue(dao, Sqls.create(" select telephone from sys_unit where id='" + info.getManagement() + "' ")));
		req.setAttribute("xqList",daoCtl.list(dao,Sys_unit.class,Cnd.where("id","LIKE","0002____").and("telephone","<>","")));
		req.setAttribute("ssxq",daoCtl.getStrRowValue(dao, Sqls.create(" select name from sys_unit where id='" + info.getManagement() + "' ")));
		req.setAttribute("management_tel", daoCtl.getStrRowValue(dao, Sqls.create(" select telephone from sys_unit where id='" + info.getManagement() + "' ")));
		req.setAttribute("fwsx", daoCtl.getStrRowValue(dao, Sqls.create(" SELECT f_vc_daimmc FROM t_daimjb WHERE ssfl = '" + info.getPhone_type() + "' AND f_vc_daimz1 = '" + info.getRepairs_type() + "' ")));
		req.setAttribute("realname", daoCtl.getStrRowValue(dao, Sqls.create(" SELECT realname FROM sys_user WHERE loginname ='" + info.getLoginname() + "' ")));
		req.setAttribute("dmjflList", getListReq(req, info.getPhone_type()));
		req.setAttribute("need_date", DateUtil.getEndDay(DateUtil.getCurDateTime(), 3) + DateUtil.getCurDateTime().substring(10, 19));
		req.setAttribute("xqmap", gson.toJson(daoCtl.getHTable(dao, Sqls.create(" SELECT id,telephone FROM sys_unit WHERE id LIKE '0002____' "))));
		req.setAttribute("zhuanban", daoCtl.detailByCnd(dao, Mgt_zhuanban.class, Cnd.where("info_id", "=", info.getId())));
	}

	@At
	@Ok("raw")
	public boolean edit(@Param("::mgt_info.") final Mgt_info mgt_info,@Param("::mgt_zhuanban.") final Mgt_zhuanban mgt_zhuanban,
						@Param("zb_management")String management,@Param("addressArr")String[] addressArr) {
		final boolean zb_flag = "1".equals(mgt_info.getState()) ? false : true ;  //1表示直办，2表示转物业
		if(addressArr.length!=0) {
			mgt_info.setAddress((addressArr[0]==null ? "" : addressArr[0]) + "@" + (addressArr[1]==null ? "" : addressArr[1]) +
					"@" + (addressArr[2]==null ? "" : addressArr[2]));
		}
		management = daoCtl.getStrRowValue(dao,Sqls.create(" SELECT id FROM sys_unit WHERE NAME = '"+management+"' "));
		if(zb_flag) {
			mgt_info.setStatus(2);//2表示转办待受理
			mgt_info.setManagement(management);
			mgt_zhuanban.setInfo_id(mgt_info.getId());
			mgt_zhuanban.setZb_date(DateUtil.getCurDateTime());
			mgt_zhuanban.setManagement(mgt_info.getManagement());
			mgt_zhuanban.setState("0");
		}else{
			mgt_info.setStatus(1);//1表示值办办结
			mgt_info.setManagement(management);
		}
		mgt_info.setAdd_date(DateUtil.getCurDateTime());
		final ThreadLocal<Boolean> updateThread = new ThreadLocal<Boolean>();
		final ThreadLocal<Boolean> sendThread = new ThreadLocal<Boolean>();
		updateThread.set(false);
		sendThread.set(false);
		Trans.exec(new Atom() {
			public void run() {
				dao.update(mgt_info);
				if (zb_flag) {
					Mgt_zhuanban zhuanban = dao.insert(mgt_zhuanban);
					if (zhuanban != null && zhuanban.getId() > 0) {
						updateThread.set(true);
						sendThread.set(true);
					}
				} else {
					updateThread.set(true);
				}
			}
		});
		if(sendThread.get()){
			//发送短信代码
			SmsClient client = new SmsClient();
			Mgt_message message = new Mgt_message();
			String seedMsg = client.sendSms(mgt_zhuanban.getManagement_tel().trim(),mgt_zhuanban.getMessage_note());
			message.setPhone_number(mgt_zhuanban.getManagement_tel());
			message.setMessage(mgt_zhuanban.getMessage_note());
			message.setMessage_info(seedMsg);
			message.setInfo_id(mgt_info.getId());
			message.setUser(mgt_info.getLoginname());
			message.setSeed_date(DateUtil.getCurDateTime());
			daoCtl.add(dao,message);
		}
		return updateThread.get();
	}

	@At("/detail")
	@Ok("->:/private/management/Mgt_infoDetail.html")
	public void view(@Param("id") String id,HttpServletRequest req,@Param("mod") String mod,
					 HttpSession session) {
		final Mgt_info info = daoCtl.detailByName(dao, Mgt_info.class, id);
		if(mod!=null&&mod.equals("dbl")){
			final Mgt_zhuanban zb = daoCtl.detailByCnd(dao, Mgt_zhuanban.class, Cnd.wrap("info_id = '"+id+"' and state=0 and history=0"));
			final Sys_user user = (Sys_user) session.getAttribute("userSession");
			Trans.exec(new Atom() {
				@Override
				public void run() {
					info.setStatus(3);//已签收
					info.setAdd_date(DateUtil.getCurDateTime());
					dao.update(info);
					zb.setGet_date(DateUtil.getCurDateTime());
					zb.setZb_user(user.getLoginname());
					dao.update(zb);
				}
			});
		}
		String[] addrs = info.getAddress().split("@");
		req.setAttribute("obj", info);
		if(addrs.length!=0) {
			req.setAttribute("add_0", StringUtil.isNull(StringUtil.null2String(addrs[0]), ""));
			req.setAttribute("add_1", StringUtil.isNull(StringUtil.null2String(addrs[1]), ""));
			req.setAttribute("add_2", StringUtil.isNull(StringUtil.null2String(addrs[2]), ""));
		}
		req.setAttribute("ssxq",daoCtl.getStrRowValue(dao, Sqls.create(" select name from sys_unit where id='" + info.getManagement() + "' ")));
		req.setAttribute("ldlx", daoCtl.getStrRowValue(dao, Sqls.create(" SELECT flmc FROM t_dmjfl WHERE flbh = '" + info.getPhone_type() + "' ")));
		req.setAttribute("fwsx", daoCtl.getStrRowValue(dao, Sqls.create(" SELECT f_vc_daimmc FROM t_daimjb WHERE ssfl = '" + info.getPhone_type() + "' AND f_vc_daimz1 = '" + info.getRepairs_type() + "' ")));
		req.setAttribute("realname", daoCtl.getStrRowValue(dao, Sqls.create(" SELECT realname FROM sys_user WHERE loginname ='" + info.getLoginname() + "' ")));
		if(info.getState().equals("2")){
			req.setAttribute("zhuanbanObj",daoCtl.detailByCnd(dao,Mgt_zhuanban.class,Cnd.where("info_id","=",info.getId()).and("history","=","0")));
			req.setAttribute("linkTel",daoCtl.getStrRowValue(dao,Sqls.create(" select telephone from sys_unit where id='" + info.getManagement() + "' ")));
		}
		if(info.getStatus()==4){
			req.setAttribute("zhuanbanObj", daoCtl.detailByCnd(dao, Mgt_zhuanban.class, Cnd.where("info_id", "=", info.getId()).and("history", "=", "0")));
			req.setAttribute("linkTel", daoCtl.getStrRowValue(dao, Sqls.create(" select telephone from sys_unit where id='" + info.getManagement() + "' ")));
			req.setAttribute("replyObj", daoCtl.detailByCnd(dao, Mgt_handle.class, Cnd.where("info_id", "=", info.getId()).and("history", "=", "0").and("state", "=", "0")));
		}else if("2".equals(info.getState()) && info.getStatus()==1){
			req.setAttribute("zhuanbanObj",daoCtl.detailByCnd(dao,Mgt_zhuanban.class,Cnd.where("info_id","=",info.getId()).and("history","=","0")));
			req.setAttribute("linkTel", daoCtl.getStrRowValue(dao, Sqls.create(" select telephone from sys_unit where id='" + info.getManagement() + "' ")));
			req.setAttribute("replyObj", daoCtl.detailByCnd(dao, Mgt_handle.class, Cnd.where("info_id", "=", info.getId()).and("history", "=", "0").and("state","=","0")));
			req.setAttribute("huifangObj",daoCtl.detailByCnd(dao, Mgt_callback.class, Cnd.where("info_id", "=", info.getId())));
		}

		//如果info.getZb_management()的值为null，则匹配Zb_management值并写入info表中
		if ("".equals(info.getZb_management()) || info.getZb_management()==null){
			System.out.println(" info.getManagement().substring(0,8) : "+info.getManagement().substring(0,8));
			info.setZb_management(daoCtl.getStrRowValue(dao, Sqls.create(" select id from sys_unit where id = '" + info.getManagement().substring(0, 8) + "' ")));
			daoCtl.update(dao, Mgt_info.class, Chain.make("zb_management",info.getZb_management()),Cnd.where("id","=",info.getId()));
		}
		String sswy = daoCtl.getStrRowValue(dao,Sqls.create(" select name from sys_unit where id = '"+info.getZb_management()+"' "));
		req.setAttribute("sswy", sswy);

		//是否存在转办历史
		List<Mgt_zhuanban> zhuanbanList = daoCtl.list(dao,Mgt_zhuanban.class,Cnd.where("info_id","=",info.getId()).and("history","=",1).asc("id"));
		Map<String,Mgt_handle>  replyMap = new HashMap<String, Mgt_handle>();
		if(!zhuanbanList.isEmpty()){
			for(Mgt_zhuanban zhuanban : zhuanbanList){
				replyMap.put(String.valueOf(zhuanban.getId()),daoCtl.detailByCnd(dao, Mgt_handle.class,Cnd.where("zb_id","=",zhuanban.getId())));
			}
			req.setAttribute("history","true");
			req.setAttribute("zhuanbanList",zhuanbanList);
			req.setAttribute("replyMap",replyMap);
		}
	}

	@At
	@Ok("->:/private/management/Mgt_infoUpdate.html")
	public Mgt_info toupdate(@Param("id") String id, HttpServletRequest req) {
		return daoCtl.detailByName(dao, Mgt_info.class, id);//html:obj
	}

	@At
	public boolean update(@Param("..") Mgt_info mgt_info) {
		return daoCtl.update(dao, mgt_info);
	}

	@At
	public boolean delete(@Param("id") String id) {
		return daoCtl.deleteByName(dao, Mgt_info.class, id);
	}

	@At
	public boolean deleteIds(@Param("ids") String[] ids) {
		String[] id = StringUtil.null2String(ids).split(",");
		return daoCtl.deleteByIds(dao, Mgt_info.class, id);
	}

	@At
	@Ok("raw")
	public JSONObject list(@Param("keyId")String keyId,@Param("searchType")String searchType,@Param("searchServer")String searchServer,
						   @Param("state")String state,@Param("start_date")String start_date,@Param("end_date")String end_date,
						   @Param("linkNumb")String linkNumb,@Param("page") int curPage,@Param("rows") int pageSize,HttpSession session){
		Sys_user user = (Sys_user)session.getAttribute("userSession");
		start_date = "".equals(StringUtil.isNull(start_date,"")) ? DateUtil.getToday() + " 00:00:00" : start_date ;
		end_date = "".equals(StringUtil.isNull(end_date,"")) ? DateUtil.getCurDateTime() : end_date ;

		Criteria cri = Cnd.cri();

		if(user.getUnitid().equals("0003")){
			cri.where().and("loginname","=",user.getLoginname());
		}
		if(state!=null&&state.equals("zb")){
			cri.where().and("status", "=", 1);
		}else{
			cri.where().and("status", "=", 0);
		}
		if(!"".equals(keyId)){
			cri.where().andLike("id",keyId+"%");
		}
		if(!"-1".equals(searchType)){
			if(!"-1".equals(searchServer)){
				cri.where().and("phone_type","=",searchType).and("repairs_type","=",searchServer);
			}else{
				cri.where().and("phone_type","=",searchType);
			}
		}
		if("zb".equals(state)){
			cri.where().and("state","=","1");
		}else if("zwy".equals(state)){
			cri.where().and("state","=","2");
		}

		if(!"".equals(StringUtil.isNull(linkNumb,""))){
			cri.where().and("phone","=",linkNumb);
		}
		if(state!=null&&state.equals("zb")) {
			cri.where().and("add_date", ">", start_date).and("add_date", "<", end_date);
		}
		cri.getOrderBy().desc("id");
		return daoCtl.listPageJson(dao, Mgt_info.class, curPage, pageSize, cri);
	}

	@At
	@Ok("raw")
	public String getOperation(@Param("ssfl")String ssfl,@Param("state")String state){
		List<T_daimjb> daimjbList = daoCtl.list(dao, T_daimjb.class, Cnd.where("ssfl", "=", ssfl));
		if(daimjbList.isEmpty() && "1".equals(StringUtil.isNull(StringUtil.null2String(state), ""))){
			T_daimjb daimjb = new T_daimjb();
			daimjb.setF_vc_daimmc("无");
			daimjb.setF_vc_daimz1("-1");
			daimjbList.add(daimjb);
		}
		return Json.toJson(daimjbList);
	}

	@At
	@Ok("raw")
	public String checkSourceName(@Param("name") String name,HttpServletRequest req,HttpServletResponse response){
		Sys_unit unit = daoCtl.detailByCnd(dao, Sys_unit.class, Cnd.where("id", "LIKE", "0002____").and("name", "=", name));
		if(unit == null){
			return "true";
		}else{
			return "false";
		}
	}

	@At
	@Ok("raw")
	public String addSource(@Param("name") String name){
		Sys_unit unit = new Sys_unit();
		String id = daoCtl.getSubMenuId(dao,"sys_unit", "id", "0002");
		unit.setId(id);
		unit.setName(name);
		unit.setUnitcode(name+"物业");
		boolean flag = daoCtl.add(dao, unit);
		if(flag){
			names.add(unit);
			return "true";
		}else{
			return "false";
		}
	}


	@At
	@Ok("raw")
	public JSONArray doGet(@Param("names") String prefix,HttpServletRequest req,HttpServletResponse response)throws ServletException, IOException {
		List<Sys_unit> jsonNames = new ArrayList<Sys_unit>();
		if(jsonNames.size()==0){
			jsonNames = daoCtl.list(dao,Sys_unit.class,Cnd.where("id","LIKE","0002________").and("name", "LIKE", "%" + prefix + "%"));
		}
		JSONArray jsonArr = JSONArray.fromObject(jsonNames);
		return jsonArr;
	}


	private  List<T_dmjfl> getListReq(HttpServletRequest req,String phone_type){
		dmjflList = dmjflList.isEmpty() ? daoCtl.list(dao, T_dmjfl.class, Cnd.where("flbh", "LIKE", "00020002____")) : dmjflList;
		if(!dmjflList.isEmpty()) {
			if(!"".equals(phone_type)) {
				daimjbList = daoCtl.list(dao, T_daimjb.class, Cnd.where("ssfl", "=", phone_type));
			}else {
				daimjbList = daimjbList.isEmpty() ? daoCtl.list(dao, T_daimjb.class, Cnd.where("ssfl", "=", dmjflList.get(0).getFlbh())) : daimjbList;
			}
			if(daimjbList.isEmpty()){
				T_daimjb daimjb = new T_daimjb();
				daimjb.setF_vc_daimmc("无");
				daimjb.setF_vc_daimz1("-1");
				daimjbList.add(daimjb);
				req.setAttribute("daimjbList",daimjbList);
			}else{
				req.setAttribute("daimjbList", daimjbList);
			}
		}else{
			T_dmjfl dmjfl = new T_dmjfl();
			dmjfl.setFlmc("无");
			dmjfl.setFlbh("-1");
			dmjflList.add(dmjfl);
		}
		return dmjflList;
	}

	@At
	@Ok("raw")
	public String getNeedDate(@Param("type")String type){
		String timeStr = DateUtil.getEndDay(DateUtil.getCurDateTime(), 3) + DateUtil.getCurDateTime().substring(10, 19);
		if("1".equals(type)){
			timeStr = DateUtil.getAddDateTime(DateUtil.getCurDateTime(), 1, 0);
		}
		return timeStr;
	}

	/**
	 *
	 * @param
	 */
	@At
	@Ok("->:/private/management/Mgt_zwyList.html")
	public void zwyPage(@Param("start_date")String start_date,@Param("end_date")String end_date,
						@Param("sys_menu") String sys_menu,HttpServletRequest req){
		Gson gson=new Gson();
		Hashtable<String,String> typeMap = daoCtl.getHTable(dao,Sqls.create(" SELECT flbh,flmc FROM t_dmjfl WHERE flbh LIKE '00020002____' "));
		Hashtable<String, String> serverMap = daoCtl.getHTable(dao,Sqls.create(" SELECT CONCAT(ssfl,f_vc_daimz1),f_vc_daimmc FROM t_daimjb WHERE ssfl LIKE '00020002%' "));
		Hashtable<String,String> managementMap = daoCtl.getHTable(dao, Sqls.create(" SELECT id,NAME FROM sys_unit WHERE id LIKE '0002________' "));
		List<T_dmjfl> typeList = daoCtl.list(dao, T_dmjfl.class, Cnd.where("flbh", "LIKE", "00020002____"));
		List<T_daimjb> serverList = new ArrayList<T_daimjb>();
		if(!typeList.isEmpty()) {
			serverList = daoCtl.list(dao, T_daimjb.class, Cnd.where("ssfl", "=", typeList.get(0).getFlbh()));
		}
		Hashtable<String,String> lightMap = lightMap = daoCtl.getHTable(dao,Sqls.create(" SELECT info_id,(UNIX_TIMESTAMP(CAST(need_date AS DATETIME)) - UNIX_TIMESTAMP(NOW()))/60 AS light FROM mgt_zhuanban  "));
		if("clz".equals(PinyinUtil.cn2py(sys_menu.trim()))){
			req.setAttribute("is_clz", "true");
		}
		start_date = StringUtil.isNull(StringUtil.null2String(start_date),DateUtil.getPreDayStr(DateUtil.getCurDateTime())+" 00:00:01");
		end_date = StringUtil.isNull(StringUtil.null2String(end_date),DateUtil.getCurDateTime());
		Iterator it = lightMap.keySet().iterator();
		while(it.hasNext()){
			String key = (String) it.next();
			String value = lightMap.get(key);
			if(value.indexOf("-")!=-1){
				lightMap.put(key, "-1");
			}

		}
		req.setAttribute("start_date",start_date);
		req.setAttribute("end_date",end_date);
		req.setAttribute("lightMap",gson.toJson(lightMap));
		req.setAttribute("managementmap",gson.toJson(managementMap));
		req.setAttribute("typemap", gson.toJson(typeMap));
		req.setAttribute("servermap", gson.toJson(serverMap));
		req.setAttribute("sys_menu",sys_menu);
		req.setAttribute("typeList",typeList);
		req.setAttribute("serverList",serverList);
		req.setAttribute("zb_type", PinyinUtil.cn2py(sys_menu.trim()));
	}

	@At
	@Ok("raw")
	public JSONObject zwylist(@Param("keyId")String keyId,@Param("searchType")String searchType,@Param("searchServer")String searchServer,
							  @Param("start_date")String start_date,@Param("end_date")String end_date,@Param("zb_type")String zb_type,
							  @Param("page") int curPage, @Param("rows") int pageSize,HttpSession session,
							  @Param("fromnum")String fromnum){
		Sys_user user = (Sys_user)session.getAttribute("userSession");

		Criteria cri = Cnd.cri();
		if(user.getUnitid().equals("0003")){
			cri.where().and("loginname","=",user.getLoginname()).and("state", "=", "2");
		}else{
			cri.where().and("state", "=", "2");
		}
		if("yjj".equals(zb_type)){
			cri.where().and("status","=","4");
		}else if("yhf".equals(zb_type)){
			cri.where().and("status","=","1");
		}else{
			cri.where().andIn("status",new String[]{"2","3"});
		}
		//搜索条件
		if(!"".equals(keyId)){
			cri.where().andLike("id",keyId+"%");
		}
		if(!"-1".equals(searchType)){
			if(!"-1".equals(searchServer)){
				cri.where().and("phone_type","=",searchType).and("repairs_type","=",searchServer);
			}else{
				cri.where().and("phone_type", "=", searchType);
			}
		}
		if(!"".equals(fromnum)){
			cri.where().and("phone","=",fromnum);
		}
		cri.where().and("add_date",">",start_date).and("add_date","<",end_date);
		cri.getOrderBy().asc("status").desc("id");
		return daoCtl.listPageJson(dao, Mgt_info.class, curPage, pageSize, cri);
	}

	@At
	@Ok("->:/private/management/play.html")
	public void bofangluyin(HttpSession session, HttpServletRequest req,@Param("id") String id) {
		try {
			Mgt_info info=daoCtl.detailByName(dao, Mgt_info.class, "id", id);
			String callSql="SELECT FileName FROM REC_File_Info WHERE appid ='"+info.getFlowid()+"'";
			List<String> fileList=daoCtl.getStrRowValues(dao2, Sqls.create(callSql));

			ArrayList lydz=new ArrayList();
			String recordURI = daoCtl.getStrRowValue(dao,Sqls.create(" SELECT flbm FROM t_dmjfl WHERE flbh = '00020006'  "));
			for(int i=0;i<fileList.size();i++){
				String filename=(String) fileList.get(i);
				String year=filename.substring(0,4);
				String month=filename.substring(4,6);
				String day = filename.substring(6, 8);
				String hour = filename.substring(8, 10);
				//String filepath="http://192.168.1.241/root/ss3000-c-ms/recserver/Records/"+year+"/"+month+"/"+day+"/"+hour+"/"+filename;
				String filepath =recordURI+year+"/"+month+"/"+day+"/"+hour+"/"+filename;
				lydz.add(filepath);
			}
			req.setAttribute("lydz", lydz);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@At
	@Ok("raw")
	public void autoSendMSG(){
		Hashtable<String,String> lightMap = lightMap = daoCtl.getHTable(dao,Sqls.create(" SELECT info_id,(UNIX_TIMESTAMP(CAST(need_date AS DATETIME)) - UNIX_TIMESTAMP(NOW()))/60 AS light FROM mgt_zhuanban where send_cbmsg = '0'  "));
		if(lightMap.size() > 0 && unitMap == null){
			unitMap = daoCtl.getHTable(dao,Sqls.create(" select id,name from sys_unit where id like '0002_%'  "));
		}
		Set<String> set = lightMap.keySet();
		SmsClient client = new SmsClient();
		for(String key : set ){
			if(Double.parseDouble(lightMap.get(key)) < 120) {
				Mgt_zhuanban info = daoCtl.detailByCnd(dao, Mgt_zhuanban.class, Cnd.where("info_id", "=", key));
				String wy = "";
				String xq = "";
				if(info.getManagement().length() > 8 ){
					wy = unitMap.get(info.getManagement().substring(0, info.getManagement().length() - 4));
					xq = unitMap.get(info.getManagement());
				}
				if (info.getSend_cbmsg() == 0) {
					//发送短信代码
					Mgt_message message = new Mgt_message();
					String msg = "单号为[" + key + "]的转办件即将过期,所属小区["+xq+"],所属物业["+wy+"],请尽快处理!!!";
					System.out.println("msg : "+msg);
					String seedMsg = client.sendSms(info.getManagement_tel(), msg);
					message.setPhone_number(info.getManagement_tel());
					message.setMessage(msg + " --- [系统催办短信]");
					message.setMessage_info(seedMsg);
					message.setInfo_id(key);
					message.setUser("superadmin");
					message.setSeed_date(DateUtil.getCurDateTime());
					daoCtl.add(dao, message);
					daoCtl.update(dao, Mgt_zhuanban.class, Chain.make("send_cbmsg", 1), Cnd.where("info_id", "=", key));
				}
			}
		}
	}

	@At
	@Ok("raw")
	public String checkName(@Param("zb_management")String name){
		Sys_unit unit = daoCtl.detailByCnd(dao, Sys_unit.class, Cnd.where("name", "=", name.trim()));
		return unit == null ? "false" : "true";
	}

	@At
	@Ok("raw")
	public String getWY(@Param("zb_management")String zb_management){
		Sys_unit unit = daoCtl.detailByCnd(dao,Sys_unit.class,Cnd.where("id","=",daoCtl.detailByCnd(dao,Sys_unit.class,Cnd.where("name","=",zb_management)).getId().substring(0,8)));
		String returnStr = unit == null ? "NULL" : unit.getId()+"@@"+unit.getName()+"@@"+unit.getTelephone();
		return returnStr;
	}


	@At
	@Ok("raw")
	public String back(@Param("id")String id){
		boolean flag = daoCtl.update(dao, Mgt_info.class, Chain.make("status", 0),Cnd.where("id","=",id));
		System.out.println("flag : " + flag);
		return flag+"";
	}


	/**
	 * 综合查询
	 * @param start_date
	 * @param end_date
	 * @param req
	 */
	@At
	@Ok("->:/private/management/Mgt_queryList.html")
	public void queryPage(@Param("start_date")String start_date,@Param("end_date")String end_date,HttpServletRequest req){
		Gson gson=new Gson();
		Hashtable<String,String> typeMap = daoCtl.getHTable(dao,Sqls.create(" SELECT flbh,flmc FROM t_dmjfl WHERE flbh LIKE '00020002____' "));
		Hashtable<String, String> serverMap = daoCtl.getHTable(dao,Sqls.create(" SELECT CONCAT(ssfl,f_vc_daimz1),f_vc_daimmc FROM t_daimjb WHERE ssfl LIKE '00020002%' "));
		Hashtable<String,String> managementMap = daoCtl.getHTable(dao, Sqls.create(" SELECT id,NAME FROM sys_unit WHERE id LIKE '0002________' order by id "));
		List<T_dmjfl> typeList = daoCtl.list(dao, T_dmjfl.class, Cnd.where("flbh", "LIKE", "00020002____"));
		List<T_daimjb> serverList = new ArrayList<T_daimjb>();
		if(!typeList.isEmpty()) {
			serverList = daoCtl.list(dao, T_daimjb.class, Cnd.where("ssfl", "=", typeList.get(0).getFlbh()));
		}
		req.setAttribute("managementmap",gson.toJson(managementMap));
		req.setAttribute("typemap", gson.toJson(typeMap));
		req.setAttribute("servermap", gson.toJson(serverMap));
		req.setAttribute("typeList",typeList);
		req.setAttribute("serverList",serverList);

		start_date = StringUtil.isNull(StringUtil.null2String(start_date),DateUtil.getPreDayStr(DateUtil.getCurDateTime())+" 00:00:01");
		end_date = StringUtil.isNull(StringUtil.null2String(end_date),DateUtil.getCurDateTime());
		req.setAttribute("start_date",start_date);
		req.setAttribute("end_date",end_date);
	}

	/**
	 * 综合查询查询方法
	 * @param keyId  关键字
	 * @param searchType 来电类型
	 * @param searchServer 服务事项
	 * @param start_date 开始时间
	 * @param end_date 结束时间
	 * @param status 处理方式
	 * @param curPage
	 * @param pageSize
	 * @param session
	 * @return
	 */
	@At
	@Ok("raw")
	public JSONObject queryList(@Param("keyId")String keyId,@Param("searchType")String searchType,@Param("searchServer")String searchServer,
								@Param("start_date")String start_date,@Param("end_date")String end_date,@Param("status")String status,
								@Param("page") int curPage,@Param("rows") int pageSize,HttpSession session){
		Sys_user user = (Sys_user)session.getAttribute("userSession");
		start_date = "".equals(StringUtil.isNull(start_date,"")) ? DateUtil.getToday() + " 00:00:00" : start_date ;
		end_date = "".equals(StringUtil.isNull(end_date,"")) ? DateUtil.getCurDateTime() : end_date ;
		Condition cnd = null;

		SqlExpressionGroup sqlGroup = null;
		if(!"-1".equals(searchType)){
			if(!"-1".equals(searchServer)){
				sqlGroup =  Cnd.exps("phone_type", "=", searchType).and("repairs_type","=",searchServer);
			}else{
				sqlGroup =  Cnd.exps("phone_type", "=", searchType);
			}
		}

		if(!"-1".equals(status)){
			if(sqlGroup == null){
				sqlGroup = Cnd.exps("status","=",status);
			}else{
				sqlGroup.and("status","=",status);
			}
		}

		if(sqlGroup == null){
			sqlGroup = Cnd.exps("add_date", ">", start_date).and("add_date", "<", end_date);
		}else{
			sqlGroup.and("add_date", ">", start_date).and("add_date", "<", end_date);
		}
		if(!"".equals(keyId)){
			// 关键字查询
			cnd = Cnd.where(Cnd.exps("id","LIKE","%"+keyId+"%").orLike("phone",keyId).orLike("fromname", keyId)
					.orLike("title", keyId).orLike("note", keyId)).and(sqlGroup).asc("id");
		}else{
			cnd = Cnd.where(sqlGroup).asc("id");
		}

		return daoCtl.listPageJson(dao, Mgt_info.class, curPage, pageSize, cnd);
	}
}