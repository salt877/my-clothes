package jp.co.example.my.clothes;

import jp.co.example.my.clothes.domain.User;

public interface IUserService {
	
	 void createPasswordResetTokenForUser(User user, String token);

}
