package com.analysis.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.analysis.po.QueryCondition;
import com.analysis.service.TermServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GetTermServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("gbk");
		resp.setContentType("text/html; charset=gbk");
		String submitJSON = req.getParameter("submitData");
		Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		QueryCondition cond = g.fromJson(submitJSON,
				new TypeToken<QueryCondition>() {
				}.getType());
		TermServiceImpl serviceImpl = new TermServiceImpl();
		serviceImpl.updateTermByRegion(cond);
		resp.getWriter().write("1");
	}

}
