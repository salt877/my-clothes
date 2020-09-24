package jp.co.example.my.clothes.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value="/profileEdit",method=RequestMethod.GET)
	public String showProfileEdit(Model model, @RequestParam(value = "userId", required = true) Integer userId,
			RegisterUserDetailForm form, BindingResult result) throws IOException {

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

		System.out.println("アイコン:" + form.getImagePath());

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
	 * @param model  リクエストスコープ
	 * @param userId ユーザID
	 * @param form   フォーム
	 * @param result エラー格納用オブジェクト
	 * @return プロフィール画面
	 * @throws Exception
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String editProfile(Model model, Integer userId, @Validated RegisterUserDetailForm form, BindingResult result)
			throws Exception {

		if (result.hasErrors()) {
			return showProfileEdit(model, userId, form, result);
		}

		UserDetail userDetail = registerUserDetailService.searchUserDetail(userId);
		MultipartFile imageFile = form.getImageFile();
		String fileExtension = null;

		System.out.println("イメージファイル:" + imageFile);

		// ①すでにアイコンが登録されている場合
		if (userDetail.getImagePath() != null) {

			System.out.println("アイコンは登録されてました");

			Path path = Paths.get(
					"src/main/resources/static/profile_img/");

			try {
				// ①-1.別のアイコンに変更したい場合
				if (!imageFile.isEmpty()) {
					Files.createDirectory(path);
					System.out.println("新しくディレクトリができた");

					int dot = form.getImageFile().getOriginalFilename().lastIndexOf(".");
					String extention = "";
					if (dot > 0) {
						extention = form.getImageFile().getOriginalFilename().substring(dot).toLowerCase();
						String filename = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
						Path uploadfile = Paths.get(
								"src/main/resources/static/profile_img/"
										+ filename + extention);

						try (OutputStream os = Files.newOutputStream(uploadfile, StandardOpenOption.CREATE)) {
							byte[] bytes = form.getImageFile().getBytes();
							os.write(bytes);
							form.setImagePath(filename + extention);
							System.out.println("この画像に変更します:" + filename + extention);
						} catch (IOException ex) {
							System.err.println(ex);
						}

					}

					// ①-2.アイコンを変更しない場合
				} else if (imageFile.isEmpty()) {
					form.setImagePath(userDetail.getImagePath());
					System.out.println("画像の変更がないので何もしない");
				}

			} catch (NullPointerException ex) {
				System.err.println("NoSuchFileException:" + ex);
				form.setImagePath(null);
				
			} 
				catch (IOException ex) {
				System.err.println("IOException:" + ex);
			}

			// ②アイコンが登録されていない場合
		} else if (userDetail.getImagePath() == null) {
			System.out.println("アイコンは登録されてませんでした");

			Path path = Paths.get(
					"src/main/resources/static/profile_img/");

			System.out.println(path);
			try {
				Files.createDirectory(path);
				System.out.println("ここ");

				int dot = form.getImageFile().getOriginalFilename().lastIndexOf(".");
				String extention = "";
				if (dot > 0) {
					extention = form.getImageFile().getOriginalFilename().substring(dot).toLowerCase();
				}
				String filename = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
				Path uploadfile = Paths.get(
						"src/main/resources/static/profile_img/"
								+ filename + extention);

				System.out.println("ここまできた");
				
				try (OutputStream os = Files.newOutputStream(uploadfile, StandardOpenOption.CREATE)) {
					byte[] bytes = form.getImageFile().getBytes();
					os.write(bytes);
					form.setImagePath(filename + extention);
					System.out.println("新しく登録したい" + filename + extention);

					// そのまま何も登録しない場合
				} catch (IOException | NullPointerException ex) {
					System.err.println(ex);
					form.setImagePath(null);
					System.out.println("そのまま何も登録しない");
				}

			} catch (NullPointerException ex) {
				System.err.println(ex);
				System.out.println("これ〜");

			} 
		}
//			catch (IOException ex) {
//				System.err.println(ex);
//			System.out.println("あれ〜");
//			}
//			

//		MultipartFile imageFile = form.getImageFile();
//		String fileExtension = null;
//
//		// すでにアイコンが登録されている場合
//		if (userDetail.getImagePath() != null) {
//
//			try {
//				if (!imageFile.isEmpty()) {
//					fileExtension = getExtension(imageFile.getOriginalFilename());
//				} else if (imageFile.isEmpty()) {
//					form.setImagePath(userDetail.getImagePath());
//				}
//
//				if (!"jpg".equals(fileExtension) && !"png".equals(fileExtension)) {
//					result.rejectValue("imageFile", "", "拡張子は.jpgか.pngのみに対応しています");
//				}
//				// 画像ファイルをBase64形式にエンコード
//				String base64FileString = Base64.getEncoder().encodeToString(imageFile.getBytes());
//				if ("jpg".equals(fileExtension)) {
//					base64FileString = "data:image/jpeg;base64," + base64FileString;
//				} else if ("png".equals(fileExtension)) {
//					base64FileString = "data:image/png;base64," + base64FileString;
//				}
//				// エンコードした画像をセットする
//				if (!form.getImageFile().isEmpty()) {
//					form.setImagePath(base64FileString);
//					// 画像の変更を行わない場合
//				} else if (form.getImageFile().isEmpty()) {
//					UserDetail oldUserDetail = registerUserDetailService.searchUserDetail(userId);
//					form.setImagePath(oldUserDetail.getImagePath());
//				}
//
//				// 削除した場合と形式が異なる場合、以下の例外処理に飛ぶ
//			} catch (NullPointerException | FileNotFoundException ex) {
//
//				System.err.print(ex);
//				result.rejectValue("imageFile", "", "拡張子は.jpgか.pngのみに対応しています");
//
//				form.setImagePath(null);
//
//			}
//
//			// アイコンを登録していない場合
//		} else if (userDetail.getImagePath() == null) {
//
//			try {
//				fileExtension = getExtension(imageFile.getOriginalFilename());
//
//				if (!"jpg".equals(fileExtension) && !"png".equals(fileExtension)) {
//					result.rejectValue("imageFile", "", "拡張子は.jpgか.pngのみに対応しています");
//				}
//				// 画像ファイルをBase64形式にエンコード
//				String base64FileString = Base64.getEncoder().encodeToString(imageFile.getBytes());
//				if ("jpg".equals(fileExtension)) {
//					base64FileString = "data:image/jpeg;base64," + base64FileString;
//				} else if ("png".equals(fileExtension)) {
//					base64FileString = "data:image/png;base64," + base64FileString;
//				}
//				// エンコードした画像をセットする
//				if (!form.getImageFile().isEmpty()) {
//					form.setImagePath(base64FileString);
//					// 画像の変更を行わない場合
//				} else {
//					UserDetail oldUserDetail = registerUserDetailService.searchUserDetail(userId);
//					form.setImagePath(oldUserDetail.getImagePath());
//				}
//
//				// 画像を変更しない場合と形式が異なる場合、以下の例外処理に飛ぶ
//			} catch (NullPointerException | FileNotFoundException ex) {
//
//				System.err.print(ex);
//				result.rejectValue("imageFile", "", "拡張子は.jpgか.pngのみに対応しています");
//
//				form.setImagePath(null);
//			}
//
//		}

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

			return showProfileEdit(model, userId, form, result);
		}
	}

