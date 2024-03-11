package report;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import love.LoveDTO;

import java.io.IOException;
import java.util.List;

import auction.AuctionDAO;
import auction.AuctionDTO;

public class ReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = request.getRequestURI();
		String path = request.getContextPath();
		ReportDAO dao = new ReportDAO();
		ReportDTO dto = new ReportDTO();

		if (url.indexOf("report.do") != -1) {
			String userid = request.getParameter("userid");
			String subject = request.getParameter("subject");
			String contents = request.getParameter("contents");
			String link = request.getParameter("link");
			String reporter = request.getParameter("reporter");
			dto.setReport_userid(userid);
			dto.setReport_subject(subject);
			dto.setReport_contents(contents);
			dto.setLink(link);
			dto.setReporter(reporter);
			dao.insertReport(dto);
			request.setAttribute("userid", userid);
			request.setAttribute("link", link);
				RequestDispatcher rd = request.getRequestDispatcher("/report/reportclose.jsp");
				rd.forward(request, response);

		}else if (url.indexOf("report_detail.do") != -1) {
			String userid = request.getParameter("userid");
			String link = request.getParameter("link");

			request.setAttribute("userid", userid);
			request.setAttribute("link", link);
				RequestDispatcher rd = request.getRequestDispatcher("/report/report.jsp");
				rd.forward(request, response);

			}else if (url.indexOf("report_List.do") != -1) {
				HttpSession session = request.getSession();
				String userid = (String) session.getAttribute("userid");
				List<ReportDTO> list = dao.report_list(); // List 타입을 LoveDTO로 변경
				request.setAttribute("filesize", list.size());
				request.setAttribute("list", list);
				RequestDispatcher rd = request.getRequestDispatcher("/report/reportlist.jsp");
				rd.forward(request, response);

			} else if (url.indexOf("delete.do") != -1) {
				HttpSession session = request.getSession();
				String userid = (String) session.getAttribute("userid");
				int idx = Integer.parseInt(request.getParameter("idx"));
				dao.report_delete(idx);
				response.sendRedirect(request.getContextPath()+ "/report_servlet/report_List.do");

			} else if (url.indexOf("delete_all.do") != -1) {
				HttpSession session = request.getSession();
				String userid = (String) session.getAttribute("userid");
				String[] num = request.getParameterValues("num");
				
				if (num != null) {
					for (int i = 0; i < num.length; i++) {
						dao.report_delete(Integer.parseInt(num[i]));
					}
				}
			response.sendRedirect(request.getContextPath()+ "/report_servlet/report_List.do");
				
			}
		}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
