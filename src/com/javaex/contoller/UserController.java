package com.javaex.contoller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.Vo.UserVo;
import com.javaex.dao.UserDao;
import com.javaex.util.WepUtil;


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
		
		}else {
			System.out.println("메인폼");
			WepUtil.forword(request, response,"/WEB-INF/views/main/index.jsp");	
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
