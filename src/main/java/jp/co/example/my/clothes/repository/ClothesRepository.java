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

	/**
	 * ユーザーIDで検索.
	 * 
	 * @param userId ユーザーID
	 * @return clothesオブジェクトが入ったリスト（０件の場合nullを返します。）
	 */
	public List<Clothes> findByUserId(Integer userId) {
		String sql = "SELECT user_id, image_path, CASE WHEN price IS NULL THEN 0 ELSE price END, category_id, brand_id, color_id, size_id, season, perchase_date, comment FROM clothes WHERE user_id = :userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Clothes> clothesList = template.query(sql, param, CLOTHES_ROW_MAPPER);

		if (clothesList.size() == 0) {
			return null;
		}

		return clothesList;

	}

	/**
	 * ユーザーIDとカテゴリーIDから服情報を検索.
	 * 
	 * @param userId     ユーザーID
	 * @param categoryId カテゴリーID
	 * @return clothesオブジェクトが入ったリスト（０件の場合nullを返します。）
	 */
	public List<Clothes> findByUserIdAndCategoryId(Integer userId, Integer categoryId) {
		String sql = "SELECT user_id, image_path, price, category_id, brand_id, color_id, size_id, season, perchase_date, comment FROM clothes WHERE user_id = :user_id AND category_id = :categoryId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("categoryId",
				categoryId);
		List<Clothes> clothesListByUserIdAndCategoryId = template.query(sql, param, CLOTHES_ROW_MAPPER);

		if (clothesListByUserIdAndCategoryId.size() == 0) {
			return null;
		}

		return clothesListByUserIdAndCategoryId;
	}

	/**
	 * ユーザーIDとブランドIDから服情報を検索.
	 * 
	 * @param userId  ユーザーID
	 * @param brandId ブランドID
	 * @return clothesオブジェクトが入ったリスト（０件の場合nullを返します。）
	 */
	public List<Clothes> findByUserIdAndBrandId(Integer userId, Integer brandId) {
		String sql = "SELECT user_id, image_path, price, category_id, brand_id, color_id, size_id, season, perchase_date, comment FROM clothes WHERE user_id = :user_id AND category_id = :brandId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("brandId", brandId);
		List<Clothes> clothesListByUserIdAndBrandId = template.query(sql, param, CLOTHES_ROW_MAPPER);

		if (clothesListByUserIdAndBrandId.size() == 0) {
			return null;
		}

		return clothesListByUserIdAndBrandId;
	}

}
