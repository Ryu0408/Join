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
		alert("중복되는 아이디가 존재합니다.");
		history.back();
	</script>
<%
	}else{
		int re=manager.insertMember(mb);
		
		if(re==1){
			session.setAttribute("uid", mb.getMem_uid());
%>
	<script language=javascript>
		alert("회원가입을 축하드립니다. \n회원으로 로그인 해주세요.");
		document.location.href='login.jsp';
	</script> 
<%
		}else{
%>
	<script language=javascript>
		alert("회원가입에 실패했습니다.");
		document.location.href='login.jsp';
	</script>
<%
		}
	}
%>	