<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import="magic.board.*"%> 

<%
String pageNUM=request.getParameter("pageNUM");

int b_id=Integer.parseInt(request.getParameter("b_id"));
String b_pwd=request.getParameter("b_pwd");

BoardDBBean db=BoardDBBean.getInstance();
//BoardDBBean ��ü�� deleteBoard() �޼ҵ带 ȣ���Ͽ� ���� �����Ѵ�. 
int re=db.deleteBoard(b_id, b_pwd);

if(re==1){        //�� ������ �����ߴٸ�
  response.sendRedirect("list.jsp?pageNUM="+pageNUM);
}else if(re==0){%>
  <script language="JavaScript">
   alert("��й�ȣ�� ���� �ʽ��ϴ�."); history.go(-1);
   </script>
<%}else if(re==-1){%>
  <script language="JavaScript">
   alert("������ �����Ͽ����ϴ�.");  history.go(-1);
  </script>
<%}%>