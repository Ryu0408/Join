package magic.board;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;
import myUtil.HanConv;

public class BoardDBBean {
 private static BoardDBBean instance = new BoardDBBean();  
 
 // 1) ���� BoardDBBean ��ü ���۷����� �����ϴ� �޼ҵ�  
 public static BoardDBBean getInstance( ){
   return instance;
 }
 
 // 2)�����۾��� ����� Ŀ�ؼ� ��ü�� �����ϴ� �޼ҵ� 
 public Connection getConnection( ) throws Exception{
   //InitialContext ��ü�� ����
   Context ctx=new InitialContext();
   //jdbc/oracle�̶� �̸��� ��ü�� ã�Ƽ� DataSource�� �޴´�. 
   DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
   
   //ds�� �����Ǿ����Ƿ� Connection�� ���Ѵ�. 
   return ds.getConnection(); 
 } 
 
 // 3)�������ڷ� ���� board�� board ���̺� �����ϴ� �޼ҵ� 
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
     
     //���� ���ڵ� �߿��� ���� ū �� ��ȣ�� ����. 
     //�̰��� ���� �׷�ȭ�Ҷ� �׷� ���̵�� ����� ���̴�. 
     sql="select max(b_id) from board"; 
     pstmt=con.prepareStatement(sql);
     rs=pstmt.executeQuery();  
     
     if(rs.next())            //�̹� �Էµ� ���� �ִٸ� 
       number=rs.getInt(1)+1; //�Էµ� �� �߿��� ���� ū �۹�ȣ���� 1�� ������Ų��.
     else                     //ù��° �ø��� ���̶��
       number=1;              //1�� �ʱ�ȭ���Ѵ�.
     //�̷��� ������ number�� �׷� ���̵��� �ȴ�. 
     
