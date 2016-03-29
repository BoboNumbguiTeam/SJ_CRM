package com.hits.modules.reportforms;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.hits.common.action.BaseAction;
import com.hits.common.filter.GlobalsFilter;
import com.hits.common.filter.UserLoginFilter;
import com.hits.common.util.StringUtil;
import com.hits.modules.com.comUtil;
import com.hits.modules.sjygl.bean.T_dmjfl;
import com.hits.modules.sys.bean.Sys_unit;
import com.hits.util.DateUtil;
import com.hits.util.SQLUtils;

/**
 * @author wanfly
 * @time 2014-03-03 13:16:15
 * 
 */
@IocBean
@At("/private/baobiao")
@Filters({ @By(type = GlobalsFilter.class), @By(type = UserLoginFilter.class) })
public class BaobiaoAction extends BaseAction {
	@Inject
	protected Dao dao;
	/**
	 * 受理分类报表
	 * 
	 * @param req
	 */
	@At("/slfltj")
	@Ok("->:/private/reportforms/sltj.html")
	public void slbaobiao(HttpServletRequest req,
			@Param("startdate") String startDate,
			@Param("enddate") String endDate) {
		String curTime = DateUtil.getDateStr(new Date());
		if (startDate == null || "".equals(startDate)) {
			startDate = curTime;
		}
		if (endDate == null || "".equals(endDate)) {
			endDate = curTime;
		}
		req.setAttribute("startdate", startDate);
		req.setAttribute("enddate", endDate);
		startDate += " 00:00:00";
		endDate += " 23:59:59";


		// 工号/loginame||dbloginname
		String sql = "select  loginname from sys_user where unitid ='0003' or loginname='1007'";
		List<String> userList = daoCtl.getStrRowValues(dao,
				Sqls.create(sql));
		// 信件类型
		List<Map> typeList = daoCtl.list(dao, Sqls.create("SELECT CONCAT(ssfl,f_vc_daimz1) as code,f_vc_daimmc FROM t_daimjb WHERE ssfl LIKE '00020002____'"));
		// 按照信件类型统计
		String type_sql = "select CONCAT(CONCAT(phone_type,repairs_type),loginname)," +
				"count(id) from mgt_info" + " where add_date>='"
				+ startDate + "' and add_date<='" + endDate + "'"
				+ " group by loginname,CONCAT(phone_type,repairs_type)";
		// 合计列--统计每一行总和
		String total_sql = "select loginname,count(id) from mgt_info"
				+ " where add_date>='" + startDate + "' and add_date<='"
				+ endDate + "' group by loginname";

		String typetotal_sql = "select CONCAT(phone_type,repairs_type),count(id) from mgt_info"
			+ " where add_date>='" + startDate + "' and add_date<='"
			+ endDate + "' group by CONCAT(phone_type,repairs_type)";
		
		// 每一行的合计
		Hashtable<String, String> typeHT = daoCtl.getHTable(dao,
				Sqls.create(type_sql));
		// 每一种类型的数量
		Hashtable<String, String> totalHT = daoCtl.getHTable(dao,
				Sqls.create(total_sql));
		// 每一种类型的数量
		Hashtable<String, String> typetotalHT = daoCtl.getHTable(dao,
				Sqls.create(typetotal_sql));
		
        Iterator totalTableIt = totalHT.keySet().iterator();
        int totalHj = 0;
        if (totalTableIt != null) {
            while (totalTableIt.hasNext()) {
                totalHj += StringUtil.StringToInt(StringUtil.null2String(totalHT.get(totalTableIt.next())));
            }
        }
		req.setAttribute("typeHT", typeHT);
		req.setAttribute("typeList", typeList);
		req.setAttribute("userList", userList);
		req.setAttribute("totalHj", totalHj);
		req.setAttribute("totalHT", totalHT);
		req.setAttribute("typetotalHT", typetotalHT);
		
	}

	
	/**
	 * 受理报表
	 * 
	 * @param req
	 */
	@At("/sltj")
	@Ok("->:/private/reportforms/sl_baobiao.html")
	public void sltj(HttpServletRequest req,
			@Param("startdate") String startDate,
			@Param("enddate") String endDate) {
		String curTime = DateUtil.getDateStr(new Date());
		if (startDate == null || "".equals(startDate)) {
			startDate = curTime;
		}
		if (endDate == null || "".equals(endDate)) {
			endDate = curTime;
		}
		req.setAttribute("startdate", startDate);
		req.setAttribute("enddate", endDate);
		startDate += " 00:00:00";
		endDate += " 23:59:59";


		// 工号/loginame||dbloginname
		String sql = "select  loginname from sys_user where unitid ='0003' or loginname='1007' ";
		List<String> userList = daoCtl.getStrRowValues(dao,
				Sqls.create(sql));
		// 受理件数
		Map<String,String> slMap = daoCtl.getHTable(dao, Sqls.create("SELECT " +
				"loginname,count(id) FROM mgt_info where add_date>='"+startDate+"' and add_date<='"+endDate+"' group by loginname"));
		//直办件数
		Map<String, String> zbMap = daoCtl.getHTable(dao,
				Sqls.create("select loginname,count(id) from mgt_info where  add_date>='"+startDate+"' and add_date<='"+endDate+"' and state=1 group by loginname"));
		
		req.setAttribute("slMap", slMap);
		req.setAttribute("zbMap", zbMap);
		req.setAttribute("userList", userList);
	}

	
	
