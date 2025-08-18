<%@page import="com.gyojincompany.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 성공</title>
</head>
<body>
	<%
		String mid = request.getParameter("loginid");
		String mpw = request.getParameter("loginpw");
		
		MemberDao memberDao = new MemberDao();
		int loginResult = memberDao.loginCheck(mid, mpw); 
		//1이 반환되면 로그인 성공처리, 0이 반환되면 로그인 실패처리
		
		if(loginResult == MemberDao.LOGIN_SUCCESS) {
			session.setAttribute("sessionId", mid); //세션에 로그인 성공한 아이디 set
			out.println("<script>alert('회원 로그인 성공!');</script>");
		} else {
			out.println("<script>alert('회원 로그인 실패!아이디 또는 비밀번호가 일치하지 않습니다.');history.go(-1);</script>");
		}
	
	%>
</body>
</html>