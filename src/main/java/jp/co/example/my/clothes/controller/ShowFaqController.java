package jp.co.example.my.clothes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.service.ShowUserNameService;

/**
 * faq画面を表示させるコントローラクラス.
 * 
 * @author masashi.nose
 *
 */
@Controller
public class ShowFaqController {

	@Autowired
	private ShowUserNameService showUserNameService;

	/**
	 * faq画面へ遷移します。
	 * 
	 * @return faq画面
	 */
	@RequestMapping("/faq")
	public String showFaq(@AuthenticationPrincipal LoginUser loginUser, Model model,ModelMap modelMap) {

		Integer userId = loginUser.getUser().getId();

		UserDetail userDetail = showUserNameService.showUserName(userId);
		model.addAttribute("userDetail", userDetail);
		
		String userMyqloId = loginUser.getUser().getMyqloId();
		modelMap.addAttribute("userMyqloId", userMyqloId);

		return "faq";
	}

}
