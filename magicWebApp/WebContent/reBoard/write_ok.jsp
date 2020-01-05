<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import="magic.board.*"%> 
<%@ page import="java.sql.Timestamp"%>

<!-- write.jsp �� ���������� �Է��� ������ ���� �´�. -->
<jsp:useBean id="board" class="magic.board.BoardBean">
<jsp:setProperty name="board" property="*" /> 
</jsp:useBean>

<% 
//Ŭ���̾�Ʈ�� ip �ּҿ� ���� �� ��¥�� �Ķ���ͷ� �Ѿ���� �ʱ⿡
//���� ���ͼ� �����ؾ� �Ѵ�. 

board.setB_ip(request.getRemoteAddr());
board.setB_date(new Timestamp(System.currentTimeMillis()));

//�Է��� ������ ��� �����ϱ� ���ؼ� BoardDBBean ��ü �ν��Ͻ��� ���´�. 
BoardDBBean db=BoardDBBean.getInstance();

//BoardDBBean ��ü�� insertBoard() �޼ҵ带 ȣ���Ͽ�  
//�� ������(write.jsp)���� �Է��� ������ board ���̺������Ѵ�. 
if(db.insertBoard(board)==1)        //�� �ø��⿡ �����ߴٸ�
  response.sendRedirect("list.jsp"); //list.jsp �������� �̵�
else                                //�� �ø��⿡ �����ߴٸ�                                
  response.sendRedirect("write.jsp");//write.jsp �������� �̵�
%>