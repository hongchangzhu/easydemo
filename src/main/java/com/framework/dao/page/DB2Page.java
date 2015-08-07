package com.framework.dao.page;

public class DB2Page extends BasePage {

	public String getPage(String baseSQL, int from, int to) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM (SELECT B.*, ROWNUMBER() OVER() AS HIBERNATENR FROM  ( ");
		sb.append(baseSQL);
		sb.append(") AS B )AS A WHERE A.HIBERNATENR BETWEEN ");
		sb.append(from);
		sb.append(" AND ");
		sb.append(to);
		return sb.toString();
	}
}
