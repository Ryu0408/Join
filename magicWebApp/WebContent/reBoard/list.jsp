<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import="magic.board.*"%> 
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%
String pageNUM=request.getParameter("pageNUM");
if(pageNUM == null)
	pageNUM="1";

BoardDBBean db=BoardDBBean.getInstance();

ArrayList<BoardBean> boardList=db.listBoard(pageNUM);

SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd  HH:mm");

String b_name, b_email, b_title, b_content;
int b_id=0, b_hit=0, b_level=0;
Timestamp b_date;

int count=0 ; //������ ī���� ����
int i,k;
%>

<html>
<head><title></title></head>

<body>
<center><h1>�Խ��ǿ� ��ϵ� �� ��� ����</h1>
<table width="600">
<tr>
<td align="right">
<a href="write.jsp" aligh="right"> �� �� �� </a></td>
</tr>
</table>

<table width="800" border="1" cellpadding="0" cellspacing="0">
<tr height="25">
<td width="40" align="center">��ȣ</td>
<td width="450" align="center">������</td>
<td width="120" align="center">�ۼ���</td>
<td width="130" align="center">�ۼ���</td>
<td width="60" align="center">��ȸ��</td>  
</tr>

<%
for(i=0; i<boardList.size(); i++) {
  BoardBean board=boardList.get(i);
  b_id=board.getB_id();
  b_level=board.getB_level();
  b_title=board.getB_title();
  b_name=board.getB_name();
  b_email=board.getB_email();
  b_date=board.getB_date();
  b_hit=board.getB_hit();
%>
  <tr height="25" bgcolor="#F7F7F7" 
    onmouseover="this.style.backgroundColor='#eeeeef'" 
    onmouseout="this.style.backgroundColor='#f7f7f7'">

  <td align="center">
  <%=b_id%>
  </td>

  <td>
   <% if(b_level>0) { 
     for(k=0; k< b_level; k++){
     %>&nbsp;<%}%>
     <img src="../images/AnswerLine.gif" width="16" height="16"border=0>    
  <% } %>
  <a href="show.jsp?b_id=<%=b_id%>&pageNUM=<%=pageNUM%>">  
  <%=b_title%>
  </a>
  </td>
  
  <td align="center">
  <a href="mailto:<%=b_email %>">
  <%=b_name%>
  </a>
  </td>
  <td align="center"><%=sdf.format(b_date)%></td>
  <td align="center"><%=b_hit%></td>
  </tr>
 <%} //for�� ��... %> 
</table>
<br><br>
<center>
 <!--  ������ ������ : ȭ��� �ִ� 4 �� ��� -->
<%=BoardBean.pageNumber(4)%>  
</center>
</body>
</html>