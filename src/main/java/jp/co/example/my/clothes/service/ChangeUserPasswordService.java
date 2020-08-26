package jp.co.example.my.clothes.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.my.clothes.domain.PasswordReset;
import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.repository.PasswordResetRepository;
import jp.co.example.my.clothes.repository.UserRepository;

/**
 * パスワードを変更する際に使用するサービスクラスです.
 * 
 * @author rinashioda
 *
 */
@Service
@Transactional
public class ChangeUserPasswordService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordResetRepository passwordResetRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailSender mailSender;

	/**
	 * メールアドレスからユーザを検索します.
	 * 
	 * @param email
	 * @return
	 */
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * DBにユーザID、URL、有効期限を保存します.
	 * 
	 * @param passwordReset
	 * @param email
	 */
	public void registerPasswordReset(HttpServletRequest request, PasswordReset passwordReset, String email) {

		Calendar expireDate = Calendar.getInstance();
		expireDate.add(Calendar.MINUTE, 60);
		Timestamp expireDate1hourAfter = new Timestamp(expireDate.getTime().getTime());

		UUID uuid = UUID.randomUUID();
		String randomUrl = uuid.toString();

		User user = userRepository.findByEmail(email);

		passwordReset.setUserId(user.getId());
		passwordReset.setRandomUrl(randomUrl);
		passwordReset.setExpireDate(expireDate1hourAfter);
		passwordResetRepository.save(passwordReset);

	}

	/**
	 * メールの本文.
	 * 
	 * @param request
	 * @param response
	 * @param passwordReset
	 * @param email
	 */
	@Async
	public void sendChangePasswordMail(HttpServletRequest request, HttpServletResponse response,
			PasswordReset passwordReset, String email) {

		
		String url = "https://myqlo.herokuapp.com" + "/passwordInputPage" + "?randomUrl=" + passwordReset.getRandomUrl();
		String to = email;
		String subject = "【MYQLO】パスワード変更はこちらから";

		StringBuilder text = new StringBuilder();
		text.append("こちらのリンクをクリックしてパスワードを変更してください。\n");
		text.append("※リンクは1時間有効です。\n");
		text.append(url);
		text.append("\n\n\n");
		text.append("※こちらのメールに心当たりのない場合は破棄してください。");

		sendMail(to, subject, text.toString());

	}

	/**
	 * メールを送信するメソッド.
	 * 
	 * @param to      送信先メールアドレス
	 * @param subject メールの件名
	 * @param context メール本文
	 */
	public void sendMail(String to, String subject, String text) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);

		mailSender.send(message);

	}

	/**
	 * ランダムURLを使ってユーザIDを検索します.
	 * 
	 * @param randomUrl ランダムURL
	 * @return ユーザ検索結果
	 */
	public PasswordReset findUserByUrl(String randomUrl) {
		return userRepository.findUserByUrl(randomUrl);
	}

	/**
	 * ユーザIDでユーザを検索します.
	 * 
	 * @param userId ユーザID
	 * @return ユーザ検索結果
	 */
	public User findByUserId(Integer userId) {
		return userRepository.findUserByUserId(userId);
	}

	/**
	 * 入力されたパスワードに変更します.
	 * 
	 * @param userId ユーザID
	 * @param        newPassword 新しいパスワード
	 */
	public void changeUserPassword(Integer userId, String newPassword) {

		String password = passwordEncoder.encode(newPassword);
		userRepository.updateUserPassword(userId, password);
	}

}
