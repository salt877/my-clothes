package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * タグ内容情報を扱うドメインクラス.
 * 
 * @author ashibe
 *
 */
@Getter
@Setter
public class TagContent {

	/** タグ内容のID */
	private Integer id;

	/** タグ内容 */
	private String name;

	/** ユーザID */
	private Integer userId;

	@Override
	public String toString() {
		return "TagContent [id=" + id + ", name=" + name + ", userId=" + userId + "]";
	}

}
