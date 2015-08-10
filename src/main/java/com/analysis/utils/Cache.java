package com.analysis.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 应用系统自定义缓存类
 * 
 * @author DELL
 */
@SuppressWarnings("unchecked")
public class Cache extends HashMap {
	private static final long serialVersionUID = 1L;
	private String name;
	private int maxSize;
	public static Cache instance;
	private static Map stat;
	static {
		stat = new HashMap();
		instance = getCache("default", 1000);
	}

	/**
	 * 获取当前的应用系统缓存对象
	 * 
	 * @param name
	 *            ：缓存名称
	 * @param maxSize
	 *            ：缓存大小
	 * @return
	 */
	public static Cache getCache(String name, int maxSize) {
		Cache cache = null;
		if (stat.containsKey(name)) {
			cache = (Cache) stat.get(name);
		} else {
			cache = new Cache(name, maxSize);
		}
		return cache;
	}

	/**
	 * 构造函数私有化，严禁外部实例化该类
	 * 
	 * @param name
	 * @param i
	 */
	private Cache(String name, int i) {
		super();
		this.name = name;
		this.maxSize = i;
	}

	/**
	 * 设置应用缓存数据内容
	 * 
	 * @param key
	 *            ：应用缓存名称
	 * @param value
	 *            ：缓存内容
	 * @return
	 */
	public Object set(Object key, Object value) {
		if (this.size() >= this.maxSize) {
			Iterator it = this.keySet().iterator();
			while (it.hasNext() && this.size() >= this.maxSize) {
				this.remove(it.next());
				it = this.keySet().iterator();
			}
			it = null;
		}
		return this.put(key, value);
	}
}