	/**
	 * 跳转到办理统计页面
	 * @param session
	 * @param req
	 */
	@At("/bltj")
	@Ok("->:/private/reportforms/bltj_baobiao.html")
	public void bltj(HttpSession session, HttpServletRequest req,
			@Param("startdate") String startdate,@Param("enddate") String enddate) {
		if(startdate==null || startdate.trim().equals("")){
			req.setAttribute("startdate", DateUtil.getCurDateTime().substring(0,4)+"-01-01");
			startdate=DateUtil.getCurDateTime().substring(0,4)+"-01-01";
		}
		req.setAttribute("startdate",startdate);
		if(enddate==null || enddate.trim().equals("")){
			req.setAttribute("enddate", DateUtil.getCurDateTime().substring(0,10));
			enddate=DateUtil.getCurDateTime().substring(0,10);
		}
		req.setAttribute("enddate",enddate);
		List<Sys_unit> unitlist=daoCtl.list(dao,Sys_unit.class, Sqls.create("select * from sys_unit where id like '0002____'order by id,location"));
		req.setAttribute("unitlist", unitlist);

        /*
         * 在选定的时间内，该单位一共受到多少转办件
         */
        String sqlZB = "select zb_management ,count(id) from mgt_zhuanban "
                /**
                 * 信件id在 选定的时间内转办的的信件id中
                 */
                + " where  zb_date>='"+startdate + "' and zb_date<='" + DateUtil.getNextDayStr(enddate) + "'  "
                /**
                 * 按单位分组
                 */
                + " group by zb_management ";
        
        /*
         * 在选定的时间内，该单位正在办理多少转办件
         */
        String sqlBLZ = "select zb_management ,count(id) from mgt_zhuanban "
                /**
                 * 信件id在 选定的时间内转办的的信件id中
                 */
                + " where state=0 and zb_date>='"
                + startdate + "' and zb_date<='" + DateUtil.getNextDayStr(enddate) + "'  "
                /**
                 * 按单位分组
                 */
                + " group by zb_management ";
        
        /*
         * 在选定的时间内，该单位一共反馈办结多少件
         */
        String sqlFK = "select zb_management ,count(id) from mgt_zhuanban"
                /**
                 * 选定时间内转办的信件
                 */
                + " where state=1 and zb_date>='"
                + startdate + "' and zb_date<='" + DateUtil.getNextDayStr(enddate) + "' "
                /**
                 * 已反馈的信件
                 */
                /**
                 * 按单位分组
                 */
                + " group by zb_management ";
        
        /*
         * 在选定的时间内，逾期未办理件数
         */
        String sqlYQWB = "select zb_management ,count(id) from mgt_zhuanban where state =0 and " +
        		"zb_date>='"+ startdate + "' and zb_date<='" + DateUtil.getNextDayStr(enddate) + "' " +
        		"and substr(need_date,1,10)< '" +DateUtil.getToday()+"'"+
        		"group by zb_management";
        
       /*
        * 在选定的时间内，该单位逾期办理多少件,
        */
        String sqlYQBL = "select zb_management ,count(a.id) from mgt_zhuanban a,mgt_handle b" +
        		" where a.state=1 and zb_date>=' "
            	+ startdate + "' and zb_date<='" + DateUtil.getNextDayStr(enddate) + "'  " +
        		"and a.id=b.zb_id and substr(need_date,1,10)< substr(b.reply_date,1,10)" +
        		" group by zb_management";
        
        /*
         *回访件数 
         */
        String sqlHF="select zb_management ,count(a.id) from mgt_zhuanban a,mgt_callback b "
			+ "where a.info_id=b.info_id and zb_date>='"
			+ startdate
			+ "' and zb_date<='"
			+ DateUtil.getNextDayStr(enddate) +"'"
			+ " group by zb_management";
     
        /*
         * 选定交办时间内，满意率情况
         */
        String sqlMY = "select zb_management ,count(a.id) from mgt_zhuanban a,mgt_callback b "
			+ "where hf_pleased!=2 and a.info_id=b.info_id and zb_date>='"
			+ startdate
			+ "' and zb_date<='"
			+ DateUtil.getNextDayStr(enddate) +"'"
			+ " group by zb_management";
        System.out.println(sqlMY);
        Hashtable<String,String> ZBTable = daoCtl.getHTable(dao, Sqls.create(sqlZB));
        Hashtable<String,String> FkTable = daoCtl.getHTable(dao, Sqls.create(sqlFK));
        Hashtable<String,String> BLZTable = daoCtl.getHTable(dao, Sqls.create(sqlBLZ));
        Hashtable<String,String> YQWBTable = daoCtl.getHTable(dao, Sqls.create(sqlYQWB));
        Hashtable<String,String> YQBLTable = daoCtl.getHTable(dao, Sqls.create(sqlYQBL));
        Hashtable<String,String> HFTable = daoCtl.getHTable(dao, Sqls.create(sqlHF));
        Hashtable<String,String> MYTable = daoCtl.getHTable(dao, Sqls.create(sqlMY));
        long[] counts=new long[8];
		List<List<String>> relist=new ArrayList<List<String>>();
		if(unitlist!=null && unitlist.size()>0){
			for(Sys_unit unit:unitlist){
				List<String> temp=new ArrayList<String>();
				temp.add(unit.getName());
				temp.add(unit.getUnitcode());
				temp.add(ZBTable.get(unit.getId())==null?"0":ZBTable.get(unit.getId()));
				temp.add(BLZTable.get(unit.getId())==null?"0":BLZTable.get(unit.getId()));
				temp.add(FkTable.get(unit.getId())==null?"0":FkTable.get(unit.getId()));
				temp.add(YQWBTable.get(unit.getId())==null?"0":YQWBTable.get(unit.getId()));
				temp.add(YQBLTable.get(unit.getId())==null?"0":YQBLTable.get(unit.getId()));
				temp.add(HFTable.get(unit.getId())==null?"0":HFTable.get(unit.getId()));
				temp.add(MYTable.get(unit.getId())==null||HFTable.get(unit.getId())==null?"0.0%":
					formatNum(StringUtil.StringToInt(MYTable.get(unit.getId()))*1.0/StringUtil.StringToInt(HFTable.get(unit.getId()))));
				counts[0]+=new Long(ZBTable.get(unit.getId())==null?"0":ZBTable.get(unit.getId()));
				counts[1]+=new Long(BLZTable.get(unit.getId())==null?"0":BLZTable.get(unit.getId()));
				counts[2]+=new Long(FkTable.get(unit.getId())==null?"0":FkTable.get(unit.getId()));
				counts[3]+=new Long(YQWBTable.get(unit.getId())==null?"0":YQWBTable.get(unit.getId()));
				counts[4]+=new Long(YQBLTable.get(unit.getId())==null?"0":YQBLTable.get(unit.getId()));
				counts[5]+=new Long(HFTable.get(unit.getId())==null?"0":HFTable.get(unit.getId()));
				counts[6]+=new Long(MYTable.get(unit.getId())==null?"0":MYTable.get(unit.getId()));
				relist.add(temp);
			}
		}
		List<String> temp=new ArrayList<String>();
		temp.add("合计");
		temp.add("合计");
		int num = 0;
		long fkhj=0;
		for(long i:counts){
			if(num==5){//反馈合计
				fkhj=i;
			}
			if(num==6){ //满意数合计
				if(fkhj!=0&&i!=0){
					temp.add(formatNum(i*1.0/fkhj)+"%");
				}else{
					temp.add(i+"%");
				}
				break;
			}
			temp.add(i+"");
			num++;
		}
		relist.add(temp);
		req.setAttribute("relist", relist);
	}
	
