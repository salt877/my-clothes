package jp.co.example.my.clothes.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class ShowMonthlyDataRestController {

	@Autowired
	private ShowCalendarService showCalendarService;

	/**
	 * カレンダーに購入日・購入アイテム・金額の情報を表示します.
	 * 
	 * @param loginUser ログインユーザ
	 * @return JSON形式のデータ
	 */
	@GetMapping("/all")
	public String showCalendar(@AuthenticationPrincipal LoginUser loginUser) {

		String jsonMsg = null;

		Integer userId = loginUser.getUser().getId();

		try {

			List<MonthlyDataDTO> events = new ArrayList<MonthlyDataDTO>();
			List<Clothes> clothesList = showCalendarService.showDailyPerchaseData(userId);

			for (Clothes clothesData : clothesList) {
				MonthlyDataDTO event = new MonthlyDataDTO();
				Integer integerItemPrice = clothesData.getPrice();
				String itemCategory = clothesData.getCategory().getName();
				Date perchaseDate2 = clothesData.getPerchaseDate();
				Integer clothesId = clothesData.getId();

				// 購入日と金額がわかる場合だけ行う処理（購入日がnullの時は何もしない）
				if (perchaseDate2 == null) {

				} else {
					String formatItemPrice = String.format("%,d", integerItemPrice);
					String itemPrice = String.valueOf(formatItemPrice);

					event.setUrl("/showDetail/?id=" + clothesId);

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String perchaseDate = dateFormat.format(perchaseDate2);
					event.setStart(perchaseDate);

					if (itemCategory.equals("ファッション雑貨")) {
						event.setColor("#a6cee3");
					}
					if (itemCategory.equals("トップス")) {
						event.setColor("#1f78b4");
					}
					if (itemCategory.equals("アウター")) {
						event.setColor("#b2df8a");
					}
					if (itemCategory.equals("ボトムス")) {
						event.setColor("#33a02c");
					}
					if (itemCategory.equals("シューズ")) {
						event.setColor("#fb9a99");
					}
					if (itemCategory.equals("バッグ")) {
						event.setColor("#e31a1c");
					}
					if (itemCategory.equals("ワンピース")) {
						event.setColor("#fdbf6f");
					}

					// 購入金額がnullで登録されている場合はカテゴリのみ表示
					if (integerItemPrice == null) {
						event.setTitle(" " + itemCategory + "　");
						// 購入金額がnull以外で登録されている場合は「○円」まで表示（0も含む）
					} else {
						event.setTitle(" " + itemCategory + "　" + itemPrice + " 円");
					}

				}
				events.add(event);
			}

			// FullCalendarにエンコード済み文字列を渡す
			ObjectMapper mapper = new ObjectMapper();
			jsonMsg = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);

		} catch (

		IOException ioex) {
			System.out.println(ioex.getMessage());
		}
		return jsonMsg;
	}

	/**
	 * 月毎の購入情報を表示します.
	 * 
	 * @param userId       ユーザID
	 * @param perchaseDate 購入年月
	 * @return 購入情報の詰まったマップ
	 */
	@GetMapping("/data")
	public Map<String, Integer> show(Integer userId, String perchaseDate) {

		int dateLength = perchaseDate.length();
		String replaceYear;

		if (dateLength == 7) {
			replaceYear = perchaseDate.replace("年", "-0");

		} else {
			replaceYear = perchaseDate.replace("年", "-");
		}

		String removedMonth = replaceYear.replace("月", "");

		Map<String, Integer> clothesMap = new HashMap<>();
		List<MonthlyDataDTO> clothesPriceList = showCalendarService.showPriceData(userId, removedMonth);

		String stringMonth = perchaseDate.substring(5);
		String stringMonth2 = stringMonth.replace("月", "");
		Integer integerMonth = Integer.parseInt(stringMonth2);
		clothesMap.put("month", integerMonth);

		if (clothesPriceList.size() == 1) {
			Integer totalPrice = clothesPriceList.get(0).getTotalPrice();
			Integer priceAverage = clothesPriceList.get(0).getPriceAverage();
			Integer itemQuantity = clothesPriceList.get(0).getItemQuantity();

			clothesMap.put("totalPrice", totalPrice);
			clothesMap.put("priceAverage", priceAverage);
			clothesMap.put("itemQuantity", itemQuantity);

		} else if (clothesPriceList.size() == 0) {
			clothesMap.put("totalPrice", 0);
			clothesMap.put("priceAverage", 0);
			clothesMap.put("itemQuantity", 0);

		}

		return clothesMap;
	}

}
