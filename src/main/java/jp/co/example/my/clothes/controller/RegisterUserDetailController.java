package jp.co.example.my.clothes.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.form.RegisterUserForm;
import jp.co.example.my.clothes.service.RegisterUserDetailService;

@Controller
public class RegisterUserDetailController {

	@Autowired
	private RegisterUserDetailService registerUserDetailService;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public RegisterUserForm setUpForm() {
		return new RegisterUserForm();
	}

	/**
	 * ユーザの基本情報登録ページを表示します.
	 * 
	 * @return 基本情報編集ページ
	 */
	@RequestMapping("/profileEdit")
	public String showProfileEdit(Model model, Integer userId, RegisterUserForm form) {
		System.out.println("profileEditパスから飛んできたuserId:" + userId);

		User user = registerUserDetailService.searchMyqloIdByUserId(userId);
		UserDetail userDetail = registerUserDetailService.showProfileEdit(userId);
		if (userDetail == null) {
			System.out.println("未登録の場合はこれ");
			return "profile";
		}
		System.out.println("profileEditパスから飛んできた時点でのuserDetailオブジェクト"+userDetail.getUserId());

		form.setImagePath(userDetail.getImagePath());
		form.setUserName(userDetail.getUserName());
		form.setGender(userDetail.getGender());
		form.setAge(userDetail.getAge());
		form.setHeight(userDetail.getHeight());
		form.setSelfIntroduction(userDetail.getSelfIntroduction());
		userDetail.setUserId(userId);

		model.addAttribute("user", user);
		model.addAttribute("userDetail", userDetail);

		return "profile";
	}

	@RequestMapping("/edit")
	public String editProfile(Model model, Integer userId, @Validated RegisterUserForm form, BindingResult result) {

		System.out.println("/editパスまできた"+userId);

		UserDetail userDetail = registerUserDetailService.searchUserDetail(userId);
		System.out.println("1");
		
		if(userDetail == null) {
			UserDetail newUserDetail = new UserDetail();
			System.out.println("2");
			
			BeanUtils.copyProperties(form, newUserDetail);
			System.out.println("3");
			
			
			newUserDetail.setUserId(userId);
			System.out.println("userにセットされた詳細IDは" + newUserDetail.getId());
			System.out.println("userにセットされたUSER IDは" + newUserDetail.getUserId());
			System.out.println("userにセットされた名前は" + form.getUserName());
			System.out.println("userにセットされた身長は" + form.getHeight());
			System.out.println("userにセットされた年齢は" + form.getAge());
			System.out.println("userにセットされた性別は" + form.getGender());
			
			registerUserDetailService.registerUserDetail(newUserDetail);
			System.out.println("4");
			
		} else {
			
			UserDetail newUserDetail = new UserDetail();
			
			if (!StringUtils.isEmpty(form.getImagePath())) {
				newUserDetail.setImagePath(form.getImagePath());
			}
			if(!StringUtils.isEmpty(form.getUserName())) {
				newUserDetail.setUserName(form.getUserName());				
			}
			if(!StringUtils.isEmpty(form.getGender())) {
				newUserDetail.setGender(form.getGender());
			}
			if(!StringUtils.isEmpty(form.getAge())) {
				newUserDetail.setAge(form.getAge());
			}
			if(!StringUtils.isEmpty(form.getHeight())) {
				newUserDetail.setHeight(form.getHeight());
			}
			if(!StringUtils.isEmpty(form.getSelfIntroduction())) {
				newUserDetail.setSelfIntroduction(form.getSelfIntroduction());
			}
				
			System.out.println("ユーザ詳細ID:"+userDetail.getId());
			newUserDetail.setId(userDetail.getId());
			
			System.out.println(newUserDetail.getUserName());
			System.out.println(newUserDetail.getGender());
			System.out.println(newUserDetail.getImagePath());
			System.out.println(newUserDetail.getHeight());
			System.out.println(newUserDetail.getAge());
			registerUserDetailService.editUserDetail(newUserDetail);
			System.out.println("5");
			
		}
		


//		if(result.hasErrors()) {
//			return showProfileEdit(model, userId, form);
//		}

		userId = userDetail.getUserId();
		System.out.println("最後！"+userId);
		
		return showProfileEdit(model, userId, form);
	}
}
