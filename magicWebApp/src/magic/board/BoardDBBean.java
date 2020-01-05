package magic.board;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;
import myUtil.HanConv;

public class BoardDBBean {
 private static BoardDBBean instance = new BoardDBBean();  
 
 // 1) 전역 BoardDBBean 객체 레퍼런스를 리턴하는 메소드  
 public static BoardDBBean getInstance( ){
   return instance;
 }
 
 // 2)쿼리작업에 사용할 커넥션 객체를 리턴하는 메소드 
 public Connection getConnection( ) throws Exception{
   //InitialContext 객체를 생성
   Context ctx=new InitialContext();
   //jdbc/oracle이란 이름을 객체를 찾아서 DataSource가 받는다. 
   DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
   
   //ds가 생성되었으므로 Connection을 구한다. 
   return ds.getConnection(); 
 } 
 
 // 3)전달인자로 받은 board를 board 테이블에 삽입하는 메소드 
 public int insertBoard(BoardBean board) throws Exception{
   Connection  con=null;            
   PreparedStatement pstmt=null;
   ResultSet rs=null;
   
   String sql="";  
   int re=-1;
   
   int id=board.getB_id();
   int ref=board.getB_ref();
   int step=board.getB_step();
   int level=board.getB_level();
   
   int number=0;
   
   try{ 
     con=getConnection();
     
     //현재 레코드 중에서 가장 큰 글 번호를 얻어낸다. 
     //이것을 글을 그룹화할때 그룹 아이디로 사용할 것이다. 
     sql="select max(b_id) from board"; 
     pstmt=con.prepareStatement(sql);
     rs=pstmt.executeQuery();  
     
     if(rs.next())            //이미 입력된 글이 있다면 
       number=rs.getInt(1)+1; //입력된 글 중에서 가장 큰 글번호에서 1을 증가시킨다.
     else                     //첫번째 올리는 글이라면
       number=1;              //1로 초기화한한다.
     //이렇게 구해진 number는 그룹 아이디값이 된다. 
     
     if(id!=0){  //답글이라면 부모글들을 찾아 그 중에서 현재글(원부모글)보다 
       //b_step이 큰 글들을 찾아서 부모글들의 b_step값을 모두 1씩 더한다. 
       sql="update board set b_step=b_step+1 where b_ref=? and b_step > ?";
       pstmt=con.prepareStatement(sql);
       pstmt.setInt(1,ref);
       pstmt.setInt(2,step);
       pstmt.executeUpdate();
       //답글의 ref는 원부모글의 ref를 그대로 가져다 사용한다. 
       step=step+1;       //글 순서를 부모 글보다 1 증가시킨다. 
       level=level+1;     //글 레벨 역시 부모 글보다 1 증가시킨다. 
     }
     else{ //답글이 아니라면
       ref=number; //그룹 아이디값은 1이고,
       step=0;     //글 순서와 
       level=0;    //글 레벨은 0이다.
     }
     sql="insert into board(b_id, b_name, b_email, b_title, ";
//     sql+="b_content, b_pwd, b_date, b_ip, b_ref, b_step, b_level, ";
     sql+="b_content, b_pwd, b_date, b_ip, b_ref, b_step, b_level) ";
     //sql+="b_fname, b_fsize) ";
     //sql+="values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
     sql+="values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
     pstmt = con.prepareStatement(sql);
     pstmt.setInt(1,number);
     pstmt.setString(2,HanConv.toKor(board.getB_name()));
     pstmt.setString(3,board.getB_email());     
     pstmt.setString(4,HanConv.toKor(board.getB_title()));
     pstmt.setString(5,HanConv.toKor(board.getB_content()));
     pstmt.setString(6,board.getB_pwd());
     pstmt.setTimestamp(7,board.getB_date());
     pstmt.setString(8,board.getB_ip());
     pstmt.setInt(9,ref);
     pstmt.setInt(10,step);
     pstmt.setInt(11,level);
//     pstmt.setString(12,HanConv.toKor(board.getB_fname()));
//     pstmt.setInt(13,board.getB_fsize()); 
     pstmt.executeUpdate();
       
     re=1;
   }catch (Exception e) {
	 e.printStackTrace();
   }finally{
	 try{
	   if(rs!=null)	    rs.close();
	   if(pstmt!=null)	pstmt.close();
	   if(con!=null)    con.close(); 
	 }catch (Exception e) {
		 e.printStackTrace();
	 }
   }
   return re;
 }
 
