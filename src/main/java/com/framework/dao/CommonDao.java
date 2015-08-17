package com.framework.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.support.JdbcUtils;

import com.framework.dao.page.IPage;
import com.framework.dao.provider.ConnectionProvider;
import com.framework.utils.ArrayUtil;

/**
 * 数据库操作的工具类 <br>
 * note:特定数据库的sql需要控制在各个dao中 <br>
 * 对jdbc的轻量封装
 * 
 * 
 */
public class CommonDao {
	public static final Logger logger = Logger.getLogger(CommonDao.class);

	// 页属性
	@Qualifier("dao-page")
	@Autowired
	private IPage page;
	// 连接管理器
	private static ConnectionProvider connectionProvider;

	public CommonDao() {
	}

	// 默认连接的处理方法
	public static Connection getConnection() throws Exception {
		return connectionProvider.getConnection();
	}

	public static void closeConnection(Connection conn) throws Exception {
		if (conn != null) {
			conn.close();
		}
	}

	/**
	 * 查询单一的实体对象
	 * 
	 * @param <T>
	 * @param sql
	 * @param clazz
	 * @return
	 */
	public <T extends IBean> T querySingleBean(String sql, Class<T> clazz) throws Exception {
		return this.querySingleBean(sql, new Object[0], clazz);
	}

	public <T extends IBean> T querySingleBean(String sql, Object[] params, Class<T> clazz) throws Exception {
		List<T> beans = this.queryBeans(sql, clazz, 0, 0, params);
		if (beans == null || beans.isEmpty()) {
			return null;
		}
		return beans.get(0);
	}

	public <T extends IBean> T querySingleBean(String sql, Class<T> clazz, Object... params) throws Exception {
		if (params == null) {
			return querySingleBean(sql, (Object[]) null, clazz);
		}
		return querySingleBean(sql, params, clazz);
	}

