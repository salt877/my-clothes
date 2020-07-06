package jp.co.example.my.clothes.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import jp.co.example.my.clothes.domain.Brand;

/**
 * ブランド情報を扱うレポジトリ.
 * 
 * @author ashibe
 *
 */
@Repository
public class BrandRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Brand> BRAND_ROW_MAPPER = new BeanPropertyRowMapper<>(Brand.class);

	/**
	 * オートコンプリート使用のためのブランドリスト.
	 * 
	 * @return
	 */
	public List<Brand> AllbrandList() {
		String sql = "select id,name from brands order by id";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Brand> brandList = template.query(sql, param, BRAND_ROW_MAPPER);
		return brandList;

	}

	/**
	 * 入力されたブランド名と一致するIDの取得.
	 * 
	 * @param name
	 * @return
	 */
	public Brand brandSearchByName(String name) {
		System.out.println(name);

		String sql = "select id ,name from brands where name = :name ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		List<Brand> brandList = template.query(sql, param, BRAND_ROW_MAPPER);
		if (brandList.size()==0) {
			return null;
		} else {
			return brandList.get(0);
		}
	}

}
