package jp.co.example.my.clothes.domain;

/**
 * 購入月毎の服データを格納するDTO.
 * 
 * @author rinashioda
 *
 */
public class MonthlyDataDTO {

	private String title;

	private String start;

	private String end;

	private Integer totalPrice;

	private Integer itemQuantity;

	private Integer priceAverage;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Integer itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Integer getPriceAverage() {
		return priceAverage;
	}

	public void setPriceAverage(Integer priceAverage) {
		this.priceAverage = priceAverage;
	}

}
