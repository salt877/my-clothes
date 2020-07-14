package jp.co.example.my.clothes.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.Brand;
import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.TagContent;
import jp.co.example.my.clothes.service.ShowTopPageService;

@Controller
@RequestMapping("")
public class ShowTopPageController {

	@Autowired
	private ShowTopPageService showTopPageService;

	@Autowired
	private HttpSession session;

	/**
	 * ログインしているユーザの登録アイテムを全て表示します.
	 * 
	 * @param model     リクエストスコープ
	 * @param loginUser ログインユーザ
	 * @return トップページ
	 */
	@RequestMapping("/")
	public String showItemList(Model model, @AuthenticationPrincipal LoginUser loginUser) {

		Integer userId = loginUser.getUser().getId();

		List<Clothes> clothesList = showTopPageService.showItemList(userId);
		model.addAttribute("clothesList", clothesList);

		// 登録ブランド名を表示させる
		List<Brand> brandList = showTopPageService.showBrandName(userId);
		Map<Integer, String> brandMap = new LinkedHashMap<>();
		for (int i = 0; i < brandList.size(); i++) {
			Integer brandId = brandList.get(i).getId();
			String brandName = brandList.get(i).getName();
			brandMap.put(brandId, brandName);
		}
		model.addAttribute("brandMap", brandMap);

		// 登録タグ名を表示させる
		List<TagContent> tagNameList = showTopPageService.showTagName(userId);
		Map<Integer, String> tagMap = new LinkedHashMap<>();
		for (int i = 0; i < tagNameList.size(); i++) {
			Integer tagContentsId = tagNameList.get(i).getId();
			String tagContentsName = tagNameList.get(i).getName();
			System.out.println("登録されてるアイテムのあるtag_contantsテーブルのidは" + tagContentsId + "、nameは" + tagContentsName);
			tagMap.put(tagContentsId, tagContentsName);
		}
		model.addAttribute("tagMap", tagMap);
		
		return "top";
	}

	@RequestMapping("/search")
	public String showItemNarrowDown(Model model, @AuthenticationPrincipal LoginUser loginUser, Integer categoryId,
			Integer brandId, Integer tagContentsId) {

		Integer userId = loginUser.getUser().getId();

		// トップページを最初に開いた時と、「ALL」が選択されたときのアイテム表示
		if (categoryId == null || categoryId.equals(0)) {
			List<Clothes> clothesList = showTopPageService.showItemList(userId);
			model.addAttribute("clothesList", clothesList);

			System.out.println("トップページを開いた時、ブランドIDは" + brandId);
		}

		// カテゴリが選択された時
		if (categoryId != null) {
			List<Clothes> clothesList = showTopPageService.showItemListByCategory(userId, categoryId);
			model.addAttribute("clothesList", clothesList);
			System.out.println("カテゴリ選択された時、ブランドIDは" + brandId);
		}

		// ブランドが選択された時
		if (brandId != null) {
			List<Clothes> clothesList = showTopPageService.showItemListByBrand(userId, brandId);
			model.addAttribute("clothesList", clothesList);
			System.out.println("ブランド選択された時、ブランドIDは" + brandId);
		}

		// タグが選択された時
		if (tagContentsId != null) {
			List<Clothes> clothesList = showTopPageService.showItemListByTag(userId, tagContentsId);
			model.addAttribute("clothesList", clothesList);
			System.out.println("タグ選択された時、タグコンテンツIDは" + tagContentsId);
		}

		// 登録ブランド名を表示させる
		List<Brand> brandList = showTopPageService.showBrandName(userId);
		Map<Integer, String> brandMap = new LinkedHashMap<>();
		for (int i = 0; i < brandList.size(); i++) {
			Integer brandId2 = brandList.get(i).getId();
			String brandName = brandList.get(i).getName();
			brandMap.put(brandId2, brandName);
			System.out.println(brandId2 + brandName);
		}
		model.addAttribute("brandMap", brandMap);

		// 登録タグ名を表示させる
		List<TagContent> tagNameList = showTopPageService.showTagName(userId);
		Map<Integer, String> tagMap = new LinkedHashMap<>();
		for (int i = 0; i < tagNameList.size(); i++) {
			Integer tagContentsId2 = tagNameList.get(i).getId();
			String tagContentsName = tagNameList.get(i).getName();
			tagMap.put(tagContentsId2, tagContentsName);
		}
		model.addAttribute("tagMap", tagMap);

		return "top";
	}

}
