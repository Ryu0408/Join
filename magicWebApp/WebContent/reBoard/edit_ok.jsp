<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import="magic.board.*"%> 

<!-- edit.jsp �� ���������� �Է��� ������ ���� �´�. -->
<jsp:useBean id="board" class="magic.board.BoardBean">
<jsp:setProperty name="board" property="*" /> 
</jsp:useBean>
<%
String pageNUM=request.getParameter("pageNUM");

int b_id=Integer.parseInt(request.getParameter("b_id"));
board.setB_id(b_id);

BoardDBBean db=BoardDBBean.getInstance();
//BoardDBBean ��ü�� editBoard() �޼ҵ带 ȣ���Ͽ�  
//�� ������(edit.jsp)���� �Է��� �������� board ���̺��� �����Ѵ�.
int re=db.editBoard(board);

if(re==1){        //�� ������ �����ߴٸ�
  response.sendRedirect("list.jsp?pageNUM="+pageNUM);
}else if(re==0){%>
  <script language="JavaScript">
    alert("��й�ȣ�� ���� �ʽ��ϴ�."); 
    history.go(-1);
  </script>
  <%}else if(re==-1){%>
  <script language="JavaScript">
    alert("������ �����Ͽ����ϴ�.");  
    history.go(-1); 
  </script>
<%}%>