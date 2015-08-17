package com.example.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.bo.App;
import com.example.service.DemoService;

@Controller
@RequestMapping(value = "easyui")
public class EasyuiDemoController {
	private Logger log = Logger.getLogger(EasyuiDemoController.class);

	@Autowired
	private DemoService demoService;

	@RequestMapping(value = "forward", method = RequestMethod.GET)
	public ModelAndView forward() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("example/datagrid");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public JSONObject query(HttpServletRequest request, @RequestParam(value = "page") int page,
			@RequestParam(value = "rows") int rows) {
		String submitData = request.getParameter("submitData");
		String queryParams = request.getParameter("queryParams");

		List<App> list = null;
		int count = 0;
		try {
			list = demoService.findAllApp(page, rows);
			count = demoService.count();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		log.info("查询到应用数据：" + count);

		JSONObject data = JSONObject.fromObject(map);
		return data;
	}
}
