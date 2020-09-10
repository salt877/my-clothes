package jp.co.example.my.clothes.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.example.my.clothes.domain.Contact;
import jp.co.example.my.clothes.form.ContactForm;
import jp.co.example.my.clothes.repository.ContactRepository;

/**
 * メールを送信する業務処理を行います.
 * 
 * @author mizuki.tanimori
 *
 */
@Service
public class SendMailService {

	@Autowired
	private HttpSession session;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private MailSender sender1;

	@Autowired
	private MailSender sender2;

	@ModelAttribute
	public ContactForm setUpContactForm() {
		return new ContactForm();
	}

	/**
	 * メールを送信するためのメソッド.
	 */
	public void sendMail(ContactForm contactForm) {
		// メールの情報を詰めるオブジェクト(ユーザへ送信)
		SimpleMailMessage toUsermsg = new SimpleMailMessage();
		// メールの情報を詰めるオブジェクト(MYQLOアカウントへ送信)
		SimpleMailMessage toManagementmsg = new SimpleMailMessage();

		// 送信元メールアドレス
		toUsermsg.setFrom("my.clothes0702@gmail.com");

		// 問い合わせ情報から送信先のメールアドレスをセット(ユーザへ送信)
		Contact contact = new Contact();
		BeanUtils.copyProperties(contactForm, contact);
		String toUserEmail = contact.getEmail();
		toUsermsg.setTo(toUserEmail);

		// 問い合わせ情報から送信先のメールアドレスをセット(MYQLOアカウントへ送信)
		String managementAccountEmail = "my.clothes0702@gmail.com";
		toManagementmsg.setTo(managementAccountEmail);

		// 件名(ユーザ宛)
		toUsermsg.setSubject("【MYQLO】お問い合わせいただきありがとうございます (自動送信メール)");
		// 件名(管理者宛)
		toManagementmsg.setSubject("問い合わせを受け付けました");

		// 本文を作成
		StringBuilder text = new StringBuilder();

		// お名前
		text.append("\n" + contact.getName() + "様" + "\n\n");

		// 本文
		text.append("お世話になっております。\n");
		text.append("MYQLO運営事務局です。この度はお問い合わせいただきありがとうございました。\n\n");
		text.append("以下の内容でお問い合わせを受け付け致しました。\n\n");
		text.append("▼お問い合わせ内容▼\n");
		text.append("---------------------------------------------------------------------------------\n");

		// 以下問い合わせ内容
		text.append("お名前：" + contact.getName() + "\n");
		text.append("メールアドレス：" + contact.getEmail() + "\n");
		text.append("お問い合わせ内容：\n");
		text.append(contact.getContent() + "\n");
		text.append("---------------------------------------------------------------------------------\n");
		text.append("ご不明な点などがございましたら、お気軽にご連絡ください。\n\n");

		// フッター部分
		text.append("──────────────────────────────────────────\n");
		text.append("MYQLO" + "\n");
		text.append("〒ooo-oooo 東京都oo区" + "\n");
		text.append("メールアドレス： my.clothes0702@gmail.com" + "\n");
		text.append("電話番号：oo-oooo-oooo" + "\n");
		text.append("営業時間：平日 oo:oo 〜 oo:oo  " + "\n");
		text.append("お問合せメールへの返信は2営業日以内にいたします。\n");
		text.append("──────────────────────────────────────────");

		// 上記で作成した本文をセット(ユーザ宛)
		toUsermsg.setText(text.toString());
		// 上記で作成した本文をセット(ユーザ宛)
		toManagementmsg.setText(text.toString());

		try {
			// 送信処理
			this.sender1.send(toUsermsg);
			this.sender2.send(toManagementmsg);
		} catch (Exception e) {
			// 例外処理が必要
			e.printStackTrace();
		}
	}
}
