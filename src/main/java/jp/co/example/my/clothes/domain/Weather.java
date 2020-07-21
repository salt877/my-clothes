package jp.co.example.my.clothes.domain;

public class Weather {

	private Integer id;

	private Integer userId;

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
