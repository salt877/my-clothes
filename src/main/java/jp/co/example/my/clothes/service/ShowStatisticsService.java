package jp.co.example.my.clothes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.my.clothes.domain.Brand;
import jp.co.example.my.clothes.domain.Category;
import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.repository.BrandRepository;
import jp.co.example.my.clothes.repository.CategoryRepository;
import jp.co.example.my.clothes.repository.ClothesRepository;

/**
 * 
 * 統計表示をするサービスクラス.
 * 
 * @author masashi.nose
 *
 */
@Service
@Transactional
public class ShowStatisticsService {

	@Autowired
	private ClothesRepository clothesRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private BrandRepository brandRepository;

	/**
	 * ユーザーIDで服情報を取得.
	 * 
	 * @param userId
	 * @return
	 */
	public List<Clothes> showStatsByUserId(Integer userId) {
		return clothesRepository.findByUserId(userId);

	}

	/**
	 * ユーザーIDとカテゴリーIDで服情報を取得
	 * 
	 * @param userId     ユーザーID
	 * @param categoryId カテゴリーID
	 * @return
	 */
	public List<Clothes> showStatsByUserIdAndCategoryId(Integer userId, Integer categoryId) {
		return clothesRepository.findByUserIdAndCategoryId(userId, categoryId);

	}

	/**
	 * ユーザーIDとブランドIDで服情報を取得
	 * 
	 * @param userId  ユーザーID
	 * @param brandId ブランドID
	 * @return
	 */
	public List<Clothes> showStatsByUserIdAndBrandId(Integer userId, Integer brandId) {
		return clothesRepository.findByUserIdAndBrandId(userId, brandId);
	}

	/**
	 * IDでカテゴリーを１件取得
	 * 
	 * @param id ID
	 * @return
	 */
	public Category showCategoryById(Integer id) {
		return categoryRepository.categorySearchByCategoryId(id);

	}

	/**
	 * 
	 * IDでブランドを１取得
	 * 
	 * @param id ID
	 * @return
	 */
	public Brand showBrandById(Integer id) {
		return brandRepository.brandSearchById(id);
	}
}
