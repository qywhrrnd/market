package quiz;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import email.EmailDTO;
import email.EmailEvent;
import email.EmailService;

public class QuizController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getContextPath();
		String url = request.getRequestURI();
		QuizDAO dao = new QuizDAO();

		if (url.indexOf("view.do") != -1) {
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userid");
			int a = dao.checkevent(userid);
			if (a >= 1) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('이미 이벤트에 참여하셨습니다.'); window.location.href='" + path
						+ "/mk_servlet/pop.do';</script>");
			} else {
				List<QuizDTO> items = dao.quiz_view(4);
				request.setAttribute("items", items);
				RequestDispatcher rd = request.getRequestDispatcher("/event/quiz.jsp");
				rd.forward(request, response);
			}
			
			
		} else if (url.indexOf("quiz_Insert.do") != -1) {

			String question = request.getParameter("question");
			String ans1 = request.getParameter("ans1");
			String ans2 = request.getParameter("ans2");
			String ans3 = request.getParameter("ans3");
			String ans4 = request.getParameter("ans4");
			int status = Integer.parseInt(request.getParameter("status"));
			System.out.println(question);
			System.out.println(ans1);
			System.out.println(ans2);
			System.out.println(ans3);
			System.out.println(ans4);
			System.out.println(status);

			QuizDTO dto = new QuizDTO();
			dto.setQuestion(question);
			dto.setAns1(ans1);
			dto.setAns2(ans2);
			dto.setAns3(ans3);
			dto.setAns4(ans4);
			dto.setStatus(status);
			dao.insertQuiz(dto);
			String page = "../report/reportclose.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);


		} else if (url.indexOf("insert.do") != -1) {
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userid");
			int num1 = Integer.parseInt(request.getParameter("num1"));
			int num2 = Integer.parseInt(request.getParameter("num2"));
			int num3 = Integer.parseInt(request.getParameter("num3"));
			int num4 = Integer.parseInt(request.getParameter("num4"));
			AnswerDTO dto = new AnswerDTO();
			dto.setUserid(userid);
			dto.setNum1(num1);
			dto.setNum2(num2);
			dto.setNum3(num3);
			dto.setNum4(num4);
			dao.answer_insert(dto);

			int quiz_idx1 = Integer.parseInt(request.getParameter("1"));
			int quiz_idx2 = Integer.parseInt(request.getParameter("2"));
			int quiz_idx3 = Integer.parseInt(request.getParameter("3"));
			int quiz_idx4 = Integer.parseInt(request.getParameter("4"));

			int status1 = dao.check_answer(quiz_idx1);
			int status2 = dao.check_answer(quiz_idx2);
			int status3 = dao.check_answer(quiz_idx3);
			int status4 = dao.check_answer(quiz_idx4);

			if (num1 == status1 && num2 == status2 && num3 == status3 && num4 == status4) {
				request.setAttribute("result", "정답입니다.");
				String page = "/event/result.jsp";
				RequestDispatcher rd = request.getRequestDispatcher(page);
				rd.forward(request, response);

			} else {
				request.setAttribute("result", "돌아가세요...");
				String page = "/event/result.jsp";
				RequestDispatcher rd = request.getRequestDispatcher(page);
				rd.forward(request, response);
			}
		} else if (url.indexOf("send.do") != -1) {
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userid");
			String email = dao.quiz_email(userid);

			String senderName = "가지나라"; // replace with actual sender name
			String senderMail = "rhwls159@naver.com";

			EmailDTO edto = new EmailDTO();
			edto.setSenderName(senderName);
			edto.setSenderName(senderMail);
			edto.setEmail(email);
			EmailEvent event = new EmailEvent();
			try {
				event.mailSender3(edto);
				String page = path + "/mk_servlet/pop.do";
				response.sendRedirect(page);
			} catch (Exception e) {
				e.printStackTrace();
				String page = path + "/mk_servlet/pop.do";
				response.sendRedirect(page);
			}
		} /*
			 * else if (url.indexOf("eventlist.do") != -1) { List<QuizDTO> items =
			 * dao.quiz_view(); request.setAttribute("items", items); RequestDispatcher rd =
			 * request.getRequestDispatcher("/admin/quiz.jsp"); rd.forward(request,
			 * response);
			 * 
			 * }
			 */else if (url.indexOf("eventdetail.do") != -1) {
			List<QuizDTO> items = dao.quiz_view_admin();
			request.setAttribute("items", items);
			RequestDispatcher rd = request.getRequestDispatcher("/admin/event_detail.jsp");
			rd.forward(request, response);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
