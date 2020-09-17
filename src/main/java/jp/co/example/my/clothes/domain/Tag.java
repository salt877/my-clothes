package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * タグ情報を扱うドメインクラス.
 * 
 * @author ashibe
 *
 */
@Getter
@Setter
public class Tag {

	/** タグID */
	private Integer id;

	/** 結び付く服のID */
	private Integer clothesId;

	/** 結び付けるタグ内容のID */
	private Integer tagContentId;

	/** 結び付けられたタグ内容 */
	private TagContent tagContent;

	/** ユーザID */
	private Integer userId;

	@Override
	public String toString() {
		return "Tag [id=" + id + ", clothesId=" + clothesId + ", tagContentId=" + tagContentId + ", tagContent="
				+ tagContent + ", userId=" + userId + "]";
	}

}
