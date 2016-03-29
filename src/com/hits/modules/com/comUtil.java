package com.hits.modules.com;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


import com.hits.util.DateUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Criteria;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;

import com.hits.common.dao.ObjectCtl;
import com.hits.modules.sjygl.bean.T_daimjb;

public class comUtil {
	private static final Log log = Logs.get();
	public static Hashtable<String, Integer> defaultSetHt = new Hashtable<String, Integer>(); // 默认基础参数
	//分类编码 代码集 List 通过分类编码获取代码集 List
    public static Hashtable<String,List<T_daimjb>> ysjdmjHt = new Hashtable<String,List<T_daimjb>>();  //元数据代码集　

	public static int qdSpacing = 0; // 查询时间间隔
	public static int pageSize = 0; // 一页记录显示条数
	public static int defalutxbd = 0; // 默认限办工作日
	public static int ldpslc = 0; // 领导批示流程
	public static int defalutybd = 0; // 默认延期限办工作日
	public static int dbtxd = 0; // 系统督办提醒工作日
	public static int rgdbqx = 0; // 督办员人工督办的期限工作日

	public void init() {
		// comletterstate.put(6, "部门退回申请");
		// comletterstate.put(8, "部门延期申请");
	}
	
	/**
     * @param ssfl :分类编号
     * @return 代码集列表，每个列表元素是一个代码集对象，包括代码名称，代码值1，代码值2
     */
	public static List<T_daimjb> getDmjList(Dao dao,String ssfl) {
		List<T_daimjb> list = null;
		try {
			if (!"".equals(ssfl) && ssfl.length() > 0 && ssfl != null) {
				list = ysjdmjHt.get(ssfl);
				if (null == list) {
					list = new ArrayList<T_daimjb>();
					//获取元数据代码集
					Criteria cri = Cnd.cri();
					cri.where().and("ssfl", "=", ssfl);
					list = dao.query(T_daimjb.class, cri);
					ysjdmjHt.put(ssfl, list);//94ye
					//System.out.println(new ObjectCtl().list(dao, T_daimjb.class, cri));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(comUtil.class, e);
		}
		return list;
	}
	
	/**
     * @param ssfl ：分类编号
     * @param dmz1 ：代码值1
     * @return 代码集名称
     */
	public static String getDmjValue(Dao dao, String ssfl, String dmz1) {
		String valtemp = "";
		String returnvalue = "";
		try {
			if (dmz1 == null || "".equals(dmz1) || dmz1.length() < 1)
				return returnvalue;
			List<T_daimjb> list = new ArrayList<T_daimjb>();
			if (!"".equals(ssfl) && ssfl.length() > 0 && ssfl != null) {
				list = getDmjList(dao, ssfl);;
			}
			for (int i = 0; i < list.size(); i++) {
				T_daimjb dmjb = list.get(i);
				valtemp = dmjb.getF_vc_daimz1();
				if (valtemp.equals(dmz1)) {
					returnvalue = dmjb.getF_vc_daimmc();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(comUtil.class, e);
			return "";
		}
		return returnvalue;
	}

	// 得到12位时间加随机数的验证码
	public static String getVifCode() {
		int num = 0;
		for (int i = 0; i < 6; i++) {
			num = (int) (num * 10 + Math.round(Math.random() * 10));
		}
		String time = DateUtil.time.format(new Date());
		return time + String.valueOf(num);
	}

	public static String getSlj_infoSNByMaxSN(String maxSN) {
		String SN = "";
		int length = maxSN.length();
		int SNEnd = Integer.parseInt(maxSN.substring(length - 4, length)) + 1;
		String strSNEnd = SNEnd + "";
		while (strSNEnd.length() < 4) {
			strSNEnd = "0" + strSNEnd;
		}
		SN = maxSN.substring(0, length - 4) + strSNEnd;
		return SN;
	}

	/**
	 * 将ISO字符转化为UTF-8字符
	 * 
	 * @param str
	 */
	public static String getUTFFromISO(String str) {
		try {
			if (str == null)
				str = "";
			byte[] buf = str.getBytes("iso-8859-1");
			byte[] buf2 = str.getBytes("UTF-8");
			if (!str.equals(new String(buf2, "UTF-8"))) {
				str = new String(buf, "UTF-8");
			}

			return str;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 获取指定begindate日期后，第works个工作日的日期，会从sys_holiday表中筛选掉节假日
	 * 
	 * @param daoCtl
	 * @param dao
	 * @param begindate
	 * @param works
	 * @return
	 */
	public static String getLimiteDate(ObjectCtl daoCtl, Dao dao, Date begindate, int works) {
		if (dao != null && begindate != null && works > 0) {
			Map<Date, Integer> _20days = new HashMap<Date, Integer>();
			List<Date> _20alldays = new ArrayList<Date>();
			Date nextdate = begindate;
			for (int i = 0; i < 20; i++) {
				nextdate = new Date(nextdate.getTime() + 24 * 3600 * 1000);
				_20days.put(nextdate, 1);// 默认全部为工作日
				_20alldays.add(nextdate);
			}
			String bdates = DateUtil.getDateStr(begindate);
			// 周末工作日
			List<String> nextwork = daoCtl.getStrRowValues(dao, Sqls.create("select holiday from SYS_HOLIDAYINFO where iswork=1 " + "and to_date(holiday,'yyyy-MM-dd')>to_date('" + bdates + "','yyyy-MM-dd') " + "and to_date(holiday,'yyyy-MM-dd')<=to_date('" + bdates + "','yyyy-MM-dd')+20"));
			// 普通工作日放假
			List<String> nextholiday = daoCtl.getStrRowValues(dao, Sqls.create("select holiday from SYS_HOLIDAYINFO where iswork=0 " + "and to_date(holiday,'yyyy-MM-dd')>to_date('" + bdates + "','yyyy-MM-dd') " + "and to_date(holiday,'yyyy-MM-dd')<=to_date('" + bdates + "','yyyy-MM-dd')+20"));
			for (Date day : _20alldays) {
				if (!DateUtil.isWeekend(DateUtil.getDateStr(day))) {// 如果是普通工作日
					if (nextholiday.contains(DateUtil.getDateStr(day))) {// 如果普通工作日调休
						_20days.put(day, 0);
					}
				} else {
					_20days.put(day, 0);
					if (nextwork.contains(DateUtil.getDateStr(day))) {// 如果周末调为工作日
						_20days.put(day, 1);
					}
				}
				// System.out.println(DateUtil.getDateStr(day)+"="+_20days.get(day));
			}
			String limitdate = "";
			int j = 0;
			for (int i = 0; i < 20; i++) {
				if (j < works - 1) {
					if (_20days.get(_20alldays.get(i)) == 1) {
						j++;
					}
				} else {
					if (_20days.get(_20alldays.get(i)) == 1) {
						limitdate = DateUtil.getDateStr(_20alldays.get(i));
						break;
					}
				}
			}
			return limitdate;
		}
		return "";
	}

	/**
	 * 获取开始日期到结束日期之间有多少个工作日
	 * 
	 * @param state
	 * @param end
	 * @return
	 */
	public static int getWorkDays(ObjectCtl daoCtl, Dao dao, Date state, Date end) {
		int works = 0;
		if (state.before(end)) {
			List<Date> alldays = new ArrayList<Date>();
			Date nextdate = state;
			alldays.add(nextdate);
			while (nextdate.before(end)) {
				nextdate = new Date(nextdate.getTime() + 24 * 3600 * 1000);
				alldays.add(nextdate);
				// System.out.println(nextdate);
			}
			// 周末工作日
			List<String> nextwork = daoCtl.getStrRowValues(dao, Sqls.create("select holiday from SYS_HOLIDAYINFO where iswork=1 " + "and to_date(holiday,'yyyy-MM-dd')>=to_date('" + DateUtil.getDateStr(state) + "','yyyy-MM-dd') " + "and to_date(holiday,'yyyy-MM-dd')<=to_date('" + DateUtil.getDateStr(end) + "','yyyy-MM-dd')"));
			// 普通工作日放假
			List<String> nextholiday = daoCtl.getStrRowValues(dao, Sqls.create("select holiday from SYS_HOLIDAYINFO where iswork=0 " + "and to_date(holiday,'yyyy-MM-dd')>=to_date('" + DateUtil.getDateStr(state) + "','yyyy-MM-dd') " + "and to_date(holiday,'yyyy-MM-dd')<=to_date('" + DateUtil.getDateStr(end) + "','yyyy-MM-dd')"));

			for (Date d : alldays) {
				if (!DateUtil.isWeekend(DateUtil.getDateStr(d))) {// 如果不是周末
					if (!nextholiday.contains(DateUtil.getDateStr(d))) {// 普通工作日放假
						// System.out.println("工作日："+DateUtil.date2str(d));
						works++;
					}
				} else {
					if (nextwork.contains(DateUtil.getDateStr(d))) {// 周末工作日
						// System.out.println("工作日："+DateUtil.date2str(d));
						works++;
					}
				}
			}
			return works;
		}
		return -4;
	}

	/**
	 * 通过单位编码和单位名得到单位名（A;B）
	 * 
	 * @param TteeidAndNames
	 */
	public static String getUnitnamesByTreeidAndNames(String TteeidAndNames) {
		StringBuffer result = new StringBuffer();

		TteeidAndNames = TteeidAndNames.trim();
		String[] strs = TteeidAndNames.split(";");
		for (int i = 0; i < strs.length; i++) {
			String str = strs[i];
			if (str != null && !str.trim().equals("")) {
				String temp = str.substring(0, str.indexOf("/"));
				result.append(temp).append("、");
			}
		}
		String str = "";
		if (result.length() > 0) {
			str = result.toString();
			str = str.substring(0, str.length() - 1);
		}
		return str;

	}

	/**
	 * 通过单位编码和单位名得到单位编码(A;B)
	 * 
	 * @param TteeidAndNames
	 */
	public static String getTreeidByTreeidAndNames(String TteeidAndNames) {
		if (TteeidAndNames == null || TteeidAndNames.length() == 0) {
			return "";
		} else {
			StringBuffer result = new StringBuffer();

			TteeidAndNames = TteeidAndNames.trim();
			String[] strs = TteeidAndNames.split(";");
			for (int i = 0; i < strs.length; i++) {
				String str = strs[i];
				if (str != null && !str.trim().equals("")) {
					String temp = str.substring(str.indexOf("/") + 1);
					result.append(temp).append(";");
				}
			}
			return result.toString();
		}
	}

	/**
	 * velocity专用object to String
	 */
	public String toStr(Object obj) {
		return obj.toString();
	}
}
