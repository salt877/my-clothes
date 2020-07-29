package jp.co.example.my.clothes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.my.clothes.domain.Coordinate;
import jp.co.example.my.clothes.repository.CoordinateRepository;

/**
 * コーディネート登録の業務処理を行う業務処理を行うサービスクラス.
 * 
 * @author masashi.nose
 *
 */
@Service
@Transactional
public class RegisterCoordinateService {

	@Autowired
	public CoordinateRepository coordinateRepository;

	/**
	 * コーディネートを登録します.
	 * 
	 * @param coordinate
	 */
	public void registerCooridnate(Coordinate coordinate) {
		coordinateRepository.insert(coordinate);
	}

}
