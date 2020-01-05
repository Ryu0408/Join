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
	b_id=board.getB_id();           // 1. 글번호
	b_name=board.getB_name();       // 2. 작성자
	b_email=board.getB_email();     // 3. 이메일
	b_title=board.getB_title();     // 4. 글제목
	b_content=board.getB_content(); // 5. 글내용
	b_date=board.getB_date();       // 7. 작성일
	b_hit=board.getB_hit();         // 8. 조회수
	b_ip=board.getB_ip();           // 9. IP 주소
 
    b_ref=board.getB_ref();         //10.글 그룹 번호
	b_step=board.getB_step();       //11.화면에 출력되는 글 위치
	b_level=board.getB_level();	    //12.답변 순위
}	
%>

<html>
<head><title></title></head>
<body>
<center><h1>글 내 용 보 기</h1>

<table border="1" cellpadding="0" cellspacing="0" width="600">
  <tr height="30" align="center">
  <td width="100">글번호</td>
  <td width="200"><%=b_id%></td>
  <td width="100">조회수</td>
  <td width="200"><%=b_hit%></td>
  </tr>
  
  <tr height="30"  align="center">
  <td width="100">작성자</td>
  <td width="200"><%=b_name%> &nbsp; </td>
  <td width="100">작성일</td>
  <td width="200"><%=sdf.format(b_date)%></td>
  </tr>
  
  <tr height="30">
  <td width="100" align="center">글제목</td>
  <td colspan="3"><%=b_title%> &nbsp; </td>
  </tr>
  
  <tr height="25">
  <td width="100"  align="center">글내용</td>
  <td colspan="3"><pre><%=b_content%></pre></td>
  </tr>
   
  <tr height="30">
  <td colspan="4" align="right">
  <input type="button" value="글수정" 
    onclick="location.href='edit.jsp?b_id=<%=b_id%>&pageNUM=<%=pageNUM%>'">
    &nbsp;&nbsp;&nbsp;&nbsp;
  <input type="button" value="글삭제" 
    onclick="location.href='delete.jsp?b_id=<%=b_id%>&pageNUM=<%=pageNUM%>'">
    &nbsp;&nbsp;&nbsp;&nbsp;  
  <input type="button" value="답변글" 
    onclick="location.href='write.jsp?b_id=<%=b_id%>&pageNUM=<%=pageNUM%>'">
    &nbsp;&nbsp;&nbsp;&nbsp;  
  <input type="button" value="글목록" 
    onclick="location.href='list.jsp?&pageNUM=<%=pageNUM%>'"/>&nbsp;
  </td>
  </tr>
</table>
</body>
</html>