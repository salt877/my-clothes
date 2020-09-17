package jp.co.example.my.clothes.form;

import lombok.Getter;
import lombok.Setter;

/**
 * 公開コーディネート切り替え時に使用するフォームクラス.
 * 
 * @author rinashioda
 *
 */
@Getter
@Setter
public class UpdateIsPublicForm {

	/** コーデID */
	private Integer coordinateId;

	/** 公開フラグ */
	private boolean isPublic;

	@Override
	public String toString() {
		return "UpdateIsPublicForm [coordinateId=" + coordinateId + ", isPublic=" + isPublic + "]";
	}

}