	/**
	 * 支持分页的查询
	 * 
	 * @param <T>
	 * @param sql
	 * @param clazz
	 * @param offset
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public <T extends IBean> List<T> queryBeans(String sql, Class<T> clazz, int offset, int limit) throws Exception {
		return queryBeans(sql, (Object[]) null, clazz, offset, limit);
	}

	/**
	 * 
	 * @param <T>
	 * @param sql
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T extends IBean> List<T> queryBeans(String sql, Class<T> clazz) throws Exception {
		return this.queryBeans(sql, new Object[0], clazz);
	}

	public <T extends IBean> List<T> queryBeans(String sql, Object[] params, Class<T> clazz, int offset, int limit)
			throws Exception {
		Connection conn = CommonDao.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<T> result = null;
		String pageSQL = formatPageSQL(sql, offset, limit);
		try {
			ps = conn.prepareStatement(pageSQL);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			rs = ps.executeQuery();

			ResultSetMetaData RSMD = rs.getMetaData();
			int colCount = RSMD.getColumnCount();
			result = new ArrayList<T>();

			// 所有方法
			Method[] methods = clazz.getMethods();

			while (rs.next()) {
				T bean = (T) clazz.newInstance();
				for (int i = 1; i <= colCount; i++) {
					Object value = rs.getObject(i);
					if (value == null) {// 忽略掉值为null的字段
						continue;
					}
					Method method = null;
					try {
						String name = RSMD.getColumnName(i);
						method = this.getMethod("set", methods, name);
						if (method == null) {
							name = RSMD.getColumnLabel(i);
							method = this.getMethod("set", methods, name);
						}
					} catch (Exception e) {
					}
					if (method == null) { // 找不到对应的方法，则忽略掉该字段
						// TD:是否需要记录下日志
						continue;
					}
					// value的类型 通过不同的jdbc驱动返回的类型有可能不大一样，此调用setter方法有可能就会出现匹配错误
					// 这里需要有个智能决策的逻辑
					Class[] arguments = method.getParameterTypes();
					if (arguments.length != 1) {// 参数值必须有且只有一个
						continue;
					}
					// 进行可能的类型转化
					Object convertedValue = JdbcUtils.getResultSetValue(rs, i, arguments[0]);
					// Object convertedValue =
					// typeConvertor.convert(arguments[0], value);
					// 调用setter方法设置值
					method.invoke(bean, convertedValue);
				}
				result.add(bean);
			}
		} finally {
			cleanup(rs, ps);
			closeConnection(conn);
		}
		return result;
	}

	// 根据数据库的列名返回指定的方法，忽略大小写
	protected Method getMethod(String methodPrefix, Method[] all, String colname) {
		for (Method method : all) {
			String name = method.getName();
			if (name.startsWith(methodPrefix)) {
				name = name.substring(methodPrefix.length());
				if (name.equalsIgnoreCase(colname)) {
					return method;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public <T extends IBean> List<T> queryBeans(String sql, Object[] params, Class<T> clazz) throws Exception {
		return queryBeans(sql, params, clazz, 0, 0);
	}

	public <T extends IBean> List<T> queryBeans(String sql, Class<T> clazz, Object... params) throws Exception {
		return queryBeans(sql, params, clazz, 0, 0);
	}

	public <T extends IBean> List<T> queryBeans(String sql, Class<T> clazz, int offset, int limit, Object... params)
			throws Exception {
		return queryBeans(sql, params, clazz, offset, limit);
	}

	/**
	 * 查询
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> query(String sql) throws Exception {
		return this.query(sql, new Object[0], null);
	}

	public List<Map<String, Object>> query(String sql, int from, int pageSize) throws Exception {
		String pageSQL = formatPageSQL(sql, from, pageSize);
		return this.query(pageSQL, new Object[0], null);
	}

	public List<Map<String, Object>> query(String sql, String[] colnameDefines) throws Exception {
		return this.query(sql, new Object[0], colnameDefines);
	}

	public List<Map<String, Object>> query(String sql, String[] colnameDefines, int from, int pageSize)
			throws Exception {
		String pageSQL = formatPageSQL(sql, from, pageSize);
		return this.query(pageSQL, new Object[0], colnameDefines);
	}

	public List<Map<String, Object>> query(String sql, Object[] params) throws Exception {
		return this.query(sql, params, null);
	}

	public List<Map<String, Object>> query(String sql, Object[] params, int from, int pageSize) throws Exception {
		String pageSQL = formatPageSQL(sql, from, pageSize);
		return this.query(pageSQL, params, null);
	}

	/**
	 * 查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> query(String sql, Object[] params, String[] colnameDefines) throws Exception {
		Connection conn = CommonDao.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, Object>> result = null;
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			rs = ps.executeQuery();
			ResultSetMetaData RSMD = rs.getMetaData();
			int colCount = RSMD.getColumnCount();
			result = new LinkedList<Map<String, Object>>();
			while (rs.next()) {
				Map<String, Object> bean = new HashMap<String, Object>();
				for (int i = 1; i <= colCount; i++) {
					Object value = rs.getObject(i);
					String name = RSMD.getColumnName(i);
					// map中存储自定义的列名，而不是数据库返回的名字，此解决不同数据库返回的大小写问题
					if (colnameDefines != null) {
						for (String dcolname : colnameDefines) {
							if (dcolname.equalsIgnoreCase(name)) {
								name = dcolname;
								break;
							}
						}
					} else {// 如果不指定列定义（colnameDefines），则以大写列明返回，客户程序在调用时由此可不关系数据库列明的大小写问题
						bean.put(name.toUpperCase(), value);
					}
					bean.put(name, value);
					String columnLabel = RSMD.getColumnLabel(i);
					if (!name.equals(columnLabel)) {
						name = columnLabel;
						if (colnameDefines != null) {
							for (String dcolname : colnameDefines) {
								if (dcolname.equalsIgnoreCase(name)) {
									name = dcolname;
									break;
								}
							}
						} else {
							bean.put(name.toUpperCase(), value);
						}
						bean.put(name, value);
					}
				}
				result.add(bean);
			}
		} finally {
			cleanup(rs, ps);
			closeConnection(conn);
		}
		return result;
	}

	public List<Map<String, Object>> query(String sql, Object[] params, String[] colnameDefines, int from, int pageSize)
			throws Exception {
		String pageSQL = formatPageSQL(sql, from, pageSize);
		return query(pageSQL, params, colnameDefines);

	}

	/**
	 * 查询数据库中的单一整形字段
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */

	public int queryIntegerValue(String sql, Object[] params) throws Exception {
		List<Map<String, Object>> result = this.query(sql, params, null, 0, 1);
		if (result == null || result.isEmpty()) {
			return -1;
		}
		Map<String, Object> data = result.get(0);
		Object[] ret = data.values().toArray();
		if (ret.length == 0 || ret[0] == null) {
			return -1;
		}
		return ((Number) ret[0]).intValue();
	}

