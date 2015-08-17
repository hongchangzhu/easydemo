package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.bo.App;
import com.example.dao.DemoDao;
import com.framework.dao.CommonDao;
import com.framework.dao.page.IPage;

/**
 * 业务类，使用spring注解@Service注册
 * 
 * @version 1.0
 */
@Service("demoService")
public class DemoService {
	@Qualifier("commonDao")
	@Autowired
	private CommonDao commonDao;

	@Autowired
	private DemoDao demoDao;

	public List<App> findAllApp(int page, int rows) throws Exception {
		if (page <= 0)
			page = IPage.DEFAULT_PAGE;
		if (rows <= 0)
			rows = IPage.DEFAULT_PAGE_COUNT;
		int start = (page - 1) * rows;
		List<App> list = this.demoDao.queryBeans("select * from t_app ", App.class, start, rows);
		return list;
	}

	public int count() throws Exception {
		int count = this.commonDao.queryIntegerValue("select count(*) from t_app ");
		return count;
	}
}
