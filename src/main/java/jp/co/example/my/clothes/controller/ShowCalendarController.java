package jp.co.example.my.clothes.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.MonthlyDataDTO;
import jp.co.example.my.clothes.service.ShowCalendarService;

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

	/**
	 * 今月のカレンダーを表示します.
	 * 
	 * @return カレンダー画面
	 */
	@RequestMapping("/calendar")
	public String showCalendar(Model model,@AuthenticationPrincipal LoginUser loginUser) {
	
		//Integer userId = loginUser.getUser().getId();
		Integer userId = 1;
		List<Clothes> dailyPerchaseDataList = showCalendarService.showDailyPerchaseData(userId);
		model.addAttribute("dailyPerchaseDataList", dailyPerchaseDataList);
		model.addAttribute("loginUserId", 1);
		
		String perchaseDate= "2020-07";
		List<MonthlyDataDTO> clothesPriceList = showCalendarService.showPriceData(userId, perchaseDate);

		for(int i=0; i < clothesPriceList.size(); i++) {
			System.out.println("7月の合計金額:"+clothesPriceList.get(i).getTotalPrice());
			System.out.println("7月の購入アイテム数:"+clothesPriceList.get(i).getItemQuantity());
			System.out.println("7月の平均金額"+clothesPriceList.get(i).getPriceAverage());
			model.addAttribute("totalPrice",clothesPriceList.get(i).getTotalPrice());
			model.addAttribute("itemQuantity",clothesPriceList.get(i).getItemQuantity());
			model.addAttribute("priceAverage",clothesPriceList.get(i).getPriceAverage());
		}
		
		return "calendar";
	}

}
