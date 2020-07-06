package jp.co.example.my.clothes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * faq画面を表示させるコントローラクラス.
 * 
 * @author masashi.nose
 *
 */
@Controller
public class ShowFaqController {

	/**
	 * faq画面へ遷移します。
	 * 
	 * @return faq画面
	 */
	@RequestMapping("/faq")
	public String showFaq() {
		return "faq";
	}

}
