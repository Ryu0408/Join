<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import="magic.board.*"%> 

<%
String pageNUM=request.getParameter("pageNUM");

int b_id=Integer.parseInt(request.getParameter("b_id"));
String b_pwd=request.getParameter("b_pwd");

BoardDBBean db=BoardDBBean.getInstance();
//BoardDBBean 객체의 deleteBoard() 메소드를 호출하여 글을 삭제한다. 
int re=db.deleteBoard(b_id, b_pwd);

if(re==1){        //글 삭제에 성공했다면
  response.sendRedirect("list.jsp?pageNUM="+pageNUM);
}else if(re==0){%>
  <script language="JavaScript">
   alert("비밀번호가 맞지 않습니다."); history.go(-1);
   </script>
<%}else if(re==-1){%>
  <script language="JavaScript">
   alert("삭제에 실패하였습니다.");  history.go(-1);
  </script>
<%}%>