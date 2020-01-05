package myUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
public class HanConv {
	public static String toKor(String str){
		if((str == null) || (str.equals("")) || (str.equals("null"))){
			return str;
		}
		try{
			return new String(str.getBytes("8859_1"), "euc-kr");
		}catch(Exception e){
			e.printStackTrace();
			return str;
		}
	}
	public static String toEng(String str){
		if((str == null) || (str.equals("")) || (str.equals("null"))){
			return str;
		}
		try{
			String result = null;
			result = URLEncoder.encode(str, "MS949");
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return str;
		}
	}
}
