package com.example.action;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.bo.User;

@Controller
@RequestMapping(value = "user")
public class UserController {
	private Logger log = Logger.getLogger(EasyuiDemoController.class);

	@RequestMapping(value = "forward", method = RequestMethod.GET)
	public ModelAndView forward() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("example/usergrid");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public JSONObject query(HttpServletRequest request, @RequestParam(value = "page") int page,
			@RequestParam(value = "rows") int rows) {
		String submitData = request.getParameter("submitData");
		String queryParams = request.getParameter("queryParams");

		return null;
	}

	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public JSONObject add(HttpServletRequest request, @RequestBody User user) {
		log.info("user info:" + user);
		return null;
	}

	@RequestMapping(value = "insert", method = RequestMethod.GET)
	public ModelAndView insert() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("example/adduser");
		return mav;
	}
}
