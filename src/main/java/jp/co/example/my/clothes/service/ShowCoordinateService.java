package jp.co.example.my.clothes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.my.clothes.domain.Coordinate;
import jp.co.example.my.clothes.domain.Like;
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
	 * 公開コーディネート情報を表示します.
	 * 
	 * @return 公開コーディネートデータ一覧
	 */
	public List<Coordinate> showPublicCoordinate() {
		return coordinateRepository.findByIsPublic();
	}

	/**
	 * コーディネートIDに紐づくコーディネートデータを論理削除します.
	 * 
	 * @param coordinateId コーデID
	 */
	public void deleteCoordinate(Integer coordinateId) {
		coordinateRepository.update(coordinateId);
	}

	/**
	 * コーディネートIDで１件検索結果を表示します.
	 * 
	 * @param coordinateId コーデID
	 * @return
	 */
	public Coordinate showCoordinateDetail(Integer coordinateId) {
		return coordinateRepository.load(coordinateId);
	}

	/**
	 * コーデにいいねします.
	 * 
	 * @param like
	 */
	public void like(Like like) {
		coordinateRepository.insert(like);
	}

	/**
	 * いいねを削除します
	 * 
	 * @param userId
	 */
	public void deleteLike(Integer userId) {
		coordinateRepository.delete(userId);
	}

	/**
	 * コーデIDに紐づくいいねを表示します.
	 * 
	 * @param coordinateId
	 * @return
	 */
	public List<Like> showLikes(Integer coordinateId) {
		return coordinateRepository.findLikes(coordinateId);
	}

}
