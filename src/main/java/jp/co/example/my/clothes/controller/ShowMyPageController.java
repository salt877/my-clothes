package jp.co.example.my.clothes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.service.ShowMyPageService;

@Controller
public class ShowMyPageController {

	@Autowired
	private ShowMyPageService showMyPageService;

	/**
	 * ログインしているユーザのマイページを表示します.
	 * 
	 * @param loginUser ログインユーザ
	 * @return マイページ
	 */
	@RequestMapping("/mypage")
	public String showMyPage(@AuthenticationPrincipal LoginUser loginUser) {

		Integer userId = loginUser.getUser().getId();
		showMyPageService.showMyPage(userId);

		return "my_page";
	}

}
