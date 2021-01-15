package com.javaex.contoller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WepUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("usercontoller");
		
		String action = request.getParameter("action");
		if("joinForm".equals(action)) {
			System.out.println("회원가입 폼");
			WepUtil.forword(request, response,"/WEB-INF/views/user/joinForm.jsp");
			
		}else if("join".equals(action)) {
			System.out.println("회원가입");
			
			String id = request.getParameter("uid");
			String password = request.getParameter("pwd");
			String name = request.getParameter("uname");
			String gender = request.getParameter("gender");
			
			UserVo userVo = new UserVo(id,password,name,gender);
			System.out.println(userVo.toString());
			
			UserDao userDao = new UserDao();
			
			userDao.insert(userVo);
			
			WepUtil.forword(request,response,"/WEB-INF/views/user/joinOk.jsp");
			
		}else if("loginForm".equals(action)) {
			System.out.println("로그인 폼");
			WepUtil.forword(request, response,"/WEB-INF/views/user/loginForm.jsp");
		
		}else if("login".equals(action)) {
			System.out.println("로그인");
			
			
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			
			UserDao userDao = new UserDao();
			UserVo authVo = userDao.getUser(id, pwd);
			
			System.out.println(authVo);
			if(authVo == null) {//실패
				System.out.println("로그인 실패");
				WepUtil.redirect(request, response, "/mysite2/user?action=loginForm&result=fail");
			}else {
				//성공일때.
				HttpSession session = request.getSession();
				session.setAttribute("authUser",authVo);
				
				WepUtil.redirect(request,response,"/mysite2/main");
			}
			
		}else if("logout".equals(action)) {
			System.out.println("로그아웃");
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WepUtil.redirect(request,response,"/mysite2/main");
		}else if("modifyForm".equals(action)) {
			
			System.out.println("업데이트폼");
			
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUserall(authUser.getNo());
			
			request.setAttribute("userVo", userVo);
			WepUtil.forword(request, response,"/WEB-INF/views/user/modifyForm.jsp");
		}else if("modify".equals(action)) {
			
			System.out.println("업데이트");
			
			String id  = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			UserVo newuserVo = new UserVo(id,pwd,name,gender); 
			
			UserDao userDao = new UserDao();
			userDao.userupdate(newuserVo); //유저 정보 업데이트
			
			HttpSession session = request.getSession();
			session.removeAttribute("authUser"); //기존 유저 세션 삭제
			
			UserVo authVo = userDao.getUser(id, pwd);//새로운 세션을 위한 정보 가져오기
			session.setAttribute("authUser",authVo); //가져와서 새 세션에 집어넣기
			
			WepUtil.redirect(request, response, "/mysite2/main");
		}else {
			System.out.println("메인폼");
			WepUtil.redirect(request, response, "/mysite2/main");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
