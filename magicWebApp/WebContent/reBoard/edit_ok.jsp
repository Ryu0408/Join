<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import="magic.board.*"%> 

<!-- edit.jsp 폼 페이지에서 입력한 내용을 가져 온다. -->
<jsp:useBean id="board" class="magic.board.BoardBean">
<jsp:setProperty name="board" property="*" /> 
</jsp:useBean>
<%
String pageNUM=request.getParameter("pageNUM");

int b_id=Integer.parseInt(request.getParameter("b_id"));
board.setB_id(b_id);

BoardDBBean db=BoardDBBean.getInstance();
//BoardDBBean 객체의 editBoard() 메소드를 호출하여  
//폼 페이지(edit.jsp)에서 입력한 내용으로 board 테이블을 수정한다.
int re=db.editBoard(board);

if(re==1){        //글 수정에 성공했다면
  response.sendRedirect("list.jsp?pageNUM="+pageNUM);
}else if(re==0){%>
  <script language="JavaScript">
    alert("비밀번호가 맞지 않습니다."); 
    history.go(-1);
  </script>
  <%}else if(re==-1){%>
  <script language="JavaScript">
    alert("수정에 실패하였습니다.");  
    history.go(-1); 
  </script>
<%}%>