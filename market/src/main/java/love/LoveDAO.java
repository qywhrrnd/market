package love;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import sqlmap.MybatisManager;

public class LoveDAO {

	public void love_clear(LoveDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		session.delete("love.love_clear", dto);
		session.commit();
		session.close();
	}

	public void love_apply(LoveDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		session.insert("love.love_apply", dto);
		session.commit();
		session.close();
	}

	
	public int love_count(int write_code) {
		SqlSession session = MybatisManager.getInstance().openSession();
		int result = 0;
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("write_code", write_code);
			result = session.selectOne("love.love_count", map);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}
	
	
	public List<LoveDTO> love_list(String userid) {
		SqlSession session = MybatisManager.getInstance().openSession();
		List<LoveDTO> lovelist = null;
		try {
			lovelist = session.selectList("love.love_list", userid);
			for (LoveDTO dto : lovelist) {
	            System.out.println("write_code from database: " + dto.getWrite_code());
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
	        if (session != null) {
	            session.close();
	        }
	    }

	    return lovelist;
	}

	public void love_delete(int write_code) {
		SqlSession session = MybatisManager.getInstance().openSession();
		System.out.println(write_code);
		session.delete("love.love_delete", write_code);
		session.commit();
		session.close();
		
	}
}
