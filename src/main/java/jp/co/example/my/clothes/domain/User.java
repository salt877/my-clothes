package jp.co.example.my.clothes.domain;

/**
 * ユーザ情報を表すドメインクラスです.
 * 
 * @author rinashioda
 *
 */
public class User {

	/** ユーザID */
	private Integer id;

	/** MYQLO ID */
	private String myqloId;

	/** メールアドレス */
	private String email;

	/** パスワード */
	private String password;

	/** 削除フラグ */
	private Boolean deleted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMyqloId() {
		return myqloId;
	}

	public void setMyqloId(String myqloId) {
		this.myqloId = myqloId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", myqloId=" + myqloId + ", email=" + email + ", password=" + password + ", deleted="
				+ deleted + "]";
	}

}