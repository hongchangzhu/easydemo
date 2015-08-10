package com.analysis.service;

import java.util.List;

import com.analysis.dao.RegionDaoImpl;
import com.analysis.dao.TermDaoImpl;
import com.analysis.po.QueryCondition;
import com.analysis.po.Regoin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TermServiceImpl {
	public String fromObject2Json(String regionId) {
		TermDaoImpl dao = new TermDaoImpl();
		List list = dao.getAllTermByRegionId(regionId);
		Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		String jsonData = g.toJson(list);
		return jsonData;
	}

	/**
	 * 更终端划表的数据，先删除再插入
	 * 
	 * @param regionId
	 *            县id
	 * @param schoolType
	 */
	public void updateAll(String regionId, String schoolType) {
		TermDaoImpl daoImpl = new TermDaoImpl();
		daoImpl.deleteAll(regionId, schoolType);
		daoImpl.saveAll(regionId, schoolType);
	}

	public void updateTermByRegion(QueryCondition cond) {
		String countryId = cond.getCountryid();
		String cityId = cond.getCityid();
		String provinceId = cond.getProvinceid();
		// System.out.println(provinceId);
		String schoolType = "JXD";
		RegionDaoImpl daoImpl = new RegionDaoImpl();
		List<Regoin> list = null;
		if (countryId != null && !"".equals(countryId.trim())) {
			this.updateAll(countryId, schoolType);// 县
		} else if (cityId != null && !"".equals(cityId.trim())) {
			// 找出市下的所有县
			list = daoImpl.getUnderline(cityId);
			for (Regoin country : list) {
				this.updateAll(country.getId(), schoolType);
			}
		} else if (provinceId != null && !"".equals(provinceId.trim())) {// 找出省下的所有县
			list = daoImpl.getAllCountryByProvinceId(provinceId);
			for (Regoin country : list) {
				this.updateAll(country.getId(), schoolType);
			}
		} else {// 找出所有的县
			list = daoImpl.getAllCountry();
			for (Regoin country : list) {
				this.updateAll(country.getId(), schoolType);
			}
		}
	}
}
