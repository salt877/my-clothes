package jp.co.example.my.clothes.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.my.clothes.domain.TagContent;

/**
 * タグ内容の情報を操作するレポジトリ.
 * 
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
	 * 
	 * @return
	 */
	public List<TagContent> AllTagContentList(Integer userId) {
		String sql = "SELECT id,name FROM tag_contents WHERE user_id=:userId or user_id is null ORDER BY id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<TagContent> brandList = template.query(sql, param, TAG_CONTENT_ROW_MAPPER);
		return brandList;
	}

	/**
	 * 登録のために入力されたタグがすでに登録されているか確認. また タグ内容を取得.
	 * 
	 * @param name
	 * @return
	 */
	public TagContent tagCcntentSearchByName(String name) {
		String sql = "SELECT id,name FROM tag_contents WHERE name=:name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		List<TagContent> tagContentList = template.query(sql, param, TAG_CONTENT_ROW_MAPPER);
		if (tagContentList.size() == 0) {
			return null;
		} else {
			return tagContentList.get(0);
		}
	}

	/**
	 * 新しくタグ内容を登録する.
	 * 
	 * @param tagContent
	 */
	public void insertTagContent(TagContent tagContent) {
		String sql = "INSERT INTO tag_contents(name,user_id) VALUES(:name,:userId)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(tagContent);
		template.update(sql, param);

	}

}
