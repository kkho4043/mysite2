package com.javaex.contoller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.Vo.GuestVo;
import com.javaex.dao.GuestDao;
import com.javaex.util.WepUtil;


@WebServlet("/guest")
public class GuestController extends HttpServlet {
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("guestcontoller");
		
		String action = request.getParameter("action");
		
		if("add".equals(action)) {
			System.out.println("방명록 등록");
			
			String name = request.getParameter("name");
			String pwd = request.getParameter("pwd");
			String content = request.getParameter("content");

			GuestDao guestDao = new GuestDao();
			GuestVo guestVo = new GuestVo(name, pwd, content);
			guestDao.guestInsert(guestVo);
			
			WepUtil.redirect(request, response, "/mysite2/guest?action=addList");
			
		}else if("deleteForm".equals(action)) {
			System.out.println("삭제폼");
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			request.setAttribute("no",no);
			WepUtil.forword(request, response,"/WEB-INF/views/guestbook/deleteForm.jsp");
			
		} else if("delete".equals(action)) {
			System.out.println("삭제");
			
			int no = Integer.parseInt(request.getParameter("no"));
			String pass2 = request.getParameter("pwd");
			
			GuestDao guestDao = new GuestDao();
			List<GuestVo> guestVo = guestDao.getList(no);			
			String pass1 = guestVo.get(0).getPassword();
			
			if(pass1.equals(pass2)) {
				guestDao.guestDelete(no);
			}
			
			request.setAttribute("no",no);
			WepUtil.redirect(request, response, "/mysite2/guest?action=addList");
			
		}else {
			System.out.println("게스트 폼");
			
			GuestDao guestDao = new GuestDao();
			List<GuestVo> guestList = guestDao.getguestList();
			request.setAttribute("glist", guestList);
			WepUtil.forword(request, response,"/WEB-INF/views/guestbook/addList.jsp");
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
