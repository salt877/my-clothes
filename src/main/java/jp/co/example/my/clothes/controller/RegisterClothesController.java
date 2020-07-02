package jp.co.example.my.clothes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registerClothes")
public class RegisterClothesController {

	@RequestMapping("showRegisterClothes")
	public String showRegisterClothes() {
		return "register_clothes";
	}

	@RequestMapping("/register")
	public String Register() {
		return "top.html";

	}
}
