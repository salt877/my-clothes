package jp.co.example.my.clothes.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.Contact;
import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.form.ContactForm;
import jp.co.example.my.clothes.service.ContactService;
import jp.co.example.my.clothes.service.SendMailService;

/**
 * 問い合わせ情報を受け付けるコントローラクラス.
 * 
 * @author mizuki.tanimori
 *
 */
@Controller
@RequestMapping("/contact")
public class ReceiveContactContaroller {

	@Autowired
	private ContactService contactService;
	
	@Autowired
	private SendMailService sendMailService;
	
	@ModelAttribute
	public ContactForm setUpContactForm() {
		return new ContactForm();
	}
	
	/**
	 * お問い合わせページを表示します.
	 * @return 問い合わせ画面
	 */
	@RequestMapping("")
	public String showContactForm(ContactForm contactForm, @AuthenticationPrincipal LoginUser loginUser) {
		
			// メールアドレスをログイン時の情報で初期表示する
			contactForm.setEmail(loginUser.getUser().getEmail());
		
		return "contact";
	}
	
	/**
	 * お問い合わせ内容を受け付けます.
	 * 
	 * @param contactForm 問い合わせ情報
	 * @param result 入力エラー
	 * @return TOPページ（入力エラーがある場合はお問い合わせ画面に戻る）
	 */
	@RequestMapping("/reception")
	public String insertContact(@Validated ContactForm contactForm, BindingResult result, @AuthenticationPrincipal LoginUser loginUser) {
		// もしエラーが一つでもあった場合は入力画面に遷移
		if(result.hasErrors()) {
			return "contact";
		}
		
		// 問い合わせ内容をContactオブジェクトにコピー
		Contact contact = new Contact();
		BeanUtils.copyProperties(contactForm, contact);
		contactService.insertContact(contact);
		// メールを送信する
		sendMailService.sendMail(contactForm);
		
		return "top";
	}
	
}
