package jp.co.example.my.clothes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.repository.UserRepository;

@Service
@Transactional
public class ChangeUserEmailService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * メールアドレスに該当するユーザを検索します.
	 * 
	 * @param email メールアドレス
	 * @return 検索結果
	 */
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * メールアドレスを変更します.
	 * 
	 * @param userId ユーザID
	 * @param email  メールアドレス
	 */
	public void changeEmail(Integer id, String email) {
		userRepository.updateUserEmail(id, email);
	}

}
