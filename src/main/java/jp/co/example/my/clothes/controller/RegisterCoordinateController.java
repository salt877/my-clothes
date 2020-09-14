package jp.co.example.my.clothes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.Coordinate;
import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.form.RegisterCoordinateForm;
import jp.co.example.my.clothes.service.RegisterCoordinateService;
import jp.co.example.my.clothes.service.ShowUserNameService;

/**
 * コーディネート登録を行うコントローラ.
 * 
 * @author masashi.nose
 *
 */
@Controller
@RequestMapping("/coordinate")
public class RegisterCoordinateController {

	@Autowired
	private RegisterCoordinateService registerCoordinateService;

	@Autowired
	private ShowUserNameService showUserNameService;

	@ModelAttribute
	public RegisterCoordinateForm setUpForm() {
		return new RegisterCoordinateForm();
	}

	/**
	 * コーディネートを登録します.登録後、完了画面へリダイレクトします.
	 * 
	 * @param loginUser
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping("/register_coordinate")
	public String registerCoordinate(@Validated RegisterCoordinateForm form, BindingResult result,
			@AuthenticationPrincipal LoginUser loginUser) {

		Coordinate coordinate = new Coordinate();
		coordinate.setUserId(loginUser.getUser().getId());
		coordinate.setFashionAccessories(form.getIntFashionAccessories());
		coordinate.setTops1(form.getIntTops1());
		coordinate.setTops2(form.getIntTops2());
		coordinate.setOuters(form.getIntOuters());
		coordinate.setBottoms(form.getIntBottoms());
		coordinate.setShoes(form.getIntShoes());
		coordinate.setBag(form.getIntBag());
		coordinate.setDress(form.getIntDress());
		coordinate.setName(form.getName());

		System.out.println(form.getIsPublic());

		if (StringUtils.isEmpty(form.getIsPublic())) {
			coordinate.setPublic(false);
		} else {
			coordinate.setPublic(true);

		}
		registerCoordinateService.registerCooridnate(coordinate);

		return "redirect:/coordinate/finished";

	}

	/**
	 * コーデ完了画面を表示します.
	 * 
	 * @return
	 */
	@RequestMapping("/finished")
	public String finishCoordinate(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		Integer userId = loginUser.getUser().getId();

		UserDetail userDetail = showUserNameService.showUserName(userId);
		model.addAttribute("userDetail", userDetail);

		return "finish_coordinate";
	}
}
