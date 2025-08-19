package com.gyojincompany.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gyojincompany.dto.MemberDto;

public class MemberDao {
	//DB 커넥션 준비
	private String driverName = "com.mysql.jdbc.Driver"; //MySQL JDBC 드라이버 이름
	private String url = "jdbc:mysql://localhost:3306/jspdb"; //MySQL이 설치된 서버의 주소(ip)와 연결할 DB(스키마) 이름		
	private String username = "root";
	private String password = "12345";	
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public static final int MEMBER_JOIN_SUCCESS = 1;
	public static final int MEMBER_JOIN_FAIL = 0;	
	public static final int LOGIN_SUCCESS = 1;
	public static final int LOGIN_FAIL = 0;
	public static final int MEMBER_ID_EXISTENT = 1;
	public static final int MEMBER_ID_NONEXISTENT = 0;
	public static final int MEMBER_DELETE_SUCCESS = 1;
	public static final int MEMBER_DELETE_FAIL = 0;
	
	public int insertMember(MemberDto memberDto) { //회원 가입 메서드
		
		String sql ="INSERT INTO membertbl(memberid, memberpw, membername, memberage, memberemail) VALUES (?,?,?,?,?)";
		
		int sqlResult = 0;
		
		try {
			Class.forName(driverName); //MySQL 드라이버 클래스 불러오기			
			conn = DriverManager.getConnection(url, username, password);
			//커넥션이 메모리 생성(DB와 연결 커넥션 conn 생성)
			
			pstmt = conn.prepareStatement(sql); //pstmt 객체 생성(sql 삽입)
			pstmt.setString(1, memberDto.getMemberid());
			pstmt.setString(2, memberDto.getMemberpw());
			pstmt.setString(3, memberDto.getMembername());
			pstmt.setInt(4, memberDto.getMemberage());
			pstmt.setString(5, memberDto.getMemberemail());
			
			sqlResult = pstmt.executeUpdate(); //성공하면 sqlResult 값이 1로 변환
			// SQL문을 DB에서 실행->성공하면 1이 반환, 실패면 1이 아닌 값 0이 반환
			
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 회원 가입 실패!");
			e.printStackTrace(); //에러 내용 출력
		} finally { //에러의 발생여부와 상관 없이 Connection 닫기 실행 
			try {
				if(pstmt != null) { //stmt가 null 이 아니면 닫기(conn 닫기 보다 먼저 실행)
					pstmt.close();
				}				
				if(conn != null) { //Connection이 null 이 아닐 때만 닫기
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		if (sqlResult == 1) {
			return MEMBER_JOIN_SUCCESS; //1
		} else {
			return MEMBER_JOIN_FAIL; //0
		}		
		
	}
	
	public int loginCheck(String id, String pw) { //로그인 성공 여부 반환하는 메서드
		
		String sql = "SELECT * FROM membertbl WHERE memberid=? AND memberpw=?";
		
		int sqlResult = 0;
		
		try {
			Class.forName(driverName); //MySQL 드라이버 클래스 불러오기			
			conn = DriverManager.getConnection(url, username, password);
			//커넥션이 메모리 생성(DB와 연결 커넥션 conn 생성)
			
			pstmt = conn.prepareStatement(sql); //pstmt 객체 생성(sql 삽입)
			pstmt.setString(1, id);
			pstmt.setString(2, pw);			
			
			rs = pstmt.executeQuery(); //성공하면 sqlResult 값이 1로 변환
			// SQL문을 DB에서 실행->성공하면 1이 반환, 실패면 1이 아닌 값 0이 반환
			
			if(rs.next()) { //로그인 성공
				sqlResult = LOGIN_SUCCESS;
				
			} else { //로그인 실패
				sqlResult = LOGIN_FAIL;
			}			
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 로그인 체크 실패!");
			e.printStackTrace(); //에러 내용 출력
		} finally { //에러의 발생여부와 상관 없이 Connection 닫기 실행 
			try {
				if(rs != null) { //rs가 null 이 아니면 닫기(stmt 닫기 보다 먼저 실행)
					rs.close();
				}				
				if(pstmt != null) { //stmt가 null 이 아니면 닫기(conn 닫기 보다 먼저 실행)
					pstmt.close();
				}				
				if(conn != null) { //Connection이 null 이 아닐 때만 닫기
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return sqlResult; //1로 반환되면 로그인 성공, 0으로 반환되면 로그인 실패
		
	}
	
	public int confirmId(String id) { //회원 가입 전에 아이디 존재 여부 확인 결과 반환 메서드
		
		String sql = "SELECT * FROM membertbl WHERE memberid = ?";
		
		int sqlResult = 0;
		
		try {
			Class.forName(driverName); //MySQL 드라이버 클래스 불러오기			
			conn = DriverManager.getConnection(url, username, password);
			//커넥션이 메모리 생성(DB와 연결 커넥션 conn 생성)
			
			pstmt = conn.prepareStatement(sql); //pstmt 객체 생성(sql 삽입)
			pstmt.setString(1, id);				
			
			rs = pstmt.executeQuery(); //성공하면 sqlResult 값이 1로 변환
			// SQL문을 DB에서 실행->성공하면 1이 반환, 실패면 1이 아닌 값 0이 반환
			
			if(rs.next()) { //참이면 아이디 가입 불가
				sqlResult = MEMBER_ID_EXISTENT; //아이디 이미 존재
				
			} else { //아이디 가입 가능
				sqlResult = MEMBER_ID_NONEXISTENT; //아이디 존재하지 않음
			}			
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 아이디 존재여부 확인 실패!");
			e.printStackTrace(); //에러 내용 출력
		} finally { //에러의 발생여부와 상관 없이 Connection 닫기 실행 
			try {
				if(rs != null) { //rs가 null 이 아니면 닫기(stmt 닫기 보다 먼저 실행)
					rs.close();
				}				
				if(pstmt != null) { //stmt가 null 이 아니면 닫기(conn 닫기 보다 먼저 실행)
					pstmt.close();
				}				
				if(conn != null) { //Connection이 null 이 아닐 때만 닫기
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return sqlResult; //1로 반환되면 아이디 가입 불가, 0으로 반환되면 아이디 가입 가능
	}
	
	public int deleteMember(String deleteId) {
		
		String sql ="DELETE FROM membertbl WHERE memberid = ?";
		
		int sqlResult = 0;
		
		try {
			Class.forName(driverName); //MySQL 드라이버 클래스 불러오기			
			conn = DriverManager.getConnection(url, username, password);
			//커넥션이 메모리 생성(DB와 연결 커넥션 conn 생성)
			
			pstmt = conn.prepareStatement(sql); //pstmt 객체 생성(sql 삽입)
			pstmt.setString(1, deleteId);			
			
			sqlResult = pstmt.executeUpdate(); //성공하면 sqlResult 값이 1로 변환
			// SQL문을 DB에서 실행->삭제 성공하면 1이 반환, 실패면 0이 반환
			
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 회원 가입 실패!");
			e.printStackTrace(); //에러 내용 출력
		} finally { //에러의 발생여부와 상관 없이 Connection 닫기 실행 
			try {
				if(pstmt != null) { //stmt가 null 이 아니면 닫기(conn 닫기 보다 먼저 실행)
					pstmt.close();
				}				
				if(conn != null) { //Connection이 null 이 아닐 때만 닫기
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		if (sqlResult == 1) {
			return MEMBER_DELETE_SUCCESS; //1
		} else {
			return MEMBER_DELETE_FAIL; //0
		}		
	}

}
