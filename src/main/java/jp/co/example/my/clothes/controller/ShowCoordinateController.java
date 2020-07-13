package jp.co.example.my.clothes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import jp.co.example.my.clothes.domain.Coordinate;
import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.service.ShowCoordinateService;

/**
 * コーディネート画面を表示するコントローラ.
 * 
 * @author masashi.nose
 *
 */
@Controller
public class ShowCoordinateController {

	@Autowired
	private ShowCoordinateService showCoordinateService;

	/**
	 * コーディネート画面を表示します.
	 * 
	 * @param loginUser ユーザー情報
	 * @param model
	 * @return コーディネート画面へ遷移
	 */
	public String showCoordinate(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		List<Coordinate> coordinateList = showCoordinateService.showCoordinate(loginUser.getUser().getId());

		model.addAttribute("coordinateList", coordinateList);

		return "coordinate";

	}

}
