package jp.co.example.my.clothes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
