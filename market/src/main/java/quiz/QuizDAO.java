package quiz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import sqlmap.MybatisManager;

public class QuizDAO {

	public List<QuizDTO> quiz_view(int num) {
		SqlSession session = MybatisManager.getInstance().openSession();
		List<QuizDTO> items = session.selectList("quiz.view",num);
		session.close();
		return items;
		
	}
	public List<QuizDTO> quiz_view_admin() {
		SqlSession session = MybatisManager.getInstance().openSession();
		List<QuizDTO> items = session.selectList("quiz.view_admin");
		session.close();
		return items;
		
	}

	public void answer_insert(AnswerDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		session.insert("quiz.insert_answer", dto);
		session.commit();
		session.close();
	}
	public int check_answer(int quiz_idx) {
		SqlSession session = MybatisManager.getInstance().openSession();
		int status = session.selectOne("quiz.check",quiz_idx);
		session.close();
		return status;
		
	}
	
	public String quiz_email(String userid) {
	      SqlSession session = MybatisManager.getInstance().openSession();
	      String str = session.selectOne("quiz.quiz_email",userid);
	      
	      session.close();
	      return str;
	   }
	public int checkevent(String userid) {
	      SqlSession session = MybatisManager.getInstance().openSession();
	      int checkevent = session.selectOne("quiz.checkevent", userid);
	      session.close();
	      return checkevent;
	   }

	public void insertQuiz(QuizDTO dto) {
		 SqlSession session = MybatisManager.getInstance().openSession();
		 session.insert("quiz.quiz_insert",dto);
		 session.commit();
		 session.close();
	}
	
}
