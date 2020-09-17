package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * カラー情報を扱うドメインクラス.
 * 
 * @author ashibe
 *
 */
@Getter
@Setter
public class Color {

	/** カラーID */
	private Integer id;

	/** カラーの名前 */
	private String name;

	@Override
	public String toString() {
		return "Color [id=" + id + ", name=" + name + "]";
	}

}
