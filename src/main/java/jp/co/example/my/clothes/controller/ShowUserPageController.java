package jp.co.example.my.clothes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.example.my.clothes.domain.Coordinate;
import jp.co.example.my.clothes.domain.Like;
import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.service.ShowCoordinateService;
import jp.co.example.my.clothes.service.ShowMyPageService;
import jp.co.example.my.clothes.service.ShowUserNameService;
import jp.co.example.my.clothes.service.ShowUserPageService;

/**
 * ユーザーページ（他人から見たマイページ）を表示するコントローラクラスです.
 * 
 * @author rinashioda
 *
 */
@Controller
public class ShowUserPageController {

	@Autowired
	private ShowUserPageService showUserPageService;

	@Autowired
	private ShowCoordinateService showCoordinateService;
	
	@Autowired
	private ShowUserNameService showUserNameService;

	/**
	 * ユーザーページを表示します.
	 * 
	 * @param model
	 * @param userMyqloId
	 * @param loginUser
	 * @return
	 */
	@RequestMapping("/{userMyqloId}")
	public String showMyPage(Model model, @PathVariable("userMyqloId") String userMyqloId, @AuthenticationPrincipal LoginUser loginUser) {

		UserDetail userInfomation = showUserPageService.showUserDetail(userMyqloId);
		model.addAttribute("userInfomation", userInfomation);
		model.addAttribute("userDetail", userInfomation);

		List<Coordinate> publicCoordinateList = showCoordinateService.showCoordinateByMyqloId(userMyqloId);

		for (Coordinate coodinate : publicCoordinateList) {
			List<Like> likeList = showCoordinateService.showLikes(coodinate.getId());
			coodinate.setLikeList(likeList);
		}
		
		Integer userId = loginUser.getUser().getId();
		UserDetail userDetail = showUserNameService.showUserName(userId);
		model.addAttribute("userDetail", userDetail);

		model.addAttribute("publicCoordinateList", publicCoordinateList);

		// 該当するMYQLOIDを持つユーザがいない場合の処理
		if (showUserPageService.showUserDetail(userMyqloId) == null) {
			return "error/404";
		}

		return "myqlo_user_page";
	}

}
