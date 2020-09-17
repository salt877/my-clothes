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
	 * コーデ名のオートコンプリート機能.
	 * 
	 * @return コーデ名の配列データ
	 */
	public StringBuilder getCoordinateListForAutoComplete() {
		List<Coordinate> coordinateList = coordinateRepository.findAll();
		StringBuilder coordinateListForAutoComplete = new StringBuilder();
		
		for(int i = 0; i < coordinateList.size(); i++) {
			if(i != 0) {
				coordinateListForAutoComplete.append(",");
			}
			
			Coordinate coordinate = coordinateList.get(i);
			coordinateListForAutoComplete.append("\"");
			coordinateListForAutoComplete.append(coordinate.getName());
			coordinateListForAutoComplete.append("\"");
		}
		
		return coordinateListForAutoComplete;
	
	}

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
	 * ユーザーIDで公開コーディネート情報を取得します.
	 * 
	 * @param userId ユーザーID
	 * @return
	 */
	public List<Coordinate> showPublicCoordinateByUserId(Integer userId) {
		return coordinateRepository.findByIsPublicAndUserId(userId);
	}

	/**
	 * コーデ名でコーデ検索します.
	 * 
	 * @param name コーデ名
	 * @return
	 */
	public List<Coordinate> showCoordinateByName(String name) {
		return coordinateRepository.findCoordinatebyName(name);
	}

	/**
	 * コーデ名とジェンダーでコーデ検索します.
	 * 
	 * @param name   コーデ名
	 * @param gender ジェンダー
	 * @return
	 */
	public List<Coordinate> showCoordinateByNameAndGender(String name, String gender) {
		return coordinateRepository.findCoordinatebyNameAndgender(name, gender);
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
	 * コーディネートIDで１件検索結果を過去コーデに表示します.
	 * 
	 * @param coordinateId コーデID
	 * @return
	 */
	public Coordinate showCoordinateDetailForPastCoordinate(Integer coordinateId) {
		return coordinateRepository.loadForPastCoordinate(coordinateId);
	}

	/**
	 * コーディネートIDで１件検索結果を公開コーデに表示します.
	 * 
	 * @param coordinateId コーデID
	 * @return
	 */
	public Coordinate showCoordinateDetailForPublicCoordinate(Integer coordinateId) {
		return coordinateRepository.loadForPublicCoordinate(coordinateId);
	}

	/**
	 * マイクロIDでコーディネートを検索します.
	 * 
	 * @param myqloId マイクロID
	 * @return
	 */
	public List<Coordinate> showCoordinateByMyqloId(String myqloId) {
		return coordinateRepository.coordinateListByMyqloId(myqloId);
	}

	/**
	 * 公開コーデ情報を更新します.
	 * 
	 * @param coordinateId コーデID
	 * @param isPublic     公開フラグ
	 */
	public void updateIsPublic(Integer coordinateId, boolean isPublic) {
		coordinateRepository.updateIsPublic(coordinateId, isPublic);
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
	 * 
	 * ユーザーIDでいいね検索します .
	 * 
	 * @param userId
	 * @return
	 */
	public List<Like> likeListByuserId(Integer userId) {
		return coordinateRepository.likeListByUserId(userId);
	}

	/**
	 * コーデIDとユーザーIDに紐づくいいねを削除します.
	 * 
	 * @param userId
	 */
	public void deleteLike(Integer coordinateId, Integer userId) {
		coordinateRepository.delete(coordinateId, userId);
	}

	/**
	 * コーデIDに紐づくいいねを表示します.
	 * 
	 * @param coordinateId コーデID
	 * @return
	 */
	public List<Like> showLikes(Integer coordinateId) {
		return coordinateRepository.findLikes(coordinateId);
	}

	public Like confirmLike(Integer coordinateId, Integer userId) {
		return coordinateRepository.load(coordinateId, userId);
	}

	/**
	 * 自分がいいねしたコーディネートリストを表示します.
	 * 
	 * @param userId ユーザID
	 * @return いいねしたコーディネートリスト
	 */
	public List<Coordinate> showLikeCoordinate(Integer userId) {
		return coordinateRepository.findBylike(userId);
	}

	/**
	 * 自分にいいねされたコーディネートリストを表示します.
	 * 
	 * @param userId ユーザID
	 * @return いいねされたコーディネートリスト
	 */
	public List<Coordinate> showLikedCoordinate(Integer userId) {
		return coordinateRepository.findByliked(userId);
	}

}
