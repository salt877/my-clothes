package jp.co.example.my.clothes.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.example.my.clothes.domain.Coordinate;
import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.service.ShowMyPageService;

@RestController
public class ShowMyPageRestController {
	
	@Autowired
	private ShowMyPageService showMyPageService;
	
	@RequestMapping(value = "/show_button", method = RequestMethod.GET)
	public boolean coordinateMap(@AuthenticationPrincipal LoginUser loginUser, Integer coordinateId){
		
		int loginUserId = loginUser.getUser().getId();
		Coordinate coordinate = showMyPageService.displayBranch(coordinateId);
		int userId = coordinate.getUserId();
		System.out.println(userId);
		
		if(loginUserId == userId) {
			return true;
		} 
		return false;
	}
}
