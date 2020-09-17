package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * ブランド情報を扱うドメインクラス.
 * 
 * @author ashibe
 *
 */
@Getter
@Setter
public class Brand {

	/**
	 * ブランドID
	 */
	private Integer id;

	/**
	 * ブランド名
	 */
	private String name;

	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + "]";
	}

}
