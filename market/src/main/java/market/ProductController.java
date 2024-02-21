package market;

import java.io.IOException;
import java.util.List;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import love.LoveDAO;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		String path = request.getContextPath();
		ProductDAO dao = new ProductDAO();
		LoveDAO ldao = new LoveDAO();
		if (url.indexOf("list.do") != -1) {
			int count = dao.count();
			int cur_page = 1;
			if (request.getParameter("cur_page") != null) {
				cur_page = Integer.parseInt(request.getParameter("cur_page"));
			}
			PageUtil page = new PageUtil(count, cur_page);
			int start = page.getPageBegin();
			int end = page.getPageEnd();
			
			List<ProductDTO> list = dao.listProduct(start,end);
			request.setAttribute("list", list);
			request.setAttribute("page", page);
			request.setAttribute("dao", ldao);
			RequestDispatcher rd = request.getRequestDispatcher("/product/list.jsp");
			rd.forward(request, response);

		} else if (url.indexOf("myList.do") != -1) {
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userid");
			List<ProductDTO> items = dao.mylist(userid);
			ProductDTO dto = new ProductDTO();
			dto.setUserid(userid);
			request.setAttribute("list", items);
			System.out.println(items);
			RequestDispatcher rd = request.getRequestDispatcher("/product/mylist.jsp");
			rd.forward(request, response);
			// myList는 userid가 작성한 판매게시물을 내 정보에서 확인하고 수정 삭제가 가능한 기능을 만들고 싶다.

		} else if (url.indexOf("insert_product.do") != -1) {
			ServletContext application = request.getSession().getServletContext();
			String img_path1 = application.getRealPath("/images/");
			// String img_path = "C:/work/marketjakun/src/main/webapp/images/";
			String filename = " ";
			try {
				for (Part part : request.getParts()) {
					filename = part.getSubmittedFileName();
					if (filename != null && !filename.trim().equals("")) {
						part.write(img_path1 + filename);
						// part.write(img_path + filename);
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			String subject = request.getParameter("subject");
			int price = Integer.parseInt(request.getParameter("price"));
			String contents = request.getParameter("contents");

			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userid");
			System.out.println(userid);

			ProductDTO dto = new ProductDTO();
			dto.setSubject(subject);
			dto.setPrice(price);
			dto.setContents(contents);
			dto.setUserid(userid);
			if (filename == null || filename.trim().equals("")) {
				filename = "-";
			}
			dto.setFilename(filename);
			dao.insertProduct(dto);
			System.out.println(dto);
			String page = path + "/mk_servlet/list.do";
			response.sendRedirect(page);

		} else if (url.indexOf("detail.do") != -1) {
			int write_code = Integer.parseInt(request.getParameter("write_code"));
			ProductDTO dto = dao.detailProduct(write_code);
			request.setAttribute("dto", dto);
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userid");
			dao.see(write_code);
			int check = dao.loveCheck(userid, write_code);
			session.setAttribute("check", check);
			RequestDispatcher rd = request.getRequestDispatcher("/product/detail.jsp");
			rd.forward(request, response);

		} else if (url.indexOf("edit.do") != -1) {
			int write_code = Integer.parseInt(request.getParameter("write_code"));
			ProductDTO dto = dao.detailProduct(write_code);
			request.setAttribute("dto", dto);
			RequestDispatcher rd = request.getRequestDispatcher("/product/edit.jsp");
			rd.forward(request, response);

		} else if (url.indexOf("update.do") != -1) {
			ServletContext application = request.getSession().getServletContext();
			String img_path = application.getRealPath("/images/");
			String filename = " ";
			try {
				for (Part part : request.getParts()) {
					filename = part.getSubmittedFileName();
					if (filename != null && !filename.trim().equals("")) {
						part.write(img_path + filename);
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			String subject = request.getParameter("subject");
			int price = Integer.parseInt(request.getParameter("price"));
			String contents = request.getParameter("contents");
			int write_code = Integer.parseInt(request.getParameter("write_code"));
			ProductDTO dto = new ProductDTO();
			dto.setSubject(subject);
			dto.setPrice(price);
			dto.setContents(contents);
			dto.setWrite_code(write_code);
			if (filename == null || filename.trim().equals("")) {
				ProductDTO dto2 = dao.detailProduct(write_code);
				filename = dto2.getFilename();
				dto.setFilename(filename);
			} else {
				dto.setFilename(filename);
			}
			dao.updateProduct(dto);
			String page = path + "/mk_servlet/myList.do";
			response.sendRedirect(page);

		} else if (url.indexOf("delete.do") != -1) {
			int write_code = Integer.parseInt(request.getParameter("write_code"));
			dao.deleteProduct(write_code);
			String page = path + "/mk_servlet/myList.do";
			response.sendRedirect(page);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}