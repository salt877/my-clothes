package jp.co.example.my.clothes.domain;

/**
 * サイズ情報を扱うドメインクラス.
 * 
 * @author ashibe
 *
 */
public class Size {

	/**
	 * サイズID
	 */
	private Integer id;

	/**
	 * サイズの名前
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
		return "Size [id=" + id + ", name=" + name + "]";
	}

}
