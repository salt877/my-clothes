package jp.co.example.my.clothes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.repository.UserRepository;

/**
 * ユーザ詳細情報を登録する際に処理を行うサービスクラス.
 * 
 * @author rinashioda
 *
 */
@Service
public class RegisterUserDetailService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * MYQLO IDを含めたプロフィール画面を表示する.
	 * 
	 * @param userId ユーザID
	 * @return ユーザ詳細情報
	 */
	public UserDetail showProfileEdit(Integer userId) {
		return userRepository.findByUserId(userId);
	}

	/**
	 * すでに詳細情報が登録されているか調べる.
	 * 
	 * @param userId ユーザID
	 * @return ユーザ詳細情報
	 */
	public UserDetail searchUserDetail(Integer userId) {
		return userRepository.findUserDetailInformation(userId);
	}

	/**
	 * ユーザ詳細情報をインサートする（初回登録）.
	 * 
	 * @param userDetail ユーザ詳細情報
	 */
	public void registerUserName(UserDetail userDetail) {
		userRepository.insertUserName(userDetail);
	}

	/**
	 * ユーザ詳細情報を更新する（2回目以降）.
	 * 
	 * @param userDetail
	 */
	public void editUserDetail(UserDetail userDetail) {
		userRepository.updateUserDetail(userDetail);
	}

	/**
	 * MYQLO IDを取得します.
	 * 
	 * @param userId ユーザID
	 * @return ユーザ情報
	 */
	public User searchMyqloIdByUserId(Integer userId) {
		return userRepository.findMyqloId(userId);
	}
	
	public User searchMyqloId(String myqloId) {
		return userRepository.findUserByMyqloId(myqloId);
	}

}
