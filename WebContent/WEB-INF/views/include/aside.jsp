<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<div id="aside">
	
	
	<c:set var="aa" value="${pageContext.request.requestURL}"/>

	<c:if test="${fn:contains(aa,'board')}">
		<h2>게시판</h2>
		<ul>
			<li><a href="">일반게시판</a></li>
			<li><a href="">댓글게시판</a></li>
		</ul>
	</c:if>
	<c:if test="${fn:contains(aa,'guest')}">
		<h2>방명록</h2>
		<ul>
			<li>일반방명록</li>
			<li>ajax방명록</li>
		</ul>
	</c:if>
	<c:if test="${fn:contains(aa,'user')}">
		<h2>회원</h2>
			<ul>
				<li>회원정보</li>
				<li>로그인</li>
				<li>회원가입</li>
			</ul>
	</c:if>




		
	

	
		
		<!-- //aside -->
</div>