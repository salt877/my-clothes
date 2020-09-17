package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 問い合わせ情報を扱うドメインクラス.
 * 
 * @author mizuki.tanimori
 *
 */
@Getter
@Setter
public class Contact {

	/** ID */
	private Integer id;

	/** 名前 */
	private String name;

	/** メールアドレス */
	private String email;

	/** 問い合わせ内容 */
	private String content;

	@Override
	public String toString() {
		return "Contact [id=" + id + ", name=" + name + ", email=" + email + ", content=" + content + "]";
	}

}
