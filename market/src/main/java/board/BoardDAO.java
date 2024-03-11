package board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import jakarta.servlet.http.HttpSession;
import sqlmap.MybatisManager;

public class BoardDAO {

	// 게시판의 리스트
	public List<BoardDTO> list(int pageStart, int pageEnd) {
		List<BoardDTO> list = null;
		SqlSession session = MybatisManager.getInstance().openSession();

		Map<String, Object> map = new HashMap<>();
		map.put("start", pageStart);
		map.put("end", pageEnd);
		list = session.selectList("board.list", map);
		return list;
	}

	// 게시판 게시글 수 계산 => 페이징을 위해
	public int count() {
		int result = 0;
		SqlSession session = MybatisManager.getInstance().openSession();

		try {
			result = session.selectOne("board.count");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	// 검색옵션 선택시 게시판의 리스트 띄우기
	public List<BoardDTO> list_search(String search_option, String key_word, int start, int end) {
		List<BoardDTO> list = null;
		SqlSession session = MybatisManager.getInstance().openSession();

		try {
			Map<String, Object> map = new HashMap<>();
			map.put("search_option", search_option);
			map.put("key_word", key_word);
			map.put("start", start);
			map.put("end", end);
			list = session.selectList("board.search_list", map);

			for (BoardDTO dto : list) {
				String nickname = dto.getNickname();
				String subject = dto.getSubject();

				switch (search_option) {
				case "all":
					nickname.replace(key_word, "<span style='color:red'>" + key_word + "</span>");
					subject.replace(key_word, "<span style='color:red'>" + key_word + "</span>");
					break;

				case "nickname":
					nickname.replace(key_word, "<span style='color:red'>" + key_word + "</span>");
					break;

				case "subject":
					subject.replace(key_word, "<span style='color:red'>" + key_word + "</span>");
					break;
				}
				dto.setNickname(nickname);
				dto.setSubject(subject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	// 검색옵션 선택시 게시판 게시글 수 계산 => 페이징을 위해
	public int search_count(String search_option, String keyword) {
		int result = 0;
		SqlSession session = MybatisManager.getInstance().openSession();

		try {
			Map<String, Object> map = new HashMap<>();
			map.put("search_option", search_option);
			map.put("keyword", keyword);
			result = session.selectOne("board.search_count", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	// 게시글 작성 => board_write.jsp
	public void insert(BoardDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		String contents = dto.getContent();
		contents = contents.replace("<", "&lt;");
		contents = contents.replace(">", "&gt;");
		contents = contents.replace("\n", "<br>");
		contents = contents.replace("  ", "&nbsp;&nbsp;");
		dto.setContent(contents);
		session.insert("board.insert", dto);
		session.commit();
		session.close();

	}

	// 게시글 수정 => board_edit.jsp
	public void update(BoardDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		String contents = dto.getContent();
		contents = contents.replace("<", "&lt;");
		contents = contents.replace(">", "&gt;");
		contents = contents.replace("\n", "<br>");
		contents = contents.replace("  ", "&nbsp;&nbsp;");
		dto.setContent(contents);
		session.update("board.update", dto);
		session.commit();
		session.close();

		
	}

	// 게시글 삭제 => board_edit.jsp
	public void delete(int num) {
		SqlSession session = null;
		try {
			session = MybatisManager.getInstance().openSession();
			session.delete("board.delete", num);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	// 조회수
	public void plus_hit(int num, HttpSession count_session) {
		SqlSession session = null;
		try {
			long read_time = 0;
			if (count_session.getAttribute("read_time_" + num) != null) {
				read_time = (long) count_session.getAttribute("read_time_" + num);
			}
			long current_time = System.currentTimeMillis();
			session = MybatisManager.getInstance().openSession();

			if (current_time - read_time > 5 * 1000) {
				session.update("board.plus_hit", num);
				session.commit();
				count_session.setAttribute("read_time_" + num, current_time);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	public BoardDTO view(int num) {
		BoardDTO dto = null;
		SqlSession session = null;
		try {
			session = MybatisManager.getInstance().openSession();
			dto = session.selectOne("board.view", num);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return dto;
	}

}
