<%@page import="com.gyojincompany.dto.MemberDto"%>
<%@page import="com.gyojincompany.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="memberDto" class="com.gyojincompany.dto.MemberDto"></jsp:useBean>    
<jsp:setProperty property="*" name="memberDto" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입 확인</title>
</head>
<body>	
	<%
		//String mid = request.getParameter("memberid");
		//String mpw = request.getParameter("memberpw");
		//String mname = request.getParameter("membername");
		//int mage = Integer.parseInt(request.getParameter("memberage"));
		//String memail = request.getParameter("memberemail");
		
		//MemberDto memberDto = new MemberDto();
		//memberDto.setMemberid(mid);
		//memberDto.setMemberpw(mpw);
		//memberDto.setMembername(mname);
		//memberDto.setMemberage(mage);
		//memberDto.setMemberemail(memail);
	
		MemberDao memberDao = new MemberDao();
	
		int joinResult = memberDao.insertMember(memberDto);
		// 1(성공) 또는 0(실패) 반환
		if(joinResult == MemberDao.MEMBER_JOIN_SUCCESS) {
			out.println("<script>alert('회원 가입 성공!');</script>");
			//response.sendRedirect("login.jsp");
		} else {
			out.println("<script>alert('회원 가입 실패!');history.go(-1);</script>");
		}
	
	%>
</body>
</html>