package me.lishuo.wechat.controller;

import java.io.IOException;

import me.lishuo.wechat.domain.ReceiveXmlEntity;
import me.lishuo.wechat.service.WeChatService;
import me.lishuo.wechat.xmlProcess.ReceiveXmlProcess;

/**
 * wehcat xml msg proces
 *
 */
public class WechatProcess {

	/**
	 * parse xml and get response
	 * 
	 * @param xml wechat data
	 * @return response
	 * @throws IOException
	 */
	public String processWechatMag(String xml) throws IOException {

		// parse xml data
		ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);
		String result = "";
		if ("text".endsWith(xmlEntity.getMsgType())) {
			result = WeChatService.getReplyMsg(xmlEntity);
		}
		return result;
	}
}
