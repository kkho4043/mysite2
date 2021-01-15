package com.javaex.contoller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WepUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("boardcontoller");
		
		
		String action = request.getParameter("action");
		
		if("read".equals(action)) {
			
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDao boardDao = new BoardDao();

			System.out.println("여기2");
			BoardVo boardVo = boardDao.getRead(no);
			System.out.println("여기 3");
			
			request.setAttribute("boardVo",boardVo);
			WepUtil.forword(request, response,"/WEB-INF/views/board/read.jsp");
		}else if("delete".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao boardDao = new BoardDao();
			
			boardDao.boardDelete(no);
			WepUtil.redirect(request, response,"/mysite2/board");
			
		}else if("writeForm".equals(action)) {
			
			WepUtil.forword(request, response,"/WEB-INF/views/board/writeForm.jsp");
			
		}else if("write".equals(action)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			BoardVo boardVo = new BoardVo(title,content,authUser.getNo());
			BoardDao boardDao = new BoardDao();
			boardDao.insert(boardVo);
			
			WepUtil.redirect(request, response,"/mysite2/board");
		}else if("modifyForm".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getRead(no);
			
			request.setAttribute("boardVo",boardVo);
			WepUtil.forword(request, response,"/WEB-INF/views/board/modifyForm.jsp");
		}else if("modify".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			BoardDao boardDao = new BoardDao();
			
			boardDao.boardmodify(title, content, no);
			
			WepUtil.redirect(request, response,"/mysite2/board");
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
