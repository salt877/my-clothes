package jp.co.example.my.clothes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.LoginUser;
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

	/**
	 * 退会ページに遷移する,
	 * 
	 * @return
	 */
	@RequestMapping("/showWithdrawalPage")
	public String showWithdrawalPage() {
		return "delete_user.html";
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
