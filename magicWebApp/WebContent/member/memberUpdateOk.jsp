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
		alert("�Է��ϽŴ�� ȸ�������� �����Ǿ����ϴ�.");
		document.location.href='main.jsp';
	</script>
<%
	}else{
%>
	<script>
		alert("������ �����Ͽ����ϴ�.");
		history.go(-1);
	</script>
<%
	}
%>	