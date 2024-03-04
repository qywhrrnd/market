package comment;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import board.BoardDTO;

public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
		CommentDAO dao = new CommentDAO();
		
		if (url.indexOf("write.do") != -1) {
			String comment_content = request.getParameter("comment_content");
			HttpSession session = request.getSession();
			String userid = (String)session.getAttribute("userid");
            int num = Integer.parseInt(request.getParameter("num"));
            // CommentDTO 객체 생성
            CommentDTO dto = new CommentDTO();
            dto.setComment_content(comment_content);
            dto.setUserid(userid);
            dto.setNum(num);
            
            
            // 댓글 작성
            dao.write(dto);
            request.setAttribute("dto", dto);

         // JSON 형태로 변환
	        Gson gson = new Gson();
	        String json = gson.toJson(dto);
	        
	        // JSON 데이터 응답으로 전송
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(json);
            
		} else if (url.indexOf("delete.do") != -1) {
			
			int comment_num = Integer.parseInt(request.getParameter("comment_num"));
			String userid = request.getParameter("userid");
			int num = Integer.parseInt(request.getParameter("num"));
			
			dao.delete(userid, comment_num, num);

		    // 삭제된 댓글 정보를 JSON으로 변환
		    CommentDTO dto = new CommentDTO();
		    dto.setComment_num(comment_num);
		    dto.setUserid(userid);
		    dto.setNum(num);
			// JSON 형태로 변환
	        Gson gson = new Gson();
	        String json = gson.toJson(dto);
	        
	        // JSON 데이터 응답으로 전송
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(json);
			
		} else if (url.indexOf("list.do") != -1) {
			int num = Integer.parseInt(request.getParameter("num"));
			
			List<CommentDTO> list = dao.getList(num);
			request.setAttribute("list", list);
			
			// JSON 형태로 변환
	        Gson gson = new Gson();
	        String json = gson.toJson(list);
	        
	        // JSON 데이터 응답으로 전송
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(json);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
