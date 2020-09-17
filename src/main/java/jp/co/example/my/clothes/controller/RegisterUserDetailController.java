package jp.co.example.my.clothes.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.form.RegisterUserDetailForm;
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
	public RegisterUserDetailForm setUpForm() {
		return new RegisterUserDetailForm();
	}

	/**
	 * ユーザの基本情報登録ページを表示します.
	 * 
	 * @return 基本情報編集ページ
	 * @throws IOException
	 */
	@RequestMapping("/profileEdit")
	public String showProfileEdit(Model model, @RequestParam(value = "userId", required = true) Integer userId,
			RegisterUserDetailForm form, BindingResult result) throws IOException {

		System.out.println("profileEditパスから飛んできたuserId:" + userId);

		User user = registerUserDetailService.searchMyqloIdByUserId(userId);
		UserDetail userDetail = registerUserDetailService.showProfileEdit(userId);

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

	/**
	 * ファイル名から拡張子を返します.
	 * 
	 * @param originalFileName ファイル名
	 * @return .を除いたファイルの拡張子
	 * @throws Exception
	 */
	private String getExtension(String originalFileName) throws Exception {
		if (originalFileName == null) {
			throw new FileNotFoundException();
		}
		int point = originalFileName.lastIndexOf(".");
		if (point == -1) {
			throw new FileNotFoundException();
		}
		return originalFileName.substring(point + 1);
	}

	/**
	 * 編集内容を保存します.
	 * 
	 * @param model リクエストスコープ
	 * @param userId　ユーザID
	 * @param form　フォーム
	 * @param result　エラー格納用オブジェクト
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/edit")
	public String editProfile(Model model, Integer userId, @Validated RegisterUserDetailForm form, BindingResult result)
			throws Exception {

		if (result.hasErrors()) {
			return showProfileEdit(model, userId, form, result);
		}

		UserDetail userDetail = registerUserDetailService.searchUserDetail(userId);

		MultipartFile imageFile = form.getImageFile();
		System.out.println(imageFile);
		String fileExtension = null;

		try {
			fileExtension = getExtension(imageFile.getOriginalFilename());

			if (!"jpg".equals(fileExtension) && !"png".equals(fileExtension)) {
				result.rejectValue("imageFile", "", "拡張子は.jpgか.pngのみに対応しています");
			}
			// 画像ファイルをBase64形式にエンコード
			String base64FileString = Base64.getEncoder().encodeToString(imageFile.getBytes());
			if ("jpg".equals(fileExtension)) {
				base64FileString = "data:image/jpeg;base64," + base64FileString;
			} else if ("png".equals(fileExtension)) {
				base64FileString = "data:image/png;base64," + base64FileString;
			}
			
			// エンコードした画像をセットする
			if (!form.getImageFile().isEmpty()) {
				form.setImagePath(base64FileString);
				// 画像の変更を行わない場合
			} else {
				UserDetail oldUserDetail = registerUserDetailService.searchUserDetail(userId);
				form.setImagePath(oldUserDetail.getImagePath());
			}
		} catch (NullPointerException | FileNotFoundException ex) {
			System.err.print(ex);
			result.rejectValue("imageFile", "", "拡張子は.jpgか.pngのみに対応しています");
			form.setImagePath(null);
			
		} 

		UserDetail newUserDetail = new UserDetail();

		if (!StringUtils.isEmpty(form.getImagePath())) {
			newUserDetail.setImagePath(form.getImagePath());
		}

		if (!StringUtils.isEmpty(form.getUserName())) {
			newUserDetail.setUserName(form.getUserName());
		}
		if (!StringUtils.isEmpty(form.getGender())) {
			newUserDetail.setGender(form.getGender());
		}
		if (!StringUtils.isEmpty(form.getAge())) {
			newUserDetail.setAge(form.getAge());
		}
		if (!StringUtils.isEmpty(form.getHeight())) {
			newUserDetail.setHeight(form.getHeight());
		}
		if (!StringUtils.isEmpty(form.getSelfIntroduction())) {
			newUserDetail.setSelfIntroduction(form.getSelfIntroduction());
		}

		newUserDetail.setId(userDetail.getId());

		registerUserDetailService.editUserDetail(newUserDetail);

		System.out.println("編集完了！");

		return showProfileEdit(model, userId, form, result);
	}
}
