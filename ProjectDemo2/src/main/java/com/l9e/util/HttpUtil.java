package com.l9e.util;

import org.apache.log4j.Logger;

import java.io.*;

public class HttpUtil {
	private static final Logger logger = Logger.getLogger(HttpUtil.class);

	/**
	 * 发送post请求
	 * 
	 * @param url
	 * @param params
	 * @param inputCharset
	 * @return
	 */
	public static String sendByPost(String url, String params,String readtimeout ,String connectiontimeout,String inputCharset) {

		StringBuffer strResponse = new StringBuffer(128);
		java.net.HttpURLConnection connection = null;
		java.net.URL reqUrl = null;
		OutputStreamWriter reqOut = null;
		InputStream in = null;
		BufferedReader br = null;
		int charCount = -1;
		if (connectiontimeout == null) {
			connectiontimeout = "30000";
		}
		if (readtimeout == null) {
			readtimeout = "30000";
		}
		try {
			reqUrl = new java.net.URL(url);
			connection = (java.net.HttpURLConnection) reqUrl.openConnection();
			connection.setConnectTimeout(Integer.parseInt(connectiontimeout));
			connection.setReadTimeout(Integer.parseInt(readtimeout));
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			reqOut = new OutputStreamWriter(connection.getOutputStream());
			reqOut.write(params);
			reqOut.flush();
			in = connection.getInputStream();
			br = new BufferedReader(new InputStreamReader(in, inputCharset));
			while ((charCount = br.read()) != -1) {
				strResponse.append((char) charCount);
			}
		} catch (Exception e) {
			logger.info("访问远程服务器超时：<" + reqUrl + ">" + e.getMessage(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (reqOut != null) {
					reqOut.close();
				}
			} catch (Exception e) {
				logger.info("访问远程服务器超时：<" + reqUrl + ">，关闭读取流异常" + e.getMessage(), e);
			}
		}
		//logger.debug("返回流:[" + strResponse.toString() + "]");
		return strResponse.toString();
	}

	/**
	 * 发送get请求
	 * 
	 * @param reqUrl
	 * @param inputCharset
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String sendByGet(String reqUrl, String connectiontimeout, String readtimeout, String inputCharset) {

		java.net.HttpURLConnection conn = null;
		InputStream in = null;
		BufferedReader rd = null;
		java.net.URL url = null;
		String tmp = null;
		if (connectiontimeout == null) {
			connectiontimeout = "30000";
		}
		if (readtimeout == null) {
			readtimeout = "30000";
		}

		try {
			url = new java.net.URL(reqUrl);
			conn = (java.net.HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(Integer.parseInt(connectiontimeout));
			conn.setReadTimeout(Integer.parseInt(readtimeout));
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			in = conn.getInputStream();
			rd = new BufferedReader(new InputStreamReader(in, inputCharset));
		} catch (IOException e) {
			logger.info("访问远程服务器超时：<" + reqUrl + ">" + e.getMessage(), e);
			try {
				if (rd != null)
					rd.close();
				if (in != null)
					in.close();
			} catch (IOException e1) {
				logger.info("访问远程服务器超时：<" + reqUrl + ">，关闭读取流异常" + e1.getMessage(), e1);
			}
			return null;
		}

		StringBuffer strResponse = new StringBuffer();
		try {
			while ((tmp = rd.readLine()) != null) {
				if (tmp.trim().length() > 0) {
					strResponse.append(tmp.trim());
				}
			}
		} catch (IOException e) {
			logger.info("读取远程服务器返回流异常：<" + reqUrl + ">" + e.getMessage(), e);
			try {
				if (rd != null)
					rd.close();
				if (in != null)
					in.close();
			} catch (IOException e1) {
				logger.info("读取远程服务器返回流后关闭读取流异常:<" + reqUrl + ">" + e1.getMessage(), e1);
			}
			return null;
		}

	//	logger.debug("返回流:[" + strResponse.toString() + "]");

		return strResponse.toString();
	}

}
