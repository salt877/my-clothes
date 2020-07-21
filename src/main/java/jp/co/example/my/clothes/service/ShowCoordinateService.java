package jp.co.example.my.clothes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.my.clothes.domain.Coordinate;
import jp.co.example.my.clothes.repository.CoordinateRepository;

/**
 * コーディナート画面を表示する業務処理を行うサービスクラス.
 * 
 * @author masashi.nose
 *
 */
@Service
@Transactional
public class ShowCoordinateService {

	@Autowired
	private CoordinateRepository coordinateRepository;

	/**
	 * ユーザーIDに紐づくコーディネートデータを全件表示します.
	 * 
	 * @param userId ユーザーID
	 * @return コーディネートデータ一覧
	 */
	public List<Coordinate> showCoordinate(Integer userId) {
		return coordinateRepository.findAll(userId);

	}

	/**
	 * コーディネートIDに紐づくコーディネートデータを論理削除します.
	 * 
	 * @param coordinateId
	 */
	public void deleteCoordinate(Integer coordinateId) {
		coordinateRepository.update(coordinateId);
	}

}