 //4) 특정 페이지에 표시될 게시글들을 컬렉션 객체로 얻어오는 메서드
 public ArrayList<BoardBean> listBoard(String pageNumber){  
   Connection  con=null;    
   Statement stmt =null;
   ResultSet pageset=null;
   ResultSet rs = null;	
   
   // 해당 페이지에 DB에서 가져올 첫번째 게시글의 위치의 절대위치
   int absolutepage=1;  
   int dbcount=0 ;      // DB 안에 등록되어 있는 글의 개수 저장 변수
   
   ArrayList<BoardBean> boardList=new ArrayList<BoardBean>();
   
   try {	     
     // 페이지 변수값 사용하기 위해서 DB에서 추출하는 부분
	 con=getConnection();
     stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
    		 ResultSet.CONCUR_UPDATABLE);  
     pageset = stmt.executeQuery("select count(b_id) from board");
     
     if(pageset.next()){
       dbcount = pageset.getInt(1);  // 글 총 갯수
       pageset.close();
     }
	 if(dbcount%BoardBean.pagesize == 0)   
	   BoardBean.pagecount = dbcount/(BoardBean.pagesize); // 총 페이지수 구하기
	 else
	   BoardBean.pagecount = dbcount/(BoardBean.pagesize)+1; // 총 페이지수 구하기
	 
	 if(pageNumber!=null) {
	   BoardBean.pageNUM=Integer.parseInt(pageNumber);//지정된 페이지 보여주기
	   absolutepage=(BoardBean.pageNUM-1)*BoardBean.pagesize+1;
	 }
	 //그룹 아이디값이 큰것 부터 출력, 같은 그룹내에서는 글 순서가 작은 것부터 출력
	 String str = "select * from  board order by b_ref desc, b_step asc";
	 rs = stmt.executeQuery(str);
	 
	 if(rs.next()){     
	   rs.absolute(absolutepage); 	   
	   int count = 0 ; //페이지 카운터 변수 
	   while(count < BoardBean.pagesize){ 
	     BoardBean board=new BoardBean();
		 board.setB_id(rs.getInt(1));          // 1. 글번호
		 board.setB_name(rs.getString(2));     // 2. 글쓴이
		 board.setB_email(rs.getString(3));    // 3. 작성자 메일
		 board.setB_title(rs.getString(4));    // 4. 글제목
		 board.setB_content(rs.getString(5));  // 5. 글내용
		 board.setB_pwd(rs.getString(6));      // 6. 비밀번호
		 board.setB_date(rs.getTimestamp(7));  // 7. 작성날짜
		 board.setB_hit(rs.getInt(8));         // 8. 조회수
		 board.setB_ip(rs.getString(9));       // 9. 글쓴이 아이피 주소 
		 board.setB_ref(rs.getInt(10));        //10. 글 그룹 번호
		 board.setB_step(rs.getInt(11));       //11. 화면에 출력되는 글 위치
		 board.setB_level(rs.getInt(12));      //12. 글 레벨
		 
//		 board.setB_fname(rs.getString(13));   //13. 파일 이름
//		 board.setB_fsize(rs.getInt(14));      //14. 파이 크기
		 
		 boardList.add(board);
		 
		 if(rs.isLast()){ //맨 마지막 레코드 일경우
		   break;
		 }else{
		   rs.next();
		 }
		 count++;
	   }//while 끝..	   
	 }	 
   }catch (Exception e) {
	 e.printStackTrace();  
   }finally{
	 try{
	   if(rs!=null)	   rs.close();
	   if(stmt!=null)	stmt.close(); 
	   if(con!=null)    con.close(); 
	 }catch(Exception e){
	   e.printStackTrace(); 
	 }
   } 
   return boardList;
 }
 //5)board 테이블에서 b_id 필드 값이 bid인 행을 검색해서
 //BoardBean 객체로 리턴하는 메소드
 public BoardBean getBoard(int bid, boolean hitadd){
   Connection con = null;   
   PreparedStatement pstmtUp=null;
   PreparedStatement pstmt=null;
   ResultSet rs=null;
   String sql="";
   
   BoardBean board=null;
   try { 
	 con=getConnection();
	 if(hitadd==true){
	 //조회수를 올린다
	 sql="update board set b_hit=b_hit+1 where b_id=?";
	 pstmtUp=con.prepareStatement(sql);
	 pstmtUp.setInt(1, bid);
	 pstmtUp.executeUpdate(); 
	 pstmtUp.close();
	 }
     //bid에 해당되는 게시물의 정보를 얻어오기
	 sql="select * from board where b_id=?"; 
	 pstmt=con.prepareStatement(sql);
	 pstmt.setInt(1, bid);
	 rs = pstmt.executeQuery();  
	 if(rs.next()) { 
	   board=new BoardBean();	 
	   board.setB_id(rs.getInt(1));          // 1. 글번호
	   board.setB_name(rs.getString(2));     // 2. 글쓴이
	   board.setB_email(rs.getString(3));    // 3. 작성자 메일
	   board.setB_title(rs.getString(4));    // 4. 글제목
	   board.setB_content(rs.getString(5));  // 5. 글내용
	   board.setB_pwd(rs.getString(6));      // 6. 비밀번호
	   board.setB_date(rs.getTimestamp(7));  // 7. 작성날짜
	   board.setB_hit(rs.getInt(8));         // 8. 조회수
	   board.setB_ip(rs.getString(9));       // 9. 글쓴이 아이피 주소 
	   board.setB_ref(rs.getInt(10));        //10. 글 그룹 번호
 	   board.setB_step(rs.getInt(11));       //11. 화면에 출력되는 글 위치
  	   board.setB_level(rs.getInt(12));      //12. 글 레벨
//  	   board.setB_fname(rs.getString(13));   //13. 파일 이름
//  	   board.setB_fsize(rs.getInt(14));   //14. 파일 크기
	 }
   }catch (Exception e) {
	   e.printStackTrace();
   }finally{
	 try{
	   if(rs!=null)	    rs.close();
	   if(pstmt!=null)	pstmt.close();
	   if(con!=null)    con.close(); 
	 }catch (Exception e) {
	   e.printStackTrace();
	 }
   }
   return board;
 }

 //6)board 테이블의 내용을 수정하는 메소드
 public int editBoard(BoardBean board){
   Connection con = null;   
   PreparedStatement pstmt=null;
   ResultSet rs=null;
   
   String sql="";
   String pwd="";
   
   int re=-1;
   try { 
	 con=getConnection();
	 	 
	 sql="select b_pwd from board where b_id=?"; 
	 pstmt=con.prepareStatement(sql);
	 pstmt.setInt(1, board.getB_id());
	 rs = pstmt.executeQuery(); 
	 
	 if(rs.next()) {
	   pwd=rs.getString(1);	   
	   if(!pwd.equals(board.getB_pwd()))
		 re=0;
	   else{
		 sql="update board set b_name=?, b_email=?, b_title=?,";
         sql+="b_content=? where b_id=?";
     
         pstmt=con.prepareStatement(sql);
     
         pstmt.setString(1, HanConv.toKor(board.getB_name()));
         pstmt.setString(2, HanConv.toKor(board.getB_email()));
         pstmt.setString(3, HanConv.toKor(board.getB_title()));
         pstmt.setString(4, HanConv.toKor(board.getB_content()));
         pstmt.setInt(5, board.getB_id());     
    
         pstmt.executeUpdate();
         re=1;
	   }//else
	 }//if
   }catch (Exception e) {
	   e.printStackTrace();
   }finally{
	 try{
	   if(rs!=null)	    rs.close();
	   if(pstmt!=null)	pstmt.close();
	   if(con!=null)    con.close(); 
	 }catch (Exception e) {
	   e.printStackTrace();
	 }
   }
   return re;
 }
 
 //7)board 테이블의 내용을 삭제하는 메소드
 public int deleteBoard(int b_id, String b_pwd){
   Connection con = null;   
   PreparedStatement pstmt=null;
   ResultSet rs=null;
   
   String sql="";
   String pwd="";
   
   int re=-1;
   try { 
	 con=getConnection();
	 	 
	 sql="select b_pwd from board where b_id=?"; 
	 pstmt=con.prepareStatement(sql);
	 pstmt.setInt(1, b_id);
	 rs = pstmt.executeQuery(); 	 
	 
	 if(rs.next()) {
	   pwd=rs.getString(1);	   
	   if(!pwd.equals(b_pwd))
		 re=0;
	   else{
		 sql="delete board where b_id=?";
     
         pstmt=con.prepareStatement(sql);
     
         pstmt.setInt(1, b_id);     
    
         pstmt.executeUpdate();
         re=1;
	   }//else
	 }//if
   }catch (Exception e) {
	   e.printStackTrace();
   }finally{
	 try{
	   if(rs!=null)	    rs.close();
	   if(pstmt!=null)	pstmt.close();
	   if(con!=null)    con.close(); 
	 }catch (Exception e) {
	   e.printStackTrace();
	 }
   }
   return re;
 }
}