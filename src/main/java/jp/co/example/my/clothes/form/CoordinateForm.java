package jp.co.example.my.clothes.form;

import lombok.Getter;
import lombok.Setter;

/**
 * 公開コーデ画面からコーデ検索パラメータを受け取るフォーム.
 * 
 * @author masashi.nose
 *
 */
@Getter
@Setter
public class CoordinateForm {

	/** コーデ名 */
	private String name;

	/** ジェンダー */
	private String gender;

	@Override
	public String toString() {
		return "CoordinateForm [name=" + name + ", gender=" + gender + "]";
	}

}
