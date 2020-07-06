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
import jp.co.example.my.clothes.domain.Size;

/**
 * サイズ情報をあつかう レポジトリ
 * 
 * @author ashibe
 *
 */
@Repository
public class SizeRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Size> SIZE_ROW_MAPPER = new BeanPropertyRowMapper<>(Size.class);

	/**
	 * サイズ一覧を取得.
	 * 
	 * @return
	 */
	public List<Size> AllsizeList() {
		String sql = "select id,name from sizes order by id";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Size> sizeList = template.query(sql, param, SIZE_ROW_MAPPER);
		return sizeList;
	}

	/**
	 * 選択されたサイズ情報を取得.
	 * 
	 * @param id
	 * @return
	 */
	public Size SizeSearchByid(Integer id) {
		String sql = "select id,name from sizes where id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Size size = template.queryForObject(sql, param, SIZE_ROW_MAPPER);
		return size;

	}

}
