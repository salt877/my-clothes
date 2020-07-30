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

import jp.co.example.my.clothes.domain.Clothes;
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
		sql.append("SELECT tc.id tc_id, tc.name tc_name, t.id tag_id, t.clothes_id clothes_id ");
		sql.append("FROM tag_contents tc ");
		sql.append("JOIN tags t ");
		sql.append("ON tc.id=t.tag_contents_id ");
		sql.append("WHERE t.clothes_id=:id;");
		SqlParameterSource param = new MapSqlParameterSource("id", id);
		return template.query(sql.toString(), param, TAG_ROW_MAPPER2);
	}
	
	/**
	 * タグオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Tag>TAG_ROW_MAPPER3 =(rs,i) ->{
		Tag tag = new Tag();
		tag.setId(rs.getInt("t_id"));
		tag.setClothesId(rs.getInt("t_clothes_id"));
		tag.setTagContentId(rs.getInt("t_tag_contents_id"));
		tag.setUserId(rs.getInt("c_user_id"));
		return tag;
	};
	
	
	/**
	 * ユーザID・アイテムID・タグコンテンツIDで1件のタグを検索します.
	 * 
	 * @param userId ユーザID
	 * @param clothesId アイテムID
	 * @param tagContentId タグコンテンツID
	 * @return 1件のタグ
	 */
	public Tag findById(Integer userId, Integer clothesId, Integer tagContentId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t.id t_id,t.clothes_id t_clothes_id, t.tag_contents_id t_tag_contents_id,c.user_id c_user_id ");
		sql.append("FROM tags t INNER JOIN clothes c ");
		sql.append("ON t.clothes_id = c.id ");
		sql.append("WHERE c.user_id=:userId AND t.clothes_id=:tagContentId AND t.tag_contents_id=:clothesId;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("clothesId", clothesId).addValue("tagContentId", tagContentId);
		return template.queryForObject(sql.toString(), param, TAG_ROW_MAPPER3);
	}
	
	/**
	 * 登録されているタグの更新を行います.
	 * 
	 * @param tag タグオブジェクト
	 */
	public void update(Tag tag) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(tag);
		String sql = "UPDATE tags SET tag_contents_id=:tagContentId;";
		template.update(sql, param);
	}

}
