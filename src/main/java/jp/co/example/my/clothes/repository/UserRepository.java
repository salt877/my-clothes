package jp.co.example.my.clothes.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.PasswordReset;
import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.domain.UserDetail;

/**
 * usersテーブルを操作するリポジトリクラス.
 * 
 * @author rinashioda
 *
 */
@Repository
public class UserRepository {

	private static final RowMapper<User> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);

	private static final RowMapper<UserDetail> USER_DETAIL_ROW_MAPPER = new BeanPropertyRowMapper<>(UserDetail.class);

	private static final RowMapper<PasswordReset> PASSWORD_RESET_ROWMAPPER = new BeanPropertyRowMapper<>(
			PasswordReset.class);

	@Autowired
	private NamedParameterJdbcTemplate template;

	private SimpleJdbcInsert insert;

	/**
	 * 自動採番確認用メソッド.
	 */
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		SimpleJdbcInsert withtableName = simpleJdbcInsert.withTableName("users");
		insert = withtableName.usingGeneratedKeyColumns("id");

	}

	/**
	 * ユーザ情報を挿入し、自動採番されたユーザIDを取得します.
	 * 
	 * @param user ユーザ情報
	 * @return ユーザ情報
	 */
	public User save(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);

		if (user.getId() == null) {

			user.setDeleted(false);
			Number key = insert.executeAndReturnKey(param);
			user.setId(key.intValue());
			System.out.println(key + "が割り当てられました");
		}
		return user;
	}

	/**
	 * ユーザIDから、ユーザ情報をユーザ詳細テーブルと結合して取得します.
	 * 
	 * @return ユーザ情報
	 */
	public UserDetail findByUserId(Integer userId) {
		String sql = "SELECT ud.user_id,u.myqlo_id, ud.image_path, ud.user_name, ud.gender, ud.age, ud.height, ud.self_introduction FROM users AS u "
				+ "LEFT OUTER JOIN user_details AS ud ON u.id = ud.user_id " + "WHERE u.id=:userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<UserDetail> userList = template.query(sql, param, USER_DETAIL_ROW_MAPPER);
		if (userList.size() == 0) {
			System.out.println("ユーザリストサイズ:" + userList.size());
			return null;
		}

		return userList.get(0);
	}

	/**
	 * 
	 * マイクロIDから詳細情報を１件検索します.
	 * 
	 * @param myqloId マイクロID
	 * @return ユーザ情報
	 */
	public UserDetail findByMyqloId(String myqloId) {
		String sql = "SELECT ud.user_id,u.myqlo_id, ud.image_path, ud.user_name, ud.gender, ud.age, ud.height, ud.self_introduction FROM users AS u "
				+ "LEFT OUTER JOIN user_details AS ud ON u.id = ud.user_id " + "WHERE u.myqlo_id=:myqloId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("myqloId", myqloId);
		List<UserDetail> userList = template.query(sql, param, USER_DETAIL_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}

		return userList.get(0);

	}

	/**
	 * MYQLO IDを検索し、結果を取得します.
	 * 
	 * @param myqloId MYQLO ID
	 * @return ユーザ情報
	 */
	public User findMyqloId(Integer userId) {
		String sql = "SELECT id,myqlo_id FROM users WHERE id=:id AND deleted=false";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", userId);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	/**
	 * ユーザID一つにつき、詳細テーブル列は一つなので列が存在するか調べます.
	 * 
	 * @param userId
	 * @return
	 */
	public UserDetail findUserDetailInformation(Integer userId) {
		String sql = "SELECT id,user_id,user_name,image_path,gender,height,age,self_introduction FROM user_details WHERE user_id=:userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		System.out.println("findUserDetailInformationメソッド:" + userId);
		List<UserDetail> userDetailList = template.query(sql, param, USER_DETAIL_ROW_MAPPER);
		if (userDetailList.size() == 0) {
			System.out.println("まだ詳細情報を登録していない");
			return null;
		}
		System.out.println("詳細情報を登録している");
		return userDetailList.get(0);
	}

	/**
	 * MYQLO IDを使ってユーザが存在するかどうか調べます.
	 * 
	 * @param myqloId
	 * @return
	 */
	public User findUserByMyqloId(String myqloId) {
		String sql = "SELECT id,myqlo_id FROM users WHERE myqlo_id=:myqloId AND deleted='false'";
		SqlParameterSource param = new MapSqlParameterSource().addValue("myqloId", myqloId);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	/**
	 * user_detailsテーブルにインサート.
	 * 
	 * @param user
	 */
	public void insertUserDetail(UserDetail userDetail) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(userDetail);
		String sql = "INSERT INTO user_details(user_id,image_path,user_name,gender,height,age,self_introduction)VALUES(:userId,:imagePath,:userName,:gender,:height,:age,:selfIntroduction);";
		template.update(sql, param);
	}

	/**
	 * user_detailsテーブルを更新.
	 * 
	 * @param user
	 */
	public void updateUserDetail(UserDetail userDetail) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(userDetail);
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE user_details SET ");
		sql.append("image_path=:imagePath, user_name=:userName, gender=:gender, height=:height, ");
		sql.append("age=:age, self_introduction=:selfIntroduction ");
		sql.append("WHERE id=:id");
		template.update(sql.toString(), param);
	}

	/**
	 * ユーザ情報を挿入します.
	 * 
	 * @param user ユーザ情報
	 */
	public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "INSERT INTO users(myqlo_id,email,password)VALUES(:myqloId,:email,:password)";
		template.update(sql, param);
	}

	/**
	 * メールアドレスからユーザ情報を取得します.
	 * 
	 * @param email メールアドレス
	 * @return ユーザ情報 存在しない場合はnullを返します
	 */
	public User findByEmail(String email) {
		String sql = "SELECT id,email,password,myqlo_id from users where email=:email AND deleted= false";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	/**
	 * メールアドレスを変更します.
	 * 
	 * @param userId ユーザID
	 * @param email  メールアドレス
	 */
	public void updateUserEmail(Integer id, String email) {
		String sql = "UPDATE users SET email=:email WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("id", id);
		template.update(sql, param);
	}

	/**
	 * ランダムURLからユーザIDを検索します.
	 * 
	 * @param token
	 * @return
	 */
	public PasswordReset findUserByUrl(String randomUrl) {
		String sql = "SELECT id,user_id,random_url,expire_date FROM password_reset WHERE random_url=:randomUrl";
		SqlParameterSource param = new MapSqlParameterSource().addValue("randomUrl", randomUrl);
		List<PasswordReset> changeList = template.query(sql, param, PASSWORD_RESET_ROWMAPPER);
		if (changeList.size() == 0) {
			return null;
		}
		return changeList.get(0);
	}

	/**
	 * ユーザIDからユーザを検索します.
	 * 
	 * @param userId ユーザID
	 * @return ユーザ情報
	 */
	public User findUserByUserId(Integer userId) {
		String sql = "SELECT id,email,password FROM users WHERE id=:id AND deleted='false'";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", userId);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	/**
	 * パスワードを変更します.
	 * 
	 * @param user ユーザ情報
	 */
	public void updateUserPassword(Integer userId, String password) {
		String sql = "UPDATE users SET password=:password WHERE id=:id AND deleted='false'";
		SqlParameterSource param = new MapSqlParameterSource().addValue("password", password).addValue("id", userId);
		template.update(sql, param);
	}

	/**
	 * ユーザー情報を論理削除（データを消すわけではなく、使えなくする）
	 * 
	 * @param user
	 */
	public void deleteUser(LoginUser user) {
		String sql = "UPDATE users SET deleted='true' where email=:email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", user.getUser().getEmail());
		template.update(sql, param);
	}

}
