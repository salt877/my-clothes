package jp.co.example.my.clothes.domain;

/**
 * likesテーブル(いいね機能)のカラム情報を持つドメイン.
 * 
 * @author masashi.nose
 *
 */
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCoordinateId() {
		return coordinateId;
	}

	public void setCoordinateId(Integer coordinateId) {
		this.coordinateId = coordinateId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
