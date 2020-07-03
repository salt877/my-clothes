package jp.co.example.my.clothes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.Category;
import jp.co.example.my.clothes.domain.Color;
import jp.co.example.my.clothes.domain.Size;
import jp.co.example.my.clothes.form.RegisterClothesForm;
import jp.co.example.my.clothes.service.RegisterClothesService;

@Controller
@RequestMapping("/registerClothes")
public class RegisterClothesController {
	@Autowired
	private RegisterClothesService registerClothesService;

	@ModelAttribute
	private RegisterClothesForm setUpRegisterClothesForm() {
		return new RegisterClothesForm();
	}

	@RequestMapping("/showRegisterClothes")
	public String showRegisterClothes(Model model) {
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
		StringBuilder brandListForAutocomplete = registerClothesService.getItemListForAutoconplete();
		model.addAttribute("brandListForAutocomplete", brandListForAutocomplete);
System.out.println(brandListForAutocomplete);
		return "register_clothes.html";
	}

	@RequestMapping("/register")
	public String Register(RegisterClothesForm form) {
		System.out.println(form);
		return "register_clothes.html";

	}
}
