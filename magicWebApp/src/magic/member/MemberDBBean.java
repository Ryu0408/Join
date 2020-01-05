package magic.member;

import java.sql.*;

import javax.sql.*;
import javax.naming.*;

public class MemberDBBean {
	private static MemberDBBean instance = new MemberDBBean();
	
	public static MemberDBBean getInstance(){
		return instance;
	}
	
	public Connection getConnection() throws Exception{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");

														
		return ds.getConnection();
	}
	
	public int insertMember(MemberBean member) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into memberT values(?,?,?,?,?,?)";
		int re =-1;
		try{
			con = getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, member.getMem_uid());
			pstmt.setString(2, member.getMem_pwd());
			pstmt.setString(3, member.getMem_name());
			pstmt.setString(4, member.getMem_email());
			pstmt.setTimestamp(5, member.getMem_regdate());
			pstmt.setString(6, member.getMem_addr());
			pstmt.executeUpdate();
			
			re=1;
			pstmt.close();
			con.close();
			System.out.println("추가 성공");
		}catch(Exception e){
			System.out.println("추가 실패");
			e.printStackTrace();
		}
		return re;
	}
	
	public int userCheck(String id, String pwd) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select mem_pwd from memberT where mem_uid=?";
		String db_mem_pwd;
		int re = -1;
		
		try{
			con = getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				db_mem_pwd=rs.getString("mem_pwd");
				if(db_mem_pwd.equals(pwd)){
					re=1;
				}else{
					re=0;
				}
			}else{
				re=-1;
				rs.close();
				pstmt.close();
				con.close();
			}
		}catch(Exception e){
		}
		return re;
	}
	
	
	public int confirmID(String id) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select mem_uid from memberT where mem_uid=?";
		int re=-1;
		try{
			con = getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			if(rs.next()){
					re=1;
			}else{
					re=-1;
					rs.close();
					pstmt.close();
			}
		}catch(Exception e){}
		return re;
	}
	
	public MemberBean getMember(String id) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from memberT where mem_uid=?";
		MemberBean member = null;
		try{
			con = getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				member = new MemberBean();
				member.setMem_uid(rs.getString("mem_uid"));
				member.setMem_pwd(rs.getString("mem_pwd"));
				member.setMem_name(rs.getString("mem_name"));
				member.setMem_email(rs.getString("mem_email"));
				member.setMem_regdate(rs.getTimestamp("mem_regdate"));
				member.setMem_addr(rs.getString("mem_addr"));
			}
			rs.close();
			pstmt.close();
			con.close();
		}catch(Exception e){
		}
		return member;
	}
	
	public int updateMember(MemberBean member) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update memberT set mem_pwd=?, mem_email=?, mem_addr=?, where mem_uid=?";
		int re = -1;
		try{
			con = getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, member.getMem_pwd());
			pstmt.setString(2, member.getMem_email());
			pstmt.setString(3, member.getMem_addr());
			pstmt.setString(4, member.getMem_uid());
			re=pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
			System.out.println("변경 성공");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("변경 싪패");
		}
		return re;
	}
}