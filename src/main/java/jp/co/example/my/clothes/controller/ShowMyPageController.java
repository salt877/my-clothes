package jp.co.example.my.clothes.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.Coordinate;
import jp.co.example.my.clothes.domain.Like;
import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.service.ShowCoordinateService;
import jp.co.example.my.clothes.service.ShowMyPageService;
import jp.co.example.my.clothes.service.ShowUserPageService;

@Controller
public class ShowMyPageController {

	@Autowired
	private ShowMyPageService showMyPageService;
	
	@Autowired
	private ShowCoordinateService showCoordinateService;

	@Autowired
	private ShowUserPageService showUserPageService;

	/**
	 * ログインしているユーザのマイページを表示します.
	 * 
	 * @param loginUser ログインユーザ
	 * @return マイページ
	 */
	@RequestMapping("/mypage")
	public String showMyPage(Model model, ModelMap modelMap, @AuthenticationPrincipal LoginUser loginUser){

		Integer userId = loginUser.getUser().getId();
		UserDetail userInfomation = showMyPageService.showMyPage(userId);
		model.addAttribute("userInfomation", userInfomation);
		model.addAttribute("userDetail", userInfomation);

		User user = showUserPageService.searchMyqloId(userId);
		String userMyqloId = user.getMyqloId();
		modelMap.addAttribute("userMyqloId", userMyqloId);
		
		// 過去のコーデ
		List<Coordinate> coordinateList = showCoordinateService.showCoordinate(userId);
		model.addAttribute("coordinateList", coordinateList);
		
		for(Coordinate coordinate : coordinateList) {
			List<Like> likeList = showCoordinateService.showLikes(coordinate.getId());
			coordinate.setLikeList(likeList);
		}
		
		// いいねされたコーデ
		List<Coordinate> likedCoordinateList = showCoordinateService.showLikedCoordinate(userId);
		model.addAttribute("likedCoordinateList", likedCoordinateList);
		
		for(Coordinate likedCoordinate : likedCoordinateList) {
			List<Like> likeList = showCoordinateService.showLikes(likedCoordinate.getId());
			likedCoordinate.setLikeList(likeList);
		}
		
//		for(int i = 0; i < likedCoordinateList.size(); i++) {
//			Coordinate likedCoordinate = likedCoordinateList.get(i);
//			List<Like> likeList = showCoordinateService.showLikes(likedCoordinate.getId());
//				if(likeList.size() == 0) {
//					likedCoordinateList.remove(likedCoordinate);
//					System.out.println(likedCoordinateList);
//				} else if(likeList.size() > 0) {
//					likedCoordinateList.add(likedCoordinate);
//					System.out.println(likedCoordinateList);
//				}
//				likedCoordinate.setLikeList(likeList);
//		}
		
		// いいねしたコーデ
		List<Coordinate> likeCoordinateList = showCoordinateService.showLikeCoordinate(userId);
		model.addAttribute("likeCoordinateList", likeCoordinateList);
				
		for(Coordinate likeCoordinate : likeCoordinateList) {
			List<Like> likeList = showCoordinateService.showLikes(likeCoordinate.getId());
			likeCoordinate.setLikeList(likeList);
		}
		
		return "my_page";
	}

}
