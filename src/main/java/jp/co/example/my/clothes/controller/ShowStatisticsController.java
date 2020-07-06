package jp.co.example.my.clothes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.service.ShowStatisticsService;

/**
 * 
 * 統計表示を行うコントローラ.
 * 
 * @author masashi.nose
 *
 */
@Controller
public class ShowStatisticsController {

	@Autowired
	private ShowStatisticsService showStatisticsService;

	@RequestMapping("/stats")
	public String showStatistics(Integer userId, Integer categoryId, Integer brandId, Model model) {
		Integer totalItemCount = 0;
		Integer totalItemPrice = 0;
		Integer itemPriceAverage = 0;

		List<Clothes> clothesListByUserId = showStatisticsService.showStatsByUserId(userId);

		// ユーザーIDに紐づくアイテムの合計点数
		totalItemCount = clothesListByUserId.size();

		// ユーザーIDに紐づくアイテムの合計金額
		for (Clothes clothesByUserId : clothesListByUserId) {
			totalItemPrice = totalItemPrice + clothesByUserId.getPrice();

		}

		// ユーザーIDに紐づくアイテム平均金額
		itemPriceAverage = totalItemPrice / clothesListByUserId.size();

		return "statistics";

	}
}
