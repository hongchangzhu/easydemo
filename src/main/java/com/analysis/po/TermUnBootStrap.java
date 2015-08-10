package com.analysis.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @说明：未开机终端
 * 
 * @作者：陈昊
 * @日期：2014-4-12
 * @修改人：
 * @修改日期：
 */
public class TermUnBootStrap implements Serializable{
	private String termId;
	private String termName;
	private Date checkDate;

	public Date getCheckDate(){
		return checkDate;
	}

	public void setCheckDate(Date checkDate){
		this.checkDate = checkDate;
	}

	public String getTermId(){
		return termId;
	}

	public void setTermId(String termId){
		this.termId = termId;
	}

	public String getTermName(){
		return termName;
	}

	public void setTermName(String termName){
		this.termName = termName;
	}
}
