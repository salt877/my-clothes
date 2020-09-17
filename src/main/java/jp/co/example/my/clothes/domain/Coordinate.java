package jp.co.example.my.clothes.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * coorinatesテーブルのカラム情報を持つドメイン.
 * 
 * @author masashi.nose
 *
 */
@Getter
@Setter
public class Coordinate {

	/** コーディネートID */
	private Integer id;

	/** ユーザーID */
	private Integer userId;

	/** ファッション雑貨 */
	private Integer fashionAccessories;

	/** トップス１の服ID */
	private Integer tops1;

	/** トップス２の服ID */
	private Integer tops2;

	/** アウターの服ID */
	private Integer outers;

	/** ボトムスの服ID */
	private Integer bottoms;

	/** シューズの服ID */
	private Integer shoes;

	/** バッグの服ID */
	private Integer bag;

	/** ワンピースの服ID */
	private Integer dress;

	/** 削除フラグ */
	private boolean deleted;

	/** コーディネート名 */
	private String name;

	/** 公開フラグ */
	private boolean isPublic;

	/** ユーザー情報格納用インスタンス */
	private User user;

	/** ユーザー詳細情報 */
	private UserDetail userDetail;

	/** 服情報を詰めるリスト（コーディネート情報） */
	private List<Clothes> clothesList;

	/** コーデのいいね */
	private List<Like> likeList;

	@Override
	public String toString() {
		return "Coordinate [id=" + id + ", userId=" + userId + ", fashionAccessories=" + fashionAccessories + ", tops1="
				+ tops1 + ", tops2=" + tops2 + ", outers=" + outers + ", bottoms=" + bottoms + ", shoes=" + shoes
				+ ", bag=" + bag + ", dress=" + dress + ", deleted=" + deleted + ", name=" + name + ", isPublic="
				+ isPublic + ", user=" + user + ", userDetail=" + userDetail + ", clothesList=" + clothesList
				+ ", likeList=" + likeList + "]";
	}

}
