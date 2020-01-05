<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<%@ page import="magic.member.*"%>

<%
	request.setCharacterEncoding("euc-kr");

	String id=request.getParameter("mem_uid");
	String pwd=request.getParameter("mem_pwd");
	
	MemberDBBean manager=MemberDBBean.getInstance();
	int check=manager.userCheck(id, pwd);
	MemberBean mb=manager.getMember(id);
	
	if(mb==null){
%>
		<script>
			alert("존재하지 않는 회원");
		</script>
<%
	}else{
		String name=mb.getMem_name();
		
		if(check == 1){
			session.setAttribute("uid", id);
			session.setAttribute("name", name);
			session.setAttribute("Member", "yes");
			response.sendRedirect("main.jsp");
		}else if(check == 0){
%>
			<script>
				alert("비밀번호가 맞지 않습니다.");
				history.go(-1);
			</script>
<%
		}
		else{
%>
			<script>
				alert("아이디가 맞지 않습니다.");
				history.go(-1);
			</script>
<%
		}
	}
%>