package jp.co.example.my.clothes.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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

}
