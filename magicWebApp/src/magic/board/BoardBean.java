package magic.board;

import java.sql.Timestamp;

public class BoardBean {
	  private  int b_id;        // 1. �� ��ȣ
	  private  String b_name;   // 2. �ۼ���
	  private  String b_email;  // 3. �۾��� �̸����ּ�
	  private  String b_title;  // 4. �� ����
	  private  String b_content;// 5. �� ����
	  private  String b_pwd;    // 6. ��й�ȣ
	  private  Timestamp b_date;// 7. �ۼ���
	  private  String b_ip;     // 8. �۾��� IP
	  private  int b_hit;       // 9. ��ȸ ȸ��

	  // �亯 �� �Խ����� ���� ����
	  private int b_ref=0;              //10. �� �׷�
	  private int b_step=0;             //11. �� ��ġ
	  private int b_level=0;            //12. �� ����
	 
	  // ������ ������ ���� ���� 
	  public static  int pagesize = 10;// �� ������ �� 10�� ��¹�
	  public static  int pagecount= 1 ;// ������ ���� ���� ����
	  public static  int pageNUM= 1;   // ������ ��ȣ
	  
	  //�� ����� �������� �����ϴ� �޼ҵ�  
	  public static String  pageNumber(int limit){ 
		String str = ""; 
		int temp = (pageNUM-1) % limit ;         //���� ������ ���ϱ�
		int startPage = pageNUM - temp;
		// [����] ��ũ �߰��ϱ�
		if ((startPage-limit)>0){
		  str="<a href='list.jsp?pageNUM="+(startPage-1)+"'>[����]</a>&nbsp;&nbsp;";
		}
		//������ ��ȣ �����ϱ�
		for(int i=startPage ; i<(startPage+limit);i++){
		  if( i == pageNUM){
	        str +="["+i+"]&nbsp;&nbsp;";
		  }else{
		    str += "<a href='list.jsp?pageNUM="+i+"'>["+i+"]</a>&nbsp;&nbsp;";
		  }
		  if(i >= pagecount) break;
		}//for�� ��...
		//[����] ��ũ �߰�
		if ((startPage+limit)<=pagecount){ 
		  str += "<a href='list.jsp?pageNUM=" + (startPage+limit)+"'>[����]</a>";
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