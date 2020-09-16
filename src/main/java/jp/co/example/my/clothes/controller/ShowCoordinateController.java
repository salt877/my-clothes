package jp.co.example.my.clothes.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.Coordinate;
import jp.co.example.my.clothes.domain.Like;
import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.form.CoordinateForm;
import jp.co.example.my.clothes.form.RegisterCoordinateForm;
import jp.co.example.my.clothes.service.ShowCoordinateService;
import jp.co.example.my.clothes.service.ShowUserNameService;

/**
 * コーディネート画面を表示するコントローラ.
 * 
 * @author masashi.nose
 *
 */
@Controller
@RequestMapping("/coordinate")
public class ShowCoordinateController {

	@Autowired
	private ShowCoordinateService showCoordinateService;

	@Autowired
	private ShowUserNameService showUserNameService;

	@ModelAttribute
	public RegisterCoordinateForm setUpForm() {
		return new RegisterCoordinateForm();
	}

	@ModelAttribute
	public CoordinateForm setUpCoordinateForm() {
		return new CoordinateForm();
	}

	/**
	 * コーディネート画面を表示します.
	 * 
	 * @param loginUser ユーザー情報
	 * @param model
	 * @return コーディネート画面へ遷移
	 */
	@RequestMapping("/")
	public String showCoordinate(@AuthenticationPrincipal LoginUser loginUser, Model model, ModelMap modelMap) {

		Integer userId = loginUser.getUser().getId();

		UserDetail userDetail = showUserNameService.showUserName(userId);
		model.addAttribute("userDetail", userDetail);

		String userMyqloId = loginUser.getUser().getMyqloId();
		modelMap.addAttribute("userMyqloId", userMyqloId);

		List<Coordinate> coordinateList = showCoordinateService.showCoordinate(userId);

		model.addAttribute("coordinateList", coordinateList);

		return "coordinate";

	}

	/**
	 * 過去のコーディネート一覧を表示します.
	 * 
	 * @param loginUser
	 * @param model
	 * @return
	 */
	@RequestMapping("/past-coordinate")
	public String showPastCoordinate(@AuthenticationPrincipal LoginUser loginUser, Model model, ModelMap modelMap) {
		Integer userId = loginUser.getUser().getId();

		List<Coordinate> coordinateList = showCoordinateService.showCoordinate(userId);

		UserDetail userDetail = showUserNameService.showUserName(userId);
		model.addAttribute("userDetail", userDetail);

		String userMyqloId = loginUser.getUser().getMyqloId();
		modelMap.addAttribute("userMyqloId", userMyqloId);

		model.addAttribute("coordinateList", coordinateList);

		return "past-coordinate";
	}

	/**
	 * 公開コーデ画面を表示します。
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/public-coordinate")
	public String showCoordinateList(@AuthenticationPrincipal LoginUser loginUser, CoordinateForm form, Model model, ModelMap modelMap,
			String gender) {
		Integer userId = loginUser.getUser().getId();
		form.setGender("ALL");

		List<Coordinate> coordinateList = showCoordinateService.showPublicCoordinate();

		for (Coordinate coodinate : coordinateList) {
			List<Like> likeList = showCoordinateService.showLikes(coodinate.getId());
			coodinate.setLikeList(likeList);

		}

		UserDetail userDetail = showUserNameService.showUserName(userId);
		model.addAttribute("userDetail", userDetail);

		modelMap.addAttribute("coordinateList", coordinateList);

		return "public_coordinate2";

	}

	/**
	 * コーデ検索します.
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping("/search")
	public String searchCoordinate(@AuthenticationPrincipal LoginUser loginUser, CoordinateForm form, Model model) {
		System.out.println(form.getName());
		System.out.println(form.getGender());

		List<Coordinate> coordinateList = new ArrayList<>();

		if (StringUtils.isEmpty(form.getName()) && form.getGender().equals("ALL")) {
			coordinateList = showCoordinateService.showPublicCoordinate();
			for (Coordinate coodinate : coordinateList) {
				List<Like> likeList = showCoordinateService.showLikes(coodinate.getId());
				coodinate.setLikeList(likeList);

			}

		}

		if (!(StringUtils.isEmpty(form.getName())) && form.getGender().equals("ALL")) {
			coordinateList = showCoordinateService.showCoordinateByName(form.getName());
			for (Coordinate coodinate : coordinateList) {
				List<Like> likeList = showCoordinateService.showLikes(coodinate.getId());
				coodinate.setLikeList(likeList);

			}

		}

		if (form.getGender().equals("ALL")) {
			coordinateList = showCoordinateService.showCoordinateByName(form.getName());
			for (Coordinate coodinate : coordinateList) {
				List<Like> likeList = showCoordinateService.showLikes(coodinate.getId());
				coodinate.setLikeList(likeList);

			}

		}

		if (form.getGender().equals("MEN")) {
			coordinateList = showCoordinateService.showCoordinateByNameAndGender(form.getName(), "MEN");
			for (Coordinate coodinate : coordinateList) {
				List<Like> likeList = showCoordinateService.showLikes(coodinate.getId());
				coodinate.setLikeList(likeList);

			}

		}

		if (form.getGender().equals("WOMEN")) {
			coordinateList = showCoordinateService.showCoordinateByNameAndGender(form.getName(), "WOMEN");
			for (Coordinate coodinate : coordinateList) {
				List<Like> likeList = showCoordinateService.showLikes(coodinate.getId());
				coodinate.setLikeList(likeList);

			}

		}

		if (form.getGender().equals("OTHER")) {
			coordinateList = showCoordinateService.showCoordinateByNameAndGender(form.getName(), "OTHER");
			for (Coordinate coodinate : coordinateList) {
				List<Like> likeList = showCoordinateService.showLikes(coodinate.getId());
				coodinate.setLikeList(likeList);

			}

		}

		model.addAttribute("coordinateList", coordinateList);

		Integer userId = loginUser.getUser().getId();

		UserDetail userDetail = showUserNameService.showUserName(userId);
		model.addAttribute("userDetail", userDetail);

		return "public_coordinate2";

	}
	
	

}
