<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import="magic.board.*"%> 

<% 
String pageNUM=request.getParameter("pageNUM");
int b_id=Integer.parseInt(request.getParameter("b_id"));
%>

<html>
<head><title></title>
<!-- ���� �Էµ� ������ �ùٸ��� �Ǵ��ϴ� �ڹٽ�ũ��Ʈ �κ� -->
<script language="JavaScript" src="board.js"></script>
</head>

<body>
<center><h1>�� �� �� �� ��</h1>
<form name="form" method="post" action="delete_ok.jsp?b_id=<%=b_id%>&pageNUM=<%=pageNUM%>">

<table border="0" cellpadding="0" cellspacing="0">
<tr height="50">
  <td colspan="2" align="left">
  <b>  >> ��ȣ�� �Է��ϼ���. <<  </b>
  </td>
 </tr> 
<tr>
  <td width="80">��&nbsp;&nbsp;ȣ</td>
  <td>
  <input type="password" name="b_pwd" maxlength="12" size="12"></td>
  </td>
</tr> 

<tr height="50" align="center">
  <td colspan="2">
  <input type="button" value="�ۻ���" onclick="delete_ok()"> &nbsp;&nbsp;&nbsp;
  <input type="reset"  value="�ٽ��ۼ�"> &nbsp;
  <input type="button" value="�۸��" onclick="location.href='list.jsp?pageNUM=<%=pageNUM%>'">
  </td>
</tr>        
</table>