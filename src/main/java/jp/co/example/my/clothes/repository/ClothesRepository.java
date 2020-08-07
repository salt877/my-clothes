package jp.co.example.my.clothes.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.my.clothes.domain.AverageDto;
import jp.co.example.my.clothes.domain.Brand;
import jp.co.example.my.clothes.domain.BrandCountDto;
import jp.co.example.my.clothes.domain.BrandSumDto;
import jp.co.example.my.clothes.domain.Category;
import jp.co.example.my.clothes.domain.CategoryCountDto;
import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.Color;
import jp.co.example.my.clothes.domain.Size;
import jp.co.example.my.clothes.domain.Tag;
import jp.co.example.my.clothes.domain.CategorySumDto;
import jp.co.example.my.clothes.domain.MonthlyDataDTO;
import jp.co.example.my.clothes.domain.TagContent;

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
	private static final RowMapper<Brand> BRAND_ROW_MAPPER = new BeanPropertyRowMapper<>(Brand.class);
	private static final RowMapper<TagContent> TAG_CONTENTS_ROW_MAPPER = new BeanPropertyRowMapper<>(TagContent.class);
	private static final RowMapper<MonthlyDataDTO> DATA_ROW_MAPPER = new BeanPropertyRowMapper<>(MonthlyDataDTO.class);

	/**
	 * カレンダー画面で使用するローマッパー.
	 */
	private static final RowMapper<Clothes> CLOTHES_ROW_MAPPER4 = (rs, i) -> {

		Clothes clothes = new Clothes();
		clothes.setId(rs.getInt("cl_id"));
		clothes.setUserId(rs.getInt("cl_user_id"));
		clothes.setCategoryId(rs.getInt("cl_category_id"));
		clothes.setBrandId(rs.getInt("cl_brand_id"));
		clothes.setImagePath(rs.getString("cl_image_path"));
		clothes.setPrice(rs.getInt("price"));
		clothes.setColorId(rs.getInt("cl_color_id"));
		clothes.setSeason(rs.getString("cl_season"));
		clothes.setSizeId(rs.getInt("cl_size_id"));
		clothes.setPerchaseDate(rs.getDate("cl_perchase_date"));
		clothes.setComment(rs.getString("cl_comment"));
		clothes.setDeleted(rs.getBoolean("cl_deleted"));
		Category category = new Category();
		category.setId(rs.getInt("ca_id"));
		category.setName(rs.getString("ca_name"));
		clothes.setCategory(category);
		return clothes;

	};

	private static final String SQL = "SELECT id,user_id,category_id,brand_id,image_path,price,color_id,season,size_id,perchase_date,comment,deleted ";

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
		String sql = SQL + "FROM clothes WHERE user_id=:userId AND deleted = FALSE ORDER BY id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Clothes> clothesList = template.query(sql, param, CLOTHES_ROW_MAPPER);
		return clothesList;
	}

	/**
	 * カテゴリー名付きの登録アイテム一覧をでID順で取得します.
	 * 
	 * @param userId ログインユーザID
	 * @return 登録アイテム一覧
	 */
	public List<Clothes> findAllWithCategory(Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT cl.id cl_id,cl.user_id cl_user_id,cl.category_id cl_category_id,cl.brand_id cl_brand_id,cl.image_path cl_image_path,");
		sql.append("CASE WHEN cl.price IS NULL THEN 0 ELSE cl.price END,cl.color_id cl_color_id,");
		sql.append(
				"cl.season cl_season,cl.size_id cl_size_id,cl.perchase_date cl_perchase_date,cl.comment cl_comment,cl.deleted cl_deleted,");
		sql.append("ca.id ca_id,ca.name ca_name FROM clothes cl INNER JOIN categories ca ");
		sql.append("ON cl.category_id=ca.id ");
		sql.append("WHERE cl.user_id=:userId AND cl.deleted='FALSE' ORDER BY cl.id;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Clothes> clothesList = template.query(sql.toString(), param, CLOTHES_ROW_MAPPER4);
		return clothesList;
	}

	/**
	 * 登録アイテムをカテゴリごとに分けて表示します.
	 * 
	 * @param userId     ログインユーザID
	 * @param categoryId カテゴリID
	 * @return 登録アイテム一覧
	 */
	public List<Clothes> findByCategory(Integer userId, Integer categoryId) {
		String sql = SQL
				+ "FROM clothes WHERE user_id=:userId AND category_id=:categoryId AND deleted='false' ORDER BY id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("categoryId",
				categoryId);
		List<Clothes> clothesList = template.query(sql, param, CLOTHES_ROW_MAPPER);
		return clothesList;
	}

	/**
	 * 登録アイテムをカテゴリごとに分けて表示します.
	 * 
	 * @param userId     ログインユーザID
	 * @param categoryId カテゴリID
	 * @return 登録アイテム一覧
	 */
	public List<Clothes> findByCategoryForCoordinate(Integer userId, Integer categoryId) {
		String sql = SQL
				+ "FROM clothes WHERE user_id=:userId AND category_id=:categoryId AND deleted = FALSE ORDER BY id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("categoryId",
				categoryId);
		List<Clothes> clothesList = template.query(sql, param, CLOTHES_ROW_MAPPER);

		if (clothesList.size() == 0) {
			return Collections.emptyList();
		}
		return clothesList;
	}

	/**
	 * 登録しているブランド名を検索します.
	 * 
	 * @param userId ログインユーザID
	 * @return ブランド名の入ったリスト
	 */
	public List<Brand> showBrandName(Integer userId) {
		String sql = "SELECT DISTINCT brands.id,brands.name FROM brands INNER JOIN clothes ON brands.id = clothes.brand_id WHERE clothes.user_id=:userId AND deleted = false;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Brand> brandList = template.query(sql, param, BRAND_ROW_MAPPER);
		return brandList;
	}

	/**
	 * ブランド名を一件検索します.
	 * 
	 * @param brandId ブランドID
	 * @return ブランド名
	 */
	public Brand findBrandNameById(Integer brandId) {
		String sql = "SELECT id,name FROM brands WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", brandId);
		Brand brandName = template.queryForObject(sql, param, BRAND_ROW_MAPPER);
		return brandName;
	}

	/**
	 * タグ名を一件検索します.
	 * 
	 * @param tagContentId タグコンテンツID
	 * @return タグ名
	 */
	public TagContent findTagContentsById(Integer tagContentsId) {
		String sql = "SELECT id,name FROM tag_contents WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", tagContentsId);
		TagContent tagName = template.queryForObject(sql, param, TAG_CONTENTS_ROW_MAPPER);
		return tagName;
	}
	

	/**
	 * 登録アイテムをブランド別に分けて表示します.
	 * 
	 * @param userId  ログインユーザID
	 * @param brandId ブランドID
	 * @return 登録アイテム一覧
	 */
	public List<Clothes> findByBrand(Integer userId, Integer brandId) {
		String sql = SQL + "FROM clothes WHERE user_id=:userId AND brand_id=:brandId  AND deleted =false ORDER BY id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("brandId", brandId);
		List<Clothes> clothesList = template.query(sql, param, CLOTHES_ROW_MAPPER);
		return clothesList;
	}

	/**
	 * 登録しているタグを検索します.
	 * 
	 * @param userId ログインユーザID
	 * @return タグ名の入ったリスト
	 */
	public List<TagContent> showTagName(Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT ON (tc.name) tc.id,t.clothes_id,tc.name FROM clothes AS C ");
		sql.append("JOIN tags AS t ON c.id=t.clothes_id JOIN tag_contents AS tc ");
		sql.append("ON t.tag_contents_id=tc.id WHERE c.user_id=:userId AND c.deleted= false");
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<TagContent> tagNameList = template.query(sql.toString(), param, TAG_CONTENTS_ROW_MAPPER);
		return tagNameList;
	}

	// アイテム詳細表示用のローマッパー.
	private static final RowMapper<Clothes> CLOTHES_ROW_MAPPER5 = (rs, i) -> {
		// タグコンテンツ
		TagContent tagContent= new TagContent();
		tagContent.setId(rs.getInt("tc_id"));
		tagContent.setName(rs.getString("name"));
		//タグ
		Tag tag = new Tag();
		tag.setId(rs.getInt("t_id"));
		tag.setClothesId(rs.getInt("c_id"));
		tag.setTagContent(tagContent);
		List<Tag>tagList = new ArrayList<>();
		tagList.add(tag);
		// 服アイテム
		Clothes clothes = new Clothes();
		clothes.setId(rs.getInt("c_id"));
		clothes.setUserId(rs.getInt("user_id"));
		clothes.setBrandId(rs.getInt("brand_id"));
		clothes.setCategoryId(rs.getInt("category_id"));
		clothes.setColorId(rs.getInt("color_id"));
		clothes.setSizeId(rs.getInt("size_id"));
		clothes.setImagePath(rs.getString("image_path"));
		clothes.setPrice(rs.getInt("price"));
		clothes.setSeason(rs.getString("season"));
		clothes.setPerchaseDate(rs.getDate("perchase_date"));
		clothes.setComment(rs.getString("comment"));
		clothes.setDeleted(rs.getBoolean("deleted"));
		clothes.setTagList(tagList);
			
		return clothes;
	};
	
	
	/**
	 * 登録アイテムをタグ別に分けて表示します.
	 * 
	 * @param userId ユーザID
	 * @param        tagContentsId タグコンテンツID
	 * @return
	 */
	public List<Clothes> findByTag(Integer userId, Integer tagContentsId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT c.id c_id,c.user_id,c.category_id,c.brand_id,c.image_path,c.price,color_id,c.season,c.size_id,c.perchase_date,c.comment,c.deleted,");
		sql.append("t.id t_id,t.clothes_id,tc.id tc_id,tc.name FROM clothes AS c ");
		sql.append("JOIN tags AS t ON c.id=t.clothes_id JOIN tag_contents AS tc ON t.tag_contents_id=tc.id ");
		sql.append("WHERE c.user_id=:userId AND tc.id=:tagContentsId AND deleted =false;");
		System.out.println(sql.toString());
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("tagContentsId",
				tagContentsId);
		List<Clothes> clothesList = template.query(sql.toString(), param, CLOTHES_ROW_MAPPER5);
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
				"ca.id ca_id, ca.name ca_name, b.id b_id, b.name b_name FROM clothes cl LEFT OUTER JOIN categories ca ON cl.category_id = ca.id ");
		sql.append(
				"LEFT OUTER JOIN brands b ON cl.brand_id = b.id WHERE user_id = :userId AND deleted = FALSE ORDER BY cl.id;");

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
		String sql = SQL + "FROM clothes WHERE 1=1 AND user_id=:userId order by id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Clothes> clothesList = template.query(sql, param, CLOTHES_ROW_MAPPER);
		return clothesList.get(0);
	}

	// アイテム詳細表示用のローマッパー.
	private static final RowMapper<Clothes> CLOTHES_ROW_MAPPER3 = (rs, i) -> {
		Clothes clothes = new Clothes();
		clothes.setId(rs.getInt("cl_id"));
		clothes.setUserId(rs.getInt("cl_user_id"));
		clothes.setBrandId(rs.getInt("cl_brand_id"));
		clothes.setCategoryId(rs.getInt("cl_category_id"));
		clothes.setColorId(rs.getInt("cl_color_id"));
		clothes.setSizeId(rs.getInt("cl_size_id"));
		clothes.setImagePath(rs.getString("cl_image_path"));
		clothes.setPrice(rs.getInt("cl_price"));
		clothes.setSeason(rs.getString("cl_season"));
		clothes.setPerchaseDate(rs.getDate("cl_perchase_date"));
		clothes.setComment(rs.getString("cl_comment"));
		// ブランド
		Brand brand = new Brand();
		brand.setId(rs.getInt("b_brand_id"));
		brand.setName(rs.getString("b_brand_name"));
		clothes.setBrand(brand);
		// カテゴリー
		Category category = new Category();
		category.setId(rs.getInt("ca_category_id"));
		category.setName(rs.getString("ca_category_name"));
		clothes.setCategory(category);
		// カラー
		Color color = new Color();
		color.setId(rs.getInt("co_id"));
		color.setName(rs.getString("co_color_name"));
		clothes.setColor(color);
		// サイズ
		Size size = new Size();
		size.setId(rs.getInt("s_id"));
		size.setName(rs.getString("s_size_name"));
		clothes.setSize(size);

		return clothes;
	};

	
	/**
	 * アイテムの1件検索を行います.
	 * 
	 * @param id アイテムID
	 * @param userId ユーザID
	 * @return 1件のアイテム
	 */
	public Clothes findById(Integer id, Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT cl.id cl_id,cl.user_id cl_user_id,ca.name ca_name,b.id b_brand_id,b.name b_brand_name,ca.id ca_category_id,ca.name ca_category_name,"
						+ "cl.image_path cl_image_path,cl.price cl_price,co.id co_id,co.name co_color_name,cl.season cl_season,"
						+ "s.id s_id,s.name s_size_name,cl.perchase_date cl_perchase_date,cl.comment cl_comment,cl.brand_id cl_brand_id,"
						+ "cl.category_id cl_category_id,cl.color_id cl_color_id,cl.size_id cl_size_id ");
		sql.append("FROM clothes cl ");
		sql.append("LEFT JOIN categories ca ON cl.category_id = ca.id ");
		sql.append("LEFT JOIN brands b ON cl.brand_id=b.id ");
		sql.append("LEFT JOIN colors co ON cl.color_id=co.id ");
		sql.append("LEFT JOIN sizes s ON cl.size_id=s.id ");
		sql.append("WHERE cl.id=:id AND cl.user_id=:userId");

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id).addValue("userId", userId);
		Clothes clothes = template.queryForObject(sql.toString(), param, CLOTHES_ROW_MAPPER3);

		return clothes;
	}

	/**
	 * アイテムの更新を行うメソッドです.
	 * 
	 * @param clothes clothesオブジェクト
	 */
	public void update(Clothes clothes) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(clothes);

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE clothes ");
		sql.append(
				"SET image_path=:imagePath, category_id=:categoryId, brand_id=:brandId, color_id=:colorId, season=:season, size_id=:sizeId,");
		sql.append("price=:price, perchase_date=:perchaseDate, comment=:comment ");
		sql.append("WHERE id=:id ");
		sql.append("AND user_id=:userId;");

		template.update(sql.toString(), param);
	}

	/**
	 * アイテムの削除を行う.
	 * 
	 * @param id
	 */
	public void deleteClothes(Integer id) {
		String sql = "UPDATE clothes SET deleted=true WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

	/**
	 * 月毎の購入金額合計、アイテム数、平均金額を検索します.
	 * 
	 * @param userId       ユーザID
	 * @param perchaseDate 購入日
	 * @return データの入ったリスト
	 */
	public List<MonthlyDataDTO> showByPerchaseData(Integer userId, String perchaseDate) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TO_CHAR(perchase_date,'YYYY-MM') as month,");
		sql.append(
				"SUM(price) AS total_price ,COUNT(perchase_date) AS item_quantity,TRUNC(AVG(price),0) AS price_average ");
		sql.append("FROM clothes where user_id=:userId AND deleted='false' ");
		sql.append("GROUP BY month HAVING TO_CHAR(perchase_date,'YYYY-MM')=:perchaseDate ");
		sql.append("ORDER BY month;");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("perchaseDate",
				perchaseDate);
		List<MonthlyDataDTO> clothesPriceList = template.query(sql.toString(), param, DATA_ROW_MAPPER);

		return clothesPriceList;

	}

	/**
	 * カテゴリーID・カテゴリー名・合計金額を詰めるローマッパー
	 */
	private static final RowMapper<CategorySumDto> CATEGORY_SUM_ROW_MAPPER = (rs, i) -> {
		CategorySumDto statsByCategorySum = new CategorySumDto();
		statsByCategorySum.setCategoryId(rs.getInt("cl_category_id"));
		statsByCategorySum.setCategoryName(rs.getString("ca_name"));
		statsByCategorySum.setCategorySum(rs.getInt("sum"));

		return statsByCategorySum;
	};

	/**
	 * ユーザーIDごとにカテゴリーID・カテゴリー名・合計金額を検索します.
	 * 
	 * @param userId ユーザーID
	 * @return
	 */
	public List<CategorySumDto> findAllStatsByCategorySum(Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT cl.category_id cl_category_id, ca.name ca_name, sum(price) sum FROM clothes cl ");
		sql.append(
				"LEFT OUTER JOIN categories ca ON cl.category_id = ca.id WHERE user_id = :userId AND deleted = FALSE GROUP BY cl.category_id, ca.name");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<CategorySumDto> stasByCategorySumList = template.query(sql.toString(), param, CATEGORY_SUM_ROW_MAPPER);

		if (stasByCategorySumList.size() == 0) {
			return Collections.emptyList();
		}

		return stasByCategorySumList;
	}

	/**
	 * ブランドID・ブランド名・合計金額を詰めるローマッパー
	 */
	private static final RowMapper<BrandSumDto> BRAND_SUM_ROW_MAPPER = (rs, i) -> {
		BrandSumDto brandSumDto = new BrandSumDto();
		brandSumDto.setBrandId(rs.getInt("cl_brand_id"));
		brandSumDto.setBrandName(rs.getString("b_name"));
		brandSumDto.setBrandSum(rs.getInt("sum"));

		return brandSumDto;
	};

	/**
	 * 
	 * ユーザーIDごとにブランドID・ブランド名・合計金額を検索します.
	 * 
	 * @param userId ユーザーID
	 * @return
	 */
	public List<BrandSumDto> findAllBrandSum(Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT cl.brand_id cl_brand_id, b.name b_name, sum(price) sum FROM clothes cl ");
		sql.append(
				"LEFT OUTER JOIN brands b ON cl.brand_id = b.id WHERE user_id = :userId AND deleted = FALSE GROUP BY cl.brand_id, b.name");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<BrandSumDto> brandSumList = template.query(sql.toString(), param, BRAND_SUM_ROW_MAPPER);

		if (brandSumList.size() == 0) {
			return Collections.emptyList();
		}

		return brandSumList;
	}

	/**
	 * カテゴリーID・カテゴリー名・カテゴリー別アイテム数を詰めるローマッパー.
	 * 
	 */
	private static final RowMapper<CategoryCountDto> CATEGORY_COUNT_ROW_MAPPER = (rs, i) -> {
		CategoryCountDto categoryCountDto = new CategoryCountDto();
		categoryCountDto.setCategoryId(rs.getInt("cl_category_id"));
		categoryCountDto.setCategoryName(rs.getString("ca_name"));
		categoryCountDto.setCategoryCount(rs.getInt("count"));

		return categoryCountDto;
	};

	/**
	 * 
	 * ユーザーごとのカテゴリー別アイテム数を検索します.
	 * 
	 * @param userId ユーザーID
	 * @return
	 */
	public List<CategoryCountDto> findAllCategoryCount(Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT cl.category_id cl_category_id, ca.name ca_name, count(category_id) count FROM clothes cl ");
		sql.append(
				"LEFT OUTER JOIN categories ca ON cl.category_id = ca.id WHERE user_id = :userId AND deleted = FALSE GROUP BY category_id, ca.name ");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<CategoryCountDto> categoryCountList = template.query(sql.toString(), param, CATEGORY_COUNT_ROW_MAPPER);

		if (categoryCountList.size() == 0) {
			return Collections.emptyList();
		}

		return categoryCountList;
	}

	/**
	 * ユーザーごとのブランドID・ブランド名・アイテム数を詰めるローマッパー.
	 * 
	 */
	private static final RowMapper<BrandCountDto> BRAND_COUNT_ROW_MAPPER = (rs, i) -> {
		BrandCountDto brandCountDto = new BrandCountDto();
		brandCountDto.setBrandId(rs.getInt("cl_brand_id"));
		brandCountDto.setBrandName(rs.getString("b_name"));
		brandCountDto.setBrandCount(rs.getInt("count"));

		return brandCountDto;

	};

	/**
	 * ユーザーごとのブランド別アイテム数を検索します.
	 * 
	 * @param userId ユーザーID
	 * @return
	 */
	public List<BrandCountDto> findAllBrandCount(Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT cl.brand_id cl_brand_id, b.name b_name, count(brand_id) count FROM clothes cl ");
		sql.append(
				"LEFT OUTER JOIN brands b ON cl.brand_id = b.id WHERE user_id = :userId AND deleted = FALSE GROUP BY cl.brand_id, b.name");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<BrandCountDto> brandCountList = template.query(sql.toString(), param, BRAND_COUNT_ROW_MAPPER);

		return brandCountList;
	}

	/**
	 * ユーザーごとのアイテム平均額情報を格納するローマッパー.
	 * 
	 */
	private static final RowMapper<AverageDto> AVERAGE_DTO_ROW_MAPPER = (rs, i) -> {
		AverageDto averageDto = new AverageDto();
		averageDto.setAverage(rs.getInt("average"));
		return averageDto;
	};

	/**
	 * ユーザーごとのアイテム平均金額（アイテムの金額が登録されているもののみ）情報を検索
	 * 
	 * @param userId ユーザーID
	 * @return
	 */
	public AverageDto findAveragePriceByUserId(Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT AVG(price) average FROM clothes WHERE user_id = :userId AND deleted = false AND price IS NOT NULL");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<AverageDto> averageDtoList = template.query(sql.toString(), param, AVERAGE_DTO_ROW_MAPPER);

		if (averageDtoList.size() == 0) {
			return null;
		}

		return averageDtoList.get(0);

	}
}
