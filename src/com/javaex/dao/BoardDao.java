package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pstmt = null;

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
//=========list
	public List<BoardVo> getboardList() {

		List<BoardVo> boardList = new ArrayList<BoardVo>();

		getConnection();
		try {
			
			String query = "";

			query += " SELECT b.no, ";
			query += " 		  b.title, ";
			query += "        u.name, ";
			query += "        b.hit, ";
			query += "        b.reg_date ";
			query += " FROM board b, users u";
			query += " WHERE b.user_no = u.no";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String date = rs.getString("reg_date");
				BoardVo vo = new BoardVo(no,title,name,hit,date);
				boardList.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("errorlist:" + e);
		}
		close();
		return  boardList;
	}
	
}
