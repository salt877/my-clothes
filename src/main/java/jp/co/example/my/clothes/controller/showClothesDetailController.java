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
import jp.co.example.my.clothes.form.EditClothesForm;
import jp.co.example.my.clothes.service.EditClothesService;
import jp.co.example.my.clothes.service.RegisterClothesService;
import jp.co.example.my.clothes.service.ShowClothesDetailService;

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
	
	
	@ModelAttribute
	public EditClothesForm setUpEditClothesForm() {
		return new EditClothesForm();
	}
	
	/**
	 * アイテムの詳細画面を表示するメソッドです.
	 * 
	 * @param model リクエストスコープ
	 * @param id アイテムID
	 * @return 詳細画面遷移
	 */
	@RequestMapping("")
	public String toClothesDetail(Model model, Integer id, EditClothesForm form) {
		
		// シーズンの選択肢を表示
		Map<String,String>seasonMap = new LinkedHashMap<>();
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
		StringBuilder tagContentListForAutocomplete = registerClothesService.getTagContentListForAutoconplete();
		model.addAttribute("tagContentsListForAutocomplete", tagContentListForAutocomplete);
		
		// 編集するアイテムの要素
		Clothes clothes = showClothesDetailService.showClothesDetail(id);
		List<Tag>tagList = showClothesDetailService.showTagList(id);
		clothes.setTagList(tagList);
		
		// 初期表示
		form.setCategory(Integer.toString(clothes.getCategoryId()));
		form.setBrand(clothes.getBrand().getName());
		form.setColor(Integer.toString(clothes.getColorId()));
		form.setSeason(clothes.getSeason());
		form.setSize(Integer.toString(clothes.getSizeId()));
		form.setPrice(Integer.toString(clothes.getPrice()));
		form.setComment(clothes.getComment());
		if(CollectionUtils.isEmpty(tagList)) {	// tagに何も登録されていない場合
			form.setTag1("");
			form.setTag2("");
			form.setTag3("");
		} else if(clothes.getTagList().size() == 1) {	// 要素が1つの場合 	
			form.setTag1(clothes.getTagList().get(0).getTagContent().getName());
			form.setTag2("");
			form.setTag3("");
		} else if(clothes.getTagList().size() ==2) {		// 要素が2つの場合
			form.setTag1(clothes.getTagList().get(0).getTagContent().getName());
			form.setTag2(clothes.getTagList().get(1).getTagContent().getName());
			form.setTag3("");
		} else {		// tagが3つ登録されている場合
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
	 * @param model リクエストスコープ
	 * @param form フォーム
	 * @param loginUser ユーザのログイン情報
	 * @param id アイテムID
	 * @return トップ画面に遷移
	 */
	@RequestMapping("/editClothes")
	public String editClothes(Model model, EditClothesForm form, BindingResult result, @AuthenticationPrincipal LoginUser loginUser)throws IOException {
		System.out.println(form);
		
		// 入力されたカテゴリー情報を取得(必須)
		Category category = editClothesService.categorySearchById(Integer.parseInt(form.getCategory()));
		// 入力されたカラー情報を取得(必須)
		Color color = editClothesService.ColorSearchById(Integer.parseInt(form.getColor()));
		// 入力されたブランド情報を取得(必須)
		Brand brand = editClothesService.brandSearchByName(form.getBrand());
		// 入力されたサイズ情報を取得(任意)
		Size size = null;
		if(!StringUtils.isEmpty(form.getSize())) {
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
			
			if(!"jpg".equals(fileExtension) && !"png".equals(fileExtension)) {
				result.rejectValue("imageFile", "", "拡張子は.jpgか.pngのみに対応しています");
			}
		} catch (Exception e) {
			result.rejectValue("imageFile", "", "拡張子は.jpgか.pngのみに対応しています");
		}
		// 画像ファイルをBase64形式にエンコード
		String base64FileString = Base64.getEncoder().encodeToString(imageFile.getBytes());
		if("jpg".equals(fileExtension)) {
			base64FileString = "data:image/jpeg;base64," + base64FileString;
		} else if("png".equals(fileExtension)){
			base64FileString = "data:image/png;base64," + base64FileString;
		}
		
		//エンコードした画像をセットする ★画像を選択しないまま変更を行うと画像が表示されない
		if(!StringUtils.isEmpty(form.getImageFile())) {
			clothes.setImagePath(base64FileString);
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
		if(!StringUtils.isEmpty(form.getSize())) {
			clothes.setSize(size);
			clothes.setSizeId(size.getId());
		}
		// シーズン
		if(!StringUtils.isEmpty(form.getSeason())) {
			clothes.setSeason(form.getSeason());
		}
		// 価格
		if(!StringUtils.isEmpty(form.getPrice())) {
			clothes.setPrice(Integer.parseInt(form.getPrice()));
		}
		// 購入日
		if(!StringUtils.isEmpty(form.getPerchaseDate())) {
			clothes.setPerchaseDate(Date.valueOf(form.getPerchaseDate()));
		}
		// コメント
		if(!StringUtils.isEmpty(form.getComment())) {
			clothes.setComment(form.getComment());
		}
		
		// アイテム情報を登録
		System.out.println(clothes);
		editClothesService.editClothes(clothes);
		
		// タグの登録を行う
		TagContent editTagContent = new TagContent();
		List<Tag>tagList = editClothesService.findATag(Integer.parseInt(form.getClothesId()), loginUser.getUser().getId());
		System.out.println(tagList);
		
		// tag1
		// タグが入力されている場合
		if(!StringUtils.isEmpty(form.getTag1())) {
			// 入力された情報が登録されているかを検索
			TagContent tagContent1 = editClothesService.tagContentSearchByName(form.getTag1());
			// もし登録されていないタグがあれば登録する　
			if(StringUtils.isEmpty(tagContent1)) {
				editTagContent.setName(form.getTag1());
				System.out.println(editTagContent);
				editClothesService.insertTagContent(editTagContent);
				
				// 既に登録されたタグがある場合、新規登録したタグでデータを更新する
				if(!CollectionUtils.isEmpty(tagList)) {
					Tag newTag1 = new Tag();
					newTag1.setId(tagList.get(0).getId());
					newTag1.setClothesId(tagList.get(0).getClothesId());
					newTag1.setUserId(loginUser.getUser().getId());
					TagContent getTagContent1 = editClothesService.tagContentSearchByName(editTagContent.getName());
					System.out.println(getTagContent1);
					newTag1.setTagContentId(getTagContent1.getId());
					newTag1.setTagContent(getTagContent1);
					System.out.println(newTag1);
					editClothesService.tagUpdate(newTag1);
				} else {
					// タグが登録されていない場合、新しく登録する
					// タグidとclothesIdと結びつけてtagテーブルに入れる
					Tag insertTag = new Tag();
					insertTag.setClothesId(Integer.parseInt(form.getClothesId()));
					insertTag.setUserId(loginUser.getUser().getId());
					TagContent newTagContent1 = registerClothesService.tagContentSearchByName(form.getTag1());
					insertTag.setTagContentId(newTagContent1.getId());
					insertTag.setTagContent(newTagContent1);
					System.out.println(insertTag);
					registerClothesService.insertTag(insertTag);
				}
			}
				
			// 既にタグが登録されている場合、入力されたタグ情報を更新する
			if(!CollectionUtils.isEmpty(tagList)) {
				Tag tag1 = new Tag();
				tag1.setId(tagList.get(0).getId());
				tag1.setClothesId(tagList.get(0).getClothesId());
				tag1.setUserId(loginUser.getUser().getId());
				tag1.setTagContentId(tagContent1.getId());
				tag1.setTagContent(tagContent1);
				System.out.println(tag1);
				editClothesService.tagUpdate(tag1);
			} else {
				// タグが登録されてなくて、既存のタグ情報で更新する
				Tag updateTag = new Tag();
				updateTag.setClothesId(Integer.parseInt(form.getClothesId()));
				updateTag.setUserId(loginUser.getUser().getId());
				updateTag.setTagContentId(tagContent1.getId());
				updateTag.setTagContent(tagContent1);
				System.out.println(tagContent1);
				registerClothesService.insertTag(updateTag);
			}
		}
		
		// tag2
		if(!StringUtils.isEmpty(form.getTag2())) {
			// 入力された情報が登録されているかを検索
			TagContent tagContent2 = editClothesService.tagContentSearchByName(form.getTag2());
			// もし登録されていないタグがあれば登録する
			if(StringUtils.isEmpty(tagContent2)) {
				TagContent editTagContent2 = new TagContent();
				editTagContent2.setName(form.getTag2());
				System.out.println(editTagContent2);
				editClothesService.insertTagContent(editTagContent2);
				
				// 既に登録されたタグがある場合、新規登録したタグでデータを更新する
				if(!CollectionUtils.isEmpty(tagList)) {
					Tag newTag2 = new Tag();
					newTag2.setId(tagList.get(1).getId());
					newTag2.setClothesId(tagList.get(1).getClothesId());
					newTag2.setUserId(loginUser.getUser().getId());
					TagContent getTagContent2 = editClothesService.tagContentSearchByName(editTagContent2.getName());
					System.out.println(getTagContent2);
					newTag2.setTagContentId(getTagContent2.getId());
					newTag2.setTagContent(getTagContent2);
					System.out.println(newTag2);
					editClothesService.tagUpdate(newTag2);
				} else {
					// タグが登録されていない場合、新しく登録する
					// タグidとclothesIdと結びつけてtagテーブルに入れる
					Tag insertTag2 = new Tag();
					insertTag2.setClothesId(Integer.parseInt(form.getClothesId()));
					insertTag2.setUserId(loginUser.getUser().getId());
					TagContent newTagContent2 = registerClothesService.tagContentSearchByName(form.getTag2());
					insertTag2.setTagContentId(newTagContent2.getId());
					insertTag2.setTagContent(newTagContent2);
					System.out.println(insertTag2);
					registerClothesService.insertTag(insertTag2);
				}
			}
			// 既にタグが登録されている場合、入力されたタグ情報を更新する
			if(!CollectionUtils.isEmpty(tagList)) {
				Tag tag2 = new Tag();
				tag2.setId(tagList.get(1).getId());
				tag2.setClothesId(tagList.get(1).getClothesId());
				tag2.setUserId(loginUser.getUser().getId());
				System.out.println(tagContent2);
				tag2.setTagContentId(tagContent2.getId());
				tag2.setTagContent(tagContent2);
				System.out.println(tag2);
				editClothesService.tagUpdate(tag2);
			} else {
				// タグが登録されてなくて、既存のタグ情報で更新する
				Tag updateTag2 = new Tag();
				updateTag2.setClothesId(Integer.parseInt(form.getClothesId()));
				updateTag2.setUserId(loginUser.getUser().getId());
				updateTag2.setTagContentId(tagContent2.getId());
				updateTag2.setTagContent(tagContent2);
				System.out.println(tagContent2);
				registerClothesService.insertTag(updateTag2);
			}
		}
		
		// tag3
		if(!StringUtils.isEmpty(form.getTag3())) {
			// 入力された情報が登録されているかを検索
			TagContent tagContent3 = editClothesService.tagContentSearchByName(form.getTag3());
			// もし登録されていないタグがあれば登録する
			if(StringUtils.isEmpty(tagContent3)) {
				TagContent editTagContent3 = new TagContent();
				editTagContent3.setName(form.getTag3());
				System.out.println(editTagContent3);
				editClothesService.insertTagContent(editTagContent3);
						
				// 既に登録されたタグがある場合、新規登録したタグでデータを更新する
				if(!CollectionUtils.isEmpty(tagList)) {
					Tag newTag3 = new Tag();
					newTag3.setId(tagList.get(2).getId());
					newTag3.setClothesId(tagList.get(2).getClothesId());
					newTag3.setUserId(loginUser.getUser().getId());
					TagContent getTagContent3 = editClothesService.tagContentSearchByName(editTagContent3.getName());
					System.out.println(getTagContent3);
					newTag3.setTagContentId(getTagContent3.getId());
					newTag3.setTagContent(getTagContent3);
					System.out.println(newTag3);
					editClothesService.tagUpdate(newTag3);
				} else {
					// タグが登録されていない場合、新しく登録する
					// タグidとclothesIdと結びつけてtagテーブルに入れる
					Tag insertTag3 = new Tag();
					insertTag3.setClothesId(Integer.parseInt(form.getClothesId()));
					insertTag3.setUserId(loginUser.getUser().getId());
					TagContent newTagContent3 = registerClothesService.tagContentSearchByName(form.getTag3());
					insertTag3.setTagContentId(newTagContent3.getId());
					insertTag3.setTagContent(newTagContent3);
					System.out.println(insertTag3);
					registerClothesService.insertTag(insertTag3);
				}
			}
			// 既にタグが登録されている場合、入力されたタグ情報を更新する
			if(!CollectionUtils.isEmpty(tagList)) {
				Tag tag3 = new Tag();
				tag3.setId(tagList.get(2).getId());
				tag3.setClothesId(tagList.get(2).getClothesId());
				tag3.setUserId(loginUser.getUser().getId());
				System.out.println(tagContent3);
				tag3.setTagContentId(tagContent3.getId());
				tag3.setTagContent(tagContent3);
				System.out.println(tag3);
				editClothesService.tagUpdate(tag3);
			} else {
				// タグが登録されてなくて、既存のタグ情報で更新する
				Tag updateTag3 = new Tag();
				updateTag3.setClothesId(Integer.parseInt(form.getClothesId()));
				updateTag3.setUserId(loginUser.getUser().getId());
				updateTag3.setTagContentId(tagContent3.getId());
				updateTag3.setTagContent(tagContent3);
				System.out.println(tagContent3);
				registerClothesService.insertTag(updateTag3);
			}
		}
		return "forward://";
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
