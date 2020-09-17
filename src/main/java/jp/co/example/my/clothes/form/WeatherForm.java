package jp.co.example.my.clothes.form;

import lombok.Getter;
import lombok.Setter;

/**
 * 天気予報情報選択用のフォームクラスです.
 * 
 * @author ashibe
 *
 */
@Getter
@Setter
public class WeatherForm {

	/** 都道府県名 */
	private String city;

	@Override
	public String toString() {
		return "WeatherForm [city=" + city + "]";
	}

}
