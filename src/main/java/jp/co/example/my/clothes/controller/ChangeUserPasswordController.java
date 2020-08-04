package jp.co.example.my.clothes.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.PasswordReset;
import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.form.ChangeUserPasswordForm;
import jp.co.example.my.clothes.service.ChangeUserPasswordService;

/**
 * ユーザのパスワードを変更する際に使用するコントローラクラスです.
 * 
 * @author rinashioda
 *
 */
@Controller
public class ChangeUserPasswordController {

//	@Autowired
//	private HttpSession session;

	@Autowired
	private ChangeUserPasswordService changeUserPasswordService;

	@ModelAttribute
	public ChangeUserPasswordForm setUpForm() {
		return new ChangeUserPasswordForm();
	}

	/**
	 * パスワードを忘れた際にメールアドレスを入力するページを表示します.
	 * 
	 * @return メールアドレス入力ページ
	 */
	@RequestMapping("/forgotPassword")
	public String showInputEmail() {
		return "send_mail";
	}

	/**
	 * 入力されたメールアドレスにURLを送ります.
	 * 
	 * @param request
	 * @param passwordReset
	 * @param email
	 * @return
	 */
	@RequestMapping("/sendInputPage")
	public String sendPasswordChangeMail(HttpServletRequest request, HttpServletResponse response,
			PasswordReset passwordReset, String email, Model model) {

		try {
			changeUserPasswordService.registerPasswordReset(request, passwordReset, email);
			changeUserPasswordService.sendChangePasswordMail(request, response, passwordReset, email);

		} catch (NullPointerException ex) {
			System.err.println("このメールアドレスのユーザーなし");
		} finally {
			System.out.println("メール送信完了の文言が表示");
		}

		return "complete_send_mail";

	}

	/**
	 * 新しいパスワードを入力するページを表示します.
	 * 
	 * @param request
	 * @param passwordReset
	 * @return パスワード変更ページ
	 */
	@RequestMapping("/passwordInputPage")
	public String showInputPassword(HttpServletRequest request, PasswordReset passwordReset, Model model) {

//		CsrfToken csrf = ((CsrfToken) request.getAttribute("_csrf"));
//		System.out.println("パスワード入力画面を開いた時のトークンは" + csrf.getToken());
//		HttpSession session = request.getSession();
//		System.out.println("パスワード入力画面を開いた時のセッションIDは" + session.getId());

		try {
			// URLを使ってDBからユーザを検索する
			PasswordReset changeList = changeUserPasswordService.findUserByUrl(passwordReset.getRandomUrl());
			model.addAttribute("userId", changeList.getUserId());
			model.addAttribute("randomUrl", changeList.getRandomUrl());
			// 現在の時間を取得する
			Timestamp nowTime = new Timestamp(System.currentTimeMillis());

			// URLがDBに格納されていない場合または有効期限切れの場合
			if (changeList == null || changeList.getExpireDate().before(nowTime)) {
				return "time_out";
			} else {
				System.out.println(changeList.getExpireDate());
			}

		} catch (NullPointerException ex) {
			return "time_out";

		}

		return "change_password";

	}

	@RequestMapping("/changePassword")
	public String changePassword(Model model, @Validated ChangeUserPasswordForm form, BindingResult result,
			HttpServletRequest request, PasswordReset passwordReset, User user) {

		//パスワードと確認用パスワードが等しくない場合
		if(!(form.getNewPassword().equals(form.getConfirmNewPassword()))){
			System.out.println(form.getNewPassword());
			System.out.println(form.getConfirmNewPassword());
			result.rejectValue("confirmNewPassword", "","確認用パスワードが一致しません");
		}
		// エラーがある場合はパスワード入力画面に戻る
		if (result.hasErrors()) {
			return showInputPassword(request, passwordReset, model);
		}

		// 抽出したオブジェクトのユーザIDを使ってユーザを検索する
		User userList = changeUserPasswordService.findByUserId(form.getUserId());

		changeUserPasswordService.changeUserPassword(userList.getId(), form.getNewPassword());

		return "complete_change_password";
	}

}
