package jp.co.example.my.clothes.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
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
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.form.EditClothesForm;
import jp.co.example.my.clothes.repository.TagRepository;
import jp.co.example.my.clothes.service.EditClothesService;
import jp.co.example.my.clothes.service.RegisterClothesService;
import jp.co.example.my.clothes.service.ShowClothesDetailService;
import jp.co.example.my.clothes.service.ShowUserNameService;

/**
 * アイテムの詳細画面の表示・編集・削除を行うコントローラクラスです.
 * 
 * @author mizuki.tanimori
 *
 */
@Controller
@RequestMapping("/showDetail")
public class showClothesDetailController {

	@Autowired
	private ShowClothesDetailService showClothesDetailService;

	@Autowired
	private RegisterClothesService registerClothesService;

	@Autowired
	private EditClothesService editClothesService;
	
	@Autowired
	private ShowUserNameService showUserNameService;

	@ModelAttribute
	public EditClothesForm setUpEditClothesForm() {
		return new EditClothesForm();
	}

	/**
	 * アイテムの詳細画面を表示するメソッドです.
	 * 
	 * @param model リクエストスコープ
	 * @param id    アイテムID
	 * @return 詳細画面遷移
	 */
	@RequestMapping("")
	public String toClothesDetail(Model model, Integer id, EditClothesForm form,
			@AuthenticationPrincipal LoginUser loginUser) {

		Integer userId = loginUser.getUser().getId();
		
		UserDetail userDetail = showUserNameService.showUserName(userId);
		model.addAttribute("userDetail", userDetail);
		
		// シーズンの選択肢を表示
		Map<String, String> seasonMap = new LinkedHashMap<>();
		seasonMap.put("春", "春");
		seasonMap.put("夏", "夏");
		seasonMap.put("秋", "秋");
		seasonMap.put("冬", "冬");
		model.addAttribute("seasonMap", seasonMap);

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
		StringBuilder tagContentListForAutocomplete = registerClothesService
				.getTagContentListForAutoconplete(loginUser.getUser().getId());
		model.addAttribute("tagContentsListForAutocomplete", tagContentListForAutocomplete);

		// 編集するアイテムの要素
		Clothes clothes = showClothesDetailService.showClothesDetail(id, loginUser.getUser().getId());
		List<Tag> tagList = showClothesDetailService.showTagList(id);
		clothes.setTagList(tagList);

		// 初期表示
		form.setCategory(Integer.toString(clothes.getCategoryId()));
		form.setBrand(clothes.getBrand().getName());
		form.setColor(Integer.toString(clothes.getColorId()));
		form.setSeason(clothes.getSeason());
		form.setSize(Integer.toString(clothes.getSizeId()));
		form.setPrice(Integer.toString(clothes.getPrice()));
		form.setComment(clothes.getComment());
		if (CollectionUtils.isEmpty(tagList)) { // tagに何も登録されていない場合
			form.setTag1("");
			form.setTag2("");
			form.setTag3("");
		} else if (clothes.getTagList().size() == 1) { // 要素が1つの場合
			form.setTag1(clothes.getTagList().get(0).getTagContent().getName());
			form.setTag2("");
			form.setTag3("");
		} else if (clothes.getTagList().size() == 2) { // 要素が2つの場合
			form.setTag1(clothes.getTagList().get(0).getTagContent().getName());
			form.setTag2(clothes.getTagList().get(1).getTagContent().getName());
			form.setTag3("");
		} else { // tagが3つ登録されている場合
			form.setTag1(clothes.getTagList().get(0).getTagContent().getName());
			form.setTag2(clothes.getTagList().get(1).getTagContent().getName());
			form.setTag3(clothes.getTagList().get(2).getTagContent().getName());
		}

		model.addAttribute("clothes", clothes);

		return "edit_clothes";
	}

