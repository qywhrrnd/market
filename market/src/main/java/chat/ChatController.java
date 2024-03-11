package chat;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;

public class ChatController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		String path = request.getContextPath();
		ChattingDAO dao = new ChattingDAO();
		ChattingDTO dto = new ChattingDTO();
		if (url.indexOf("boxlist.do") != -1) {
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userid");
			List<String> items = dao.boxlist(userid);
			request.setAttribute("list", items);

			RequestDispatcher rd = request.getRequestDispatcher("/chat/box.jsp");
			rd.forward(request, response);
		} else if (url.indexOf("chat.do") != -1) {
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userid");
			String toid = request.getParameter("toid");

			List<ChattingDTO> items = dao.chatlist(userid, toid);
			request.setAttribute("list", items);
			request.setAttribute("toid", toid);
			RequestDispatcher rd = request.getRequestDispatcher("/chat/chat.jsp");
			rd.forward(request, response);
		} else if (url.indexOf("chatwrite.do") != -1) {
			HttpSession session = request.getSession();
			String fromid = (String) session.getAttribute("userid");
			String toid = request.getParameter("toid");
			String chatcomment = request.getParameter("chatcomment");
			String time = request.getParameter("time");
			dto.setFromid(fromid);
			dto.setChatcomment(chatcomment);
			dto.setToid(toid);
			dto.setTime(time);
			dao.chatwrite(dto);

		} else if (url.indexOf("update.do") != -1) {
			String toid = request.getParameter("fromid");
			HttpSession session = request.getSession();
			String fromid = (String) session.getAttribute("userid");
			String time = request.getParameter("time");
			List<ChattingDTO> items = dao.chatupdate(toid, fromid, time);
			request.setAttribute("items", items);
			Gson gson = new Gson();
	        String json = gson.toJson(items);
	        // JSON 형식의 응답을 클라이언트에게 반환
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(json);
	        // 
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
