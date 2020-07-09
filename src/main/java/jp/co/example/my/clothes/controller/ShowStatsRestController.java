package jp.co.example.my.clothes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.service.ShowStatisticsService;

/**
 * 統計画面のグラフデータをjson（map）形式で返すRestController.
 * 
 * @author masashi.nose
 *
 */
@RestController
public class ShowStatsRestController {

	@Autowired
	private ShowStatisticsService showStatisticsService;

	/**
	 * カテゴリー別ドーナツグラフの表示を行う.
	 * 
	 * @param userId ユーザーID
	 * @return カテゴリーごとの合計金額リストが詰まったマップ
	 */
	@RequestMapping(value = "/get_category_price_data", method = RequestMethod.GET)
	public Map<String, List<Integer>> getTotalPriceByCategory(Integer userId) {
		Map<String, List<Integer>> categoryPriceMap = new HashMap<>();

		List<List<Clothes>> bigClothesList = showStatisticsService.showStatsByUserIdAndListedByCategoryId(1);
		System.out.println("レストコントローラのリスト:" + bigClothesList);

		List<Integer> priceList = new ArrayList<>();
		int totalPriceByCategory = 0;

		for (List<Clothes> clothesList : bigClothesList) {
			for (Clothes clothes : clothesList) {
				System.out.println("足しあげ前" + totalPriceByCategory);
				totalPriceByCategory += clothes.getPrice();
				System.out.println("足しあげ後" + totalPriceByCategory);
			}

			System.out.println("カテゴリ別合計" + totalPriceByCategory);
			priceList.add(totalPriceByCategory);
			System.out.println(priceList);
			totalPriceByCategory = 0;
		}
		categoryPriceMap.put("categoryPriceList", priceList);

		return categoryPriceMap;
	}

	/**
	 * カテゴリー別ドーナツグラフの表示を行う.
	 * 
	 * @param userId ユーザーID
	 * @return カテゴリーごとの合計アイテム数リストが詰まったマップ
	 */
	@RequestMapping(value = "/get_category_count_data", method = RequestMethod.GET)
	public Map<String, List<Integer>> getTotalCountByCategory(Integer userId) {
		Map<String, List<Integer>> categoryCountMap = new HashMap<>();

		List<List<Clothes>> bigClothesList = showStatisticsService.showStatsByUserIdAndListedByCategoryId(1);
		System.out.println("レストコントローラのリスト:" + bigClothesList);

		List<Integer> countList = new ArrayList<>();
		int totalCountByCategory = 0;

		for (List<Clothes> clothesList : bigClothesList) {
			System.out.println("カテゴリごとの服リスト" + clothesList);
			totalCountByCategory = clothesList.size();
			countList.add(totalCountByCategory);
			totalCountByCategory = 0;

		}

		categoryCountMap.put("categoryCountList", countList);

		return categoryCountMap;

	}

	/**
	 * ブランド別ドーナツグラフの表示を行う.
	 * 
	 * @param userId ユーザーID
	 * @return ブランドごとの合計金額リストが詰まったマップ
	 */
	@RequestMapping(value = "/get_brand_price_data", method = RequestMethod.GET)
	public Map<String, List<Integer>> getTotalPriceByBrand(Integer userId) {
		Map<String, List<Integer>> brandPriceMap = new HashMap<>();

		List<List<Clothes>> bigClothesList = showStatisticsService.showStatsByUserIdAndListedByBrandId(1);
		System.out.println("レストコントローラのリスト:" + bigClothesList);

		List<Integer> priceList = new ArrayList<>();
		int totalPriceByBrand = 0;

		for (List<Clothes> clothesList : bigClothesList) {
			for (Clothes clothes : clothesList) {
				System.out.println("足しあげ前" + totalPriceByBrand);
				totalPriceByBrand += clothes.getPrice();
				System.out.println("足しあげ後" + totalPriceByBrand);
			}

			System.out.println("カテゴリ別合計" + totalPriceByBrand);
			priceList.add(totalPriceByBrand);
			System.out.println(priceList);
			totalPriceByBrand = 0;
		}
		brandPriceMap.put("brandPriceList", priceList);

		return brandPriceMap;
	}

	/**
	 * カテゴリー別ドーナツグラフの表示を行う.
	 * 
	 * @param userId ユーザーID
	 * @return ブランドごとの合計アイテム数リストが詰まったマップ
	 */
	@RequestMapping(value = "/get_brand_count_data", method = RequestMethod.GET)
	public Map<String, List<Integer>> getTotalCountByBrand(Integer userId) {
		Map<String, List<Integer>> brandCountMap = new HashMap<>();

		List<List<Clothes>> bigClothesList = showStatisticsService.showStatsByUserIdAndListedByBrandId(1);
		System.out.println("レストコントローラのリスト:" + bigClothesList);

		List<Integer> countList = new ArrayList<>();
		int totalCountBybrand = 0;

		for (List<Clothes> clothesList : bigClothesList) {
			System.out.println("ブランドごとの服リスト" + clothesList);
			totalCountBybrand = clothesList.size();
			countList.add(totalCountBybrand);
			totalCountBybrand = 0;

		}

		brandCountMap.put("brandCountList", countList);

		return brandCountMap;

	}
}
