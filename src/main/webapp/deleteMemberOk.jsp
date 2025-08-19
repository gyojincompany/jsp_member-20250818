<%@page import="com.gyojincompany.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 탈퇴 확인</title>
</head>
<body>
	<%
		String mid = request.getParameter("deleteid");
		
		MemberDao memberDao = new MemberDao();
		//int confirmID = memberDao.confirmId(mid); //1을 반환하면 아이디가 존재 O, 0이 반환되면 아이디가 존재 X
		
		//if(confirmID == MemberDao.MEMBER_ID_EXISTENT) {
		//	int deleteResult = memberDao.deleteMember(mid);
		//	if(deleteResult == MemberDao.MEMBER_DELETE_SUCCESS) {
		//		out.println("<script>alert('회원 탈퇴 성공!');</script>");
		//	} else {
		//		out.println("<script>alert('회원 탈퇴 실패! 다시 시도해 주시기 바랍니다.');</script>");
		//	}
		//} else {
		//	out.println("<script>alert('회원 탈퇴 실패! 아이디가 존재하지 않습니다.');</script>");
		//}
		
		int deleteResult = memberDao.deleteMember(mid); //삭제 성공이면 1, 실패면 0		
		//out.println(deleteResult);
		
		if(deleteResult == MemberDao.MEMBER_DELETE_SUCCESS) {
			out.println("<script>alert('회원 탈퇴 성공!');</script>");
		} else { //0이 반환->아이디 없어서 0이 반환
			out.println("<script>alert('회원 탈퇴 실패! 아이디가 존재하지 않습니다.');</script>");
		}
		
		
	
	%>
</body>
</html>