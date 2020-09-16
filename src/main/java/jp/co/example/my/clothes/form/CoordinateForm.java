package jp.co.example.my.clothes.form;

/**
 * 公開コーデ画面からコーデ検索パラメータを受け取るフォーム.
 * 
 * @author masashi.nose
 *
 */
public class CoordinateForm {

	/** コーデ名 */
	private String name;
	/** ジェンダー */
	private String gender;

	@Override
	public String toString() {
		return "CoordinateForm [name=" + name + ", gender=" + gender + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
