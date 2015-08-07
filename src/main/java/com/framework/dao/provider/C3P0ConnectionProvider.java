package com.framework.dao.provider;

import java.sql.Connection;

import javax.sql.DataSource;

/**
 * 基于C3P0连接池的连接获取器
 * 
 * 
 */
public class C3P0ConnectionProvider implements ConnectionProvider {

	private DataSource ds;

	public DataSource getDataSource() {
		return ds;
	}

	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
	}

	public Connection getConnection() throws Exception {
		return ds.getConnection();
	}

}
