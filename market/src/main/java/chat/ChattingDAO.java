package chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import sqlmap.MybatisManager;

public class ChattingDAO {

	public List<String> boxlist(String userid) {
		SqlSession session = MybatisManager.getInstance().openSession();
		List<String> items = session.selectList("chat.boxlist", userid);
		session.close();
		return items;

	}

	public List<ChattingDTO> chatlist(String userid, String toid) {
		SqlSession session = MybatisManager.getInstance().openSession();
		Map<String, Object> map = new HashMap<>();
		map.put("userid", userid);
		map.put("toid", toid);
		List<ChattingDTO> items = session.selectList("chat.chatlist", map);
		return items;
	}
	
	

	
	public void chatwrite(ChattingDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		session.insert("chat.chatwrite", dto);
		session.commit();
		session.close();
	}

	public List<ChattingDTO> chatupdate(String toid,String userid, String time) {
		SqlSession session = MybatisManager.getInstance().openSession();
		Map<String, Object> map = new HashMap<>();
		map.put("toid", toid);
		map.put("userid", userid);
		map.put("time", time);
		List<ChattingDTO> items = session.selectList("chat.chatupdate", map);
		return items;
		
	}

}
