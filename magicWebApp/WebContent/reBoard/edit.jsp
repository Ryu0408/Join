<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import="magic.board.*"%> 

<% 
String pageNUM=request.getParameter("pageNUM");

BoardDBBean db=BoardDBBean.getInstance();

int b_id=Integer.parseInt(request.getParameter("b_id"));

BoardBean board=db.getBoard(b_id, false);
%>

<html>
<head><title></title>
<!-- ���� �Էµ� ������ �ùٸ��� �Ǵ��ϴ� �ڹٽ�ũ��Ʈ �κ� -->
<script language="JavaScript" src="board.js"></script>
</head>
<body>
<center><h1>�� �� �� �� ��</h1>
<form name="form" method="post" action="edit_ok.jsp?b_id=<%=b_id%>&pageNUM=<%=pageNUM%>">

<table border="0" cellpadding="0" cellspacing="0">
<tr height="30">
  <td width="80">�ۼ���</td>
  <td width="140">
  <input type="text" name="b_name" maxlength="15" size="10" value="<%=board.getB_name()%>"></td>
  
  <td width="80">�̸���</td>
  <td width="240">
  <input type="text" name="b_email" maxlength="30" size="24" value="<%=board.getB_email()%>"></td>
</tr>

<tr height="30">
  <td width="80">������</td>
  <td colspan="3" width="460">
  <input type="text" name="b_title" maxlength="60" size="55" value="<%=board.getB_title()%>">
  </td>
</tr>

<tr>
<td colspan="4" width="480">
<textarea name="b_content" rows="10" cols="65">
<%=board.getB_content()%>
</textarea>
</td>
</tr>  

<tr>
  <td width="80">��&nbsp;&nbsp;ȣ</td>
  <td colspan="3" width="460">
  <input type="password" name="b_pwd" maxlength="12" size="12"></td>
  </td>
</tr> 

<tr height="50" align="center">
  <td colspan="4" width="480">
  <input type="button" value="�ۼ���" onclick="check_ok()"> &nbsp;&nbsp;&nbsp;
  <input type="reset"  value="�ٽ��ۼ�"> &nbsp;
  <input type="button" value="�۸��" onclick="location.href='list.jsp?pageNUM=<%=pageNUM%>'">
  </td>
</tr>        
</table>