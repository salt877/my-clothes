package jp.co.example.my.clothes.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.domain.UserDetail;

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

	private static final RowMapper<Coordinate> COORDINATE_ROW_MAPPER = new BeanPropertyRowMapper<>(Coordinate.class);

	/**
	 * ユーザーごとのコーディネートデータを格納するResultSetExtractor.
	 */
	private static final ResultSetExtractor<List<Coordinate>> COORDINATE_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Coordinate> coordinateList = new ArrayList<>();
		List<Clothes> clothesList = new ArrayList<>();
		Coordinate coordinate = new Coordinate();
		User user = new User();
		UserDetail userDetail = new UserDetail();

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

				user = new User();
				user.setId(rs.getInt("u_id"));
				user.setMyqloId(rs.getString("u_myqlo_id"));
				user.setEmail(rs.getString("u_email"));
				user.setPassword(rs.getString("u_password"));
				user.setDeleted(rs.getBoolean("u_deleted"));
				coordinate.setUser(user);

				userDetail = new UserDetail();
				userDetail.setId(rs.getInt("ud_id"));
				userDetail.setUserId(rs.getInt("ud_user_id"));
				userDetail.setImagePath(rs.getString("ud_image_path"));
				userDetail.setUserName(rs.getString("ud_user_name"));
				userDetail.setGender(rs.getString("ud_gender"));
				userDetail.setHeight(rs.getInt("ud_height"));
				userDetail.setAge(rs.getString("ud_age"));
				userDetail.setSelfIntroduction(rs.getString("ud_self_introduction"));

				coordinate.setUserDetail(userDetail);
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
	 * コーデを全件検索します.
	 * 
	 * @return
	 */
	public List<Coordinate> findAll() {
		String sql = "SELECT id, user_id, fashion_accessories, tops1, tops2, outers, bottoms, shoes, bag, dress, deleted, name, is_public FROM coordinates";

		List<Coordinate> coordinateList = template.query(sql, COORDINATE_ROW_MAPPER);

		return coordinateList;
	}

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
		sql.append("ca.id ca_id, ca.name ca_name, b.id b_id, b.name b_name, ");
		sql.append("u.id u_id, u.myqlo_id u_myqlo_id, u.email u_email, u.password u_password, u.deleted u_deleted, ");
		sql.append(
				"ud.id ud_id, ud.user_id ud_user_id, ud.image_path ud_image_path, ud.user_name ud_user_name, ud.gender ud_gender, ud.height ud_height, ud.age ud_age, ud.self_introduction ud_self_introduction ");
		sql.append("FROM coordinates co LEFT OUTER JOIN clothes cl ");
		sql.append(
				"ON co.fashion_accessories  = cl.id OR co.tops1 = cl.id OR co.tops2 = cl.id OR co.outers = cl.id OR co.bottoms = cl.id OR co.shoes = cl.id OR co.bag = cl.id OR co.dress = cl.id ");
		sql.append("LEFT OUTER JOIN categories ca ON cl.category_id = ca.id ");
		sql.append("LEFT OUTER JOIN brands b ON cl.brand_id = b.id ");
		sql.append("LEFT OUTER JOIN users u ON co.user_id = u.id ");
		sql.append("LEFT OUTER JOIN user_details ud ON co.user_id = ud.user_id ");
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
		sql.append("ca.id ca_id, ca.name ca_name, b.id b_id, b.name b_name, ");
		sql.append("u.id u_id, u.myqlo_id u_myqlo_id, u.email u_email, u.password u_password, u.deleted u_deleted, ");
		sql.append(
				"ud.id ud_id, ud.user_id ud_user_id, ud.image_path ud_image_path, ud.user_name ud_user_name, ud.gender ud_gender, ud.height ud_height, ud.age ud_age, ud.self_introduction ud_self_introduction ");
		sql.append("FROM coordinates co LEFT OUTER JOIN clothes cl ");
		sql.append(
				"ON co.fashion_accessories  = cl.id OR co.tops1 = cl.id OR co.tops2 = cl.id OR co.outers = cl.id OR co.bottoms = cl.id OR co.shoes = cl.id OR co.bag = cl.id OR co.dress = cl.id ");
		sql.append("LEFT OUTER JOIN categories ca ON cl.category_id = ca.id ");
		sql.append("LEFT OUTER JOIN brands b ON cl.brand_id = b.id ");
		sql.append("LEFT OUTER JOIN users u ON co.user_id = u.id ");
		sql.append("LEFT OUTER JOIN user_details ud ON co.user_id = ud.user_id ");
		sql.append("WHERE co.deleted = 'FALSE' AND co.is_public = 'TRUE' ");
		sql.append("ORDER BY co.id, cl.category_id");

		List<Coordinate> coordinateList = template.query(sql.toString(), COORDINATE_RESULT_SET_EXTRACTOR);

		if (coordinateList.size() == 0) {
			return Collections.emptyList();

		}

		return coordinateList;

	}

	/**
	 * ユーザーIDで公開コーディネート情報を取得します.
	 * 
	 * @return
	 */
	public List<Coordinate> findByIsPublicAndUserId(Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT co.id co_id, co.user_id co_user_id, co.fashion_accessories co_fashion_accessories, co.tops1 co_tops1, co.tops2 co_tops2, ");
		sql.append(
				"co.outers co_outers, co.bottoms co_bottoms, co.shoes co_shoes, co.bag co_bag, co.dress co_dress, co.deleted co_deleted, co.name co_name, co.is_public co_is_public, b.id b_id, b.name b_name, ");
		sql.append(
				"cl.id cl_id, cl.user_id cl_user_id, cl.category_id cl_category_id, cl.brand_id cl_brand_id, cl.color_id cl_color_id, cl.season cl_season, cl.image_path cl_image_path, ");
		sql.append(
				"cl.perchase_date cl_perchase_date, cl.price cl_price, cl.size_id cl_size_id, cl.comment cl_comment, cl.deleted cl_deleted, ");
		sql.append("ca.id ca_id, ca.name ca_name, b.id b_id, b.name b_name, ");
		sql.append("u.id u_id, u.myqlo_id u_myqlo_id, u.email u_email, u.password u_password, u.deleted u_deleted, ");
		sql.append(
				"ud.id ud_id, ud.user_id ud_user_id, ud.image_path ud_image_path, ud.user_name ud_user_name, ud.gender ud_gender, ud.height ud_height, ud.age ud_age, ud.self_introduction ud_self_introduction ");
		sql.append("FROM coordinates co LEFT OUTER JOIN clothes cl ");
		sql.append(
				"ON co.fashion_accessories  = cl.id OR co.tops1 = cl.id OR co.tops2 = cl.id OR co.outers = cl.id OR co.bottoms = cl.id OR co.shoes = cl.id OR co.bag = cl.id OR co.dress = cl.id ");
		sql.append("LEFT OUTER JOIN categories ca ON cl.category_id = ca.id ");
		sql.append("LEFT OUTER JOIN brands b ON cl.brand_id = b.id ");
		sql.append("LEFT OUTER JOIN users u ON co.user_id = u.id ");
		sql.append("LEFT OUTER JOIN user_details ud ON co.user_id = ud.user_id ");
		sql.append("WHERE co.user_id = :userId AND co.deleted = 'FALSE' AND co.is_public = 'TRUE' ");
		sql.append("ORDER BY co.id, cl.category_id");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);

		List<Coordinate> coordinateList = template.query(sql.toString(), param, COORDINATE_RESULT_SET_EXTRACTOR);

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
	public Coordinate loadForPastCoordinate(Integer coordinateId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT co.id co_id, co.user_id co_user_id, co.fashion_accessories co_fashion_accessories, co.tops1 co_tops1, co.tops2 co_tops2, ");
		sql.append(
				"co.outers co_outers, co.bottoms co_bottoms, co.shoes co_shoes, co.bag co_bag, co.dress co_dress, co.deleted co_deleted, co.name co_name, co.is_public co_is_public, b.id b_id, b.name b_name, ");
		sql.append(
				"cl.id cl_id, cl.user_id cl_user_id, cl.category_id cl_category_id, cl.brand_id cl_brand_id, cl.color_id cl_color_id, cl.season cl_season, cl.image_path cl_image_path, ");
		sql.append(
				"cl.perchase_date cl_perchase_date, cl.price cl_price, cl.size_id cl_size_id, cl.comment cl_comment, cl.deleted cl_deleted, ");
		sql.append("ca.id ca_id, ca.name ca_name, b.id b_id, b.name b_name, ");
		sql.append("u.id u_id, u.myqlo_id u_myqlo_id, u.email u_email, u.password u_password, u.deleted u_deleted, ");
		sql.append(
				"ud.id ud_id, ud.user_id ud_user_id, ud.image_path ud_image_path, ud.user_name ud_user_name, ud.gender ud_gender, ud.height ud_height, ud.age ud_age, ud.self_introduction ud_self_introduction ");
		sql.append("FROM coordinates co LEFT OUTER JOIN clothes cl ");
		sql.append(
				"ON co.fashion_accessories  = cl.id OR co.tops1 = cl.id OR co.tops2 = cl.id OR co.outers = cl.id OR co.bottoms = cl.id OR co.shoes = cl.id OR co.bag = cl.id OR co.dress = cl.id ");
		sql.append("LEFT OUTER JOIN categories ca ON cl.category_id = ca.id ");
		sql.append("LEFT OUTER JOIN brands b ON cl.brand_id = b.id ");
		sql.append("LEFT OUTER JOIN users u ON co.user_id = u.id ");
		sql.append("LEFT OUTER JOIN user_details ud ON co.user_id = ud.user_id ");
		sql.append("WHERE co.id = :coordinateId AND co.deleted = 'FALSE' ");
		sql.append("ORDER BY co.id, cl.category_id");

		SqlParameterSource param = new MapSqlParameterSource().addValue("coordinateId", coordinateId);
		List<Coordinate> coordinateList = template.query(sql.toString(), param, COORDINATE_RESULT_SET_EXTRACTOR);

		if (coordinateList.size() == 0) {
			return null;
		}

		return coordinateList.get(0);

	}

	/**
	 * コーディネートIDでコーディネートを１件検索します.
	 * 
	 * @param coordinateId コーデID
	 * @return
	 */
	public Coordinate loadForPublicCoordinate(Integer coordinateId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT co.id co_id, co.user_id co_user_id, co.fashion_accessories co_fashion_accessories, co.tops1 co_tops1, co.tops2 co_tops2, ");
		sql.append(
				"co.outers co_outers, co.bottoms co_bottoms, co.shoes co_shoes, co.bag co_bag, co.dress co_dress, co.deleted co_deleted, co.name co_name, co.is_public co_is_public, b.id b_id, b.name b_name, ");
		sql.append(
				"cl.id cl_id, cl.user_id cl_user_id, cl.category_id cl_category_id, cl.brand_id cl_brand_id, cl.color_id cl_color_id, cl.season cl_season, cl.image_path cl_image_path, ");
		sql.append(
				"cl.perchase_date cl_perchase_date, cl.price cl_price, cl.size_id cl_size_id, cl.comment cl_comment, cl.deleted cl_deleted, ");
		sql.append("ca.id ca_id, ca.name ca_name, b.id b_id, b.name b_name, ");
		sql.append("u.id u_id, u.myqlo_id u_myqlo_id, u.email u_email, u.password u_password, u.deleted u_deleted, ");
		sql.append(
				"ud.id ud_id, ud.user_id ud_user_id, ud.image_path ud_image_path, ud.user_name ud_user_name, ud.gender ud_gender, ud.height ud_height, ud.age ud_age, ud.self_introduction ud_self_introduction ");
		sql.append("FROM coordinates co LEFT OUTER JOIN clothes cl ");
		sql.append(
				"ON co.fashion_accessories  = cl.id OR co.tops1 = cl.id OR co.tops2 = cl.id OR co.outers = cl.id OR co.bottoms = cl.id OR co.shoes = cl.id OR co.bag = cl.id OR co.dress = cl.id ");
		sql.append("LEFT OUTER JOIN categories ca ON cl.category_id = ca.id ");
		sql.append("LEFT OUTER JOIN brands b ON cl.brand_id = b.id ");
		sql.append("LEFT OUTER JOIN users u ON co.user_id = u.id ");
		sql.append("LEFT OUTER JOIN user_details ud ON co.user_id = ud.user_id ");
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
	 * マイクロIDでコーディネートを検索します.
	 * 
	 * @param myqloId マイクロID
	 * @return
	 */
	public List<Coordinate> coordinateListByMyqloId(String myqloId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT co.id co_id, co.user_id co_user_id, co.fashion_accessories co_fashion_accessories, co.tops1 co_tops1, co.tops2 co_tops2, ");
		sql.append(
				"co.outers co_outers, co.bottoms co_bottoms, co.shoes co_shoes, co.bag co_bag, co.dress co_dress, co.deleted co_deleted, co.name co_name, co.is_public co_is_public, b.id b_id, b.name b_name, ");
		sql.append(
				"cl.id cl_id, cl.user_id cl_user_id, cl.category_id cl_category_id, cl.brand_id cl_brand_id, cl.color_id cl_color_id, cl.season cl_season, cl.image_path cl_image_path, ");
		sql.append(
				"cl.perchase_date cl_perchase_date, cl.price cl_price, cl.size_id cl_size_id, cl.comment cl_comment, cl.deleted cl_deleted, ");
		sql.append("ca.id ca_id, ca.name ca_name, b.id b_id, b.name b_name, ");
		sql.append("u.id u_id, u.myqlo_id u_myqlo_id, u.email u_email, u.password u_password, u.deleted u_deleted, ");
		sql.append(
				"ud.id ud_id, ud.user_id ud_user_id, ud.image_path ud_image_path, ud.user_name ud_user_name, ud.gender ud_gender, ud.height ud_height, ud.age ud_age, ud.self_introduction ud_self_introduction ");
		sql.append("FROM coordinates co LEFT OUTER JOIN clothes cl ");
		sql.append(
				"ON co.fashion_accessories  = cl.id OR co.tops1 = cl.id OR co.tops2 = cl.id OR co.outers = cl.id OR co.bottoms = cl.id OR co.shoes = cl.id OR co.bag = cl.id OR co.dress = cl.id ");
		sql.append("LEFT OUTER JOIN categories ca ON cl.category_id = ca.id ");
		sql.append("LEFT OUTER JOIN brands b ON cl.brand_id = b.id ");
		sql.append("LEFT OUTER JOIN users u ON co.user_id = u.id ");
		sql.append("LEFT OUTER JOIN user_details ud ON co.user_id = ud.user_id ");
		sql.append("WHERE u.myqlo_id = :myqloId AND co.deleted = 'FALSE' AND co.is_public = 'TRUE' ");
		sql.append("ORDER BY co.id, cl.category_id");

		SqlParameterSource param = new MapSqlParameterSource().addValue("myqloId", myqloId);
		List<Coordinate> coordinateList = template.query(sql.toString(), param, COORDINATE_RESULT_SET_EXTRACTOR);

		if (coordinateList.size() == 0) {
			return Collections.emptyList();
		}

		return coordinateList;

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
				"VALUES (:userId, :fashionAccessories, :tops1, :tops2, :outers, :bottoms, :shoes, :bag, :dress, :name, :public)");

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
	 * ユーザーIDとコーデIDに紐づくいいねを削除
	 * 
	 * @param userId
	 */
	public void delete(Integer coordinateId, Integer userId) {
		String sql = "DELETE FROM likes WHERE coordinate_id = :coordinateId AND user_id = :userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("coordinateId", coordinateId).addValue("userId",
				userId);
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

	/**
	 * 公開情報を更新します.
	 * 
	 * @param coordinateId コーデID
	 * @param isPublic     公開フラグ
	 */
	public void updateIsPublic(Integer coordinateId, boolean isPublic) {
		String sql = "UPDATE coordinates SET is_public = :isPublic WHERE id = :coordinateId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("isPublic", isPublic).addValue("coordinateId",
				coordinateId);
		template.update(sql, param);

	}

	/**
	 * likesテーブルのユーザIDで自分がいいねしたコーデリストを検索します.
	 * 
	 * @param userId ユーザID
	 * @return いいねしたコーディネートリスト
	 */
	public List<Coordinate> findBylike(Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT co.id co_id, li.id li_id, co.user_id co_user_id, li.user_id li_user_id, co.fashion_accessories co_fashion_accessories, co.tops1 co_tops1, co.tops2 co_tops2, ");
		sql.append(
				"co.outers co_outers, co.bottoms co_bottoms, co.shoes co_shoes, co.bag co_bag, co.dress co_dress, co.deleted co_deleted, co.name co_name, ");
		sql.append(
				"co.is_public co_is_public, b.id b_id, b.name b_name,cl.id cl_id, cl.user_id cl_user_id, cl.category_id cl_category_id, cl.brand_id cl_brand_id, ");
		sql.append("cl.color_id cl_color_id, cl.season cl_season, cl.image_path cl_image_path, ");
		sql.append(
				"cl.perchase_date cl_perchase_date, cl.price cl_price, cl.size_id cl_size_id, cl.comment cl_comment, cl.deleted cl_deleted, ");
		sql.append("ca.id ca_id, ca.name ca_name, b.id b_id, b.name b_name, ");
		sql.append("u.id u_id, u.myqlo_id u_myqlo_id, u.email u_email, u.password u_password, u.deleted u_deleted, ");
		sql.append(
				"ud.id ud_id, ud.user_id ud_user_id, ud.image_path ud_image_path, ud.user_name ud_user_name, ud.gender ud_gender, ud.height ud_height, ud.age ud_age, ud.self_introduction ud_self_introduction ");
		sql.append("FROM coordinates co LEFT OUTER JOIN clothes cl ");
		sql.append(
				"ON co.fashion_accessories  = cl.id OR co.tops1 = cl.id OR co.tops2 = cl.id OR co.outers = cl.id OR co.bottoms = cl.id OR co.shoes = cl.id OR co.bag = cl.id OR co.dress = cl.id ");
		sql.append("LEFT OUTER JOIN categories ca ON cl.category_id = ca.id ");
		sql.append("LEFT OUTER JOIN brands b ON cl.brand_id = b.id ");
		sql.append("LEFT OUTER JOIN users u ON co.user_id = u.id ");
		sql.append("LEFT OUTER JOIN user_details ud ON co.user_id = ud.user_id ");

		sql.append("LEFT OUTER JOIN likes li ON co.id = li.coordinate_id ");
		sql.append("WHERE co.deleted = 'FALSE' AND co.is_public = 'TRUE' AND li.user_id= :userId ");
		sql.append("ORDER BY co.id, cl.category_id;");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);

		List<Coordinate> coordinateList = template.query(sql.toString(), param, COORDINATE_RESULT_SET_EXTRACTOR);

		if (coordinateList.size() == 0) {
			return Collections.emptyList();

		}

		return coordinateList;
	}

	/**
	 * likesテーブルのユーザIDで自分にいいねされたコーデリストを検索します.
	 * 
	 * @param userId ユーザID
	 * @return いいねされたコーディネートリスト
	 */
	public List<Coordinate> findByliked(Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT co.id co_id, li.id li_id, co.user_id co_user_id, li.user_id li_user_id, co.fashion_accessories co_fashion_accessories, co.tops1 co_tops1, co.tops2 co_tops2, ");
		sql.append(
				"co.outers co_outers, co.bottoms co_bottoms, co.shoes co_shoes, co.bag co_bag, co.dress co_dress, co.deleted co_deleted, co.name co_name, ");
		sql.append(
				"co.is_public co_is_public, b.id b_id, b.name b_name,cl.id cl_id, cl.user_id cl_user_id, cl.category_id cl_category_id, cl.brand_id cl_brand_id, ");
		sql.append("cl.color_id cl_color_id, cl.season cl_season, cl.image_path cl_image_path, ");
		sql.append(
				"cl.perchase_date cl_perchase_date, cl.price cl_price, cl.size_id cl_size_id, cl.comment cl_comment, cl.deleted cl_deleted, ");
		sql.append("ca.id ca_id, ca.name ca_name, b.id b_id, b.name b_name, ");
		sql.append("u.id u_id, u.myqlo_id u_myqlo_id, u.email u_email, u.password u_password, u.deleted u_deleted, ");
		sql.append(
				"ud.id ud_id, ud.user_id ud_user_id, ud.image_path ud_image_path, ud.user_name ud_user_name, ud.gender ud_gender, ud.height ud_height, ud.age ud_age, ud.self_introduction ud_self_introduction ");
		sql.append("FROM coordinates co LEFT OUTER JOIN clothes cl ");
		sql.append(
				"ON co.fashion_accessories  = cl.id OR co.tops1 = cl.id OR co.tops2 = cl.id OR co.outers = cl.id OR co.bottoms = cl.id OR co.shoes = cl.id OR co.bag = cl.id OR co.dress = cl.id ");
		sql.append("LEFT OUTER JOIN categories ca ON cl.category_id = ca.id ");
		sql.append("LEFT OUTER JOIN brands b ON cl.brand_id = b.id ");
		sql.append("LEFT OUTER JOIN users u ON co.user_id = u.id ");
		sql.append("LEFT OUTER JOIN user_details ud ON co.user_id = ud.user_id ");
		sql.append("right outer join likes li ON co.id = li.coordinate_id ");
		sql.append("WHERE co.deleted = 'FALSE' AND co.is_public = 'TRUE' AND co.user_id = :userId ");
		sql.append("ORDER BY co.id, cl.category_id;");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);

		List<Coordinate> coordinateList = template.query(sql.toString(), param, COORDINATE_RESULT_SET_EXTRACTOR);

		if (coordinateList.size() == 0) {
			return Collections.emptyList();

		}

		return coordinateList;
	}

	/**
	 * コーデ名からコーデを検索します.
	 * 
	 * @param name
	 * @param gender
	 * @return
	 */
	public List<Coordinate> findCoordinatebyName(String name) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT co.id co_id, co.user_id co_user_id, co.fashion_accessories co_fashion_accessories, co.tops1 co_tops1, co.tops2 co_tops2, ");
		sql.append(
				"co.outers co_outers, co.bottoms co_bottoms, co.shoes co_shoes, co.bag co_bag, co.dress co_dress, co.deleted co_deleted, co.name co_name, co.is_public co_is_public, b.id b_id, b.name b_name, ");
		sql.append(
				"cl.id cl_id, cl.user_id cl_user_id, cl.category_id cl_category_id, cl.brand_id cl_brand_id, cl.color_id cl_color_id, cl.season cl_season, cl.image_path cl_image_path, ");
		sql.append(
				"cl.perchase_date cl_perchase_date, cl.price cl_price, cl.size_id cl_size_id, cl.comment cl_comment, cl.deleted cl_deleted, ");
		sql.append("ca.id ca_id, ca.name ca_name, b.id b_id, b.name b_name, ");
		sql.append("u.id u_id, u.myqlo_id u_myqlo_id, u.email u_email, u.password u_password, u.deleted u_deleted, ");
		sql.append(
				"ud.id ud_id, ud.user_id ud_user_id, ud.image_path ud_image_path, ud.user_name ud_user_name, ud.gender ud_gender, ud.height ud_height, ud.age ud_age, ud.self_introduction ud_self_introduction ");
		sql.append("FROM coordinates co LEFT OUTER JOIN clothes cl ");
		sql.append(
				"ON co.fashion_accessories  = cl.id OR co.tops1 = cl.id OR co.tops2 = cl.id OR co.outers = cl.id OR co.bottoms = cl.id OR co.shoes = cl.id OR co.bag = cl.id OR co.dress = cl.id ");
		sql.append("LEFT OUTER JOIN categories ca ON cl.category_id = ca.id ");
		sql.append("LEFT OUTER JOIN brands b ON cl.brand_id = b.id ");
		sql.append("LEFT OUTER JOIN users u ON co.user_id = u.id ");
		sql.append("LEFT OUTER JOIN user_details ud ON co.user_id = ud.user_id ");
		sql.append("WHERE UPPER(co.name) LIKE UPPER(:name) ");
		sql.append("AND co.deleted = 'FALSE' AND co.is_public = 'TRUE' ");
		sql.append("ORDER BY co.id, cl.category_id");

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
		List<Coordinate> coordinateList = template.query(sql.toString(), param, COORDINATE_RESULT_SET_EXTRACTOR);
		if (coordinateList.size() == 0) {
			return Collections.emptyList();
		}

		return coordinateList;

	}

	/**
	 * コーデ名とジェンダーからコーデを検索します.
	 * 
	 * @param name
	 * @param gender
	 * @return
	 */
	public List<Coordinate> findCoordinatebyNameAndgender(String name, String gender) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT co.id co_id, co.user_id co_user_id, co.fashion_accessories co_fashion_accessories, co.tops1 co_tops1, co.tops2 co_tops2, ");
		sql.append(
				"co.outers co_outers, co.bottoms co_bottoms, co.shoes co_shoes, co.bag co_bag, co.dress co_dress, co.deleted co_deleted, co.name co_name, co.is_public co_is_public, b.id b_id, b.name b_name, ");
		sql.append(
				"cl.id cl_id, cl.user_id cl_user_id, cl.category_id cl_category_id, cl.brand_id cl_brand_id, cl.color_id cl_color_id, cl.season cl_season, cl.image_path cl_image_path, ");
		sql.append(
				"cl.perchase_date cl_perchase_date, cl.price cl_price, cl.size_id cl_size_id, cl.comment cl_comment, cl.deleted cl_deleted, ");
		sql.append("ca.id ca_id, ca.name ca_name, b.id b_id, b.name b_name, ");
		sql.append("u.id u_id, u.myqlo_id u_myqlo_id, u.email u_email, u.password u_password, u.deleted u_deleted, ");
		sql.append(
				"ud.id ud_id, ud.user_id ud_user_id, ud.image_path ud_image_path, ud.user_name ud_user_name, ud.gender ud_gender, ud.height ud_height, ud.age ud_age, ud.self_introduction ud_self_introduction ");
		sql.append("FROM coordinates co LEFT OUTER JOIN clothes cl ");
		sql.append(
				"ON co.fashion_accessories  = cl.id OR co.tops1 = cl.id OR co.tops2 = cl.id OR co.outers = cl.id OR co.bottoms = cl.id OR co.shoes = cl.id OR co.bag = cl.id OR co.dress = cl.id ");
		sql.append("LEFT OUTER JOIN categories ca ON cl.category_id = ca.id ");
		sql.append("LEFT OUTER JOIN brands b ON cl.brand_id = b.id ");
		sql.append("LEFT OUTER JOIN users u ON co.user_id = u.id ");
		sql.append("LEFT OUTER JOIN user_details ud ON co.user_id = ud.user_id ");
		sql.append("WHERE UPPER(co.name) LIKE UPPER(:name) AND ud.gender = :gender ");
		sql.append("AND co.deleted = 'FALSE' AND co.is_public = 'TRUE' ");
		sql.append("ORDER BY co.id, cl.category_id");

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%").addValue("gender",
				gender);
		List<Coordinate> coordinateList = template.query(sql.toString(), param, COORDINATE_RESULT_SET_EXTRACTOR);
		if (coordinateList.size() == 0) {
			return Collections.emptyList();
		}

		return coordinateList;

	}
}
