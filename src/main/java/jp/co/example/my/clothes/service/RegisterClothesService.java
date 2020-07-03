package jp.co.example.my.clothes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.my.clothes.domain.Brand;
import jp.co.example.my.clothes.domain.Category;
import jp.co.example.my.clothes.domain.Color;
import jp.co.example.my.clothes.domain.Size;
import jp.co.example.my.clothes.repository.BrandRepository;
import jp.co.example.my.clothes.repository.CategoryRepository;
import jp.co.example.my.clothes.repository.ColorRepository;
import jp.co.example.my.clothes.repository.SizeRepository;

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

	@Autowired
	private SizeRepository sizeRepository;

	@Autowired
	private BrandRepository brandRepository;

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

	public List<Size> showSizeList() {
		List<Size> sizeList = sizeRepository.AllsizeList();
		return sizeList;
	}

	/**
	 * オートコンプリート機能を追加するためのメソッド.
	 * 
	 * @param itemList
	 * @return オートコンプリートのための文字列をデータベースから取得した配列
	 */
	public StringBuilder getItemListForAutoconplete() {
		List<Brand> brandList = brandRepository.AllbrandList();
		StringBuilder itemListForAutocomplete = new StringBuilder();
		for (int i = 0; i < brandList.size(); i++) {
			if (i != 0) {
				itemListForAutocomplete.append(",");
			}
			Brand brand = brandList.get(i);
			itemListForAutocomplete.append("\"");
			itemListForAutocomplete.append(brand.getName());
			itemListForAutocomplete.append("\"");

		}
		return itemListForAutocomplete;
	}

}
