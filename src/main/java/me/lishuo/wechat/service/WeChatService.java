package me.lishuo.wechat.service;

import me.lishuo.wechat.domain.ReceiveXmlEntity;
import me.lishuo.wechat.util.HttpUtils;
import me.lishuo.wechat.xmlProcess.FormatXmlProcess;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class WeChatService {

	
	/** 
	 * tuling robot
	 * you can visit 'http://www.tuling123.com/' for API key	
	 * @param info
	 * @return
	 */
	private static String tulingRobot(String info) {
		String url = "http://www.tuling123.com/openapi/api";
		String param = "key=********************&info=" + info;
		return HttpUtils.sendPost(url, param);
	}

	public static String getReplyMsg(ReceiveXmlEntity xmlEntity) {
		String info = xmlEntity.getContent();
		String msg = "";
		String jsonMsg = tulingRobot(info);
		JSONObject jsonObj = JSON.parseObject(jsonMsg);
		int code = (Integer) jsonObj.get("code");
		if (100000 == code) {
			msg = new FormatXmlProcess().formatMsgXmlAnswer(
					xmlEntity.getFromUserName(), xmlEntity.getToUserName(),
					(String) jsonObj.get("text"));
		} else if (302000 == code) {
			// TODO parse another xmlanswer
			msg = new FormatXmlProcess().formatMsgXmlAnswer(
					xmlEntity.getFromUserName(), xmlEntity.getToUserName(),
					"comming soon~~");
		}
		return msg;
	}
}
