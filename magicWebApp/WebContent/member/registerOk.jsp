<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<%@ page import="magic.member.*"%>

<% request.setCharacterEncoding("euc-kr"); %>

<jsp:useBean id="mb" class="magic.member.MemberBean" scope="page"></jsp:useBean>

<jsp:setProperty property="*" name="mb"/>

<%
	mb.setMem_regdate(new Timestamp(System.currentTimeMillis()));

	MemberDBBean manager=MemberDBBean.getInstance();
	
	if(manager.confirmID(mb.getMem_uid()) == 1){
%>
	<script language=javascript>
		alert("�ߺ��Ǵ� ���̵� �����մϴ�.");
		history.back();
	</script>
<%
	}else{
		int re=manager.insertMember(mb);
		
		if(re==1){
			session.setAttribute("uid", mb.getMem_uid());
%>
	<script language=javascript>
		alert("ȸ�������� ���ϵ帳�ϴ�. \nȸ������ �α��� ���ּ���.");
		document.location.href='login.jsp';
	</script> 
<%
		}else{
%>
	<script language=javascript>
		alert("ȸ�����Կ� �����߽��ϴ�.");
		document.location.href='login.jsp';
	</script>
<%
		}
	}
%>	