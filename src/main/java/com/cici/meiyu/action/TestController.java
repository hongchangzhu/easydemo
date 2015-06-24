package com.cici.meiyu.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("testController")
public class TestController {

	@RequestMapping(value = "/easyui", method = RequestMethod.POST, consumes = "application/json")
	public String easyui() {
		return null;
	}
}
