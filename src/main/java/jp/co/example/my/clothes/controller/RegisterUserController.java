package jp.co.example.my.clothes.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.form.RegisterUserForm;
import jp.co.example.my.clothes.service.RegisterUserCompleteSendMailService;
import jp.co.example.my.clothes.service.RegisterUserService;
import jp.co.example.my.clothes.service.SendMailService;

/**
 * ユーザ情報を操作するコントローラークラス.
 * 
 * @author rinashioda
 *
 */
@Controller
@RequestMapping("")
public class RegisterUserController {

	@Autowired
	private RegisterUserService registerUserService;
	
	@Autowired
	private RegisterUserCompleteSendMailService sendMailService;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public RegisterUserForm setUpForm() {
		return new RegisterUserForm();
	}

	/**
	 * ユーザ登録画面を出力します.
	 * 
	 * @return ユーザ登録画面
	 */
	@RequestMapping("/showRegisterUser")
	public String showRegisterUser() {
		return "register_user";
	}

	/**
	 * ユーザ情報を登録します.
	 * 
	 * @param form ユーザ情報用フォーム
	 * @param result エラー格納オブジェクト
	 * @return ログイン画面へリダイレクト
	 */
	@RequestMapping("/registerUser")
	public String registerUser(@Validated RegisterUserForm form, BindingResult result) {

		// メールアドレスが重複している場合
		User duplicationUser = registerUserService.searchUserByEmail(form.getEmail());
		if (duplicationUser != null) {
			result.rejectValue("email", "", "そのメールアドレスはすでに使われています");
		}

		// エラーがあれば登録画面に戻る
		if (result.hasErrors()) {
			return showRegisterUser();
		}

		User user = new User();
		BeanUtils.copyProperties(form, user);
		
		registerUserService.registerUser(user);
		
		
		// メールを送信する
		sendMailService.sendMail(form);

		return "redirect:/showLogin";
	}
}
