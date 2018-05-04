package com.eastrobot.robotdev.service.task.impl;

import com.eastrobot.robotdev.service.task.ITask;
import com.eastrobot.robotdev.utils.WeixinUtil;

public class SendWXtxtMsg implements ITask{
	
	private String msg;
	private String openId;
	
	public SendWXtxtMsg() {
		super();
	}

	public SendWXtxtMsg(String msg, String openId) {
		super();
		this.msg = msg;
		this.openId = openId;
	}

	@Override
	public void run() {
		WeixinUtil.pushTxtMsg(openId, msg);
	}
	
}
