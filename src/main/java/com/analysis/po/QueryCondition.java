package com.analysis.po;

import java.io.Serializable;

public class QueryCondition implements Serializable {
	private String provinceid;// 省id
	private String cityid;// 市id
	private String countryid;// 县id
	private String termid;// 终端id
	private String times;// 统计期间分类id
	private String weekyear;// 周期 年
	private String week;// 周
	private String semesteryear;// 学期 年
	private String semester;// 学期
	private String classid;// 年级id
	private String subjectid;// 学科id
	private String date1;
	private String date2;
	private String opttypeid;// 操作类型
	private String isExport = "0";// 0表示不是导出的条件，1表示是导出到excel的条件

	/** 作为后台管理的统计条件 start */
	private String restype;
	private String reststat;
	private String date3;
	private String date4;
	private String weekyear2;
	private String week2;
	private String classid2;// 年级id
	private String subjectid2;// 学科id

	/** 作为后台管理的统计条件 end */

	public String getRestype() {
		return restype;
	}

	public void setRestype(String restype) {
		this.restype = restype;
	}

	public String getReststat() {
		return reststat;
	}

	public void setReststat(String reststat) {
		this.reststat = reststat;
	}

	public String getDate3() {
		return date3;
	}

	public void setDate3(String date3) {
		this.date3 = date3;
	}

	public String getDate4() {
		return date4;
	}

	public void setDate4(String date4) {
		this.date4 = date4;
	}

	public String getWeekyear2() {
		return weekyear2;
	}

	public void setWeekyear2(String weekyear2) {
		this.weekyear2 = weekyear2;
	}

	public String getWeek2() {
		return week2;
	}

	public void setWeek2(String week2) {
		this.week2 = week2;
	}

	public String getIsExport() {
		return isExport;
	}

	public void setIsExport(String isExport) {
		this.isExport = isExport;
	}

	public String getOpttypeid() {
		return opttypeid;
	}

	public void setOpttypeid(String opttypeid) {
		this.opttypeid = opttypeid;
	}

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public String getDate2() {
		return date2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getCountryid() {
		return countryid;
	}

	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}

	public String getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	public String getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(String subjectid) {
		this.subjectid = subjectid;
	}

	public String getTermid() {
		return termid;
	}

	public void setTermid(String termid) {
		this.termid = termid;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getSemesteryear() {
		return semesteryear;
	}

	public void setSemesteryear(String semesteryear) {
		this.semesteryear = semesteryear;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getWeekyear() {
		return weekyear;
	}

	public void setWeekyear(String weekyear) {
		this.weekyear = weekyear;
	}

	public String getClassid2() {
		return classid2;
	}

	public void setClassid2(String classid2) {
		this.classid2 = classid2;
	}

	public String getSubjectid2() {
		return subjectid2;
	}

	public void setSubjectid2(String subjectid2) {
		this.subjectid2 = subjectid2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
