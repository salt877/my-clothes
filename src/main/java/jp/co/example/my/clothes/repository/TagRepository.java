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

import jp.co.example.my.clothes.domain.Tag;
import jp.co.example.my.clothes.domain.TagContent;

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
	
	/**
	 * tagコンテンツを生成するローマッパー.
	 */
	private static final RowMapper<Tag>TAG_ROW_MAPPER2 =(rs,i) ->{
		Tag tag = new Tag();
		tag.setId(rs.getInt("tag_id"));
		tag.setClothesId(rs.getInt("clothes_id"));
		tag.setTagContentId(rs.getInt("tc_id"));
		// タグコンテンツ
		TagContent tagContent = new TagContent();
		tagContent.setId(rs.getInt("tc_id"));
		tagContent.setName(rs.getString("tc_name"));
		tag.setTagContent(tagContent);
		return tag;
	};
	
	/**
	 * DBからタグリストを服のID検索で取得します.
	 * 
	 * @param id 服のID
	 * @return タグ一覧
	 */
	public List<Tag> findById(Integer id){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tc.id tc_id, tc.name tc_name, t.id tag_id, t.clothes_id clothes_id");
		sql.append("FROM tag_contents tc");
		sql.append("JOIN tags t");
		sql.append("ON tc.id=t.tag_contents_id");
		sql.append("WHERE t.clothes_id=1;");
		SqlParameterSource param = new MapSqlParameterSource("id", id);
		return template.query(sql.toString(), param, TAG_ROW_MAPPER2);
	}

}
