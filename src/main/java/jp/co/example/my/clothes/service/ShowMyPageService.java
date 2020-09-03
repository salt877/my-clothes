package jp.co.example.my.clothes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.my.clothes.domain.UserDetail;
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
	
	/**
	 * マイページを表示させます.
	 * 
	 * @param userId ユーザID
	 * @return　ユーザ詳細情報
	 */
	public UserDetail showMyPage(Integer userId) {
		return userRepository.findUserDetailInformation(userId);
	}

}
