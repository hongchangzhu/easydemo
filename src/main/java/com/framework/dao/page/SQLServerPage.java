package com.framework.dao.page;

/**
 * sqlserver2000的分页
 * 
 */
public class SQLServerPage extends BasePage {

	public String getPage(String baseSQL, int from, int pageSize) {
		return baseSQL;
	}

}
