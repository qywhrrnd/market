package comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import sqlmap.MybatisManager;

public class CommentDAO {
	
	// 다음 댓글 번호 가져오기
//	public int getNext() {
//		int result = 0;
//		SqlSession session = MybatisManager.getInstance().openSession();
//		result = session.selectOne("comment.get_next");
//		session.commit();
//		session.close();
//		
//		return result;
//	}

	// 댓글 쓰기
	public void write(CommentDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		String contents = dto.getComment_content();
		contents = contents.replace("<", "&lt;");
		contents = contents.replace(">", "&gt;");
		contents = contents.replace("\n", "<br>");
		contents = contents.replace("  ", "&nbsp;&nbsp;");
		dto.setComment_content(contents);
		session.insert("comment.write", dto);
		session.commit();
		session.close();
	}

	// 댓글 리스트
	public List<CommentDTO> getList(int num) {
		List<CommentDTO> list = null;
		SqlSession session = MybatisManager.getInstance().openSession();
		list = session.selectList("comment.get_list", num);
		session.commit();
		session.close();
		return list;
	}


	// 댓글 삭제
	public void delete(String userid, int comment_num, int num) {
		SqlSession session = MybatisManager.getInstance().openSession();
		Map<String,Object> map = new HashMap<>();		
		map.put("userid", userid);
		map.put("comment_num", comment_num);
		map.put("num", num);
		session.delete("comment.delete", map);
		session.commit();
		session.close();
	}

	public void update(String comment_content, String userid, int comment_num) {
		SqlSession session = MybatisManager.getInstance().openSession();
		Map<String,Object> map = new HashMap<>();	
		map.put("comment_content", comment_content);
		map.put("userid", userid);
		map.put("comment_num", comment_num);
		session.update("comment.update", map);
		session.commit();
		session.close();
	}

	
}
