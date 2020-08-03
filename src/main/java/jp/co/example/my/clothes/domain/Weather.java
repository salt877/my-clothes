package jp.co.example.my.clothes.domain;

/**
 * 天気予報情報を扱うドメイン.
 * @author ashibe
 *
 */
public class Weather {

	/**
	 * ID
	 */
	private Integer id;

	/**
	 * ユーザーID
	 */
	private Integer userId;

	/**
	 * 都道府県名
	 */
	private String cityName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public String toString() {
		return "Weather [id=" + id + ", userId=" + userId + ", cityName=" + cityName + "]";
	}

}
