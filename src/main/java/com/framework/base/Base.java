package com.framework.base;

import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base implements IBase {
	private static Logger logger = LoggerFactory.getLogger(Base.class);

	public void logError(Object error, Object errorCode, Object message) {
		logger.error(error + "[" + errorCode + "/" + message + "]");
	}

	public void logError(Object obj, Throwable e) {
		String msg = null;
		if (obj != null) {
			msg = obj.toString();
		}
		logger.error(msg, e);
	}

	public void logInfo(Object obj) {
		if (obj == null) {
			return;
		}
		logger.info(obj.toString());
	}

	public void addMessage(String error, int errorCode, String message, Map<String, Object> map) throws Exception {
		map.put("error", error);
		map.put("errorCode", errorCode);
		logger.info(error + "[" + errorCode + "/" + message + "]");
	}

	public void addError(String error, int errorCode, String message, Map<String, Object> map) throws Exception {
		map.put("success", false);
		map.put("error", error);
		map.put("errorCode", errorCode);
		logger.error(error + "[" + errorCode + "/" + message + "]");
	}

	/**
	 * 将share或者xt返回的错误信息进行转换后返回
	 * 
	 * @param jsonObject
	 *            调用接口返回的错误信息
	 * @param logMessage
	 *            具体的错误描述
	 * @param map
	 * @throws Exception
	 */
	public void addError(JSONObject jsonObject, String logMessage, Map<String, Object> map) throws Exception {
		if (jsonObject.isNullObject()) {
			addError("error is null", 100, "错误信息为空!", map);
		}
		String error = null;
		if (jsonObject.has("error"))
			error = (String) jsonObject.get("error");
		int errorCode = 0;
		if (jsonObject.has("error_Code"))
			errorCode = (Integer) jsonObject.get("error_Code");
		if (jsonObject.has("errorCode"))
			errorCode = (Integer) jsonObject.get("errorCode");
		if (jsonObject.has("error_description"))
			logMessage = (String) jsonObject.get("error_description");
		addError(error, errorCode, logMessage, map);
	}

	/**
	 * 对传入的jsonobject处理后返回一个jsonobject
	 * 
	 * @param jsonObject
	 *            调用接口返回的错误信息
	 * @param logMessage
	 *            具体的错误描述
	 * @param map
	 * @throws Exception
	 */
	public void addErrorJson(JSONObject input, String logMessage, JSONObject output) throws Exception {
		if (input.isNullObject()) {
			output.put("error", "input error is null");
			output.put("errorCode", "100");
			output.put("error_description", logMessage);
		}
		String error = null;
		if (input.has("error"))
			error = (String) input.get("error");
		int errorCode = 0;
		if (input.has("error_Code"))
			errorCode = (Integer) input.get("error_Code");
		if (input.has("errorCode"))
			errorCode = (Integer) input.get("errorCode");
		if (input.has("error_description"))
			logMessage = (String) input.get("error_description");
		output.put("success", false);
		output.put("error", error);
		output.put("errorCode", errorCode);
		output.put("error_description", logMessage);
	}

}
