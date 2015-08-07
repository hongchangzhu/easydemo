package com.framework.dao.page;

public class OraclePage extends BasePage {

	public String getPage(String baseSQL, int from, int pageSize) {
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT * FROM");
		sb.append(" ( ");
		sb.append(" SELECT A.*, ROWNUM ROWNUM__ ");
		sb.append(" FROM (");
		sb.append(baseSQL);
		sb.append(" )A");
		sb.append(" WHERE ROWNUM < ");
		sb.append(from + pageSize);
		sb.append(" ) ");
		sb.append(" WHERE ROWNUM__ >=");
		sb.append(from);

		return sb.toString();
	}
}
