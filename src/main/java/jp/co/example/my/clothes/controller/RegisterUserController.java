package jp.co.example.my.clothes.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.form.RegisterUserForm;
import jp.co.example.my.clothes.service.RegisterUserCompleteSendMailService;
import jp.co.example.my.clothes.service.RegisterUserDetailService;
import jp.co.example.my.clothes.service.RegisterUserService;

/**
 * ユーザ情報を登録するコントローラークラス.
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
	private RegisterUserDetailService registerUserDetailService;

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
	 * @param form   ユーザ情報用フォーム
	 * @param result エラー格納オブジェクト
	 * @return ユーザ登録完了画面に遷移
	 */
	@RequestMapping("/registerUser")
	public String registerUser(@Validated RegisterUserForm form, BindingResult result) {

		String myqloEmail = "my.clothes0702@gmail.com";

		// メールアドレスが重複している場合
		User duplicationUser = registerUserService.searchUserByEmail(form.getEmail());
		if (duplicationUser != null) {
			result.rejectValue("email", "", "そのメールアドレスはすでに使われています");
		}

		// MYQLOのメールアドレスを登録しようとした場合
		if (myqloEmail.equals(form.getEmail())) {
			result.rejectValue("email", "", "そのメールアドレスは使用できません");
		}

		// パスワードと確認用パスワードが一致しない場合
		if (!(form.getPassword().equals(form.getConfirmPassword()))) {
			result.rejectValue("confirmPassword", "", "確認用パスワードが一致しません");
		}

		// エラーがあれば登録画面に戻る
		if (result.hasErrors()) {
			System.out.println(result.toString());
			return showRegisterUser();
		}

		User user = new User();
		BeanUtils.copyProperties(form, user);

		// usersテーブルにログイン時入力情報を挿入
		registerUserService.registerUser(user);

		// user_detailsテーブルにログイン時登録したMYQLO IDをニックネームとして挿入
		UserDetail newUserDetail = new UserDetail();
		newUserDetail.setUserId(user.getId());
		newUserDetail.setUserName(user.getMyqloId());
		registerUserDetailService.registerUserName(newUserDetail);

		// メールを送信する
		sendMailService.sendMail(form, user);

		System.out.println("初回登録完了！");

		return "complete_register";
	}

}