	public int queryIntegerValue(String sql) throws Exception {
		return this.queryIntegerValue(sql, new Object[0]);
	}

	public int count(String sql) throws Exception {
		return this.queryIntegerValue(sql, new Object[0]);
	}

	public int count(String sql, Object[] params) throws Exception {
		return this.queryIntegerValue(sql, params);

	}

	/**
	 * 执行数据的更新操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int execute(String sql) throws Exception {
		return this.execute(sql, new Object[0]);
	}

	public int execute(String sql, Object[] params) throws Exception {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			return ps.executeUpdate();
		} finally {
			cleanup(ps);
			closeConnection(conn);
		}
	}

	/**
	 * 执行删除操作
	 * 
	 * @param tableName
	 * @param conditions
	 * @param params
	 * @throws Exception
	 */
	public int delete(String tableName, String conditions, Object... params) throws Exception {
		StringBuffer sql = new StringBuffer("DELETE FROM ");
		sql.append(tableName);
		sql.append(" WHERE ");
		sql.append(conditions);
		return this.execute(sql.toString(), params);

	}

	/**
	 * 在数据库中创建一个对象： 使用约定优于配置的方式 <br>
	 * 1.所有的get属性的字段与数据库中对应 <br>
	 * 2.表名与实体名对应(或者需要将表名传递进来)<br>
	 * 
	 * @param <T>
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public <T extends IBean> int createBean(T bean, String tableName) throws Exception {
		// 执行sql
		return createBean(bean, tableName, true);
	}

	public <T extends IBean> int createBean(T bean, String tableName, boolean ignoreNull) throws Exception {
		List<Object> params = new ArrayList<Object>();
		List<Object> fieldArr = new ArrayList<Object>();
		List<Object> valueArr = new ArrayList<Object>();
		if (bean instanceof Map) {
			Map map = (Map) bean;
			Set<Entry> entrySet = (Set<Entry>) map.entrySet();
			for (Entry e : entrySet) {
				Object value = e.getValue();
				if (value == null && ignoreNull) {
					continue;
				}
				fieldArr.add(e.getKey());
				valueArr.add("?");
				params.add(value);
			}
		} else {
			Class clazz = bean.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field f : fields) {
				if (Modifier.isStatic(f.getModifiers())) {
					continue;
				}
				String name = f.getName();
				Method method = null;
				String capitalize = StringUtils.capitalize(name);
				try {
					method = clazz.getMethod("get" + capitalize);
				} catch (Exception e) {
				}
				if (method == null) {
					Class<?> type = f.getType();
					if (type == Boolean.class || type == boolean.class) {
						try {
							method = clazz.getMethod("is" + capitalize);
						} catch (Exception e) {
						}
					}
				}
				if (method == null) {
					continue;
				}
				Object value = method.invoke(bean, new Object[0]);
				if (value == null && ignoreNull) {
					continue;
				}
				fieldArr.add(name);
				valueArr.add("?");
				params.add(value);
			}
		}

		if (params.size() == 0) {
			return 0;
		}

		StringBuffer sb = new StringBuffer();
		sb.append(" INSERT INTO ");
		sb.append(tableName);
		sb.append("(");
		sb.append(ArrayUtil.join(fieldArr, ","));
		sb.append(") VALUES(");
		sb.append(ArrayUtil.join(valueArr, ","));
		sb.append(")");

		return this.execute(sb.toString(), params.toArray());
	}

	public <T extends IBean> int createBean(T bean) throws Exception {
		return this.createBean(bean, bean.getClass().getSimpleName());
	}

	/**
	 * 
	 * @param <T>
	 * @param bean
	 * @param idcol
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public <T extends IBean> int updateBean(T bean, String idcol, String tableName) throws Exception {
		// 分析bean中的数据
		Object id = null;
		if (bean instanceof Map) {
			Map map = (Map) bean;
			id = map.get(idcol);
		} else {
			Class clazz = bean.getClass();
			Method method = getMethod("get", clazz.getMethods(), idcol);
			id = method.invoke(bean, new Object[] {});
		}
		return updateBean(bean, tableName, (idcol + "=?"), new Object[] { id });
	}

	/**
	 * 
	 * @param <T>
	 * @param bean
	 *            实体
	 * @param tableName
	 *            表名
	 * @param whereSql
	 *            sql中的where段
	 * @param whereParams
	 *            where的?的值
	 * @return
	 * @throws Exception
	 */
	public <T extends IBean> int updateBean(T bean, String tableName, String whereSql, Object[] whereParams)
			throws Exception {
		return updateBean(bean, tableName, whereSql, whereParams, true);
	}

