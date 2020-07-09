package jp.co.example.my.clothes.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.my.clothes.domain.Tag;

@Repository
public class TagRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Tag> TAG_ROW_MAPPER = new BeanPropertyRowMapper<>(Tag.class);

	/**
	 * 登録されたclothesのidに結び付けたタグを登録.
	 * 
	 * @param tag
	 */
	public void insertTag(Tag tag) {

		String sql = "INSERT INTO tags(clothes_id,tag_contents_id) VALUES(:clothesId, :tagContentId)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(tag);
		template.update(sql, param);

	}

}
