package com.analysis.utils;

import java.util.ArrayList;
import java.util.List;

import com.analysis.dao.RegionDaoImpl;
import com.analysis.po.QueryCondition;

public class Query {
	private List condParams = new ArrayList();

	public List getCondParams() {
		return condParams;
	}

	public void setCondParams(List condParams) {
		this.condParams = condParams;
	}

	public String getSql(QueryCondition cond) {
		String optType = cond.getOpttypeid();
		if (Constant.UN_BOOT_STRAP_TYPE.equals(optType)) {
			return this.getUnBootStrapSql(cond);
		} else
			return this.getTermResourceStatSql(cond);
	}

	/**
	 * 
	 * @功能：拼装统计查询sql
	 * 
	 * @param cond
	 * @return
	 */
	public String getTermResourceStatSql(QueryCondition cond) {
		String termId = cond.getTermid();
		String countryId = cond.getCountryid();
		String cityId = cond.getCityid();
		String provinceId = cond.getProvinceid();
		String codePath = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select t4.resource_id, t4.resource_name, t4.totalnum ");
		sb.append("from (select distinct t.resource_id, t0.resource_name, t.totalnum ");
		sb.append("from t_term_resource_relation_11 t0,");
		sb.append("(select t1.resource_id, sum(t1.opt_count) totalnum ");

		StringBuffer innerCond = new StringBuffer();
		innerCond.append("where t1.term_id = t2.term_id ");
		innerCond.append("and t2.regoin_id = t3.id ");
		innerCond.append("and t3.code_path like ? ");// 1
		innerCond.append("and t1.opt_type = ? ");// 2

		RegionDaoImpl regionDaoImpl = new RegionDaoImpl();
		if (countryId != null && !"".equals(countryId.trim())) {
			codePath = regionDaoImpl.getCodePath(countryId);
		} else if (cityId != null && !"".equals(cityId.trim())) {
			codePath = regionDaoImpl.getCodePath(cityId);
		} else if (provinceId != null && !"".equals(provinceId.trim())) {
			codePath = regionDaoImpl.getCodePath(provinceId);
		}
		condParams.add(codePath + "%");
		condParams.add(cond.getOpttypeid());

		if (termId != null && !"".equals(termId.trim())) {
			innerCond.append("and t1.term_id = ? ");
			condParams.add(termId);
		}

		String code = codePath.substring(0, 2);// 省编码
		// 定位表
		sb.append("from t_term_resource_relation_").append(code).append(" t1, t_term t2, t_region t3 ");

		String subjectId = cond.getSubjectid();
		if (subjectId != null && !"".equals(subjectId.trim())) {
			innerCond.append("and t1.subject_id = ? ");
			condParams.add(subjectId);
		}

		String classId = cond.getClassid();
		if (classId != null && !"".equals(classId.trim())) {
			innerCond.append("and t1.class_id = ? ");
			condParams.add(classId);
		}

		String date1 = null;
		String date2 = null;
		String times = cond.getTimes();// 按时间统计
		if (times != null && "1".equals(times.trim())) {// 按周期统计
			String weekyear = cond.getWeekyear();
			String week = cond.getWeek();
			// 年和期数要都有值才作为条件统计
			if (weekyear != null && !"".equals(weekyear.trim()) && week != null && !"".equals(week.trim())) {
				int yearnum = new Integer(weekyear).intValue();
				int weeknum = new Integer(week).intValue();
				date1 = DateTool.getFirstDayOfYearWeek(yearnum, weeknum);
				date2 = DateTool.getLastDayOfYearWeek(yearnum, weeknum);
				// innerCond.append("and to_char(t1.opt_time,'yyyy-mm-dd') >= ?
				// ");//oracle
				// innerCond.append("and to_char(t1.opt_time,'yyyy-mm-dd') <= ?
				// ");//oracle
				innerCond.append("and date_format(t1.opt_time,'%Y-%m-%d') >= ? ");// mysql
				innerCond.append("and date_format(t1.opt_time,'%Y-%m-%d') <= ? ");// mysql
				// date_format(t.birthday,'%Y-%m-%d')>='1987-01-21'; ;mysql
				condParams.add(date1);
				condParams.add(date2);
			}
		} else if (times != null && "2".equals(times.trim())) {// 按时间段统计
			date1 = cond.getDate1();
			date2 = cond.getDate2();
			if (date1 != null && !"".equals(date1.trim()) && date2 != null && !"".equals(date2.trim())) {
				// innerCond.append("and to_char(t1.opt_time,'yyyy-mm-dd') >= ?
				// ");//oracle
				// innerCond.append("and to_char(t1.opt_time,'yyyy-mm-dd') <= ?
				// ");//oracle
				innerCond.append("and date_format(t1.opt_time,'%Y-%m-%d') >= ? ");// mysql
				innerCond.append("and date_format(t1.opt_time,'%Y-%m-%d') <= ? ");// mysql
				condParams.add(date1);
				condParams.add(date2);
			}
		} else if (times != null && "3".equals(times.trim())) {// 按学期 统计
			String semesteryear = cond.getSemesteryear();
			String semester = cond.getSemester();
			if (semesteryear != null && !"".equals(semesteryear.trim()) && semester != null
					&& !"".equals(semester.trim())) {
				int year = new Integer(semesteryear).intValue();
				int nextyear = year + 1;
				// 上学期时间跨度为当年的8月1日到下年的1月31日
				if (Constant.UP_SEMESTER.equals(semester)) {
					date1 = year + "-08-01";
					date2 = (year + 1) + "-01-31";
				} else if (Constant.DOWN_SEMESTER.equals(semester)) {// 下学期时间跨度为下年的2月1日到7月31日
					date1 = (year + 1) + "-02-01";
					date2 = (year + 1) + "-07-31";
				}
				// innerCond.append("and to_char(t1.opt_time,'yyyy-mm-dd') >= ?
				// ");//oracle
				// innerCond.append("and to_char(t1.opt_time,'yyyy-mm-dd') <= ?
				// ");//oracle
				innerCond.append("and date_format(t1.opt_time,'%Y-%m-%d') >= ? ");// mysql
				innerCond.append("and date_format(t1.opt_time,'%Y-%m-%d') <= ? ");// mysql
				condParams.add(date1);
				condParams.add(date2);
			}
		}

		sb.append(innerCond);
		sb.append("group by t1.resource_id) t ");
		sb.append("where t0.resource_id = t.resource_id ");
		sb.append("order by t.totalnum desc) t4 ");
		// sb.append(" where rownum <= 10 ");oracle
		if ("0".equals(cond.getIsExport()))
			sb.append(" limit 10 ");// mysql

		return sb.toString();
	}

