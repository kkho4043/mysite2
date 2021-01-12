package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class GuestDao {
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

//insert========================================================	
	public int guestInsert(GuestVo guestVo) {

		getConnection();
		int count = 0;
		try {
			String query = "";
			query += " insert into guestbook1 ";
			query += " values(seq_guest_no.nextval,?,?,?,sysdate) ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, guestVo.getName());
			pstmt.setString(2, guestVo.getPassword());
			pstmt.setString(3, guestVo.getContent());
			
			count = pstmt.executeUpdate();

			System.out.println("[" + count + "삽입완료.]");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	
		close();
		return count;
	}
//list========================================================	
	public List<GuestVo> getguestList() {

		List<GuestVo> guestList = new ArrayList<GuestVo>();

		getConnection();
		try {

			// SQL�� �غ� / ���ε� / ����
			String query = "";

			query += " SELECT no, ";
			query += " 		  name, ";
			query += "        password, ";
			query += "        content, ";
			query += "        reg_date ";
			query += " FROM guestbook1 ";
			query += " order by no asc";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			// ���ó��
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String pwd = rs.getString("password");
				String content = rs.getString("content");
				String date = rs.getString("reg_date");
				GuestVo vo = new GuestVo(no,name,pwd,content,date);
				guestList.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("errorddd:" + e);
		}
		close();
		return  guestList;
	}
	
	
//onelist========================================================	
		public List<GuestVo> getList(int guestno) {

			List<GuestVo> getList = new ArrayList<GuestVo>();

			getConnection();
			try {

				// SQL�� �غ� / ���ε� / ����
				String query = "";

				query += " SELECT no, ";
				query += " 		  name, ";
				query += "        password, ";
				query += "        content, ";
				query += "        reg_date ";
				query += " FROM guestbook1 ";
				query += " where no = ? ";
				query += " order by no asc";

				pstmt = conn.prepareStatement(query);
				
		
				pstmt.setInt(1, guestno);
				rs = pstmt.executeQuery();
				// ���ó��
				while (rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("name");
					String pwd = rs.getString("password");
					String content = rs.getString("content");
					String date = rs.getString("reg_date");
					GuestVo vo = new GuestVo(no,name,pwd,content,date);
					getList.add(vo);
				}
			} catch (SQLException e) {
				System.out.println("errorddd:" + e);
			}
			close();
			return  getList;
		}
	
//delete=======================================================
	public int guestDelete(int no) {
		getConnection();
		int count = 0;
		try {

			// 3. SQL�� �غ� / ���ε� / ����

			String query = "";
			query += " delete from guestbook1 ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);

			count = pstmt.executeUpdate();

			// 4.���ó��
			System.out.println("[" + count + "건 잘 삭제됨.]");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		close();
		return count;
	}
}
