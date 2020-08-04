package jp.co.example.my.clothes.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import jp.co.example.my.clothes.domain.Brand;
import jp.co.example.my.clothes.domain.Category;
import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.Color;
import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.Size;
import jp.co.example.my.clothes.domain.Tag;
import jp.co.example.my.clothes.domain.TagContent;
import jp.co.example.my.clothes.form.RegisterClothesForm;
import jp.co.example.my.clothes.service.RegisterClothesService;

/**
 * アイテム登録画面を操作するコントローラー.
 * 
 * @author ashibe
 *
 */
@Controller
@RequestMapping("/registerClothes")
public class RegisterClothesController {
	@Autowired
	private RegisterClothesService registerClothesService;

	@ModelAttribute
	private RegisterClothesForm setUpRegisterClothesForm() {
		return new RegisterClothesForm();
	}

	/**
	 * アイテム登録画面を開く.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/showRegisterClothes")
	public String showRegisterClothes(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		// カテゴリの選択肢一覧を取得
		List<Category> categoryList = registerClothesService.showCategoryList();
		model.addAttribute("categoryList", categoryList);
		// カラーの選択肢一覧を取得
		List<Color> colorList = registerClothesService.showColorList();
		model.addAttribute("colorList", colorList);
		// サイズの選択し一覧を表示
		List<Size> sizeList = registerClothesService.showSizeList();
		model.addAttribute("sizeList", sizeList);

		// ブランドのオートコンプリート機能
		StringBuilder brandListForAutocomplete = registerClothesService.getBrandListForAutoconplete();
		model.addAttribute("brandListForAutocomplete", brandListForAutocomplete);

		// タグのオートコンプリート機能.
		StringBuilder tagContentListForAutocomplete = registerClothesService.getTagContentListForAutoconplete();
		model.addAttribute("tagContentsListForAutocomplete", tagContentListForAutocomplete);

		return "register_clothes.html";
	}

	/**
	 * 入力された情報を受け取りアイテム登録を行う.
	 * 
	 * @param form
	 * @return
	 */
	@RequestMapping("/register")
	public String Register(Model model, @Validated RegisterClothesForm form, BindingResult result,
			@AuthenticationPrincipal LoginUser loginUser) throws IOException {
		System.out.println(form);

//		// 入力必須欄に未入力項目があったら入力画面に返す.
//		if (result.hasErrors()) {
//			model.addAttribute("season", form.getSeason());
//			return showRegisterClothes(model, form);
//		}

		// 入力されたブランド情報を取得(必須)
		Brand brand = registerClothesService.brandSearchByName(form.getBrand());
		if (brand == null) {
			model.addAttribute("message", "ソートされた選択肢の中から選択してください");
			model.addAttribute("season", form.getSeason());
			// model.addAttribute("myImage",form.getImageFile());
			return showRegisterClothes(model, loginUser);
		}

		if (!Pattern.matches("^[0-9]*$", form.getPrice()) && !StringUtils.isEmpty(form.getPrice())) {
			System.out.println("数字以外が入力されています");
			return showRegisterClothes(model, loginUser);
		}

		// 入力されたカテゴリ情報を取得（必須）
		Category category = registerClothesService.categorySearchById(Integer.parseInt(form.getCategory()));

		// 入力したカラー情報を取得（必須)
		Color color = registerClothesService.ColorSearchById(Integer.parseInt(form.getColor()));

		// 入力されたサイズ情報を取得(任意)
		Size size = null;
		if (!StringUtils.isEmpty(form.getSize())) {
			size = registerClothesService.sizeSearchById(Integer.parseInt(form.getSize()));
		}
		// 取得した情報をカテゴリにセット
		Clothes clothes = new Clothes();
		// 入力必須項目
		// userId
		clothes.setUserId(loginUser.getUser().getId());

		// 画像パス（仮）
		// 画像ファイル形式チェック
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

		// エンコードした画像をセットする.
		clothes.setImagePath(base64FileString);

		// ブランド情報
		clothes.setBrand(brand);
		clothes.setBrandId(brand.getId());

		// カテゴリ情報
		clothes.setCategory(category);
		clothes.setCategoryId(category.getId());

		// カラー情報
		clothes.setColor(color);
		clothes.setColorId(color.getId());

		// 入力任意項目

		// 季節
		if (!StringUtils.isEmpty(form.getSeason())) {
			clothes.setSeason(form.getSeason());
		}

		// サイズ情報
		if (!StringUtils.isEmpty(form.getSize())) {
			clothes.setSize(size);
			clothes.setSizeId(size.getId());
		}

		// 購入日付
		if (!StringUtils.isEmpty(form.getPerchaseDate())) {
			Date sqlDate = Date.valueOf(form.getPerchaseDate());
			clothes.setPerchaseDate(sqlDate);
		}

		// 価格
		if (!StringUtils.isEmpty(form.getPrice())) {
			clothes.setPrice(Integer.parseInt(form.getPrice()));
		}

		// メモ
		if (!StringUtils.isEmpty(form.getComment())) {
			clothes.setComment(form.getComment());
		}

		// アイテム情報を登録
		System.out.println(clothes);
		registerClothesService.insertNewClothes(clothes);

		// タグ情報の登録(アイテム登録後出ないと結び付けるclothesIdが存在しない為アイテム登録後に実施)

		// userIdに紐づいた一番最新に登録したアイテムを取得
		Clothes registerdClothes = registerClothesService.newClothesSearchByUserId(loginUser.getUser().getId());

		// 入力された情報があればすでにタグとして登録されているか確認
		TagContent registerTagContent = new TagContent();
		Tag tag = null;

		// タグが入力されていればその情報が登録されているか検索
		// タグ１
		if (!StringUtils.isEmpty(form.getTag1())) {
			TagContent tagContent1 = registerClothesService.tagContentSearchByName(form.getTag1());
			// もし登録されていないタグであれば登録する
			if (StringUtils.isEmpty(tagContent1)) {
				registerTagContent.setId(loginUser.getUser().getId());// ユーザーIDに変更する.
				registerTagContent.setName(form.getTag1());
				System.out.println(registerTagContent);
				registerClothesService.insertTagContent(registerTagContent);
			}
			// タグidとclothesIdと結びつけてtagテーブルに入れる
			tag = new Tag();
			tag.setClothesId(registerdClothes.getId());
			TagContent newTagContent1 = registerClothesService.tagContentSearchByName(form.getTag1());
			tag.setTagContentId(newTagContent1.getId());
			registerClothesService.insertTag(tag);
		}

		// タグ２
		if (!StringUtils.isEmpty(form.getTag2())) {
			TagContent tagContent2 = registerClothesService.tagContentSearchByName(form.getTag2());
			if (StringUtils.isEmpty(tagContent2)) {
				registerTagContent.setId(loginUser.getUser().getId());// ユーザーIDに変更する.
				registerTagContent.setName(form.getTag2());
				registerClothesService.insertTagContent(registerTagContent);
			}
			tag = new Tag();
			tag.setClothesId(registerdClothes.getId());
			TagContent newTagContent2 = registerClothesService.tagContentSearchByName(form.getTag2());
			tag.setTagContentId(newTagContent2.getId());
			registerClothesService.insertTag(tag);

		}

		// タグ３
		if (!StringUtils.isEmpty(form.getTag3())) {
			TagContent tagContent3 = registerClothesService.tagContentSearchByName(form.getTag3());
			if (StringUtils.isEmpty(tagContent3)) {
				registerTagContent.setId(loginUser.getUser().getId());// ユーザーIDに変更する.
				registerTagContent.setName(form.getTag3());
				registerClothesService.insertTagContent(registerTagContent);
			}
			tag = new Tag();
			tag.setClothesId(registerdClothes.getId());
			TagContent newTagContent3 = registerClothesService.tagContentSearchByName(form.getTag3());
			tag.setTagContentId(newTagContent3.getId());
			registerClothesService.insertTag(tag);
		}

		return "redirect:/";

	}

	/*
	 * ファイル名から拡張子を返します.
	 * 
	 * @param originalFileName ファイル名
	 * 
	 * @return .を除いたファイルの拡張子
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
}
