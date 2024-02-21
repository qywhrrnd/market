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
import java.util.Map;

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
			int phone = Integer.parseInt(request.getParameter("phone"));
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

			String page = "/login/login.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);

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
				HttpSession session = request.getSession();
				session.setAttribute("userid", userid);
				session.setAttribute("nickname", nickname);
				request.setAttribute("result", nickname + "님 환영합니다.");
				String page = "/login/result.jsp";
				RequestDispatcher rd = request.getRequestDispatcher(page);
				rd.forward(request, response);
			}
		} else if (url.indexOf("logout.do") != -1) {
			HttpSession session = request.getSession();
			session.invalidate();
			String page = path + "/login/login.jsp?message=logout";
			response.sendRedirect(page);
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
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
