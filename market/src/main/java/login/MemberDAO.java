package login;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

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

}
