package jp.co.example.my.clothes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.repository.UserRepository;

/**
 * ユーザ名を検索する処理を行うサービスクラスです.
 * 
 * @author rinashioda
 *
 */
@Service
public class ShowUserNameService {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * ログインしているユーザの詳細情報を検索します.
	 * 
	 * @param userId ユーザID
	 * @return ユーザ詳細情報
	 */
	public UserDetail showUserName(Integer userId) {
		return userRepository.findUserDetailInformation(userId);
	}
	
	public UserDetail changeUrlByUser(Integer userId) {
		return userRepository.findByUserId(userId);
	}

}
