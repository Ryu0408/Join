<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import="magic.board.*"%> 

<html>
<head><title></title>
<!-- ���� �Էµ� ������ �ùٸ��� �Ǵ��ϴ� �ڹٽ�ũ��Ʈ �κ� -->
<script language="JavaScript" src="board.js"></script>
</head>
<%
int b_id=0, b_ref=1, b_step=0, b_level=0;
String b_name="", b_title="";

//out.println("@@@###b_id===>"+request.getParameter("b_id"));

if(request.getParameter("b_id")!=null){  
  //�亯 ���� ��쿡�� �̺κ��� ����ȴ�.
  b_id=Integer.parseInt(request.getParameter("b_id")); 
}

BoardDBBean db=BoardDBBean.getInstance();
BoardBean board=db.getBoard(b_id, false);
if(board!=null){
	//b_name=board.getB_name();       // 2. �ۼ���
	b_title=board.getB_title();     // 4. ������

	b_ref=board.getB_ref();         //10.�� �׷� ��ȣ
	b_step=board.getB_step();       //11.ȭ�鿡 ��µǴ� �� ��ġ
	b_level=board.getB_level();	    //12.�亯 ����
}	
%>
<body>
<center><h1>�� �� �� ��</h1>
<form name="form" method="post" action="write_ok.jsp">

<input type="hidden" name="b_id" value="<%=b_id%>">
<input type="hidden" name="b_ref" value="<%=b_ref%>">
<input type="hidden" name="b_step" value="<%=b_step%>">
<input type="hidden" name="b_level" value="<%=b_level%>">

<table border="0" cellpadding="0" cellspacing="0">
<tr height="30">
  <td width="80">�ۼ���</td>
  <td width="140">
  <input type="text" name="b_name" maxlength="15" size="10"></td>
  
  <td width="80">�̸���</td>
  <td width="240">
  <input type="text" name="b_email" maxlength="30" size="24"></td>
</tr>

<tr height="30">
  <td width="80">������</td>
  <td colspan="3" width="460">
  <%if(b_id==0){%>
  <input type="text" name="b_title" maxlength="60" size="55">
  <%}else{%>
  <input type="text" name="b_title" maxlength="60" size="55" 
  value="[�亯]:<%=b_title%>">
  <%}%>
  </td>
</tr>

<tr>
  <td colspan="4">
  <textarea name="b_content" rows="10" cols="65"></textarea>
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
  <input type="button" value="�۾���" onclick="check_ok()"> &nbsp;
  <input type="reset"  value="�ٽ��ۼ�"> &nbsp;
  <input type="button" value="�۸��" onclick="location.href='list.jsp'">&nbsp;
  </td>
</tr>        
</table>
</form>
</center>
</body>
</html>