package jp.co.example.my.clothes.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.form.ChangeUserEmailForm;
import jp.co.example.my.clothes.service.ChangeUserEmailService;

@Controller
@RequestMapping("/showChangeEmail")
public class ChangeEmailController {

	@Autowired
	private ChangeUserEmailService changeUserEmailService;

	@Autowired
	private HttpSession session;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public ChangeUserEmailForm setUpForm() {
		return new ChangeUserEmailForm();
	}

	/**
	 * メールアドレス変更画面を表示します.
	 * 
	 * @return パスワード変更画面
	 */
	@RequestMapping("")
	public String showChangeEmail() {
		return "change_email";
	}

	/**
	 * メールアドレスを変更します.
	 * 
	 * @param model モデル
	 * @param loginUser　ログインユーザ
	 * @param form　メールアドレス変更時に使用するフォーム
	 * @param result　エラー結果
	 * @return　メールアドレス変更完了画面
	 */
	@RequestMapping("/input")
	public String changeEmail(Model model, @AuthenticationPrincipal LoginUser loginUser,
			@Validated ChangeUserEmailForm form, BindingResult result) {

		String myqloEmail = "my.clothes0702@gmail.com";

		User user = changeUserEmailService.findByEmail(form.getEmail());

		// 入力したメールアドレスですでに登録されていたら入力画面に戻る
		if (user != null) {
			result.rejectValue("email", "", "そのメールアドレスはすでに使われています");
			return showChangeEmail();
		}
		
		// MYQLOのメールアドレスに変更できないようにする
		if (myqloEmail.equals(form.getEmail())) {
			result.rejectValue("email", "", "そのメールアドレスは使用できません");
		}
		
		// 入力したメールアドレスと確認用メールアドレスが一致しなければ入力画面に戻る
		if (!(form.getEmail().equals(form.getConfirmEmail()))) {
			result.rejectValue("confirmEmail", "", "確認用メールアドレスが一致しません");
			return showChangeEmail();
		}
		
		// エラーがあれば入力画面に戻る
		if (result.hasErrors()) {
			return showChangeEmail();
		}

		Integer id = loginUser.getUser().getId();
		changeUserEmailService.changeEmail(id, form.getEmail());

		return "complete_change_email";
	}

}
