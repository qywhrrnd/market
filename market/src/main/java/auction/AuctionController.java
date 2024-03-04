package auction;

import java.io.IOException;
import java.io.PrintWriter;
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
import market.PageUtil;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class AuctionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		String path = request.getContextPath();
		AuctionDAO dao = new AuctionDAO();
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
			List<AuctionDTO> list = dao.listAuction(start, end);

			request.setAttribute("mdao", mdao);
			request.setAttribute("list", list);
			request.setAttribute("page", page);
			RequestDispatcher rd = request.getRequestDispatcher("/auction/auction_list.jsp");
			rd.forward(request, response);

		} else if (url.indexOf("myList.do") != -1) {
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userid");
			List<AuctionDTO> items = dao.mylist(userid);
			AuctionDTO dto = new AuctionDTO();
			dto.setUserid(userid);
			request.setAttribute("list", items);
			RequestDispatcher rd = request.getRequestDispatcher("/auction/auction_mylist.jsp");
			rd.forward(request, response);
			// myList는 userid가 작성한 판매게시물을 내 정보에서 확인하고 수정 삭제가 가능한 기능을 만들고 싶다.

		} else if (url.indexOf("insert_auction.do") != -1) {
			ServletContext application = request.getSession().getServletContext();
			String img_path1 = application.getRealPath("/images/");
			// String img_path = "C:/work/market/src/main/webapp/images/";
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

			AuctionDTO dto = new AuctionDTO();
			dto.setSubject(subject);
			dto.setPrice(price);
			dto.setContents(contents);
			dto.setUserid(userid);
			dto.setBiduserid(userid);
			if (filename == null || filename.trim().equals("")) {
				filename = "-";
			}
			dto.setFilename(filename);
			dao.insertAuction(dto);
			String page = path + "/at_servlet/list.do";
			response.sendRedirect(page);

		} else if (url.indexOf("detail.do") != -1) {
			int auction_code = Integer.parseInt(request.getParameter("auction_code"));
			AuctionDTO dto = dao.detailAuction(auction_code);
			request.setAttribute("dto", dto);
			RequestDispatcher rd = request.getRequestDispatcher("/auction/auction_detail.jsp");
			rd.forward(request, response);

		} else if (url.indexOf("bid.do") != -1) {

			String biduserid = request.getParameter("biduserid");
			int price = Integer.parseInt(request.getParameter("bidPrice"));
			int auction_code = Integer.parseInt(request.getParameter("auction_code"));
			System.out.println(biduserid);
			System.out.println(price);
			System.out.println(auction_code);
			dao.bid(price, biduserid, auction_code);

		} else if (url.indexOf("updatePriceAndBidder.do") != -1) {
			int auction_code = Integer.parseInt(request.getParameter("auction_code"));
			AuctionDTO dto = dao.getAuctionInfo(auction_code);
			int price = dto.getPrice();
			String bidder = dto.getBiduserid();
			int time = dto.getTime();
			System.out.println(bidder);
			System.out.println(price);
			System.out.println(auction_code);
			dao.bid(price, bidder, auction_code);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.println("{ \"price\": " + price + ", \"bidder\": \"" + bidder + "\", \"time\": " + time + " }");
			out.close();

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}