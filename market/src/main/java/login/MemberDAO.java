package login;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.ibatis.session.SqlSession;

import market.ProductDTO;
import sqlmap.MybatisManager;

public class MemberDAO {

	private static final String ALGORITHM = "AES";
	private static final String KEY = "123ssdadssaassdd"; // 암호화에 사용할 키 (16, 24, 32 bytes)

	public void join(MemberDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		session.insert("login.join", dto);
		session.commit();
		session.close();
	}

	public String login(MemberDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		String nickname = session.selectOne("login.login", dto);
		session.close();
		return nickname;
	}

	public int loginCheck(MemberDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		int report_code = session.selectOne("login.loginCheck", dto);
		session.close();
		return report_code;
	}

	public String check(String userid) {
		SqlSession session = null;
		String str = "";
		try {
			session = MybatisManager.getInstance().openSession();

			str = session.selectOne("login.check", userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public String address(String userid) {
		SqlSession session = MybatisManager.getInstance().openSession();
		String address = session.selectOne("login.address", userid);
		session.close();
		return address;
	}

	public MemberDTO mypage(String userid) {
		SqlSession session = MybatisManager.getInstance().openSession();
		MemberDTO dto = session.selectOne("login.mypage", userid);
		session.close();
		return dto;
	}

	public void updateMypage(MemberDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		session.update("login.update_mypage", dto);
		session.commit();
		session.close();
	}

	public String nicknamecheck(String nickname) {
		SqlSession session = null;
		String result = "";
		try {
			session = MybatisManager.getInstance().openSession();

			result = session.selectOne("login.nicknamecheck", nickname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Object> info() {
		SqlSession session = MybatisManager.getInstance().openSession();
		List<Object> dto = session.selectList("login.info");
		session.close();
		return dto;
	}

	public void updateReport(MemberDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		session.update("login.changeReport", dto);
		session.commit();
		session.close();
	}

	public String emailcheck(String email) {
		SqlSession session = null;
		String str = "";
		try {
			session = MybatisManager.getInstance().openSession();

			str = session.selectOne("login.emailcheck", email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public String email(String userid) {
		SqlSession session = MybatisManager.getInstance().openSession();
		String email = session.selectOne("login.email", userid);
		session.close();
		return email;
	}

	public String encrypt(String pwd) {
		String salt = "gagimama";
		String result = "";
		try {
			// 1. SHA256 알고리즘 객체 생성
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			// 2. 비밀번호와 salt 합친 문자열에 SHA 256 적용
			md.update((pwd + salt).getBytes());
			byte[] pwdsalt = md.digest();
			// 3. byte To String (10진수의 문자열로 변경)
			StringBuffer sb = new StringBuffer();
			for (byte b : pwdsalt) {
				sb.append(String.format("%02x", b));
			}
			result = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String findId(String name, String birth, String phone) {
		SqlSession session = MybatisManager.getInstance().openSession();
		Map<String, String> map = new HashMap<>();
		map.put("name", name);
		map.put("birth", birth);
		map.put("phone", phone);
		String foundId = session.selectOne("login.findId", map);
		session.close();

		return foundId;
	}

	public void findPwd(String userid, String passwd) {
		SqlSession session = MybatisManager.getInstance().openSession();
		Map<String, String> map = new HashMap<>();
		map.put("userid", userid);
		map.put("passwd", passwd);
		session.update("login.findPwd", map);
		session.commit();
		session.close();
	}

	public String email_id(String userid) {
		SqlSession session = MybatisManager.getInstance().openSession();
		String str = session.selectOne("login.email_id", userid);

		session.close();
		return str;
	}
	public String mypasswd(String userid) {
		SqlSession session = MybatisManager.getInstance().openSession();
		String mypasswd = session.selectOne("login.mypasswd", userid);
		session.close();
		return mypasswd;
	}
}
