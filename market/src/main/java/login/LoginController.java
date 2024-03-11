package login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;

import email.EmailDTO;
import email.EmailFindPwd;
import email.EmailService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		String path = request.getContextPath();
		MemberDAO dao = new MemberDAO();

		if (url.contains("join.do")) {
			// 폼데이터 변수에 저장
			String userid = request.getParameter("userid");
			String passwd = request.getParameter("passwd");
			String name = request.getParameter("name");
			String nickname = request.getParameter("nickname");
			int birth = Integer.parseInt(request.getParameter("birth"));
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String address = request.getParameter("address1") + request.getParameter("address2");
			// 최종 비밀번호 생성
			String pass = dao.encrypt(passwd);

			MemberDTO dto = new MemberDTO();
			dto.setUserid(userid);
			dto.setPasswd(pass);
			dto.setName(name);
			dto.setNickname(nickname);
			dto.setBirth(birth);
			dto.setPhone(phone);
			dto.setEmail(email);
			dto.setAddress(address);
			dao.join(dto);

			String senderName = "가지나라"; // replace with actual sender name
			String senderMail = "rhwls159@naver.com";

			EmailDTO edto = new EmailDTO();
			edto.setSenderName(senderName);
			edto.setSenderName(senderMail);
			edto.setEmail(email);
			EmailService service = new EmailService();
			try {
				service.mailSender(edto);
				String page = "/login/login.jsp";
				RequestDispatcher rd = request.getRequestDispatcher(page);
				rd.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
				String page = "/login/join.jsp";
				RequestDispatcher rd = request.getRequestDispatcher(page);
				rd.forward(request, response);
			}

		} else if (url.indexOf("login.do") != -1) {
			String userid = request.getParameter("userid");
			String passwd = request.getParameter("passwd");
			MemberDTO dto = new MemberDTO();
			String pass = dao.encrypt(passwd);
			dto.setUserid(userid);
			dto.setPasswd(pass);
			String nickname = dao.login(dto);

			if (nickname == null) {
				String page = "/login/login.jsp?message=error";
				response.sendRedirect(path + page);
			} else {
				int report_code = dao.loginCheck(dto);
				if (report_code == 1) {
					String page = path + "/login/login.jsp?message=report";
					response.sendRedirect(page);
				} else if (report_code == 3) {
					HttpSession session = request.getSession();
					session.setAttribute("userid", userid);
					session.setAttribute("nickname", nickname);
					request.setAttribute("result", nickname + "님 환영합니다.");
					response.sendRedirect("/market/mk_servlet/pop.do");
				} else if (report_code == 0) {
					HttpSession session = request.getSession();
					session.setAttribute("userid", userid);
					session.setAttribute("nickname", nickname);
					request.setAttribute("result", nickname + "님 환영합니다.");
					response.sendRedirect("/market/mk_servlet/pop.do");
				}
			}

		} else if (url.indexOf("logout.do") != -1) {
			HttpSession session = request.getSession();
			session.invalidate();
			String page = path + "/login/login.jsp?message=logout";
			response.sendRedirect(page);

		} else if (url.indexOf("nicknamecheck.do") != -1) {
			String nickname = (String) request.getParameter("nickname");
			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=UTF-8");

			if (nickname == null || nickname.isEmpty()) {
				out.println("닉네임을 입력해주세요.");
			} else {
				String result = dao.nicknamecheck(nickname);
				if (result != null) {
					out.println("중복된 닉네임입니다.");
				} else {
					out.println("사용 가능한 닉네임입니다.");
				}
			}

		} else if (url.indexOf("check.do") != -1) {
			String id = (String) request.getParameter("userid");
			System.out.println("check;"+id);
			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=UTF-8");

			if (id == null || id.isEmpty()) {
				out.println("아이디를 입력해주세요.");
			} else {
				String str = dao.check(id);
				if (str != null) {
					out.println("중복된 아이디입니다.");
				} else if (id.length() < 5) {
					out.println("아이디는 5글자 이상이어야됩니다.");
				} else {
					out.println("사용 가능한 아이디입니다.");
				}
			}

		} else if (url.indexOf("myPage.do") != -1) {
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userid");
			MemberDTO dto = new MemberDTO();
			dto = dao.mypage(userid);
			request.setAttribute("dto", dto);
			RequestDispatcher rd = request.getRequestDispatcher("/product/mypage.jsp");
			rd.forward(request, response);

		} else if (url.indexOf("update.do") != -1) {
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userid");
			String name = request.getParameter("name");
			String nickname = request.getParameter("nickname");
			int birth = Integer.parseInt(request.getParameter("birth"));
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String address = request.getParameter("address1") + request.getParameter("address2");
			MemberDTO dto = new MemberDTO();
			dto.setUserid(userid);
			dto.setName(name);
			dto.setNickname(nickname);
			dto.setBirth(birth);
			dto.setPhone(phone);
			dto.setEmail(email);
			dto.setAddress(address);
			dao.updateMypage(dto);
			response.sendRedirect("/market/login_servlet/myPage.do");
		} else if (url.indexOf("info.do") != -1) {
			List<Object> dto = dao.info();
			request.setAttribute("dto", dto);
			RequestDispatcher rd = request.getRequestDispatcher("/admin/admin_info.jsp");
			rd.forward(request, response);
		} else if (url.indexOf("updateReport.do") != -1) {
			String userid = request.getParameter("userid");
			int report_code = Integer.parseInt(request.getParameter("report_code"));
			MemberDTO dto = new MemberDTO();
			dto.setUserid(userid);
			dto.setReport_code(report_code);
			dao.updateReport(dto);

			String page = path + "/login_servlet/info.do";
			response.sendRedirect(page);
		} else if (url.indexOf("EmailCheck.do") != -1) {
			String email = (String) request.getParameter("email");
			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=UTF-8");

			if (email == null || email.isEmpty()) {
				out.println("이메일을 입력해주세요.");
			} else {
				String str = dao.emailcheck(email);
				if (str != null) {
					out.println("중복된 이메일입니다.");
				} else {
					out.println("사용 가능한 이메일입니다 .");
				}
			}

		} else if (url.indexOf("findid.do") != -1) {
			String name = request.getParameter("name");
			String birth = request.getParameter("birth");
			String phone = request.getParameter("phone");
			String findid = dao.findId(name, birth, phone);

			// JSON 형태로 변환
			Gson gson = new Gson();
			String json = gson.toJson(findid);

			// JSON 데이터 응답으로 전송
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

		} else if (url.indexOf("findpwd.do") != -1) {
			String userid = request.getParameter("userid");
			String passwd = "a123456789";
			String pass = dao.encrypt(passwd);

			String email = dao.email_id(userid);

			dao.findPwd(userid, pass);

			if (userid != null) {
				String senderName = "가지나라"; // replace with actual sender name
				String senderMail = "rhwls159@naver.com";

				EmailDTO edto = new EmailDTO();
				edto.setSenderName(senderName);
				edto.setSenderName(senderMail);
				edto.setEmail(email);
				EmailFindPwd findpwd = new EmailFindPwd();
				try {
					findpwd.mailSender2(edto);
					String page = "/login/login.jsp";
					RequestDispatcher rd = request.getRequestDispatcher(page);
					rd.forward(request, response);

				} catch (Exception e) {
					e.printStackTrace();
					String page = "/login/join.jsp";
					RequestDispatcher rd = request.getRequestDispatcher(page);
					rd.forward(request, response);
				}
			}
		} else if (url.indexOf("detail_passwd.do") != -1) {
			String userid = request.getParameter("userid");
			request.setAttribute("userid", userid);
			RequestDispatcher rd = request.getRequestDispatcher("/login/change_passwd.jsp");
			rd.forward(request, response);

		} else if (url.indexOf("change_passwd.do") != -1) {
			String userid = request.getParameter("userid");
			String passwd1 = request.getParameter("passwd1");
			String passwd2 = request.getParameter("passwd2");
			String passwd3 = dao.encrypt(passwd1);
			String passwd4 = dao.encrypt(passwd2);
			String mypasswd = dao.mypasswd(userid);

			if (mypasswd.equals(passwd3)) {
				dao.findPwd(userid, passwd4);
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('변경되었습니다.'); window.location.href='" + path
						+ "/report/reportclose.jsp';</script>");

			} else {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('기존 비밀번호가 틀렸습니다.'); window.location.href='" + path
						+ "/login_servlet/detail_passwd.do';</script>");
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}