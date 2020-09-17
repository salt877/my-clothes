package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * likesテーブル(いいね機能)のカラム情報を持つドメイン.
 * 
 * @author masashi.nose
 *
 */
@Getter
@Setter
public class Like {

	/** ID */
	private Integer id;

	/** コーデID */
	private Integer coordinateId;

	/** ユーザーID */
	private Integer userId;

	@Override
	public String toString() {
		return "Like [id=" + id + ", coordinateId=" + coordinateId + ", userId=" + userId + "]";
	}

}
