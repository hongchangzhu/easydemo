package com.analysis.po;

import java.io.Serializable;
import java.math.BigDecimal;

public class BookCatelog implements Serializable {
	private String parentId;
	private String bookCatelogName;
	private String bookCatelogId;
	private String versionId;
	private String subjectId;
	private BigDecimal seqNo;

	/**
	 * @return the seqNo
	 */
	public BigDecimal getSeqNo() {
		return seqNo;
	}

	/**
	 * @param seqNo
	 *            the seqNo to set
	 */
	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the bookCatelogName
	 */
	public String getBookCatelogName() {
		return bookCatelogName;
	}

	/**
	 * @param bookCatelogName
	 *            the bookCatelogName to set
	 */
	public void setBookCatelogName(String bookCatelogName) {
		this.bookCatelogName = bookCatelogName;
	}

	/**
	 * @return the bookCatelogId
	 */
	public String getBookCatelogId() {
		return bookCatelogId;
	}

	/**
	 * @param bookCatelogId
	 *            the bookCatelogId to set
	 */
	public void setBookCatelogId(String bookCatelogId) {
		this.bookCatelogId = bookCatelogId;
	}

	/**
	 * @return the versionId
	 */
	public String getVersionId() {
		return versionId;
	}

	/**
	 * @param versionId
	 *            the versionId to set
	 */
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	/**
	 * @return the subjectId
	 */
	public String getSubjectId() {
		return subjectId;
	}

	/**
	 * @param subjectId
	 *            the subjectId to set
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
}
