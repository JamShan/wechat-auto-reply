package me.lishuo.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author lis
 * 
 */

public class HttpUtils {

	public static int getParameter(HttpServletRequest request, String parameterName, int defaultValue) {
		String val = request.getParameter(parameterName);
		if (CommonUtils.isEmptyString(val))
			return defaultValue;
		try {
			return Integer.valueOf(val.trim()).intValue();
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public static long getParameter(HttpServletRequest request, String parameterName, long defaultValue) {
		String val = request.getParameter(parameterName);
		if (CommonUtils.isEmptyString(val))
			return defaultValue;
		try {
			return Long.valueOf(val.trim()).longValue();
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public static float getParameter(HttpServletRequest request, String parameterName, float defaultValue) {
		String val = request.getParameter(parameterName);
		if (CommonUtils.isEmptyString(val))
			return defaultValue;
		try {
			return Float.valueOf(val.trim()).floatValue();
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public static double getParameter(HttpServletRequest request, String parameterName, double defaultValue) {
		String val = request.getParameter(parameterName);
		if (CommonUtils.isEmptyString(val))
			return defaultValue;
		try {
			return Double.valueOf(val.trim()).doubleValue();
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public static boolean getParameter(HttpServletRequest request, String parameterName, boolean defaultValue) {
		String val = request.getParameter(parameterName);
		if (CommonUtils.isEmptyString(val))
			return defaultValue;
		try {
			val = val.trim().toLowerCase();

			return (val.equals("y")) || (val.equals("yes")) || (val.equals("true")) || (val.equals("1"));
		} catch (Exception e) {
		}

		return defaultValue;
	}

	public static String getParameter(HttpServletRequest request, String parameterName, String defaultValue) {
		String val = request.getParameter(parameterName);
		if (CommonUtils.isEmptyString(val)) {
			return defaultValue;
		}
		return val.trim();
	}

	public static String getRawParameter(HttpServletRequest request, String parameterName) {
		String val = request.getParameter(parameterName);
		if (CommonUtils.isEmptyString(val)) {
			return "";
		}
		return val.trim();
	}

	public static void buildJsonResponse(HttpServletResponse response, boolean status, String runtime, JSONArray result)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setStatus(200);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", Boolean.toString(status));
		jsonObj.put("runtime", runtime);
		jsonObj.put("result", result);
		response.getWriter().println(jsonObj.toString());
	}

	public static void buildStrResponse(HttpServletResponse response, boolean status, Object result)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setStatus(200);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", Boolean.toString(status));
		jsonObj.put("result", result);
		response.getWriter().println(jsonObj.toString());
	}

	public static void buildJsonResponse(HttpServletResponse response, boolean status, String errorMessage)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setStatus(200);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", Boolean.toString(status));
		jsonObj.put("errorMessage", errorMessage);
		response.getWriter().println(jsonObj.toString());
	}

	public static void buildJsonResponse(HttpServletResponse response, boolean status, String runtime, String request,
			String result) throws IOException {
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setStatus(200);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", Boolean.toString(status));
		jsonObj.put("runtime", runtime);
		jsonObj.put("request", request);
		jsonObj.put("result", result);
		response.getWriter().println(jsonObj.toString());
	}

	public static void buildErrorResponse(HttpServletResponse response, boolean status, String errorMessage)
			throws IOException {
		buildJsonResponse(response, false, errorMessage);
	}

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// open connection
			URLConnection conn = realUrl.openConnection();
			// set property
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// get URLConnection outputStream
			out = new PrintWriter(conn.getOutputStream());
			// send param
			out.print(param);
			// flush cache
			out.flush();
			// define BufferedReader to read URL response
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// close I/O
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
