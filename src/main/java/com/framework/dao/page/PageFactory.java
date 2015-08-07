package com.framework.dao.page;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.framework.dao.CommonDao;

/**
 * 根据数据库的类型获得分页的逻辑
 * 
 * 
 */
public class PageFactory {
	private static Logger logger = Logger.getLogger(PageFactory.class);
	private static int DBTYPE;

	static {

	}

	private PageFactory() {
	}

	public static IPage getPage(CommonDao dao) {
		return getInstance(dao);
	}

	private static IPage instance;

	private static IPage getInstance(CommonDao dao) {
		if (instance != null) {
			return instance;
		}
		switch (DBTYPE) {
		case 0: {// sql server
			String version = getSQLServerVersion(dao);
			if (!StringUtils.isBlank(version) && version.startsWith("8")) {
				instance = new SQLServerPage();
			}
			instance = new SQLServer2005Page();
			break;
		}
		case 1: // db2
		{
			instance = new DB2Page();
			break;
		}

		case 2:// oracle
		{
			instance = new OraclePage();
			break;
		}
		}
		return instance;
	}

	// 判断sql server的版本2000,2005,2008
	public static String getSQLServerVersion(CommonDao dao) {
		try {
			List<Map<String, Object>> data = dao.query("SELECT SERVERPROPERTY('productversion') as VERSIOIN");
			Map<String, Object> o = data.get(0);
			return String.valueOf(o.get("VERSIOIN"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
