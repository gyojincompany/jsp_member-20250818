<%@page import="com.gyojincompany.dto.MemberDto"%>
<%@page import="com.gyojincompany.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정 완료</title>
</head>
	<%
		request.setCharacterEncoding("utf-8");
		String memberid = request.getParameter("memberid");
		String memberpw = request.getParameter("memberpw");
		String membername = request.getParameter("membername");
		int memberage = Integer.parseInt(request.getParameter("memberage"));
		String memberemail = request.getParameter("memberemail");
	
		MemberDao memberDao = new MemberDao();
		int updateResult = memberDao.updateMember(memberid, memberpw, membername, memberage, memberemail);
		//1이 반환되면 수정 성공, 0이 반환되면 수정 실패
		if(updateResult == MemberDao.MEMBER_UPDATE_SUCCESS) {
			MemberDto memberDto = memberDao.getMemberInfo(memberid);
			request.setAttribute("memberDto", memberDto);
		} else {
			out.println("<script>alert('회원 수정 실패!');history.go(-1);</script>");
		}
	
	%>
	
<body>
	<h2>수정된 회원정보 확인</h2>
	<hr>
	아이디 : ${memberDto.memberid } <br><br>
	비밀번호 : ${memberDto.memberpw } <br><br>
	이름 : ${memberDto.membername } <br><br>
	나이 : ${memberDto.memberage } <br><br>
	이메일 : ${memberDto.memberemail } <br><br>
	<hr>
	<h3>
		<a href="logout.jsp">로그 아웃</a>
	</h3>
</body>
</html>