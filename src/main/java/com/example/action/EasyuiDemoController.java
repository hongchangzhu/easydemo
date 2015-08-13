package com.example.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
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

	@ResponseBody
	@RequestMapping(value = "query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public JSONObject query(HttpServletRequest request, @RequestParam(value = "page") int page,
			@RequestParam(value = "rows") int rows) {
		List<Map<String, Object>> list = null;
		try {
			list = this.commonDao.query("select * from t_app ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		JSONArray array = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("FAppID", 1);
		jsonObj.put("FAppName", "测试");
		array.add(jsonObj);

		List<Map<String, Object>> subList = list.subList(0, 10);
		map.put("total", list.size());
		map.put("rows", subList);
		String rtn = JSONObject.fromObject(map).toString();
		JSONObject jo = JSONObject.fromObject(rtn);

		JSONObject data = new JSONObject();
		data.put("total", list.size());
		data.put("rows", array);

		return data;
	}
}
