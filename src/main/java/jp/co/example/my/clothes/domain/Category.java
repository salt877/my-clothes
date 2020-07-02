package jp.co.example.my.clothes.domain;

/**
 * カテゴリ情報を扱うドメインクラス.
 * 
 * @author ashibe
 *
 */
public class Category {

	/**
	 * カテゴリID
	 */
	private Integer id;

	/**
	 * カテゴリの名前
	 */
	private String name;

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

}
