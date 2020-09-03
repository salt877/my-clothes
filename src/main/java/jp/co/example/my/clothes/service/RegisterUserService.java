package jp.co.example.my.clothes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.repository.UserRepository;

/**
 * ユーザ情報を処理するサービスクラス.
 * 
 * @author rinashioda
 *
 */
@Service
@Transactional
public class RegisterUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * ユーザ情報を登録します.
	 * 
	 * パスワードのハッシュ化をしてから登録
	 * 
	 * @param user ユーザ情報
	 */
	public void registerUser(User user) {

		// パスワードのハッシュ化
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		System.out.println("サービスでインサート行う前のユーザID:" + user.getId());
		userRepository.save(user);
		System.out.println("サービスでインサート行ったあとのユーザID:" + user.getId());
	}

	/**
	 * メールアドレスでユーザ情報を検索します.
	 * 
	 * @param email メールアドレス
	 * @return ユーザ情報 存在しない場合はnullを返す
	 */
	public User searchUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
