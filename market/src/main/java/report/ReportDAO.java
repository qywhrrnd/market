package report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import auction.AuctionDTO;
import sqlmap.MybatisManager;

public class ReportDAO {
	
	public void insertReport(ReportDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		String contents = dto.getReport_contents();
		contents = contents.replace("<", "&lt;");
		contents = contents.replace(">", "&gt;");
		contents = contents.replace("\n", "<br>");
		contents = contents.replace("  ", "&nbsp;&nbsp;");
		dto.setReport_contents(contents);
		session.insert("report.insert", dto);
		session.commit();
		session.close();

	}

	public List<ReportDTO> report_list() {
		List<ReportDTO> list = null;
		SqlSession session = MybatisManager.getInstance().openSession();
		list = session.selectList("report.report_list");
		session.close();
		return list;
	}
	public void report_delete(int idx) {
		SqlSession session = MybatisManager.getInstance().openSession();
		System.out.println(idx);
		session.delete("report.report_delete", idx);
		session.commit();
		session.close();
		
	}

}
