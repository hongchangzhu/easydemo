package com.analysis.service;

import java.util.ArrayList;
import java.util.List;

import com.analysis.dao.SubjectDaoImpl;
import com.analysis.po.BookCatelog;
import com.analysis.po.Subject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SubjectServiceImpl {
	public void updateAll() {
		SubjectDaoImpl subjectDao = new SubjectDaoImpl();
		subjectDao.deleteAll();
		subjectDao.saveAll();
	}

	public String fromSubject2Json() {
		SubjectDaoImpl subjectDao = new SubjectDaoImpl();
		List<Subject> list = new ArrayList<Subject>();
		list = subjectDao.getAllSubject();
		Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		String jsonData = g.toJson(list);
		return jsonData;
	}

	public String fromBookCatelog2Json(String subjectId) {
		SubjectDaoImpl subjectDao = new SubjectDaoImpl();
		List<BookCatelog> list = new ArrayList<BookCatelog>();
		list = subjectDao.getAllBookCatelog(subjectId);
		Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		String jsonData = g.toJson(list);
		return jsonData;
	}

	public String getSubjectNameBySubjectId(String subjectId) {
		SubjectDaoImpl subjectDao = new SubjectDaoImpl();
		return subjectDao.getSubjectNameBySubjectId(subjectId);
	}

	public String getGradeNameByGradeId(String gradeId) {
		SubjectDaoImpl subjectDao = new SubjectDaoImpl();
		return subjectDao.getGradeNameByGradeId(gradeId);
	}
}
