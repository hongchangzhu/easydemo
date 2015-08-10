package com.analysis.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.analysis.po.QueryCondition;
import com.analysis.service.FusionChartsService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class TermStatServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("gbk");
		response.setContentType("text/html; charset=gbk");

		String submitJSON = request.getParameter("submitData");
		//System.out.println(submitJSON);
		Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		QueryCondition cond = g.fromJson(submitJSON,
				new TypeToken<QueryCondition>(){
				}.getType());

		FusionChartsService chartsService = new FusionChartsService();
		response.getWriter().write(chartsService.getChartsXML(cond));
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		this.doGet(request, response);
	}

}
