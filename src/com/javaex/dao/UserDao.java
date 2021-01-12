package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.GuestVo;
import com.javaex.vo.UserVo;

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
//가입----------------------
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
//유저 정보
	public UserVo getUser(String id,String pwd) {
		UserVo userVo = null;

		getConnection();
		
		try {
			String query = "";
			 query += "select no,";
			 query += "		  name ";
			 query += "from users ";
			 query += "where id = ? ";
			 query += "and password = ? ";
			 
			 
			 pstmt = conn.prepareStatement(query);
			 
			 pstmt.setString(1, id);
			 pstmt.setString(2, pwd);
			 
			 rs = pstmt.executeQuery();
			 
			 while(rs.next()) {
				 int no = rs.getInt("no");
				 String name = rs.getString("name");
				 
				 userVo = new UserVo(no,name);
				
			 }
			 
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		close();
		
		return userVo;
		
	}
//한명분 전부
	public UserVo getUserall(int uno) {
		UserVo userVo = null;

		getConnection();
		
		try {
			String query = "";
			 query += "select no, ";
			 query += "		  id, ";
			 query += "		  password, ";
			 query += "		  name, ";
			 query += "		  gender ";
			 query += "from users ";
			 query += "where no = ? ";
			 
			 pstmt = conn.prepareStatement(query);
			 
			 pstmt.setInt(1, uno);
			
			 rs = pstmt.executeQuery();
			 
			 while(rs.next()) {
				 int no = rs.getInt("no");
				 String id = rs. getString("id");
				 String password = rs. getString("password");
				 String name = rs.getString("name");
				 String gender = rs. getString("gender");
				 
				 userVo = new UserVo(no,id,password,name,gender);
			 }
			 
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		close();
		
		return userVo;
		
	}
//유저 수정
	public int userupdate(UserVo userVo) {
		getConnection();
		int count = 0;
		System.out.println(userVo);
		try {

			String query = "";
			query += " update users ";
			query += " set password = ?, ";
			query += "     name = ? ,";
			query += "     gender = ?";
			query += " where id = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, userVo.getPassward());
			pstmt.setString(2, userVo.getName());
			pstmt.setString(3, userVo.getGender());
			pstmt.setString(4, userVo.getId());

			count = pstmt.executeUpdate();
			System.out.println("[" + count + "수정완료.]");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		close();
		return count;
	}
	
	
}
