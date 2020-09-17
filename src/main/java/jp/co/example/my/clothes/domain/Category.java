package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * カテゴリ情報を扱うドメインクラス.
 * 
 * @author ashibe
 *
 */
@Getter
@Setter
public class Category {

	/** カテゴリID */
	private Integer id;

	/** カテゴリの名前 */
	private String name;

}
