package magic.member;

import java.sql.Timestamp;

public class MemberBean {
/*	CREATE TABLE memberT(
		    mem_uid varchar2(15) primary key,
		    mem_pwd varchar2(15) not null,
		    mem_name varchar2(10) not null,
		    mem_email varchar2(50),
		    mem_redate date,
		    mem_addr varchar2(100));
		    
		SELECT * FROM memberT;*/
	private String mem_uid;
	private String mem_pwd;
	private String mem_name;
	private String mem_email;
	private Timestamp mem_regdate;
	private String mem_addr;
	
	public String getMem_uid() {
		return mem_uid;
	}
	public void setMem_uid(String mem_uid) {
		this.mem_uid = mem_uid;
	}
	public String getMem_pwd() {
		return mem_pwd;
	}
	public void setMem_pwd(String mem_pwd) {
		this.mem_pwd = mem_pwd;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public Timestamp getMem_regdate() {
		return mem_regdate;
	}
	public void setMem_regdate(Timestamp mem_regdate) {
		this.mem_regdate = mem_regdate;
	}
	public String getMem_addr() {
		return mem_addr;
	}
	public void setMem_addr(String mem_addr) {
		this.mem_addr = mem_addr;
	}
	
	
	
	
}
