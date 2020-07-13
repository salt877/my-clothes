package jp.co.example.my.clothes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.my.clothes.domain.Brand;
import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.Tag;
import jp.co.example.my.clothes.domain.TagContent;
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
	 * タグ名を表示します.
	 * 
	 * @param userId ユーザID
	 * @return タグ名の入ったリスト
	 */
	public List<TagContent> showTagName(Integer userId) {
		List<TagContent> tagNameList = clothesRepository.showTagName(userId);
		return tagNameList;
	}

	/**
	 * 登録アイテムをブランドごとに検索します.
	 * 
	 * @param userId  ユーザID
	 * @param brandId ブランドID
	 * @return 登録アイテム一覧
	 */
	public List<Clothes> showItemListByBrand(Integer userId, Integer brandId) {
		List<Clothes> clothesList = clothesRepository.findByBrand(userId, brandId);
		return clothesList;
	}

	/**
	 * 登録アイテムをタグごとに検索します.
	 * 
	 * @param userId ユーザID
	 * @param        tagContentsId タグ内容ID
	 * @return 登録アイテム一覧
	 */
	public List<Clothes> showItemListByTag(Integer userId, Integer tagContentsId) {
		List<Clothes> clothesList = clothesRepository.findByTag(userId, tagContentsId);
		return clothesList;
	}

}
