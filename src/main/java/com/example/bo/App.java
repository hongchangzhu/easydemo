package com.example.bo;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.framework.dao.IBean;

public class App implements IBean {
	private int FAppID;
	private String FAppName;
	private Integer FDomainID;
	private String FNote;
	private String FWhite3gNo;
	private String FBlacklist3gNo;// 黑名单
	private Integer FAppType;
	private String FAppAdress;
	private Integer FDefaultAppTab;
	private Integer FAutoAppTab;
	private Integer FIsFree;// 是否免费，0是，1不是，默认免费
	private Integer FAllowConfig;// 是否允许企业配置应用，1是，0否,默认否,只有FAutoAppTab=1才起作用
	private Integer FAutoAppCenter;// 是否默认添加到应用中心，0：否；1：是
	private Integer FUCanDelete;// 是否允许用户删除,0：否，1：是
	private Integer FAllowConfigTab;// 是否允许客户配置应用页签，0否，1是

	public Integer getFAllowConfigTab() {
		return FAllowConfigTab;
	}

	public void setFAllowConfigTab(Integer fAllowConfigTab) {
		FAllowConfigTab = fAllowConfigTab;
	}

	public Integer getFUCanDelete() {
		return FUCanDelete;
	}

	public void setFUCanDelete(Integer fUCanDelete) {
		FUCanDelete = fUCanDelete;
	}

	public Integer getFAutoAppCenter() {
		return FAutoAppCenter;
	}

	public void setFAutoAppCenter(Integer fAutoAppCenter) {
		FAutoAppCenter = fAutoAppCenter;
	}

	public String getFBlacklist3gNo() {
		return FBlacklist3gNo;
	}

	public void setFBlacklist3gNo(String fBlacklist3gNo) {
		FBlacklist3gNo = fBlacklist3gNo;
	}

	public Integer getFAllowConfig() {
		return FAllowConfig;
	}

	public void setFAllowConfig(Integer fAllowConfig) {
		FAllowConfig = fAllowConfig;
	}

	public Integer getFIsFree() {
		return FIsFree;
	}

	public void setFIsFree(Integer fIsFree) {
		FIsFree = fIsFree;
	}

	public String getFAppAdress() {
		return FAppAdress;
	}

	public void setFAppAdress(String fAppAdress) {
		FAppAdress = fAppAdress;
	}

	private String FPubCloudKey;

	public Integer getFAppType() {
		return FAppType;
	}

	public void setFAppType(Integer fAppType) {
		FAppType = fAppType;
	}

	public String getFPubCloudKey() {
		return FPubCloudKey;
	}

	public void setFPubCloudKey(String fPubCloudKey) {
		FPubCloudKey = fPubCloudKey;
	}

	public int getFAppID() {
		return FAppID;
	}

	public void setFAppID(int fAppID) {
		FAppID = fAppID;
	}

	public String getFAppName() {
		return FAppName;
	}

	public void setFAppName(String fAppName) {
		FAppName = fAppName;
	}

	public Integer getFDomainID() {
		return FDomainID;
	}

	public void setFDomainID(Integer fDomainID) {
		FDomainID = fDomainID;
	}

	public String getFNote() {
		return FNote;
	}

	public void setFNote(String fNote) {
		FNote = fNote;
	}

	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}

	public void setFWhite3gNo(String fWhite3gNo) {
		FWhite3gNo = fWhite3gNo;
	}

	public String getFWhite3gNo() {
		return FWhite3gNo;
	}

	public Integer getFDefaultAppTab() {
		return FDefaultAppTab;
	}

	public void setFDefaultAppTab(Integer fDefaultAppTab) {
		FDefaultAppTab = fDefaultAppTab;
	}

	public Integer getFAutoAppTab() {
		return FAutoAppTab;
	}

	public void setFAutoAppTab(Integer fAutoAppTab) {
		FAutoAppTab = fAutoAppTab;
	}

}
