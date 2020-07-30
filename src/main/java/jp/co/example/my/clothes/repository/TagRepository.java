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
		sql.append("WHERE t.clothes_id=:id ");
		sql.append("ORDER BY tag_id;");
		SqlParameterSource param = new MapSqlParameterSource("id", id);
		return template.query(sql.toString(), param, TAG_ROW_MAPPER2);
	}
	
	/**
	 * タグオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Tag>TAG_ROW_MAPPER3 =(rs,i) ->{
		Tag tag = new Tag();
		tag.setId(rs.getInt("tag_id"));
		tag.setClothesId(rs.getInt("clothes_id"));
		tag.setTagContentId(rs.getInt("tc_id"));
		tag.setUserId(rs.getInt("user_id"));
		// タグコンテンツ
		TagContent tagContent = new TagContent();
		tagContent.setId(rs.getInt("tc_id"));
		tagContent.setName(rs.getString("tc_name"));
		tag.setTagContent(tagContent);
		return tag;
	};
	
	/**
	 * ユーザId・clothesIdでタグ検索を行います.
	 * 
	 * @param userId ユーザId
	 * @param clothesId アイテムId
	 * @return タグリスト
	 */
	public List<Tag> findByUserIdAndClothesIdAndId(Integer clothesId,Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t.id tag_id, t.clothes_id clothes_id, tc.id tc_id,tc.name tc_name, c.user_id user_id ");
		sql.append("FROM tags t JOIN tag_contents tc ON t.tag_contents_id=tc.id ");
		sql.append("JOIN clothes c ON t.clothes_id=c.id ");
		sql.append("WHERE t.clothes_id=:clothesId AND c.user_id=:userId ");
		sql.append("ORDER bY t.id;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("clothesId", clothesId).addValue("userId", userId);
		return template.query(sql.toString(), param, TAG_ROW_MAPPER3);
	}
	
	/**
	 * 登録されているタグの更新を行います.
	 * 
	 * @param tag タグオブジェクト
	 */
	public void update(Tag tag) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(tag);
		String sql = "UPDATE tags SET tag_contents_id=:tagContentId WHERE clothes_id=:clothesId AND id=:id;";
		template.update(sql, param);
	}
	
	

}
