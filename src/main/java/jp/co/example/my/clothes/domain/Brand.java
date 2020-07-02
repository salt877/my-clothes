package jp.co.example.my.clothes.domain;

/**
 * ブランド情報を扱うドメインクラス.
 * 
 * @author ashibe
 *
 */
public class Brand {

	/**
	 * ブランドID
	 */
	private Integer id;

	/**
	 * ブランド名
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

	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + "]";
	}

}
