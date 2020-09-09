package jp.co.example.my.clothes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.repository.UserRepository;

/**
 * ユーザーページを表示する処理を行うサービスクラスです.
 * 
 * @author rinashioda
 *
 */
@Service
public class ShowUserPageService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * ユーザIDから、該当するMYQLO IDを取得します.
	 * 
	 * @param userId ユーザID
	 * @return ユーザ情報
	 */
	public User searchMyqloId(Integer userId) {
		return userRepository.findMyqloId(userId);
	}

}
