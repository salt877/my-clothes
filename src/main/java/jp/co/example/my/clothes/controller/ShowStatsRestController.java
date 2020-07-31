package jp.co.example.my.clothes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.BrandCountDto;
import jp.co.example.my.clothes.domain.BrandSumDto;
import jp.co.example.my.clothes.domain.CategoryCountDto;
import jp.co.example.my.clothes.domain.CategorySumDto;
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
	 * ユーザーごとのカテゴリー別金額情報をJSON形式でリターン.
	 * 
	 * @param loginUser
	 * @return
	 */
	@RequestMapping(value = "/get_category_price_data", method = RequestMethod.GET)
	public Map<String, List<CategorySumDto>> showTotalPriceByCategory(@AuthenticationPrincipal LoginUser loginUser) {
		Map<String, List<CategorySumDto>> categoryPriceMap = new HashMap<>();

		List<CategorySumDto> categorySumList = showStatisticsService
				.showStatsByCategorySum(loginUser.getUser().getId());

		categoryPriceMap.put("statsByCategorySumList", categorySumList);

		return categoryPriceMap;

	}

	/**
	 * ユーザーごとのカテゴリー別アイテム数情報をJSON形式でリターン.
	 * 
	 * @param loginUser
	 * @return
	 */
	@RequestMapping(value = "/get_category_count_data", method = RequestMethod.GET)
	public Map<String, List<CategoryCountDto>> showCountByCategory(@AuthenticationPrincipal LoginUser loginUser) {
		Map<String, List<CategoryCountDto>> categoryCountMap = new HashMap<>();

		List<CategoryCountDto> categoryCountList = showStatisticsService
				.showStatsByCategoryCount(loginUser.getUser().getId());

		categoryCountMap.put("categoryCountList", categoryCountList);

		return categoryCountMap;

	}

	/**
	 * ユーザーごとのブランド別合計金額情報をJSON形式でリターン.
	 * 
	 * @param loginUser
	 * @return
	 */
	@RequestMapping(value = "/get_brand_price_data", method = RequestMethod.GET)
	public Map<String, List<BrandSumDto>> showTotalPriceByBrand(@AuthenticationPrincipal LoginUser loginUser) {
		Map<String, List<BrandSumDto>> brandSumMap = new HashMap<>();

		List<BrandSumDto> brandSumList = showStatisticsService.showStatsByBrandSum(loginUser.getUser().getId());

		brandSumMap.put("brandSumList", brandSumList);

		return brandSumMap;
	}

	/**
	 * ユーザーごとのブランド別アイテム数をJSON形式でリターン.
	 * 
	 * @param loginUser
	 * @return
	 */
	@RequestMapping(value = "/get_brand_count_data", method = RequestMethod.GET)
	public Map<String, List<BrandCountDto>> showCountByBrand(@AuthenticationPrincipal LoginUser loginUser) {
		Map<String, List<BrandCountDto>> brandCountMap = new HashMap<>();

		List<BrandCountDto> brandCountList = showStatisticsService.showStatsByBrandCount(loginUser.getUser().getId());

		brandCountMap.put("brandCountList", brandCountList);

		return brandCountMap;
	}
}
