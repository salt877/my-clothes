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

import jp.co.example.my.clothes.domain.Brand;
import jp.co.example.my.clothes.domain.Category;
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

	private static final String SQL = "SELECT id,user_id,category_id,brand_id,image_path,price,color_id,season,size_id,perchase_date,comment,deleted FROM clothes WHERE ";

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
	 * 登録アイテム一覧をID順で取得します.
	 * 
	 * @param ログインユーザID
	 * @return 登録アイテム一覧
	 */
	public List<Clothes> findAll(Integer userId) {
		String sql = SQL + "user_id=:userId ORDER BY id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Clothes> clothesList = template.query(sql, param, CLOTHES_ROW_MAPPER);
		return clothesList;
	}

	/**
	 * 登録アイテムをカテゴリごとに分けて表示します.
	 * 
	 * @param userId ログインユーザID
	 * @param        categoryId カテゴリID
	 * @return 登録アイテム一覧
	 */
	public List<Clothes> findByCategory(Integer userId, Integer categoryId) {
		String sql = SQL + "user_id=:userId AND category_id=:categoryId ORDER BY id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("categoryId",
				categoryId);
		List<Clothes> clothesList = template.query(sql, param, CLOTHES_ROW_MAPPER);
		return clothesList;
	}

	/**
	 * 統計画面用のローマッパー.
	 */
	private static final RowMapper<Clothes> CLOTHES_ROW_MAPPER2 = (rs, i) -> {
		Clothes clothes = new Clothes();
		clothes.setImagePath(rs.getString("cl_image_path"));
		clothes.setUserId(rs.getInt("cl_user_id"));
		clothes.setCategoryId(rs.getInt("cl_category_id"));
		clothes.setBrandId(rs.getInt("cl_brand_id"));
		clothes.setColorId(rs.getInt("cl_color_id"));
		clothes.setId(rs.getInt("cl_id"));
		clothes.setPrice(rs.getInt("price"));
		clothes.setSizeId(rs.getInt("cl_size_id"));
		clothes.setSeason(rs.getString("cl_season"));
		clothes.setPerchaseDate(rs.getDate("cl_perchase_date"));
		clothes.setComment(rs.getString("cl_comment"));
		clothes.setDeleted(rs.getBoolean("cl_deleted"));
		Category category = new Category();
		category.setId(rs.getInt("ca_id"));
		category.setName(rs.getString("ca_name"));
		clothes.setCategory(category);
		Brand brand = new Brand();
		brand.setId(rs.getInt("b_id"));
		brand.setName(rs.getString("b_name"));
		clothes.setBrand(brand);

		return clothes;
	};

	/**
	 * ユーザーIDで服データ検索.
	 * 
	 * @param userId ユーザーID
	 * @return clothesオブジェクトが入ったリスト（０件の場合nullを返します。）
	 */
	public List<Clothes> findByUserId(Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT cl.id cl_id, cl.user_id cl_user_id, cl.image_path cl_image_path, CASE WHEN cl.price IS NULL THEN 0 ELSE cl.price END, ");
		sql.append(
				"cl.category_id cl_category_id, cl.brand_id cl_brand_id, cl.color_id cl_color_id, cl.size_id cl_size_id, ");
		sql.append(
				"cl.season cl_season, cl.perchase_date cl_perchase_date, cl.comment cl_comment, cl.deleted cl_deleted, ");
		sql.append(
				"ca.id ca_id, ca.name ca_name, b.id b_id, b.name b_name FROM clothes cl left outer join categories ca on cl.category_id = ca.id ");
		sql.append("left outer join brands b on cl.brand_id = b.id WHERE user_id = :userId;");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Clothes> clothesList = template.query(sql.toString(), param, CLOTHES_ROW_MAPPER2);

		if (clothesList.size() == 0) {
			return null;
		}

		return clothesList;

	}

	/**
	 * 登録した商品とuserIdに結びつけてタグを登録する為に新規登録したclothesオブジェクトを取得.
	 * 
	 * @param userId
	 * @return
	 */
	public Clothes findNewClothes(Integer userId) {
		String sql = SQL + " 1=1 AND user_id=:userId order by id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Clothes> clothesList = template.query(sql, param, CLOTHES_ROW_MAPPER);
		return clothesList.get(0);

	}

}
