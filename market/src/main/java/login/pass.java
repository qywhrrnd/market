package login;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class pass {

	public static void main(String[] args) {
		pass en = new pass();
		
		String pwd = "tistory";
		System.out.println("pwd : "+ pwd);
		
		//Salt 생성
                // 현재 랜덤으로 Salt값을 생성하였지만, 실제 구현시 고정시키거나 Salt값을 저장해 두어야합니다.
		String salt = "gagimama";
		System.out.println("salt : "+salt);
		
		//최종 비밀번호 생성
		String res = en.getEncrypt(pwd, salt);
	}

	
	public String getEncrypt(String pwd, String salt) {
		
		String result = "";
		try {
			//1. SHA256 알고리즘 객체 생성
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			//2. 비밀번호와 salt 합친 문자열에 SHA 256 적용
			System.out.println("비밀번호 + salt 적용 전 : " + pwd+salt);
			md.update((pwd+salt).getBytes());
			byte[] pwdsalt = md.digest();
			
			//3. byte To String (10진수의 문자열로 변경)
			StringBuffer sb = new StringBuffer();
			for (byte b : pwdsalt) {
				sb.append(String.format("%02x", b));
			}
			
			result=sb.toString();
			System.out.println("비밀번호 + salt 적용 후 : " + result);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return result;
	}
}