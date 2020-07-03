package jp.co.example.my.clothes.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.my.clothes.domain.Color;

/**
 * カラー情報を扱うレポジトリ.
 * 
 * @author ashibe
 *
 */
@Repository
public class ColorRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Color> COLOR_ROW_MAPPER = new BeanPropertyRowMapper<>(Color.class);

	/**
	 * カラー一覧を取得
	 * 
	 * @return
	 */
	public List<Color> showColorList() {
		String sql = "select id,name from colors order by id";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Color> colorList = template.query(sql, param, COLOR_ROW_MAPPER);
		return colorList;

	}

}
