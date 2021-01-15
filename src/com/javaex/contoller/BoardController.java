package com.javaex.contoller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WepUtil;
import com.javaex.vo.BoardVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("boardcontoller");
		
		
		String action = request.getParameter("action");
		
		if("ddddd".equals(action)) {
			
			
		}else {
			System.out.println("boardlist");
			
			
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getboardList();
			
			request.setAttribute("blist", boardList);
			WepUtil.forword(request, response,"/WEB-INF/views/board/list.jsp");
		}
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
