package com.red.crm;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.analysis.utils.Config;

public class MetadataServiceUtils {

	public static String URL = "https://meta.eduyun.cn/base/metadataServlet";
	public static String keyStorePw = "mis123mis";
	public static String trustStorePw = "mis123mis";
	public static String keyStoreFile = "D:\\metadata\\clientKeystore.jks";
	public static String trustStoreFile = "D:\\metadata\\clientTruststore.jks";
	public static String appName = "test";
	public static String appPassWord = "123456";
	public static Integer port = 443;
	static {
		keyStoreFile = Config.getValue("store", "keyStoreFile");
		trustStoreFile = Config.getValue("store", "trustStoreFile");
	}

	public static String getRemotData(Map<String, String> mapParams) {
		String resultStr = null;
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());

			FileInputStream keyStoreIn = new FileInputStream(new File(
					keyStoreFile));
			FileInputStream trustStoreIn = new FileInputStream(new File(
					trustStoreFile));
			try {
				keyStore.load(keyStoreIn, keyStorePw.toCharArray());
				trustStore.load(trustStoreIn, trustStorePw.toCharArray());
			} catch (Exception e) {
			} finally {
				keyStoreIn.close();
				trustStoreIn.close();
			}

			SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore,
					keyStorePw, trustStore);
			Scheme sch = new Scheme("https", port, socketFactory);
			httpclient.getConnectionManager().getSchemeRegistry().register(sch);
			HttpPost httpPost = new HttpPost(URL);
			System.out.println(httpPost.getRequestLine());
			HttpRequestBase httpRequest = null;
			AbstractHttpEntity entity = null;
			entity = new StringEntity(JSONObject.fromObject(mapParams)
					.toString());
			entity.setContentType("application/json");
			if (entity != null) {
				entity.setContentEncoding("UTF-8");
			}
			httpPost.setEntity(entity);
			httpPost.addHeader("charset", "UTF-8");
			httpRequest = httpPost;

			HttpResponse httpResponse = httpclient.execute(httpRequest);
			Integer status = httpResponse.getStatusLine().getStatusCode();
			if (status == 200) {
				resultStr = EntityUtils.toString(httpResponse.getEntity());
			}
			httpclient.getConnectionManager().shutdown();
			return resultStr;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultStr;
	}

	// ********************************************************************
	/**
	 * 0
	 * 
	 * @param strURL
	 * @return
	 */
	public static String getAreaList() {
		Map<String, String> mapParams = new LinkedHashMap<String, String>();

		mapParams.put("method", "getAreaList");
		mapParams.put("appName", appName);
		mapParams.put("appPassWord", appPassWord);
		return getRemotData(mapParams);
	}

	/**
	 * 
	 * 
	 * @param strURL
	 * @return
	 */
	public static String getAreaLog(String vNo) {
		Map<String, String> mapParams = new LinkedHashMap<String, String>();

		mapParams.put("method", "getAreaLog");
		mapParams.put("appName", appName);
		mapParams.put("appPassWord", appPassWord);
		mapParams.put("vNo", vNo);
		return getRemotData(mapParams);
	}

	/**
	 * 获取学校数据
	 * 
	 * @param strURL
	 * @return
	 */
	public static String getSchoolList(String areaId, String schoolType) {
		Map<String, String> mapParams = new LinkedHashMap<String, String>();

		mapParams.put("method", "getSchoolList");
		mapParams.put("appName", "djg-jxd");
		mapParams.put("appPassWord", "8433575");
		mapParams.put("areaId", areaId);
		mapParams.put("schoolType", schoolType);
		return getRemotData(mapParams);
	}

	/**
	 * 获取学科数据
	 * 
	 * @param areaId
	 * @param schoolType
	 * @return [ { "subjectName": "小学科学", "subjectId":
	 *         "402880e42e13953c012e139823860000" }, { "subjectName": "小学音乐",
	 *         "subjectId": "402880e42e13953c012e13983f8a0001" } ]
	 * 
	 */
	public static String getSubjectList() {
		Map<String, String> mapParams = new LinkedHashMap<String, String>();
		mapParams.put("method", "getSubjectList");
		mapParams.put("appName", appName);
		mapParams.put("appPassWord", appPassWord);
		return getRemotData(mapParams);
	}

	/**
	 * 获取教材版本列表
	 * 
	 * @param subjectId
	 * @return [ { "versionId": "A48D400A-8638-29D2-23CA-7147B2B8DB9A",
	 *         "versionName": "教科版（2001）" }, { "versionId":
	 *         "F9787500-C457-8634-53EA-19144DE0C376", "versionName": "苏教课标版" } ]
	 * 
	 */
	public static String getPubVerList(String subjectId) {
		Map<String, String> mapParams = new LinkedHashMap<String, String>();

		mapParams.put("method", "getPubVerList");
		mapParams.put("appName", appName);
		mapParams.put("appPassWord", appPassWord);
		mapParams.put("subjectId", subjectId);
		return getRemotData(mapParams);
	}

	/**
	 * 获取某教材章节,解析时只去parentId为-1的数据
	 * 
	 * @param versionId
	 * @return [ { "parentId": "-1", "bookCatelogName": "一年级", "bookCatelogId":
	 *         "7faa2586-975e-4d7d-8483-da43caad6e8e" }, { "parentId":
	 *         "7faa2586-975e-4d7d-8483-da43caad6e8e", "bookCatelogName":
	 *         "一年级上", "bookCatelogId": "78E80063-C1DD-646E-10C1-5045983761EF" }, {
	 *         "parentId": "78E80063-C1DD-646E-10C1-5045983761EF",
	 *         "bookCatelogName": "第一单元 我们爱科学", "bookCatelogId":
	 *         "B12111D3-59EB-801B-00F2-39E36319DB21" } ]
	 * 
	 */
	public static String getBookCat(String versionId) {
		Map<String, String> mapParams = new LinkedHashMap<String, String>();

		mapParams.put("method", "getBookCat");
		mapParams.put("appName", appName);
		mapParams.put("appPassWord", appPassWord);
		mapParams.put("versionId", versionId);
		return getRemotData(mapParams);
	}
}
