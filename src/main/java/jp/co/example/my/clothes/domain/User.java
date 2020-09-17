package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザ情報を表すドメインクラスです.
 * 
 * @author rinashioda
 *
 */
@Getter
@Setter
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

	@Override
	public String toString() {
		return "User [id=" + id + ", myqloId=" + myqloId + ", email=" + email + ", password=" + password + ", deleted="
				+ deleted + "]";
	}

}