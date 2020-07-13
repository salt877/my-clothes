package jp.co.example.my.clothes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.repository.UserRepository;

/**
 * 退会手続きのためのサービス.
 * 
 * @author ashibe
 *
 */
@Service
@Transactional
public class WIthdrawalService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * ユーザー情報をログインに使えなくする(退会する).
	 * 
	 * @param user
	 */
	public void WithdrawalUser(LoginUser user) {
		userRepository.deleteUser(user);
	}

}
