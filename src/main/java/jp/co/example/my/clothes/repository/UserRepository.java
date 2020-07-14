package jp.co.example.my.clothes.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.User;

/**
 * usersテーブルを操作するリポジトリクラス.
 * 
 * @author rinashioda
 *
 */
@Repository
public class UserRepository {

	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		return user;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * ユーザ情報を挿入します.
	 * 
	 * @param user ユーザ情報
	 */
	public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "INSERT INTO users(email,password)VALUES(:email,:password)";
		template.update(sql, param);
	}

	/**
	 * メールアドレスからユーザ情報を取得します.
	 * 
	 * @param email メールアドレス
	 * @return ユーザ情報 存在しない場合はnullを返します
	 */
	public User findByEmail(String email) {
		String sql = "SELECT id,email,password from users where email=:email AND deleted= false";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	/**
	 * メールアドレスを変更する.
	 * 
	 * @param userId ユーザID
	 * @param email  メールアドレス
	 */
	public void updateUserEmail(Integer id, String email) {
		String sql = "UPDATE users SET email=:email WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("id", id);
		template.update(sql, param);
		System.out.println(sql);
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
