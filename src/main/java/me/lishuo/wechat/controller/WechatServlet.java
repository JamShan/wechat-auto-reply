package me.lishuo.wechat.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.lishuo.wechat.util.HttpUtils;
import me.lishuo.wechat.util.SignUtil;

/**
 * wechat server
 * 
 */
public class WechatServlet extends HttpServlet {
	private static final long serialVersionUID = 4440739483644821986L;

	// auth
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String signature = HttpUtils.getParameter(request, "signature", "");
		String timestamp = HttpUtils.getParameter(request, "timestamp", "");
		String nonce = HttpUtils.getParameter(request, "nonce", "");
		String echostr = HttpUtils.getParameter(request, "echostr", "");

		PrintWriter out = response.getWriter();
		// auth
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	// process the msg from wechat
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		StringBuffer sb = new StringBuffer();
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String s = "";
		while ((s = br.readLine()) != null) {
			sb.append(s);
		}
		String xml = sb.toString(); 
		String result = "";
		result = new WechatProcess().processWechatMag(xml);
		try {
			OutputStream os = response.getOutputStream();
			os.write(result.getBytes("UTF-8"));
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
