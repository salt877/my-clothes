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
import jp.co.example.my.clothes.domain.Like;
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

		List<Clothes> clothesList = showTopPageService.showItemListByCategoryForCoordinate(loginUser.getUser().getId(),
				categoryId);

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

	/**
	 * コーデIDに紐づくいいね数をJSON形式でリターン.
	 * 
	 * @param coordinateId コーデID
	 * @return
	 */
	@RequestMapping(value = "/show_likes", method = RequestMethod.GET)
	public Map<String, Integer> likeMap(Integer coordinateId) {
		Map<String, Integer> likeMap = new HashMap<>();
		Integer likes = showCoodinateService.showLikes(coordinateId).size();

		likeMap.put("likes", likes);

		return likeMap;
	}

	/**
	 * いいねする
	 * 
	 * @param like
	 */
	@RequestMapping(value = "/like", method = RequestMethod.GET)
	public Map<String, Integer> like(Like like) {
		showCoodinateService.like(like);

		Map<String, Integer> likeMap = new HashMap<>();
		Integer likes = showCoodinateService.showLikes(like.getCoordinateId()).size();

		likeMap.put("likes", likes);

		return likeMap;

	}

	/**
	 * いいねを削除する.
	 * 
	 * @param userId       ユーザーID
	 * @param coordinateId コーデID
	 * @return
	 */
	@RequestMapping(value = "/deleteLike", method = RequestMethod.GET)
	public Map<String, Integer> deleteLike(Integer userId, Integer coordinateId) {
		showCoodinateService.deleteLike(userId);

		Map<String, Integer> likeMap = new HashMap<>();
		Integer likes = showCoodinateService.showLikes(coordinateId).size();

		likeMap.put("likes", likes);

		return likeMap;

	}

}
