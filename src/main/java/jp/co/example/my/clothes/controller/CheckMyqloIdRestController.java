package jp.co.example.my.clothes.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.service.RegisterUserDetailService;

@RestController
public class CheckMyqloIdRestController {

	@Autowired
	private RegisterUserDetailService registerUserDatailService;

	@RequestMapping(value = "/myqloId_check", method = RequestMethod.POST)
	public Map<String, String> myqloIdCheck(String myqloId, Integer userId) {

		System.out.println("非同期通信成功" + myqloId);
		Map<String, String> myqloIdMap = new HashMap<>();
		String duplicateMessage = null;
		User user = registerUserDatailService.searchMyqloIdByUserId(userId);

		if (user == null) {
			duplicateMessage = "使用できます";

		} else {
			duplicateMessage = "使用できません";

		}
		myqloIdMap.put("duplicateMessage", duplicateMessage);

		return myqloIdMap;

	}

}
