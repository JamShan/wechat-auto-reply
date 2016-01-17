package me.lishuo.wechat.xmlProcess;

import java.util.Date;
import java.util.List;

import me.lishuo.wechat.domain.TuwenMsg;

/**
 *
 */
public class FormatXmlProcess {
	/**
	 * text msg
	 * 
	 * @param to
	 * @param from
	 * @param content
	 * @return
	 */
	public String formatMsgXmlAnswer(String to, String from, String content) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(to);
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(from);
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
		sb.append(content);
		sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
		return sb.toString();
	}

	/**
	 * tuwen msg
	 * 
	 * @param to
	 * @param from
	 * @param list
	 * @return
	 */
	public String formatTuwenXmlAnswer(String to, String from, List<TuwenMsg> list) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(to);
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(from);
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[news]]></MsgType>");
		sb.append("<ArticleCount>" + list.size() + "</ArticleCount>");
		for (TuwenMsg tuwen : list) {
			sb.append("<item><Title><![CDATA[" + tuwen.getArticle() + "]]></Title>");
			sb.append("<Description><![CDATA[" + tuwen.getSource() + "]]></Description>");
			sb.append("<PicUrl><![CDATA[" + tuwen.getDetailurl() + "]]></PicUrl>");
			sb.append("<Url><![CDATA[" + tuwen.getDetailurl() + "]]></Url></item>");
		}
		sb.append("<FuncFlag>0</FuncFlag></xml>");
		return sb.toString();
	}
}
