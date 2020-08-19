package jp.co.example.my.clothes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.MonthlyDataDTO;
import jp.co.example.my.clothes.repository.ClothesRepository;

/**
 * カレンダー表示機能を処理するサービスクラスです.
 * 
 * @author rinashioda
 *
 */
@Service
public class ShowCalendarService {

	@Autowired
	private ClothesRepository clothesRepository;

	/**
	 * 月毎の購入情報を取得します.
	 * 
	 * @param userId ユーザID
	 * @param        perchaseDate 購入年月
	 * @return 購入情報のリスト
	 */
	public List<MonthlyDataDTO> showPriceData(Integer userId, String perchaseDate) {
		List<MonthlyDataDTO> clothesPriceList = clothesRepository.showByPerchaseData(userId, perchaseDate);
		return clothesPriceList;
	}

	/**
	 * 全てのアイテム登録情報を取得します.
	 * 
	 * @param userId ユーザID @return アイテム情報のリスト
	 */
	public List<Clothes> showDailyPerchaseData(Integer userId) {
		List<Clothes> dailyPerchaseDataList = clothesRepository.findAllWithCategory(userId);

		return dailyPerchaseDataList;
	}

}
