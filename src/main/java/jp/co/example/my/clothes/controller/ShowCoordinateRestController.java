package jp.co.example.my.clothes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.service.ShowCoordinateService;
import jp.co.example.my.clothes.service.ShowTopPageService;

/**
 * コーディネート画面でカテゴリー別の情報を表示させる処理を行います.
 * 
 * @author masashi.nose
 *
 */
@RestController
public class ShowCoordinateRestController {

	@Autowired
	private ShowTopPageService showTopPageService;

	@Autowired
	private ShowCoordinateService showCoodinateService;

	/**
	 * ユーザー別カテゴリーごとのアイテム情報をJSON形式で返す.
	 * 
	 * @param loginUser
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value = "/show_clotheslist", method = RequestMethod.GET)
	public Map<String, List<Clothes>> clothesMap(@AuthenticationPrincipal LoginUser loginUser, Integer categoryId) {
		Map<String, List<Clothes>> clothesMap = new HashMap<>();

		List<Clothes> clothesList = showTopPageService.showItemListByCategoryForCoordinate(loginUser.getUser().getId(), categoryId);

		clothesMap.put("clothesList", clothesList);

		return clothesMap;
	}

	/**
	 * コーディネートIDに紐づくコーディネートデータを論理削除.
	 * 
	 * @param coordinateId
	 */
	@RequestMapping(value = "/delete_coordinate", method = RequestMethod.GET)
	public void deleteCoordinate(Integer coordinateId) {
		showCoodinateService.deleteCoordinate(coordinateId);
		
	}
	
	

}
