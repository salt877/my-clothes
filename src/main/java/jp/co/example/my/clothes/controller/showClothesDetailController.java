package jp.co.example.my.clothes.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.Category;
import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.Color;
import jp.co.example.my.clothes.domain.Size;
import jp.co.example.my.clothes.domain.Tag;
import jp.co.example.my.clothes.form.EditClothesForm;
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
		
		// 編集するアイテムの要素表示
		Clothes clothes = showClothesDetailService.showClothesDetail(id);
		List<Tag>tagList = showClothesDetailService.showTagList(id);
		clothes.setTagList(tagList);
		
		// 初期表示
		
		model.addAttribute("clothes", clothes);
		
		return "edit_clothes";
	}
	
	@RequestMapping("/editClothes")
	public String editClothes(Model model, EditClothesForm form) {
		return "";
	}
	
	
}
