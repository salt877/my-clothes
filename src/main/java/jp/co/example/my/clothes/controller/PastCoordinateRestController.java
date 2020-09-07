package jp.co.example.my.clothes.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.example.my.clothes.domain.Clothes;
import jp.co.example.my.clothes.domain.Coordinate;
import jp.co.example.my.clothes.service.ShowCoordinateService;

@RestController
public class PastCoordinateRestController {

	@Autowired
	private ShowCoordinateService showCoordinateService;

	@RequestMapping(value = "/culc_pastcoordinate_price", method = RequestMethod.GET)
	public Map<String, Integer> culcCoordinatePriceForPast(Integer coordinateId) {
		Coordinate coordinate = showCoordinateService.showCoordinateDetailForPastCoordinate(coordinateId);

		int totalPrice = 0;

		for (Clothes clothes : coordinate.getClothesList()) {
			totalPrice += clothes.getPrice();
		}

		Map<String, Integer> priceMap = new HashMap<>();
		priceMap.put("totalPrice", totalPrice);

		return priceMap;

	}
	
	@RequestMapping(value = "/culc_publiccoordinate_price", method = RequestMethod.GET)
	public Map<String, Integer> culcCoordinatePriceForPublic(Integer coordinateId) {
		Coordinate coordinate = showCoordinateService.showCoordinateDetailForPublicCoordinate(coordinateId);

		int totalPrice = 0;

		for (Clothes clothes : coordinate.getClothesList()) {
			totalPrice += clothes.getPrice();
		}

		Map<String, Integer> priceMap = new HashMap<>();
		priceMap.put("totalPrice", totalPrice);

		return priceMap;

	}
	
	

}
