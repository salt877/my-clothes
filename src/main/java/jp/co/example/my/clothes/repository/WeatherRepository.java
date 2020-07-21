package jp.co.example.my.clothes.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.my.clothes.domain.Weather;

@Repository
public class WeatherRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Weather> WEATHER_ROW_MAPPER = new BeanPropertyRowMapper<>(Weather.class);

	/**
	 * 自分の街の情報を登録(初回)
	 * 
	 * @param weather
	 */
	public void insertMyCity(Weather weather) {

		String sql = "INSERT INTO weather (user_id,city_name)VALUES(:userId,:cityName) ";
		SqlParameterSource param = new BeanPropertySqlParameterSource(weather);
		template.update(sql, param);

	}

	/**
	 * 自分の登録した県の情報を取得.(登録していない場合はnullを返す)
	 * 
	 * @param userId
	 * @return
	 */
	public Weather myCityFindByUserId(Integer userId) {
		String sql = "select * from weather WHERE user_id=:userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Weather> weatherList = template.query(sql, param, WEATHER_ROW_MAPPER);
		if (weatherList.size() == 0) {
			return null;
		} else {
			return weatherList.get(0);
		}
	}

	/**
	 * 自分の登録した県情報を更新.
	 * 
	 * @param Weather
	 */
	public void updateCity(Weather Weather) {
		String sql = "UPDATE weather SET city_name = :cityName WHERE user_Id=:userId; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("cityName", Weather.getCityName())
				.addValue("userId", Weather.getUserId());
		template.update(sql, param);
	}
}
