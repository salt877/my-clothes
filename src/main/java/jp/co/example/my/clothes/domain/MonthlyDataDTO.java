package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 購入月毎の服データを格納するDTO.
 * 
 * @author rinashioda
 *
 */
@Getter
@Setter
public class MonthlyDataDTO {

	/** タイトル */
	private String title;

	/** 購入日 */
	private String start;

	/** URL */
	private String url;

	/** イベントの色 */
	private String color;

	/** 合計金額 */
	private Integer totalPrice;

	/** アイテム数量 */
	private Integer itemQuantity;

	/** アイテム購入平均額 */
	private Integer priceAverage;

}
