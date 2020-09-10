package jp.co.example.my.clothes.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.BeanUtils;
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
	 * @throws IOException
	 */
	@RequestMapping("/profileEdit")
	public String showProfileEdit(Model model, @RequestParam(value = "userId", required = true) Integer userId,
			RegisterUserForm form, BindingResult result) throws IOException {
		System.out.println("profileEditパスから飛んできたuserId:" + userId);

		User user = registerUserDetailService.searchMyqloIdByUserId(userId);
		UserDetail userDetail = registerUserDetailService.showProfileEdit(userId);
		if (userDetail.getUserId() == null) {
			System.out.println("未登録の場合はここ");
			System.out.println("MYQLO ID:" + user.getMyqloId() + "USER ID" + user.getId());
			model.addAttribute("user", user);
			model.addAttribute("userDetail", userDetail);
			return "profile";
		}
		System.out.println("profileEditパスから飛んできた時点でのuserDetailオブジェクト" + userDetail.getUserId());

//		MultipartFile imageFile = form.getImageFile();
//		String fileExtension = null;
//
//		try {
//			fileExtension = getExtension(imageFile.getOriginalFilename());
//
//			if (!"jpg".equals(fileExtension) && !"png".equals(fileExtension)) {
//				result.rejectValue("imageFile", "", "拡張子は.jpgか.pngのみに対応しています");
//			}
//		} catch (Exception e) {
//			result.rejectValue("imageFile", "", "拡張子は.jpgか.pngのみに対応しています");
//		}
//		// 画像ファイルをBase64形式にエンコード
//		String base64FileString = Base64.getEncoder().encodeToString(imageFile.getBytes());
//		if ("jpg".equals(fileExtension)) {
//			base64FileString = "data:image/jpeg;base64," + base64FileString;
//		} else if ("png".equals(fileExtension)) {
//			base64FileString = "data:image/png;base64," + base64FileString;
//		}
//
//		// エンコードした画像をセットする
//		form.setImagePath(base64FileString);

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

	@RequestMapping("/edit")
	public String editProfile(Model model, Integer userId, @Validated RegisterUserForm form, BindingResult result)
			throws IOException {

		System.out.println("/editパスまできた" + userId);

		UserDetail userDetail = registerUserDetailService.searchUserDetail(userId);
		System.out.println("1");

		//ユーザ詳細情報未登録の時（初回登録時）
		if (userDetail == null) {
			UserDetail newUserDetail = new UserDetail();
			System.out.println("2");

			MultipartFile imageFile = form.getImageFile();
			String fileExtension = null;

			try {
				fileExtension = getExtension(imageFile.getOriginalFilename());

				if (!"jpg".equals(fileExtension) && !"png".equals(fileExtension)) {
					result.rejectValue("imageFile", "", "拡張子は.jpgか.pngのみに対応しています");
				}
			} catch (Exception e) {
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
			form.setImagePath(base64FileString);

			BeanUtils.copyProperties(form, newUserDetail);
			System.out.println("3");

			newUserDetail.setUserId(userId);
			System.out.println("userにセットされた詳細IDは" + newUserDetail.getId());
			System.out.println("userにセットされたUSER IDは" + newUserDetail.getUserId());
			System.out.println("userにセットされた名前は" + form.getUserName());
			System.out.println("userにセットされた身長は" + form.getHeight());
			System.out.println("userにセットされた年齢は" + form.getAge());
			System.out.println("userにセットされた性別は" + form.getGender());
			System.out.println("userにセットされた写真は" + form.getImagePath());

			registerUserDetailService.registerUserDetail(newUserDetail);
			System.out.println("初回の詳細情報登録が完了しuser_detailsにインサートされている状態");

			//初回登録ではなく、情報を上書き更新
		} else {

			MultipartFile imageFile = form.getImageFile();
			String fileExtension = null;

			try {
				fileExtension = getExtension(imageFile.getOriginalFilename());

				if (!"jpg".equals(fileExtension) && !"png".equals(fileExtension)) {
					result.rejectValue("imageFile", "", "拡張子は.jpgか.pngのみに対応しています");
				}
			} catch (Exception e) {
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
			if(!form.getImageFile().isEmpty()) {
				form.setImagePath(base64FileString);	
			//画像の変更を行わない場合
			} else {
				UserDetail oldUserDetail = registerUserDetailService.searchUserDetail(userId);
				form.setImagePath(oldUserDetail.getImagePath());
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

			System.out.println("ユーザ詳細ID:" + userDetail.getId());
			newUserDetail.setId(userDetail.getId());

			System.out.println(newUserDetail.getUserName());
			System.out.println(newUserDetail.getImagePath());
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
		System.out.println("最後！" + userId);

		return showProfileEdit(model, userId, form, result);
	}
}
