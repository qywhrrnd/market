package auction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import market.ProductDTO;
import sqlmap.MybatisManager;

public class AuctionDAO {

	public List<AuctionDTO> listAuction(int pageStart, int pageEnd) {
		List<AuctionDTO> list = null;
		SqlSession session = MybatisManager.getInstance().openSession();
		Map<String, Object> map = new HashMap<>();
		map.put("start", pageStart);
		map.put("end", pageEnd);
		list = session.selectList("auction.list_auction", map);
		session.close();
		return list;
	}

	public void insertAuction(AuctionDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		String contents = dto.getContents();
	      contents = contents.replace("<", "&lt;");
	      contents = contents.replace(">", "&gt;");
	      contents = contents.replace("\n", "<br>");
	      contents = contents.replace("  ", "&nbsp;&nbsp;");
	      dto.setContents(contents);
		session.insert("auction.insert", dto);
		session.commit();
		session.close();

	}

	public List<AuctionDTO> mylist(String userid) {
		SqlSession session = MybatisManager.getInstance().openSession();
		List<AuctionDTO> list = session.selectList("auction.mylist", userid);
		session.close();
		return list;

	}

	public AuctionDTO detailAuction(int auction_code) {
		SqlSession session = MybatisManager.getInstance().openSession();
		AuctionDTO dto = session.selectOne("auction.detail_auction", auction_code);
		session.close();
		return dto;
	}

	public int count() {
		int result = 0;
		SqlSession session = MybatisManager.getInstance().openSession();

		try {
			result = session.selectOne("auction.count");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	public void bid(int price, String biduserid, int auction_code) {
		SqlSession session = MybatisManager.getInstance().openSession();
		Map<String, Object> map = new HashMap<>();
		map.put("price", price);
		map.put("biduserid", biduserid);
		map.put("auction_code", auction_code);

		session.selectOne("auction.bid", map);
		session.commit();
		session.close();

	}

	public AuctionDTO getAuctionInfo(int auctionCode) {
		SqlSession session = MybatisManager.getInstance().openSession();
		AuctionDTO dto = session.selectOne("auction.getAuctionInfo", auctionCode);
		session.close();
		return dto;
	}
	
	
	public void deleteAuction(int auctionCode) {
		SqlSession session = MybatisManager.getInstance().openSession();
		session.delete("auction.delete", auctionCode);
		session.commit();
		session.close();

	}

}