package com.analysis.po;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 终端
 * 
 * @说明：
 * 
 * @作者：陈昊
 * @日期：2014-4-3
 * @修改人：
 * @修改日期：
 */
public class Term implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1330629465665953818L;

	private String termId;// 终端/学校id
	private String termName;// 终端/学校名称
	private String regoinId;// 县/区id

	private String provinceId;// 省id
	private String cityId;// 市id
	private String schoolType;// 学校类型
	private BigDecimal vNo;// 版本号
	private String XXJGBXLXM3;

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

	public BigDecimal getVNo() {
		return vNo;
	}

	public void setVNo(BigDecimal no) {
		vNo = no;
	}

	public String getXXJGBXLXM3() {
		return XXJGBXLXM3;
	}

	public void setXXJGBXLXM3(String xxjgbxlxm3) {
		XXJGBXLXM3 = xxjgbxlxm3;
	}

	public String getRegoinId() {
		return regoinId;
	}

	public void setRegoinId(String regoinId) {
		this.regoinId = regoinId;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	public String toString() {
		return new ToStringBuilder(this).append("termId", getTermId()).append(
				"termName", getTermName()).append("regoinId", getRegoinId())
				.toString();
	}

	public int hashCode() {
		return super.hashCode();
	}
}
