package com.framework.dao.page;

public class MySQLPage extends BasePage {

	public String getPage(String baseSQL, int from, int to) {
		StringBuffer sb = new StringBuffer(baseSQL);
		sb.append(" limit ");
		sb.append(from);
		sb.append(" , ");
		sb.append(to);
		return sb.toString();
	}

}
