package jp.co.example.my.clothes.controller;

import java.util.ArrayList;
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
	 * コーデIDに紐づくいいねをJSON形式でリターン.
	 * 
	 * @param coordinateId コーデID
	 * @return
	 */
	@RequestMapping(value = "/show_likes", method = RequestMethod.GET)
	public Map<String, List<Like>> likeMap(@AuthenticationPrincipal LoginUser loginUser, Integer coordinateId) {
		Map<String, List<Like>> LikeMap = new HashMap<>();
		
		List<Like> likeListByCoordinateId = showCoodinateService.showLikes(coordinateId);
		
		if(likeListByCoordinateId.size() == 0) {
			LikeMap.put("likeMap", Collections.emptyList());
			
		}
		
		List<Like> likeListByUserId = new ArrayList<>();
		
		for(Like like : likeListByCoordinateId) {
			if(like.getUserId() == loginUser.getUser().getId()) {
				likeListByUserId.add(like);
			}
		}
		
		if(likeListByUserId.size() == 0) {
			LikeMap.put("likeMap", Collections.emptyList());
		}
		
		LikeMap.put("likeMap", likeListByUserId);
		
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
		showCoodinateService.deleteLike(loginUser.getUser().getId());

		Map<String, Integer> likeMap = new HashMap<>();
		Integer likes = showCoodinateService.showLikes(coordinateId).size();

		likeMap.put("likes", likes);

		return likeMap;

	}

}
