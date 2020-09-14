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
@RequestMapping(value = "/input_myqloId")
public class CheckMyqloIdRestController {

	@Autowired
	private RegisterUserDetailService registerUserDatailService;

	@RequestMapping(value = "/myqloId_check", method = RequestMethod.POST)
	public Map<String, String> myqloIdCheck(String myqloId) {

		Map<String, String> myqloIdMap = new HashMap<>();

		Boolean pattern = myqloId.matches("^([a-zA-Z0-9]{6,15})$");
		String duplicateMessage = null;

		User user = registerUserDatailService.searchMyqloId(myqloId);

		if (user == null && pattern == true) {
			duplicateMessage = "使用できます";
		} else if (user != null) {
			duplicateMessage = "こちらのMYQLO IDは使用できません";
		} else if (pattern == false) {
			duplicateMessage = "6文字以上15文字以下で入力してください";
		}
		myqloIdMap.put("duplicateMessage", duplicateMessage);

		return myqloIdMap;

	}

}
