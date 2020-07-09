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

	/**
	 * ユーザーIDで服情報を取得.
	 * 
	 * @param userId
	 * @return
	 */
	public List<Clothes> showStatsByUserId(Integer userId) {
		return clothesRepository.findByUserId(userId);

	}

	public List<Clothes> showStatsCategoryLabel(Integer userId) {
		return clothesRepository.findByUserIdOrderByCategoryId(userId);
	}

	public List<Clothes> showStatsBrandLabel(Integer userId) {
		return clothesRepository.findByUserIdOrderByBrandId(userId);
	}

	/**
	 * ユーザーIDで服情報を取得（カテゴリーごとにリスト化）.
	 * 
	 * @param userId
	 * @return
	 */
	public List<List<Clothes>> showStatsByUserIdAndListedByCategoryId(Integer userId) {
		return clothesRepository.findByUserIdAndListedByCategoryId(userId);
	}

	/**
	 * ユーザーIDで服情報を取得（ブランドごとにリスト化）.
	 * 
	 * @param userId
	 * @return
	 */
	public List<List<Clothes>> showStatsByUserIdAndListedByBrandId(Integer userId) {
		return clothesRepository.findByUserIdAndListedByBrandId(userId);
	}

}
