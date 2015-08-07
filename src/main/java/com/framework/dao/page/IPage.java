package com.framework.dao.page;

/**
 * 产生不同数据库的分页sql
 * 
 */
public interface IPage {

	String synthesisPage(String baseSQL, int from, int pageSize);

}
