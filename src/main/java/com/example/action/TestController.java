package com.example.action;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.base.BaseController;

@Controller
@RequestMapping(value = "tc")
public class TestController extends BaseController {
	private Logger log = Logger.getLogger(TestController.class);

	@RequestMapping(value = "stat")
	public String stat() {
		log.info("*********************");
		// return this.SUCCESS(new HashMap<String, Object>());
		return "test";
	}
	
	/**
	 * 
	 * @author chenhao
	 * @date 2015年8月11日 下午4:59:21
	 * @param map 封装了给前端的数据
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