     if(id!=0){  //����̶�� �θ�۵��� ã�� �� �߿��� �����(���θ��)���� 
       //b_step�� ū �۵��� ã�Ƽ� �θ�۵��� b_step���� ��� 1�� ���Ѵ�. 
       sql="update board set b_step=b_step+1 where b_ref=? and b_step > ?";
       pstmt=con.prepareStatement(sql);
       pstmt.setInt(1,ref);
       pstmt.setInt(2,step);
       pstmt.executeUpdate();
       //����� ref�� ���θ���� ref�� �״�� ������ ����Ѵ�. 
       step=step+1;       //�� ������ �θ� �ۺ��� 1 ������Ų��. 
       level=level+1;     //�� ���� ���� �θ� �ۺ��� 1 ������Ų��. 
     }
     else{ //����� �ƴ϶��
       ref=number; //�׷� ���̵��� 1�̰�,
       step=0;     //�� ������ 
       level=0;    //�� ������ 0�̴�.
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
 
 //4) Ư�� �������� ǥ�õ� �Խñ۵��� �÷��� ��ü�� ������ �޼���
 public ArrayList<BoardBean> listBoard(String pageNumber){  
   Connection  con=null;    
   Statement stmt =null;
   ResultSet pageset=null;
   ResultSet rs = null;	
   
   // �ش� �������� DB���� ������ ù��° �Խñ��� ��ġ�� ������ġ
   int absolutepage=1;  
   int dbcount=0 ;      // DB �ȿ� ��ϵǾ� �ִ� ���� ���� ���� ����
   
   ArrayList<BoardBean> boardList=new ArrayList<BoardBean>();
   
   try {	     
     // ������ ������ ����ϱ� ���ؼ� DB���� �����ϴ� �κ�
	 con=getConnection();
     stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
    		 ResultSet.CONCUR_UPDATABLE);  
     pageset = stmt.executeQuery("select count(b_id) from board");
     
     if(pageset.next()){
       dbcount = pageset.getInt(1);  // �� �� ����
       pageset.close();
     }
	 if(dbcount%BoardBean.pagesize == 0)   
	   BoardBean.pagecount = dbcount/(BoardBean.pagesize); // �� �������� ���ϱ�
	 else
	   BoardBean.pagecount = dbcount/(BoardBean.pagesize)+1; // �� �������� ���ϱ�
	 
	 if(pageNumber!=null) {
	   BoardBean.pageNUM=Integer.parseInt(pageNumber);//������ ������ �����ֱ�
	   absolutepage=(BoardBean.pageNUM-1)*BoardBean.pagesize+1;
	 }
	 //�׷� ���̵��� ū�� ���� ���, ���� �׷쳻������ �� ������ ���� �ͺ��� ���
	 String str = "select * from  board order by b_ref desc, b_step asc";
	 rs = stmt.executeQuery(str);
	 
	 if(rs.next()){     
	   rs.absolute(absolutepage); 	   
	   int count = 0 ; //������ ī���� ���� 
	   while(count < BoardBean.pagesize){ 
	     BoardBean board=new BoardBean();
		 board.setB_id(rs.getInt(1));          // 1. �۹�ȣ
		 board.setB_name(rs.getString(2));     // 2. �۾���
		 board.setB_email(rs.getString(3));    // 3. �ۼ��� ����
		 board.setB_title(rs.getString(4));    // 4. ������
		 board.setB_content(rs.getString(5));  // 5. �۳���
		 board.setB_pwd(rs.getString(6));      // 6. ��й�ȣ
		 board.setB_date(rs.getTimestamp(7));  // 7. �ۼ���¥
		 board.setB_hit(rs.getInt(8));         // 8. ��ȸ��
		 board.setB_ip(rs.getString(9));       // 9. �۾��� ������ �ּ� 
		 board.setB_ref(rs.getInt(10));        //10. �� �׷� ��ȣ
		 board.setB_step(rs.getInt(11));       //11. ȭ�鿡 ��µǴ� �� ��ġ
		 board.setB_level(rs.getInt(12));      //12. �� ����
		 
//		 board.setB_fname(rs.getString(13));   //13. ���� �̸�
//		 board.setB_fsize(rs.getInt(14));      //14. ���� ũ��
		 
		 boardList.add(board);
		 
		 if(rs.isLast()){ //�� ������ ���ڵ� �ϰ��
		   break;
		 }else{
		   rs.next();
		 }
		 count++;
	   }//while ��..	   
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
 //5)board ���̺��� b_id �ʵ� ���� bid�� ���� �˻��ؼ�
 //BoardBean ��ü�� �����ϴ� �޼ҵ�
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
	 //��ȸ���� �ø���
	 sql="update board set b_hit=b_hit+1 where b_id=?";
	 pstmtUp=con.prepareStatement(sql);
	 pstmtUp.setInt(1, bid);
	 pstmtUp.executeUpdate(); 
	 pstmtUp.close();
	 }
     //bid�� �ش�Ǵ� �Խù��� ������ ������
	 sql="select * from board where b_id=?"; 
	 pstmt=con.prepareStatement(sql);
	 pstmt.setInt(1, bid);
	 rs = pstmt.executeQuery();  
	 if(rs.next()) { 
	   board=new BoardBean();	 
	   board.setB_id(rs.getInt(1));          // 1. �۹�ȣ
	   board.setB_name(rs.getString(2));     // 2. �۾���
	   board.setB_email(rs.getString(3));    // 3. �ۼ��� ����
	   board.setB_title(rs.getString(4));    // 4. ������
	   board.setB_content(rs.getString(5));  // 5. �۳���
	   board.setB_pwd(rs.getString(6));      // 6. ��й�ȣ
	   board.setB_date(rs.getTimestamp(7));  // 7. �ۼ���¥
	   board.setB_hit(rs.getInt(8));         // 8. ��ȸ��
	   board.setB_ip(rs.getString(9));       // 9. �۾��� ������ �ּ� 
	   board.setB_ref(rs.getInt(10));        //10. �� �׷� ��ȣ
 	   board.setB_step(rs.getInt(11));       //11. ȭ�鿡 ��µǴ� �� ��ġ
  	   board.setB_level(rs.getInt(12));      //12. �� ����
//  	   board.setB_fname(rs.getString(13));   //13. ���� �̸�
//  	   board.setB_fsize(rs.getInt(14));   //14. ���� ũ��
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

 //6)board ���̺��� ������ �����ϴ� �޼ҵ�
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
 
 //7)board ���̺��� ������ �����ϴ� �޼ҵ�
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