package login;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import email.EmailDTO;
import email.EmailService;

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

			MemberDTO dto = new MemberDTO();
			dto.setUserid(userid);
			dto.setPasswd(passwd);
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
			dto.setUserid(userid);
			dto.setPasswd(passwd);
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
					String page = "/main/main.jsp";
					RequestDispatcher rd = request.getRequestDispatcher(page);
					rd.forward(request, response);
				} else if (report_code == 0) {
					HttpSession session = request.getSession();
					session.setAttribute("userid", userid);
					session.setAttribute("nickname", nickname);
					request.setAttribute("result", nickname + "님 환영합니다.");
					String page = "/main/main.jsp";
					RequestDispatcher rd = request.getRequestDispatcher(page);
					rd.forward(request, response);
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
				System.out.println(nickname);
				if (result != null) {
					out.println("중복된 닉네임입니다.");
				} else {
					out.println("사용 가능한 닉네임입니다.");
				}
			}

		} else if (url.indexOf("check.do") != -1) {
			String id = (String) request.getParameter("userid");
			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=UTF-8");

			if (id == null || id.isEmpty()) {
				out.println("아이디를 입력해주세요.");
			} else {
				String str = dao.check(id);
				if (str != null) {
					out.println("중복된 아이디입니다.");
				} else {
					out.println("사용 가능한 아이디입니다 .");
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
			System.out.println(dto);
			dao.updateMypage(dto);
			response.sendRedirect("/market/login_servlet/myPage.do");

		} else if (url.indexOf("findid.do") != -1) {
			String name = request.getParameter("name");
			String birth = request.getParameter("birth");
			String phone = request.getParameter("phone");
			String findid = dao.findId(name, birth, phone);
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			if (findid != null) {
				out.println("당신의 아이디는" + findid + "입니다");
			} else {
				out.println("<h3>일치하는 사용자 정보가 없습니다.</h3>");
			}
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
		}else if (url.indexOf("EmailCheck.do") != -1) {
			String email = (String) request.getParameter("email");
			System.out.println(email);
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

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