	/**
	 * 更新实体
	 * 
	 * @param <T>
	 * @param bean
	 *            实体
	 * @param tableName
	 *            表名
	 * @param whereSql
	 *            sql中的where段
	 * @param whereParams
	 *            where的?的值
	 * @param ignoreNull
	 *            是否过滤null值的属性字段
	 * @return
	 * @throws Exception
	 */
	public <T extends IBean> int updateBean(T bean, String tableName, String whereSql, Object[] whereParams,
			boolean ignoreNull) throws Exception {
		// 分析bean中的数据
		StringBuffer sb = new StringBuffer();
		sb.append(" update ");
		sb.append(tableName);
		sb.append(" set ");
		List<Object> params = new ArrayList<Object>();
		List<String> fv = new ArrayList<String>();

		if (bean instanceof Map) {
			Map map = (Map) bean;
			Set<Entry> entrySet = (Set<Entry>) map.entrySet();
			for (Entry e : entrySet) {
				Object value = e.getValue();
				if (value == null && ignoreNull) {
					continue;
				}
				fv.add(e.getKey() + "=?");
				params.add(value);
			}
		} else {
			Class clazz = bean.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field f : fields) {
				if (Modifier.isStatic(f.getModifiers())) {
					continue;
				}
				String name = f.getName();
				Method method = null;
				String capitalize = StringUtils.capitalize(name);
				try {
					method = clazz.getMethod("get" + capitalize);
				} catch (Exception e) {
				}
				if (method == null) {
					Class<?> type = f.getType();
					if (type == Boolean.class || type == boolean.class) {
						try {
							method = clazz.getMethod("is" + capitalize);
						} catch (Exception e) {
						}
					}
				}
				if (method == null) {
					continue;
				}
				Object value = method.invoke(bean, new Object[0]);
				if (value == null && ignoreNull) {
					continue;
				}
				fv.add(name + "=?");
				params.add(value);
			}
		}

		sb.append(ArrayUtil.join(fv, ","));
		sb.append(" where ");
		sb.append(whereSql);
		params.addAll(Arrays.asList(whereParams));
		return this.execute(sb.toString(), params.toArray());
	}

	public static ConnectionProvider getConnectionProvider() {
		return connectionProvider;
	}

	public void setConnectionProvider(ConnectionProvider connectionProvider) {
		CommonDao.connectionProvider = connectionProvider;
	}

	public IPage getPage() {
		return page;
	}

	public void setPage(IPage page) {
		this.page = page;
	}

	public void cleanup(Statement stat) {
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				// ignore
			}
		}
	}

	public void cleanup(ResultSet rs, Statement stat) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// ignore
			}
		}
		cleanup(stat);
	}

	/**
	 * 格式化分页的SQL语句
	 * 
	 * @param sql
	 * @param offset
	 * @param limit
	 * @return
	 */
	private String formatPageSQL(String sql, int offset, int limit) {
		if (offset < 0) {
			offset = 0;
		}
		if (limit < 0) {
			limit = 0;
		}
		if (offset == 0 && limit == 0) {
			return sql;
		}
		if (offset > 0 && limit == 0) {
			limit = Integer.MAX_VALUE;
		}
		return page.synthesisPage(sql, offset, limit);
	}

	/**
	 * 辅助方法，打印出RS中的所有内容
	 * 
	 * @param rs
	 * @throws Exception
	 */
	protected void printRS(ResultSet rs) throws Exception {
		rs.getMetaData();
		ResultSetMetaData RSMD = rs.getMetaData();
		int colCount = RSMD.getColumnCount();
		while (rs.next()) {
			for (int i = 1; i <= colCount; i++) {
				Object value = rs.getObject(i);
				System.out.println(value);
			}
		}
	}

	// 获得自动的ID
	public String autoID() {
		return UUID.randomUUID().toString();
	}

	public boolean existByCondition(String tableName, String condition, Object... params) throws Exception {
		return this.count("select count(0) from " + tableName + " where " + condition, params) > 0;
	}
}
