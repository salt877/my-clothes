package jp.co.example.my.clothes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.my.clothes.domain.Coordinate;
import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.repository.CoordinateRepository;
import jp.co.example.my.clothes.repository.UserRepository;

/**
 * マイページを表示する処理を行うサービスクラス.
 * 
 * @author rinashioda
 *
 */
@Service
public class ShowMyPageService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CoordinateRepository coordinateRepository;
	
	/**
	 * マイページを表示させます.
	 * 
	 * @param userId ユーザID
	 * @return　ユーザ詳細情報
	 */
	public UserDetail showMyPage(Integer userId) {
		return userRepository.findByUserId(userId);
	}
	
	/**
	 * ユーザが存在するか調べます.
	 * 
	 * @param myqloId
	 * @return 存在しない場合はnullを返します
	 */
	public User searchUserByMyqloId(String myqloId) {
		return userRepository.findUserByMyqloId(myqloId);
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

}
