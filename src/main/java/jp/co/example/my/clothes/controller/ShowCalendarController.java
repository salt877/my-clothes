package jp.co.example.my.clothes.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.MonthlyDataDTO;
import jp.co.example.my.clothes.domain.UserDetail;
import jp.co.example.my.clothes.service.ShowCalendarService;
import jp.co.example.my.clothes.service.ShowUserNameService;

/**
 * カレンダーを表示するコントローラクラスです.
 * 
 * @author rinashioda
 *
 */
@Controller
public class ShowCalendarController {

	@Autowired
	private ShowCalendarService showCalendarService;

	@Autowired
	private ShowUserNameService showUserNameService;

	/**
	 * 今月のカレンダーと今月のアイテム購入情報を表示します.
	 * 
	 * @param model     リクエストスコープ
	 * @param loginUser ログインユーザ
	 * @return カレンダー画面
	 * @throws ParseException
	 */
	@RequestMapping("/calendar")
	public String showCalendar(Model model,ModelMap modelMap, @AuthenticationPrincipal LoginUser loginUser) throws ParseException {

		Integer userId = loginUser.getUser().getId();

		UserDetail userDetail = showUserNameService.showUserName(userId);
		model.addAttribute("userDetail", userDetail);
		
		String userMyqloId = loginUser.getUser().getMyqloId();
		modelMap.addAttribute("userMyqloId", userMyqloId);

		List<Clothes> dailyPerchaseDataList = showCalendarService.showDailyPerchaseData(userId);
		model.addAttribute("dailyPerchaseDataList", dailyPerchaseDataList);
		model.addAttribute("loginUserId", userId);

		Date today = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		String perchaseDate = dateFormat.format(today);

		int dateLength = perchaseDate.length();
		if (dateLength == 7) {
			String month = perchaseDate.substring(6);
			model.addAttribute("month", month);
		} else {
			String month = perchaseDate.substring(7);
			model.addAttribute("month", month);
		}

		List<MonthlyDataDTO> clothesPriceList = showCalendarService.showPriceData(userId, perchaseDate);

		if (clothesPriceList.size() == 1) {
			model.addAttribute("totalPrice", clothesPriceList.get(0).getTotalPrice());
			model.addAttribute("itemQuantity", clothesPriceList.get(0).getItemQuantity());
			model.addAttribute("priceAverage", clothesPriceList.get(0).getPriceAverage());
		} else {
			model.addAttribute("totalPrice", 0);
			model.addAttribute("itemQuantity", 0);
			model.addAttribute("priceAverage", 0);

		}

		return "calendar";
	}

}
