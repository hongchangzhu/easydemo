package com.analysis.service;

import java.util.ArrayList;
import java.util.List;

import com.analysis.dao.RegionDaoImpl;
import com.analysis.po.Regoin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RegoinServiceImpl {

	/**
	 * 
	 * @功能：获取默认行政区划id
	 * 
	 * @return
	 */
	public String getDefaultRegoinId() {
		RegionDaoImpl daoImpl = new RegionDaoImpl();
		Regoin regoin = daoImpl.getDefaultRegoin();
		if (regoin == null || regoin.getId() == null)
			return "";
		return regoin.getId();
	}

	/**
	 * 
	 * @功能：更新行政区划表的数据，先删除再插入
	 * 
	 */
	public int updateAll() {
		RegionDaoImpl daoImpl = new RegionDaoImpl();
		// 先删除再插入全部
		daoImpl.deleteAll();
		return daoImpl.saveAll();
	}

	/**
	 * 
	 * @功能：对象转化为json
	 * 
	 * @param pid
	 * @return
	 */
	public String fromObject2Json(String pid) {
		RegionDaoImpl daoImpl = new RegionDaoImpl();
		List<Regoin> list = daoImpl.getUnderline(pid);
		List<Regoin> all = new ArrayList<Regoin>();
		Regoin r = new Regoin();
		r.setId("-1");
		r.setNodeName("全部");
		all.add(r);
		all.addAll(list);
		Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		String jsonData = g.toJson(list);
		return jsonData;
	}
}