	/**
	 * 跳转到汇总统计页面
	 * @param session
	 * @param req
	 */
	@At
	@Ok("->:/private/reportforms/huizong.html")
	public void hztj(HttpSession session, HttpServletRequest req,
			@Param("startdate") String startdate,@Param("enddate") String enddate,@Param("wy") String wy) {
		if(startdate==null || startdate.trim().equals("")){
			req.setAttribute("startdate", DateUtil.getCurDateTime().substring(0,4)+"-01-01");
			startdate=DateUtil.getCurDateTime().substring(0,4)+"-01-01";
		}
		req.setAttribute("startdate",startdate);
		if(enddate==null || enddate.trim().equals("")){
			req.setAttribute("enddate", DateUtil.getCurDateTime().substring(0,10));
			enddate=DateUtil.getCurDateTime().substring(0,10);
		}
		req.setAttribute("enddate",enddate);
         String unitSql = "select * from sys_unit where id like '0002____' ";
         if(wy!=null&&!"".equals(wy)){
        	 unitSql+=" and id='"+wy+"'";
          }
         unitSql+=" order by id,location";
		List<Sys_unit> unitlist=daoCtl.list(dao,Sys_unit.class, Sqls.create(unitSql));
		List<T_dmjfl> fl=daoCtl.list(dao,T_dmjfl.class, Sqls.create("select * from T_dmjfl where flbh like '00020002____'order by id"));
		req.setAttribute("fl", fl);
		req.setAttribute("unitlist", unitlist);
		String flSql = "SELECT CONCAT(phone_type,SUBSTR(management,1,8)),COUNT(*) FROM mgt_info ";
        if(wy!=null&&!"".equals(wy)){
        	flSql+=" where SUBSTR(management,1,8)='"+wy+"'" +
        			" and add_date>='"+startdate + "' and add_date<='" + DateUtil.getNextDayStr(enddate) + "'";
         }
        flSql+=" GROUP BY management,phone_type";
		Map<String,String> flMap= daoCtl.getHTable(dao, Sqls.create(flSql));
		req.setAttribute("flMap", flMap);
		String flSql2 = "SELECT phone_type,COUNT(*) FROM mgt_info ";
        if(wy!=null&&!"".equals(wy)){
        	flSql2+=" where SUBSTR(management,1,8)='"+wy+"'" +
        			" and add_date>='"+startdate + "' and add_date<='" + DateUtil.getNextDayStr(enddate) + "'";
         }
        flSql2+=" GROUP BY phone_type";
		Map<String,String> flhjMap= daoCtl.getHTable(dao, Sqls.create(flSql2));
		req.setAttribute("flhjMap", flhjMap);
		req.setAttribute("wy", wy);

		
		  /*
         * 在选定的时间内，该单位一共受到多少转办件
         */
        String sqlZHB = "select substr(management,1,8) ,count(id) from mgt_info "
                /**
                 * 信件id在 选定的时间内转办的的信件id中
                 */
                + " where state=1 and add_date>='"+startdate + "' and add_date<='" + DateUtil.getNextDayStr(enddate) + "'  ";
                /**
                 * 按单位分组
                 */
        		if(wy!=null&&!"".equals(wy)){
                	sqlZHB+=" and substr(management,1,8)='"+wy+"'";
                }
                sqlZHB+= " group by  substr(management,1,8) ";
        /*
         * 在选定的时间内，该单位一共受到多少转办件
         */
        String sqlZB = "select zb_management ,count(id) from mgt_zhuanban "
                /**
                 * 信件id在 选定的时间内转办的的信件id中
                 */
                + " where  zb_date>='"+startdate + "' and zb_date<='" + DateUtil.getNextDayStr(enddate) + "'  ";
                /**
                 * 按单位分组
                 */
				if(wy!=null&&!"".equals(wy)){
					sqlZB+=" and zb_management='"+wy+"'";
                }
				sqlZB+= " group by  zb_management";
        
        /*
         * 在选定的时间内，该单位正在办理多少转办件
         */
        String sqlBLZ = "select zb_management ,count(id) from mgt_zhuanban "
                /**
                 * 信件id在 选定的时间内转办的的信件id中
                 */
                + " where state=0 and zb_date>='"
                + startdate + "' and zb_date<='" + DateUtil.getNextDayStr(enddate) + "'  ";
                /**
                 * 按单位分组
                 */
				if(wy!=null&&!"".equals(wy)){
                	sqlBLZ+=" and zb_management='"+wy+"'";
                }
                sqlBLZ+= " group by  zb_management";
        
        /*
         * 在选定的时间内，该单位一共反馈办结多少件
         */
        String sqlFK = "select zb_management ,count(id) from mgt_zhuanban"
                /**
                 * 选定时间内转办的信件
                 */
                + " where state=1 and zb_date>='"
                + startdate + "' and zb_date<='" + DateUtil.getNextDayStr(enddate) + "' ";
                /**
                 * 已反馈的信件
                 */
                /**
                 * 按单位分组
                 */
				if(wy!=null&&!"".equals(wy)){
                	sqlFK+=" and zb_management='"+wy+"'";
                }
                sqlFK+= " group by  zb_management";
        
        /*
         * 在选定的时间内，逾期未办理件数
         */
        String sqlYQWB = "select zb_management ,count(id) from mgt_zhuanban where state =0 and " +
        		"zb_date>='"+ startdate + "' and zb_date<='" + DateUtil.getNextDayStr(enddate) + "' " +
        		"and substr(need_date,1,10)< '" +DateUtil.getToday()+"'";
				if(wy!=null&&!"".equals(wy)){
                 	sqlYQWB+=" and zb_management='"+wy+"'";
                 }
                 sqlYQWB+= " group by  zb_management";
        
       /*
        * 在选定的时间内，该单位逾期办理多少件,
        */
        String sqlYQBL = "select zb_management ,count(a.id) from mgt_zhuanban a,mgt_handle b" +
        		" where a.state=1 and zb_date>=' "
            	+ startdate + "' and zb_date<='" + DateUtil.getNextDayStr(enddate) + "'  " +
        		"and a.id=b.zb_id and substr(need_date,1,10)< substr(b.reply_date,1,10)" ;
				if(wy!=null&&!"".equals(wy)){
                 	sqlYQBL+=" and zb_management='"+wy+"'";
                 }
                 sqlYQBL+= " group by  zb_management";
        
        /*
         *回访件数 
         */
        String sqlHF="select zb_management ,count(a.id) from mgt_zhuanban a,mgt_callback b "
			+ "where a.info_id=b.info_id and zb_date>='"
			+ startdate
			+ "' and zb_date<='"
			+ DateUtil.getNextDayStr(enddate) +"'";
			if(wy!=null&&!"".equals(wy)){
             	sqlHF+=" and zb_management='"+wy+"'";
             }
             sqlHF+= " group by  zb_management";
     
        /*
         * 选定交办时间内，满意率情况
         */
        String sqlMY = "select zb_management ,count(a.id) from mgt_zhuanban a,mgt_callback b "
			+ "where hf_pleased!=2 and a.info_id=b.info_id and zb_date>='"
			+ startdate
			+ "' and zb_date<='"
			+ DateUtil.getNextDayStr(enddate) +"'";
			if(wy!=null&&!"".equals(wy)){
             	sqlMY+=" and zb_management='"+wy+"'";
             }
             sqlMY+= " group by  zb_management";
        System.out.println(sqlMY);
        Hashtable<String,String> ZHBTable = daoCtl.getHTable(dao, Sqls.create(sqlZHB));
        Hashtable<String,String> ZBTable = daoCtl.getHTable(dao, Sqls.create(sqlZB));
        Hashtable<String,String> FkTable = daoCtl.getHTable(dao, Sqls.create(sqlFK));
        Hashtable<String,String> BLZTable = daoCtl.getHTable(dao, Sqls.create(sqlBLZ));
        Hashtable<String,String> YQWBTable = daoCtl.getHTable(dao, Sqls.create(sqlYQWB));
        Hashtable<String,String> YQBLTable = daoCtl.getHTable(dao, Sqls.create(sqlYQBL));
        Hashtable<String,String> HFTable = daoCtl.getHTable(dao, Sqls.create(sqlHF));
        Hashtable<String,String> MYTable = daoCtl.getHTable(dao, Sqls.create(sqlMY));
        long[] counts=new long[8];
		List<List<String>> relist=new ArrayList<List<String>>();
		if(unitlist!=null && unitlist.size()>0){
			for(Sys_unit unit:unitlist){
				System.out.println(unit.getId());
				List<String> temp=new ArrayList<String>();
				temp.add(unit.getId());
				temp.add(unit.getUnitcode());
				temp.add(ZBTable.get(unit.getId())==null?"0":ZBTable.get(unit.getId()));
				temp.add(BLZTable.get(unit.getId())==null?"0":BLZTable.get(unit.getId()));
				temp.add(FkTable.get(unit.getId())==null?"0":FkTable.get(unit.getId()));
				temp.add(YQWBTable.get(unit.getId())==null?"0":YQWBTable.get(unit.getId()));
				temp.add(YQBLTable.get(unit.getId())==null?"0":YQBLTable.get(unit.getId()));
				temp.add(HFTable.get(unit.getId())==null?"0":HFTable.get(unit.getId()));
				temp.add(MYTable.get(unit.getId())==null||HFTable.get(unit.getId())==null?"0.0%":
					formatNum(StringUtil.StringToInt(MYTable.get(unit.getId()))*1.0/StringUtil.StringToInt(HFTable.get(unit.getId())))+"%");
				temp.add(ZHBTable.get(unit.getId())==null?"0":ZHBTable.get(unit.getId()));
				counts[0]+=new Long(ZBTable.get(unit.getId())==null?"0":ZBTable.get(unit.getId()));
				counts[1]+=new Long(BLZTable.get(unit.getId())==null?"0":BLZTable.get(unit.getId()));
				counts[2]+=new Long(FkTable.get(unit.getId())==null?"0":FkTable.get(unit.getId()));
				counts[3]+=new Long(YQWBTable.get(unit.getId())==null?"0":YQWBTable.get(unit.getId()));
				counts[4]+=new Long(YQBLTable.get(unit.getId())==null?"0":YQBLTable.get(unit.getId()));
				counts[5]+=new Long(HFTable.get(unit.getId())==null?"0":HFTable.get(unit.getId()));
				counts[6]+=new Long(MYTable.get(unit.getId())==null?"0":MYTable.get(unit.getId()));
				counts[7]+=new Long(ZHBTable.get(unit.getId())==null?"0":ZHBTable.get(unit.getId()));
				relist.add(temp);
			}
		}
		List<String> temp=new ArrayList<String>();
		temp.add("合计");
		temp.add("合计");
		int num = 0;
		long fkhj=0;
		for(long i:counts){
			if(num==5){//反馈合计
				fkhj=i;
			}
			if(num==6){ //满意数合计
				if(fkhj!=0&&i!=0){
					temp.add(formatNum(i*1.0/fkhj)+"%");
				}else{
					temp.add(i+"%");
				}
			}
			temp.add(i+"");
			num++;
		}
		if(wy==null||"".equals(wy)){
			relist.add(temp);
		}
		req.setAttribute("relist", relist);
	}
	
	
	
