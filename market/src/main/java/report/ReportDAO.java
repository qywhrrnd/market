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
