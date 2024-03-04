package login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import market.ProductDTO;
import sqlmap.MybatisManager;

public class MemberDAO {

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

	public String findId(String name, String birth, String phone) {
		SqlSession session = null;
		String foundId = null;
		try {
			session = MybatisManager.getInstance().openSession();
			Map<String, String> map = new HashMap<>();
			map.put("name", name);
			map.put("birth", birth);
			map.put("phone", phone);
			foundId = session.selectOne("login.findId", map);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return foundId;
	}
	
	public List<Object> info() {
		SqlSession session = MybatisManager.getInstance().openSession();
		List<Object> dto = session.selectList("login.info");
		session.close();
		return dto;
	}

	public void updateReport(MemberDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		session.update("login.changeReport",dto);
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
}
