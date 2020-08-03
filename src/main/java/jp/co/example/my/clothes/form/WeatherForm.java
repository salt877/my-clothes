package jp.co.example.my.clothes.form;

/**
 * 天気予報情報選択用のフォーム
 * 
 * @author ashibe
 *
 */
public class WeatherForm {

	/**
	 * 都道府県名
	 */
	private String city;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "WeatherForm [city=" + city + "]";
	}

}
