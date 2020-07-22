package jp.co.example.my.clothes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.repository.ClothesRepository;

@Service
public class ShowClothesDetailService {

	@Autowired
	private ClothesRepository clothesRepository;
	
	/**
	 * アイテムIDで詳細情報を取得します.
	 * 
	 * @param id アイテムID
	 * @return 1件のアイテム情報
	 */
	public Clothes showClothesDetail(Integer id) {
		return clothesRepository.findById(id);
	}
}
