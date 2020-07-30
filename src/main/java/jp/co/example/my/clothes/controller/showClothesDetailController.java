package jp.co.example.my.clothes.controller;

import java.sql.Date;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
		} else if(clothes.getTagList().get(1).equals(null) && clothes.getTagList().get(2).equals(null)) {
			form.setTag1(clothes.getTagList().get(0).getTagContent().getName());
			form.setTag2("");
			form.setTag3("");
		} else if(clothes.getTagList().get(2).equals(null)) {
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
	

	@RequestMapping("/editClothes")
	public String editClothes(Model model, EditClothesForm form, @AuthenticationPrincipal LoginUser loginUser) {
		
		// formからオブジェクトに情報をコピー
		Clothes clothes = new Clothes();
		BeanUtils.copyProperties(form, clothes);
		
		// 上記でコピーできなかったものをコピー
		// userId
		clothes.setUserId(loginUser.getUser().getId());
		// アイテムID
		clothes.setId(Integer.parseInt(form.getClothesId()));;
		
		// 入力されたカテゴリー情報を取得
		Category category = editClothesService.categorySearchById(Integer.parseInt(form.getCategory()));
		// カテゴリー情報
		clothes.setCategory(category);
		clothes.setCategoryId(category.getId());
		
		// 入力されたカラー情報を取得
		Color color = editClothesService.ColorSearchById(Integer.parseInt(form.getColor()));
		// カラー情報
		clothes.setColor(color);
		clothes.setColorId(color.getId());
		
		// 入力されたサイズ情報を取得
		Size size = editClothesService.sizeSearchById(Integer.parseInt(form.getSize()));
		// サイズ情報
		clothes.setSize(size);
		clothes.setSizeId(size.getId());
		
		// 入力されたブランド情報を取得
		Brand brand = editClothesService.brandSearchByName(form.getBrand());
		// ブランド情報
		clothes.setBrand(brand);
		clothes.setBrandId(brand.getId());
		
		clothes.setPrice(Integer.parseInt(form.getPrice()));
		clothes.setPerchaseDate(Date.valueOf(form.getPerchaseDate()));
		
		// userIdに紐づいた一番最新に登録したアイテムを取得
//		Clothes editClothes = editClothesService.newClothesSearchByUserId(loginUser.getUser().getId());
		
		// 入力された情報があればすでにタグとして登録されているかを確認
		TagContent registerTagConetent = new TagContent();
		
		// tag1情報について
		// tagが入力されていればその情報が登録されているか検索
		if(!StringUtils.isEmpty(form.getTag1())) {
			TagContent tagContent1 = editClothesService.tagContentSearchByName(form.getTag1());
			// もし登録されていないタグであれば登録する
			if(StringUtils.isEmpty(tagContent1)) {
				registerTagConetent.setId(loginUser.getUser().getId());  // ユーザIDに変更する
				registerTagConetent.setName(form.getTag1());
				editClothesService.insertTagContent(registerTagConetent);
			}
			// tagIdとclothesIdを結び付けてデータの上書きを行う
			// 1件のタグ検索を行う
			Tag tag1 = editClothesService.findATag(loginUser.getUser().getId(),Integer.parseInt(form.getClothesId()), clothes.getTagList().get(0).getTagContentId());
			System.out.println(tag1);
			if(!(clothes.getTagList().get(0).getTagContent().getName().equals(form.getTag1()))) {
				tag1.setTagContentId(tagContent1.getId());
				System.out.println(tag1);
				editClothesService.tagUpdate(tag1);
			}
			
		}
		
		
		
		System.out.println(clothes);
		
		editClothesService.editClothes(clothes);
		
		
		return "forward://";
	}
	
	
}
