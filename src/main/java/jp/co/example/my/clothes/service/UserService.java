//package jp.co.example.my.clothes.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import jp.co.example.my.clothes.IUserService;
//import jp.co.example.my.clothes.domain.PasswordResetToken;
//import jp.co.example.my.clothes.domain.User;
//import jp.co.example.my.clothes.repository.PasswordResetTokenRepository;
//
///**
// * baeldung/service/UserService.java
// * 
// * @author rinashioda
// *
// */
//public class UserService implements IUserService {
//
//	@Autowired
//	private PasswordResetTokenRepository passwordTokenRepository;
//
//	@Override
//	public void createPasswordResetTokenForUser(final User user, final String token) {
//		PasswordResetToken myToken = new PasswordResetToken(token, user);
//		passwordTokenRepository.save(myToken);
//	}
//
//}
