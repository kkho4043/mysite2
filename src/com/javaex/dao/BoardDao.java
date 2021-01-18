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

	public int insert(BoardVo boardVo) {

		getConnection();
		int count = 0;
		try {

			String query = "";
			query += " insert into board";
			query += " values(seq_board_no.nextval,?,?,0,sysdate,?)";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserno());

			count = pstmt.executeUpdate();

			System.out.println("[" + count + "건 완료.]");

		} catch (SQLException e) {
			System.out.println("errorinsert:" + e);
		}
		close();
		return count;
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
			query += "        b.hit,";
			query += "        TO_CHAR((b.reg_date),'YYYY/MM/DD HH:MI') reg_date,";
			query += "        b.user_no ";
			query += " FROM board b, users u";
			query += " WHERE b.user_no = u.no";
			query += " order by reg_date asc";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getNString("title");
				String name = rs.getNString("name");
				int hit = rs.getInt("hit");
				String date = rs.getString("reg_date");
				int userno = rs.getInt("user_no");
				BoardVo vo = new BoardVo(no, userno,title, name, hit, date);
				boardList.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("errorlist:" + e);
		}
		close();
		return boardList;
	}

//read
	public BoardVo getRead(int bno) {

		BoardVo readList = new BoardVo();
		int count = 0;
		getConnection();
		try {

			String query = "";

			query += " update board ";
			query += " set hit = hit + 1 ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bno);

			count = pstmt.executeUpdate();

			query = "";

			query += " SELECT b.no, ";
			query += " 		  b.title, ";
			query += "        u.name, ";
			query += "        b.hit,";
			query += "        TO_CHAR((b.reg_date),'YYYY/MM/DD HH:MI') reg_date,";
			query += "        b.content, ";
			query += "        b.user_no ";
			query += " FROM board b, users u";
			query += " WHERE b.user_no = u.no";
			query += " and b.no = ?";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, bno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getNString("title");
				String name = rs.getNString("name");
				int hit = rs.getInt("hit");
				String date = rs.getString("reg_date");
				String content = rs.getString("content");
				int userno = rs.getInt("user_no");

				readList = new BoardVo(no, title, name, hit, date, content,userno);
			}

		} catch (SQLException e) {
			System.out.println("errorlist:" + e);
		}
		close();
		return readList;
	}

// modify
	public int boardmodify(String title, String content, int no) {

		getConnection();
		int count = 0;
		try {

			String query = "";
			query += " update board ";
			query += " set title = ?, ";
			query += "     content = ? ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, no);

			count = pstmt.executeUpdate();

			// 4.���ó��
			System.out.println("[" + count + "건 변경완료.]");

		} catch (SQLException e) {
			System.out.println("errorlist:" + e);
		}
		close();
		return count;
	}

//delete
	public int boardDelete(int no) {
		getConnection();
		int count = 0;
		try {

			String query = "";
			query += " delete from board ";
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

	// search
	public List<BoardVo> boardSearch(String search) {
		List<BoardVo> searchList = new ArrayList<BoardVo>();
		getConnection();

		try {

			String query = "";
			query += " SELECT b.no, ";
			query += " 		  b.title, ";
			query += "        u.name, ";
			query += "        b.hit,";
			query += "        TO_CHAR((b.reg_date),'YYYY/MM/DD HH:MI') reg_date,";
			query += "        b.content, ";
			query += "        b.user_no ";
			query += " FROM board b, users u";
			query += " WHERE(";
			query += " b.no LIKE '%" + search + "%' ";
			query += " or b.title LIKE '%" + search + "%' ";
			query += " or u.name LIKE '%" + search + "%') ";
			query += " and b.user_no = u.no";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getNString("title");
				String name = rs.getNString("name");
				int hit = rs.getInt("hit");
				String date = rs.getString("reg_date");
				int userno = rs.getInt("user_no");
				BoardVo vo = new BoardVo(no,userno,title, name, hit, date);
				searchList.add(vo);
			}

			System.out.println(search+" 검색");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return searchList;
	}

}
