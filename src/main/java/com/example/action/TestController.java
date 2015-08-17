package com.example.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.framework.base.BaseController;

@Controller
@RequestMapping(value = "tc")
public class TestController extends BaseController {
	private Logger log = Logger.getLogger(TestController.class);

	@RequestMapping(value = "stat", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ModelAndView stat(@RequestBody List<Map<String, Object>> list) {
		log.info("*********************");
		Map<String, Object> map = new HashMap<String, Object>();
		HttpHeaders httpHeaders = new HttpHeaders();
		// ResponseEntity<String> responseEntity = new
		// ResponseEntity<String>("Hello WY!", httpHeaders, HttpStatus.CREATED);
		// return responseEntity;
		ModelAndView mav = new ModelAndView();
		mav.addObject("data", "");
		mav.addObject("success", true);
		// return this.SUCCESS(map);
		return mav;
	}

	/**
	 * 输入 和输出为JSON格式的数据的方式
	 * 
	 * @author chenhao
	 * @date 2015年8月17日 下午3:03:52
	 * @param session
	 * @param list
	 * @return
	 * @throws ServletRequestBindingException
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap query(HttpSession session, @RequestBody List<Map<String, Object>> list)
			throws ServletRequestBindingException {
		log.info("=================使用ResponseBody输出====================");
		ModelMap modelMap = new ModelMap();
		modelMap.put("success", true);
		modelMap.put("data", list);

		return modelMap;
	}

	/**
	 * 
	 * @author chenhao
	 * @date 2015年8月11日 下午4:59:21
	 * @param map
	 *            封装了给前端的数据
	 * @return
	 */
	@RequestMapping(value = "/pages/reg4")
	public String reg4(ModelMap map) {
		System.out.println("BaseController.handleRequest()");
		map.put("name", "king");
		return "test";
		// return "redirect:http://www.kingdee.com";
	}
}