	/**
	 * 
	 * @功能：拼装统计未开机查询sql
	 * 
	 * @param cond
	 * @return
	 */
	public String getUnBootStrapSql(QueryCondition cond) {
		String countryId = cond.getCountryid();
		String cityId = cond.getCityid();
		String provinceId = cond.getProvinceid();
		String codePath = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select t0.term_id, t0.term_name, t4.tatolnum ");
		sb.append("from t_term t0,");
		sb.append("(select t1.term_id, count(t1.term_id) tatolnum ");
		sb.append("from t_un_boot_strap_term t1, t_term t2, t_region t3 ");

		StringBuffer innerCond = new StringBuffer();
		innerCond.append("where t1.term_id = t2.term_id ");
		innerCond.append("and t2.regoin_id = t3.id ");
		innerCond.append("and t3.code_path like ? ");

		RegionDaoImpl regionDaoImpl = new RegionDaoImpl();
		if (countryId != null && !"".equals(countryId.trim())) {
			codePath = regionDaoImpl.getCodePath(countryId);
		} else if (cityId != null && !"".equals(cityId.trim())) {
			codePath = regionDaoImpl.getCodePath(cityId);
		} else if (provinceId != null && !"".equals(provinceId.trim())) {
			codePath = regionDaoImpl.getCodePath(provinceId);
		}
		condParams.add(codePath + "%");

		String date1 = null;
		String date2 = null;
		String times = cond.getTimes();// 按时间统计
		if (times != null && "1".equals(times.trim())) {// 按周期统计
			String weekyear = cond.getWeekyear();
			String week = cond.getWeek();
			// 年和期数要都有值才作为条件统计
			if (weekyear != null && !"".equals(weekyear.trim()) && week != null && !"".equals(week.trim())) {
				int yearnum = new Integer(weekyear).intValue();
				int weeknum = new Integer(week).intValue();
				date1 = DateTool.getFirstDayOfYearWeek(yearnum, weeknum);
				date2 = DateTool.getLastDayOfYearWeek(yearnum, weeknum);
				// innerCond
				// .append("and to_char(t1.check_date,'yyyy-mm-dd') >= ?
				// ");//oracle
				// innerCond
				// .append("and to_char(t1.check_date,'yyyy-mm-dd') <= ?
				// ");//oracle
				innerCond.append("and date_format(t1.check_date,'%Y-%m-%d') >= ? ");// mysql
				innerCond.append("and date_format(t1.check_date,'%Y-%m-%d') <= ? ");// mysql
				condParams.add(date1);
				condParams.add(date2);
			}
		} else if (times != null && "2".equals(times.trim())) {// 按时间段统计
			date1 = cond.getDate1();
			date2 = cond.getDate2();
			if (date1 != null && !"".equals(date1.trim()) && date2 != null && !"".equals(date2.trim())) {
				// innerCond
				// .append("and to_char(t1.check_date,'yyyy-mm-dd') >= ?
				// ");//oracle
				// innerCond
				// .append("and to_char(t1.check_date,'yyyy-mm-dd') <= ?
				// ");//oracle
				innerCond.append("and date_format(t1.check_date,'%Y-%m-%d') >= ? ");// mysql
				innerCond.append("and date_format(t1.check_date,'%Y-%m-%d') <= ? ");// mysql
				condParams.add(date1);
				condParams.add(date2);
			}
		} else if (times != null && "3".equals(times.trim())) {// 按学期 统计
			String semesteryear = cond.getSemesteryear();
			String semester = cond.getSemester();
			if (semesteryear != null && !"".equals(semesteryear.trim()) && semester != null
					&& !"".equals(semester.trim())) {
				int year = new Integer(semesteryear).intValue();
				int nextyear = year + 1;
				// 上学期时间跨度为当年的8月1日到下年的1月31日
				if (Constant.UP_SEMESTER.equals(semester)) {
					date1 = year + "-08-01";
					date2 = (year + 1) + "-01-31";
				} else if (Constant.DOWN_SEMESTER.equals(semester)) {// 下学期时间跨度为下年的2月1日到7月31日
					date1 = (year + 1) + "-02-01";
					date2 = (year + 1) + "-07-31";
				}
				// innerCond
				// .append("and to_char(t1.check_date,'yyyy-mm-dd') >= ?
				// ");//oracle
				// innerCond
				// .append("and to_char(t1.check_date,'yyyy-mm-dd') <= ?
				// ");//oracle
				innerCond.append("and date_format(t1.check_date,'%Y-%m-%d') >= ? ");// mysql
				innerCond.append("and date_format(t1.check_date,'%Y-%m-%d') <= ? ");// mysql
				condParams.add(date1);
				condParams.add(date2);
			}
		}

		sb.append(innerCond);
		sb.append("group by t1.term_id) t4 ");
		sb.append("where t0.term_id = t4.term_id ");
		// sb.append("and rownum <= 10 ");//oracle
		sb.append("order by t4.tatolnum desc ");
		if ("0".equals(cond.getIsExport()))
			sb.append(" limit 10 ");// mysql
		return sb.toString();
	}
}
