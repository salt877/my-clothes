package jp.co.example.my.clothes.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

	/**
	 * ユーザーごとの統計画面を表示します.
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping("/stats")
	public String showStatistics(Integer userId, Model model) {
		Integer totalItemCount = 0;
		Integer totalItemPrice = 0;
		Integer itemPriceAverage = 0;

		// ユーザーIDに紐づく服データ全件取得
		List<Clothes> clothesListByUserId = showStatisticsService.showStatsByUserId(1);

		// 合計点数
		totalItemCount = clothesListByUserId.size();

		// 合計金額
		for (Clothes clothesByUserId : clothesListByUserId) {
			totalItemPrice += clothesByUserId.getPrice();

		}

		// 平均金額
		itemPriceAverage = totalItemPrice / clothesListByUserId.size();

		// グラフ用
		//clothesオブジェクトからカテゴリー・ブランド名を入れるリスト
		List<String> categoryNameList = new ArrayList<>();
		List<String> brandNameList = new ArrayList<>();

		//重複削除したカテゴリー・ブランド名を入れるリスト
		List<String> categoryNameSimpleList = new ArrayList<>();
		List<String> brandNameSimpleList = new ArrayList<>();

		// カテゴリー・ブランドをclothesオブジェクトから抽出し、それぞれの名前をリスト化、重複を削除
		for (Clothes clothes : clothesListByUserId) {
			categoryNameList.add(clothes.getCategory().getName());
			categoryNameSimpleList = categoryNameList.stream().distinct().collect(Collectors.toList());

			brandNameList.add(clothes.getBrand().getName());
			brandNameSimpleList = brandNameList.stream().distinct().collect(Collectors.toList());
		}

		// Chart.js用にリストを配列化
		String categoryArray[] = categoryNameSimpleList.toArray(new String[categoryNameSimpleList.size()]);
		String brandArray[] = brandNameSimpleList.toArray(new String[brandNameSimpleList.size()]);

		model.addAttribute("totalItemCount", totalItemCount);
		model.addAttribute("totalItemPrice", totalItemPrice);
		model.addAttribute("itemPriceAverage", itemPriceAverage);
		model.addAttribute("categoryLabel", categoryArray);
		model.addAttribute("brandLabel", brandArray);

		return "statistics";

	}
}
