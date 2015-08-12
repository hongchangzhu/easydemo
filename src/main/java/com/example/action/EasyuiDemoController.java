package com.example.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public String query() {
		List<Map<String, Object>> list = null;
		try {
			list = this.commonDao.query("select * from t_app ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("success", true);
		map.put("error", null);
		map.put("errorCode", 100);
		map.put("total", list.size());
		map.put("rows", list);

		String rtn = JSONObject.fromObject(map).toString();
		return rtn;
	}
}
