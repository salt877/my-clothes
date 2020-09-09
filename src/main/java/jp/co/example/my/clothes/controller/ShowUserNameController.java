package jp.co.example.my.clothes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.service.ShowUserNameService;

/**
 * ヘッダーにユーザ名と写真を表示させるコントローラクラスです.
 * 
 * @author rinashioda
 *
 */
@Controller
public class ShowUserNameController {
	
	@Autowired
	private ShowUserNameService showUserNameService;
	
	@ResponseBody
	public String showUserName(Model model,@AuthenticationPrincipal LoginUser loginUser) {
		
		Integer userId = loginUser.getUser().getId();
		System.out.println("ヘッダーのコントローラ"+userId);
		UserDetail userDetail = showUserNameService.showUserName(userId);
		
		model.addAttribute("userDetail", userDetail);
		
		return "header";
	}

}
