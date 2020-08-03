package jp.co.example.my.clothes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.LoginUser;
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

	/**
	 * ユーザーごとの統計画面を表示します.
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping("/stats")
	public String showStatistics(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		Integer totalItemCount = 0;
		Integer totalItemPrice = 0;
		Integer itemPriceAverage = 0;

		// ユーザーIDに紐づく服データ全件取得
		List<Clothes> clothesListByUserId = showStatisticsService.showStatsByUserId(loginUser.getUser().getId());

		// アイテム登録が１件もない場合、統計画面には何も表示せず、アイテム登録画面へ誘導します。
		if (clothesListByUserId == null) {
			model.addAttribute("clothesListByUserId", clothesListByUserId);
			model.addAttribute("message", "表示するデータがありません。");

			return "statistics";

		}

		// 合計点数
		totalItemCount = clothesListByUserId.size();

		// 合計金額
		for (Clothes clothesByUserId : clothesListByUserId) {
			totalItemPrice += clothesByUserId.getPrice();

		}

		// 平均金額
		itemPriceAverage = totalItemPrice / totalItemCount;

		model.addAttribute("clothesListByUserId", clothesListByUserId);
		model.addAttribute("totalItemCount", totalItemCount);
		model.addAttribute("totalItemPrice", totalItemPrice);
		model.addAttribute("itemPriceAverage", itemPriceAverage);

		return "statistics";
	}

}
