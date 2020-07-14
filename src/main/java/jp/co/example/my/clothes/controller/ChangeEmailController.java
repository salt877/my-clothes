package jp.co.example.my.clothes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/changeEmail")
public class ChangeEmailController {
	
	/**
	 * メールアドレス変更画面を表示します.
	 * 
	 * @return パスワード変更画面
	 */
	@RequestMapping("")
	public String showChangeEmail() {
		return "change_email";
	}

}
