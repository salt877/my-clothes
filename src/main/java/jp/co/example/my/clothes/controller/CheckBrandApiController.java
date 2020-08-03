//package jp.co.example.my.clothes.controller;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import jp.co.example.my.clothes.domain.Brand;
//import jp.co.example.my.clothes.service.RegisterClothesService;
//
//@RestController
//@RequestMapping(value = "/check_brand_api")
//public class CheckBrandApiController {
//
//	@Autowired
//	private RegisterClothesService registerClothesService;
//
//	@RequestMapping(value = "/brandcheck", method = RequestMethod.POST)
//	public Map<String, String> emailcheck(String brand) {
//
//		Map<String, String> map = new HashMap<>();
//
//		String duplicateMessage = null;
//
//		List<Brand> brandList = registerClothesService.brandList();
//
//		for (Brand confirmBrand : brandList) {
//			if (confirmBrand.equals(brand)) {
//				duplicateMessage = "「" + brand + "」は登録できます";
//			} else {
//				duplicateMessage = "「" + brand + "」は登録されていません";
//			}
//		}
//		return map;
//	}
//}
