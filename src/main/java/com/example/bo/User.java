package com.example.bo;

import com.framework.dao.IBean;

public class User implements IBean{
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
