package love;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import market.ProductDAO;
import market.ProductDTO;

public class LoveController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		String path = request.getContextPath();
		LoveDAO dao = new LoveDAO();
		LoveDTO dto = new LoveDTO();
		ProductDAO pdao = new ProductDAO();
		ProductDTO pdto = new ProductDTO();

		if (url.indexOf("love_apply.do") != -1) {
			int write_code = Integer.parseInt(request.getParameter("write_code"));
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userid");
			dto.setUserid(userid);
			dto.setWrite_code(write_code);
			dao.love_apply(dto);

		} else if (url.indexOf("love_clear.do") != -1) {
			int write_code = Integer.parseInt(request.getParameter("write_code"));
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userid");
			dto.setUserid(userid);
			dto.setWrite_code(write_code);
			dao.love_clear(dto);

		} else if (url.indexOf("love_List.do") != -1) {
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userid");

			dto.setUserid(userid);
			List<LoveDTO> list = dao.love_list(userid); // List 타입을 LoveDTO로 변경
			request.setAttribute("filesize", list.size());
			request.setAttribute("list", list);
			RequestDispatcher rd = request.getRequestDispatcher("/product/lovelist.jsp");
			rd.forward(request, response);

		} else if (url.indexOf("delete.do") != -1) {
			int write_code = Integer.parseInt(request.getParameter("write_code"));
			dao.love_delete(write_code);
			response.sendRedirect(request.getContextPath()+ "/love_servlet/love_List.do");

		} else if (url.indexOf("delete_all.do") != -1) {
			String[] num = request.getParameterValues("num");
			
			if (num != null) {
				for (int i = 0; i < num.length; i++) {
					dao.love_delete(Integer.parseInt(num[i]));
				}
			}
		response.sendRedirect(request.getContextPath()+ "/love_servlet/love_List.do");
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
