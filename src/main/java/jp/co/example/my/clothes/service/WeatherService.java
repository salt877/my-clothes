package jp.co.example.my.clothes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.my.clothes.domain.Weather;
import jp.co.example.my.clothes.repository.WeatherRepository;

/**
 * 天気予報の情報を取り扱うサービスクラス.
 * 
 * @author ashibe
 *
 */
@Service
@Transactional
public class WeatherService {

	@Autowired
	private WeatherRepository weatherRepository;

	/**
	 * 天気予報情報を登録(初回)
	 * 
	 * @param weather
	 */
	public void insertMyCity(Weather weather) {
		weatherRepository.insertMyCity(weather);
	}

	/**
	 * 天気予報情報を更新（二回目以降）
	 * 
	 * @param weather
	 */
	public void updateCity(Weather weather) {
		weatherRepository.updateCity(weather);
	}

	/**
	 * ユーザーが登録している県情報を取得.
	 * 
	 * @param userId
	 * @return
	 */
	public Weather cityFindByUserId(Integer userId) {
		Weather weather = weatherRepository.myCityFindByUserId(userId);
		return weather;
	}

}