	/**
	 * アイテム情報の編集を行うメソッドです.
	 * 
	 * @param model     リクエストスコープ
	 * @param form      フォーム
	 * @param loginUser ユーザのログイン情報
	 * @param id        アイテムID
	 * @return トップ画面に遷移
	 */
	@RequestMapping("/editClothes")
	public String editClothes(Integer id, Model model, @Validated EditClothesForm form, BindingResult result,
			@AuthenticationPrincipal LoginUser loginUser) throws IOException {

		// 入力されたカテゴリー情報を取得(必須)
		Category category = editClothesService.categorySearchById(Integer.parseInt(form.getCategory()));
		// 入力されたカラー情報を取得(必須)
		Color color = editClothesService.ColorSearchById(Integer.parseInt(form.getColor()));
		// 入力されたブランド情報を取得(必須)
		Brand brand = editClothesService.brandSearchByName(form.getBrand());
		// ブランドの入力がされていなければ入力画面へ戻る
		if (result.hasErrors()) {
			return toClothesDetail(model, Integer.parseInt(form.getClothesId()), form, loginUser);
		}
		// 入力されたサイズ情報を取得(任意)
		Size size = null;
		if (!StringUtils.isEmpty(form.getSize())) {
			size = editClothesService.sizeSearchById(Integer.parseInt(form.getSize()));
		}

		// 取得した情報をclothesオブジェクトにセット
		Clothes clothes = new Clothes();

		// 必須項目
		// userId
		clothes.setUserId(loginUser.getUser().getId());
		// アイテムID
		clothes.setId(Integer.parseInt(form.getClothesId()));

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

		// エンコードした画像をセットして変更する
		if (!form.getImageFile().isEmpty()) {
			clothes.setImagePath(base64FileString);
		} else {
			// 画像の変更を行わない場合
			Clothes oldClothes = showClothesDetailService.showClothesDetail(Integer.parseInt(form.getClothesId()),
					loginUser.getUser().getId());
			clothes.setImagePath(oldClothes.getImagePath());
		}

		// カテゴリー情報
		clothes.setCategory(category);
		clothes.setCategoryId(category.getId());

		// カラー情報
		clothes.setColor(color);
		clothes.setColorId(color.getId());

		// ブランド情報
		clothes.setBrand(brand);
		clothes.setBrandId(brand.getId());

		// 任意項目
		// サイズ情報
		if (!StringUtils.isEmpty(form.getSize())) {
			clothes.setSize(size);
			clothes.setSizeId(size.getId());
		}
		// シーズン
		if (!StringUtils.isEmpty(form.getSeason())) {
			clothes.setSeason(form.getSeason());
		}
		// 価格
		if (!StringUtils.isEmpty(form.getPrice())) {
			clothes.setPrice(Integer.parseInt(form.getPrice()));
		}
		// 購入日
		if (!StringUtils.isEmpty(form.getPerchaseDate())) {
			clothes.setPerchaseDate(Date.valueOf(form.getPerchaseDate()));
		}
		// コメント
		if (!StringUtils.isEmpty(form.getComment())) {
			clothes.setComment(form.getComment());
		}

		// アイテム情報を登録
		editClothesService.editClothes(clothes);

		// タグの登録を行う
		TagContent editTagContent = new TagContent();
		List<Tag> tagList = editClothesService.findATag(Integer.parseInt(form.getClothesId()),
				loginUser.getUser().getId());
		Tag tag = null;

		// tag1
		// タグが入力されている場合
		if (!StringUtils.isEmpty(form.getTag1())) {
			// 入力された情報が登録されているかを検索
			TagContent tagContent1 = editClothesService.tagContentSearchByName(form.getTag1());
			// もし登録されていないタグがあれば登録する
			if (StringUtils.isEmpty(tagContent1)) {
				editTagContent.setName(form.getTag1());
				editTagContent.setUserId(loginUser.getUser().getId());
				editClothesService.insertTagContent(editTagContent);

				// 既に登録されたタグがある場合、新規登録したタグでデータを更新する
				if (!CollectionUtils.isEmpty(tagList)) {
					tag = tagList.get(0);
					TagContent getTagContent1 = editClothesService.tagContentSearchByName(editTagContent.getName());
					tag.setTagContentId(getTagContent1.getId());
					tag.setTagContent(getTagContent1);
					editClothesService.tagUpdate(tag);
				} else {
					// タグが登録されていない場合、新しく登録する
					// タグidとclothesIdと結びつけてtagテーブルに入れる
					tag = new Tag();
					tag.setClothesId(Integer.parseInt(form.getClothesId()));
					tag.setUserId(loginUser.getUser().getId());
					TagContent newTagContent1 = registerClothesService.tagContentSearchByName(form.getTag1());
					tag.setTagContentId(newTagContent1.getId());
					tag.setTagContent(newTagContent1);
					registerClothesService.insertTag(tag);
				}
			}
			// 既にタグが登録されている場合、入力されたタグ情報を更新する
			if (!CollectionUtils.isEmpty(tagList) && tagContent1 != null) {
				tag = tagList.get(0);
				tag.setTagContentId(tagContent1.getId());
				tag.setTagContent(tagContent1);
				editClothesService.tagUpdate(tag);
			} else if (CollectionUtils.isEmpty(tagList) && tagContent1 != null) {
				// タグが登録されてなくて、既存のタグ情報で更新する
				tag = new Tag();
				tag.setClothesId(Integer.parseInt(form.getClothesId()));
				tag.setUserId(loginUser.getUser().getId());
				tag.setTagContentId(tagContent1.getId());
				tag.setTagContent(tagContent1);
				registerClothesService.insertTag(tag);
			}
		} else if (StringUtils.isEmpty(form.getTag1()) && !CollectionUtils.isEmpty(tagList)) {
			// タグが入力されていない場合は、タグの削除を行う
			tag = tagList.get(0);
			editClothesService.delete(tag);
		}

		// tag2
		// タグが入力されている場合
		if (!StringUtils.isEmpty(form.getTag2())) {
			// 入力された情報が登録されているかを検索
			TagContent tagContent2 = editClothesService.tagContentSearchByName(form.getTag2());
			// もし登録されていないタグがあれば登録する
			if (StringUtils.isEmpty(tagContent2)) {
				TagContent editTagContent2 = new TagContent();
				editTagContent2.setName(form.getTag2());
				editTagContent2.setUserId(loginUser.getUser().getId());
				editClothesService.insertTagContent(editTagContent2);

				// 既に登録されたタグがある場合、新規登録したタグでデータを更新する
				if (tagList.size() >= 2) {
					tag = tagList.get(1);
					TagContent getTagContent2 = editClothesService.tagContentSearchByName(editTagContent2.getName());
					tag.setTagContentId(getTagContent2.getId());
					tag.setTagContent(getTagContent2);
					editClothesService.tagUpdate(tag);
				} else if (tagList.size() == 1 || CollectionUtils.isEmpty(tagList)) {
					// タグ2が登録されていない場合またはタグ1が登録されていない場合、新規登録したタグを新たに登録する
					// タグidとclothesIdと結びつけてtagテーブルに入れる
					tag = new Tag();
					tag.setClothesId(Integer.parseInt(form.getClothesId()));
					tag.setUserId(loginUser.getUser().getId());
					TagContent newTagContent2 = registerClothesService.tagContentSearchByName(form.getTag2());
					tag.setTagContentId(newTagContent2.getId());
					tag.setTagContent(newTagContent2);
					registerClothesService.insertTag(tag);
					tagList.add(tag);
				}
			}
			// 既にタグが登録されている場合、入力されたタグ情報を更新する
			if (tagContent2 != null) {
				if (tagList.size() >= 2) {
					tag = tagList.get(1);
					tag.setTagContentId(tagContent2.getId());
					tag.setTagContent(tagContent2);
					editClothesService.tagUpdate(tag);
				} else if (tagList.size() == 1 || CollectionUtils.isEmpty(tagList)) {
					// タグ2が登録されていないまたはタグ１が登録されていない場合、既存のタグ情報をインサートする
					tag = new Tag();
					tag.setClothesId(Integer.parseInt(form.getClothesId()));
					tag.setUserId(loginUser.getUser().getId());
					tag.setTagContentId(tagContent2.getId());
					tag.setTagContent(tagContent2);
					registerClothesService.insertTag(tag);
				}
			}
		} else if (StringUtils.isEmpty(form.getTag2())) {
			// タグが入力されていない場合
			if (tagList.size() == 2 || tagList.size() == 3) {
				// タグ2が登録されている場合、タグの削除を行う
				tag = tagList.get(1);
				editClothesService.delete(tag);
			}
		}

		// tag3
		if (!StringUtils.isEmpty(form.getTag3())) {
			// 入力された情報が登録されているかを検索
			TagContent tagContent3 = editClothesService.tagContentSearchByName(form.getTag3());
			// もし登録されていないタグがあれば登録する
			if (StringUtils.isEmpty(tagContent3)) {
				TagContent editTagContent3 = new TagContent();
				editTagContent3.setName(form.getTag3());
				editTagContent3.setUserId(loginUser.getUser().getId());
				editClothesService.insertTagContent(editTagContent3);

				// 既に登録されたタグがある場合、新規登録したタグでデータを更新する
				if (tagList.size() == 3) {
					tag = tagList.get(2);
					TagContent getTagContent3 = editClothesService.tagContentSearchByName(editTagContent3.getName());
					tag.setTagContentId(getTagContent3.getId());
					tag.setTagContent(getTagContent3);
					editClothesService.tagUpdate(tag);
				} else if (tagList.size() <= 2 || CollectionUtils.isEmpty(tagList)) {
					// タグ3が登録されていないまたはタグリストが登録されていない場合、新規登録したタグを新たに登録する
					// タグidとclothesIdと結びつけてtagテーブルに入れる
					tag = new Tag();
					tag.setClothesId(Integer.parseInt(form.getClothesId()));
					tag.setUserId(loginUser.getUser().getId());
					TagContent newTagContent3 = registerClothesService.tagContentSearchByName(form.getTag3());
					tag.setTagContentId(newTagContent3.getId());
					tag.setTagContent(newTagContent3);
					registerClothesService.insertTag(tag);
					tagList.add(tag);
				}
			}
			// 既にタグが登録されている場合、入力されたタグ情報を更新する
			if (tagContent3 != null) {
				if (tagList.size() == 3) {
					tag = tagList.get(2);
					tag.setTagContentId(tagContent3.getId());
					tag.setTagContent(tagContent3);
					editClothesService.tagUpdate(tag);
				} else if (tagList.size() == 1 || tagList.size() == 2 || CollectionUtils.isEmpty(tagList)) {
					// タグが登録されてなくて、既存のタグ情報で更新する
					tag = new Tag();
					tag.setClothesId(Integer.parseInt(form.getClothesId()));
					tag.setUserId(loginUser.getUser().getId());
					tag.setTagContentId(tagContent3.getId());
					tag.setTagContent(tagContent3);
					registerClothesService.insertTag(tag);
				}
			}
		} else if (StringUtils.isEmpty(form.getTag3())) {
			// タグが入力されていない場合
			if (tagList.size() == 3) {
				// タグ2が登録されている場合、タグの削除を行う
				tag = tagList.get(2);
				editClothesService.delete(tag);
			}
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
