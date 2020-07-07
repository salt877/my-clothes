package jp.co.example.my.clothes.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.service.ShowTopPageService;

@Controller
@RequestMapping("")
public class ShowTopPageController {

	@Autowired
	private ShowTopPageService showTopPageService;

	@Autowired
	private HttpSession session;

	/**
	 * ログインしているユーザの登録アイテムを全て表示します.
	 * 
	 * @param model リクエストスコープ
	 * @param       loginUser ログインユーザ
	 * @return トップページ
	 */
	@RequestMapping("/")
	public String showItemList(Model model,@AuthenticationPrincipal LoginUser loginUser) {

		Integer userId = loginUser.getUser().getId();

		List<Clothes> clothesList = showTopPageService.showItemList(userId);

		model.addAttribute("clothesList", clothesList);

		return "top";
	}

}
