package com.framework.dao.page;

/**
 * 产生不同数据库的分页sql
 * 
 */
public interface IPage {
	public static final int DEFAULT_PAGE = 1;
	public static final int DEFAULT_PAGE_COUNT = 10;

	String synthesisPage(String baseSQL, int from, int pageSize);

}
