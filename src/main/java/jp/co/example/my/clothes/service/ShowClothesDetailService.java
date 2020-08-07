package jp.co.example.my.clothes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.Tag;
import jp.co.example.my.clothes.repository.ClothesRepository;
import jp.co.example.my.clothes.repository.TagRepository;

@Service
public class ShowClothesDetailService {

	@Autowired
	private ClothesRepository clothesRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	
	/**
	 * アイテムの詳細表示を行うメソッドです.
	 * 
	 * @param id アイテムID
	 * @param userId ユーザID
	 * @return 1件のアイテム
	 */
	public Clothes showClothesDetail(Integer id, Integer userId) {
		return clothesRepository.findById(id, userId);
	}
	
	/**
	 * アイテムIDでタグ一覧を取得します.
	 * 
	 * @param id アイテムID
	 * @return タグ一覧
	 */
	public List<Tag> showTagList(Integer id){
		return tagRepository.findById(id);
	}
}
