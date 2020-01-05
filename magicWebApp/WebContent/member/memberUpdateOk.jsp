<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<%@ page import="magic.member.*"%>

<% request.setCharacterEncoding("euc-kr"); %>
<jsp:useBean id="mb" class="magic.member.MemberBean" scope="page"></jsp:useBean>
<jsp:setProperty property="*" name="mb"/>
<%
	String uid=(String)session.getAttribute("uid");
	mb.setMem_uid(uid);
	
	MemberDBBean manager=MemberDBBean.getInstance();
	int re=manager.updateMember(mb);
	
	if(re==1){
%>
	<script>
		alert("입력하신대로 회원정보가 수정되었습니다.");
		document.location.href='main.jsp';
	</script>
<%
	}else{
%>
	<script>
		alert("수정이 실패하였습니다.");
		history.go(-1);
	</script>
<%
	}
%>	