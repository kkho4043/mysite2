package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.Vo.UserVo;

public class UserDao {

	private ResultSet rs = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	private void getConnection() {
		try {
			Class.forName(driver);

			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("여기서 문제 1 :" + e);
		} catch (SQLException e) {
			System.out.println("여기서 문제 2 :" + e);
		}
	}

	private void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	public int insert(UserVo userVo) {
		int count = 0;
		getConnection();

		try {

			String query = "";
			query += " insert into users";
			query += " values(seq_users_no.nextval,?,?,?,?) ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassward());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
			

			count = pstmt.executeUpdate();

			// ���ó��
			System.out.println("[" + count + "건 등록완료.]");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		
		return count;
	}

}
