/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.robotdev.config;

import java.io.Serializable;
import java.util.Date;



public class BaseBean implements Serializable {

	private String createUser;
	private Date createDate = new Date();
	private String modifyUser;
	private Date modifyDate = new Date();
	private Integer delFlag = 0;

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
}
