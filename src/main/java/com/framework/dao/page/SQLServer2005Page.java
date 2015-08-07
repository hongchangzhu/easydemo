package com.framework.dao.page;

/**
 * 使用rownumber<br>
 * 注意：<br>
 * 1.支持sqlserver 2005以上的数据库。 <br>
 * 2.sqlserver2005的"纯sql"的分页语句必须要有order by约束。<br>
 * 3.如果是sql2000"纯sql"只能用top语句在内存中分页（hibernate旧的SQLServerDialect做法)，大数据量时有问题 <br>
 * TO CONSIDER：为sqlserver 定制分页存储过程使用cursor方式分页。
 * 
 */

public class SQLServer2005Page extends BasePage {

	public String getPage(String baseSQL, int from, int pageSize) {
		return getLimitString(baseSQL, from, pageSize);
	}

	/**
	 * Add a LIMIT clause to the given SQL SELECT
	 * 
	 * The LIMIT SQL will look like:
	 * 
	 * WITH query AS (SELECT TOP last ROW_NUMBER() OVER (ORDER BY orderby) as
	 * __hibernate_row_nr__, ... original_query) SELECT * FROM query WHERE
	 * __hibernate_row_nr__ > offset ORDER BY __hibernate_row_nr__
	 * 
	 * @param querySqlString
	 *            The SQL statement to base the limit query off of.
	 * @param offset
	 *            Offset of the first row to be returned by the query
	 *            (zero-based)
	 * @param last
	 *            Maximum number of rows to be returned by the query
	 * @return A new SQL statement with the LIMIT clause applied.
	 */
	public String getLimitString(String querySqlString, int offset, int last) {
		/*
		 * WITH query AS (SELECT TOP last ROW_NUMBER() OVER (ORDER BY orderby)
		 * as __hibernate_row_nr__, ... original_query) SELECT * FROM query
		 * WHERE __hibernate_row_nr__ > offset ORDER BY __hibernate_row_nr__
		 */
		StringBuffer pagingBuilder = new StringBuffer();
		String orderby = getOrderByPart(querySqlString);
		String distinctStr = "";

		String loweredString = querySqlString.toLowerCase();
		String sqlPartString = querySqlString;
		if (loweredString.trim().startsWith("select")) {
			int index = 6;
			if (loweredString.startsWith("select distinct")) {
				distinctStr = "DISTINCT ";
				index = 15;
			}
			sqlPartString = sqlPartString.substring(index);
		}
		pagingBuilder.append(sqlPartString);

		// if no ORDER BY is specified use fake ORDER BY field to avoid errors
		if (orderby == null || orderby.length() == 0) {
			orderby = "ORDER BY CURRENT_TIMESTAMP";
		}

		StringBuffer result = new StringBuffer();
		result.append("WITH query AS (SELECT ").append(distinctStr).append("TOP ").append(offset + last)
				.append(" ROW_NUMBER() OVER (").append(orderby).append(") as __hibernate_row_nr__, ")
				.append(pagingBuilder).append(") SELECT * FROM query WHERE __hibernate_row_nr__ > ").append(offset)
				.append(" ORDER BY __hibernate_row_nr__");

		return result.toString();
	}

	static String getOrderByPart(String sql) {
		String loweredString = sql.toLowerCase();
		int orderByIndex = loweredString.indexOf("order by");
		if (orderByIndex != -1) {
			// if we find a new "order by" then we need to ignore
			// the previous one since it was probably used for a subquery
			return sql.substring(orderByIndex);
		} else {
			return "";
		}
	}

	public static void main(String[] args) {
		System.out.println(new SQLServer2005Page().getLimitString("select FID from T_3G_APPINFO", 10, 20));
	}

}
