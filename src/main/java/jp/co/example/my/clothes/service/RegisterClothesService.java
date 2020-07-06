package jp.co.example.my.clothes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.my.clothes.domain.Brand;
import jp.co.example.my.clothes.domain.Category;
import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.Color;
import jp.co.example.my.clothes.domain.Size;
import jp.co.example.my.clothes.domain.TagContent;
import jp.co.example.my.clothes.repository.BrandRepository;
import jp.co.example.my.clothes.repository.CategoryRepository;
import jp.co.example.my.clothes.repository.ClothesRepository;
import jp.co.example.my.clothes.repository.ColorRepository;
import jp.co.example.my.clothes.repository.SizeRepository;
import jp.co.example.my.clothes.repository.TagContentRepository;

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

	@Autowired
	private TagContentRepository tagContentRepository;

	@Autowired
	private ClothesRepository clothesRepository;

	// 新規登録画面の表示の際に必要なメソッド.

	/**
	 * 選択肢のカテゴリ一覧を取得
	 * 
	 * @return カテゴリ一覧
	 */
	public List<Category> showCategoryList() {
		List<Category> categoryList = categoryRepository.showCategoryList();
		return categoryList;
	}

	/**
	 * 選択肢のカラー一覧を取得
	 * 
	 * @return
	 */
	public List<Color> showColorList() {
		List<Color> colorList = colorRepository.showColorList();
		return colorList;

	}

	//
	/**
	 * 選択肢のサイズ一覧を取得
	 * 
	 * @return
	 */
	public List<Size> showSizeList() {
		List<Size> sizeList = sizeRepository.AllsizeList();
		return sizeList;
	}

	/**
	 * ブランド検索のオートコンプリート機能を追加するためのメソッド.
	 * 
	 * @param itemList
	 * @return オートコンプリートのための文字列をデータベースから取得した配列
	 */
	public StringBuilder getBrandListForAutoconplete() {
		List<Brand> brandList = brandRepository.AllbrandList();
		StringBuilder brandListForAutocomplete = new StringBuilder();
		for (int i = 0; i < brandList.size(); i++) {
			if (i != 0) {
				brandListForAutocomplete.append(",");
			}
			Brand brand = brandList.get(i);
			brandListForAutocomplete.append("\"");
			brandListForAutocomplete.append(brand.getName());
			brandListForAutocomplete.append("\"");
		}
		return brandListForAutocomplete;
	}

	/**
	 * タグ検索のオートコンプリート機能を追加するためのメソッド.
	 * 
	 * @param itemList
	 * @return オートコンプリートのための文字列をデータベースから取得した配列
	 */
	public StringBuilder getTagContentListForAutoconplete() {
		List<TagContent> tagContentList = tagContentRepository.AllTagContentList();
		StringBuilder tagContentListForAutocomplete = new StringBuilder();
		for (int i = 0; i < tagContentList.size(); i++) {
			if (i != 0) {
				tagContentListForAutocomplete.append(",");
			}
			TagContent tagContent = tagContentList.get(i);
			tagContentListForAutocomplete.append("\"");
			tagContentListForAutocomplete.append(tagContent.getName());
			tagContentListForAutocomplete.append("\"");
		}
		return tagContentListForAutocomplete;
	}

	// 新規アイテム登録時に必要になるメソッド.
	/**
	 * 検索されたブランドの情報を取得.
	 * 
	 * @param name
	 * @return
	 */
	public Brand brandSearchByName(String name) {
		Brand brand = brandRepository.brandSearchByName(name);
		return brand;
	}

	/**
	 * 検索されたブランドの情報を取得.
	 * 
	 * @param name
	 * @return
	 */
	public Category categorySearchById(Integer id) {
		Category category = categoryRepository.categorySearchByCategoryId(id);
		return category;
	}

	/**
	 * 検索されたカラー情報を取得.
	 * 
	 * @param id
	 * @return
	 */
	public Color ColorSearchById(Integer id) {
		Color color = colorRepository.ColorSearchByid(id);
		return color;
	}

	/**
	 * 検索されたサイズの情報を取得
	 * 
	 * @param id
	 * @return
	 */
	public Size sizeSearchById(Integer id) {
		Size size = sizeRepository.SizeSearchByid(id);
		return size;
	}

	/**
	 * 新規でアイテム登録
	 * 
	 * @param clothes
	 */
	public void insertNewClothes(Clothes clothes) {
		clothesRepository.insertClothes(clothes);
	}

}
