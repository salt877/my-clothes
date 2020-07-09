package jp.co.example.my.clothes.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.my.clothes.domain.Category;

/**
 * カテゴリ情報を操作するレポシトリ.
 * 
 * @author ashibe
 *
 */
@Repository
public class CategoryRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Category> CATEGORY_ROW_MAPPER = new BeanPropertyRowMapper<>(Category.class);

	/**
	 * selectboxの表示の為のカテゴリ一覧を取得.
	 * 
	 * @return カテゴリ一覧
	 */
	public List<Category> showCategoryList() {
		String sql = "select id,name from categories order by name";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return categoryList;
	}

	/**
	 * idでカテゴリを一件取得.
	 * 
	 * @return
	 */
	public Category categorySearchByCategoryId(Integer id) {
		String sql = "select id,name from categories where id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Category category = template.queryForObject(sql, param, CATEGORY_ROW_MAPPER);
		return category;
	}
	
	public List<Category> categoryList(){
		String sql = "select id,name from categories order by id";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return categoryList;
		
	}

}
