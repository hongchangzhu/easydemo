package com.framework.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.PropertyPlaceholderHelper;

/**
 * 读取配置文件，继承spring的PropertyPlaceholderConfigurer类，扩展了可以在代码中显示调用以获取配置值功能
 * 
 * @date 2015年8月18日 上午10:02:37
 * @version 1.0
 */
public class BasePropertyConfigurer extends PropertyPlaceholderConfigurer {
	private static Map<String, String> properties = new HashMap<String, String>();
	private static BasePropertyConfigurer instance = new BasePropertyConfigurer();

	private BasePropertyConfigurer() {

	}

	public static BasePropertyConfigurer getInstance() {
		return instance;
	}

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(DEFAULT_PLACEHOLDER_PREFIX,
				DEFAULT_PLACEHOLDER_SUFFIX, DEFAULT_VALUE_SEPARATOR, false);
		for (Entry<Object, Object> entry : props.entrySet()) {
			String stringKey = String.valueOf(entry.getKey());
			String stringValue = String.valueOf(entry.getValue());
			stringValue = helper.replacePlaceholders(stringValue, props);
			properties.put(stringKey, stringValue);
		}
		super.processProperties(beanFactoryToProcess, props);
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public String getString(String key) {
		return properties.get(key);
	}
}
