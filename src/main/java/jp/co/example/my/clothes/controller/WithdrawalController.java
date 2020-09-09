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
import jp.co.example.my.clothes.service.WIthdrawalService;

/**
 * 退会機能を操作するコントローラー.
 * 
 * @author ashibe
 *
 */
@Controller
@RequestMapping("/withdrawal")
public class WithdrawalController {

	@Autowired
	private WIthdrawalService withdrawalService;
	
	@Autowired
	private ShowUserNameService showUserNameService;

	/**
	 * 退会ページに遷移する,
	 * 
	 * @return
	 */
	@RequestMapping("/showWithdrawalPage")
	public String showWithdrawalPage(Model model,ModelMap modelMap,@AuthenticationPrincipal LoginUser loginUser) {
		
		Integer userId = loginUser.getUser().getId();

		UserDetail userDetail = showUserNameService.showUserName(userId);
		model.addAttribute("userDetail", userDetail);
		
		String userMyqloId = loginUser.getUser().getMyqloId();
		modelMap.addAttribute("userMyqloId", userMyqloId);
		System.out.println("ここ"+userMyqloId);
		
		return "delete_user";
	}

	/**
	 * 退会する.
	 * 
	 * @param loginUser
	 * @return
	 */
	@RequestMapping("/withdrawalUser")
	public String WithdrawalUser(@AuthenticationPrincipal LoginUser loginUser) {
		System.out.println(loginUser.getUser().getEmail());

		withdrawalService.WithdrawalUser(loginUser);

		return "login.html";
	}
}
