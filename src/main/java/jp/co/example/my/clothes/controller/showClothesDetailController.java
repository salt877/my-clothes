package jp.co.example.my.clothes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.service.ShowClothesDetailService;

@Controller
@RequestMapping("/showDetail")
public class showClothesDetailController {

	@Autowired
	private ShowClothesDetailService showClothesDetailService;
	
	
}