	/**
	 * 跳转到项目统计页面
	 * @param session
	 * @param req
	 */
	@At
	@Ok("->:/private/reportforms/xmbb.html")
	public void xmbb(HttpSession session, HttpServletRequest req,
			@Param("startdate") String startdate,@Param("enddate") String enddate) {
		if(startdate==null || startdate.trim().equals("")){
			req.setAttribute("startdate", DateUtil.getCurDateTime().substring(0,4)+"-01-01");
			startdate=DateUtil.getCurDateTime().substring(0,4)+"-01-01";
		}
		req.setAttribute("startdate",startdate);
		if(enddate==null || enddate.trim().equals("")){
			req.setAttribute("enddate", DateUtil.getCurDateTime().substring(0,10));
			enddate=DateUtil.getCurDateTime().substring(0,10);
		}
		req.setAttribute("enddate",enddate);
         String unitSql = "select * from sys_unit where id like '0002____' ";
         unitSql+=" order by id,location";
         Map<String,String> unitMap=daoCtl.getHTable(dao,Sqls.create("select id,name from sys_unit where id like '0002____'"));
  		req.setAttribute("unitMap", unitMap);
 		Map<String,String> countMap=daoCtl.getHTable(dao,Sqls.create("SELECT ssfl,COUNT(ssfl) FROM t_daimjb WHERE ssfl LIKE '00020002____' GROUP BY ssfl"));
 		req.setAttribute("countMap", countMap);
 		Map<String,String> countMap2=daoCtl.getHTable(dao,Sqls.create("SELECT SUBSTR(id,1,8),COUNT(id) FROM sys_unit WHERE id LIKE '0002________' GROUP BY SUBSTR(id,1,8)"));
 		req.setAttribute("countMap2", countMap2);
		List<Sys_unit> unitlist=daoCtl.list(dao,Sys_unit.class, Sqls.create(unitSql));
		List<T_dmjfl> fl=daoCtl.list(dao,T_dmjfl.class, Sqls.create("select * from T_dmjfl where flbh like '00020002____'order by id"));
		req.setAttribute("fl", fl);
		List ejfl=daoCtl.getMulRowValue(dao, Sqls.create("SELECT CONCAT(b.flbh,f_vc_daimz1),f_vc_daimmc FROM t_daimjb a,t_dmjfl b WHERE ssfl=b.flbh AND b.flbh LIKE '00020002____' ORDER BY CONCAT(b.flbh,f_vc_daimz1) "));
		req.setAttribute("ejfl", ejfl);
		Map<String,String> ejdw=daoCtl.getHTable(dao, Sqls.create("SELECT id,name FROM sys_unit WHERE id LIKE '0002________' ORDER BY id,location "));
		req.setAttribute("ejdw", ejdw);
		Map<String,String> ejflMap=daoCtl.getHTable(dao, Sqls.create("SELECT " +
				"CONCAT(phone_type,repairs_type,management),COUNT(*) FROM mgt_info " +
				" where add_date>='"+startdate + "' and add_date<='" + DateUtil.getNextDayStr(enddate) + "' " +
						" GROUP BY CONCAT(phone_type,repairs_type,management) "));
		req.setAttribute("ejflMap", ejflMap);
		req.setAttribute("unitlist", unitlist);
		List<Sys_unit> ejdwList=daoCtl.list(dao, Sys_unit.class, Sqls.create("SELECT id,name FROM sys_unit WHERE id LIKE '0002________' ORDER BY id,location "));


		
		  /*
         * 在选定的时间内，该单位一共受到多少转办件
         */
        String sqlZHB = "select management ,count(id) from mgt_info "
                /**
                 * 信件id在 选定的时间内转办的的信件id中
                 */
                + " where state=1 and add_date>='"+startdate + "' and add_date<='" + DateUtil.getNextDayStr(enddate) + "'  ";
                /**
                 * 按单位分组
                 */
                sqlZHB+= " group by management ";
        /*
         * 在选定的时间内，该单位一共受到多少转办件
         */
        String sqlZB = "select management ,count(id) from mgt_zhuanban "
                /**
                 * 信件id在 选定的时间内转办的的信件id中
                 */
                + " where  zb_date>='"+startdate + "' and zb_date<='" + DateUtil.getNextDayStr(enddate) + "'  ";
                /**
                 * 按单位分组
                 */
				sqlZB+= " group by  management";
        
        /*
         * 在选定的时间内，该单位正在办理多少转办件
         */
        String sqlBLZ = "select management ,count(id) from mgt_zhuanban "
                /**
                 * 信件id在 选定的时间内转办的的信件id中
                 */
                + " where state=0 and zb_date>='"
                + startdate + "' and zb_date<='" + DateUtil.getNextDayStr(enddate) + "'  ";
                /**
                 * 按单位分组
                 */
                sqlBLZ+= " group by  management";
        
        /*
         * 在选定的时间内，该单位一共反馈办结多少件
         */
        String sqlFK = "select management ,count(id) from mgt_zhuanban"
                /**
                 * 选定时间内转办的信件
                 */
                + " where state=1 and zb_date>='"
                + startdate + "' and zb_date<='" + DateUtil.getNextDayStr(enddate) + "' ";
                /**
                 * 已反馈的信件
                 */
                /**
                 * 按单位分组
                 */
                sqlFK+= " group by  management";
        
        /*
         * 在选定的时间内，逾期未办理件数
         */
        String sqlYQWB = "select management ,count(id) from mgt_zhuanban where state =0 and " +
        		"zb_date>='"+ startdate + "' and zb_date<='" + DateUtil.getNextDayStr(enddate) + "' " +
        		"and substr(need_date,1,10)< '" +DateUtil.getToday()+"'";
                 sqlYQWB+= " group by  management";
        
       /*
        * 在选定的时间内，该单位逾期办理多少件,
        */
        String sqlYQBL = "select management ,count(a.id) from mgt_zhuanban a,mgt_handle b" +
        		" where a.state=1 and zb_date>=' "
            	+ startdate + "' and zb_date<='" + DateUtil.getNextDayStr(enddate) + "'  " +
        		"and a.id=b.zb_id and substr(need_date,1,10)< substr(b.reply_date,1,10)" ;
                 sqlYQBL+= " group by  management";
        
        /*
         *回访件数 
         */
        String sqlHF="select management ,count(a.id) from mgt_zhuanban a,mgt_callback b "
			+ "where a.info_id=b.info_id and zb_date>='"
			+ startdate
			+ "' and zb_date<='"
			+ DateUtil.getNextDayStr(enddate) +"'";
             sqlHF+= " group by  management";
     
        /*
         * 选定交办时间内，满意率情况
         */
        String sqlMY = "select management ,count(a.id) from mgt_zhuanban a,mgt_callback b "
			+ "where hf_pleased!=2 and a.info_id=b.info_id and zb_date>='"
			+ startdate
			+ "' and zb_date<='"
			+ DateUtil.getNextDayStr(enddate) +"'";
             sqlMY+= " group by  management";
             
             /*
              * 在选定的时间内，该单位一共未完成受到多少件
              */
             String sqlWWC = "select management ,count(id) from mgt_info "
                     /**
                      * 信件id在 选定的时间内转办的的信件id中
                      */
                     + " where state not in (1,2,4) and add_date>='"+startdate + "' and add_date<='" + DateUtil.getNextDayStr(enddate) + "'  ";
                     /**
                      * 按单位分组
                      */
             sqlWWC+= " group by management ";
             
             /*
              * 在选定的时间内，该单位一共受理多少件
              */
             String sqlSL = "select management ,count(id) from mgt_info "
                     /**
                      * 信件id在 选定的时间内转办的的信件id中
                      */
                     + " where  add_date>='"+startdate + "' and add_date<='" + DateUtil.getNextDayStr(enddate) + "'  ";
                     /**
                      * 按单位分组
                      */
             sqlSL+= " group by management ";
        Hashtable<String,String> ZHBTable = daoCtl.getHTable(dao, Sqls.create(sqlZHB));
        Hashtable<String,String> ZBTable = daoCtl.getHTable(dao, Sqls.create(sqlZB));
        Hashtable<String,String> FkTable = daoCtl.getHTable(dao, Sqls.create(sqlFK));
        Hashtable<String,String> BLZTable = daoCtl.getHTable(dao, Sqls.create(sqlBLZ));
        Hashtable<String,String> YQWBTable = daoCtl.getHTable(dao, Sqls.create(sqlYQWB));
        Hashtable<String,String> YQBLTable = daoCtl.getHTable(dao, Sqls.create(sqlYQBL));
        Hashtable<String,String> HFTable = daoCtl.getHTable(dao, Sqls.create(sqlHF));
        Hashtable<String,String> MYTable = daoCtl.getHTable(dao, Sqls.create(sqlMY));
        Hashtable<String,String> WWCTable = daoCtl.getHTable(dao, Sqls.create(sqlWWC));
        Hashtable<String,String> SLTable = daoCtl.getHTable(dao, Sqls.create(sqlSL));
		req.setAttribute("ZHBTable", ZHBTable);
		req.setAttribute("ZBTable", ZBTable);
		req.setAttribute("FkTable", FkTable);
		req.setAttribute("BLZTable", BLZTable);
		req.setAttribute("YQWBTable", YQWBTable);
		req.setAttribute("YQBLTable", YQBLTable);
		req.setAttribute("HFTable", HFTable);
		req.setAttribute("MYTable", MYTable);
		req.setAttribute("WWCTable",WWCTable);
		req.setAttribute("SLTable", SLTable);
		req.setAttribute("Str", new StringUtil());
	}
	
	
	
	
	public static String formatNum(double f) {
		return new BigDecimal(f * 100).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue()
				+ "";
	}
}
