package jp.co.example.my.clothes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.my.clothes.repository.ClothesRepository;

/**
 * 商品を削除する為のメソッド.
 * 
 * @author ashibe
 *
 */
@Service
@Transactional
public class DeleteClothesService {

	@Autowired
	private ClothesRepository clothesRepository;

	public void deleteclothes(Integer id) {
		clothesRepository.deleteClothes(id);
	}

}
