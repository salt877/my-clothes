package jp.co.example.my.clothes.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.Brand;
import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.TagContent;
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.domain.Weather;
import jp.co.example.my.clothes.service.ShowTopPageService;
import jp.co.example.my.clothes.service.ShowUserNameService;
import jp.co.example.my.clothes.service.WeatherService;

/**
 * topページを操作するコントローラー.
 * 
 * @author ashibe
 *
 */
@Controller
@RequestMapping("")
public class ShowTopPageController {

	@Autowired
	private ShowTopPageService showTopPageService;

	@Autowired
	private ShowUserNameService showUserNameService;
	
	@Autowired
	private HttpSession session;

	@Autowired
	private WeatherService weatherService;

	/**
	 * ログインしているユーザの登録アイテムを全て表示します.
	 * 
	 * @param model     リクエストスコープ
	 * @param loginUser ログインユーザ
	 * @return トップページ
	 */
	@RequestMapping("/")
	public String showItemList(Model model, @AuthenticationPrincipal LoginUser loginUser) {

		session.removeAttribute("categoryId");
		session.removeAttribute("showBrandName");
		session.removeAttribute("showTagName");

		Integer userId = loginUser.getUser().getId();

		UserDetail userDetail = showUserNameService.showUserName(userId);
		model.addAttribute("userDetail", userDetail);
		
		List<Clothes> clothesList = showTopPageService.showItemList(userId);
		model.addAttribute("clothesList", clothesList);

		// 天気予報情報の表示
		weatherService.cityFindByUserId(userId);
		Weather weather = weatherService.cityFindByUserId(userId);

		if (StringUtils.isEmpty(weather)) {
			model.addAttribute("city", "東京都");
		} else {
			model.addAttribute("city", weather.getCityName());
		}

		// 登録ブランド名を表示するメソッドを呼び出し
		Map<Integer, String> brandMap = showBrandName(userId);
		model.addAttribute("brandMap", brandMap);

		// 登録タグ名を表示するメソッドを呼び出し
		Map<Integer, String> tagMap = showTagName(userId);
		model.addAttribute("tagMap", tagMap);

		return "top";
	}

	/**
	 * アイテムを絞り込み検索します.
	 * 
	 * @param model
	 * @param loginUser
	 * @param categoryId
	 * @param brandId
	 * @param tagContentsId
	 * @return
	 */
	@RequestMapping("/search")
	public String showItemNarrowDown(Model model, @AuthenticationPrincipal LoginUser loginUser, Integer categoryId,
			Integer brandId, Integer tagContentsId, String showBrandName) {

		session.removeAttribute("categoryId");
		session.removeAttribute("showBrandName");
		session.removeAttribute("showTagName");

		Integer userId = loginUser.getUser().getId();
		
		UserDetail userDetail = showUserNameService.showUserName(userId);
		model.addAttribute("userDetail", userDetail);

		// 天気予報情報の表示
		// weatherService.cityFindByUserId(loginUser.getUser().getId());
		Weather weather = weatherService.cityFindByUserId(userId);
		if (StringUtils.isEmpty(weather)) {
			model.addAttribute("city", "東京都");
		} else {
			model.addAttribute("city", weather.getCityName());
		}

		// 登録ブランド名を表示するメソッドを呼び出し
		Map<Integer, String> brandMap = showBrandName(userId);
		model.addAttribute("brandMap", brandMap);

		// 登録タグ名を表示するメソッドを呼び出し
		Map<Integer, String> tagMap = showTagName(userId);
		model.addAttribute("tagMap", tagMap);

		// 「ALL」が選択されたときのアイテム表示
		if (categoryId != null && categoryId == 0 && brandId == null && tagContentsId == null) {
			List<Clothes> clothesList = showTopPageService.showItemList(userId);
			model.addAttribute("clothesList", clothesList);
			session.setAttribute("categoryId", categoryId);
		}

		// カテゴリが選択された時
		if (categoryId != null && categoryId != 0 && brandId == null && tagContentsId == null) {
			List<Clothes> clothesList = showTopPageService.showItemListByCategory(userId, categoryId);
			model.addAttribute("clothesList", clothesList);
			session.setAttribute("categoryId", categoryId);
		}

		// ブランドが選択された時
		if (brandId != null && categoryId == null && tagContentsId == null) {
			List<Clothes> clothesList = showTopPageService.showItemListByBrand(userId, brandId);
			model.addAttribute("clothesList", clothesList);
			Brand brandName = showTopPageService.findBrandName(brandId);
			session.setAttribute("showBrandName", brandName.getName());
		}

		// タグが選択された時
		if (tagContentsId != null && brandId == null && showBrandName == null) {
			List<Clothes> clothesList = showTopPageService.showItemListByTag(userId, tagContentsId);
			model.addAttribute("clothesList", clothesList);
			TagContent tagName = showTopPageService.findTagName(tagContentsId);
			session.setAttribute("showTagName", tagName.getName());
		}

		return "top";
	}

	/**
	 * 登録しているブランド名を表示します.
	 * 
	 * @param userId ユーザID
	 * @return ブランド名が格納されたマップ
	 */
	public Map<Integer, String> showBrandName(Integer userId) {

		List<Brand> brandList = showTopPageService.showBrandName(userId);
		Map<Integer, String> brandMap = new LinkedHashMap<>();

		for (Brand registerBrand : brandList) {
			Integer brandId = registerBrand.getId();
			String brandName = registerBrand.getName();
			brandMap.put(brandId, brandName);
		}
		return brandMap;
	}

	/**
	 * 登録しているタグ名を表示します.
	 * 
	 * @param userId ユーザID
	 * @return タグ名が格納されたマップ
	 */
	public Map<Integer, String> showTagName(Integer userId) {

		List<TagContent> tagNameList = showTopPageService.showTagName(userId);
		Map<Integer, String> tagMap = new LinkedHashMap<>();

		for (TagContent registerTag : tagNameList) {
			Integer tagContentsId = registerTag.getId();
			String tagContentsName = registerTag.getName();
			tagMap.put(tagContentsId, tagContentsName);
		}
		return tagMap;

	}

}
