package com.example.action;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}
