package jp.co.example.my.clothes.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.LoginUser;
import jp.co.example.my.clothes.domain.MonthlyDataDTO;
import jp.co.example.my.clothes.service.ShowCalendarService;

/**
 * 月別の情報を取得するRestControllerクラスです.
 * 
 * @author rinashioda
 *
 */
@RestController
@RequestMapping("/show/perchase")
public class ShowMonthlyDataController {

	@Autowired
	private ShowCalendarService showCalendarService;

	/**
	 * カレンダーに購入日・購入アイテム・金額の情報を表示します.
	 * 
	 * @param loginUser ログインユーザ
	 * @return JSON形式のデータ
	 */
	@GetMapping(value = "/all")
	public String showCalendar(@AuthenticationPrincipal LoginUser loginUser) {

		String jsonMsg = null;

//		Integer userId = loginUser.getUser().getId();
		Integer userId = 1;

		try {

			List<MonthlyDataDTO> events = new ArrayList<MonthlyDataDTO>();

			System.out.println("ユーザーIDは" + userId);
			List<Clothes> clothesList = showCalendarService.showDailyPerchaseData(userId);

			for (int i = 0; i < clothesList.size(); i++) {
				MonthlyDataDTO event = new MonthlyDataDTO();
				Integer integerItemPrice = clothesList.get(i).getPrice();
				String itemCategory = clothesList.get(i).getCategory().getName();
				Date perchaseDate2 = clothesList.get(i).getPerchaseDate();

				// 購入日と金額がわかる場合だけ行う処理（購入日がnullの時は何もしない）
				if (perchaseDate2 == null) {
					System.out.println("購入日がnullのアイテムID:" + clothesList.get(i).getId());
				} else {
					String formatItemPrice = String.format("%,d", integerItemPrice);
					String itemPrice = String.valueOf(formatItemPrice);
					event.setTitle(itemCategory + "　" + itemPrice + "円");

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String perchaseDate = dateFormat.format(perchaseDate2);
					event.setStart(perchaseDate);
				}

				events.add(event);

			}

			// FullCalendarにエンコード済み文字列を渡す
			ObjectMapper mapper = new ObjectMapper();
			jsonMsg = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);

		} catch (IOException ioex) {
			System.out.println(ioex.getMessage());
		}
		return jsonMsg;
	}
	
	
//	public Map<Integer,String> showperchaseData(Integer userId,String perchaseDate){
//		List<MonthlyDataDTO> clothesPriceList = showCalendarService.showPriceData(userId, perchaseDate);
//		System.out.println(clothesPriceList.size());
//		
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
