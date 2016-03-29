package com.hits.common.util;

import com.hits.common.dao.ObjectCtl;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.mvc.Mvcs;

/**
 * Created by Numbgui on 2015/5/28.
 */
public class IdUtil {
    private static final int gonghaoLength = 5;

    public static String getInfoSN(String str,String maxSN) {
        String infoSN = "";
        if (maxSN == null || "".equals(maxSN)) {
            infoSN = str + "0001";
        } else {
            infoSN = getInfoSNByMaxSN(maxSN);
        }
        return infoSN;
    }

    /**
     * @param maxSN
     * @return String
     */
    public static String getInfoSNByMaxSN(String maxSN) {
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
}
