package com;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.framework.utils.http.HttpHelper;

public class HttpClientTest {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException {
		restotal();
	}

	public static void restotal() throws ClientProtocolException, IOException {
		String URL = "http://localhost/easydemo/stat/resourceActivity";

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("macid", "aaa");
		jsonObj.put("resname", "陈昊");
		jsonObj.put("restotal", 5);
		jsonObj.put("adddate", "2015-08-13");
		jsonArray.add(jsonObj);

		jsonObj = new JSONObject();
		jsonObj.put("macid", "bbb");
		jsonObj.put("resname", "陈昊2");
		jsonObj.put("restotal", 68);
		jsonObj.put("adddate", "2015-08-13");
		jsonArray.add(jsonObj);

		String json = jsonArray.toString();
		try {
			String resultStr = HttpHelper.post(null, json, URL);
			System.out.println("***response:" + resultStr);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// �ն���Դ�������ͳ�ƽӿ�
	public static void resclickcount() throws ClientProtocolException, IOException {
		String URL = "http://localhost:8080/statAnalysis/stat/termClickCount";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(URL);
		HttpRequestBase httpRequest = null;
		AbstractHttpEntity entity = null;

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("macid", "aaa");
		jsonObj.put("clicktotal", 89);
		jsonObj.put("adddate", "2015-08-13");
		jsonArray.add(jsonObj);

		jsonObj = new JSONObject();
		jsonObj.put("macid", "bbb");
		jsonObj.put("clicktotal", 45);
		jsonObj.put("adddate", "2015-08-13");
		jsonArray.add(jsonObj);

		String json = jsonArray.toString();
		try {
			entity = new StringEntity(json);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// entity.setContentType("application/json");
		entity.setContentType("application/x-www-form-urlencoded");
		if (entity != null) {
			entity.setContentEncoding("UTF-8");
		}
		httpPost.setEntity(entity);
		httpPost.addHeader("charset", "UTF-8");
		httpRequest = httpPost;

		HttpResponse httpResponse = httpclient.execute(httpRequest);
		Integer status = httpResponse.getStatusLine().getStatusCode();
		String resultStr = null;
		if (status == 200) {
			resultStr = EntityUtils.toString(httpResponse.getEntity());
			System.out.println("***response:" + resultStr);
		}
	}

	// �����ǰ5����Դͳ�ƽӿ�
	public static void hotres() throws ClientProtocolException, IOException {
		String URL = "http://localhost:8080/statAnalysis/stat/resourceActivity";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(URL);
		HttpRequestBase httpRequest = null;
		AbstractHttpEntity entity = null;

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("macid", "aaa");
		jsonObj.put("resid", "1");
		jsonObj.put("resname", "001 ��һ��ʱ");
		jsonObj.put("respath", "��Դ·��");
		jsonObj.put("clickcount", 89);
		jsonObj.put("adddate", "2015-08-14");
		jsonArray.add(jsonObj);

		jsonObj = new JSONObject();
		jsonObj.put("macid", "bbb");
		jsonObj.put("resid", "2");
		jsonObj.put("resname", "001 ��һ��ʱ");
		jsonObj.put("respath", "��Դ·��");
		jsonObj.put("clickcount", 15);
		jsonObj.put("adddate", "2015-08-14");
		jsonArray.add(jsonObj);

		String json = jsonArray.toString();
		try {
			entity = new StringEntity(json);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entity.setContentType("application/json");
		// entity.setContentType("application/x-www-form-urlencoded");
		if (entity != null) {
			entity.setContentEncoding("UTF-8");
		}
		httpPost.setEntity(entity);
		httpPost.addHeader("charset", "UTF-8");
		httpRequest = httpPost;

		HttpResponse httpResponse = httpclient.execute(httpRequest);
		Integer status = httpResponse.getStatusLine().getStatusCode();
		String resultStr = null;
		if (status == 200) {
			resultStr = EntityUtils.toString(httpResponse.getEntity());
			System.out.println("***response:" + resultStr);
		}
	}

}
