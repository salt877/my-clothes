package jp.co.example.my.clothes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.my.clothes.repository.ClothesRepository;

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
	
	
}
