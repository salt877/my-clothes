package jp.co.example.my.clothes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.my.clothes.domain.Category;
import jp.co.example.my.clothes.domain.Color;
import jp.co.example.my.clothes.repository.CategoryRepository;
import jp.co.example.my.clothes.repository.ColorRepository;

/**
 * アイテムを登録するためのサービス.
 * 
 * @author ashibe
 *
 */
@Service
@Transactional
public class RegisterClothesService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ColorRepository colorRepository;

	/**
	 * 選択肢のカテゴリ一覧を取得
	 * 
	 * @return カテゴリ一覧
	 */
	public List<Category> showCategoryList() {
		List<Category> categoryList = categoryRepository.showCategoryList();
		return categoryList;
	}

	public List<Color> showColorList() {
		List<Color> colorList = colorRepository.showColorList();
		return colorList;

	}

}
