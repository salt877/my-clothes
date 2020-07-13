package jp.co.example.my.clothes.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.my.clothes.domain.Brand;
import jp.co.example.my.clothes.domain.Category;
import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.Coordinate;

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
	 * coordinates/clothes/categories/brandsテーブルを結合した結果を格納するローマッパー.
	 */
	private static final RowMapper<Coordinate> COORDINATE_ROW_MAPPER = (rs, i) -> {
		Coordinate coordinate = new Coordinate();
		coordinate.setId(rs.getInt("id"));
		coordinate.setUserId(rs.getInt("user_id"));
		coordinate.setTops1(rs.getInt("tops1"));
		coordinate.setTops2(rs.getInt("tops2"));
		coordinate.setOuters(rs.getInt("outers"));
		coordinate.setBottoms(rs.getInt("bottoms"));
		coordinate.setShoes(rs.getInt("shoes"));
		coordinate.setBag(rs.getInt("bag"));
		coordinate.setDress(rs.getInt("dress"));
		Clothes clothes = new Clothes();
		clothes.setId(rs.getInt("id"));
		clothes.setUserId(rs.getInt("user_id"));
		clothes.setCategoryId(rs.getInt("category_id"));
		clothes.setBrandId(rs.getInt("brandId"));
		clothes.setColorId(rs.getInt("color_id"));
		clothes.setImagePath(rs.getString("image_path"));
		clothes.setPerchaseDate(rs.getDate("perchase_date"));
		clothes.setPrice(rs.getInt("price"));
		clothes.setSizeId(rs.getInt("size_id"));
		clothes.setSeason(rs.getString("season"));
		clothes.setComment(rs.getString("comment"));
		clothes.setDeleted(rs.getBoolean("deleted"));
		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setName(rs.getString("name"));
		clothes.setCategory(category);
		Brand brand = new Brand();
		brand.setId(rs.getInt("id"));
		brand.setName(rs.getString("name"));
		clothes.setBrand(brand);
		coordinate.setClothes(clothes);
		coordinate.addClothesList(clothes);

		return coordinate;

	};

	/**
	 * ユーザーIDに紐づくコーディネートデータを全件取得します.
	 * 
	 * @param userId ユーザーID
	 * @return コーディネート情報が詰まったリスト
	 */
	public List<Coordinate> findAll(Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT co.id, co.userId, co.fashion_accessories, co.tops1, co.tops2, co.outers, co.bottoms, co.shoes, co.bag, co.dress, co.name ");
		sql.append("FROM coordinates co LEFT OUTER JOIN clothes cl ");
		sql.append(
				"ON co.fashion_accessories  = cl.id OR co.tops1 = cl.id OR co.tops2 = cl.id OR co.outers = cl.id OR co.bottoms = cl.id OR co.shoes = cl.id OR co.bag = cl.id OR co.dress = cl.id ");
		sql.append("LEFT OUTER JOIN categories ca ON cl.category_id = ca.id ");
		sql.append("LEFT OUTER JOIN brands b ON cl.brand_id = cl.id ");
		sql.append("WHERE co.user_id = :userId ");
		sql.append("ORDER BY co.id, cl.id");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Coordinate> coordinateList = template.query(sql.toString(), param, COORDINATE_ROW_MAPPER);

		return coordinateList;
	}

}
