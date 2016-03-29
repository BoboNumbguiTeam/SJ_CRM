package com.hits.modules.msg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

/**
 * Http 请求工具类
 * 
 */
public class HttpClientUtil {
	private static Logger log = Logger.getLogger(HttpClientUtil.class);

	/**
	 * post 请求
	 * 
	 * @param url
	 * @param request
	 *            请求报文
	 * @return
	 */
	public static String httpPost(String url, String request) {
		log.info("request:" + request);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("request", request);

		return httpPost(url, paramMap);
	}
	
	/**
	 * get 请求
	 * 
	 * @param url
	 * @param request
	 *            请求报文
	 * @return
	 */
	public static String httpGet(String url, String request) {
		log.info("request:" + request);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("request", request);

		return httpGet(url, paramMap);
	}
	
	/**
	 * get 请求
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String httpAdminGet(String url,Map<String,Object> param){
		log.info("URL:" + url);
		if (url == null || url.trim().length() == 0) {
			return null;
		}
		HttpClient httpClient = new HttpClient();
		StringBuffer params = new StringBuffer();
		for(Entry<String, Object> entry : param.entrySet()){
			params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		GetMethod get = new GetMethod(url+"?"+params);
		get.releaseConnection();
		String response=null;
		try {
			httpClient.executeMethod(get);
			response = new String(get.getResponseBody(), "UTF-8");
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * get 请求
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String httpGet(String url,Map<String,Object> param){
		log.info("URL:" + url);
		if (url == null || url.trim().length() == 0) {
			return null;
		}
		HttpClient httpClient = new HttpClient();
		GetMethod get = new GetMethod(url+"?"+param.get("request"));
		get.releaseConnection();
		String response=null;
		try {
			httpClient.executeMethod(get);
			response = new String(get.getResponseBody(), "UTF-8");
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * post 请求
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String httpPost(String url, Map<String, Object> paramMap) {
		log.info("URL:" + url);

		if (url == null || url.trim().length() == 0) {
			return null;
		}

		HttpClient httpClient = new HttpClient();
		// 设置header
		// httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,
		// "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");//
		
		PostMethod method = new PostMethod(url);
		method.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		if (paramMap != null && paramMap.size() > 0) {
			Iterator<String> it = paramMap.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				Object o = paramMap.get(key);
				if (o != null && o instanceof String) {
					method.addParameter(new NameValuePair(key, o.toString()));
				}
				if (o != null && o instanceof String[]) {
					String[] s = (String[]) o;
					if (s != null) {
						for (int i = 0; i < s.length; i++) {
							method.addParameter(new NameValuePair(key, s[i]));
						}
					}
				}
			}
		}
		String content = null;
		try {
			int statusCode = httpClient.executeMethod(method);
			log.info("httpClientUtils::statusCode=" + statusCode);
			log.info("statusLine:" + method.getStatusLine());
			content = new String(method.getResponseBody(), "UTF-8");
			log.info("response:" + content);
		} catch (Exception e) {
			log.error("time out", e);
			return content;
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
			method = null;
			httpClient = null;
		}
		return content;
	}
	
	/**
	 * post 请求
	 * 
	 * @param url
	 * @param request
	 *            请求报文
	 * @return
	 */
	public static String TShttpPost(String url, String request) {
		log.info("request:" + request);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("request", request);

		return TShttpPost(url, paramMap);
	}
	
	/**
	 * post 请求
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String TShttpPost(String url, Map<String, Object> paramMap) {
		log.info("URL:" + url);

		if (url == null || url.trim().length() == 0) {
			return null;
		}

		HttpClient httpClient = new HttpClient();
		// 设置header
		// httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,
		// "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");//

		PostMethod method = new PostMethod(url);
		method.setRequestHeader("Content-Type", "text/xml; charset=utf-8");
		if (paramMap != null && paramMap.size() > 0) {
			Iterator<String> it = paramMap.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				Object o = paramMap.get(key);
				if (o != null && o instanceof String) {
					method.addParameter(new NameValuePair(key, o.toString()));
				}
				if (o != null && o instanceof String[]) {
					String[] s = (String[]) o;
					if (s != null) {
						for (int i = 0; i < s.length; i++) {
							method.addParameter(new NameValuePair(key, s[i]));
						}
					}
				}
			}
		}
		String content = null;
		try {
			int statusCode = httpClient.executeMethod(method);
			log.info("httpClientUtils::statusCode=" + statusCode);
			log.info("statusLine:" + method.getStatusLine());
			content = new String(method.getResponseBody(), "UTF-8");
			log.info("response:" + content);
		} catch (Exception e) {
			log.error("time out", e);
			return content;
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
			method = null;
			httpClient = null;
		}
		return content;
	}
}
