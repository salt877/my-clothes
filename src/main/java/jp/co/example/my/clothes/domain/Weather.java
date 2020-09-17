package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 天気予報情報を扱うドメイン.
 * 
 * @author ashibe
 *
 */
@Getter
@Setter
public class Weather {

	/** ID */
	private Integer id;

	/** ユーザーID */
	private Integer userId;

	/** 都道府県名 */
	private String cityName;

	@Override
	public String toString() {
		return "Weather [id=" + id + ", userId=" + userId + ", cityName=" + cityName + "]";
	}

}
