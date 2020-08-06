package jp.co.example.my.clothes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.my.clothes.domain.AverageDto;
import jp.co.example.my.clothes.domain.BrandCountDto;
import jp.co.example.my.clothes.domain.BrandSumDto;
import jp.co.example.my.clothes.domain.CategoryCountDto;
import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.CategorySumDto;
import jp.co.example.my.clothes.repository.ClothesRepository;

/**
 * 
 * 統計表示をするサービスクラス.
 * 
 * @author masashi.nose
 *
 */
@Service
@Transactional
public class ShowStatisticsService {

	@Autowired
	private ClothesRepository clothesRepository;

	/**
	 * ユーザーIDで服情報を取得.
	 * 
	 * @param userId ユーザーID
	 * @return
	 */
	public List<Clothes> showStatsByUserId(Integer userId) {
		return clothesRepository.findByUserId(userId);

	}

	/**
	 * ユーザーごとのアイテム平均金額（アイテム金額が入力されているもののみ）を表示します.
	 * 
	 * @param userId ユーザーID
	 * @return
	 */
	public AverageDto showAveragePrice(Integer userId) {
		return clothesRepository.findAveragePriceByUserId(userId);
	}

	/**
	 * ユーザーIDでカテゴリーごとの合計金額を表示します.
	 * 
	 * @param userId ユーザーID
	 * @return
	 */
	public List<CategorySumDto> showStatsByCategorySum(Integer userId) {
		return clothesRepository.findAllStatsByCategorySum(userId);
	}

	/**
	 * 
	 * ユーザーIDでカテゴリーごとのアイテム数を表示します.
	 * 
	 * @param userId ユーザーID
	 * @return
	 */
	public List<CategoryCountDto> showStatsByCategoryCount(Integer userId) {
		return clothesRepository.findAllCategoryCount(userId);
	}

	/**
	 * 
	 * ユーザーIDでブランドごとの合計金額を表示します.
	 * 
	 * @param userId ユーザーID
	 * @return
	 */
	public List<BrandSumDto> showStatsByBrandSum(Integer userId) {
		return clothesRepository.findAllBrandSum(userId);
	}

	/**
	 * ユーザーIDでブランドごとのアイテム数を表示します.
	 * 
	 * @param userId ユーザーID
	 * @return
	 */
	public List<BrandCountDto> showStatsByBrandCount(Integer userId) {
		return clothesRepository.findAllBrandCount(userId);
	}

}
