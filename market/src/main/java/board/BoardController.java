package board;

import java.io.IOException;
import java.util.List;

import comment.CommentDAO;
import comment.CommentDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
		String path =request.getContextPath();
		BoardDAO dao = new BoardDAO();
		CommentDAO dao2 = new CommentDAO();
		CommentDTO dto2 = new CommentDTO(); 
		if(url.indexOf("list.do") != -1) {
			int count = dao.count();
			int cur_page = 1;
			if (request.getParameter("cur_page") != null) {
				cur_page = Integer.parseInt(request.getParameter("cur_page"));
			}
			PageUtil page = new PageUtil(count, cur_page);
			int start = page.getPageBegin();
			int end = page.getPageEnd();
			
			List<BoardDTO> list = dao.list(start,end);
			request.setAttribute("list", list);
			request.setAttribute("page", page);
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/board_list.jsp");
			rd.forward(request, response);
			
		} else if(url.indexOf("search.do") != -1) {
			String search_option = request.getParameter("search_option");
			String keyword = request.getParameter("keyword");
			int count = dao.search_count(search_option, keyword);
			int cur_page = 1;
			if (request.getParameter("cur_page") != null) {
				cur_page = Integer.parseInt(request.getParameter("cur_page"));
			}
			PageUtil page = new PageUtil(count, cur_page);
			int start = page.getPageBegin();
			int end = page.getPageEnd();
			
			List<BoardDTO> list = dao.list_search(search_option,keyword,start,end);
			request.setAttribute("list", list);
			request.setAttribute("search_option", search_option);
			request.setAttribute("keyword", keyword);			
			request.setAttribute("page", page);
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/board_list.jsp");
			rd.forward(request, response);
			
		} else if (url.indexOf("insert.do") != -1 ) {
			BoardDTO dto = new BoardDTO();
			HttpSession session = request.getSession();
			String nickname = request.getParameter("nickname");
			String userid = (String)session.getAttribute("userid");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			
			dto.setNickname(nickname);
			dto.setUserid(userid);
			dto.setSubject(subject);
			dto.setContent(content);
			dao.insert(dto);
			
			response.sendRedirect(path + "/board_servlet/list.do");
		} else if (url.indexOf("view.do") != -1) {
			int num = Integer.parseInt(request.getParameter("num"));
			HttpSession session = request.getSession();
			dao.plus_hit(num, session);
			BoardDTO dto = dao.view(num);
			request.setAttribute("dto", dto);
			
			 
			 
			 
//		     List<CommentDTO> list = dao2.getList(num);
//		     request.setAttribute("list", list);
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/board_view.jsp");
			rd.forward(request, response);
			
		} else if (url.indexOf("update.do") != -1) {
			BoardDTO dto = new BoardDTO();
			String nickname = request.getParameter("nickname");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			
			int num = Integer.parseInt(request.getParameter("num"));
			
			dto.setNum(num);
			dto.setNickname(nickname);
			dto.setSubject(subject);
			dto.setContent(content);
			dao.update(dto);
			request.setAttribute("dto", dto);
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/board_view.jsp");
			rd.forward(request, response);
		
		} else if (url.indexOf("delete.do") != -1) {
			int num = Integer.parseInt(request.getParameter("num"));
			dao.delete(num);
			
			response.sendRedirect("/market/board_servlet/list.do");
		} else if (url.indexOf("edit.do") != -1) {
			int num = Integer.parseInt(request.getParameter("num"));
			request.setAttribute("dto",dao.view(num));
			
			String page = "/board/board_edit.jsp";
			
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
