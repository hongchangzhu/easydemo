package com.example.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.bo.App;
import com.framework.dao.CommonDao;

@Controller
@RequestMapping(value = "easyui")
public class EasyuiDemoController {
	private Logger log = Logger.getLogger(EasyuiDemoController.class);

	@Qualifier("commonDao")
	@Autowired
	private CommonDao commonDao;

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@RequestMapping(value = "forward", method = RequestMethod.GET)
	public ModelAndView forward() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("example/datagrid");
		return mav;
	}

	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_PAGE_COUNT = 10;

	@ResponseBody
	@RequestMapping(value = "query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public JSONObject query(HttpServletRequest request, @RequestParam(value = "page") int page,
			@RequestParam(value = "rows") int rows) {
		String submitData = request.getParameter("submitData");
		String queryParams = request.getParameter("queryParams");
		if (page <= 0)
			page = DEFAULT_PAGE;
		if (rows <= 0)
			rows = DEFAULT_PAGE_COUNT;
		int start = (page - 1) * rows;
		List<App> list = null;
		int count = 0;
		try {
			list = this.commonDao.queryBeans("select * from t_app ", App.class, start, rows);
			count = this.commonDao.queryIntegerValue("select count(*) from t_app ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		JSONObject data = JSONObject.fromObject(map);
		return data;
	}
}
