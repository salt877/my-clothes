package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * サイズ情報を扱うドメインクラス.
 * 
 * @author ashibe
 *
 */
@Getter
@Setter
public class Size {

	/** サイズID */
	private Integer id;

	/** サイズの名前 */
	private String name;

	@Override
	public String toString() {
		return "Size [id=" + id + ", name=" + name + "]";
	}

}
