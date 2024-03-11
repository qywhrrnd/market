package market;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import board.BoardDTO;
import sqlmap.MybatisManager;

public class ProductDAO {

	public List<ProductDTO> listProduct(int pageStart, int pageEnd) {
		List<ProductDTO> list = null;
		SqlSession session = MybatisManager.getInstance().openSession();
		Map<String,Object> map = new HashMap<>();
		map.put("start", pageStart);
		map.put("end", pageEnd);
		list = session.selectList("product.list_product",map);
		return list;
	}
	
	public int count() {
		int result = 0;
		SqlSession session = MybatisManager.getInstance().openSession();
		
		try {
			result = session.selectOne("product.count");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) session.close();
		}
		return result;
	}
	

	public void insertProduct(ProductDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		String contents = dto.getContents();
		contents = contents.replace("<", "&lt;");
		contents = contents.replace(">", "&gt;");
		contents = contents.replace("\n", "<br>");
		contents = contents.replace("  ", "&nbsp;&nbsp;");
		dto.setContents(contents);
		session.insert("product.insert", dto);
		session.commit();
		session.close();

	}

	

	public List<ProductDTO> mylist(String userid) {
		SqlSession session = MybatisManager.getInstance().openSession();
		List<ProductDTO> list = session.selectList("product.mylist", userid);
		session.close();
		return list;

	}

	public ProductDTO detailProduct(int write_code) {
		SqlSession session = MybatisManager.getInstance().openSession();
		ProductDTO dto = session.selectOne("product.detail_product", write_code);
		session.close();
		return dto;
	}

	public int loveCheck(String userid, int write_code) {
		SqlSession session = MybatisManager.getInstance().openSession();
		int result = 0;
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("userid", userid);
			map.put("write_code", write_code);
			result = session.selectOne("product.love_check", map);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}
	

	
	public void updateProduct(ProductDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		String contents = dto.getContents();
		contents = contents.replace("<", "&lt;");
		contents = contents.replace(">", "&gt;");
		contents = contents.replace("\n", "<br>");
		contents = contents.replace("  ", "&nbsp;&nbsp;");
		dto.setContents(contents);
		session.insert("product.update", dto);
		session.commit();
		session.close();

	}

	public void deleteProduct(int write_code) {
		SqlSession session = MybatisManager.getInstance().openSession();
		session.delete("product.delete", write_code);
		session.commit();
		session.close();

	}
	
	public void see(int write_code) {
		SqlSession session = MybatisManager.getInstance().openSession();
		session.update("product.see", write_code);
		session.commit();
		session.close();
	}

	
	public void updateStatus(ProductDTO dto) {
		SqlSession session = MybatisManager.getInstance().openSession();
		session.update("product.status",dto);
		session.commit();
		session.close();
	}
	
	public List<ProductDTO> search(String keyword, int start, int end) {
		List<ProductDTO> list = null;
		SqlSession session = MybatisManager.getInstance().openSession();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("keyword", keyword);
			map.put("start", start);
			map.put("end", end);
			list = session.selectList("product.search", map);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}
	
	public int count(String keyword) {
		int result = 0;
		SqlSession session = MybatisManager.getInstance().openSession();
		try {
			result = session.selectOne("product.search_count", keyword);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	public List<ProductDTO> poplist() {
		List<ProductDTO> list = null;
		SqlSession session = MybatisManager.getInstance().openSession();
		list = session.selectList("product.list_pop");
		return list;
	}
	
	public String product_userid(int write_code) {
		SqlSession session = MybatisManager.getInstance().openSession();
		String product_userid = session.selectOne("product.product_userid", write_code);
		session.close();
		return product_userid;
	}
	

}
