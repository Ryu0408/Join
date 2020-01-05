package magic.board;

import java.sql.Timestamp;

public class BoardBean {
	  private  int b_id;        // 1. 글 번호
	  private  String b_name;   // 2. 작성자
	  private  String b_email;  // 3. 글쓴이 이메일주소
	  private  String b_title;  // 4. 글 제목
	  private  String b_content;// 5. 글 내용
	  private  String b_pwd;    // 6. 비밀번호
	  private  Timestamp b_date;// 7. 작성일
	  private  String b_ip;     // 8. 글쓴이 IP
	  private  int b_hit;       // 9. 조회 회수

	  // 답변 형 게시판을 위한 변수
	  private int b_ref=0;              //10. 글 그룹
	  private int b_step=0;             //11. 글 위치
	  private int b_level=0;            //12. 글 레벨
	 
	  // 페이지 구현을 위한 변수 
	  public static  int pagesize = 10;// 한 페이지 당 10개 출력물
	  public static  int pagecount= 1 ;// 페이지 개수 지정 변수
	  public static  int pageNUM= 1;   // 페이지 번호
	  
	  //글 목록을 페이지로 구현하는 메소드  
	  public static String  pageNumber(int limit){ 
		String str = ""; 
		int temp = (pageNUM-1) % limit ;         //시작 페이지 구하기
		int startPage = pageNUM - temp;
		// [이전] 링크 추가하기
		if ((startPage-limit)>0){
		  str="<a href='list.jsp?pageNUM="+(startPage-1)+"'>[이전]</a>&nbsp;&nbsp;";
		}
		//페이지 번호 나열하기
		for(int i=startPage ; i<(startPage+limit);i++){
		  if( i == pageNUM){
	        str +="["+i+"]&nbsp;&nbsp;";
		  }else{
		    str += "<a href='list.jsp?pageNUM="+i+"'>["+i+"]</a>&nbsp;&nbsp;";
		  }
		  if(i >= pagecount) break;
		}//for문 끝...
		//[다음] 링크 추가
		if ((startPage+limit)<=pagecount){ 
		  str += "<a href='list.jsp?pageNUM=" + (startPage+limit)+"'>[다음]</a>";
		}  
	    return str;
	  }
	  
	public int getB_id() {
		return b_id;
	}
	public int getB_ref() {
		return b_ref;
	}
	public void setB_ref(int b_ref) {
		this.b_ref = b_ref;
	}
	public int getB_step() {
		return b_step;
	}
	public void setB_step(int b_step) {
		this.b_step = b_step;
	}
	public int getB_level() {
		return b_level;
	}
	public void setB_level(int b_level) {
		this.b_level = b_level;
	}
	
	public void setB_id(int b_id) {
		this.b_id = b_id;
	}
	public String getB_name() {
		return b_name;
	}
	public void setB_name(String b_name) {
		this.b_name = b_name;
	}
	public String getB_email() {
		return b_email;
	}
	public void setB_email(String b_email) {
		this.b_email = b_email;
	}
	public String getB_title() {
		return b_title;
	}
	public void setB_title(String b_title) {
		this.b_title = b_title;
	}
	public String getB_content() {
		return b_content;
	}
	public void setB_content(String b_content) {
		this.b_content = b_content;
	}
	public String getB_pwd() {
		return b_pwd;
	}
	public void setB_pwd(String b_pwd) {
		this.b_pwd = b_pwd;
	}
	public Timestamp getB_date() {
		return b_date;
	}
	public void setB_date(Timestamp b_date) {
		this.b_date = b_date;
	}
	public String getB_ip() {
		return b_ip;
	}
	public void setB_ip(String b_ip) {
		this.b_ip = b_ip;
	}
	public int getB_hit() {
		return b_hit;
	}
	public void setB_hit(int b_hit) {
		this.b_hit = b_hit;
	}
	  
}