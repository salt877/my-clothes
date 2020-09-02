package jp.co.example.my.clothes.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.my.clothes.domain.Brand;
import jp.co.example.my.clothes.domain.Category;
import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.Coordinate;
import jp.co.example.my.clothes.domain.Like;

/**
 * coordinatesテーブルを操作するリポジトリ.
 * 
 * @author masashi.nose
 *
 */
@Repository
public class CoordinateRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * ユーザーごとのコーディネートデータを格納するResultSetExtractor.
	 */
	private static final ResultSetExtractor<List<Coordinate>> COORDINATE_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Coordinate> coordinateList = new ArrayList<>();
		List<Clothes> clothesList = new ArrayList<>();
		Like like = new Like();
		Coordinate coordinate = new Coordinate();

		int checkCoId = 0;
		int checkClId = 0;

		while (rs.next()) {
			if (rs.getInt("co_id") != checkCoId) {
				coordinate = new Coordinate();
				coordinate.setId(rs.getInt("co_id"));
				coordinate.setUserId(rs.getInt("co_user_id"));
				coordinate.setFashionAccessories(rs.getInt("co_fashion_accessories"));
				coordinate.setTops1(rs.getInt("co_tops1"));
				coordinate.setTops2(rs.getInt("co_tops2"));
				coordinate.setOuters(rs.getInt("co_outers"));
				coordinate.setBottoms(rs.getInt("co_bottoms"));
				coordinate.setShoes(rs.getInt("co_shoes"));
				coordinate.setBag(rs.getInt("co_bag"));
				coordinate.setDress(rs.getInt("co_dress"));
				coordinate.setDeleted(rs.getBoolean("co_deleted"));
				coordinate.setName(rs.getString("co_name"));
				coordinate.setPublic(rs.getBoolean("co_is_public"));

				clothesList = new ArrayList<>();
				coordinate.setClothesList(clothesList);
				coordinateList.add(coordinate);

			}

			if (rs.getInt("cl_id") != checkClId) {
				Clothes clothes = new Clothes();
				clothesList.add(clothes);
				clothes.setId(rs.getInt("cl_id"));
				clothes.setUserId(rs.getInt("cl_user_id"));
				clothes.setCategoryId(rs.getInt("cl_category_id"));
				clothes.setBrandId(rs.getInt("cl_brand_id"));
				clothes.setColorId(rs.getInt("cl_color_id"));
				clothes.setImagePath(rs.getString("cl_image_path"));
				clothes.setPerchaseDate(rs.getDate("cl_perchase_date"));
				clothes.setPrice(rs.getInt("cl_price"));
				clothes.setSizeId(rs.getInt("cl_size_id"));
				clothes.setSeason(rs.getString("cl_season"));
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

			}

			checkCoId = rs.getInt("co_id");
			checkClId = rs.getInt("cl_id");

		}

		return coordinateList;

	};

	/**
	 * ユーザーIDに紐づくコーディネートデータを全件取得します（coordinates/clothes/categories/brandsテーブル結合）.
	 * 
	 * @param userId ユーザーID
	 * @return コーディネート情報が詰まったリスト
	 */
	public List<Coordinate> findAll(Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT co.id co_id, co.user_id co_user_id, co.fashion_accessories co_fashion_accessories, co.tops1 co_tops1, co.tops2 co_tops2, ");
		sql.append(
				"co.outers co_outers, co.bottoms co_bottoms, co.shoes co_shoes, co.bag co_bag, co.dress co_dress, co.deleted co_deleted, co.name co_name, co.is_public co_is_public, b.id b_id, b.name b_name, ");
		sql.append(
				"cl.id cl_id, cl.user_id cl_user_id, cl.category_id cl_category_id, cl.brand_id cl_brand_id, cl.color_id cl_color_id, cl.season cl_season, cl.image_path cl_image_path, ");
		sql.append(
				"cl.perchase_date cl_perchase_date, cl.price cl_price, cl.size_id cl_size_id, cl.comment cl_comment, cl.deleted cl_deleted, ");
		sql.append("ca.id ca_id, ca.name ca_name, b.id b_id, b.name b_name ");
		sql.append("FROM coordinates co LEFT OUTER JOIN clothes cl ");
		sql.append(
				"ON co.fashion_accessories  = cl.id OR co.tops1 = cl.id OR co.tops2 = cl.id OR co.outers = cl.id OR co.bottoms = cl.id OR co.shoes = cl.id OR co.bag = cl.id OR co.dress = cl.id ");
		sql.append("LEFT OUTER JOIN categories ca ON cl.category_id = ca.id ");
		sql.append("LEFT OUTER JOIN brands b ON cl.brand_id = b.id ");
		sql.append("WHERE co.user_id = :userId AND co.deleted = 'FALSE' ");
		sql.append("ORDER BY co.id, cl.category_id");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Coordinate> coordinateList = template.query(sql.toString(), param, COORDINATE_RESULT_SET_EXTRACTOR);

		if (coordinateList.size() == 0) {
			return Collections.emptyList();

		}

		return coordinateList;
	}

	/**
	 * 公開コーディネート情報を取得します.
	 * 
	 * @return
	 */
	public List<Coordinate> findByIsPublic() {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT co.id co_id, co.user_id co_user_id, co.fashion_accessories co_fashion_accessories, co.tops1 co_tops1, co.tops2 co_tops2, ");
		sql.append(
				"co.outers co_outers, co.bottoms co_bottoms, co.shoes co_shoes, co.bag co_bag, co.dress co_dress, co.deleted co_deleted, co.name co_name, co.is_public co_is_public, b.id b_id, b.name b_name, ");
		sql.append(
				"cl.id cl_id, cl.user_id cl_user_id, cl.category_id cl_category_id, cl.brand_id cl_brand_id, cl.color_id cl_color_id, cl.season cl_season, cl.image_path cl_image_path, ");
		sql.append(
				"cl.perchase_date cl_perchase_date, cl.price cl_price, cl.size_id cl_size_id, cl.comment cl_comment, cl.deleted cl_deleted, ");
		sql.append("ca.id ca_id, ca.name ca_name, b.id b_id, b.name b_name ");
		sql.append("FROM coordinates co LEFT OUTER JOIN clothes cl ");
		sql.append(
				"ON co.fashion_accessories  = cl.id OR co.tops1 = cl.id OR co.tops2 = cl.id OR co.outers = cl.id OR co.bottoms = cl.id OR co.shoes = cl.id OR co.bag = cl.id OR co.dress = cl.id ");
		sql.append("LEFT OUTER JOIN categories ca ON cl.category_id = ca.id ");
		sql.append("LEFT OUTER JOIN brands b ON cl.brand_id = b.id ");
		sql.append("WHERE co.deleted = 'FALSE' AND co.is_public = 'TRUE' ");
		sql.append("ORDER BY co.id, cl.category_id");

		List<Coordinate> coordinateList = template.query(sql.toString(), COORDINATE_RESULT_SET_EXTRACTOR);

		if (coordinateList.size() == 0) {
			return Collections.emptyList();

		}

		return coordinateList;

	}

	/**
	 * コーディネートIDでコーディネートを１件検索します.
	 * 
	 * @param coordinateId コーデID
	 * @return
	 */
	public Coordinate load(Integer coordinateId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT co.id co_id, co.user_id co_user_id, co.fashion_accessories co_fashion_accessories, co.tops1 co_tops1, co.tops2 co_tops2, ");
		sql.append(
				"co.outers co_outers, co.bottoms co_bottoms, co.shoes co_shoes, co.bag co_bag, co.dress co_dress, co.deleted co_deleted, co.name co_name, co.is_public co_is_public, b.id b_id, b.name b_name, ");
		sql.append(
				"cl.id cl_id, cl.user_id cl_user_id, cl.category_id cl_category_id, cl.brand_id cl_brand_id, cl.color_id cl_color_id, cl.season cl_season, cl.image_path cl_image_path, ");
		sql.append(
				"cl.perchase_date cl_perchase_date, cl.price cl_price, cl.size_id cl_size_id, cl.comment cl_comment, cl.deleted cl_deleted, ");
		sql.append("ca.id ca_id, ca.name ca_name, b.id b_id, b.name b_name ");
		sql.append("FROM coordinates co LEFT OUTER JOIN clothes cl ");
		sql.append(
				"ON co.fashion_accessories  = cl.id OR co.tops1 = cl.id OR co.tops2 = cl.id OR co.outers = cl.id OR co.bottoms = cl.id OR co.shoes = cl.id OR co.bag = cl.id OR co.dress = cl.id ");
		sql.append("LEFT OUTER JOIN categories ca ON cl.category_id = ca.id ");
		sql.append("LEFT OUTER JOIN brands b ON cl.brand_id = b.id ");
		sql.append("WHERE co.id = :coordinateId AND co.deleted = 'FALSE' AND co.is_public = 'TRUE' ");
		sql.append("ORDER BY co.id, cl.category_id");

		SqlParameterSource param = new MapSqlParameterSource().addValue("coordinateId", coordinateId);
		List<Coordinate> coordinateList = template.query(sql.toString(), param, COORDINATE_RESULT_SET_EXTRACTOR);

		if (coordinateList.size() == 0) {
			return null;
		}

		return coordinateList.get(0);

	}

	/**
	 * coordinatesテーブルにインサートします.
	 * 
	 * @param coordinate
	 */
	public void insert(Coordinate coordinate) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"INSERT INTO coordinates (user_id, fashion_accessories, tops1, tops2, outers, bottoms, shoes, bag, dress, name, is_public) ");
		sql.append(
				"VALUES (:userId, :fashionAccessories, :tops1, :tops2, :outers, :bottoms, :shoes, :bag, :dress, :name, :isPublic)");

		SqlParameterSource param = new BeanPropertySqlParameterSource(coordinate);

		template.update(sql.toString(), param);

	}

	/**
	 * 
	 * coodinatesテーブルから指定IDのデータを論理削除します.
	 * 
	 * @param coordinateId
	 */
	public void update(Integer coordinateId) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE coordinates SET deleted = 'true' WHERE id = :coordinateId");

		SqlParameterSource param = new MapSqlParameterSource().addValue("coordinateId", coordinateId);
		template.update(sql.toString(), param);

	}

	/** likesテーブルのデータを保持するローマッパー */
	private static final RowMapper<Like> LIKE_ROW_MAPPER = new BeanPropertyRowMapper<>(Like.class);

	/**
	 * likesテーブルにインサート
	 * 
	 * @param like
	 */
	public void insert(Like like) {
		String sql = "INSERT INTO likes (coordinate_id, user_id) VALUES (:coordinateId, :userId)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(like);
		template.update(sql, param);
	}

	/**
	 * ユーザーIDでいいね検索
	 * 
	 * @param userId
	 * @return
	 */
	public List<Like> likeListByUserId(Integer userId) {
		String sql = "SELECT id, coordinate_id, user_id FROM likes WHERE user_id = :userId ORDER BY coordinate_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);

		List<Like> likeList = template.query(sql, param, LIKE_ROW_MAPPER);

		return likeList;

	}

	/**
	 * ユーザーIDに紐づくいいねを削除
	 * 
	 * @param userId
	 */
	public void delete(Integer userId) {
		String sql = "DELETE FROM likes WHERE user_id = :userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		template.update(sql, param);
	}

	/**
	 * 
	 * コーデIDに紐づくいいねを検索します.
	 * 
	 * @param coordinateId コーデID
	 * @return
	 */
	public List<Like> findLikes(Integer coordinateId) {
		String sql = "SELECT id, coordinate_id, user_id FROM likes WHERE coordinate_id = :coordinateId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("coordinateId", coordinateId);

		List<Like> likeList = template.query(sql, param, LIKE_ROW_MAPPER);

		return likeList;

	}

	public Like load(Integer coordinateId, Integer userId) {
		String sql = "SELECT id, coordinate_id, user_id FROM likes WHERE coordinate_id = :coordinateId AND userId = :userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("coordinateId", coordinateId).addValue("userId",
				userId);

		List<Like> likeList = template.query(sql, param, LIKE_ROW_MAPPER);

		if (likeList.size() == 0) {
			return null;
		}

		return likeList.get(0);

	}

}
