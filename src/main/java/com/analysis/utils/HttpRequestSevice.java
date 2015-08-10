package com.analysis.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.analysis.po.QueryCondition;
import com.analysis.service.SubjectServiceImpl;

/**
 * 
 * @author Administrator
 * 
 */
public class HttpRequestSevice {
	public static final String URL = Config.getValue("store", "httpurl");;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public static String getResponseResult(QueryCondition cond) {
		String restype = cond.getRestype();
		String reststat = cond.getReststat();
		String subject = cond.getSubjectid2();
		String gradeId = cond.getClassid2();
		String term = cond.getWeek2();
		String date3 = cond.getDate3();
		String date4 = cond.getDate4();

		String method = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Date beginDate = null;
		Date endDate = null;

		SubjectServiceImpl serviceImpl = new SubjectServiceImpl();
		String subjectName = "";
		String gradeName = "";

		// 资源上传
		if ("1".equals(restype)) {
			// 资源上传条数
			if ("1".equals(reststat)) {
				method = "getUploadTotalCount";
			} else if ("2".equals(reststat)) {// 资源上传总的容量
				method = "getUploadTotalSize";
			}
			nvps.add(new BasicNameValuePair("method", method));

			if (date3 != null && !"".equals(date3)) {
				beginDate = DateTool.parseDate(date3, "yyyy-MM-dd");
				date3 = DateTool.DateToString(beginDate, "yyyy-MM-dd HH:mm:ss");
				nvps.add(new BasicNameValuePair("begin", date3));
			}
			if (date4 != null && !"".equals(date4)) {
				endDate = DateTool.parseDate(date4, "yyyy-MM-dd");
				date4 = DateTool.DateToString(endDate, "yyyy-MM-dd HH:mm:ss");
				nvps.add(new BasicNameValuePair("end", date4));
			}
		} else if ("2".equals(restype)) {// 资源初审
			if ("1".equals(reststat)) {// 按学科统计
				subjectName = serviceImpl.getSubjectNameBySubjectId(subject);
				nvps.add(new BasicNameValuePair("subject", subject));
				method = "getFCheckSizeBySubject";
				System.out.println("按学科统计初审资源，学科名称：" + subjectName);
			} else if ("2".equals(reststat)) {// 按年级统计
				subjectName = serviceImpl.getSubjectNameBySubjectId(subject);
				gradeName = serviceImpl.getGradeNameByGradeId(gradeId);
				nvps.add(new BasicNameValuePair("grade", gradeName));
				method = "getFCheckSizeByGrade";
				System.out.println("按年级统计初审资源，年级名称：" + gradeName);
			} else if ("3".equals(reststat)) {// 按周期统计
				nvps.add(new BasicNameValuePair("term", term));
				method = "getFCheckSizeByTerm";
				System.out.println("按周期统计初审资源，期数：" + term);
			} else if ("4".equals(reststat)) {// 按时间统计
				method = "getFCheckSizeByTime";
				if (date3 != null && !"".equals(date3)) {
					beginDate = DateTool.parseDate(date3, "yyyy-MM-dd");
					date3 = DateTool.DateToString(beginDate, "yyyy-MM-dd HH:mm:ss");
					nvps.add(new BasicNameValuePair("begin", date3));
				}
				if (date4 != null && !"".equals(date4)) {
					endDate = DateTool.parseDate(date4, "yyyy-MM-dd");
					date4 = DateTool.DateToString(endDate, "yyyy-MM-dd HH:mm:ss");
					nvps.add(new BasicNameValuePair("end", date4));
				}
			}
		} else if ("3".equals(restype)) {// 资源复审
			if ("1".equals(reststat)) {// 按学科统计
				subjectName = serviceImpl.getSubjectNameBySubjectId(subject);
				nvps.add(new BasicNameValuePair("subject", subjectName));
				method = "getRCheckSizeBySubject";
				System.out.println("按学科统计复审资源，学科名称：" + subjectName);
			} else if ("2".equals(reststat)) {// 按年级统计
				subjectName = serviceImpl.getSubjectNameBySubjectId(subject);
				gradeName = serviceImpl.getGradeNameByGradeId(gradeId);
				nvps.add(new BasicNameValuePair("grade", gradeName));
				method = "getRCheckSizeByGrade";
				System.out.println("按年级统计复审资源，年级名称：" + gradeName);
			} else if ("3".equals(reststat)) {// 按周期统计
				nvps.add(new BasicNameValuePair("term", term));
				method = "getRCheckSizeByTerm";
				System.out.println("按周期统计复审资源，期数：" + term);
			} else if ("4".equals(reststat)) {// 按时间统计
				method = "getRCheckSizeByTime";
				if (date3 != null && !"".equals(date3)) {
					beginDate = DateTool.parseDate(date3, "yyyy-MM-dd");
					date3 = DateTool.DateToString(beginDate, "yyyy-MM-dd HH:mm:ss");
					nvps.add(new BasicNameValuePair("begin", date3));
				}
				if (date4 != null && !"".equals(date4)) {
					endDate = DateTool.parseDate(date4, "yyyy-MM-dd");
					date4 = DateTool.DateToString(endDate, "yyyy-MM-dd HH:mm:ss");
					nvps.add(new BasicNameValuePair("end", date4));
				}
			}
		} else if ("4".equals(restype)) {// 调度任务

		}
		String resultStr = null;
		try {
			HttpPost httpPost = new HttpPost(URL);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(httpPost);
			Integer status = httpResponse.getStatusLine().getStatusCode();
			if (status == 200) {
				resultStr = EntityUtils.toString(httpResponse.getEntity());
				// System.out.println(resultStr);
			}
			httpclient.getConnectionManager().shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
		resultStr = resultStr.replace("}", "");
		resultStr += ", \"subjectName\":\"" + subjectName + "\", \"gradeName\":\"" + gradeName + "\"}";
		return resultStr;
	}
}
