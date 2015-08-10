package com.analysis.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.analysis.db.DBConnection;
import com.analysis.po.StatResult;
import com.analysis.po.TermResourceRelation;
import com.analysis.po.TermUnBootStrap;

public class TermResourceDaoImpl {
	// private List condParams = new ArrayList();

	/**
	 * 
	 * @功能：统计
	 * 
	 * @param sql
	 *            预编译sql
	 * @param condParams
	 *            条件集合
	 * @return
	 */
	public List stat(String sql, List condParams) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			con = DBConnection.getConnection();
			stmt = con.prepareStatement(sql);
			for (int i = 0; i < condParams.size(); i++) {
				String param = (String) condParams.get(i);
				stmt.setString(i + 1, param);
			}

			rs = stmt.executeQuery();
			StatResult result = null;
			while (rs.next()) {
				result = new StatResult();
				result.setId(rs.getString(1));
				result.setName(rs.getString(2));
				result.setTotalNum(rs.getBigDecimal(3));
				list.add(result);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(stmt);
			DBConnection.closeConnection(con);
		}
		return list;
	}

	/**
	 * 
	 * @功能：保存终端上的资源
	 * 
	 * @param relation
	 *            终端数据
	 * @param optType
	 *            操作类型 1已接收，2未开机，3点击量，4下载量
	 * @param code
	 *            省编码
	 */
	public void saveAll(TermResourceRelation relation, String optType,
			String code) {
		Connection con = null;
		PreparedStatement stmt = null;
		String tableName = "t_term_resource_relation_" + code;
		try {
			con = DBConnection.getConnection();
			con.setAutoCommit(false);
			String insert = "insert into "
					+ tableName
					+ "(resource_id,resource_name,term_id,subject_id,class_id,opt_count,opt_type,opt_time)"
					+ " values(?,?,?,?,?,?,?,?)";
			stmt = con.prepareStatement(insert);
			stmt.setString(1, relation.getResourceId());
			stmt.setString(2, relation.getResourceName());
			stmt.setString(3, relation.getTermId());
			stmt.setString(4, relation.getSubjectId());
			stmt.setString(5, relation.getClassId());
			stmt.setBigDecimal(6, relation.getOptCount());
			stmt.setString(7, optType);
			stmt.setDate(8, new Date(System.currentTimeMillis()));

			stmt.execute();
			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(stmt);
			DBConnection.closeConnection(con);
		}
	}

	/**
	 * 
	 * @功能：取前两位省代码
	 * 
	 * @param termId
	 * @return
	 */
	public String getProvinceCode(String termId) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String code = "";
		try {
			con = DBConnection.getConnection();
			String insert = "select r.code_path from t_region r, t_school t where r.id=t.country_id and t.school_id=?";
			stmt = con.prepareStatement(insert);
			stmt.setString(1, termId);
			rs = stmt.executeQuery();
			String codePath = null;

			while (rs.next()) {
				codePath = rs.getString(1);
			}
			if (codePath != null || codePath.length() >= 2) {
				code = codePath.substring(0, 2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(stmt);
			DBConnection.closeConnection(con);
		}
		return code;
	}

	public void saveUnBootStrap(TermUnBootStrap strap) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBConnection.getConnection();
			con.setAutoCommit(false);
			String insert = "insert into t_un_boot_strap_term(term_id,check_date) values(?,?)";
			stmt = con.prepareStatement(insert);
			stmt.setString(1, strap.getTermId());
			stmt.setDate(2, new Date(System.currentTimeMillis()));

			stmt.execute();
			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(stmt);
			DBConnection.closeConnection(con);
		}
	}
}
