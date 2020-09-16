package jp.co.example.my.clothes.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.Coordinate;
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

		System.out.println("リストサイズ：" + clothesList.size());

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
	 * ユーザーIDに紐づくいいねをJSON形式でリターン.
	 * 
	 * @param coordinateId コーデID
	 * @return
	 */
	@RequestMapping(value = "/show_likes", method = RequestMethod.GET)
	public Map<String, List<Like>> likeMap(@AuthenticationPrincipal LoginUser loginUser) {
		Map<String, List<Like>> LikeMap = new HashMap<>();
		int userId = loginUser.getUser().getId();

		List<Like> likeListByUserId = showCoodinateService.likeListByuserId(userId);

		if (likeListByUserId.size() == 0) {
			LikeMap.put("likeList", Collections.emptyList());

		} else {
			LikeMap.put("likeList", likeListByUserId);

		}

		return LikeMap;

	}

	/**
	 * いいねする
	 * 
	 * @param like
	 */
	@RequestMapping(value = "/like", method = RequestMethod.GET)
	public Map<String, Integer> like(@AuthenticationPrincipal LoginUser loginUser, Integer coordinateId) {
		Like like = new Like();
		like.setCoordinateId(coordinateId);
		like.setUserId(loginUser.getUser().getId());

		showCoodinateService.like(like);

		Map<String, Integer> likeMap = new HashMap<>();
		Integer likes = showCoodinateService.showLikes(coordinateId).size();

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
	public Map<String, Integer> deleteLike(@AuthenticationPrincipal LoginUser loginUser, Integer coordinateId) {
		showCoodinateService.deleteLike(coordinateId, loginUser.getUser().getId());

		Map<String, Integer> likeMap = new HashMap<>();
		Integer likes = showCoodinateService.showLikes(coordinateId).size();

		likeMap.put("likes", likes);

		return likeMap;

	}

	/**
	 * ユーザーIDでコーデ検索
	 * 
	 * @param loginUser ログイン情報
	 * @return
	 */
	@RequestMapping(value = "/find_coordinate", method = RequestMethod.GET)
	public Map<String, List<Coordinate>> findCoordinateByUserId(@AuthenticationPrincipal LoginUser loginUser) {
		Map<String, List<Coordinate>> coordinateMapByUserId = new HashMap<>();

		List<Coordinate> coordinateListByUserId = showCoodinateService.showCoordinate(loginUser.getUser().getId());

		coordinateMapByUserId.put("coordinateListByUserId", coordinateListByUserId);

		return coordinateMapByUserId;

	}

	/**
	 * 公開情報を更新します.
	 * 
	 * @param coordinateId コーデID
	 * @param isPublic     公開フラグ
	 */
	@RequestMapping(value = "/update_isPublic", method = RequestMethod.GET)
	public void updateIsPublic(Integer coordinateId, boolean isPublic) {
		System.out.println("コーデID：" + coordinateId + "/" + "フラグ:" + isPublic);

		showCoodinateService.updateIsPublic(coordinateId, isPublic);

	}

	/**
	 * コーデIDでログインユーザーIDをチェックします.
	 * 
	 * @param loginUser
	 * @param coordinateId
	 * @return
	 */
	@RequestMapping(value="/check_user", method = RequestMethod.GET)
	public Map<String, Boolean> checkUserId(@AuthenticationPrincipal LoginUser loginUser, Integer coordinateId) {
		Map<String, Boolean> checkUserIdMap = new HashMap<>();
		Coordinate coordinate = showCoodinateService.showCoordinateDetailForPublicCoordinate(coordinateId);

		Integer userId = loginUser.getUser().getId();
				
		if (coordinate.getUserId() == userId) {
			checkUserIdMap.put("isLogin", true);

		} else {
			checkUserIdMap.put("isLogin", false);
			
		}

		return checkUserIdMap;

	}

}
