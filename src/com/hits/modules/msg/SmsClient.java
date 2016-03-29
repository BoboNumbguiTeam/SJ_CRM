package com.hits.modules.msg;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.hits.modules.msg.bean.WindControlData;
import com.hits.modules.msg.bean.WindControlMsg;
import com.hits.modules.msg.bean.WindControlRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;


/**
 * 中意之旅短信接口Demo
 * @author wq
 */
public class SmsClient {

	public String sendSms(String mobile,String content) {
		String operCode = "xdwy0001";//短信平台中的用户账号
		String secretKey = "8DC0E47C27B33E65EDBE33FED583BC91";//短信平台用户的密钥 （登录系统后--个人信息）
		/*################################ 以上参数仅供测试使用，真实开发需根据自身信息修改   #################################*/
		
		String url = "http://211.141.176.118/zyzlsms/service/sms";//短信接口
		String method = "send_sms";//接口方法名称
		
		WindControlData windControlData=new WindControlData();
		Map<String,String> msgMap = new HashMap<String,String>();
		WindControlRequest windControlRequest =new  WindControlRequest();
		windControlRequest.setMobile(mobile);
		windControlRequest.setOperCode(operCode);
		
		windControlData.setMethod(method);
		windControlData.setRequest(windControlRequest);
		windControlData.setContent(content);
		windControlData.setOperCode(operCode);
		
		//签名
		String reqJsonString = JSON.toJSONStringWithDateFormat(windControlRequest, "yyyy-MM-dd HH:mm:ss");
		String md5TempString = operCode+"&"+method+"&"+new MD5().getMD5ofStr(reqJsonString)+"&"+secretKey;
		String sign = new MD5().getMD5ofStr(md5TempString);
		
		windControlData.setSign(sign);
		String body = JSON.toJSONStringWithDateFormat(windControlData,"yyyy-MM-dd HH:mm:ss");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("body", this.getEncodeValue(body));
		
		String json = HttpClientUtil.httpPost(url, param);//
		WindControlMsg windControlMsg = (WindControlMsg) JSON.parseObject(json,WindControlMsg.class, Feature.AllowISO8601DateFormat);
		if(null!=windControlMsg && null!=windControlMsg.getRetCode()){
			if(!(WindControlMsg.RETCODE.CHECK_BY).equals(windControlMsg.getRetCode())){
				msgMap.put("error", windControlMsg.getRetMsg());
				return windControlMsg.getRetCode();
			}
		}else{
			msgMap.put("error", "接口异常");
			return windControlMsg.getRetCode();
		}
		 return windControlMsg.getRetCode();
	}
	
	public static final String getEncodeValue(String str) {
		if (isEmpty(str)) {
			return str;
		}
		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
	
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.length() == 0));
	}
	
	public static void main(String[] args){
		System.out.println(new SmsClient().sendSms("13695696244", "测试短信"));
	}
}
