package jp.co.example.my.clothes.domain;

/**
 * カラー情報を扱うドメインクラス.
 * 
 * @author ashibe
 *
 */
public class Color {

	/**
	 * カラーID
	 */
	private Integer id;

	/**
	 * カラーの名前
	 */
	private String name;

	@Override
	public String toString() {
		return "Color [id=" + id + ", name=" + name + "]";
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

}
