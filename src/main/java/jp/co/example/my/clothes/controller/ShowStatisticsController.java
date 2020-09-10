package jp.co.example.my.clothes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.AverageDto;
import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.service.ShowStatisticsService;
import jp.co.example.my.clothes.service.ShowUserNameService;

/**
 * 
 * 統計表示を行うコントローラ.
 * 
 * @author masashi.nose
 *
 */
@Controller
public class ShowStatisticsController {

	@Autowired
	private ShowStatisticsService showStatisticsService;
	
	@Autowired
	private ShowUserNameService showUserNameService;


	/**
	 * ユーザーごとの統計画面を表示します.
	 * 
	 * @param userId ユーザーID
	 * @param model
	 * @return
	 */
	@RequestMapping("/stats")
	public String showStatistics(@AuthenticationPrincipal LoginUser loginUser, Model model,ModelMap modelMap) {
		Integer userId = loginUser.getUser().getId();
		Integer totalItemCount = 0;
		Integer totalItemPrice = 0;
		
		UserDetail userDetail = showUserNameService.showUserName(userId);
		model.addAttribute("userDetail", userDetail);
		
		String userMyqloId = loginUser.getUser().getMyqloId();
		modelMap.addAttribute("userMyqloId", userMyqloId);

		// ユーザーIDに紐づく服データ全件取得
		List<Clothes> clothesListByUserId = showStatisticsService.showStatsByUserId(userId);

		// アイテム登録が１件もない場合、統計画面には何も表示せず、アイテム登録画面へ誘導します。
		if (clothesListByUserId == null) {
			model.addAttribute("clothesListByUserId", clothesListByUserId);
			model.addAttribute("message", "表示するデータがありません。");

			return "statistics";

		}

		// 合計点数
		totalItemCount = clothesListByUserId.size();

		// 合計金額		
		for (Clothes clothesByUserId : clothesListByUserId) {
			totalItemPrice += clothesByUserId.getPrice();

		}

		// 平均金額(アイテム金額登録しているもののみ)
		AverageDto averagePrice = showStatisticsService.showAveragePrice(userId);

		model.addAttribute("clothesListByUserId", clothesListByUserId);
		model.addAttribute("totalItemCount", totalItemCount);
		model.addAttribute("totalItemPrice", totalItemPrice);
		model.addAttribute("averagePrice", averagePrice);

		return "statistics";
	}

}
