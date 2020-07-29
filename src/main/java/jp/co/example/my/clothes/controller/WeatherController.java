package jp.co.example.my.clothes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.Weather;
import jp.co.example.my.clothes.form.WeatherForm;
import jp.co.example.my.clothes.service.WeatherService;

/**
 * 天気予報ページを操作するコントローラー.
 * 
 * @author ashibe
 *
 */
@RequestMapping("/weather")
@Controller
public class WeatherController {

	@ModelAttribute
	public WeatherForm setUpWeatherForm() {
		return new WeatherForm();
	}

	@Autowired
	private WeatherService weatherService;

	/**
	 * 天気予報詳細ページに遷移する.
	 * 
	 * @param form
	 * @param model
	 * @param loginUser
	 * @return
	 */
	@RequestMapping("/showWeatherPage")
	public String showWeatherPage(WeatherForm form, Model model, @AuthenticationPrincipal LoginUser loginUser) {

		// 天気予報表示のための街が登録されているか検索
		Weather weather = weatherService.cityFindByUserId(loginUser.getUser().getId());
		if (!StringUtils.isEmpty(weather)) {
			form.setCity(weather.getCityName());
			model.addAttribute("pref", weather.getCityName());
		} else {// 初期表示は東京都
			form.setCity("東京都");
			model.addAttribute("pref", form.getCity());
		}

		return "weather.html";

	}

	/**
	 * 天気予報情報を登録.
	 * 
	 * @param form
	 * @param model
	 * @param loginUser
	 * @return
	 */
	@RequestMapping("/registerCity")
	public String registerPref(WeatherForm form, Model model, @AuthenticationPrincipal LoginUser loginUser) {

		// 天気予報情報が登録されているか確認.
		Weather weather = weatherService.cityFindByUserId(loginUser.getUser().getId());
		// 情報がなかったら新規に登録
		if (StringUtils.isEmpty(weather)) {
			weather = new Weather();
			weather.setUserId(loginUser.getUser().getId());
			weather.setCityName(form.getCity());
			weatherService.insertMyCity(weather);
		} else {
			// 既に情報が登録されていたら更新
			if (!StringUtils.isEmpty(form.getCity())) {
				weather = new Weather();
				weather.setUserId(loginUser.getUser().getId());
				weather.setCityName(form.getCity());
				weatherService.updateCity(weather);
			}
		}

		return showWeatherPage(form, model, loginUser);
	}

}