package jp.co.example.my.clothes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.my.clothes.domain.Brand;
import jp.co.example.my.clothes.domain.Category;
import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.Color;
import jp.co.example.my.clothes.domain.Size;
import jp.co.example.my.clothes.domain.Tag;
import jp.co.example.my.clothes.domain.TagContent;
import jp.co.example.my.clothes.repository.BrandRepository;
import jp.co.example.my.clothes.repository.CategoryRepository;
import jp.co.example.my.clothes.repository.ClothesRepository;
import jp.co.example.my.clothes.repository.ColorRepository;
import jp.co.example.my.clothes.repository.SizeRepository;
import jp.co.example.my.clothes.repository.TagContentRepository;
import jp.co.example.my.clothes.repository.TagRepository;

/**
 * アイテム情報の編集を行うサービスクラスです.
 * 
 * @author mizuki.tanimori
 *
 */
@Service
public class EditClothesService {

	@Autowired
	private ClothesRepository clothesRepository;
	
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
	private TagRepository tagRepository;
	
	/**
	 * アイテム情報の編集を行うメソッドです.
	 * 
	 * @param clothes
	 */
	public void editClothes(Clothes clothes) {
		clothesRepository.update(clothes);
	}
	
	/**
	 * 1件のタグ検索を行います.
	 * 
	 * @param userId ユーザID
	 * @param clothesId アイテムID
	 * @param tagContentId タグコンテンツID
	 * @return 1件のタグ
	 */
	public Tag findATag(Integer userId, Integer clothesId, Integer tagContentId) {
		return tagRepository.findById(userId, clothesId, tagContentId);
	}
	
	/**
	 * タグの編集を行うメソッドです.
	 * 
	 * @param tag タグ
	 */
	public void tagUpdate(Tag tag) {
		tagRepository.update(tag);
	}
	
	/**
	 * userIdで新規に追加したclothesオブジェクトの情報を取得します.
	 * 
	 * @param userId
	 * @return
	 */
	public Clothes newClothesSearchByUserId(Integer userId) {
		Clothes clothes = clothesRepository.findNewClothes(userId);
		return clothes;
	}
	
	/**
	 * 検索されたブランドの情報を取得します.
	 * 
	 * @param name
	 * @return
	 */
	public Brand brandSearchByName(String name) {
		Brand brand = brandRepository.brandSearchByName(name);
		return brand;
	}
	
	/**
	 * 検索されたカテゴリーの情報を取得します.
	 * 
	 * @param name
	 * @return
	 */
	public Category categorySearchById(Integer id) {
		Category category = categoryRepository.categorySearchByCategoryId(id);
		return category;
	}

	/**
	 * 検索されたカラー情報を取得します.
	 * 
	 * @param id
	 * @return
	 */
	public Color ColorSearchById(Integer id) {
		Color color = colorRepository.ColorSearchByid(id);
		return color;
	}

	/**
	 * 検索されたサイズの情報を取得します.
	 * 
	 * @param id
	 * @return
	 */
	public Size sizeSearchById(Integer id) {
		Size size = sizeRepository.SizeSearchByid(id);
		return size;
	}
	
	/**
	 * 入力されたタグがすでに登録されているか確認します.
	 * 
	 * @param name
	 * @return
	 */
	public TagContent tagContentSearchByName(String name) {
		TagContent tagContent = tagContentRepository.tagCcntentSearchByName(name);
		return tagContent;

	}

	/**
	 * 新しくタグ内容を登録します.
	 * 
	 * @param tagContent
	 */
	public void insertTagContent(TagContent tagContent) {
		tagContentRepository.insertTagContent(tagContent);
	}

	/**
	 * タグの登録を行います.
	 * 
	 * @param tag
	 */
	public void insertTag(Tag tag) {
		tagRepository.insertTag(tag);

	}

}
