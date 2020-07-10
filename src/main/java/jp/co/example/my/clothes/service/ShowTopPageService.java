package jp.co.example.my.clothes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.my.clothes.domain.Brand;
import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.repository.ClothesRepository;

@Service
public class ShowTopPageService {

	@Autowired
	private ClothesRepository clothesRepository;

	/**
	 * 登録アイテムを全件検索します.
	 * 
	 * @param userId ユーザID
	 * @return 登録アイテム一覧
	 */
	public List<Clothes> showItemList(Integer userId) {
		List<Clothes> clothesList = clothesRepository.findAll(userId);
		return clothesList;
	}

	/**
	 * 登録アイテムをカテゴリごとに検索します.
	 * 
	 * @param userId     ログインユーザID
	 * @param categoryId カテゴリID
	 * @return 登録アイテム一覧
	 */
	public List<Clothes> showItemListByCategory(Integer userId, Integer categoryId) {
		List<Clothes> clothesList = clothesRepository.findByCategory(userId, categoryId);
		return clothesList;
	}

	/**
	 * ブランド名を表示します.
	 * 
	 * @param userId ユーザID
	 * @return ブランド名の入ったリスト
	 */
	public List<Brand> showBrandName(Integer userId) {
		List<Brand> brandList = clothesRepository.showBrandName(userId);
		return brandList;
	}

	/**
	 * 登録アイテムをブランドごとに検索します.
	 * 
	 * @param userId ユーザID
	 * @param brandId ブランドID
	 * @return 登録アイテム一覧
	 */
	public List<Clothes> showItemListByBrand(Integer userId, Integer brandId) {
		List<Clothes> clothesList = clothesRepository.findByBrand(userId, brandId);
		return clothesList;
	}

}
