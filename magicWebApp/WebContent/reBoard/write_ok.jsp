<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import="magic.board.*"%> 
<%@ page import="java.sql.Timestamp"%>

<!-- write.jsp 폼 페이지에서 입력한 내용을 가져 온다. -->
<jsp:useBean id="board" class="magic.board.BoardBean">
<jsp:setProperty name="board" property="*" /> 
</jsp:useBean>

<% 
//클라이언트의 ip 주소와 글을 쓴 날짜는 파라미터로 넘어오지 않기에
//따로 얻어와서 세팅해야 한다. 

board.setB_ip(request.getRemoteAddr());
board.setB_date(new Timestamp(System.currentTimeMillis()));

//입력한 내용을 디비에 저장하기 위해서 BoardDBBean 객체 인스턴스를 얻어온다. 
BoardDBBean db=BoardDBBean.getInstance();

//BoardDBBean 객체의 insertBoard() 메소드를 호출하여  
//폼 페이지(write.jsp)에서 입력한 내용을 board 테이블에저장한다. 
if(db.insertBoard(board)==1)        //글 올리기에 성공했다면
  response.sendRedirect("list.jsp"); //list.jsp 페이지로 이동
else                                //글 올리기에 실패했다면                                
  response.sendRedirect("write.jsp");//write.jsp 페이지로 이동
%>