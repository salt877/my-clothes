package jp.co.example.my.clothes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.my.clothes.service.DeleteClothesService;

@Controller
@RequestMapping("/deleteClothes")
public class DeleteClothesController {

	@Autowired
	private DeleteClothesService deleteClothesService;
	
	
	@RequestMapping("/pushDeleteButton")
	public String pushDeleteButton(Integer id) {
		
		deleteClothesService.deleteclothes(id);
		
		return "redirect:/";
		
	}
}
