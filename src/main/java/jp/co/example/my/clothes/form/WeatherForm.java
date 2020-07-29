package jp.co.example.my.clothes.form;

public class WeatherForm {

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
