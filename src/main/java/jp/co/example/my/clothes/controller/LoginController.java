package jp.co.example.my.clothes.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ログイン画面へ遷移するコントローラークラス.
 * 
 * @author rinashioda
 *
 */
@Controller
@RequestMapping("")
public class LoginController {

	@Autowired
	private HttpSession session;

	/**
	 * ログイン画面を表示します.
	 * 
	 * @param model リクエストスコープ
	 * @param error エラー
	 * @return ログイン画面
	 */
	@RequestMapping("/showLogin")
	public String showLogin(Model model, @RequestParam(required = false) String error) {

		if (error != null) {
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です。");
			System.err.println(error);
		}
		return "login";
	}

}
