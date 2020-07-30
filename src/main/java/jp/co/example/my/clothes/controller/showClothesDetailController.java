package jp.co.example.my.clothes.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
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
	public String editClothes(Model model, EditClothesForm form, @AuthenticationPrincipal LoginUser loginUser) {
		
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
		
		// userIdに紐づいた一番最新に登録したアイテムを取得
		Clothes registerdClothes = registerClothesService.newClothesSearchByUserId(loginUser.getUser().getId());

		// タグの登録を行う
		TagContent editTagContent = new TagContent();
		Tag tag = null;
		
		// tag1
		// タグが入力されている場合
		if(!StringUtils.isEmpty(form.getTag1())) {
			// 入力された情報が登録されているかを検索
			TagContent tagContent1 = editClothesService.tagContentSearchByName(form.getTag1());
			// もし登録されていないタグがあれば登録する　★表示がされない
			if(StringUtils.isEmpty(tagContent1)) {
				editTagContent.setId(loginUser.getUser().getId());  // ユーザIDに変更する
				editTagContent.setName(form.getTag1());
				System.out.println(editTagContent);
				editClothesService.insertTagContent(editTagContent);
				
				// 登録したタグでデータを更新する
				List<Tag>tagList = editClothesService.findATag(Integer.parseInt(form.getClothesId()), loginUser.getUser().getId());
				if(!CollectionUtils.isEmpty(tagList)) {
					tag = tagList.get(0);
					tag.setTagContentId(editTagContent.getId());
					System.out.println(tag);
					editClothesService.tagUpdate(tag);
				}
			}
		// 既にタグが登録されている場合、入力されたタグ情報を更新する
		
		
			
			
		}
		
		
		return "forward://";
	}
}
