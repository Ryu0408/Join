<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import="magic.board.*"%> 
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
String pageNUM=request.getParameter("pageNUM");
int bid=Integer.parseInt(request.getParameter("b_id"));

BoardDBBean db=BoardDBBean.getInstance();
BoardBean board=db.getBoard(bid, true);

SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");

String b_name="", b_title="", b_content="", b_email="", b_ip="";
int b_id=0, b_hit=0;
int b_ref=0, b_step=0, b_level=0;

Timestamp b_date=null;

if(board!=null){
	b_id=board.getB_id();           // 1. �۹�ȣ
	b_name=board.getB_name();       // 2. �ۼ���
	b_email=board.getB_email();     // 3. �̸���
	b_title=board.getB_title();     // 4. ������
	b_content=board.getB_content(); // 5. �۳���
	b_date=board.getB_date();       // 7. �ۼ���
	b_hit=board.getB_hit();         // 8. ��ȸ��
	b_ip=board.getB_ip();           // 9. IP �ּ�
 
    b_ref=board.getB_ref();         //10.�� �׷� ��ȣ
	b_step=board.getB_step();       //11.ȭ�鿡 ��µǴ� �� ��ġ
	b_level=board.getB_level();	    //12.�亯 ����
}	
%>

<html>
<head><title></title></head>
<body>
<center><h1>�� �� �� �� ��</h1>

<table border="1" cellpadding="0" cellspacing="0" width="600">
  <tr height="30" align="center">
  <td width="100">�۹�ȣ</td>
  <td width="200"><%=b_id%></td>
  <td width="100">��ȸ��</td>
  <td width="200"><%=b_hit%></td>
  </tr>
  
  <tr height="30"  align="center">
  <td width="100">�ۼ���</td>
  <td width="200"><%=b_name%> &nbsp; </td>
  <td width="100">�ۼ���</td>
  <td width="200"><%=sdf.format(b_date)%></td>
  </tr>
  
  <tr height="30">
  <td width="100" align="center">������</td>
  <td colspan="3"><%=b_title%> &nbsp; </td>
  </tr>
  
  <tr height="25">
  <td width="100"  align="center">�۳���</td>
  <td colspan="3"><pre><%=b_content%></pre></td>
  </tr>
   
  <tr height="30">
  <td colspan="4" align="right">
  <input type="button" value="�ۼ���" 
    onclick="location.href='edit.jsp?b_id=<%=b_id%>&pageNUM=<%=pageNUM%>'">
    &nbsp;&nbsp;&nbsp;&nbsp;
  <input type="button" value="�ۻ���" 
    onclick="location.href='delete.jsp?b_id=<%=b_id%>&pageNUM=<%=pageNUM%>'">
    &nbsp;&nbsp;&nbsp;&nbsp;  
  <input type="button" value="�亯��" 
    onclick="location.href='write.jsp?b_id=<%=b_id%>&pageNUM=<%=pageNUM%>'">
    &nbsp;&nbsp;&nbsp;&nbsp;  
  <input type="button" value="�۸��" 
    onclick="location.href='list.jsp?&pageNUM=<%=pageNUM%>'"/>&nbsp;
  </td>
  </tr>
</table>
</body>
</html>