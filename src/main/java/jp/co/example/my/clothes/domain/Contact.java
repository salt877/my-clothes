package jp.co.example.my.clothes.domain;

/**
 * 問い合わせ情報を扱うドメインクラス.
 * 
 * @author mizuki.tanimori
 *
 */
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
