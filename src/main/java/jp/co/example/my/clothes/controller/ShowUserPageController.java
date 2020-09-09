package jp.co.example.my.clothes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.service.ShowMyPageService;

/**
 * ユーザーページ（他人から見たマイページ）を表示するコントローラクラスです.
 * 
 * @author rinashioda
 *
 */
@Controller
public class ShowUserPageController {
	
	@Autowired
	private ShowMyPageService showMyPageService;
	
	@RequestMapping("/{userMyqloId}")
	public String showMyPage(Model model,@AuthenticationPrincipal LoginUser loginUser,@PathVariable("userMyqloId")String userMyqloId) {
		
		Integer userId = loginUser.getUser().getId();
		UserDetail userInfomation = showMyPageService.showMyPage(userId);
		model.addAttribute("userInfomation", userInfomation);
		model.addAttribute("userDetail", userInfomation);
		
		System.out.println("このMYQLOIDのマイページを表示:"+userMyqloId);
		
		//該当するMYQLOIDを持つユーザがいない場合の処理
		if(showMyPageService.searchUserByMyqloId(userMyqloId)==null) {
			return "error/404";
		}
		
		return "myqlo_user_page";
	}

}
