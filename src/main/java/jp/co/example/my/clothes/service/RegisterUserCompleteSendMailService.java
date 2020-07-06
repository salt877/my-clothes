package jp.co.example.my.clothes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.repository.UserRepository;

/**
 * ユーザ登録完了後に完了メールを送る処理をするサービスクラスです.
 * 
 * @author mizuki.tanimori
 *
 */
@Service
@Transactional
public class RegisterUserCompleteSendMailService {
	
	@Autowired
	private MailSender sender;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * ユーザ登録完了後にメールを送信するメソッド.
	 */
	public void sendMail() {
		// メール情報を詰めるオブジェクト
		SimpleMailMessage msg = new SimpleMailMessage();
		
		// 送信元アドレス
		msg.setFrom("my.clothes0702@gmail.com");
		
		// ユーザ情報から送信先のメールアドレスをセット
		User user = new User();
		msg.setTo(user.getEmail());
		
		// 件名
		msg.setSubject("【MYQLO】ユーザ登録完了のお知らせ（自動送信メール）");
	
		// 本文を作成
		StringBuilder text = new StringBuilder();
		
		// お名前
		text.append("\n" + "新規ユーザ様" + "\n\n");
		
		// 本文
		text.append("お世話になっております。\n");
		text.append("MYQLO運営事務局です。この度は本サイトにご登録いただきありがとうございます。\n");
		text.append("下記にご登録いただいたメールアドレスとパスワードをお知らせ致します。\n");
		text.append("URLよりログイン画面にアクセスできますので、\n\n");
		text.append("ログイン画面URL：http://localhost:8080//showLogin\n");
		text.append("メールアドレス：" + user.getEmail() + "\n");
		text.append("パスワード：" + user.getPassword() + "\n\n");
		text.append("引き続き、MYQLOをお楽しみください。\n");
		text.append("今後ともよろしくお願い申し上げます。\n");
		
		// フッター部分
		text.append("──────────────────────────────────────────\n");
		text.append("MYQLO" + "\n");
		text.append("〒ooo-oooo 東京都oo区" + "\n");
		text.append("メールアドレス： my.clothes0702@gmail.com" + "\n");
		text.append("電話番号：oo-oooo-oooo" + "\n");
		text.append("営業時間：平日 oo:oo 〜 oo:oo  " + "\n");
		text.append("お問合せメールへの返信は2営業日以内にいたします。\n");
		text.append("──────────────────────────────────────────");
		
		
		
		
	
	}

}
