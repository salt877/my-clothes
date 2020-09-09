package jp.co.example.my.clothes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.service.ShowMyPageService;
import jp.co.example.my.clothes.service.ShowUserPageService;

@Controller
public class ShowMyPageController {

	@Autowired
	private ShowMyPageService showMyPageService;

	@Autowired
	private ShowUserPageService showUserPageService;

	/**
	 * ログインしているユーザのマイページを表示します.
	 * 
	 * @param loginUser ログインユーザ
	 * @return マイページ
	 */
	@RequestMapping("/mypage")
	public String showMyPage(Model model, ModelMap modelMap, @AuthenticationPrincipal LoginUser loginUser) {

		Integer userId = loginUser.getUser().getId();
		UserDetail userInfomation = showMyPageService.showMyPage(userId);
		model.addAttribute("userInfomation", userInfomation);
		model.addAttribute("userDetail", userInfomation);

		User user = showUserPageService.searchMyqloId(userId);
		String userMyqloId = user.getMyqloId();
		modelMap.addAttribute("userMyqloId", userMyqloId);

		return "my_page";
	}

}
