<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="magic.member.*"%>
<%
	if(session.getAttribute("Member") == null){
%>
	<jsp:forward page="login.jsp"></jsp:forward>
<%
	}
%>

<%
	String name=(String)session.getAttribute("name");
	String uid=(String)session.getAttribute("uid");
%>
<html>
	<head></head>
	<body>
		<table border="1" align="center">
			<form action="logOut.jsp" method="post">
				<tr height="30">
					<td>
						�ȳ��ϼ���. <%=name %>(<%=uid %>>)��
					</td>
				</tr>
				<tr height="30">
					<td colspan="2" align="center">
						<input type="submit" value="�α׾ƿ�">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="ȸ����������"
								onclick="javascript:window.location='memberUpdate.jsp'">
					</td>
				</tr>
			</form>
		</table>
	</body>
</html>