package com.analysis.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 行政区划
 * 
 * @说明：
 * 
 * @作者：陈昊
 * @日期：2014-4-3
 * @修改人：
 * @修改日期：
 */
public class Regoin implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4582072633812661971L;

	private String id;
	private String nodeName;
	private String parentId;
	private BigDecimal seqNo;
	private String nationalCode;
	private String nodeType;
	private BigDecimal versionNo;
	private String codePath;
	private BigDecimal codeLevel;
	private List regionSet = new ArrayList();

	public List getRegionSet(){
		return regionSet;
	}

	public BigDecimal getCodeLevel(){
		return codeLevel;
	}

	public void setCodeLevel(BigDecimal codeLevel){
		this.codeLevel = codeLevel;
	}

	public String getCodePath(){
		return codePath;
	}

	public void setCodePath(String codePath){
		this.codePath = codePath;
	}

	public void setRegionSet(List regionSet){
		this.regionSet = regionSet;
	}

	public void addRegionSet(Regoin region){
		this.regionSet.add(region);
	}

	public String getParentId(){
		return parentId;
	}

	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getNodeName(){
		return nodeName;
	}

	public void setNodeName(String nodeName){
		this.nodeName = nodeName;
	}

	public BigDecimal getSeqNo(){
		return seqNo;
	}

	public void setSeqNo(BigDecimal seqNo){
		this.seqNo = seqNo;
	}

	public String getNationalCode(){
		return nationalCode;
	}

	public void setNationalCode(String nationalCode){
		this.nationalCode = nationalCode;
	}

	public String getNodeType(){
		return nodeType;
	}

	public void setNodeType(String nodeType){
		this.nodeType = nodeType;
	}

	public BigDecimal getVersionNo(){
		return versionNo;
	}

	public void setVersionNo(BigDecimal versionNo){
		this.versionNo = versionNo;
	}

	public String toString(){
		return new ToStringBuilder(this).append("id", getId()).append(
				"nodeName", getNodeName()).append("parentId", getParentId())
				.append("versionNo", getVersionNo()).toString();
	}

	public int hashCode(){
		return super.hashCode();
	}

}
