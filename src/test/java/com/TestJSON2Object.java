package com;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.analysis.db.DBConnection;
import com.analysis.po.Regoin;
import com.analysis.service.SubjectServiceImpl;
import com.red.crm.MetadataServiceUtils;

public class TestJSON2Object {

	/**
	 * @���ܣ�
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// String data = "[{\"regoinId\": \"ff808081429511330142b62e65ec1b61\","
		// + "\"parentId\": \"3C2F72BF-23E9-2207-CE2D-B4720ED0F5A5\","
		// + "\"regoinName\": \"������\"," + "\"vNo\": 7},"
		// + "{\"regoinId\": \"ff808081429511330142b62e65ec1b62\","
		// + "\"parentId\": \"3C2F72BF-23E9-2207-CE2D-B4720ED0F5A5\","
		// + "\"regoinName\": \"������2\"," + "\"vNo\": 7}]";
		// data = MetadataServiceUtils.getAreaList();
		// Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
		// .create();
		// try {
		// List<Regoin> list = g.fromJson(data, new TypeToken<List<Regoin>>() {
		// }.getType());
		// // Regoin regoin = g.fromJson(data, new TypeToken<Regoin>(){
		// // }.getType());
		// System.out.println(list.size());
		//
		//			
		// } catch (JsonSyntaxException e) {
		// e.printStackTrace();
		// }
		// long start = System.currentTimeMillis();
		// RegionDaoImpl daoImpl = new RegionDaoImpl();
		// RegoinServiceImpl serviceImpl = new RegoinServiceImpl();
		// String jsonData = serviceImpl.fromObject2Json();
		// //List list = daoImpl.getAll();
		// long end = System.currentTimeMillis();
		// System.out.println(end - start);
		// System.out.println(jsonData);
		// Calendar cal = Calendar.getInstance();
		// cal.set(2014, 0, 1);
		// cal.add(cal.WEEK_OF_YEAR, 51);
		// System.out.println(cal.getTime().toLocaleString());

		// long start = System.currentTimeMillis();
		// TermServiceImpl serviceImpl = new TermServiceImpl();
		// // serviceImpl.updateAll("1B34A4DB-9196-A3DF-654C-7B51296F5808",
		// "JXD");
		// String data = MetadataServiceUtils.getSchoolList(
		// "D5CD4405-9A61-BB98-B80D-5AF58520CD12", "JXD");
		// long end = System.currentTimeMillis();
		// System.out.println(end - start);
		// System.out.println(data);
		SubjectServiceImpl subjectService = new SubjectServiceImpl();
		subjectService.updateAll();
		// String jsonData = MetadataServiceUtils
		// .getPubVerList("ff8080814356663c01436a7d74990ed9");
		// System.out.println("Զ�̲̽İ汾��ݣ�" + jsonData);
	}

	public static void resetRegion() {
		List<Regoin> list = getAllTemp();
		Connection con = null;
		PreparedStatement stmt = null;
		// ResultSet rs = null;
		try {
			con = DBConnection.getConnection();
			String insert = "insert into t_region(id,parent_id,node_name,v_no,node_type,seq_no,national_code,code_path,code_level)"
					+ " values(?,?,?,?,?,?,?,?,?)";
			stmt = con.prepareStatement(insert);
			String codePath = null;
			BigDecimal codeLevel = null;
			for (Regoin regoin : list) {
				codePath = regoin.getNationalCode().replaceAll("00", "");
				codeLevel = new BigDecimal(codePath.length() / 2);
				stmt.setString(1, regoin.getId());
				stmt.setString(2, regoin.getParentId());
				stmt.setString(3, regoin.getNodeName());
				stmt.setBigDecimal(4, regoin.getVersionNo());
				stmt.setString(5, regoin.getNodeType());
				stmt.setBigDecimal(6, regoin.getSeqNo());
				stmt.setString(7, regoin.getNationalCode());
				stmt.setString(8, codePath);
				stmt.setBigDecimal(9, codeLevel);
				stmt.execute();
			}
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

	public static List getAllTemp() {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Regoin regoin = null;
		List list = new ArrayList();
		try {
			con = DBConnection.getConnection();
			String insert = "select * from t_region_temp t";
			stmt = con.prepareStatement(insert);
			rs = stmt.executeQuery();

			while (rs.next()) {
				regoin = new Regoin();
				regoin.setId(rs.getString(1));
				regoin.setParentId(rs.getString(2));
				regoin.setNodeName(rs.getString(3));
				regoin.setVersionNo(rs.getBigDecimal(4));
				regoin.setNodeType(rs.getString(5));
				regoin.setSeqNo(rs.getBigDecimal(6));
				regoin.setNationalCode(rs.getString(7));
				list.add(regoin);
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

}
