package com.analysis.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.analysis.dao.RegionDaoImpl;
import com.analysis.dao.TermResourceDaoImpl;
import com.analysis.excel.ExcelBuilder;
import com.analysis.po.QueryCondition;
import com.analysis.po.StatResult;
import com.analysis.utils.Query;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ExportDataServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// req.setCharacterEncoding("gbk");
		// resp.setContentType("text/html; charset=gbk");
		String submitJSON = req.getParameter("submitData");
		// System.out.println(submitJSON);
		Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		QueryCondition cond = g.fromJson(submitJSON,
				new TypeToken<QueryCondition>() {
				}.getType());
		cond.setIsExport("1");
		ExcelBuilder eb = new ExcelBuilder();
		HSSFWorkbook wb = eb.createExcel(this.getDataRes(cond), this
				.getTitle(cond));

		eb.downloadExcel(wb, resp, this.getFileName(cond));
	}

	private String[] getTitle(QueryCondition cond) {
		String opttype = cond.getOpttypeid();
		String[] titles = new String[3];
		titles[0] = "���";
		titles[1] = "��Դ���";
		if ("1".equals(opttype)) {
			titles[2] = "��Դ�ѽ���";
		} else if ("2".equals(opttype)) {
			titles[1] = "�ն�վ�����";
			titles[2] = "δ����";
		} else if ("3".equals(opttype)) {
			titles[2] = "�����";
		} else if ("4".equals(opttype)) {
			titles[2] = "������";
		}
		return titles;
	}

	private String getFileName(QueryCondition cond) {
		String countryId = cond.getCountryid();
		String cityId = cond.getCityid();
		String provinceId = cond.getProvinceid();
		StringBuffer sb = new StringBuffer();
		RegionDaoImpl regionDaoImpl = new RegionDaoImpl();
		if (provinceId != null && !"".equals(provinceId.trim())) {
			sb.append(regionDaoImpl.getRegion(provinceId).getNodeName());
		} else if (cityId != null && !"".equals(cityId.trim())) {
			sb.append("-")
					.append(regionDaoImpl.getRegion(cityId).getNodeName());
		} else if (countryId != null && !"".equals(countryId.trim())) {
			sb.append("-").append(
					regionDaoImpl.getRegion(countryId).getNodeName());
		}
		sb.append("�ն�վ��ͳ�ƻ���.xls");
		return sb.toString();
	}

	public List getDataRes(QueryCondition cond) {
		TermResourceDaoImpl daoImpl = new TermResourceDaoImpl();
		Query query = new Query();
		String sql = query.getSql(cond);
		List condParams = query.getCondParams();

		List<StatResult> list = daoImpl.stat(sql, condParams);
		// System.out.println("*******************" + list.size());
		Object[] objs = null;
		List aList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			StatResult rs = list.get(i);
			objs = new Object[3];
			objs[0] = new BigDecimal(i + 1);
			objs[1] = rs.getName();
			objs[2] = rs.getTotalNum();
			aList.add(objs);
		}
		return aList;
	}
}
