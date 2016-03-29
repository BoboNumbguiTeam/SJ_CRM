package com.hits.common.util;

import org.nutz.json.Json;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wizzer on 14-4-2.
 */
public class ErrorUtil {
    public static String getErrorMsg(int res){
        Map<String,Object> map=new HashMap<String, Object>();
        switch (res){
            case -1:
                map.put("errcode",-1);
                map.put("errmsg","��Դδͨ����֤");
                break;
            case 1:
                map.put("errcode",1);
                map.put("errmsg","�����ɹ�");
                break;
            case 2:
                map.put("errcode",2);
                map.put("errmsg","����ʧ��");
                break;
            case 3:
                map.put("errcode",3);
                map.put("errmsg","��������");
                break;
            case 4:
                map.put("errcode",4);
                map.put("errmsg","����δ����");
                break;
            case 5:
                map.put("errcode",5);
                map.put("errmsg","δ��ȡ������");
                break;
            default:
                map.put("errcode",0);
                map.put("errmsg","ϵͳ����");
                break;

        }
        return Json.toJson(map);
    }
}
