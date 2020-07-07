package jp.co.example.my.clothes.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.my.clothes.domain.Clothes;

/**
 * clothesを扱うためのレポジトリ.
 * 
 * @author ashibe
 *
 */
@Repository
public class ClothesRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Clothes> CLOTHES_ROW_MAPPER = new BeanPropertyRowMapper<>(Clothes.class);

	/**
	 * 新規にアイテム情報をインサート.
	 * 
	 * @param clothes
	 */
	public void insertClothes(Clothes clothes) {
		String sql = "insert into clothes(user_id,image_path,price,category_id,brand_id, color_id,size_id,season,Perchase_date,comment)values(:userId,:imagePath,:price, :categoryId,:brandId,:colorId,:sizeId,:season,:PerchaseDate,:comment)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(clothes);
		template.update(sql, param);

	}

//	private static final RowMapper<Clothes> CLOTHES_ROW_MAPPER = (rs,i) ->{
//		
//		Clothes clothes = new Clothes();
//		clothes.setId(rs.getInt("id"));
//		clothes.setUserId(rs.getInt("user_id"));		
//		clothes.setCategoryId(rs.getInt("category_id"));
//		clothes.setBrandId(rs.getInt("brand_id"));
//		clothes.setImagePath(rs.getString("image_path"));
//		clothes.setPrice(rs.getInt("price"));
//		clothes.setColorId(rs.getInt("color_id"));
//		clothes.setSeason(rs.getString("season"));
//		clothes.setSizeId(rs.getInt("size_id"));
//		clothes.setPerchaseDate(rs.getDate("perchase_date"));
//		clothes.setComment(rs.getString("comment"));
//		clothes.setDeleted(rs.getBoolean("deleted"));
//		return clothes;
//	};

	private static final String SQL = "SELECT id,user_id,category_id,brand_id,image_path,price,color_id,season,size_id,perchase_date,comment,deleted FROM clothes ";

	/**
	 * 登録アイテム一覧をID順で取得します.
	 * 
	 * @return 登録アイテム一覧
	 */
	public List<Clothes> findAll(Integer userId) {
		String sql = SQL + "WHERE user_id=:userId ORDER BY id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Clothes> clothesList = template.query(sql, param, CLOTHES_ROW_MAPPER);
		return clothesList;
	}
}
