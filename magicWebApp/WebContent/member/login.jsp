<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	if(session.getAttribute("Member") != null){
%>
<jsp:forward page='main.jsp'/>
<% } %>
<html>
	<head>
	</head>
	<body>
		<table border="1" align="center">
			<form action="loginOk.jsp" method="post">
				<tr height="30">
					<td width="100" align="center">�����ID</td>
					<td width="100">
						<input type="text" name="mem_uid" value="<%
						if(session.getAttribute("uid") != null)
							out.println(session.getAttribute("uid"));
						%>">
					</td>
				</tr>
				<tr height="30">
					<td width="100" align="center">��й�ȣ</td>
					<td width="100">
						<input type="password" name="mem_pwd">
					</td>
				</tr>
				<tr height="30">
					<td colspan="2" align="center">
						<input type="submit" value="�� �� ��">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="ȸ������"
							onclick="javascript:window.location='register.jsp'">
					</td>
				</tr>
			</form>
		</table>
	</body>
</html>