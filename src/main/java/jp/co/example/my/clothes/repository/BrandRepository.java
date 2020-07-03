package jp.co.example.my.clothes.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.my.clothes.domain.Brand;

@Repository
public class BrandRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Brand> BRAND_ROW_MAPPER = new BeanPropertyRowMapper<>(Brand.class);

	public List<Brand> AllbrandList() {
		String sql = "select id,name from brands order by id";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Brand> brandList = template.query(sql, param, BRAND_ROW_MAPPER);
		return brandList;

	}

}
