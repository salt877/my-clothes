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

}
