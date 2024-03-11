package market;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import login.MemberDAO;
import login.MemberDTO;
import love.LoveDAO;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		String path = request.getContextPath();
		ProductDAO dao = new ProductDAO();
		ProductDTO dto = new ProductDTO();
		LoveDAO ldao = new LoveDAO();
		MemberDAO mdao = new MemberDAO();
		if (url.indexOf("list.do") != -1) {
			int count = dao.count();
			int cur_page = 1;
			if (request.getParameter("cur_page") != null) {
				cur_page = Integer.parseInt(request.getParameter("cur_page"));
			}
			PageUtil page = new PageUtil(count, cur_page);
			int start = page.getPageBegin();
			int end = page.getPageEnd();

			List<ProductDTO> list = dao.listProduct(start, end);
			request.setAttribute("dao", ldao);
			request.setAttribute("mdao", mdao);
			request.setAttribute("list", list);
			request.setAttribute("page", page);

			RequestDispatcher rd = request.getRequestDispatcher("/product/list.jsp");
			rd.forward(request, response);

		} else if (url.indexOf("myList.do") != -1) {
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userid");
			List<ProductDTO> items = dao.mylist(userid);
			dto.setUserid(userid);
			request.setAttribute("list", items);
			RequestDispatcher rd = request.getRequestDispatcher("/product/mylist.jsp");
			rd.forward(request, response);
			// myList는 userid가 작성한 판매게시물을 내 정보에서 확인하고 수정 삭제가 가능한 기능을 만들고 싶다.

		} else if (url.indexOf("insert_product.do") != -1) {
		    ServletContext application = request.getSession().getServletContext();
		    String imgPath = application.getRealPath("/images/");

		    String filename = "";

		    try {
		        boolean fileUploaded = false;

		        for (Part part : request.getParts()) {
		            filename = part.getSubmittedFileName();

		            if (filename != null && !filename.trim().equals("")) {
		                part.write(imgPath + filename);
		                fileUploaded = true;
		                break;
		            }
		        }

		        // 파일이 없을 때 기본 이미지를 내 파일에서 가져와 사용
		        if (!fileUploaded) {
		            // 기본 이미지 파일 경로를 적절히 설정하세요.
		            String defaultImagePath = application.getRealPath("/images/nogazi.jpg");

		            try (FileInputStream input = new FileInputStream(defaultImagePath);
		            		FileOutputStream output = new FileOutputStream(imgPath + "nogazi.jpg")) {

		                byte[] buffer = new byte[1024];
		                int bytesRead;
		                while ((bytesRead = input.read(buffer)) != -1) {
		                    output.write(buffer, 0, bytesRead);
		                }
		            } catch (IOException e) {
		                e.printStackTrace();
		            }

		            filename = "nogazi.jpg";
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    String subject = request.getParameter("subject");
		    int price = Integer.parseInt(request.getParameter("price"));
		    String contents = request.getParameter("contents");

		    HttpSession session = request.getSession();
		    String userid = (String) session.getAttribute("userid");

		    dto.setSubject(subject);
		    dto.setPrice(price);
		    dto.setContents(contents);
		    dto.setUserid(userid);
		    dto.setFilename(filename);

		    dao.insertProduct(dto);
		    String page = path + "/mk_servlet/list.do";
		    response.sendRedirect(page);
		} else if (url.indexOf("detail.do") != -1) {
			int write_code = Integer.parseInt(request.getParameter("write_code"));
			dto = dao.detailProduct(write_code);
			request.setAttribute("dto", dto);
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userid");
			dao.see(write_code);
			int check = dao.loveCheck(userid, write_code);
			String product_userid = dao.product_userid(write_code);
			String address = mdao.address(product_userid);
			request.setAttribute("address", address);
			session.setAttribute("check", check);
			RequestDispatcher rd = request.getRequestDispatcher("/product/detail.jsp");
			rd.forward(request, response);

		} else if (url.indexOf("edit.do") != -1) {
			int write_code = Integer.parseInt(request.getParameter("write_code"));
			dto = dao.detailProduct(write_code);
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
		} else if (url.indexOf("updateStatus.do") != -1) {
			int write_code = Integer.parseInt(request.getParameter("write_code"));
			int status_code = Integer.parseInt(request.getParameter("status_code"));
			dto.setWrite_code(write_code);
			dto.setStatus_code(status_code);
			dao.updateStatus(dto);

			String page = path + "/mk_servlet/myList.do";
			response.sendRedirect(page);
		} else if (url.indexOf("search.do") != -1) {
			String keyword = request.getParameter("keyword");

			int count = dao.count(keyword);
			int cur_page = 1;
			if (request.getParameter("cur_page") != null) {
				cur_page = Integer.parseInt(request.getParameter("cur_page"));
			}
			PageUtil page = new PageUtil(count, cur_page);
			int start = page.getPageBegin();
			int end = page.getPageEnd();
			List<ProductDTO> list = dao.search(keyword, start, end);
			request.setAttribute("list", list);
			request.setAttribute("keyword", keyword);
			request.setAttribute("page", page);
			request.setAttribute("dao", ldao);
			request.setAttribute("mdao", mdao);

			RequestDispatcher rd = request.getRequestDispatcher("/product/list.jsp");
			rd.forward(request, response);
		} else if (url.indexOf("admin_del.do") != -1) {
			int write_code = Integer.parseInt(request.getParameter("write_code"));
			dao.deleteProduct(write_code);
			String page = path + "/mk_servlet/list.do";
			response.sendRedirect(page);
		} else if (url.indexOf("pop.do") != -1) {
			List<ProductDTO> list = dao.poplist();
			request.setAttribute("dao", ldao);
			request.setAttribute("list", list);
			RequestDispatcher rd = request.getRequestDispatcher("/main/main.jsp");
			rd.forward(request, response);
		}else if (url.indexOf("address.do") != -1) {
			String address = request.getParameter("address");
			request.setAttribute("address", address);
			RequestDispatcher rd = request.getRequestDispatcher("/main/map.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}