package jp.co.example.my.clothes.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.my.clothes.domain.TagContent;

/**
 * タグ内容の情報を操作するレポジトリ.
 * @author ashibe
 *
 */
@Repository
public class TagContentRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<TagContent> TAG_CONTENT_ROW_MAPPER = new BeanPropertyRowMapper<>(TagContent.class);

	/**
	 * タグ一覧を取得.
	 * @return
	 */
	public List<TagContent> AllTagContentList() {
		String sql = "select id,name from tag_contents order by id";
		SqlParameterSource param = new MapSqlParameterSource();
		List<TagContent> brandList = template.query(sql, param, TAG_CONTENT_ROW_MAPPER);
		return brandList;
	}

}
